package be.greifmatthias.toddler.Exercises;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Models.User;

public class ExerciseGroup {
    private String _word;
    private List<Exercise> _exercises;

    public ExerciseGroup(String word){
        this._word = word;
    }

    public String getWord(){
        return this._word;
    }

//    Get all possible exercisegroups
    public static List<ExerciseGroup> get(){
        List<ExerciseGroup> groups = new ArrayList<>();

        groups.add(new ExerciseGroup("De duikbril"));
        groups.add(new ExerciseGroup("Het klimtouw"));
        groups.add(new ExerciseGroup("Het kroos"));
        groups.add(new ExerciseGroup("Het riet"));
        groups.add(new ExerciseGroup("De val"));
        groups.add(new ExerciseGroup("Het kompas"));
        groups.add(new ExerciseGroup("Steil"));
        groups.add(new ExerciseGroup("De zwaan"));
        groups.add(new ExerciseGroup("Het kamp"));
        groups.add(new ExerciseGroup("De zaklamp"));

        for (ExerciseGroup group : groups){
            group.loadDefault();
        }

        return groups;
    }

//    Get exercises for this group
    public List<Exercise> getExercises(){
        if(_exercises == null){
            _exercises = new ArrayList<>();
        }

        return _exercises;
    }

    public void addExercise(Exercise e){
        getExercises();

        _exercises.add(e);
    }

    public void loadDefault(){

        getExercises();

//        Set default
        Exercise introExercise = new IntroExercise(null);
        Exercise listenExercise = new ListenExercise(this.getWord());
        Exercise sortingExercise = new SortingExercise(this.getWord());

        _exercises.add(introExercise);
        _exercises.add(listenExercise);
        _exercises.add(sortingExercise);
    }

    public void clearExercises() {
        this._exercises = new ArrayList<>();
    }
}
