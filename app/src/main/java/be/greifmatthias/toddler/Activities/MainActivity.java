package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.greifmatthias.toddler.DataHandler;
import be.greifmatthias.toddler.Models.Class;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class MainActivity extends Activity {

    private ExpandableListView _elvStuds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Load theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Init handlers
        DataHandler.getInstance(this);

//        Get controls
        this._elvStuds = findViewById(R.id.elvStuds);

//        Setup click for settings
        findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

//        Setup click for manager
        findViewById(R.id.fabManager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(managerIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Class.get().size() > 0) {
//        Load data
            HashMap<String, List<User>> data = new HashMap<>();
            for (Class c : Class.get()) {
                data.put(c.getName(), c.getStuds());
            }

//        Setup list
            StudsAdapter adapter = new StudsAdapter(this, Class.get(), data);
            this._elvStuds.setAdapter(adapter);

            this._elvStuds.setVisibility(View.VISIBLE);
            findViewById(R.id.llNotif).setVisibility(View.GONE);
        }else{
            this._elvStuds.setVisibility(View.GONE);
            findViewById(R.id.llNotif).setVisibility(View.VISIBLE);
        }
    }

    private class StudsAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<Class> _headers;
        private HashMap<String, List<User>> _data;

        public StudsAdapter(Context context, List<Class> classes, HashMap<String, List<User>> data) {
            this._context = context;
            this._headers = classes;
            this._data = data;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._data.get(this._headers.get(groupPosition).getName()).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            User u = (User) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.studs_stud, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.tvName);

            txtListChild.setText(u.getName() + " " + u.getFamname());
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._data.get(this._headers.get(groupPosition).getName()).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
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
            Class c = (Class)getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.studs_group, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.tvName);
            lblListHeader.setText(c.getName());

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