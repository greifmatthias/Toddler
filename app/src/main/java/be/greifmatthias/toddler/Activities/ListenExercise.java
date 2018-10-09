package be.greifmatthias.toddler.Activities;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.greifmatthias.toddler.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListenExercise extends Fragment {


    public ListenExercise() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listen_exercise, container, false);
    }

}
