package com.ia.desafioia.util;

public class Converters {
    public static String booleanToUserRole(boolean isAdmin){
        return isAdmin ? "ADMIN" : "USER";
    }
}
