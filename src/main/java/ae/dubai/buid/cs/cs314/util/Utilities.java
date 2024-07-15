package ae.dubai.buid.cs.cs314.util;

import java.util.ArrayList;
import java.util.BitSet;  // Import BitSet
import java.util.List;
import java.util.Random;

public class Utilities {
    public static List<String> generateRandomStrings(int count) {
        List<String> randomStrings = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int length = random.nextInt(10) + 1; // Random string length between 1 and 10
            String randomString = generateRandomString(length);
            randomStrings.add(randomString);
        }

        return randomStrings;
    }

    public static List<Integer> generateRandomIntegers(int count) {
        List<Integer> randomIntegers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            randomIntegers.add(random.nextInt());
        }

        return randomIntegers;
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
