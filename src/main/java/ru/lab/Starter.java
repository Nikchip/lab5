package ru.lab;

import ru.lab.runner.ApplicationRunner;
import ru.lab.runner.impls.CommandLineRunner;

import java.util.Arrays;

public class Starter {
    public static void main(String[] args) {
        ApplicationRunner applicationRunner = new CommandLineRunner();
        applicationRunner.start(Arrays.asList(args));
    }
}
