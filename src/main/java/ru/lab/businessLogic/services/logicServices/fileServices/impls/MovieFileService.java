package ru.lab.businessLogic.services.logicServices.fileServices.impls;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.logicServices.fileServices.FileService;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.CorruptedFileException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.FileNotFoundException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.PermissionDeniedException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieFileService implements FileService<Movie> {

    private final Logger logger = Logger.getLogger(MovieFileService.class);
    private final File file;
    private final ObjectMapper objectMapper;
    private FileWriter fileWriter;
    private BufferedReader bufferedInputStream;

    public MovieFileService(String filePath, ObjectMapper objectMapper) throws PermissionDeniedException, FileNotFoundException, IOException {
        file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.canRead() || !file.canWrite()) {
            throw new PermissionDeniedException();
        }
        fileWriter = new FileWriter(file, true);
        bufferedInputStream = new BufferedReader(new FileReader(file));
        this.objectMapper = objectMapper;
        logger.info("file service init");
    }


    @Override
    public Boolean writeToFile(List<Movie> collection) throws IOException {
        StringBuilder elementsToFile = new StringBuilder();
        for (Movie movie : collection) {
            elementsToFile
                    .append(objectMapper.writeValueAsString(movie))
                    .append("\n");
        }
        fileWriter.write(elementsToFile.toString());
        fileWriter.flush();
        return true;

    }

    @Override
    public List<Movie> readFromFile() throws IOException, CorruptedFileException {
        List<Movie> movieList = new ArrayList<>();
        try {
            String currentFilm = bufferedInputStream.readLine();
            while (currentFilm != null) {
                        movieList.add(
                        objectMapper.readValue(currentFilm, Movie.class));
                currentFilm = bufferedInputStream.readLine();
            }
            if (movieList.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
            return movieList;
        } catch (JacksonException e) {
            throw new CorruptedFileException();
        }
    }
}
