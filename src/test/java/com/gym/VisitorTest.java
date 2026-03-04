package com.gym;

import com.gym.model.Visitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VisitorTest {
    private Visitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new Visitor(1, "Олександр", "Голота", "0501234567");
    }

    @Test
    void addVisit_validDate_addsToHistory() {
        LocalDate date = LocalDate.now().minusDays(1);
        visitor.addVisit(date);

        assertEquals(1, visitor.getVisitHistory().size());
        assertEquals(date, visitor.getVisitHistory().get(0));
    }

    @Test
    void addVisit_duplicateDate_throwsException() {
        LocalDate date = LocalDate.now().minusDays(1);
        visitor.addVisit(date);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> visitor.addVisit(date));
        assertTrue(ex.getMessage().contains("вже записано"));
    }

    @Test
    void addVisit_futureDate_throwsException() {
        LocalDate future = LocalDate.now().plusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> visitor.addVisit(future));
        assertTrue(ex.getMessage().contains("майбутню"));
    }

    @Test
    void equalsAndHashCode_sameId_areEqual() {
        Visitor other = new Visitor(1, "Інше", "Ім'я", "0509999999");
        assertEquals(visitor, other);
        assertEquals(visitor.hashCode(), other.hashCode());
    }

    @Test
    void equalsAndHashCode_differentId_areNotEqual() {
        Visitor other = new Visitor(2, "Олександр", "Голота", "0501234567");
        assertNotEquals(visitor, other);
    }
}
