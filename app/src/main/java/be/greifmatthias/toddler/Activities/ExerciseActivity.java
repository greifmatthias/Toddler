package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.os.Bundle;

import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ExerciseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);
    }
}
