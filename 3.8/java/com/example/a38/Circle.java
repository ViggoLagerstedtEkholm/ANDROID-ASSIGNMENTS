package com.example.a38;

/**
 * Class contains height and width for a circle, we also calculate the radius.
 * We use getters and setters for access to these values.
 */
public class Circle{
    private int radius;

    public Circle(){
        radius = 20;
    }

    public float getRadius(){
        return radius;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }
}
