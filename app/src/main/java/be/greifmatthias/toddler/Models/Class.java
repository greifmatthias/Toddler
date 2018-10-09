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
    private String _name;

    private List<User> _studs;

    public Class(int id, String name){
        this._id = id;
        this._name = name;

        this._studs = new ArrayList<>();
    }

    public int getId(){
        return this._id;
    }

    public String getName(){
        return this._name;
    }

//
    private static final String dataname = "data_classes";

//    Caching
    private static List<Class> _classes;

//    Public get all classes
    public static List<Class> get(){
        if(_classes == null){
            _classes = load();

            if(_classes == null){
                _classes = new ArrayList<>();
            }
        }

        _classes.add(new Class(0, "U mama"));
        return _classes;
    }

//    Private data loader
    private static List<Class> load(){
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