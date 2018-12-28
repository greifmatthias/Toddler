package be.greifmatthias.toddler.Exercises;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Models.Group;
import be.greifmatthias.toddler.R;

public class ExerciseGroup {
    private String _word;
    private List<Exercise> _exercises;

    private boolean _preteached;
    private boolean _known;

    private boolean _endteached;
    private boolean _endknown;

    private Group.Condition _condition;

    public ExerciseGroup(String word, Group.Condition condition){
        this._word = word;
        this._condition = condition;

        this._known = false;
        this._preteached = false;

        this._endteached = false;
        this._endknown = false;
    }

    public String getWord(){
        return this._word;
    }

    public Group.Condition getCondition() {
        return _condition;
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

//    Returns image for word
    public int getImage(){
        return getImage(this.getWord());
    }
//    Returns image for word in HD
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

    public boolean isPreteached() {
        return this._preteached;
    }

    public void setPreteached(boolean correct){
        this._preteached = true;
        this._known = correct;
    }

    public void setEndteached(boolean correct) {
        this._endteached = true;
        this._endknown = correct;
    }

    public boolean isEndteached(){
        return this._endteached;
    }

    public boolean isEndknown(){
        return this._endknown;
    }
    public boolean isKnown() {
        return this._known;
    }

    public boolean isExercised(){
        for(Exercise e : this.getExercises()){
            if(!e.hasScore()){
                return false;
            }
        }

        return true;
    }

    //    Get exercises for this group
    public List<Exercise> getExercises(){
//        Init exercises if not loaded yet
        if(_exercises == null){
            _exercises = new ArrayList<>();
        }

        return _exercises;
    }

    public void addExercise(Exercise e){
//        Make sure initialization
        getExercises();

//        Add exercise
        _exercises.add(e);
    }

    public void loadDefault(){
//        Make sure initialization
        getExercises();

//        Set default
        ListenExercise listenExercise = new ListenExercise(this.getWord());
        SpeakingExercise speakingExercise = new SpeakingExercise(this.getWord());
        SentenceExercise sentenceExercise = new SentenceExercise(this.getWord());
        SortingExercise sortingExercise = new SortingExercise(this.getWord());
        SectionExercise sectionExercise = new SectionExercise(this.getWord());

        _exercises.add(listenExercise);
        _exercises.add(speakingExercise);
        _exercises.add(sentenceExercise);
        _exercises.add(sortingExercise);
        _exercises.add(sectionExercise);

//        Add exercise for each condition if test exercise, else only required condition
        if(_condition == Group.Condition.TEST){
            AdaptiveExercise adaptiveExerciseA = new AdaptiveExercise(this.getWord(), Group.Condition.A);
            _exercises.add(adaptiveExerciseA);

            AdaptiveExercise adaptiveExerciseB = new AdaptiveExercise(this.getWord(), Group.Condition.B);
            _exercises.add(adaptiveExerciseB);

            AdaptiveExercise adaptiveExerciseC = new AdaptiveExercise(this.getWord(), Group.Condition.C);
            _exercises.add(adaptiveExerciseC);
        }else{
            AdaptiveExercise adaptiveExercise = new AdaptiveExercise(this.getWord(), this.getCondition());
            _exercises.add(adaptiveExercise);
        }
    }

//    Resets exercises
    public void clearExercises() {
        this._exercises = new ArrayList<>();
    }
}
