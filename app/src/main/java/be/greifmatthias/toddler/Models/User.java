package be.greifmatthias.toddler.Models;

import java.util.List;

public class User {
    private int _id;
    private String _name;
    private String _famname;
    private boolean _isboy;

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