package be.greifmatthias.toddler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import be.greifmatthias.toddler.Models.User;

public class PreteachingActivity extends Activity {

    private User _toddler;
    private int _currentWord;

    private TextView _tvWord;
    private ImageView _ivImage01;
    private ImageView _ivImage02;
    private ImageView _ivImage03;
    private ImageView _ivImage04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preteaching);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Get controls
        this._tvWord = findViewById(R.id.tvWord);
        this._ivImage01 = findViewById(R.id.ivImage01);
        this._ivImage02 = findViewById(R.id.ivImage02);
        this._ivImage03 = findViewById(R.id.ivImage03);
        this._ivImage04 = findViewById(R.id.ivImage04);

//        Load data
        this._toddler = User.get(this.getIntent().getIntExtra("toddlerId", 0));
        this._currentWord = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.setContent();
    }

    private void setContent(){
//        Set header
        this._tvWord.setText(this._toddler.getExercises().get(_currentWord).getWord());

//        Set images
        this._ivImage01.setImageResource(this._toddler.getExercises().get(_currentWord).getImage());
        this._ivImage02.setImageResource(this._toddler.getExercises().get(_currentWord).getImage());
        this._ivImage03.setImageResource(this._toddler.getExercises().get(_currentWord).getImage());
        this._ivImage04.setImageResource(this._toddler.getExercises().get(_currentWord).getImage());
    }
}
