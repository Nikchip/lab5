package ru.lab.businessLogic.services.logicServices.fileServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.CorruptedFileException;

import java.io.IOException;
import java.util.List;


/**
 * Сервис для работы с файлом
 * @param <Entity> - класс, с которым ведется работа внутри файла
 */
public interface FileService<Entity> {
    Boolean writeToFile(List<Entity> collection) throws IOException;
    List<Entity> readFromFile() throws IOException, CorruptedFileException;
}
