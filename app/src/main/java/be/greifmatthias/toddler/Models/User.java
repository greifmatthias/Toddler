package be.greifmatthias.toddler.Models;

import java.util.List;

public class User {
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
}