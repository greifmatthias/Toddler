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
        private int _voice;
        private boolean _valid;

        public Sentence(String sentence, int voice, boolean valid){
            this._sentence = sentence;
            this._voice = voice;
            this._valid = valid;
        }

        public String getSentence(){
            return this._sentence;
        }
        public int getVoice(){
            return this._voice;
        }
        public boolean getValid(){
            return this._valid;
        }
    }

    @Override
    public String getKaatje() {
        return "Ik zou " + this._word + " graag in een zinnetje gebruiken, maar ik weet niet of ik het goed zeg. Help jij mij? Ik zeg een zinnetje en jij moet aanduiden of het zinnetje juist of fout is.";
    }

    public int getVoice(){
        switch (this._word){
            case "De duikbril":
                return R.raw.sentence_duikbril;
            case "Het klimtouw":
                return R.raw.sentence_klimtouw;
            case "Het kroos":
                return R.raw.sentence_kroos;
            case "Het riet":
                return R.raw.sentence_riet;
            case "De val":
                return R.raw.sentence_val;
            case "Het kompas":
                return R.raw.sentence_kompas;
            case "Steil":
                return R.raw.sentence_steil;
            case "De zwaan":
                return R.raw.sentence_zwaan;
            case "Het kamp":
                return R.raw.sentence_kamp;
            case "De zaklamp":
                return R.raw.sentence_zaklamp;
        }

        return 0;
    }

    public Sentence getSentence(int part){
        switch (this._word){
            case "De duikbril":
                if(part == 0){
                    return new Sentence("Met zijn duikbril kan de jongen de vissen onder water goed bekijken.", R.raw.sentence_duikbril_true, true);
                }

                return new Sentence("Met een duikbril kan ik schrijven op papier.", R.raw.sentence_duikbril_false, false);
            case "Het klimtouw":
                if(part == 0){
                    return new Sentence("In de turnzaal klim ik omhoog in het klimtouw.", R.raw.sentence_klimtouw_true, true);
                }

                return new Sentence("Ik wacht op de bus in het klimtouw.", R.raw.sentence_klimtouw_false, false);
            case "Het kroos":
                if(part == 0){
                    return new Sentence("De vijver is groen door het kroos.", R.raw.sentence_kroos_true, true);
                }

                return new Sentence("Oma en het kroos zitten in de auto.", R.raw.sentence_kroos_false, false);
            case "Het riet":
                if(part == 0){
                    return new Sentence("De eenden zitten bij het water tussen het riet.", R.raw.sentence_riet_true, true);
                }

                return new Sentence("Ik ga naar buiten met mijn jas en het riet aan.", R.raw.sentence_riet_false, false);
            case "De val":
                if(part == 0){
                    return new Sentence("Wat was dat een pijnlijke val!", R.raw.sentence_val_true, true);
                }

                return new Sentence("Jan zit op de val aan tafel.", R.raw.sentence_val_false, false);
            case "Het kompas":
                if(part == 0){
                    return new Sentence("Omdat papa niet weet waar we naartoe moeten lopen, kijkt hij op zijn kompas.", R.raw.sentence_kompas_true, true);
                }

                return new Sentence("Mama belt met het kompas naar papa.", R.raw.sentence_kompas_false, false);
            case "Steil":
                if(part == 0){
                    return new Sentence("Jan loopt de steile berg omhoog.", R.raw.sentence_steil_true, true);
                }

                return new Sentence("Papa leest een steil verhaaltje voor.", R.raw.sentence_steil_false, false);
            case "De zwaan":
                if(part == 0){
                    return new Sentence("In de vijver in het park zwemt een witte zwaan.", R.raw.sentence_zwaan_true, true);
                }

                return new Sentence("De zwaan fietst in het park.", R.raw.sentence_zwaan_false, false);
            case "Het kamp":
                if(part == 0){
                    return new Sentence("De kinderen zitten te eten tussen de tenten van het kamp.", R.raw.sentence_kamp_true, true);
                }

                return new Sentence("Jonas wast zich met het kamp.", R.raw.sentence_kamp_false, false);
            case "De zaklamp":
                if(part == 0){
                    return new Sentence("De jongen schijnt met de zaklamp in de donkere grot.", R.raw.sentence_zaklamp_true, true);
                }

                return new Sentence("Jef opent de deur met de zaklamp.", R.raw.sentence_zaklamp_false, false);
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
            this._activity.setKaatje_voice(this._exercise.getVoice(), new ExerciseActivity.setKaatjeVoiceCallback() {
                @Override
                public void onCompete() {
                    _activity.setKaatje(_exercise.getKaatje());
                    _activity.setKaatje_voice(R.raw.sentence_end, new ExerciseActivity.setKaatjeVoiceCallback() {
                        @Override
                        public void onCompete() {
                            _activity.setKaatje_voice(_exercise.getSentence(_current).getVoice());
                        }
                    });
                }
            });
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

        private void checkResult(final boolean input, final View view){

            this._activity.setKaatje_voice(getVoice(this._exercise.getSentence(this._current).getValid() == input), new ExerciseActivity.setKaatjeVoiceCallback() {
                @Override
                public void onCompete() {
                    if(_islast){
                        _exercise._hasscore = true;
                        _exercise._haspassed = input == _exercise.getSentence(_current).getValid() && _firstresult;
                        _activity.goNext();
                    }else{
                        _islast = true;
                        _firstresult = input == _exercise.getSentence(_current).getValid();

                        _current = 1 - _current;
                        ((TextView)view.findViewById(R.id.tvSentence)).setText(_exercise.getSentence(_current).getSentence());
                        _activity.setKaatje_voice(_exercise.getSentence(_current).getVoice());
                    }
                }
            });
        }

        private int getVoice(boolean correct){
            if(correct){
                return R.raw.sentence_true;
            }

            return R.raw.sentence_false;
        }
    }
}