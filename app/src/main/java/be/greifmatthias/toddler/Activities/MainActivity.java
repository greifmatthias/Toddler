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

import java.util.HashMap;
import java.util.List;

import be.greifmatthias.toddler.Helpers.DataHelper;
import be.greifmatthias.toddler.Models.Class;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Helpers.Theme;

public class MainActivity extends Activity {

    private ExpandableListView _elvStuds;
    private StudsAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Load theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Init handlers
        DataHelper.getInstance(this);

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

//        Setup click for manager (fab)
        findViewById(R.id.fabManager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent managerIntent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(managerIntent);
            }
        });

//        Setup click for toddler list item clicks
        this._elvStuds.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent toddlerIntent = new Intent(getApplicationContext(), ToddlerDetailActivity.class);
                toddlerIntent.putExtra("toddlerId", _adapter.getChild(groupPosition, childPosition).getId());
                startActivity(toddlerIntent);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Check for classes
        if (Class.get().size() > 0) {
//        Load data
            HashMap<String, List<User>> data = new HashMap<>();
            for (Class c : Class.get()) {
                data.put(c.getId() + "", c.getStuds());
            }

//        Setup list
            this._adapter = new StudsAdapter(this, Class.get(), data);
            this._elvStuds.setAdapter(this._adapter);

//            Setup UI
            this._elvStuds.setVisibility(View.VISIBLE);
            findViewById(R.id.llNotif).setVisibility(View.GONE);
        }else{
//            Setup UI
            this._elvStuds.setVisibility(View.GONE);
            findViewById(R.id.llNotif).setVisibility(View.VISIBLE);
        }
    }

    private class StudsAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<Class> _headers;
        private HashMap<String, List<User>> _data;

//        Constructors
        public StudsAdapter(Context context, List<Class> classes, HashMap<String, List<User>> data) {
            this._context = context;
            this._headers = classes;
            this._data = data;
        }

//        For getting Toddler
        @Override
        public User getChild(int groupPosition, int childPosititon) {
            return this._data.get(this._headers.get(groupPosition).getId() + "").get(childPosititon);
        }

//        For gettings id of Toddler
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

//        For generating views for Toddlers
        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

//            Get Toddler
            User u = getChild(groupPosition, childPosition);

//            Build if no view
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.studs_stud, null);
            }

//            Get controls
            TextView txtListChild = convertView.findViewById(R.id.tvName);

//            Set content
            txtListChild.setText(u.getFamname() + " " + u.getName());

            return convertView;
        }

//        Get count of Toddlers for Group
        @Override
        public int getChildrenCount(int groupPosition) {
            return this._data.get(this._headers.get(groupPosition).getId() + "").size();
        }

//        Get Group
        @Override
        public Class getGroup(int groupPosition) {
            return this._headers.get(groupPosition);
        }

//        Get total Groups
        @Override
        public int getGroupCount() {
            return this._headers.size();
        }

//        Get id of Group
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

//        Generate view for Class
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            Get class
            Class c = getGroup(groupPosition);

//            Build if no view
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.studs_group, null);
            }

//            Get controls
            TextView lblListHeader = convertView.findViewById(R.id.tvName);

//            Set content
            lblListHeader.setText(c.getYear() + ": " + c.getName() + " (Groep " + c.getGroup().getId() + ")");

            return convertView;
        }

//        Override stable ids
        @Override
        public boolean hasStableIds() {
            return false;
        }

//        Override child selection enable
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}