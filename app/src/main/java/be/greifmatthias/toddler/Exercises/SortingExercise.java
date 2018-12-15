package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Helpers.TypeHelper;
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
        return "Ik zoek 3 woorden die bij " + this.getWord() + " passen, weet jij welke?";
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

        private int[] _content;
        private int[] _selected;

        public SortingFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SortingFragment(ExerciseActivity activity, SortingExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            this._selected = new int[] {};

            activity.setKaatje(exercise.getKaatje());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view =  inflater.inflate(R.layout.fragment_sort_exercise, container, false);

            // Set banner
            ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(_exercise._word));

            this._content = TypeHelper.shuffleArray(new int[]{ 0, 1, 2, 3 });

            // Set exercise images
            ((ImageView)view.findViewById(R.id.ivImage01)).setImageResource(this._exercise.getTiles().get(this._content[0]).getImage());
            ((ImageView)view.findViewById(R.id.ivImage02)).setImageResource(this._exercise.getTiles().get(this._content[1]).getImage());
            ((ImageView)view.findViewById(R.id.ivImage03)).setImageResource(this._exercise.getTiles().get(this._content[2]).getImage());
            ((ImageView)view.findViewById(R.id.ivImage04)).setImageResource(this._exercise.getTiles().get(this._content[3]).getImage());
            // Set exercise texts
            ((TextView)view.findViewById(R.id.tvImage01)).setText(this._exercise.getTiles().get(this._content[0]).getSubject());
            ((TextView)view.findViewById(R.id.tvImage02)).setText(this._exercise.getTiles().get(this._content[1]).getSubject());
            ((TextView)view.findViewById(R.id.tvImage03)).setText(this._exercise.getTiles().get(this._content[2]).getSubject());
            ((TextView)view.findViewById(R.id.tvImage04)).setText(this._exercise.getTiles().get(this._content[3]).getSubject());

//            Set click handlers
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Get val
                    int val = 0;
                    switch (v.getId()){
                        case R.id.rlImage01:
                            val = 0;
                            break;
                        case R.id.rlImage02:
                            val = 1;
                            break;
                        case R.id.rlImage03:
                            val = 2;
                            break;
                        case R.id.rlImage04:
                            val = 3;
                            break;
                    }

//                    Update selection
                    if(TypeHelper.contains(_selected, val)){
                        // remove selection
                        _selected = TypeHelper.remove(_selected, val);

//                        Update view
                        switch (val){
                            case 0:
                                view.findViewById(R.id.rlImage01_check).setVisibility(View.GONE);
                                break;
                            case 1:
                                view.findViewById(R.id.rlImage02_check).setVisibility(View.GONE);
                                break;
                            case 2:
                                view.findViewById(R.id.rlImage03_check).setVisibility(View.GONE);
                                break;
                            case 3:
                                view.findViewById(R.id.rlImage04_check).setVisibility(View.GONE);
                                break;
                        }
                    }else{
                        if(_selected.length < 3) {
                            // add selection
                            _selected = TypeHelper.add(_selected, val);

//                        Update view
                            switch (val) {
                                case 0:
                                    view.findViewById(R.id.rlImage01_check).setVisibility(View.VISIBLE);
                                    break;
                                case 1:
                                    view.findViewById(R.id.rlImage02_check).setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    view.findViewById(R.id.rlImage03_check).setVisibility(View.VISIBLE);
                                    break;
                                case 3:
                                    view.findViewById(R.id.rlImage04_check).setVisibility(View.VISIBLE);
                                    break;
                            }
                        }
                    }

                    if(_selected.length == 3){
                        view.findViewById(R.id.fabNext).setVisibility(View.VISIBLE);
                    }else{
                        view.findViewById(R.id.fabNext).setVisibility(View.GONE);
                    }
                }
            };

            view.findViewById(R.id.rlImage01).setOnClickListener(clickListener);
            view.findViewById(R.id.rlImage02).setOnClickListener(clickListener);
            view.findViewById(R.id.rlImage03).setOnClickListener(clickListener);
            view.findViewById(R.id.rlImage04).setOnClickListener(clickListener);

            view.findViewById(R.id.fabNext).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Check score
                    boolean isvalid = true;
                    for(int i = 0; i < _selected.length; i++){
                        if(!_exercise.getTiles().get(_content[_selected[i]])._valid){
                            isvalid = false;
                            break;
                        }
                    }

                    _exercise._haspassed = isvalid;
                    _exercise._hasscore = true;

                    _activity.goNext();
                }
            });

            return view;
        }
    }
}
