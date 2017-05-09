package it.eng.zerohqt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/rest")
public class ZeroHqTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroHqTestApplication.class, args);
    }
}
