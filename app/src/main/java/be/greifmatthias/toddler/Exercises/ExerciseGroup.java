package be.greifmatthias.toddler.Exercises;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Models.User;

public class ExerciseGroup {
    private String _word;
    private static List<Exercise> _exercises;

    public ExerciseGroup(String word){
        this._word = word;
    }

    public String getWord(){
        return this._word;
    }

    public List<Exercise> getExercises(User user){
        if(_exercises == null){
            _exercises = new ArrayList<>();

//            Try to load
            Exercise introExercise = new IntroExercise(null);
            Exercise listenExercise = new ListenExercise(user, this.getWord());
            Exercise sortingExercise = new SortingExercise(this.getWord());

            _exercises.add(introExercise);
            _exercises.add(listenExercise);
            _exercises.add(sortingExercise);
        }

        return _exercises;
    }
}
