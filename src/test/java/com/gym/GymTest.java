package com.gym;

import com.gym.model.Gym;
import com.gym.model.Trainer;
import com.gym.model.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GymTest {
    private Gym gym;
    private Visitor visitor;

    @BeforeEach
    void setUp() {
        gym = new Gym("TestGym");
        visitor = new Visitor(1, "Іван", "Петренко", "+380501111111");
        gym.addVisitor(visitor);
        gym.addTrainer(new Trainer(1, "Олена", "Коваль", "Фітнес"));
    }

    @Test
    void visitGym_validVisitor_recordsVisit() {
        LocalDate date = LocalDate.of(2026, 1, 15);
        gym.visitGym(1, date);
        assertEquals(1, visitor.getVisitHistory().size());
        assertEquals(date, visitor.getVisitHistory().get(0));
    }

    @Test
    void visitGym_unknownVisitor_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> gym.visitGym(999, LocalDate.of(2026, 1, 1)));
    }

    @Test
    void addVisitor_duplicatePhone_throwsException() {
        Visitor dup = new Visitor(2, "Артем", "Тішин", "0501111111");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> gym.addVisitor(dup));
        assertTrue(ex.getMessage().contains("вже існує"));
    }

    @Test
    void getInfo_returnsNonEmptyString() {
        String info = gym.getInfo();
        assertNotNull(info);
        assertTrue(info.contains("TestGym"));
        assertTrue(info.contains("Відвідувачі"));
    }
}
