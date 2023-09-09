package ms.apirequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AnimeApiRequestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimeApiRequestApplication.class, args);
    }
}
