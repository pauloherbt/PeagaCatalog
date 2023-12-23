package com.example.peagacatalog.configs;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

@Configurable
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Teste");
    }
}
