package com.arseniy.duck.responses;

import java.util.HashMap;
import java.util.Map;

sealed public class HttpResponse permits JSONResponse, HTMLResponse {


    private String version = "";
    private String code = "";
    private String message ="";
    private Map<String, String> headers = new HashMap<>();
    private String body = "";


    public void  addHeader(String key, String value ){
        headers.put(key, value);
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

    HttpResponse(String code, String message, String version, String body){
        this.body =  body;
        this.code = code;
        this.version = version;
        this.message =message;
    }



}
