package com.dzl.sport.pcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CenterPortalController {
    @RequestMapping("")
    public String getIndex(){
        return "index";
    }
}
