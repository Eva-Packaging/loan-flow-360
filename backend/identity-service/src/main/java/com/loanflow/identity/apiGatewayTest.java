package com.loanflow.identity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class apiGatewayTest {
    @GetMapping("/apiGatewayTest")
    public String getApiGatewayTest() {
        return "This is api gateway test for identity route";
    }
    
}
