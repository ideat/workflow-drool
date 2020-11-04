package com.mindware.workflow.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatching {
    private static Pattern cellNumberPattern = Pattern.compile("(6|7)\\d{7}");

    public static boolean validateCellNumber(String cellNumber){
        Matcher matcher = cellNumberPattern.matcher(cellNumber);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
