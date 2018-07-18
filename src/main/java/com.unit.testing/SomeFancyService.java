package com.unit.testing;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

class SomeFancyService {

    int sum(int a, int b) {
        return a + b;
    }

    double divide(double a, double b) {
        return a / b;
    }

    Set<String> getAllDevelopers(){
        return new HashSet<>(Arrays.asList("Ruslan", "Gerd"));
    }

    EnumSet<Game> getAllGames(){
        return EnumSet.allOf(Game.class);
    }

    enum Game {
        ASSASSINS_CREED,
        GTA_V
    }
}
