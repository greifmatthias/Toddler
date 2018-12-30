package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Exercises.ExerciseGroup;
import be.greifmatthias.toddler.Exercises.IntroExercise;
import be.greifmatthias.toddler.Models.Group;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Helpers.Theme;

public class ExerciseActivity extends Activity {

    private List<ExerciseGroup> _exercises;

    private User _toddler;
    private int _condition;
    private int _curExercise;
    private int _curGroup;

    private TextView _tvWord;

    private RelativeLayout _rlOverlayKaatje;
    private RelativeLayout _rlKaatje;
    private TextView _tvKaatje;
    private RelativeLayout _rlVoiceKaatje;

    private boolean _hasvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Get stud
        this._toddler = User.get(this.getIntent().getIntExtra("toddlerId", 0));
        this._condition = this.getIntent().getIntExtra("condition", 0);
        this._curExercise = 0;
        this._curGroup = 0;

//        Get controls
        this._tvWord = findViewById(R.id.tvWord);
        this._rlOverlayKaatje = findViewById(R.id.rlKaatje_Overlay);
        this._rlKaatje = findViewById(R.id.rlKaatje);
        this._tvKaatje = findViewById(R.id.tvKaatje);
        this._rlVoiceKaatje = findViewById(R.id.rlVoiceKaatje);

//        Get exercises
        this._exercises = new ArrayList<>();

//        Setup exercises
        ExerciseGroup exercise = new ExerciseGroup("", Group.Condition.A);
        exercise.clearExercises();

//        Add exercise_intro
        exercise.addExercise(new IntroExercise(""));
        this._exercises.add(exercise);

//        Loop others
        for(ExerciseGroup e : this._toddler.getExercises()){
            if (e.getCondition().equals(Group.getCondition(this._condition))) {
                this._exercises.add(e);
            }
        }

        // Set Kaatje click listener
        this.findViewById(R.id.ivKaatje).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_rlOverlayKaatje.getVisibility() == View.VISIBLE){
                    _rlOverlayKaatje.setVisibility(View.GONE);
                    _tvKaatje.setVisibility(View.GONE);
                }else{
                    _rlOverlayKaatje.setVisibility(View.VISIBLE);
                    _tvKaatje.setVisibility(View.VISIBLE);
                }
            }
        });

//        Set KaatjeVoice click listener, to replay sound
        this._rlVoiceKaatje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_hasvoice){
                    __player.start();
                }
            }
        });

        this.findViewById(R.id.rlKaatje_Overlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _rlOverlayKaatje.setVisibility(View.GONE);
                _tvKaatje.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setContent(this._curExercise);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(this._hasvoice){
            this.__player.stop();
        }
    }

    private void setContent(int position){
//        Revert if fullscreen
        if(_tvWord.getVisibility() == View.GONE){
            this.setFullScreen(false);
        }

//        Set header
        this._tvWord.setText(this._exercises.get(this._curGroup).getWord());

//        Set screen
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, this._exercises.get(this._curGroup).getExercises().get(position).getFragment(this));
        transaction.commit();
    }

    public void goNext() {
//        Stop voice if playing
        if(this._hasvoice){
            this.__player.stop();

            this._rlVoiceKaatje.setVisibility(View.GONE);
        }

//        Set to next exercise
        this._curExercise++;

        if(_curGroup < this._exercises.size()) {
            if (this._curExercise == this._exercises.get(_curGroup).getExercises().size()) {
//            Is last so reset for next word
                this._curExercise = 0;
                this._curGroup++;

//            Check if last word
                if (this._curGroup == this._exercises.size()) {
//                    Update exercises to toddler, and save
                    this._toddler.setExercises(this._exercises);
                    this._toddler.saveExercises();

//                    Return to last activity
                    this.finish();
                    return;
                }
            }

//            Update UI
            setContent(this._curExercise);
        }
    }

//    Public function to set talking of kaatje
    public void setKaatje(String text){
        if(text.equals("")){
            this._rlKaatje.setVisibility(View.GONE);
        }else{
            this._rlKaatje.setVisibility(View.VISIBLE);
            this._rlOverlayKaatje.setVisibility(View.VISIBLE);
            this._tvKaatje.setVisibility(View.VISIBLE);
            this._tvKaatje.setText(text);
        }
    }

    public void setFullScreen(boolean fullScreen){
        if(fullScreen){
            _tvWord.setVisibility(View.GONE);
        }else{
            _tvWord.setVisibility(View.VISIBLE);
        }
    }

    private MediaPlayer __player;
    public void setKaatje_voice(int resource) {
        if(resource == 0){
            this._hasvoice = false;
        }else{
            this._hasvoice = true;

            this._rlVoiceKaatje.setVisibility(View.GONE);

            if(this.__player != null) {
                this.__player.release();
            }

            this.__player = MediaPlayer.create(this, resource);

            this.__player.start();

            this.__player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    _rlVoiceKaatje.setVisibility(View.VISIBLE);

                    _rlOverlayKaatje.setVisibility(View.GONE);
                    _tvKaatje.setVisibility(View.GONE);
                }
            });
        }
    }

    public void setKaatje_voice(int resource, final setKaatjeVoiceCallback callback) {
        if(resource == 0){
            this._hasvoice = false;
        }else{
            this._hasvoice = true;

            this._rlVoiceKaatje.setVisibility(View.GONE);

            if(this.__player != null) {
                this.__player.release();
            }

            this.__player = MediaPlayer.create(this, resource);

            this.__player.start();

            this.__player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    _rlVoiceKaatje.setVisibility(View.VISIBLE);

                    _rlOverlayKaatje.setVisibility(View.GONE);
                    _tvKaatje.setVisibility(View.GONE);

                    callback.onCompete();
                }
            });
        }
    }

    public interface setKaatjeVoiceCallback{
        public void onCompete();
    }
}
