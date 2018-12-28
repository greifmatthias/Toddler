package be.greifmatthias.toddler.Models;

import java.util.ArrayList;
import java.util.List;

import be.greifmatthias.toddler.Exercises.ExerciseGroup;

public class Group {
    public enum WordCollection {
        A,
        B,
        C
    }

    public enum Condition {
        TEST,
        A,
        B,
        C
    }

    public class WordSet{
        public WordSet(WordCollection words, Condition condition){
            this.words = words;
            this.condition = condition;
        }

        public WordCollection words;
        public Condition condition;
    }

    private int _group;

    public Group(int group){
        this._group = group;
    }

    public String getName(){
        return getName(this._group);
    }

    public static String getName(int group){
        switch (group){
            case 1:
                return "Groep 2";
            case 2:
                return "Groep 3";
            default:
                return "Groep 1";
        }
    }

    public String getId(){
        return "" + (this._group + 1);
    }

    public List<Condition> getConditions(){
        List<Condition> output = new ArrayList<>();
        switch (this._group){
            case 1:
                output.add(Condition.B);
                output.add(Condition.C);
                output.add(Condition.A);
                break;
            case 2:
                output.add(Condition.C);
                output.add(Condition.A);
                output.add(Condition.B);
                break;
            default:
                output.add(Condition.A);
                output.add(Condition.B);
                output.add(Condition.C);
                break;
        }

        return output;
    }

    public static Condition getCondition(int condition){
        switch (condition){
            case -1:
                return Condition.TEST;
            case 0:
                return Condition.A;
            case 1:
                return Condition.B;
            case 2:
                return Condition.C;
        }

        return null;
    }

    public List<WordSet> getWordSet(){
        List<WordSet> output = new ArrayList<>();

        WordSet set = new WordSet(WordCollection.A, Condition.A);
        WordSet set2 = new WordSet(WordCollection.B, Condition.B);
        WordSet set3 = new WordSet(WordCollection.C, Condition.C);

        switch (this._group){
            case 1:
                set = new WordSet(WordCollection.B, Condition.A);
                set2 = new WordSet(WordCollection.C, Condition.B);
                set3 = new WordSet(WordCollection.A, Condition.C);
                break;
            case 2:
                set = new WordSet(WordCollection.C, Condition.A);
                set2 = new WordSet(WordCollection.A, Condition.B);
                set3 = new WordSet(WordCollection.B, Condition.C);
                break;
        }

        output.add(set);
        output.add(set2);
        output.add(set3);

        return output;
    }

    public static List<Group> getGroups(){
        List<Group> output = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            output.add(new Group(i));
        }

        return output;
    }

    public Condition getCondition_forWordcollection(WordCollection collection){

        for(WordSet set : this.getWordSet()){
            if(set.words == collection){
                return set.condition;
            }
        }

        return null;
    }

    public List<ExerciseGroup> getExercises_ofGroup(WordCollection collection){
        List<ExerciseGroup> groups = new ArrayList<>();

        switch (collection){
            case A:
                groups.add(new ExerciseGroup("Het klimtouw", getCondition_forWordcollection(WordCollection.A)));
                groups.add(new ExerciseGroup("Het kroos", getCondition_forWordcollection(WordCollection.A)));
                groups.add(new ExerciseGroup("Het riet", getCondition_forWordcollection(WordCollection.A)));
                break;
            case B:
                groups.add(new ExerciseGroup("De val", getCondition_forWordcollection(WordCollection.B)));
                groups.add(new ExerciseGroup("Het kompas", getCondition_forWordcollection(WordCollection.B)));
                groups.add(new ExerciseGroup("Steil", getCondition_forWordcollection(WordCollection.B)));
                break;
            case C:
                groups.add(new ExerciseGroup("De zwaan", getCondition_forWordcollection(WordCollection.C)));
                groups.add(new ExerciseGroup("Het kamp", getCondition_forWordcollection(WordCollection.C)));
                groups.add(new ExerciseGroup("De zaklamp", getCondition_forWordcollection(WordCollection.C)));
                break;
        }

        return groups;
    }

    public List<ExerciseGroup> getExercises(){
        List<ExerciseGroup> groups = new ArrayList<>();

        ExerciseGroup testExercise = new ExerciseGroup("De duikbril", Condition.TEST);
        groups.add(testExercise);

        for(WordSet set : this.getWordSet()){
            groups.addAll(getExercises_ofGroup(set.words));
        }

        for (ExerciseGroup group : groups){
            group.loadDefault();
        }

        return groups;
    }
}
