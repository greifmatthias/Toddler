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

    private class Sentence{
        private String _sentence;
        private boolean _valid;

        public Sentence(String sentence, boolean valid){
            this._sentence = sentence;
            this._valid = valid;
        }

        public String getSentence(){
            return this._sentence;
        }

        public boolean getValid(){
            return this._valid;
        }
    }

    @Override
    public String getKaatje() {
        return "Ik zou " + this._word + " graag in een zinnetje gebruiken, maar ik weet niet of ik het goed zeg. Help jij mij? Ik zeg een zinnetje en jij moet aanduiden of het zinnetje juist of fout is.";
    }

    public Sentence getSentence(int part){
        switch (this._word){
            case "De duikbril":
                if(part == 0){
                    return new Sentence("Met zijn duikbril kan de jongen de vissen onder water goed bekijken.", true);
                }

                return new Sentence("Met een duikbril kan ik schrijven op papier.", false);
            case "Het klimtouw":
                if(part == 0){
                    return new Sentence("In de turnzaal klim ik omhoog in het klimtouw.", true);
                }

                return new Sentence("Ik wacht op de bus in het klimtouw.", false);
            case "Het kroos":
                if(part == 0){
                    return new Sentence("De vijver is groen door het kroos.", true);
                }

                return new Sentence("Oma en het kroos zitten in de auto.", false);
            case "Het riet":
                if(part == 0){
                    return new Sentence("De eenden zitten bij het water tussen het riet.", true);
                }

                return new Sentence("Ik ga naar buiten met mijn jas en het riet aan.", false);
            case "De val":
                if(part == 0){
                    return new Sentence("Wat was dat een pijnlijke val!", true);
                }

                return new Sentence("Jan zit op de val aan tafel.", false);
            case "Het kompas":
                if(part == 0){
                    return new Sentence("Omdat papa niet weet waar we naartoe moeten lopen, kijkt hij op zijn kompas.", true);
                }

                return new Sentence("Mama belt met het kompas naar papa.", false);
            case "Steil":
                if(part == 0){
                    return new Sentence("Jan loopt de steile berg omhoog.", true);
                }

                return new Sentence("Papa leest een steil verhaaltje voor.", false);
            case "De zwaan":
                if(part == 0){
                    return new Sentence("In de vijver in het park zwemt een witte zwaan.", true);
                }

                return new Sentence("De zwaan fietst in het park.", false);
            case "Het kamp":
                if(part == 0){
                    return new Sentence("De kinderen zitten te eten tussen de tenten van het kamp.", true);
                }

                return new Sentence("Jonas wast zich met het kamp.", false);
            case "De zaklamp":
                if(part == 0){
                    return new Sentence("De jongen schijnt met de zaklamp in de donkere grot.", true);
                }

                return new Sentence("Jef opent de deur met de zaklamp.", false);
            default:
                return null;
        }
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SentenceFragment(activity, this);
    }

    public static class SentenceFragment extends Fragment {
        private ExerciseActivity _activity;
        private SentenceExercise _exercise;
        private int _current;
        private boolean _islast;
        private boolean _firstresult;

        public SentenceFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SentenceFragment(ExerciseActivity activity, SentenceExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            activity.setKaatje(exercise.getKaatje());
            activity.setFullScreen(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view =  inflater.inflate(R.layout.fragment_sentence_exercise, container, false);

            this._current = TypeHelper.getRandom(0, 1);
            ((TextView)view.findViewById(R.id.tvSentence)).setText(_exercise.getSentence(this._current).getSentence());

            view.findViewById(R.id.rlRate_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkResult(true, view);
                }
            });

            view.findViewById(R.id.rlRate_not).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkResult(false, view);
                }
            });

            return view;
        }

        private void checkResult(boolean input, View view){
            if(_islast){
                _exercise._hasscore = true;
                _exercise._haspassed = input == this._exercise.getSentence(this._current).getValid() && this._firstresult;
                _activity.goNext();
            }else{
                this._islast = true;
                this._firstresult = input == this._exercise.getSentence(this._current).getValid();

                this._current = 1 - this._current;
                ((TextView)view.findViewById(R.id.tvSentence)).setText(_exercise.getSentence(this._current).getSentence());
            }
        }
    }
}