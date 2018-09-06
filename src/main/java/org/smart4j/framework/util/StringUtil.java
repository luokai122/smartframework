//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.smart4j.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringUtil {
    public static final String SEPARATOR = String.valueOf('\u001d');

    public StringUtil() {
    }

    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static String defaultIfEmpty(String str, String defaultValue) {
        return StringUtils.defaultIfEmpty(str, defaultValue);
    }

    public static String replaceAll(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            m.appendReplacement(sb, replacement);
        }

        m.appendTail(sb);
        return sb.toString();
    }

    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    public static boolean isDigits(String str) {
        return NumberUtils.isDigits(str);
    }

    public static String camelhumpToUnderline(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);

        for(int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
        }

        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }

        return builder.toString();
    }

    public static String underlineToCamelhump(String str) {
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);

        for(int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }

        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }

        return builder.toString();
    }

    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

    public static String firstToUpper(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String firstToLower(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String toPascalStyle(String str, String seperator) {
        return firstToUpper(toCamelhumpStyle(str, seperator));
    }

    public static String toCamelhumpStyle(String str, String seperator) {
        return underlineToCamelhump(toUnderlineStyle(str, seperator));
    }

    public static String toUnderlineStyle(String str, String seperator) {
        str = str.trim().toLowerCase();
        if (str.contains(seperator)) {
            str = str.replace(seperator, "_");
        }

        return str;
    }

    public static String toDisplayStyle(String str, String seperator) {
        String displayName = "";
        str = str.trim().toLowerCase();
        if (str.contains(seperator)) {
            String[] words = splitString(str, seperator);
            String[] arr$ = words;
            int len$ = words.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String word = arr$[i$];
                displayName = displayName + firstToUpper(word) + " ";
            }

            displayName = displayName.trim();
        } else {
            displayName = firstToUpper(str);
        }

        return displayName;
    }
}
