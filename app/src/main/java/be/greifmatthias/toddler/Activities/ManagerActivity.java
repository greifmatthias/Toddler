package be.greifmatthias.toddler.Activities;

import android.os.Bundle;
import android.app.Activity;

import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ManagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Theme.setStatusbarWhite(getWindow(), true);
    }

}
