package com.gym.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Gym {
    private String name;
    private final List<Visitor> visitors = new ArrayList<>();
    private final List<Trainer> trainers = new ArrayList<>();

    public Gym() {
        this.name = "";
    }

    public Gym(String name) {
        this.name = name;
    }


    public void addVisitor(Visitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Відвідувач не може бути null");
        }
        boolean phoneDuplicate = visitors.stream()
                .anyMatch(v -> v.getPhone() != null && v.getPhone().equals(visitor.getPhone()));
        if (phoneDuplicate) {
            throw new IllegalArgumentException("Відвідувач з телефоном " + visitor.getPhone() + " вже існує");
        }
        visitors.add(visitor);
    }

    public Visitor findVisitorById(int id) {
        return visitors.stream().filter(v -> v.getId() == id).findFirst().orElse(null);
    }

    public boolean removeVisitor(int id) {
        return visitors.removeIf(v -> v.getId() == id);
    }

    public void addTrainer(Trainer trainer) {
        if (trainer == null) {
            throw new IllegalArgumentException("Тренер не може бути null");
        }
        trainers.add(trainer);
    }

    public boolean removeTrainer(int id) {
        return trainers.removeIf(t -> t.getId() == id);
    }

    public void visitGym(int visitorId, LocalDate date) {
        Visitor visitor = findVisitorById(visitorId);
        if (visitor == null) {
            throw new IllegalArgumentException("Відвідувача з id=" + visitorId + " не знайдено");
        }
        visitor.addVisit(date);
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Спортзал: ").append(name).append(" ===\n");
        sb.append("Відвідувачі (").append(visitors.size()).append("):\n");
        for (Visitor v : visitors) {
            sb.append("  ").append(v).append("\n");
        }
        sb.append("Тренери (").append(trainers.size()).append("):\n");
        for (Trainer t : trainers) {
            sb.append("  ").append(t).append("\n");
        }
        return sb.toString();
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Visitor> getVisitors() { return Collections.unmodifiableList(visitors); }
    public List<Trainer> getTrainers() { return Collections.unmodifiableList(trainers); }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gym gym = (Gym) o;
        return Objects.equals(name, gym.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
