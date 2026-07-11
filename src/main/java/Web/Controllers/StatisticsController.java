package Web.Controllers;

import Web.Services.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/programmi")
    public String getStatisticheProgrammi(Model model) {
        Map<String, Integer> statsProgrammi = statisticsService.getAllenamentiTotaliPerProgramma();

        // Passing keys and values for Chart.js
        model.addAttribute("programmiLabels", statsProgrammi.keySet());
        model.addAttribute("programmiValori", statsProgrammi.values());

        return "admin/stats_programmi";
    }

    @GetMapping("/ruoli")
    public String getStatisticheRuoli(Model model) {
        Map<String, Double> medieRuoli = statisticsService.getMediaAllenamentiPerRuolo();

        model.addAttribute("mediaBasic", medieRuoli.get("BASIC"));
        model.addAttribute("mediaPro", medieRuoli.get("PRO"));

        return "admin/stats_ruoli";
    }
}