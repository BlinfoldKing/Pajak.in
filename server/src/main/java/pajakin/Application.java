package pajakin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pajakin.controller.PersonController;
import pajakin.controller.VehicleController;
import pajakin.controller.PropertyController;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
