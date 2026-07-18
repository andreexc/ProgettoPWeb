package Web.Clients;

import Web.Controllers.DTO.Training.Personalized.EsercizioSceltaMultipla;
import Web.Controllers.DTO.Training.Personalized.NuovoProgrammaRequest;
import Web.Controllers.DTO.Training.Programma;
import Web.Controllers.DTO.Training.ProgramSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@FeignClient(name = "server-rest", url = "http://localhost:8081/api")
public interface RestClient {

    @GetMapping("/programs/public")
    List<ProgramSummary> getPublicProgramNames();

    @GetMapping("/programs/{id}/detail")
    Programma getProgramDetailById(@PathVariable Long id);

    @GetMapping("/exercises")
    List<EsercizioSceltaMultipla> getAllExercises();

    @PostMapping("/programs/new_program")
    Long createNewProgram(@RequestBody NuovoProgrammaRequest request );
}