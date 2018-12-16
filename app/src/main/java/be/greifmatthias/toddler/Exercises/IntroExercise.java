package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.R;

public class IntroExercise extends Exercise {

    public IntroExercise(String word) {
        super(word);

        this._type = "Intro";
    }

    @Override
    public String getName() {
        return "Introfilmpje";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new IntroFragment(activity);
    }

    public static class IntroFragment extends Fragment {
        private IntroExercise _exercise;
        private ExerciseActivity _activity;

        public IntroFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public IntroFragment(ExerciseActivity activity){
            this._activity = activity;

            this._activity.setFullScreen(true);

            this._activity.setKaatje("Hallo ik ben Kaat. Ik ga vandaag samen met mijn vriendjes naar het bos, op groot avontuur. Klik maar op de bomen als je wilt starten.");
            this._activity.setKaatje_voice(R.raw.intro);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_intro, container, false);

            view.findViewById(R.id.ivStory).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _activity.goNext();
                }
            });

            return view;
        }
    }
}