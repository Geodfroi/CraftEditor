package ch.azure.aurore.crafteditor.main;

import ch.azure.aurore.javaxt.strings.Strings;

public enum Token {
    EDEN, CRAFT, DUMMY, NONE;

    public static Token fromString(String str) {
        if (Strings.isNullOrEmpty(str))
            return Token.NONE;
        try{
            return Token.valueOf(str);
        }catch (IllegalArgumentException e0){
            return Token.NONE;
        }
    }
}