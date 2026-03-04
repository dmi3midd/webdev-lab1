package com.gym;

import com.gym.io.DataExporter;
import com.gym.io.DataImporter;
import com.gym.model.Gym;
import com.gym.model.Trainer;
import com.gym.model.Visitor;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gym gym = new Gym("FitLife");
        Scanner scanner = new Scanner(System.in);
        DataExporter exporter = new DataExporter();
        DataImporter importer = new DataImporter();
        int nextVisitorId = 1;
        int nextTrainerId = 1;

        System.out.println("Ласкаво просимо до спортзалу \"" + gym.getName() + "\"!");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Додати відвідувача");
            System.out.println("2. Додати тренера");
            System.out.println("3. Список відвідувачів");
            System.out.println("4. Список тренерів");
            System.out.println("5. Відвідати зал");
            System.out.println("6. Інформація про зал");
            System.out.println("7. Експорт даних (JSON)");
            System.out.println("8. Імпорт даних (JSON)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Ім'я: ");
                        String fn = scanner.nextLine().trim();
                        System.out.print("Прізвище: ");
                        String ln = scanner.nextLine().trim();
                        System.out.print("Телефон: ");
                        String phone = scanner.nextLine().trim();
                        Visitor v = new Visitor(nextVisitorId++, fn, ln, phone);
                        gym.addVisitor(v);
                        System.out.println("Додано: " + v);
                    }
                    case "2" -> {
                        System.out.print("Ім'я: ");
                        String fn = scanner.nextLine().trim();
                        System.out.print("Прізвище: ");
                        String ln = scanner.nextLine().trim();
                        System.out.print("Спеціалізація: ");
                        String spec = scanner.nextLine().trim();
                        Trainer t = new Trainer(nextTrainerId++, fn, ln, spec);
                        gym.addTrainer(t);
                        System.out.println("Додано: " + t);
                    }
                    case "3" -> gym.getVisitors().forEach(System.out::println);
                    case "4" -> gym.getTrainers().forEach(System.out::println);
                    case "5" -> {
                        System.out.print("ID відвідувача: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Дата (yyyy-MM-dd, Enter = сьогодні): ");
                        String ds = scanner.nextLine().trim();
                        LocalDate date = ds.isEmpty() ? LocalDate.now() : LocalDate.parse(ds);
                        gym.visitGym(id, date);
                        System.out.println("Відвідування зафіксовано.");
                    }
                    case "6" -> System.out.println(gym.getInfo());
                    case "7" -> {
                        System.out.print("Ім'я файлу (без розширення): ");
                        String base = scanner.nextLine().trim();
                        try (Writer w = new FileWriter(base + "_visitors.json")) {
                            exporter.exportVisitors(gym.getVisitors(),
                                    Comparator.comparing(Visitor::getLastName), w);
                        }
                        try (Writer w = new FileWriter(base + "_trainers.json")) {
                            exporter.exportTrainers(gym.getTrainers(),
                                    Comparator.comparing(Trainer::getLastName), w);
                        }
                        System.out.println("Дані експортовано.");
                    }
                    case "8" -> {
                        System.out.print("Ім'я файлу (без розширення): ");
                        String base = scanner.nextLine().trim();
                        File vf = new File(base + "_visitors.json");
                        if (vf.exists()) {
                            try (Reader r = new FileReader(vf)) {
                                List<Visitor> list = importer.importVisitors(r);
                                list.forEach(v -> {
                                    try { gym.addVisitor(v); } catch (Exception ignored) {}
                                });
                                System.out.println("Імпортовано відвідувачів: " + list.size());
                            }
                        }
                        File tf = new File(base + "_trainers.json");
                        if (tf.exists()) {
                            try (Reader r = new FileReader(tf)) {
                                List<Trainer> list = importer.importTrainers(r);
                                list.forEach(gym::addTrainer);
                                System.out.println("Імпортовано тренерів: " + list.size());
                            }
                        }
                    }
                    case "0" -> running = false;
                    default -> System.out.println("Невірний вибір.");
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        System.out.println("До побачення!");
    }
}
