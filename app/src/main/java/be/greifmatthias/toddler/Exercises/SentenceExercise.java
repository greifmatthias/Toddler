package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.R;

public class SentenceExercise extends Exercise {

    public SentenceExercise(String word) {
        super(word);

        this._type = "Sentence";
    }
    @Override
    public int getIcon() {
        if(this._haspassed){
            return R.drawable.ic_round_done;
        }

        return R.drawable.ic_round_close;
    }

    @Override
    public String getName() {
        return "Zinnenoefening";
    }

    @Override
    public String getKaatje() {
        return "Ik zou " + this._word + " graag in een zinnetje gebruiken, maar ik weet niet of ik het goed zeg. Help jij mij? Ik zeg een zinnetje en jij moet aanduiden of het zinnetje juist of fout is. Als het juist is, dan druk je op de groene duim. Als het zinnetje fout is, dan druk je op de rode duim. Hier gaan we.";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SentenceFragment(activity, this);
    }

    public static class SentenceFragment extends Fragment {
        private ExerciseActivity _activity;
        private SentenceExercise _exercise;

        public SentenceFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SentenceFragment(ExerciseActivity activity, SentenceExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            activity.setKaatje(exercise.getKaatje());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view =  inflater.inflate(R.layout.fragment_sentence_exercise, container, false);

            ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(_exercise._word));

            view.findViewById(R.id.fabNext).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _activity.goNext();
                }
            });

            this._exercise.setScore(true);

            return view;
        }
    }
}