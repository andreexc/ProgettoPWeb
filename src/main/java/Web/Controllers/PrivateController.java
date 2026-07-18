package Web.Controllers;

import Web.Services.AdminService;
import Web.Services.UserService;
import Web.Services.StatisticsService; // Iniettato correttamente per i grafici utente
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import Web.Clients.ProgramClient;

import Web.Controllers.DTO.Training.Programma;

@Controller
public class PrivateController {

    private final AdminService adminService;
    private final UserService userService;
    private final StatisticsService statisticsService;
    private final ProgramClient programClient;

    public PrivateController(AdminService adminService, UserService userService, StatisticsService statisticsService, ProgramClient programClient) {
        this.adminService = adminService;
        this.userService = userService;
        this.statisticsService = statisticsService;
        this.programClient = programClient;
    }

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
        if (authentication != null) {
            model.addAttribute("adminUsername", authentication.getName());
        }
        List<Map<String, Object>> utenti = adminService.ottieniListaUtenti();
        model.addAttribute("listaUtenti", utenti);
        return "admin/admin_dashboard";
    }

    @PostMapping("/admin/rimuovi-scaduti")
    public String rimuoviScaduti(RedirectAttributes redirectAttributes) {
        int utentiRimossi = adminService.rimuoviUtentiScaduti();
        redirectAttributes.addFlashAttribute("messaggioRimozione",
                "Operazione completata. Sono stati rimossi " + utentiRimossi + " utenti scaduti.");
        return "redirect:/admin/home";
    }

    // --- user routes ---
    @GetMapping("/dashboard/prova/home")
    public String provaHome(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("usernameUtente", username);

            int completati = statisticsService.getConteggioAllenamenti(username);
            model.addAttribute("allenamentiSvolti", completati);
        }
        return "private/prova_dashboard";
    }

    @GetMapping("/dashboard/basic/home")
    public String basicHome(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("usernameUtente", username);

            Map<String, Integer> statsPersonali = statisticsService.getAllenamentiUtentePerProgramma(username);
            model.addAttribute("statsLabels", statsPersonali.keySet());
            model.addAttribute("statsValori", statsPersonali.values());
        }
        return "private/basic_dashboard";
    }

    @GetMapping("/dashboard/pro/home")
    public String proHome(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            model.addAttribute("usernameUtente", username);

            Map<String, Integer> statsPersonali = statisticsService.getAllenamentiUtentePerProgramma(username);
            model.addAttribute("statsLabels", statsPersonali.keySet());
            model.addAttribute("statsValori", statsPersonali.values());
        }
        return "private/pro_dashboard";
    }

    // --- utility shared routes ---
    @GetMapping("/dashboard/profilo")
    public String visualizzaProfilo(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            Map<String, Object> profilo = userService.ottieniProfilo(username);

            model.addAttribute("usernameUtente", username);
            model.addAttribute("nome", profilo.get("nome"));
            model.addAttribute("cognome", profilo.get("cognome"));
            model.addAttribute("dataNascita", profilo.get("data_nascita"));

            String ruoloRaw = (String) profilo.get("authority");
            String ruoloPulito = "PROVA";

            if (ruoloRaw != null) {
                if (ruoloRaw.startsWith("ROLE_USER_")) {
                    ruoloPulito = ruoloRaw.replace("ROLE_USER_", "");
                } else if (ruoloRaw.equals("ROLE_ADMIN")) {
                    ruoloPulito = "ADMIN";
                }
            }
            model.addAttribute("ruolo", ruoloPulito);
        }
        return "private/profilo";
    }

    @GetMapping("/dashboard/cambio-password")
    public String cambioPasswordForm(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("usernameUtente", authentication.getName());
        }
        return "private/cambio_password";
    }

    @PostMapping("/dashboard/cambio-password")
    public String eseguiCambioPassword(@RequestParam String vecchiaPassword,
                                       @RequestParam String nuovaPassword,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes) {
        if (authentication != null) {
            String username = authentication.getName();
            boolean successo = userService.cambiaPassword(username, vecchiaPassword, nuovaPassword);

            if (successo) {
                redirectAttributes.addFlashAttribute("messaggioSuccesso", "Password aggiornata con successo!");
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("messaggioErrore", "La vecchia password inserita non è corretta.");
            }
        }
        return "redirect:/dashboard/cambio-password";
    }

    @GetMapping("/dashboard/upgrade")
    public String upgradeForm() {
        return "private/upgrade";
    }

    @PostMapping("/dashboard/upgrade")
    public String eseguiUpgrade(@RequestParam String pianoScelto,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        if (authentication != null) {
            String username = authentication.getName();
            boolean successo = userService.eseguiUpgrade(username, pianoScelto);
            if (successo) {
                redirectAttributes.addFlashAttribute("messaggioUpgrade", "Upgrade completato! Effettua di nuovo il login.");
                return "redirect:/login?logout";
            }
        }
        redirectAttributes.addFlashAttribute("messaggioErrore", "Errore nell'upgrade.");
        return "redirect:/dashboard/upgrade";
    }

    @GetMapping("/dashboard/allenamento")
    public String visualizzaAllenamenti(Model model) {

        List<Programma> listaAllenamenti = programClient.getPublicPrograms();

        model.addAttribute("listaAllenamenti", listaAllenamenti);

        return "private/allenamenti_lista";
    }

    @GetMapping("/dashboard/inserisci-programma")
    public String inserisciProgrammaForm() {
        return "private/inserisci_programma";
    }
}