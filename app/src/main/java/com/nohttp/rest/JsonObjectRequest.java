/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nohttp.rest;

import com.nohttp.Headers;
import com.nohttp.RequestMethod;

import org.json.JSONObject;

/**
 * <p>JsonObject is returned by the server data, using the request object.</p>
 * Created in Jan 19, 2016 3:27:35 PM.
 *
 * @author Yan Zhenjie.
 */
public class JsonObjectRequest extends RestRequest<JSONObject> {

    public JsonObjectRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public JsonObjectRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setAccept(Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON);
    }

    @Override
    public JSONObject parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String jsonStr = StringRequest.parseResponseString(responseHeaders, responseBody);
        return new JSONObject(jsonStr);
    }
}