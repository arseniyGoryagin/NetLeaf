package com.arseniy.duck;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method = "";
    private String path = "";
    private String version ="";
    private Map<String, String> headers =new HashMap<>();
    private String body ="";

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void  addHeader(String key, String value ){
        headers.put(key, value);
    }
}
