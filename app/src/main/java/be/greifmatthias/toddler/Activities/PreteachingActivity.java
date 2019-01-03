package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Helpers.TypeHelper;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Helpers.Theme;

public class PreteachingActivity extends Activity {

    private User _toddler;
    private int _currentWord;
    private int _currentCorrect;

    private boolean _end;

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
        this._end = this.getIntent().getBooleanExtra("end", false);
        this._currentWord = 1; // 0 = Word for testexercising so skip
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.setContent();
    }

    private MediaPlayer _player;
    private void setContent(){
//        Set header
        this._tvWord.setText(this._toddler.getExercises().get(_currentWord).getWord());

//        Set voice
        if(this._player != null){
            this._player.release();
        }
        this._player = MediaPlayer.create(this, getVoice(this._toddler.getExercises().get(_currentWord).getWord()));
        this._player.start();

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

        this._currentCorrect = TypeHelper.getRandom(0, 3);
        images.get(this._currentCorrect).setImageResource(this._toddler.getExercises().get(this._currentWord).getImage());
    }

    private int getRandomWord(List<Integer> exclude){
        int index = -1;

        while (index == -1) {
            index = TypeHelper.getRandom(1, this._toddler.getExercises().size() - 1);

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
        if(_currentWord < this._toddler.getExercises().size()) {
//        Set result
            if(this._end){
                this._toddler.getExercises().get(this._currentWord).setEndteached(view.getTag().equals(Integer.toString(this._currentCorrect)));
            }else {
                this._toddler.getExercises().get(this._currentWord).setPreteached(view.getTag().equals(Integer.toString(this._currentCorrect)));
            }

//        Next word
            this._currentWord++;

//        Update UI
            if (this._currentWord == this._toddler.getExercises().size()) {
//            Save and close
                this._toddler.saveExercises();

                this.finish();
            } else {
//            Next image
                this.setContent();
            }
        }
    }

    private int getVoice(String word){
        switch (word){
            case "De duikbril":
                return R.raw.preteach_duikbril;
            case "Het klimtouw":
                return R.raw.preteach_klimtouw;
            case "Het kroos":
                return R.raw.preteach_kroos;
            case "Het riet":
                return R.raw.preteach_riet;
            case "De val":
                return R.raw.preteach_val;
            case "Het kompas":
                return R.raw.preteach_kompas;
            case "Steil":
                return R.raw.preteach_steil;
            case "De zwaan":
                return R.raw.preteach_zwaan;
            case "Het kamp":
                return R.raw.preteach_kamp;
            case "De zaklamp":
                return R.raw.preteach_zaklamp;
        }

        return 0;
    }
}
