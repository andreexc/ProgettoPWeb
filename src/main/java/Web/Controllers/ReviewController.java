package Web.Controllers;

import Web.Controllers.DTO.RecensioneRequest;
import Web.Services.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboard/recensioni")
public class ReviewController {
    private final ReviewService service;
    public ReviewController(ReviewService service) { this.service = service; }

    @GetMapping("/api/carosello")
    public String getCarosello(Model model) {
        model.addAttribute("recensioni", service.getAllRecensioni());
        return "fragments/review :: carouselRecensioni";
    }

    @PostMapping("/api/invia")
    @ResponseBody
    public void invia(@RequestBody RecensioneRequest req, Authentication auth) {
        service.salvaRecensione(auth.getName(), req.getTesto());
    }
}