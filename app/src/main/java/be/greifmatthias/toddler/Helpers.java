package be.greifmatthias.toddler;

public class Helpers {
    public static int getRandom(int min, int max){
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}
