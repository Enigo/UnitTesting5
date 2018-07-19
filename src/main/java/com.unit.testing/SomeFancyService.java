package com.unit.testing;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

class SomeFancyService {

    int sum(int a, int b) {
        return a + b;
    }

    double divide(int a, int b) {
        return a / b;
    }

    Set<String> getAllDevelopers() {
        return new LinkedHashSet<>(Arrays.asList("Ruslan", "Gerd"));
    }

    EnumSet<Game> getAllGames() {
        return EnumSet.allOf(Game.class);
    }

    enum Game {
        ASSASSINS_CREED,
        GTA_V
    }
}
