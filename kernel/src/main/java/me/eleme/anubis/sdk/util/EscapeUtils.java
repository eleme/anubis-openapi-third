package me.eleme.anubis.sdk.util;

import org.apache.commons.text.StringEscapeUtils;

public class EscapeUtils {
    public static String escapeJson(String source){
        return StringEscapeUtils.escapeJson(source);
    }
}
