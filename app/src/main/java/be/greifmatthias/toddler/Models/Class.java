package be.greifmatthias.toddler.Models;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.greifmatthias.toddler.Helpers.DataHelper;

public class Class implements Comparable<Class> {
//    Members
    private int _id;
    private String _name;
    private String _year;

    private int _group;

    private List<User> _studs;

    public Class(int id, String name, String year, int group){
        this._id = id;
        this._name = name;
        this._group = group;
        this._year = year;

        this._studs = new ArrayList<>();
    }

    public Class(String name, String year, int group){
        int max = 0;
        for(Class c : _classes){
            if(max < c.getId()){
                max = c.getId();
            }
        }
        this._id = max + 1;
        this._name = name;
        this._year = year;
        this._group = group;
    }

    public int getId(){
        return this._id;
    }

    public String getName(){
        return this._name;
    }

    public String getYear(){ return this._year; }

    public Group getGroup() { return Group.getGroups().get(this._group); }

    public List<User> getStuds(){
        if(this._studs == null){
            this._studs = new ArrayList<>();
        }

        if(this._studs.size() > 0) {
//        Sort
            Collections.sort(this._studs);
        }

        return this._studs;
    }

    public static int getNextToddlerId(){
        int max = 0;
        for(Class c : get()){
            for(User u : c.getStuds()){
                if(max < u.getId()){
                    max = u.getId();
                }
            }
        }

        return max + 1;
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

        Collections.sort(_classes);

        return _classes;
    }

    public static Class getOfuser(User user){
        get();

        for (Class c : _classes) {
            for(User u : c.getStuds()){
                if(u.equals(user)){
                    return c;
                }
            }
        }

        return null;
    }

//    Private data loader
    private static List<Class> load(){
        Gson gson = new GsonBuilder().create();
        List<Class> output = new ArrayList<>();

        List<String> data = DataHelper.getInstance().read(dataname);
        if(data.size() > 0) {
            for (String s : data) {
//                Convert
                Class entry = gson.fromJson(s, Class.class);

                if(entry._studs == null){
                    entry._studs = new ArrayList<>();
                }

                for(User u : entry.getStuds()){
                    u.clearExercises();
                }

                output.add(entry);
            }
        }

        return output;
    }

//    Public add class
    public static void add(Class entry){
//        Prepare data
        List<Class> classes = get();

        _classes.add(entry);

        entry.save();
    }

//    Public add student
    public void addStud(User user){
        this._studs.add(user);

        save();
    }

    public void removeStud(User user){
        this._studs.remove(user);

        save();
    }

    private void save(){
        Gson gson = new GsonBuilder().create();
        List<String> output = new ArrayList<>();

        for(Class c : _classes){
            output.add(gson.toJson(c));
        }

        DataHelper.getInstance().write(dataname, output);
    }

    @Override
    public int compareTo(@NonNull Class aClass) {
        if(Integer.parseInt(this.getYear().substring(0, 4)) > Integer.parseInt(aClass.getYear().substring(0, 4))){
            return -1;
        }

        if(Integer.parseInt(this.getYear().substring(0, 4)) == Integer.parseInt(aClass.getYear().substring(0, 4))){
            return 0;
        }

        return 1;
    }
}