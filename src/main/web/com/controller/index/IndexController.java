package com.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
    @RequestMapping(value = "/index")
    public String index(){
        return "/index/index";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "/index/shoplist";
    }
}
