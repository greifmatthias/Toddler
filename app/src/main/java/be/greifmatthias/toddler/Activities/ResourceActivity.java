package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Helpers.Theme;

public class ResourceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

        findViewById(R.id.llIcons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://material.io/tools/icons/?style=round"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.llImages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.llCharacters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com"));
                startActivity(browserIntent);
            }
        });
    }
}
