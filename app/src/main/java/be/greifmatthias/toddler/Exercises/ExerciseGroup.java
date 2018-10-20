package be.greifmatthias.toddler.Exercises;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class ExerciseGroup {
    private String _word;
    private List<Exercise> _exercises;

    private boolean _preteached;
    private boolean _known;

    public ExerciseGroup(String word){
        this._word = word;

        this._known = false;
        this._preteached = false;
    }

    public String getWord(){
        return this._word;
    }

    public int getImage(){
        switch (this.getWord()){
            case "De duikbril":
                return R.drawable.duikbril;
            case "Het klimtouw":
                return R.drawable.klimtouw;
            case "Het kroos":
                return R.drawable.kroos;
            case "Het riet":
                return R.drawable.riet;
            case "De val":
                return R.drawable.val;
            case "Het kompas":
                return R.drawable.kompas;
            case "Steil":
                return R.drawable.steil;
            case "De zwaan":
                return R.drawable.zwaan;
            case "Het kamp":
                return R.drawable.kamp;
            case "De zaklamp":
                return R.drawable.zaklamp;
                default:
                    return 0;
        }
    }

    public boolean isPreteached() {
        return this._preteached;
    }

    public void setPreteached(){
        this._preteached = true;
    }

    public void setCorrect(){
        this._known = true;
    }

    public boolean isKnown() {
        return this._known;
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
