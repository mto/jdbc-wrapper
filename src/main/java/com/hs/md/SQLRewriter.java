package com.hs.md;

import java.util.regex.Pattern;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/25/17
 */
public class SQLRewriter {

    final Pattern pattern;

    final String replacement;

    public SQLRewriter(String regex, String replacement) {
        this.pattern = Pattern.compile(regex);
        this.replacement = replacement;
    }

    public String getRegex() {
        return pattern.toString();
    }

    public String getReplacement() {
        return replacement;
    }
}
