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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.greifmatthias.toddler.Exercises.Exercise;
import be.greifmatthias.toddler.Exercises.ExerciseGroup;
import be.greifmatthias.toddler.Models.Class;
import be.greifmatthias.toddler.Models.Group;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ToddlerDetailActivity extends Activity {

    private TextView _tvName;

    private ImageView _ivMore;
    private LinearLayout _llNotif;
    private LinearLayout _llExercises;
    private ImageView _ivTest;
    private ExpandableListView _lvExercisegroups;
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

        this._lvExercisegroups.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent exerciseActivity = new Intent(getApplicationContext(), ExerciseActivity.class);
                exerciseActivity.putExtra("toddlerId", _toddler.getId());
                exerciseActivity.putExtra("condition", groupPosition);
                startActivity(exerciseActivity);

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this._tvName.setText(this._toddler.getName() + " " + this._toddler.getFamname());

//        Set exercise group data
        HashMap<String, List<ExerciseGroup>> data = new HashMap<>();
        String pre = null;
        List<ExerciseGroup> groups = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        for (ExerciseGroup group : this._toddler.getExercises()) {
            if (!group.getWord().equals("De duikbril")) {
                if (pre != null && pre.equals(group.getCondition().toString())) {
                    groups.add(group);

//                    Check if last, then add
                    if(group.equals(this._toddler.getExercises().get(this._toddler.getExercises().size() - 1))){
                        data.put(pre, groups);
                    }
                } else {
                    if (pre != null) {
                        data.put(pre, groups);
                    } else {
                        pre = group.getCondition().toString();
                    }

//                New entry
                    groups = new ArrayList<>();
                    groups.add(group);
                    pre = group.getCondition().toString();

                    headers.add(pre);
                }
            }
        }

        GroupsAdapter adapter = new GroupsAdapter(this, headers, data);
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

    private class GroupsAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<String> _headers;
        private HashMap<String, List<ExerciseGroup>> _data;

        public GroupsAdapter(Context context, List<String> headers, HashMap<String, List<ExerciseGroup>> data) {
            this._context = context;
            this._headers = headers;
            this._data = data;
        }

        @Override
        public ExerciseGroup getChild(int groupPosition, int childPosititon) {
            return this._data.get(this._headers.get(groupPosition)).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {

            ExerciseGroup u = (ExerciseGroup) getChild(groupPosition, childPosition);

            if (view == null) {
                view = LayoutInflater.from(this._context).inflate(R.layout.row_exercisegroups, parent, false);
            }

//            Get controls
            ImageView ivState = view.findViewById(R.id.ivState);
            TextView tvWord = view.findViewById(R.id.tvWord);
            LinearLayout llExercises = view.findViewById(R.id.llExercises);

            llExercises.removeAllViews();

//            Set content
            tvWord.setText(u.getWord());

            if(u.isPreteached() && u.isKnown()) {
                ivState.setImageResource(R.drawable.ic_round_done);
            }else{
                ivState.setImageResource(R.drawable.ic_round_close);
            }

            for(Exercise exercise : u.getExercises()){
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


        @Override
        public int getChildrenCount(int groupPosition) {
            return this._data.get(this._headers.get(groupPosition)).size();
        }

        @Override
        public String getGroup(int groupPosition) {
            return this._headers.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._headers.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String c = getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.studs_group, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.tvName);
            lblListHeader.setText(c);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
