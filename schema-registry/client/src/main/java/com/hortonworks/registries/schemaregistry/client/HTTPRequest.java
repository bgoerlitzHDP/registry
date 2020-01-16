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

import javax.ws.rs.client.WebTarget;
import java.util.Objects;

public abstract class HTTPRequest<T> {

    protected WebTarget target;
    protected Class<T> responseType;

    public HTTPRequest(WebTarget target, Class<T> responseType) {
        Objects.requireNonNull(target, "target can't be null");
        Objects.requireNonNull(responseType, "responseType can't be null");

        this.target = target;
        this.responseType = responseType;
    }

    public void maybeUpdateTarget(WebTarget newTarget) {
        if (target.equals(newTarget)) {
            target = newTarget;
        }
    }

    public abstract T request();

}
