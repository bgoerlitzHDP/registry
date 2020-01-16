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

public abstract class HTTPPostRequest<T> extends HTTPRequest<T> {

    private Object payload;

    public HTTPPostRequest(WebTarget target, Object payload, Class<T> clazz) {
        super(target, clazz);

        Objects.requireNonNull(payload, "payload can't be null");

        this.payload = payload;
    }

    @Override
    public T request() {
        return apply(target, payload, responseType);
    }

    public abstract T apply(WebTarget target, Object payload, Class<T> clazz);

}
