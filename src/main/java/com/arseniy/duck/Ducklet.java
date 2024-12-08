package com.arseniy.duck;

import com.arseniy.duck.responses.HttpResponse;

public interface Ducklet {
    HttpResponse handle(HttpRequest request);
}
