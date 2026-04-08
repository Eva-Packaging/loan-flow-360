package com.loanflow.identity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/identityService")
public class apiGatewayTest {
    @GetMapping("/apiGatewayTest")
    public String getApiGatewayTest() {
        return "This is api gateway test for identity route";
    }
    
}
