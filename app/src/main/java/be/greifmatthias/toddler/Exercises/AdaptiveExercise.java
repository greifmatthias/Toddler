package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Helpers.TypeHelper;
import be.greifmatthias.toddler.Models.Group;
import be.greifmatthias.toddler.R;

public class AdaptiveExercise extends Exercise {
    private Group.Condition condition;

    public AdaptiveExercise(String word, Group.Condition condition) {
        super(word);

        this._type = "Adaptive";
        this.condition = condition;
    }
    @Override
    public int getIcon() {
        if(this._haspassed){
            return R.drawable.ic_round_sentiment_satisfied;
        }

        return R.drawable.ic_round_sentiment_dissatisfied;
    }

    @Override
    public String getName() {
        String name = "Veranderende oefening: ";
        switch (condition){
            case A:
                name += "Gevoel";
                break;
            case B:
                name += "Zoemen";
                break;
            case C:
                name += "Lettergrepen";
                break;
        }
        return name;
    }

    @Override
    public String getKaatje() {
        switch (condition){
            case A:
                return this._word + ", wat is dat een mooi woord. Vind jij " + this._word + " ook zo een mooi woord of toch niet?";
                break;
            case B:
                return "";
                break;
            case C:
                return "";
                break;
        }
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new AdaptiveFragment(activity, this);
    }

    public static class AdaptiveFragment extends Fragment {
        private ExerciseActivity _activity;
        private AdaptiveExercise _exercise;

        public AdaptiveFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public AdaptiveFragment(ExerciseActivity activity, AdaptiveExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            activity.setKaatje(exercise.getKaatje());
            activity.setFullScreen(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view =  inflater.inflate(getLayout(), container, false);

            return view;
        }

        private int getLayout(){
            switch (this._exercise.condition){
                case B:
                    return R.layout.fragment_adaptive_exercise_B;
                case C:
                    return R.layout.fragment_adaptive_exercise_C;
                    default:
                        return R.layout.fragment_adaptive_exercise_A;
            }
        }
    }

}