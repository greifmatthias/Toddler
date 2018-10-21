package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.os.Bundle;

import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ResourceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);
    }
}
