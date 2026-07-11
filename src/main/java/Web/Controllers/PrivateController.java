package Web.Controllers;

import Web.Services.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class PrivateController {

    private final AdminService adminService;

    // Injecting service
    public PrivateController(AdminService adminService) {
        this.adminService = adminService;
    }

    /* Post-Login dispatcher. Redirects by the user role to the right page */
    @GetMapping("/dashboard")
    public String dispatchUserByRole(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMIN" -> { return "redirect:/admin/home"; }
                case "ROLE_USER_PRO" -> { return "redirect:/dashboard/pro/home"; }
                case "ROLE_USER_BASIC" -> { return "redirect:/dashboard/basic/home"; }
                case "ROLE_USER_PROVA" -> { return "redirect:/dashboard/prova/home"; }
            }
        }
        return "redirect:/index";
    }

    // --- admin routes ---

    @GetMapping("/admin/home")
    public String adminHome(Model model, Authentication authentication) {
        // Welcome message
        if (authentication != null) {
            model.addAttribute("adminUsername", authentication.getName());
        }

        // Ordered list
        List<Map<String, Object>> utenti = adminService.ottieniListaUtenti();
        model.addAttribute("listaUtenti", utenti);

        return "admin/admin_dashboard";
    }

    @PostMapping("/admin/rimuovi-scaduti")
    public String rimuoviScaduti(RedirectAttributes redirectAttributes) {
        // Remove disabled users
        int utentiRimossi = adminService.rimuoviUtentiScaduti();


        // RedirectAttributes (FlashAttribute) grants to send the feedback message through a redirect
        // so thymeleaf can read it when building the template after the redirect
        redirectAttributes.addFlashAttribute("messaggioRimozione",
                "Operazione completata. Sono stati rimossi " + utentiRimossi + " utenti scaduti.");

        return "redirect:/admin/home";
    }

    // --- user routes ---

    @GetMapping("/dashboard/basic/home")
    public String basicHome(Model model, Authentication authentication) {
        return "private/basic_dashboard";
    }

    @GetMapping("/dashboard/pro/home")
    public String proHome(Model model, Authentication authentication) {
        return "private/pro_dashboard";
    }

    @GetMapping("/dashboard/prova/home")
    public String provaHome(Model model, Authentication authentication) {
        return "private/prova_dashboard";
    }
}