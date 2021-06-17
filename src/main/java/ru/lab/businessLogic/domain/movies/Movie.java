package ru.lab.businessLogic.domain.movies;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public class Movie implements Comparable<Movie> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private String tagline; //Длина строки не должна быть больше 130, Поле может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле не может быть null

    public Movie(String name, Coordinates coordinates, int oscarsCount, String tagline, MpaaRating mpaaRating, Person director) {
        this.id = (long) (1 + Math.random() * 100000);
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.tagline = tagline;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.creationDate = LocalDate.now();
    }


    public Movie() {

    }

    public void update(Movie movie) {
        this.name = movie.getName();
        this.coordinates = movie.getCoordinates();
        this.oscarsCount = movie.getOscarsCount();
        this.tagline = movie.getTagline();
        this.mpaaRating = movie.getMpaaRating();
        this.director = movie.getDirector();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", oscarsCount=" + oscarsCount +
                ", tagline='" + tagline + '\'' +
                ", mpaaRating=" + mpaaRating +
                ", director=" + director +
                "}";
    }

    @Override
    public int compareTo(Movie o) {
        return oscarsCount - o.oscarsCount;
    }
}
