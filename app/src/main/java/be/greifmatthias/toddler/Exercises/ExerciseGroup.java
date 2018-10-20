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

    private int _image;

    public ExerciseGroup(String word, int imageResource){
        this._word = word;
        this._image = imageResource;

        this._known = false;
        this._preteached = false;
    }

    public String getWord(){
        return this._word;
    }

    public int getImage(){
        return this._image;
    }

    public boolean isPreteached() {
        return this._preteached;
    }

    public boolean isKnown() {
        return this._known;
    }

    //    Get all possible exercisegroups
    public static List<ExerciseGroup> get(){
        List<ExerciseGroup> groups = new ArrayList<>();

        groups.add(new ExerciseGroup("De duikbril", R.drawable.duikbril));
        groups.add(new ExerciseGroup("Het klimtouw", R.drawable.klimtouw));
        groups.add(new ExerciseGroup("Het kroos", R.drawable.kroos));
        groups.add(new ExerciseGroup("Het riet", R.drawable.riet));
        groups.add(new ExerciseGroup("De val", R.drawable.val));
        groups.add(new ExerciseGroup("Het kompas", R.drawable.kompas));
        groups.add(new ExerciseGroup("Steil", R.drawable.steil));
        groups.add(new ExerciseGroup("De zwaan", R.drawable.zwaan));
        groups.add(new ExerciseGroup("Het kamp", R.drawable.kamp));
        groups.add(new ExerciseGroup("De zaklamp", R.drawable.zaklamp));

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
