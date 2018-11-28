package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class SpeakingExercise extends Exercise {

    private boolean _listened;

    public SpeakingExercise(String word) {
        super(word);

        this._type = "Speak";
        this._listened = false;
    }

    public boolean getListened(){
        return this._listened;
    }

    public void setListened() {
        this._listened = true;
    }

    @Override
    public int getIcon() {
        if(this._listened){
            return R.drawable.ic_round_done;
        }

        return R.drawable.ic_round_close;
    }

    @Override
    public String getName() {
        return "Spreekoefening";
    }

    @Override
    public String getKaatje() {
        String output = "Wat zie je hier? ... " + this._word + ". Weet jij wat dat is? ... ";

        return "Wat is " + this.getWord() + " een leuk woord. Kan jij het ook zeggen, " + this.getWord() + "? Doe maar!​";
    }

    private String getWord(){
        List<String> wordparts = Arrays.asList(this._word.split(" "));

        if(wordparts.size() > 1){
            return wordparts.get(wordparts.size() - 1);
        }

        return this._word;
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SpeakingFragment(activity, this);
    }

    public static class SpeakingFragment extends Fragment {
        private ExerciseActivity _activity;
        private SpeakingExercise _exercise;

        public SpeakingFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SpeakingFragment(ExerciseActivity activity, SpeakingExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            activity.setKaatje(exercise.getKaatje());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view =  inflater.inflate(R.layout.fragment_listen_exercise, container, false);

            ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(_exercise._word));

            ((ImageView)view.findViewById(R.id.ivImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _activity.goNext();
                }
            });

            this._exercise.setScore(true);
            this._exercise.setListened();

            return view;
        }
    }
}
