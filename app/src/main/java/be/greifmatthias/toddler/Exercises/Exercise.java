package be.greifmatthias.toddler.Exercises;

import android.app.Fragment;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class Exercise {
    protected String _type;
    protected String _word;
    protected boolean _hasscore;
    protected boolean _haspassed;

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
        return this._hasscore;
    }

    public String getKaatje(){ return  ""; }

    public int getIcon(){
        return R.drawable.ic_round_close;
    }

    public void setScore(boolean passed) {
        this._hasscore = true;
        this._haspassed = passed;
    }
}
