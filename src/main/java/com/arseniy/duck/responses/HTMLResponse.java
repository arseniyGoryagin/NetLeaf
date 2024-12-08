package com.arseniy.duck.responses;

import com.arseniy.duck.HttpStatusCode;
import com.arseniy.duck.constants.HttpConstants;

final public class HTMLResponse extends HttpResponse {
    public HTMLResponse(HttpStatusCode code,  String body) {
        super(code.getCode(), code.getMessage(), HttpConstants.HTTP_VERSION_1_1, body);
        super.addHeader("Content-Type", "text/html");
    }
}
