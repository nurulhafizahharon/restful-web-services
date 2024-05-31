package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// REST API
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class HelloWorldController {

    @GetMapping(path = "/basicauth")
    public String basicAuthCheck() {
        return "Success";
    }

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World v2";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean v1");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
