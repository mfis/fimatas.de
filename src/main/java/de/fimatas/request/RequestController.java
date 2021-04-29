package de.fimatas.request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {

    @RequestMapping("/")
    public String main(Model model) {
        return "main";
    }

    @RequestMapping("/impressum")
    public String impressum(Model model) {
        return "impressum";
    }
}
