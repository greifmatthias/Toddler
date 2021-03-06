package be.greifmatthias.toddler.Models;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.greifmatthias.toddler.Exercises.AdaptiveExercise;
import be.greifmatthias.toddler.Exercises.SectionExercise;
import be.greifmatthias.toddler.Exercises.SentenceExercise;
import be.greifmatthias.toddler.Exercises.SpeakingExercise;
import be.greifmatthias.toddler.Helpers.DataHelper;
import be.greifmatthias.toddler.Exercises.Exercise;
import be.greifmatthias.toddler.Exercises.ExerciseGroup;
import be.greifmatthias.toddler.Exercises.IntroExercise;
import be.greifmatthias.toddler.Exercises.ListenExercise;
import be.greifmatthias.toddler.Exercises.SortingExercise;

public class User implements Comparable<User> {
    private int _id;
    private String _name;
    private String _famname;

    private List<ExerciseGroup> _exercises;

    public User(int id, String name, String famname){
        this._id = id;
        this._name = name;
        this._famname = famname;
    }

    public User(String name, String famname){
        this(Class.getNextToddlerId(), name, famname);
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

//    Search and delete toddler
    public void delete(){
        List<Class> classes = Class.get();

        Iterator<Class> iter = Class.get().iterator();
        while (iter.hasNext()) {
            Class aClass = iter.next();

            for (Iterator<User> useriter = aClass.getStuds().iterator(); useriter.hasNext(); ) {
                User aUser = useriter.next();

                if(aUser.getId() == this.getId()){
                    aClass.removeStud(aUser);
                    this.deleteData();
                }
            }
        }
    }

    private void deleteData() {
        DataHelper.getInstance().write(this.getDataname(), new ArrayList<String>());
    }

    private String getDataname(){
        return "data_user_exercises_" + this.getId();
    }

    public List<ExerciseGroup> getExercises(){
        if(this._exercises == null){
//            Try to load exercises of stud
            Gson gson = new GsonBuilder().create();
            List<ExerciseGroup> output = new ArrayList<>();

//            Read data
            List<String> data = DataHelper.getInstance().read(this.getDataname());

//            Convert data
            if(data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
//                Convert
                    ExerciseGroup entry = gson.fromJson(data.get(i), ExerciseGroup.class);

                    entry.clearExercises();

//                    Load exercises of group
                    boolean keepgoing = true;
                    while(keepgoing){
                        i = i + 1;

//                        Try to load exercise from json string
                        Exercise etemp = gson.fromJson(data.get(i), Exercise.class);

                        if(etemp.getType() != null) {
                            Exercise e = null;

//                            Convert into right exercise
                            switch (etemp.getType()){
                                case "Intro":
                                    e = gson.fromJson(data.get(i), IntroExercise.class);
                                    break;
                                case "Listen":
                                    e = gson.fromJson(data.get(i), ListenExercise.class);
                                    break;
                                case "Speak":
                                    e = gson.fromJson(data.get(i), SpeakingExercise.class);
                                    break;
                                case "Sentence":
                                    e = gson.fromJson(data.get(i), SentenceExercise.class);
                                    break;
                                case "Sorting":
                                    e = gson.fromJson(data.get(i), SortingExercise.class);
                                    break;
                                case "Section":
                                    e = gson.fromJson(data.get(i), SectionExercise.class);
                                    break;
                                case "Adaptive":
                                    e = gson.fromJson(data.get(i), AdaptiveExercise.class);
                                    break;
                            }

                            entry.addExercise(e);
                        }else{
                            keepgoing = false;
                        }

//                        Last record, end
                        if(i == data.size() - 1){
                            keepgoing = false;
                        }
                    }

//                    No exercises saved, load defaults
                    if(entry.getExercises().size() == 0){
                        entry.loadDefault();
                    }

                    output.add(entry);

                    if(i != data.size() - 1) {
                        i--;
                    }
                }

                this._exercises = output;
            }else{
//                Load default state
                this._exercises = new ArrayList<>();

                Group group = Class.getOfuser(this).getGroup();

                this._exercises.addAll(group.getExercises());
            }
        }

        return this._exercises;
    }

    public void saveExercises(){
        Gson gson = new GsonBuilder().create();
        List<String> output = new ArrayList<>();

        for(ExerciseGroup c : this._exercises){
            output.add(gson.toJson(c));

//            Save exercise of group
            for(Exercise e : c.getExercises()){
                output.add(gson.toJson(e));
            }
        }

        DataHelper.getInstance().write(this.getDataname(), output);
    }

    public boolean isPreteached(){
        for(ExerciseGroup exerciseGroup : this.getExercises()){
            if(!exerciseGroup.isPreteached() && exerciseGroup.getCondition() != Group.Condition.TEST){
                return false;
            }
        }

        return true;
    }

    public boolean isEndteached(){
        for(ExerciseGroup exerciseGroup : this.getExercises()){
            if(!exerciseGroup.isEndteached() && exerciseGroup.getCondition() != Group.Condition.TEST){
                return false;
            }
        }

        return true;
    }

    public boolean allExercised(){
        for(ExerciseGroup exerciseGroup : this.getExercises()){
            if(exerciseGroup.getCondition() != Group.Condition.TEST) {
                for (Exercise exercise : exerciseGroup.getExercises()) {
                    if(!exercise.hasScore()){
                        Log.d("deb", exercise.getType() + "not" + exerciseGroup.getWord());
                        return false;
                    }else{
                        Log.d("deb", exercise.getType() + "yes" + exerciseGroup.getWord());
                    }
                }
            }
        }

        return true;
    }

    @Override
    public int compareTo(@NonNull User user) {
        return this.getFamname().compareTo(user.getFamname());
    }

    public void clearExercises() {
        this._exercises = null;
    }

    public void setExercises(List<ExerciseGroup> exercises) {
        for(ExerciseGroup e : this._exercises){
            for(ExerciseGroup g : exercises){
                if(e.getWord().equals(g.getWord())){
                    e = g;
                }
            }
        }
    }
}