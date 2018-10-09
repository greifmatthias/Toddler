package be.greifmatthias.toddler.Models.Exercises;

import be.greifmatthias.toddler.R;

public class ListerExercise extends Exercise {

    @Override
    public int getView() {
        return R.layout.fragment_listen_exercise;
    }

    @Override
    public boolean isCorrect() {
        return true;
    }

    @Override
    public boolean hasCorrection() {
        return false;
    }
}
