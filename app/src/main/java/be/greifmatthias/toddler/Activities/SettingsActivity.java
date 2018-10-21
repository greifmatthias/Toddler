package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Theme.setStatusbarWhite(getWindow(), true);

        findViewById(R.id.llManager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(managerIntent);
            }
        });

        findViewById(R.id.llAbout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

        findViewById(R.id.llResources).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resourcesIntent = new Intent(getApplicationContext(), ResourceActivity.class);
                startActivity(resourcesIntent);
            }
        });
    }
}
