package Web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProgettoPWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProgettoPWebApplication.class, args);
    }
}