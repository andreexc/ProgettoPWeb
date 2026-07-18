package Web.Clients;

import Web.Controllers.DTO.Training.Programma;
import Web.Controllers.DTO.Training.ProgramSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "server-rest", url = "http://localhost:8081/api/programs")
public interface ProgramClient {

    @GetMapping("/public")
    List<ProgramSummary> getPublicProgramNames();

    @GetMapping("/{id}/detail")
    Programma getProgramDetailById(@PathVariable Long id);
}