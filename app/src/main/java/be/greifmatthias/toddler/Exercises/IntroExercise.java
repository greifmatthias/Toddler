package be.greifmatthias.toddler.Exercises;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.greifmatthias.toddler.R;

public class IntroExercise extends Exercise {
    private boolean _viewed;

    public IntroExercise(String word) {
        super(word);

        this._type = "Intro";
        this._viewed = false;
    }

    @Override
    public boolean hasPasses() {
        return this._viewed;
    }

    @Override
    public String getName() {
        return "Introfilmpje";
    }

    @Override
    public Fragment getFragment() {
        return new IntroFragment();
    }

    public static class IntroFragment extends Fragment {
        public IntroFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_intro, container, false);
        }
    }
}