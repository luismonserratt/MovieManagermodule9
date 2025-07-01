package com.luismonserratt.moviemanager;

/**
 * Represents a movie with title, genre, year, rating, duration, and availability.
 */
public class Movie {
    private String title;
    private String genre;
    private int year;
    private double rating;
    private int duration;
    private boolean available;

    public Movie(String title, String genre, int year, double rating, int duration, boolean available) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.duration = duration;
        this.available = available;
    }

    // Getters y setters
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return title + " (" + year + ") - " + genre + ", " + rating + "★, " + duration + " min - " +
                (available ? "Available" : "Unavailable");
    }
}
