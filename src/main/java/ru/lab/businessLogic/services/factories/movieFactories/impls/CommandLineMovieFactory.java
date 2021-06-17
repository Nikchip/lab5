package ru.lab.businessLogic.services.factories.movieFactories.impls;

import ru.lab.businessLogic.domain.movies.*;
import ru.lab.businessLogic.services.factories.movieFactories.MovieFactory;
import ru.lab.businessLogic.services.factories.movieFactories.exceptions.CreateObjectFromInputParametrsException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CommandLineMovieFactory implements MovieFactory {

    private final float X_MAX_VALUE = 997;
    private final int Y_MIN_VALUE = -487;

    @Override
    public Movie createMovie() {
        System.out.println("___START CREATE NEW MOVIE___");
        Scanner scanner = new Scanner(System.in);
        Movie movie = new Movie();
        System.out.println("ENTER FILM NAME");
        while (true) {
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                continue;
            }
            movie.setName(name);
            break;
        }
        System.out.println("ENTER OSCAR COUNT (больше 0)");
        while (true) {
            try {
                int oscarCount = Integer.parseInt(scanner.nextLine());
                if (oscarCount < 0) {
                    throw new NumberFormatException();
                }
                movie.setOscarsCount(oscarCount);
                break;
            } catch (NumberFormatException exception) {
                System.out.println("COUNT MUST BE MORE THAN 0");
            }

        }
        System.out.println("ENTER TAGS");
        movie.setTagline(scanner.nextLine());
        System.out.println("CHOOSE Mpaa Rating:" +
                "\nPG = 1" +
                "\nPG_13 = 2" +
                "\nNC_17 = 3");
        while (true) {
            try {
                int choise = Integer.parseInt(scanner.nextLine());
                switch (choise) {
                    case 1:
                        movie.setMpaaRating(MpaaRating.PG);
                        break;
                    case 2:
                        movie.setMpaaRating(MpaaRating.PG_13);
                        break;
                    case 3:
                        movie.setMpaaRating(MpaaRating.NC_17);
                        break;
                    default:
                        throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("CHOOSE ONLY FROM 1-3");
            }
        }

        movie.setCoordinates(createCoordinates());
        movie.setDirector(createDirector());
        movie.setId((long) (1 + Math.random() * 100000));
        movie.setCreationDate(LocalDate.now());
        System.out.println("MOVIE SUCCESSFULLY CREATED");
        return movie;
    }


    //shit...
    @Override
    public Movie createMovie(List<String> data) throws CreateObjectFromInputParametrsException {
        int num = 0;
        try {
            Movie movie = new Movie();
            String filmName = data.get(num++);
            Integer oscarCount = Integer.parseInt(data.get(num++));
            String tags = data.get(num++);
            MpaaRating rating = MpaaRating.valueOf(data.get(num++));
            Float x = Float.parseFloat(data.get(num++));
            Integer y = Integer.parseInt(data.get(num++));
            String derectorName = data.get(num++);
            LocalDate date = LocalDate.parse(data.get(num++));
            Color eyeColor = Color.valueOf(data.get(num++));
            Color hairColor = Color.valueOf(data.get(num++));
            Country country = Country.valueOf(data.get(num++));
            Coordinates coordinates = new Coordinates(x,y);
            Person person = new Person(derectorName, date, eyeColor, hairColor, country);
            return new Movie(filmName, coordinates, oscarCount, tags, rating, person);
        } catch (Exception e){
            throw new CreateObjectFromInputParametrsException("cannot create object. Got wrong parameters!, check row " + num);
        }

    }

    private Coordinates createCoordinates() {
        Scanner scanner = new Scanner(System.in);
        float x;
        int y;
        System.out.println("ENTER X COORDINATE");
        while (true) {
            try {
                x = Float.parseFloat(scanner.nextLine());
                if (x <= X_MAX_VALUE) {
                    break;
                }
                System.out.println("X COORDINATE MUST BE LESS THAN  " + X_MAX_VALUE);
            } catch (NumberFormatException e) {
                System.out.println("X COORDINATE MUST BE NUMBER");
            }
        }
        System.out.println("ENTER Y COORDINATE");
        while (true) {
            try {
                y = Integer.parseInt(scanner.nextLine());
                if (y >= Y_MIN_VALUE) {
                    break;
                }
                System.out.println("Y COORDINATE MUST BE MORE THAN " + Y_MIN_VALUE);
            } catch (NumberFormatException e) {
                System.out.println("Y COORDINATE MUST BE NUMBER");
            }
        }
        return new Coordinates(x, y);
    }

    private Person createDirector() {
        System.out.println("___ENTER DIRECTOR INFO___");
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER DIRECTOR NAME");
        String name = scanner.nextLine();
        LocalDate date;
        System.out.println("ENTER DATE IN FORMAT YYYY-MM-YY");
        while (true) {
            try {
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("DATE FORMAT MUST BE YYYY-MM-YY");
            }
        }
        System.out.println("CHOOSE EYE COLOR: " +
                "\nGREEN - 1" +
                "\nBLACK - 2" +
                "\nBLUE - 3" +
                "\nYELLOW - 4" +
                "\nWHITE - 5");
        Color eyeColor;
        while (true) {
            try {
                int choise = Integer.parseInt(scanner.nextLine());
                switch (choise) {
                    case 1:
                        eyeColor = Color.GREEN;
                        break;
                    case 2:
                        eyeColor = Color.BLACK;
                        break;
                    case 3:
                        eyeColor = Color.BLUE;
                        break;
                    case 4:
                        eyeColor = Color.YELLOW;
                        break;
                    case 5:
                        eyeColor = Color.WHITE;
                        break;
                    default:
                        throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("CHOOSE ONLY FROM 1-5");
            }
        }
        System.out.println("CHOOSE HAIR COLOR" +
                "\nGREEN - 1" +
                "\nBLACK - 2" +
                "\nBLUE - 3" +
                "\nYELLOW - 4" +
                "\nWHITE - 5");
        Color hairColor;
        while (true) {
            try {
                int choise = Integer.parseInt(scanner.nextLine());
                switch (choise) {
                    case 1:
                        hairColor = Color.GREEN;
                        break;
                    case 2:
                        hairColor = Color.BLACK;
                        break;
                    case 3:
                        hairColor = Color.BLUE;
                        break;
                    case 4:
                        hairColor = Color.YELLOW;
                        break;
                    case 5:
                        hairColor = Color.WHITE;
                        break;
                    default:
                        throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("CHOOSE ONLY FROM 1-5");
            }
        }
        System.out.println("CHOOSE NATIONALITY" +
                "\nSPAIN - 1" +
                "\nVATIKAN -2" +
                "\nITALY - 3");
        Country country;
        while (true) {
            try {
                int choise = Integer.parseInt(scanner.nextLine());
                switch (choise) {
                    case 1:
                        country = Country.SPAIN;
                        break;
                    case 2:
                        country = Country.VATICAN;
                        break;
                    case 3:
                        country = Country.ITALY;
                        break;
                    default:
                        throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("CHOOSE ONLY FROM 1-5");
            }
        }
        return new Person(name, date, eyeColor, hairColor, country);
    }


}
