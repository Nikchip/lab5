package ru.lab.businessLogic.services.factories.movieFactories;

import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.factories.movieFactories.exceptions.CreateObjectFromInputParametrsException;

import java.util.List;

/**
 * фабрика для создания объектов класса Movie. На момент написания этого комментария
 * содержит одну реализацию для заполнения данных из командной строки
 */
public interface MovieFactory {
    Movie createMovie();
    Movie createMovie(List<String> data) throws CreateObjectFromInputParametrsException;
}
