package be.greifmatthias.toddler.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import be.greifmatthias.toddler.Models.Class;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ManagerActivity extends Activity {

    private Spinner _spClasses;
    private ClassAdapter _classadapter;
    private ListView _lvToddlers;
    private ToddlerAdapter _toddleradapter;

    private PopupWindow _addpopup;
    private PopupWindow _classpopup;
    private PopupWindow _toddlerpopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Get controls
        this._spClasses = findViewById(R.id.spClasses);
        this._lvToddlers = findViewById(R.id.lvToddlers);

//        Show popups
        setupAddPopup();
        setupClassPopup();
        setupToddlerPopup();
        findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _addpopup.showAtLocation(findViewById(R.id.rlRoot), Gravity.CENTER, 0, 0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        Setup spinner
        this._spClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Load toddlers
                reloadToddlers(_classadapter._classes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

//        Set adapter
        this._classadapter = new ClassAdapter(this, R.layout.classes_class_default, R.id.tvName, Class.get());
        this._classadapter.setDropDownViewResource(R.layout.classes_class);
        this._spClasses.setAdapter(this._classadapter);

//            Select latest
        _spClasses.setSelection(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        check();
    }

    @Override
    public void onBackPressed() {
//        Check if opened popups
        if(this._classpopup.isShowing() || this._toddlerpopup.isShowing()){
            this._classpopup.dismiss();
            this._toddlerpopup.dismiss();
        }else{
            super.onBackPressed();
        }
    }

    private void check() {
        if (Class.get().size() > 0) {
            findViewById(R.id.llNotif).setVisibility(View.GONE);
        } else {
            findViewById(R.id.llNotif).setVisibility(View.VISIBLE);
        }
    }

    private void reloadToddlers(Class c){
        if(c != null) {
            _toddleradapter = new ToddlerAdapter(getApplicationContext(), R.layout.studs_stud, R.id.tvName, c.getStuds());

            if (c.getStuds().size() > 0) {
                _lvToddlers.setAdapter(_toddleradapter);

                findViewById(R.id.llNotifToddlers).setVisibility(View.GONE);
            } else {
                _lvToddlers.setAdapter(null);

                findViewById(R.id.llNotifToddlers).setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupAddPopup() {
//        Generate popup
        LayoutInflater inflater = this.getLayoutInflater();
        final View customView = inflater.inflate(R.layout.popup_manager_add, null);

        this._addpopup = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        this._addpopup.setFocusable(true);
        this._addpopup.update();

        customView.findViewById(R.id.llAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _addpopup.dismiss();

                _classpopup.showAtLocation(findViewById(R.id.rlRoot), Gravity.CENTER, 0, 0);
            }
        });

        customView.findViewById(R.id.llAddToddler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _addpopup.dismiss();

                _toddlerpopup.showAtLocation(findViewById(R.id.rlRoot), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void setupClassPopup(){
//        Generate popup
        LayoutInflater inflater = this.getLayoutInflater();
        final View customView = inflater.inflate(R.layout.popup_manager_addclass, null);

        _classpopup = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        _classpopup.setFocusable(true);
        _classpopup.update();

        customView.findViewById(R.id.rlBackground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _classpopup.dismiss();
            }
        });

        customView.findViewById(R.id.fabAddClassAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Save class
                String classname = ((EditText) customView.findViewById(R.id.etClassName)).getText().toString();
                if (!classname.equals("")) {
                    Class.add(new Class(classname));
                    ((EditText) customView.findViewById(R.id.etClassName)).setText("");

//                Update
                    _classadapter.notifyDataSetChanged();

//                Close window
                    _classpopup.dismiss();

//                Check ui
                    check();

//                    Select latest
                    _spClasses.setSelection(0);

                    reloadToddlers(Class.get().get(0));
                }
            }
        });
    }

    private void setupToddlerPopup(){
//        Generate popup
        LayoutInflater inflater = this.getLayoutInflater();
        final View customView = inflater.inflate(R.layout.popup_manager_addtoddler, null);

        this._toddlerpopup = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        this._toddlerpopup.setFocusable(true);
        this._toddlerpopup.update();

//        Get Controls
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etFamname = customView.findViewById(R.id.etFamname);
        final View fabAddToddlerAccept = customView.findViewById(R.id.fabAddToddlerAccept);

//       Set input listeners
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etName.getText().toString().equals("") && !etFamname.getText().toString().equals("")) {
                    fabAddToddlerAccept.setVisibility(View.VISIBLE);
                } else {
                    fabAddToddlerAccept.setVisibility(View.GONE);
                }
            }
        };
        etName.addTextChangedListener(watcher);
        etFamname.addTextChangedListener(watcher);

        customView.findViewById(R.id.rlBackground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _toddlerpopup.dismiss();
            }
        });

        customView.findViewById(R.id.fabAddToddlerAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Save toddler
                Class c = ((Class) _spClasses.getSelectedItem());
                c.addStud(new User(Class.getNextToddlerId(), etName.getText().toString(), etFamname.getText().toString(), true));

//                Reset views
                etName.setText("");
                etFamname.setText("");

                reloadToddlers(c);

//                Close window
                _classpopup.dismiss();

//                Check ui
                check();
            }
        });
    }

    private class ClassAdapter extends ArrayAdapter<Class> {

        private List<Class> _classes;

        public ClassAdapter(Context context, int View, int TextView, List<Class> classes) {
            super(context, View, TextView, classes);

            this._classes = classes;
        }

        @Override
        public int getCount() {
            return this._classes.size();
        }

        @Override
        public Class getItem(int position) {
            return this._classes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            ((TextView) v.findViewById(R.id.tvName)).setText(this._classes.get(position).getName());

            return v;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = super.getDropDownView(position, convertView, parent);
            ((TextView) v.findViewById(R.id.tvName)).setText(this._classes.get(position).getName());

            return v;
        }
    }

    private class ToddlerAdapter extends ArrayAdapter<User> {

        private List<User> _toddlers;

        public ToddlerAdapter(Context context, int View, int TextView, List<User> toddlers) {
            super(context, View, TextView, toddlers);

            this._toddlers = toddlers;
        }

        @Override
        public int getCount() {
            return this._toddlers.size();
        }

        @Override
        public User getItem(int position) {
            return this._toddlers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            ((TextView) v.findViewById(R.id.tvName)).setText(this._toddlers.get(position).getFamname() + " " + this._toddlers.get(position).getName());

            return v;
        }
    }
}
