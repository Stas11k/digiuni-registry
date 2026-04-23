package ua.edu.ukma.ui;

import ua.edu.ukma.domain.University;
import ua.edu.ukma.io.DataPaths;
import ua.edu.ukma.io.UniversityFileService;

import java.util.Optional;
import java.util.Scanner;

public class UniversityMenu {
    private final UniversityFileService fileService = new UniversityFileService();
    private final Scanner scanner;
    private University university;

    public UniversityMenu(Scanner scanner, University university) {
        this.scanner = scanner;
        this.university = university;
    }

    public void start() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("""
                    --- University settings ---
                    1. Show info
                    2. Edit
                    3. Save to file
                    4. Load from file
                    0. Back
                    """);

            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> show();
                case 2 -> edit();
                case 3 -> saveToFile();
                case 4 -> loadFromFile();
                case 0 -> inMenu = false;
                default -> System.out.println("Unknown option\n");
            }
        }
    }

    private void show() {
        System.out.println("Full name : " + university.getFullName());
        System.out.println("Short name: " + university.getShortName());
        System.out.println("City      : " + university.getCity());
        System.out.println("Address   : " + university.getAddress());
        System.out.println();
    }

    private void edit() {
        boolean editing = true;

        while (editing) {
            System.out.println("""
                    What do you want to edit
                    1. Full name
                    2. Short name
                    3. City
                    4. Address
                    9. Edit all fields
                    0. Back
                    """);

            System.out.print("Choose option: ");
            int c = readInt();

            Optional<String> fullName = Optional.empty();
            Optional<String> shortName = Optional.empty();
            Optional<String> city = Optional.empty();
            Optional<String> address = Optional.empty();

            switch (c) {
                case 1 -> fullName = Optional.of(readRequiredLine("New full name"));
                case 2 -> shortName = Optional.of(readRequiredLine("New short name"));
                case 3 -> city = Optional.of(readRequiredLine("New city"));
                case 4 -> address = Optional.of(readRequiredLine("New address"));
                case 9 -> {
                    fullName = Optional.of(readRequiredLine("New full name"));
                    shortName = Optional.of(readRequiredLine("New short name"));
                    city = Optional.of(readRequiredLine("New city"));
                    address = Optional.of(readRequiredLine("New address"));
                }
                case 0 -> {
                    editing = false;
                    continue;
                }
                default -> {
                    System.out.println("Unknown option\n");
                    continue;
                }
            }

            updatePartial(fullName, shortName, city, address);
            System.out.println("Updated\n");
        }
    }

    private void saveToFile() {
        fileService.saveToFile(university, DataPaths.UNIVERSITY);
        System.out.println("Saved");
    }

    private void loadFromFile() {
        University loaded = fileService.loadFromFile(DataPaths.UNIVERSITY);

        if (loaded != null) {
            university = loaded;
            System.out.println("Loaded");
        }
    }

    private void updatePartial(Optional<String> fullName, Optional<String> shortName, Optional<String> city, Optional<String> address) {
        if (fullName.isPresent()) university.setFullName(fullName.get());
        if (shortName.isPresent()) university.setShortName(shortName.get());
        if (city.isPresent()) university.setCity(city.get());
        if (address.isPresent()) university.setAddress(address.get());
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("Value cannot be empty");
        }
    }

    private int readInt() {
        while (true) {
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a number: ");
            }
        }
    }
}