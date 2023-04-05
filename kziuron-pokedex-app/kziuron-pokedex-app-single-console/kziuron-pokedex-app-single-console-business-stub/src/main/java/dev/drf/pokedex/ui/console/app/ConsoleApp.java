package dev.drf.pokedex.ui.console.app;

import dev.drf.pokedex.ui.console.app.service.ConsoleAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {
    private static final System.Logger LOGGER = System.getLogger(ConsoleApp.class.getName());

    @Autowired
    private ConsoleAppService consoleAppService;

    public static void main(String[] args) {
        LOGGER.log(System.Logger.Level.INFO, "STARTING THE APPLICATION");
        SpringApplication.run(ConsoleApp.class, args);
        LOGGER.log(System.Logger.Level.INFO, "APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        consoleAppService.runConsole();
    }
}
