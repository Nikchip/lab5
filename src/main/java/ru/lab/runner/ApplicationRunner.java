package ru.lab.runner;

import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.CorruptedFileException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.FileNotFoundException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.PermissionDeniedException;

import java.io.IOException;
import java.util.List;

/**
 * класс для запуска приложения
 */
public interface ApplicationRunner {
    void start(List<String> args);
}
