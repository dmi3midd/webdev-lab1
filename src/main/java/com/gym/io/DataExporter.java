package com.gym.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gym.model.Trainer;
import com.gym.model.Visitor;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class DataExporter {
    private final Gson gson;

    public DataExporter() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    public void exportVisitors(List<Visitor> visitors, Comparator<Visitor> comparator, Writer writer) throws IOException {
        List<Visitor> sorted = visitors.stream().sorted(comparator).toList();
        writer.write(gson.toJson(sorted));
        writer.flush();
    }

    public void exportTrainers(List<Trainer> trainers, Comparator<Trainer> comparator, Writer writer) throws IOException {
        List<Trainer> sorted = trainers.stream().sorted(comparator).toList();
        writer.write(gson.toJson(sorted));
        writer.flush();
    }
}
