package ua.edu.ukma.ui;

import ua.edu.ukma.domain.University;

import java.util.Optional;
import java.util.Scanner;

public class UniversityMenu {

    private final Scanner scanner;
    private final University university;

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
                    0. Back
                    """);

            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> show();
                case 2 -> edit();
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

    private void updatePartial(Optional<String> fullName,
                               Optional<String> shortName,
                               Optional<String> city,
                               Optional<String> address) {
        if (fullName.isPresent()) university.setFullName(fullName.get());
        if (shortName.isPresent()) university.setShortName(shortName.get());
        if (city.isPresent()) university.setCity(city.get());
        if (address.isPresent()) university.setAddress(address.get());
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String v = scanner.nextLine();
            if (!v.isBlank()) return v;
            System.out.println("Value cannot be empty\n");
        }
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}