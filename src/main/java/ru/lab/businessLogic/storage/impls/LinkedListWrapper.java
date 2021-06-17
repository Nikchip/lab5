package ru.lab.businessLogic.storage.impls;

import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.storage.CollectionWrapper;
import ru.lab.businessLogic.storage.exciptions.CollectionException;
import ru.lab.businessLogic.storage.exciptions.EmptyCollectionException;
import ru.lab.businessLogic.storage.exciptions.NoSuchElementInCollectionException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LinkedListWrapper implements CollectionWrapper<Movie> {

    private final List<Movie> movieList;
    private final LocalDate creationDate;

    public LinkedListWrapper() {
        creationDate = LocalDate.now();
        movieList = new LinkedList<>();
    }

    @Override
    public void clear() {
        movieList.clear();
    }


    @Override
    public int getSize() {
        return movieList.size();
    }

    @Override
    public LocalDate getInitTime() {
        return creationDate;
    }

    @Override
    public void remove(Movie movie) throws NoSuchElementInCollectionException {
        if (!movieList.remove(movie)) {
            throw new NoSuchElementInCollectionException();
        }
    }

    @Override
    public void remove(long id) throws NoSuchElementInCollectionException, EmptyCollectionException {
        if (movieList.isEmpty()) {
            throw new EmptyCollectionException();
        }
        Optional<Movie> movie = movieList.stream()
                .filter(x -> x.getId() == id)
                .findFirst();
        if (!movie.isPresent()) {
            throw new NoSuchElementInCollectionException();
        }
        movieList.remove(movie.get());
    }

    @Override
    public Movie get(long id) throws NoSuchElementInCollectionException {
        Movie movie = movieList.get((int) id);
        if (Objects.isNull(movie)) {
            throw new NoSuchElementInCollectionException();
        }
        return movie;
    }

    @Override
    public List<Movie> getList() throws EmptyCollectionException {
        if (movieList.isEmpty()) {
            throw new EmptyCollectionException();
        }
        return new ArrayList<>(movieList);
    }

    @Override
    public Movie add(Movie value) {
        movieList.add(value);
        return value;
    }

    @Override
    public void addAll(Collection<? extends Movie> collection) {
        movieList.addAll(collection);
    }

    @Override
    public Movie replace(long id, Movie value) throws CollectionException {
        try {
           Optional<Movie> movie = movieList.stream()
                    .filter(x -> x.getId() == id)
                    .findFirst();
            if (!movie.isPresent()) {
                throw new NoSuchElementInCollectionException();
            }
            Movie old = movie.get();
            movieList.remove(old);
            old.update(value);
            movieList.add(old);
            return value;
        } catch (IndexOutOfBoundsException e){
            throw new CollectionException("index of bound exception!");
        }

    }

    @Override
    public Movie min() {
        return movieList.stream()
                .min((Movie::compareTo))
                .orElse(null);
    }

    @Override
    public Movie max() {
        return movieList.stream()
                .max((Movie::compareTo))
                .orElse(null);
    }

    @Override
    public List<Movie> sort(boolean descending) throws EmptyCollectionException {
        if (movieList.isEmpty()) {
            throw new EmptyCollectionException();
        }
        if (descending) {
            return movieList.stream()
                    .sorted(Movie::compareTo)
                    .collect(Collectors.toList());
        } else {
            return movieList.stream()
                    .sorted((x, y) -> y.getOscarsCount() - x.getOscarsCount())
                    .collect(Collectors.toList());
        }

    }

    @Override
    public String getCollectionInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type: " + movieList.getClass().getName() + "\n");
        stringBuilder.append("creation date: " + creationDate + "\n");
        stringBuilder.append("size: " + getSize() + "\n");
        return stringBuilder.toString();
    }

    @Override
    public Movie insertAt(Long position, Movie movie) throws CollectionException {
        try {
            movieList.add(position.intValue(), movie);
            return movie;
        } catch (IndexOutOfBoundsException e) {
            throw new CollectionException("index out of bounds!");
        }

    }
}
