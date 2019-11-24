package se.test.k2htestbench.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {

    @GetMapping(path = "/hub")
    public String root(){
        return "Some data";
    }
}
