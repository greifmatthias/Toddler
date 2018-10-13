package be.greifmatthias.toddler.Models;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class User implements Comparable<User> {
    private int _id;
    private String _name;
    private String _famname;
    private boolean _isboy;

    public User(int id, String name, String famname, boolean isboy){
        this._id = id;
        this._name = name;
        this._famname = famname;
        this._isboy = isboy;
    }

    public User(String name, String famname, boolean isboy){
        this(Class.getNextToddlerId(), name, famname, isboy);
    }

    public int getId(){
        return this._id;
    }

    public String getName(){
        return this._name;
    }

    public String getFamname(){
        return this._famname;
    }

    public boolean isBoy(){
        return this._isboy;
    }

    public static User get(int id){
        List<Class> classes = Class.get();

        for(Class c : classes){
            for(User u : c.getStuds()){
                if(u.getId() == id){
                    return u;
                }
            }
        }

        return null;
    }

    public void delete(){
        List<Class> classes = Class.get();

        for(Class c : classes){
            for(User u : c.getStuds()){
                if(u.getId() == this.getId()){
                    c.getStuds().remove(u);
                }
            }
        }
    }

    @Override
    public int compareTo(@NonNull User user) {
        return this.getFamname().compareTo(user.getFamname());
    }
}