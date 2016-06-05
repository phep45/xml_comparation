package com.luxoft.xmlcomparator;

import org.custommonkey.xmlunit.Difference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.luxoft.xmlcomparator.XmlMatcherTypeConstants.ACTUAL_VALUE;
import static com.luxoft.xmlcomparator.XmlMatcherTypeConstants.APOSTROPHE;
import static com.luxoft.xmlcomparator.XmlMatcherTypeConstants.FOUND_VALUE;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public enum XmlMatcherType {
    ANY_OF {
        @Override
        public boolean matches(Difference difference, String xpathRegex){
            Pattern pattern = Pattern.compile(xpathRegex);
            Matcher matcher = pattern.matcher(difference.toString());
            if (matcher.find()) {
                Pattern insidePattern = Pattern.compile(FOUND_VALUE);
                Matcher insideMatcher = insidePattern.matcher(difference.toString());

                List<String> vals = new LinkedList<>();
                while (insideMatcher.find()) {
                    vals.add(insideMatcher.group());
                }
                return !whiteList.contains(vals.get(ACTUAL_VALUE).replaceAll(APOSTROPHE, EMPTY));
            }
            return true;
        }
    },
    ANY {
        @Override
        public boolean matches(Difference difference, String xpathRegex){
            Pattern pattern = Pattern.compile(xpathRegex);
            Matcher matcher = pattern.matcher(difference.toString());
            return !matcher.find();
        }
    },
    NONE_OF {
        @Override
        public boolean matches(Difference difference, String xpathRegex){
            Pattern pattern = Pattern.compile(xpathRegex);
            Matcher matcher = pattern.matcher(difference.toString());
            if (matcher.find()) {
                Pattern insidePattern = Pattern.compile(FOUND_VALUE);
                Matcher insideMatcher = insidePattern.matcher(difference.toString());

                List<String> vals = new LinkedList<>();
                while (insideMatcher.find()) {
                    vals.add(insideMatcher.group());
                }
                return blackList.contains(vals.get(ACTUAL_VALUE).replaceAll(APOSTROPHE, EMPTY));
            }
            return true;
        }
    };

    protected List<String> whiteList = new ArrayList<>();
    protected List<String> blackList = new ArrayList<>();

    public static XmlMatcherType anyOf(String ... vals) {
        XmlMatcherType xmlMatcherType = ANY_OF;
        xmlMatcherType.whiteList.addAll(Arrays.asList(vals));

        return xmlMatcherType;
    }

    public static XmlMatcherType any() {
        return ANY;
    }

    public static XmlMatcherType noneOf(String ... vals) {
        XmlMatcherType xmlMatcherType = NONE_OF;
        xmlMatcherType.blackList.addAll(Arrays.asList(vals));

        return xmlMatcherType;
    }

    public abstract boolean matches(Difference difference, String xpathRegex);

}
