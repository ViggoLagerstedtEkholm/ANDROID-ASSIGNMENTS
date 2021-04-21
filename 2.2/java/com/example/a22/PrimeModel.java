package com.example.a22;

/**
 * Class holds all the wanted data that we want our prime model to hold.
 */
public class PrimeModel {
    private final long prime;
    private final String date;

    public PrimeModel( long prime, String date){
        this.prime = prime;
        this.date = date;
    }

    public long getPrime() {
        return prime;
    }

    public String getDate() {
        return date;
    }
}
