package be.greifmatthias.toddler.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import be.greifmatthias.toddler.Models.Class;
import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;
import be.greifmatthias.toddler.Theme;

public class ManagerActivity extends Activity {

    private Spinner _spToddlers;
    private LinearLayout _llAddToddlerContainer;
    private EditText _etName;
    private EditText _etFamname;
    private RelativeLayout _rlAddToddlerAccept;

    private PopupWindow _popupWindow;

    private ToddlerAdapter _toddleradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

//        Set theme
        Theme.setStatusbarWhite(getWindow(), true);

//        Get controls
        this._spToddlers = findViewById(R.id.spToddlers);
        this._llAddToddlerContainer = findViewById(R.id.llAddToddlerContainer);
        this._etName = findViewById(R.id.etName);
        this._etFamname = findViewById(R.id.etFamname);
        this._rlAddToddlerAccept = findViewById(R.id.rlAddToddlerAccept);

//        Generate popup
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.popup_manager_addclass,null);

        _popupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        _popupWindow.setFocusable(true);
        _popupWindow.update();

        customView.findViewById(R.id.rlBackground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _popupWindow.dismiss();
            }
        });

        customView.findViewById(R.id.ivAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Save class
                Class.add(new Class(0, ((EditText)customView.findViewById(R.id.etClassName)).getText().toString()));
                ((EditText)customView.findViewById(R.id.etClassName)).setText("");

//                Update
                _toddleradapter.notifyDataSetChanged();

//                Close window
                _popupWindow.dismiss();

//                Check ui
                check();
            }
        });

        //        Toddler toggler
        (findViewById(R.id.llAddToddler)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_llAddToddlerContainer.getVisibility() == View.VISIBLE){
                    toggleToddlerAdded(true);
                }else{
                    toggleToddlerAdded(false);
                }
            }
        });

//        Set input listeners
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!_etName.getText().toString().equals("") && !_etFamname.getText().toString().equals("")){
                    _rlAddToddlerAccept.setVisibility(View.VISIBLE);
                }else{
                    _rlAddToddlerAccept.setVisibility(View.GONE);
                }
            }
        };

        _etName.addTextChangedListener(watcher);
        _etFamname.addTextChangedListener(watcher);

//        Add
        findViewById(R.id.rlAddToddlerAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Save
                ((Class)_spToddlers.getSelectedItem()).addStud(new User(0, _etName.getText().toString(), _etFamname.getText().toString(), true));

//                Reset views
                _etName.setText("");
                _etFamname.setText("");
            }
        });

        findViewById(R.id.fabAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _popupWindow.showAtLocation(findViewById(R.id.rlRoot), Gravity.CENTER,0,0);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //        Setup spinner
        this._spToddlers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                toggleToddlerAdded(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

//        Set adapter
        this._toddleradapter = new ToddlerAdapter(this, R.layout.classes_class_default, R.id.tvName, Class.get());
        this._toddleradapter.setDropDownViewResource(R.layout.classes_class);
        this._spToddlers.setAdapter(this._toddleradapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        check();
    }

    private void check(){
        if(Class.get().size() > 0){
            findViewById(R.id.llManager).setVisibility(View.VISIBLE);
            findViewById(R.id.llNotif).setVisibility(View.GONE);

//            Select latest
            _spToddlers.setSelection(_toddleradapter._classes.size() - 1);
        }else{
            findViewById(R.id.llManager).setVisibility(View.GONE);
            findViewById(R.id.llNotif).setVisibility(View.VISIBLE);
        }
    }

    private void toggleToddlerAdded(boolean close){
        _etFamname.setText("");
        _etName.setText("");

        if(close){
           this. _llAddToddlerContainer.setVisibility(View.GONE);
            ((ImageView)findViewById(R.id.ivAddToddlerCollapseIcon)).setImageResource(R.drawable.ic_round_add);
        }else{
            this._llAddToddlerContainer.setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.ivAddToddlerCollapseIcon)).setImageResource(R.drawable.ic_round_expand_less);
        }
    }

    private class ToddlerAdapter extends ArrayAdapter<Class> {

        private List<Class> _classes;

        public ToddlerAdapter(Context context, int View, int TextView, List<Class> classes) {
            super(context, View, TextView, classes);

            this._classes = classes;
        }

        @Override
        public int getCount(){
            return this._classes.size();
        }

        @Override
        public Class getItem(int position){
            return this._classes.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);

            ((TextView)v.findViewById(R.id.tvName)).setText(this._classes.get(position).getName());

            return v;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v =  super.getDropDownView(position, convertView, parent);
            ((TextView)v.findViewById(R.id.tvName)).setText(this._classes.get(position).getName());

            return v;
        }
    }
}
