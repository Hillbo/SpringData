package com.sdata.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestService extends ResourceConfig {

    public RestService() {
        packages("com.sdata.controller");
    }

}
