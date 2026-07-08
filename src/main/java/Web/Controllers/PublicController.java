package Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping({"/", "/index"})
    public String home() { return "index"; }

    @GetMapping("/signup")
    public String signup() { return ""; }

    @GetMapping("/login")
    public String login() { return ""; }

    @GetMapping("/contatti")
    public String contatti() { return "contatti"; }
}