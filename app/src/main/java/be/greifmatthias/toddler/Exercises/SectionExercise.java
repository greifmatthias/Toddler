package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Helpers.TypeHelper;
import be.greifmatthias.toddler.R;

public class SectionExercise extends Exercise {

    public SectionExercise(String word) {
        super(word);

        this._type = "Section";
    }

    @Override
    public String getName() {
        return "Vakjesoefening";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SectionFragment(this, activity);
    }

    public List<Tile> getTiles(){
        List<Tile> tiles = new ArrayList<>();

        switch (this._word){
            case "De duikbril":
                tiles.add(new Tile(R.drawable.ogen, true));
                tiles.add(new Tile(R.drawable.strand, true));
                tiles.add(new Tile(R.drawable.zwemmen, true));
                tiles.add(new Tile(R.drawable.schrijven, false));
                break;
            case "Het klimtouw":
                tiles.add(new Tile(R.drawable.klimmen, true));
                tiles.add(new Tile(R.drawable.sterk, true));
                tiles.add(new Tile(R.drawable.turnzaal, true));
                tiles.add(new Tile(R.drawable.zwembad, false));
                break;
            case "Het kroos":
                tiles.add(new Tile(R.drawable.groen, true));
                tiles.add(new Tile(R.drawable.vijver, true));
                tiles.add(new Tile(R.drawable.lamp, false));
                tiles.add(new Tile(R.drawable.eend, false));
                break;
            case "Het riet":
                tiles.add(new Tile(R.drawable.vijver, true));
                tiles.add(new Tile(R.drawable.eend, true));
                tiles.add(new Tile(R.drawable.bos, true));
                tiles.add(new Tile(R.drawable.bril, false));
                break;
            case "De val":
                tiles.add(new Tile(R.drawable.pijn, true));
                tiles.add(new Tile(R.drawable.naarvoor, true));
                tiles.add(new Tile(R.drawable.pleister, true));
                tiles.add(new Tile(R.drawable.appel, false));
                break;
            case "Het kompas":
                tiles.add(new Tile(R.drawable.wandelen, true));
                tiles.add(new Tile(R.drawable.rugzak, true));
                tiles.add(new Tile(R.drawable.landkaart, true));
                tiles.add(new Tile(R.drawable.bad, false));
                break;
            case "Steil":
                tiles.add(new Tile(R.drawable.berg, true));
                tiles.add(new Tile(R.drawable.klimmen, true));
                tiles.add(new Tile(R.drawable.trap, true));
                tiles.add(new Tile(R.drawable.bloem, false));
                break;
            case "De zwaan":
                tiles.add(new Tile(R.drawable.vijver, true));
                tiles.add(new Tile(R.drawable.vleugels, true));
                tiles.add(new Tile(R.drawable.wit, true));
                tiles.add(new Tile(R.drawable.boek, false));
                break;
            case "Het kamp":
                tiles.add(new Tile(R.drawable.kamp, true));
                tiles.add(new Tile(R.drawable.kampvuur, true));
                tiles.add(new Tile(R.drawable.slaapzak, true));
                tiles.add(new Tile(R.drawable.deur, false));
                break;
            case "De zaklamp":
                tiles.add(new Tile(R.drawable.licht, true));
                tiles.add(new Tile(R.drawable.batterij, true));
                tiles.add(new Tile(R.drawable.donker, true));
                tiles.add(new Tile(R.drawable.paard, false));
                break;
        }

        return tiles;
    }

    public class Tile{
        private int _image;
        private boolean _valid;

        public Tile(int image, boolean valid){
            this._image = image;
            this._valid = valid;
        }

        public int getImage(){
            return this._image;
        }

        public boolean isValid(){
            return this._valid;
        }
    }

    public static class SectionFragment extends Fragment {
        private ExerciseActivity _activity;
        private SectionExercise _exercise;
        private int[] _content;
        private int _currentselection = -1;

        public SectionFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SectionFragment(SectionExercise exercise, ExerciseActivity activity){
            this._exercise = exercise;
            this._activity = activity;

            this._activity.setFullScreen(true);

            this._activity.setKaatje("Hallo ik ben Kaat. Ik ga vandaag samen met mijn vriendjes naar het bos, op groot avontuur. Klik maar op de bomen als je wilt starten.");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view = inflater.inflate(R.layout.fragment_section_exercise, container, false);

            this._content = TypeHelper.shuffleArray(new int[]{ 0, 1, 2, 3 });

            // Set exercise images
            ((ImageView)view.findViewById(R.id.ivImage01)).setImageResource(this._exercise.getTiles().get(this._content[0]).getImage());
            ((ImageView)view.findViewById(R.id.ivImage02)).setImageResource(this._exercise.getTiles().get(this._content[1]).getImage());
            ((ImageView)view.findViewById(R.id.ivImage03)).setImageResource(this._exercise.getTiles().get(this._content[2]).getImage());
            ((ImageView)view.findViewById(R.id.ivImage04)).setImageResource(this._exercise.getTiles().get(this._content[3]).getImage());

//            Set click handlers
            final View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_currentselection == -1) {
//                    Get val
                        int val = 0;
                        switch (v.getId()) {
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

                        _currentselection = val;

                        view.findViewById(R.id.rlImage01_check).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.rlImage02_check).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.rlImage03_check).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.rlImage04_check).setVisibility(View.VISIBLE);


//                        Set selection
                        switch (val) {
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
                    }
                }
            };

            view.findViewById(R.id.rlImage01).setOnClickListener(clickListener);
            view.findViewById(R.id.rlImage02).setOnClickListener(clickListener);
            view.findViewById(R.id.rlImage03).setOnClickListener(clickListener);
            view.findViewById(R.id.rlImage04).setOnClickListener(clickListener);

            View.OnClickListener sectionclicklistener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_currentselection >= 0) {
                        switch (v.getId()) {
                            case R.id.llSectionYes:

                                break;
                            case R.id.llSectionNo:

                                break;
                        }

//                        Hide inserted image
                        switch (_currentselection) {
                            case 0:
                                view.findViewById(R.id.ivImage01).setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                view.findViewById(R.id.ivImage02).setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                view.findViewById(R.id.ivImage03).setVisibility(View.INVISIBLE);
                                break;
                            case 3:
                                view.findViewById(R.id.ivImage04).setVisibility(View.INVISIBLE);
                                break;
                        }

//                        Revert UI
                        view.findViewById(R.id.rlImage01_check).setVisibility(View.GONE);
                        view.findViewById(R.id.rlImage02_check).setVisibility(View.GONE);
                        view.findViewById(R.id.rlImage03_check).setVisibility(View.GONE);
                        view.findViewById(R.id.rlImage04_check).setVisibility(View.GONE);

                        _currentselection = -1;
                    }
                }
            };

            view.findViewById(R.id.llSectionYes).setOnClickListener(sectionclicklistener);
            view.findViewById(R.id.llSectionNo).setOnClickListener(sectionclicklistener);

            return view;
        }
    }
}