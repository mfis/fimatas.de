package de.fimatas.request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class RequestController {

    private final static String MAIL_ADDRESS = "homepage-kontakt";
    private final static String MAIL_HOST = "fimatas.de";

    @RequestMapping("/")
    public String root(Model model) {
        processModel(model);
        return "root";
    }

    @RequestMapping("/impressum")
    public String impressum(Model model) {
        processModel(model);
        return "impressum";
    }

    @RequestMapping("/impressum/")
    public String impressum2(Model model) {
        processModel(model);
        return "impressum";
    }

    private void processModel(Model model){
        model.addAttribute("email", obfuscateEmail(MAIL_ADDRESS + "@" + MAIL_HOST));
    }

    private String obfuscateEmail(@SuppressWarnings("SameParameterValue") String email) {
        var text = "E-Mail: " + email;
        StringBuilder obfuscatedEmail = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String htmlEntity = "&#" + (int) c + ";";
            obfuscatedEmail.append(htmlEntity);
            obfuscatedEmail.append("<!-- ").append("x").append(" -->");
            obfuscatedEmail.append("<!--\n-->");
        }
        return obfuscatedEmail.toString();
    }
}
