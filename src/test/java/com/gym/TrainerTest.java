package com.gym;

import com.gym.model.Trainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {
    @Test
    void matchesSpecialization_matchFound() {
        Trainer trainer = new Trainer(1, "Дмитро", "Ставицький", "Силові тренування");
        assertTrue(trainer.matchesSpecialization("силов"));
    }

    @Test
    void matchesSpecialization_noMatch() {
        Trainer trainer = new Trainer(1, "Дмитро", "Ставицький", "Силові тренування");
        assertFalse(trainer.matchesSpecialization("йога"));
    }

    @Test
    void matchesSpecialization_nullKeyword_returnsFalse() {
        Trainer trainer = new Trainer(1, "Дмитро", "Ставицький", "Силові тренування");
        assertFalse(trainer.matchesSpecialization(null));
    }

    @Test
    void matchesSpecialization_blankKeyword_returnsFalse() {
        Trainer trainer = new Trainer(1, "Дмитро", "Ставицький", "Силові тренування");
        assertFalse(trainer.matchesSpecialization("   "));
    }
}
