package com.chongdao.client.controller;


import com.chongdao.client.utils.TokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public Map login(String token){
        return  TokenUtil.validateToken(token);
    }
}
