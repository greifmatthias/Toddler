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

public class ListenExercise extends Exercise {

    public ListenExercise(String word) {
        super(word);

        this._type = "Listen";
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
        return "Luisteroefening";
    }

    @Override
    public String getKaatje() {
        String output = "Wat zie je hier? ... " + this._word + ". Weet jij wat dat is? ... ";

        switch (this._word){
            case "De duikbril":
                output += "Een duikbril is een bril voor onder water. Daarmee kun je onder water je ogen open houden.";
                break;
            case "Het klimtouw":
                output += "Een klimtouw is een touw waarin je omhoog kunt klimmen.";
                break;
            case "Het kroos":
                output += "Kroos bestaat uit kleine, groene plantjes die op het water groeien. Je ziet het bijvoorbeeld in een sloot.";
                break;
            case "Het riet":
                output += "Riet lijkt op hoog gras. Het heeft lange stengels en groeit langs het water.";
                break;
            case "De val":
                output += "Als je een val maakt, val je op de grond.";
                break;
            case "Het kompas":
                output += "Met een kompas weet je waar je naartoe moet. De naald van het kompas geeft het noorden aan.";
                break;
            case "Steil":
                output += "Een steile berg gaat heel schuin omhoog of omlaag.";
                break;
            case "De zwaan":
                output += "Een zwaan is een grote vogel met een lange, kromme hals. Meestal zijn zwanen wit, maar soms zwart.";
                break;
            case "Het kamp":
                output += "Een kamp is een plaats om buiten te wonen en te slapen, bijvoorbeeld in tenten.";
                break;
            case "De zaklamp":
                output += "Een zaklamp is een kleine lamp die je overal mee naartoe kunt nemen.";
                break;
        }

        return output;
    }

    public int getVoices(){
        switch (this._word){
            case "De duikbril":
                return R.raw.listen_duikbril;
            case "Het klimtouw":
                return R.raw.listen_klimtouw;
            case "Het kroos":
                return R.raw.listen_kroos;
            case "Het riet":
                return R.raw.listen_riet;
            case "De val":
                return R.raw.listen_val;
            case "Het kompas":
                return R.raw.listen_kompas;
            case "Steil":
                return R.raw.listen_steil;
            case "De zwaan":
                return R.raw.listen_zwaan;
            case "Het kamp":
                return R.raw.listen_kamp;
            case "De zaklamp":
                return R.raw.listen_zaklamp;
        }

        return 0;
    }

    public int getVoices_pre(){
        switch (this._word){
            case "De duikbril":
                return R.raw.listen_duikbril_aanbieden;
            case "Het klimtouw":
                return R.raw.listen_klimtouw_aanbieden;
            case "Het kroos":
                return R.raw.listen_kroos_aanbieden;
            case "Het riet":
                return R.raw.listen_riet_aanbieden;
            case "De val":
                return R.raw.listen_val_aanbieden;
            case "Het kompas":
                return R.raw.listen_kompas_aanbieden;
            case "Steil":
                return R.raw.listen_steil_aanbieden;
            case "De zwaan":
                return R.raw.listen_zwaan_aanbieden;
            case "Het kamp":
                return R.raw.listen_kamp_aanbieden;
            case "De zaklamp":
                return R.raw.listen_zaklamp_aanbieden;
        }

        return 0;
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new ListenFragment(activity, this);
    }

    public static class ListenFragment extends Fragment {
        private ExerciseActivity _activity;
        private ListenExercise _exercise;

        public ListenFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public ListenFragment(ExerciseActivity activity, ListenExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            this._exercise.setScore(true);

            this._activity.setKaatje(this._exercise.getKaatje());
            this._activity.setKaatje_voice(this._exercise.getVoices_pre(), new ExerciseActivity.setKaatjeVoiceCallback() {
                @Override
                public void onCompete() {
                    _activity.setKaatje_voice(_exercise.getVoices());
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view =  inflater.inflate(R.layout.fragment_listen_exercise, container, false);

            ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(_exercise._word));

            view.findViewById(R.id.fabNext).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _activity.goNext();
                }
            });

            return view;
        }
    }
}
