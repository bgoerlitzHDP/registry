/*
 * Copyright 2016-2019 Cloudera, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hortonworks.registries.schemaregistry.client;

public class RequestHandler {

    private RetryPolicy retryPolicy;
    private RetryCondition retryCondition;

    public RequestHandler retryWhen(RetryCondition retryCondition) {
        this.retryCondition = retryCondition;
        return this;
    }

    public RequestHandler policy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    public <T> T request(HTTPRequest<T> HTTPRequest) {
        RetryPolicyInput input = new RetryPolicyInput(0, System.currentTimeMillis());
        RetryPolicyOutput output = null;
        T result = null;
        Exception exception = null;
        do {
            try {
                output = retryPolicy.checkIfRetryRequired(input);
                HTTPRequest.maybeUpdateTarget(output.target());
                result = HTTPRequest.request();
            } catch (Exception e) {
                exception = e;
            }
        } while (output.shouldExecute() && retryCondition.shouldRetry(result, exception));
    }
}
