package com.gym.model;

import java.util.Objects;

public class Trainer {
    private int id;
    private String firstName;
    private String lastName;
    private String specialization;

    public Trainer() {
    }

    public Trainer(int id, String firstName, String lastName, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public boolean matchesSpecialization(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return false;
        }
        if (specialization == null) {
            return false;
        }
        return specialization.toLowerCase().contains(keyword.toLowerCase());
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return id == trainer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Trainer{id=" + id + ", name='" + firstName + " " + lastName + "', specialization='" + specialization + "'}";
    }
}
