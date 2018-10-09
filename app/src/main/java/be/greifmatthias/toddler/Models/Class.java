package be.greifmatthias.toddler.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.greifmatthias.toddler.DataHandler;

public class Class {
//    Members
    private int _id;
    private int _name;
    private List<Group> groups;

//
    private static final String dataname = "data_classes";

//    Caching
    private static List<Class> _classes;

//    Public get all classes
    public List<Class> get(){
        if(_classes == null){
            _classes = load();

            if(_classes == null){
                _classes = new ArrayList<>();
            }
        }

        return _classes;
    }

//    Private data loader
    private List<Class> load(){
        Gson gson = new GsonBuilder().create();
        List<Class> output = new ArrayList<>();

        List<String> data = DataHandler.getInstance().read(dataname);
        if(data.size() > 0) {
            for (String s : data) {
//                Convert
                Class entry = gson.fromJson(s, Class.class);

                output.add(entry);
            }
        }

        return output;
    }

//    Public add class + save
    public void add(Class entry){
        Gson gson = new GsonBuilder().create();

//        Prepare data
        List<Class> classes = get();

        _classes.add(entry);

        List<String> output = new ArrayList<>();
        for(Class c : _classes){
            output.add(gson.toJson(c));
        }

        DataHandler.getInstance().write(dataname, output);
    }
}