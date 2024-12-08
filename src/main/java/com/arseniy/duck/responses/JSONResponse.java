package com.arseniy.duck.responses;

import com.arseniy.duck.HttpStatusCode;

import static com.arseniy.duck.constants.HttpConstants.HTTP_VERSION_1_1;

final public class JSONResponse extends HttpResponse{

    public JSONResponse(HttpStatusCode code, String body){
        super(code.getCode(), code.getMessage(), HTTP_VERSION_1_1, body);
        super.addHeader("Content-Type", "application/json");
    }


}
