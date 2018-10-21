package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Exercises.Exercise;
import be.greifmatthias.toddler.Exercises.ExerciseGroup;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ExerciseActivity extends Activity {

    private User _toddler;
    private int _curGroup;
    private int _curExercise;

    private TextView _tvWord;
    private FrameLayout _content;
    private View _fabNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Get stud
        this._toddler = User.get(this.getIntent().getIntExtra("toddlerId", 0));
        this._curGroup = this.getIntent().getIntExtra("group", 0);
        this._curExercise = 0;

//        Get controls
        this._tvWord = findViewById(R.id.tvWord);
        this._content = findViewById(R.id.flContent);
        this._fabNext = findViewById(R.id.fabNext);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Set header
        this._tvWord.setText(this._toddler.getExercises().get(this._curGroup).getWord());

        setContent(this._curExercise);
    }

    public void enableNext(boolean enable){
        if(enable) {
            this._fabNext.setVisibility(View.VISIBLE);
        }else{
            this._fabNext.setVisibility(View.GONE);
        }
    }

    private void setContent(int position){
        this._content.removeAllViews();

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, this._toddler.getExercises().get(this._curGroup).getExercises().get(position).getFragment(this));
        transaction.commit();
    }

    public void goNext() {
        this._curExercise++;

        setContent(this._curExercise);
    }
}
