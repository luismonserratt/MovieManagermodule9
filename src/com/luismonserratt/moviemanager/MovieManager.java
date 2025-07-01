package com.luismonserratt.moviemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages a collection of com.luismonserratt.moviemanager.Movie objects and provides CRUD operations and custom actions.
 */
public class MovieManager {
    private List<Movie> movies = new ArrayList<>();

    public boolean createMovie(String title, String genre, int year, double rating, int duration, boolean available) {
        if (title == null || title.isBlank() || genre == null || genre.isBlank()
                || year < 1800 || rating < 0 || rating > 10 || duration <= 0) {
            return false;
        }
        movies.add(new Movie(title, genre, year, rating, duration, available));
        return true;
    }

    public boolean updateMovie(String title, String newGenre, int newYear, double newRating, int newDuration, boolean newAvailable) {
        Movie m = findMovie(title);
        if (m == null || newGenre == null || newGenre.isBlank()
                || newYear < 1800 || newRating < 0 || newRating > 10 || newDuration <= 0) {
            return false;
        }
        m.setGenre(newGenre);
        m.setYear(newYear);
        m.setRating(newRating);
        m.setDuration(newDuration);
        m.setAvailable(newAvailable);
        return true;
    }

    public boolean deleteMovie(String title) {
        return movies.removeIf(m -> m.getTitle().equalsIgnoreCase(title));
    }

    public Movie findMovie(String title) {
        for (Movie m : movies) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                return m;
            }
        }
        return null;
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public double getAverageDuration() {
        if (movies.isEmpty()) return 0;
        double total = 0;
        for (Movie m : movies) {
            total += m.getDuration();
        }
        return total / movies.size();
    }

    public int loadFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner fileScanner = new Scanner(file);
        int count = 0;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] data = line.split(",");
            if (data.length != 6) continue;

            try {
                String title = data[0].trim();
                String genre = data[1].trim();
                int year = Integer.parseInt(data[2].trim());
                double rating = Double.parseDouble(data[3].trim());
                int duration = Integer.parseInt(data[4].trim());
                boolean available = Boolean.parseBoolean(data[5].trim());

                if (createMovie(title, genre, year, rating, duration, available)) {
                    count++;
                }
            } catch (Exception e) {
                // Ignore invalid lines
            }
        }
        return count;
    }
}
