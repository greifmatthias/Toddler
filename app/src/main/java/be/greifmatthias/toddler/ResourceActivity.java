package be.greifmatthias.toddler;

import android.app.Activity;
import android.os.Bundle;

public class ResourceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);
    }
}
