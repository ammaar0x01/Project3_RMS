package com.restaurant.rms.utilities;

public class Tools {
    // general tools

    public static String capitalize(String str){
        char firstChar = str.charAt(0);
        String mostOfTheWord = str.substring(1);
        return String.valueOf(firstChar).toUpperCase() + mostOfTheWord;
    }

    public static String capitalize1(String str){
        char firstChar = str.charAt(0);
        String mostOfTheWord = str.toLowerCase().substring(1, str.length());
        return String.valueOf(firstChar).toUpperCase() + mostOfTheWord;
    }

    // testing
//    public static void main(String[] args){
//        String str = Tools.capitalize("wORd");
//        System.out.println(str);
//
//        String str1 = Tools.capitalize1("wORd");
//        System.out.println(str1);
//    }
}
