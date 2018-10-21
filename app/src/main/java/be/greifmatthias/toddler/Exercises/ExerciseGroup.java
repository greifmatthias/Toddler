package be.greifmatthias.toddler.Exercises;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class ExerciseGroup {
    private boolean _isTest;
    private String _word;
    private List<Exercise> _exercises;

    private boolean _preteached;
    private boolean _known;

    public ExerciseGroup(String word){
        this._isTest = false;

        this._word = word;

        this._known = false;
        this._preteached = false;
    }

    public String getWord(){
        return this._word;
    }

    public static int getImage(String word){
        switch (word){
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

    public int getImage(){
        return getImage(this.getWord());
    }

    public static int getHdImage(String word){
        switch (word){
            case "De duikbril":
                return R.drawable.duikbril_hd;
            case "Het klimtouw":
                return R.drawable.klimtouw_hd;
            case "Het kroos":
                return R.drawable.kroos_hd;
            case "Het riet":
                return R.drawable.riet_hd;
            case "De val":
                return R.drawable.val_hd;
            case "Het kompas":
                return R.drawable.kompas_hd;
            case "Steil":
                return R.drawable.steil_hd;
            case "De zwaan":
                return R.drawable.zwaan_hd;
            case "Het kamp":
                return R.drawable.kamp_hd;
            case "De zaklamp":
                return R.drawable.zaklamp_hd;
            default:
                return 0;
        }
    }

    public int getHdImage(){
        return getHdImage(this.getWord());
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

        ExerciseGroup testExercise = new ExerciseGroup("De duikbril");
        testExercise.setTest();
        groups.add(testExercise);
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

    public boolean isTest() {
        return this._isTest;
    }

    private void setTest() {
        this._isTest = true;
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
        IntroExercise introExercise = new IntroExercise(null);
        ListenExercise listenExercise = new ListenExercise(this.getWord());
        SortingExercise sortingExercise = new SortingExercise(this.getWord());

        _exercises.add(introExercise);
        _exercises.add(listenExercise);
        _exercises.add(sortingExercise);
    }

    public void clearExercises() {
        this._exercises = new ArrayList<>();
    }
}
