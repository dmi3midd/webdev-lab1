package com.gym.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gym.model.Trainer;
import com.gym.model.Visitor;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class DataImporter {
    private final Gson gson;

    public DataImporter() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public List<Visitor> importVisitors(Reader reader) throws IOException {
        Type listType = new TypeToken<List<Visitor>>() {}.getType();
        List<Visitor> result = gson.fromJson(reader, listType);
        return result != null ? result : List.of();
    }

    public List<Trainer> importTrainers(Reader reader) throws IOException {
        Type listType = new TypeToken<List<Trainer>>() {}.getType();
        List<Trainer> result = gson.fromJson(reader, listType);
        return result != null ? result : List.of();
    }
}
