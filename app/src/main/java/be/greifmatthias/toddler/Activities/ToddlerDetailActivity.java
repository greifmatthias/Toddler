package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import be.greifmatthias.toddler.Exercises.Exercise;
import be.greifmatthias.toddler.Exercises.ExerciseGroup;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ToddlerDetailActivity extends Activity {

    private TextView _tvName;

    private ImageView _ivMore;
    private LinearLayout _llNotif;
    private LinearLayout _llExercises;
    private ImageView _ivTest;
    private ListView _lvExercisegroups;
    private View _llActions;
    private View _rlOverlay;

    private User _toddler;

    private List<ExerciseGroup> _groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toddler_detail);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

        this._toddler = User.get(this.getIntent().getIntExtra("toddlerId", 0));

//        Get controls
        this._tvName = findViewById(R.id.tvName);
        this._ivMore = findViewById(R.id.ivMore);
        this._llNotif = findViewById(R.id.llNotif);
        this._llExercises = findViewById(R.id.llExercises);
        this._ivTest = findViewById(R.id.ivTest);
        this._lvExercisegroups = findViewById(R.id.lvExercisegroups);
        this._llActions = findViewById(R.id.llActions);
        this._rlOverlay = findViewById(R.id.rlOverlay);

//        Toggle actions
        this._ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleActions(_llActions.getVisibility() == View.VISIBLE);
            }
        });

//        Actions
        findViewById(R.id.llDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _toddler.delete();
                finish();
            }
        });

        findViewById(R.id.llStartTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseActivity = new Intent(getApplicationContext(), ExerciseActivity.class);
                exerciseActivity.putExtra("toddlerId", _toddler.getId());
                exerciseActivity.putExtra("group", 0);
                startActivity(exerciseActivity);
            }
        });

        findViewById(R.id.fabUtil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_llNotif.getVisibility() == View.VISIBLE){
//                    Start preteaching
                    Intent preteachIntent = new Intent(getApplicationContext(), PreteachingActivity.class);
                    preteachIntent.putExtra("toddlerId", _toddler.getId());
                    startActivity(preteachIntent);
                }
            }
        });

        //        Load exercisedata
        this._groups = this._toddler.getExercises();

        this._lvExercisegroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent exerciseActivity = new Intent(getApplicationContext(), ExerciseActivity.class);
                exerciseActivity.putExtra("toddlerId", _toddler.getId());
                exerciseActivity.putExtra("group", i + 1);
                startActivity(exerciseActivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this._tvName.setText(this._toddler.getName() + " " + this._toddler.getFamname());

//        Set exercise group data
        GroupsAdapter adapter = new GroupsAdapter(this, this._groups);
        this._lvExercisegroups.setAdapter(adapter);

//        Check if test done
        if(this._toddler.getExercises().get(0).isKnown()){
            _ivTest.setImageResource(R.drawable.ic_round_assignment_turned_in);
        }else{
            _ivTest.setImageResource(R.drawable.ic_round_assignment);
        }

//        Check if already preteached words
        if(!this._toddler.getExercises().get(1).isPreteached()){
            this._llNotif.setVisibility(View.VISIBLE);
            this._llExercises.setVisibility(View.GONE);

//            Enable fab
            findViewById(R.id.fabUtil).setVisibility(View.VISIBLE);
        }else{
            this._llNotif.setVisibility(View.GONE);
            this._llExercises.setVisibility(View.VISIBLE);
            findViewById(R.id.fabUtil).setVisibility(View.GONE);
        }
    }

    private void toggleActions(boolean close){
        if(close){
            this._llActions.setVisibility(View.GONE);
            this._rlOverlay.setVisibility(View.GONE);
            this._ivMore.setImageResource(R.drawable.ic_round_more_vert);
        }else{
            this._llActions.setVisibility(View.VISIBLE);
            this._rlOverlay.setVisibility(View.VISIBLE);
            this._ivMore.setImageResource(R.drawable.ic_round_close);
        }
    }

    private class GroupsAdapter extends BaseAdapter {

        private List<ExerciseGroup> _groups;
        private Context _context;

        private int _modificationfactor;

        public GroupsAdapter(Context context, List<ExerciseGroup> groups){
            this._context = context;

            this._groups = groups;
            this._modificationfactor = 1;
        }

        @Override
        public int getCount() {
            return this._groups.size() - 1;
        }

        @Override
        public ExerciseGroup getItem(int position) {
            return this._groups.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(this._context).inflate(R.layout.row_exercisegroups, parent, false);
            }

//            Get controls
            ImageView ivState = view.findViewById(R.id.ivState);
            TextView tvWord = view.findViewById(R.id.tvWord);
            LinearLayout llExercises = view.findViewById(R.id.llExercises);

            llExercises.removeAllViews();

//            Set content
            tvWord.setText(this._groups.get(position + _modificationfactor).getWord());

            if(this._groups.get(position + _modificationfactor).isPreteached() && this._groups.get(position + _modificationfactor).isKnown()) {
                ivState.setImageResource(R.drawable.ic_round_done);
            }else{
                ivState.setImageResource(R.drawable.ic_round_close);
            }

            for(Exercise exercise : this._groups.get(position + _modificationfactor).getExercises()){
                llExercises.addView(getRow(exercise));
            }

            return view;
        }

        private View getRow(Exercise exercise){
            View view = getLayoutInflater().inflate(R.layout.row_exercise, null);

//            Get controls
            TextView tvWord = view.findViewById(R.id.tvWord);
            ImageView ivResult = view.findViewById(R.id.ivResult);

//            Set content
            tvWord.setText(exercise.getName());

            ivResult.setVisibility(View.VISIBLE);
            if(!exercise.hasScore()){
                ivResult.setVisibility(View.GONE);
            }

            return view;
        }
    }
}
