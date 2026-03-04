package com.gym.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Visitor {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private List<LocalDate> visitHistory;

    public Visitor() {
        this.visitHistory = new ArrayList<>();
    }

    public Visitor(int id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.visitHistory = new ArrayList<>();
    }


    public void addVisit(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Дата відвідування не може бути null");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Не можна записати відвідування на майбутню дату: " + date);
        }
        if (visitHistory.contains(date)) {
            throw new IllegalArgumentException("Відвідування на дату " + date + " вже записано");
        }
        visitHistory.add(date);
    }


    public List<LocalDate> getVisitHistory() {
        return Collections.unmodifiableList(visitHistory);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVisitHistory(List<LocalDate> visitHistory) {
        this.visitHistory = visitHistory != null ? new ArrayList<>(visitHistory) : new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return id == visitor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Visitor{id=" + id + ", name='" + firstName + " " + lastName + "', phone='" + phone + "'}";
    }
}
