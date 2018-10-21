package be.greifmatthias.toddler.Exercises;

import android.app.Fragment;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Models.User;

public class Exercise {
    protected String _type;
    protected String _word;

    public Exercise(String word){
        this._word = word;
    }

    public String getType(){ return this._type; }

    public String getName(){
        return "";
    }

    public Fragment getFragment(ExerciseActivity activity){
        return null;
    }

    public boolean hasScore(){
        return false;
    }

    public boolean isDone(){
        return false;
    }

    public boolean hasPasses(){
        return false;
    }

    public String getKaatje(){ return  ""; }
}
