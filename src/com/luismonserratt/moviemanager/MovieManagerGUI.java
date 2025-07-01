package com.luismonserratt.moviemanager;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * MovieManagerGUI class provides a graphical interface for interacting with the MovieManager.
 * It allows users to perform CRUD operations, load movies from a file, display movies,
 * and calculate the average duration.
 */
public class MovieManagerGUI extends JFrame {
    private MovieManager manager;      // Handles movie data and operations
    private JTextArea displayArea;     // Displays movie information

    /**
     * Constructor sets up the GUI layout, buttons, and their event listeners.
     */
    public MovieManagerGUI() {
        manager = new MovieManager();
        setTitle("Movie Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the text area where movie information will be shown
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Create a panel with buttons for user interaction
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));

        JButton loadButton = new JButton("Load File");
        JButton displayButton = new JButton("Display Movies");
        JButton addButton = new JButton("Add Movie");
        JButton updateButton = new JButton("Update Movie");
        JButton deleteButton = new JButton("Delete Movie");
        JButton avgButton = new JButton("Avg Duration");
        JButton exitButton = new JButton("Exit");

        // Add buttons to the panel
        buttonPanel.add(loadButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(avgButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Attach event handlers to the buttons
        loadButton.addActionListener(e -> loadFile());
        displayButton.addActionListener(e -> displayMovies());
        addButton.addActionListener(e -> addMovie());
        updateButton.addActionListener(e -> updateMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        avgButton.addActionListener(e -> showAverageDuration());
        exitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * Opens a file chooser for the user to select a file and loads movie data from it.
     */
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("DEBUG Path selected: " + path);  // For debugging in console
            try {
                int count = manager.loadFromFile(path);
                displayArea.setText(count + " movies loaded.\n");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "File not found.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error reading file.");
            }
        }
    }

    /**
     * Displays all movies currently stored in the system in the text area.
     */
    private void displayMovies() {
        StringBuilder sb = new StringBuilder();
        for (Movie m : manager.getAllMovies()) {
            sb.append(m).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    /**
     * Prompts the user for movie details and adds the movie to the system.
     */
    private void addMovie() {
        try {
            String title = JOptionPane.showInputDialog(this, "Enter title:");
            if (title == null || title.isBlank()) {
                JOptionPane.showMessageDialog(this, "Title is required.");
                return;
            }

            String genre = JOptionPane.showInputDialog(this, "Enter genre:");
            if (genre == null || genre.isBlank()) {
                JOptionPane.showMessageDialog(this, "Genre is required.");
                return;
            }

            int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter year:"));
            double rating = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter rating (0-10):"));
            int duration = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter duration (minutes):"));
            boolean available = Boolean.parseBoolean(JOptionPane.showInputDialog(this, "Available? (true/false):"));

            boolean success = manager.createMovie(title, genre, year, rating, duration, available);
            if (success) {
                JOptionPane.showMessageDialog(this, "Movie added successfully.");
                displayMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid data. Movie not added.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Movie not added.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding movie.");
        }
    }

    /**
     * Prompts the user for the title of a movie and new details, then updates the movie.
     */
    private void updateMovie() {
        try {
            String title = JOptionPane.showInputDialog(this, "Enter the title of the movie to update:");
            if (title == null || title.isBlank()) {
                JOptionPane.showMessageDialog(this, "Title is required.");
                return;
            }

            String newGenre = JOptionPane.showInputDialog(this, "Enter new genre:");
            if (newGenre == null || newGenre.isBlank()) {
                JOptionPane.showMessageDialog(this, "Genre is required.");
                return;
            }

            int newYear = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new year:"));
            double newRating = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new rating (0-10):"));
            int newDuration = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new duration (minutes):"));
            boolean newAvailable = Boolean.parseBoolean(JOptionPane.showInputDialog(this, "Available? (true/false):"));

            boolean success = manager.updateMovie(title, newGenre, newYear, newRating, newDuration, newAvailable);
            if (success) {
                JOptionPane.showMessageDialog(this, "Movie updated successfully.");
                displayMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid data or movie not found. Update failed.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Update failed.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating movie.");
        }
    }

    /**
     * Prompts the user for the title of a movie and deletes it if found.
     */
    private void deleteMovie() {
        String title = JOptionPane.showInputDialog(this, "Enter the title of the movie to delete:");
        if (title != null && !title.isBlank()) {
            boolean success = manager.deleteMovie(title);
            if (success) {
                JOptionPane.showMessageDialog(this, "Movie deleted successfully.");
                displayMovies();
            } else {
                JOptionPane.showMessageDialog(this, "Movie not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Title is required.");
        }
    }

    /**
     * Calculates and shows the average duration of all movies.
     */
    private void showAverageDuration() {
        double avg = manager.getAverageDuration();
        JOptionPane.showMessageDialog(this, String.format("Average duration: %.2f minutes", avg));
    }

    /**
     * Main method that launches the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieManagerGUI gui = new MovieManagerGUI();
            gui.setVisible(true);
        });
    }
}
