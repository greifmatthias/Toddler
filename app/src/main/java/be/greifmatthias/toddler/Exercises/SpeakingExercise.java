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

    public SpeakingExercise(String word) {
        super(word);

        this._type = "Speak";
    }

    @Override
    public int getIcon() {
        if(this.hasScore() && this._haspassed){
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

//            Set Kaatjes text
            activity.setKaatje(exercise.getKaatje());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view =  inflater.inflate(R.layout.fragment_speak_exercise, container, false);

//            Set image
            ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(_exercise._word));

//            Set clickhandlers
//            Unlock fab
            view.findViewById(R.id.fabUnlock).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.findViewById(R.id.rlRate_yes).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.rlRate_not).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.fabUnlock).setVisibility(View.GONE);
                }
            });

//            Raters
            view.findViewById(R.id.rlRate_not).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _exercise.setScore(false);
                    _activity.goNext();
                }
            });

            view.findViewById(R.id.rlRate_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _exercise.setScore(true);
                    _activity.goNext();
                }
            });

            return view;
        }
    }
}
