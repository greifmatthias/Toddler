package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.R;

public class SortingExercise extends Exercise {

    public SortingExercise(String word) {
        super(word);

        this._type = "Sorting";
    }

    @Override
    public String getName() {
        return "Sorteeroefening";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SortingFragment(activity);
    }

    public static class SortingFragment extends Fragment {
        private ExerciseActivity _activity;

        public SortingFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SortingFragment(ExerciseActivity activity){
            this._activity = activity;

            this._activity.setKaatje("Hallo ik ben Kaat. Ik ga vandaag samen met mijn vriendjes naar het bos, op groot avontuur. Klik maar op de bomen als je wilt starten.");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_sort_exercise, container, false);

            this._activity.goNext();

            return view;
        }
    }
}