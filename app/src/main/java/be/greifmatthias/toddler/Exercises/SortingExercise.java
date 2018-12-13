package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class SortingExercise extends Exercise {

    public SortingExercise(String word) {
        super(word);

        this._type = "Sorting";
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
        return "Sorteeroefening";
    }

    @Override
    public String getKaatje() {
        return "";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SortingFragment(activity, this);
    }

    public List<Tile> getTiles(){
        List<Tile> tiles = new ArrayList<>();

        switch (this._word){
            case "De duikbril":
                tiles.add(new Tile("Ogen", R.drawable.ogen, true));
                tiles.add(new Tile("In de zee", R.drawable.appel, true));
                tiles.add(new Tile("Zwemmen", R.drawable.zwemmen, true));
                tiles.add(new Tile("Schrijven", R.drawable.schrijven, false));
                break;
            case "Het klimtouw":
                tiles.add(new Tile("Klimmen", R.drawable.klimmen, true));
                tiles.add(new Tile("Sterk", R.drawable.sterk, true));
                tiles.add(new Tile("In de turnzaal", R.drawable.turnzaal, true));
                tiles.add(new Tile("Het zwembad", R.drawable.zwembad, false));
                break;
            case "Het kroos":
                tiles.add(new Tile("Groen", R.drawable.groen, true));
                tiles.add(new Tile("In de vijver", R.drawable.vijver, true));
                tiles.add(new Tile("De lamp", R.drawable.lamp, false));
                tiles.add(new Tile("De eend", R.drawable.eend, false));
                break;
            case "Het riet":
                tiles.add(new Tile("De vijver", R.drawable.vijver, true));
                tiles.add(new Tile("De eend", R.drawable.eend, true));
                tiles.add(new Tile("Het bos", R.drawable.bos, true));
                tiles.add(new Tile("De bril", R.drawable.bril, false));
                break;
            case "De val":
                tiles.add(new Tile("De pijn", R.drawable.pijn, true));
                tiles.add(new Tile("Naar voor", R.drawable.naarvoor, true));
                tiles.add(new Tile("De pleister", R.drawable.pleister, true));
                tiles.add(new Tile("De appel", R.drawable.appel, false));
                break;
            case "Het kompas":
                tiles.add(new Tile("Wandelen", R.drawable.wandelen, true));
                tiles.add(new Tile("Rugzak", R.drawable.rugzak, true));
                tiles.add(new Tile("De landkaart", R.drawable.landkaart, true));
                tiles.add(new Tile("Het bad", R.drawable.bad, false));
                break;
            case "Steil":
                tiles.add(new Tile("De berg", R.drawable.berg, true));
                tiles.add(new Tile("Beklimmen", R.drawable.klimmen, true));
                tiles.add(new Tile("De trap", R.drawable.trap, true));
                tiles.add(new Tile("De bloem", R.drawable.bloem, false));
                break;
            case "De zwaan":
                tiles.add(new Tile("De vijver", R.drawable.vijver, true));
                tiles.add(new Tile("Vleugels", R.drawable.vleugels, true));
                tiles.add(new Tile("Wit", R.drawable.wit, true));
                tiles.add(new Tile("Het boek", R.drawable.boek, false));
                break;
            case "Het kamp":
                tiles.add(new Tile("De tent", R.drawable.kamp, true));
                tiles.add(new Tile("Het kampvuur", R.drawable.kampvuur, true));
                tiles.add(new Tile("De slaapzak", R.drawable.slaapzak, true));
                tiles.add(new Tile("De deur", R.drawable.deur, false));
                break;
            case "De zaklamp":
                tiles.add(new Tile("Het licht", R.drawable.licht, true));
                tiles.add(new Tile("De batterij", R.drawable.batterij, true));
                tiles.add(new Tile("In het donker", R.drawable.donker, true));
                tiles.add(new Tile("Het paard", R.drawable.paard, false));
                break;
        }

        return tiles;
    }

    public class Tile{
        private String _subject;
        private int _image;
        private boolean _valid;

        public Tile(String subject, int image, boolean valid){
            this._subject = subject;
            this._image = image;
            this._valid = valid;
        }

        public String getSubject(){
            return this._subject;
        }

        public int getImage(){
            return this._image;
        }

        public boolean isValid(){
            return this._valid;
        }
    }

    public static class SortingFragment extends Fragment {
        private ExerciseActivity _activity;
        private SortingExercise _exercise;

        public SortingFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SortingFragment(ExerciseActivity activity, SortingExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            activity.setKaatje("");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view =  inflater.inflate(R.layout.fragment_sort_exercise, container, false);

            // Set banner
            ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(_exercise._word));

            // Set exercise images
            ((ImageView)view.findViewById(R.id.ivImage01)).setImageResource(this._exercise.getTiles().get(0).getImage());
            ((ImageView)view.findViewById(R.id.ivImage02)).setImageResource(this._exercise.getTiles().get(1).getImage());
            ((ImageView)view.findViewById(R.id.ivImage03)).setImageResource(this._exercise.getTiles().get(2).getImage());
            ((ImageView)view.findViewById(R.id.ivImage04)).setImageResource(this._exercise.getTiles().get(3).getImage());
            // Set exercise texts
            ((TextView)view.findViewById(R.id.tvImage01)).setText(this._exercise.getTiles().get(0).getSubject());
            ((TextView)view.findViewById(R.id.tvImage02)).setText(this._exercise.getTiles().get(1).getSubject());
            ((TextView)view.findViewById(R.id.tvImage03)).setText(this._exercise.getTiles().get(2).getSubject());
            ((TextView)view.findViewById(R.id.tvImage04)).setText(this._exercise.getTiles().get(3).getSubject());


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
