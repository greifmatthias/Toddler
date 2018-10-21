package be.greifmatthias.toddler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Models.User;

public class PreteachingActivity extends Activity {

    private User _toddler;
    private int _currentWord;
    private int _currentCorrect;

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
        List<ImageView> images = new ArrayList<>();
        images.add(this._ivImage01);
        images.add(this._ivImage02);
        images.add(this._ivImage03);
        images.add(this._ivImage04);

        List<Integer> usedwords = new ArrayList<>();
        usedwords.add(this._currentWord);

        for(ImageView view : images){
            usedwords.add(getRandomWord(usedwords));
            view.setImageResource(this._toddler.getExercises().get(usedwords.get(usedwords.size() - 1)).getImage());
        }

        this._currentCorrect = Helpers.getRandom(0, 3);
        images.get(this._currentCorrect).setImageResource(this._toddler.getExercises().get(this._currentWord).getImage());
    }

    private int getRandomWord(List<Integer> exclude){
        int index = -1;

        while (index == -1) {
            index = Helpers.getRandom(0, this._toddler.getExercises().size() - 1);

            boolean isgood = true;
            for(Integer i : exclude){
                if(index == i){
                    isgood = false;
                    break;
                }
            }

            if(!isgood){
                index = -1;
            }
        }

        return index;
    }

    public void onImageClick(View view) {
//        Set preteached
        this._toddler.getExercises().get(this._currentWord).setPreteached();

//        Set if correct
        if(view.getTag().equals(Integer.toString(this._currentCorrect))){
            this._toddler.getExercises().get(this._currentWord).setCorrect();
        }

//        Next word
        this._currentWord++;

//        Update UI
        if(this._currentWord == this._toddler.getExercises().size()){
//            Save and close
            this._toddler.saveExercises();

            this.finish();
        }else {
//            Next image
            this.setContent();
        }
    }
}
