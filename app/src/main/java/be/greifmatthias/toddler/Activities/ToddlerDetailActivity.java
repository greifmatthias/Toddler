package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Exercises.Exercise;
import be.greifmatthias.toddler.Exercises.ExerciseGroup;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ToddlerDetailActivity extends Activity {

    private TextView _tvName;

    private ImageView _ivMore;
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


        findViewById(R.id.fabManager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _toddler.saveExercises();
            }
        });

        //        Load exercisedata
        this._groups = this._toddler.getExercises();

        this._lvExercisegroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent exerciseActivity = new Intent(getApplicationContext(), ExerciseActivity.class);
                exerciseActivity.putExtra("toddlerId", _toddler.getId());
                exerciseActivity.putExtra("group", i);
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

        public GroupsAdapter(Context context, List<ExerciseGroup> groups){
            this._context = context;
            this._groups = groups;
        }

        @Override
        public int getCount() {
            return this._groups.size();
        }

        @Override
        public ExerciseGroup getItem(int position) {
            return this._groups.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(this._context).inflate(R.layout.row_exercisegroups, parent, false);
            }

//            Get controls
            TextView tvWord = view.findViewById(R.id.tvWord);
            LinearLayout llExercises = view.findViewById(R.id.llExercises);

            llExercises.removeAllViews();

//            Set content
            tvWord.setText(getItem(position).getWord());

            for(Exercise exercise : getItem(position).getExercises()){
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
