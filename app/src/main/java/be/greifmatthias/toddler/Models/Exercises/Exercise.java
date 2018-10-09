package be.greifmatthias.toddler.Models.Exercises;

public class Exercise {
    private String _title;
    private String _word;

    public int getView(){
        return -1;
    }

    public boolean isCorrect() {
        return false;
    }

    public boolean hasCorrection(){
        return true;
    }
}
