package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
    public int getIcon() {
        if(this._haspassed){
            return R.drawable.ic_round_done;
        }

        return R.drawable.ic_round_close;
    }

    @Override
    public String getName() {
        return "Vakjesoefening";
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new SectionFragment(this, activity);
    }

    public String getKaatje(){
        return this._word + ". Hoe ziet " + this._word + " eruit? Weet jij op welke prentjes " + this._word + " staat? Zet deze in het groene vak.";
    }

    public List<Tile> getTiles(){
        List<Tile> tiles = new ArrayList<>();

        switch (this._word){
            case "De duikbril":
                tiles.add(new Tile(R.drawable.duikbril_01, true));
                tiles.add(new Tile(R.drawable.duikbril_02, true));
                tiles.add(new Tile(R.drawable.duikbril_03, true));
                tiles.add(new Tile(R.drawable.duikbril_false, false));
                break;
            case "Het klimtouw":
                tiles.add(new Tile(R.drawable.klimtouw_01, true));
                tiles.add(new Tile(R.drawable.klimtouw_02, true));
                tiles.add(new Tile(R.drawable.klimtouw_03, true));
                tiles.add(new Tile(R.drawable.klimtouw_false, false));
                break;
            case "Het kroos":
                tiles.add(new Tile(R.drawable.kroos_01, true));
                tiles.add(new Tile(R.drawable.kroos_02, true));
                tiles.add(new Tile(R.drawable.kroos_03, false));
                tiles.add(new Tile(R.drawable.kroos_false, false));
                break;
            case "Het riet":
                tiles.add(new Tile(R.drawable.riet_01, true));
                tiles.add(new Tile(R.drawable.riet_02, true));
                tiles.add(new Tile(R.drawable.riet_03, true));
                tiles.add(new Tile(R.drawable.riet_false, false));
                break;
            case "De val":
                tiles.add(new Tile(R.drawable.val_01, true));
                tiles.add(new Tile(R.drawable.val_02, true));
                tiles.add(new Tile(R.drawable.val_03, true));
                tiles.add(new Tile(R.drawable.val_false, false));
                break;
            case "Het kompas":
                tiles.add(new Tile(R.drawable.kompas_01, true));
                tiles.add(new Tile(R.drawable.kompas_02, true));
                tiles.add(new Tile(R.drawable.kompas_03, true));
                tiles.add(new Tile(R.drawable.kompas_false, false));
                break;
            case "Steil":
                tiles.add(new Tile(R.drawable.steil_01, true));
                tiles.add(new Tile(R.drawable.steil_02, true));
                tiles.add(new Tile(R.drawable.steil_03, true));
                tiles.add(new Tile(R.drawable.steil_false, false));
                break;
            case "De zwaan":
                tiles.add(new Tile(R.drawable.zwaan_01, true));
                tiles.add(new Tile(R.drawable.zwaan_02, true));
                tiles.add(new Tile(R.drawable.zwaan_03, true));
                tiles.add(new Tile(R.drawable.zwaan_false, false));
                break;
            case "Het kamp":
                tiles.add(new Tile(R.drawable.kamp_01, true));
                tiles.add(new Tile(R.drawable.kamp_02, true));
                tiles.add(new Tile(R.drawable.kamp_03, true));
                tiles.add(new Tile(R.drawable.kamp_false, false));
                break;
            case "De zaklamp":
                tiles.add(new Tile(R.drawable.zaklamp_01, true));
                tiles.add(new Tile(R.drawable.zaklamp_02, true));
                tiles.add(new Tile(R.drawable.zaklamp_03, true));
                tiles.add(new Tile(R.drawable.zaklamp_false, false));
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

        private boolean _iscorrect;

        public SectionFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public SectionFragment(SectionExercise exercise, ExerciseActivity activity){
            this._iscorrect = true;

            this._exercise = exercise;
            this._activity = activity;

            this._activity.setFullScreen(true);

            this._activity.setKaatje(this._exercise.getKaatje());
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
                            case R.id.ivImage01:
                                val = 0;
                                break;
                            case R.id.ivImage02:
                                val = 1;
                                break;
                            case R.id.ivImage03:
                                val = 2;
                                break;
                            case R.id.ivImage04:
                                val = 3;
                                break;
                        }

                        _currentselection = val;

                        if(view.findViewById(R.id.ivImage01).getVisibility() != View.INVISIBLE) {
                            view.findViewById(R.id.rlImage01_check).setVisibility(View.VISIBLE);
                        }
                        if(view.findViewById(R.id.ivImage02).getVisibility() != View.INVISIBLE) {
                            view.findViewById(R.id.rlImage02_check).setVisibility(View.VISIBLE);
                        }
                        if(view.findViewById(R.id.ivImage03).getVisibility() != View.INVISIBLE) {
                            view.findViewById(R.id.rlImage03_check).setVisibility(View.VISIBLE);
                        }
                        if(view.findViewById(R.id.ivImage04).getVisibility() != View.INVISIBLE) {
                            view.findViewById(R.id.rlImage04_check).setVisibility(View.VISIBLE);
                        }


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

            view.findViewById(R.id.ivImage01).setOnClickListener(clickListener);
            view.findViewById(R.id.ivImage02).setOnClickListener(clickListener);
            view.findViewById(R.id.ivImage03).setOnClickListener(clickListener);
            view.findViewById(R.id.ivImage04).setOnClickListener(clickListener);

            View.OnClickListener sectionclicklistener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_currentselection >= 0) {
//                        Get selected
                        boolean setcorrect = false;

                        switch (v.getId()) {
                            case R.id.llSectionYes:
                                setcorrect = true;
                                break;
                            case R.id.llSectionNo:
                                setcorrect = false;
                                break;
                        }

//                        Update result
                        _iscorrect = _iscorrect && (_exercise.getTiles().get(_content[_currentselection]).isValid() == setcorrect);

                        Log.d("select", _exercise.getTiles().get(_content[_currentselection]).isValid() + "");
                        Log.d("current", _iscorrect + "");

//                        Revert UI
                        view.findViewById(R.id.rlImage01_check).setVisibility(View.GONE);
                        view.findViewById(R.id.rlImage02_check).setVisibility(View.GONE);
                        view.findViewById(R.id.rlImage03_check).setVisibility(View.GONE);
                        view.findViewById(R.id.rlImage04_check).setVisibility(View.GONE);

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

//                        Check if last
                        if(view.findViewById(R.id.ivImage01).getVisibility() == View.INVISIBLE && view.findViewById(R.id.ivImage02).getVisibility() == View.INVISIBLE && view.findViewById(R.id.ivImage03).getVisibility() == View.INVISIBLE && view.findViewById(R.id.ivImage04).getVisibility() == View.INVISIBLE) {
//                            Set score
                            _exercise._haspassed = _iscorrect;
                            _exercise._hasscore = true;

//                            Next exercise
                            _activity.goNext();
                        }else{
//                            Revert selection
                            _currentselection = -1;
                        }
                    }
                }
            };

            view.findViewById(R.id.llSectionYes).setOnClickListener(sectionclicklistener);
            view.findViewById(R.id.llSectionNo).setOnClickListener(sectionclicklistener);

            return view;
        }
    }
}