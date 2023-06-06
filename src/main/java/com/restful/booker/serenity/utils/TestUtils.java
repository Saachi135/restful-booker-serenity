package com.restful.booker.serenity.utils;

import java.util.Random;

public class TestUtils {

    public static int getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        return randomInt;
    }
}
