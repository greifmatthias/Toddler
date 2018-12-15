package be.greifmatthias.toddler.Helpers;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TypeHelper {
    public static int getRandom(int min, int max){
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public static int[] shuffleArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();

        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);

            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }

    public static boolean contains(final int[] array, final int key) {
        return ArrayUtils.contains(array, key);
    }

    public static int[] remove(int[] array, int key)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == key)
            {
                int[] copy = new int[array.length-1];
                System.arraycopy(array, 0, copy, 0, i);
                System.arraycopy(array, i+1, copy, i, array.length-i-1);
                return copy;
            }
        }

        return array;
    }

    public static int[] add(int[] array, int key)
    {
        int[] copy = new int[array.length + 1];

        System.arraycopy(array, 0, copy, 0, array.length);
        copy[copy.length - 1] = key;

        return copy;
    }

    public static String convertName(String input){
        input = input.trim();
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
