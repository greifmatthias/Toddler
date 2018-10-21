package be.greifmatthias.toddler.Exercises;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class ListenExercise extends Exercise {

    public ListenExercise(String word) {
        super(word);

        this._type = "Listen";
    }

    @Override
    public String getName() {
        return "Luisteroefening";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new ListenFragment();
    }

    public static class ListenFragment extends Fragment {
        public ListenFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_listen, container, false);
        }
    }
}
