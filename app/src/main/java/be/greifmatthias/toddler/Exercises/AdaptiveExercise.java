package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
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
        switch (this.condition){
            case A:
                if(this._haspassed){
                    return R.drawable.ic_round_sentiment_satisfied;
                }

                return R.drawable.ic_round_sentiment_dissatisfied;
            default:
                if(this._haspassed){
                    return R.drawable.ic_round_done;
                }

                return R.drawable.ic_round_close;
        }
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
                return this._word + ", wat is dat een mooi woord. Vind jij " + this.getWord() + " ook zo een mooi woord of toch niet?";
            case B:
                return "";
            case C:
                return "";
        }

        return "";
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

            this._activity.setKaatje(exercise.getKaatje());
            this._activity.setFullScreen(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view = inflater.inflate(getLayout(), container, false);

            setupLayout(view);

            return view;
        }

        private int getLayout() {
            switch (this._exercise.condition) {
                case B:
                    return R.layout.fragment_adaptive_exercise_b;
                case C:
                    return R.layout.fragment_adaptive_exercise_c;
                default:
                    return R.layout.fragment_adaptive_exercise_a;
            }
        }

        private void setupLayout(View view){
            switch (this._exercise.condition) {
                case B:
//                    B
                case C:
//                    C
                    this._activity.setFullScreen(false);
                default:
//                    A
                    ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(this._exercise._word));

//                    Set onclick listeners
                    view.findViewById(R.id.rlRate_yes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setresult(true);
                        }
                    });

                    view.findViewById(R.id.rlRate_not).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setresult(false);
                        }
                    });
            }
        }

//        Set result for exercises
        private void setresult(boolean passed){
//            Update results
            this._exercise._hasscore = true;
            this._exercise._haspassed = passed;

//            Continue flow
            this._activity.goNext();
        }
    }
}