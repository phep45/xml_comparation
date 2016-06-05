package com.luxoft.xmlcomparator;

import org.custommonkey.xmlunit.Difference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum XmlMatcherType {
    ANY_OF {
        @Override
        public boolean filter(Difference difference, String xpathRegex){
            Pattern pattern = Pattern.compile(xpathRegex);
            Matcher matcher = pattern.matcher(difference.toString());
            if (matcher.find()) {
                Pattern insidePattern = Pattern.compile("'[\\w\\.]*'");
                Matcher insideMatcher = insidePattern.matcher(difference.toString());

                List<String> vals = new LinkedList<>();
                while (insideMatcher.find()) {
                    vals.add(insideMatcher.group());
                }
                if(getWhiteList().contains(vals.get(1).replaceAll("'", ""))) {
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }
    },
    ANY {
        @Override
        public boolean filter(Difference difference, String xpathRegex){
            Pattern pattern = Pattern.compile(xpathRegex);
            Matcher matcher = pattern.matcher(difference.toString());
            if (matcher.find()) {
                return false;
            }
            return true;
        }
    },
    NONE_OF {
        @Override
        public boolean filter(Difference difference, String xpathRegex){
            Pattern pattern = Pattern.compile(xpathRegex);
            Matcher matcher = pattern.matcher(difference.toString());
            if (matcher.find()) {
                Pattern insidePattern = Pattern.compile("'[\\w\\.]*'");
                Matcher insideMatcher = insidePattern.matcher(difference.toString());

                List<String> vals = new LinkedList<>();
                while (insideMatcher.find()) {
                    vals.add(insideMatcher.group());
                }
                if(getBlackList().contains(vals.get(1).replaceAll("'", ""))) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
    };

    private List<String> whiteList = new ArrayList<>();
    private List<String> blackList = new ArrayList<>();

    private boolean everythingAllowed;

    private XmlMatcherType() {
    }

    public static XmlMatcherType anyOf(String ... vals) {
        XmlMatcherType xmlMatcherType = ANY_OF;
        xmlMatcherType.whiteList.addAll(Arrays.asList(vals));

        return xmlMatcherType;
    }

    public static XmlMatcherType any() {
        XmlMatcherType xmlMatcherType = ANY;
        xmlMatcherType.everythingAllowed = true;
        return xmlMatcherType;
    }

    public static XmlMatcherType noneOf(String ... vals) {
        XmlMatcherType xmlMatcherType = NONE_OF;
        xmlMatcherType.blackList.addAll(Arrays.asList(vals));

        return xmlMatcherType;
    }

    public abstract boolean filter(Difference difference, String xpathRegex);

    public List<String> getWhiteList() {
        return whiteList;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public boolean isEverythingAllowed() {
        return everythingAllowed;
    }
}
