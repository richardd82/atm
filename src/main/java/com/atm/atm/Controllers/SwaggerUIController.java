package com.atm.atm.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerUIController {

    @RequestMapping("/swagger-ui")
    public String redirectToUi() {
        return "redirect:/swagger-ui/index.html";
    }
}
