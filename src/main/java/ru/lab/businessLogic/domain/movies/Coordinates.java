package ru.lab.businessLogic.domain.movies;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlRootElement;

public class Coordinates {
    private float x; //Значение поля должно быть больше -482
    private int y; //Максимальное значение поля: 997

    public Coordinates(float x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
