package com.mdem.costms.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api(tags = {"Home"}, description = "Controller for Home page")
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Visit to Home page")
    public String homePage() {
        return "WEB-INF/index.jsp";
    }
}
