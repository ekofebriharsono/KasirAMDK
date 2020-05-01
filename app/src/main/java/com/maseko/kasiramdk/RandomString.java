package com.maseko.kasiramdk;

import java.util.Random;

public class RandomString {
    private char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private StringBuilder stringBuilder = new StringBuilder();
    private Random random = new Random();
    private String output;

    public String getRandom() {
        for (int lenght = 0; lenght < 10; lenght++) {
            Character character = chars[random.nextInt(chars.length)];
            stringBuilder.append(character);
        }
        output = stringBuilder.toString();
        stringBuilder.delete(0, 10);

        return output;
    }
}
