package com.task.taskmanager;

public class Util {

    public static boolean validateName(String name) {
        if (name == null || name.trim().equals("")) {
            return false;
        }

        if(name.split(" ").length > 1) {
            return false;
        }

        if(!Character.isLetter(name.charAt(0))) {
            return false;
        }


        for(char c: name.toCharArray()){
            if(!Character.isLetter(c) && !Character.isDigit(c)){
                return false;
            }
        }

        return true;
    }
    
}
