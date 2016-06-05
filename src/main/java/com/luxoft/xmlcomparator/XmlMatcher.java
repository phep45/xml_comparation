package com.luxoft.xmlcomparator;


import org.apache.commons.lang3.StringUtils;
import org.custommonkey.xmlunit.Difference;

public class XmlMatcher {

    private static final String SLASH = "/";
    private static final String REGEX_DIGIT_AND_SLASH = "\\\\[\\\\d*\\\\]\\\\/";
    private static final String PREFIX = "\\\\\\[\\\\d\\*\\\\]";
    private static final String OPEN_PARENTHESIS = "\\(";
    private static final String REGEX_OPEN_PARENTHESIS = "\\\\(";
    private static final String CLOSE_PARENTHESIS = "\\)";
    private static final String REGEX_CLOSE_PARENTHESIS = "\\\\)";
    private static final String REGEX_DIGIT = "\\[\\d*\\]";

    private String xpath;
    private XmlMatcherType matcherType;

    public XmlMatcher(String xpath, XmlMatcherType matcherType) {
        this.xpath = xpath;
        this.matcherType = matcherType;
    }

    public boolean filter(Difference difference) {
        String xpathRegex = convertXPathToRegex();
        return matcherType.filter(difference, xpathRegex);
    }

    private String convertXPathToRegex() {
        return xpath
                .replaceAll(SLASH, REGEX_DIGIT_AND_SLASH)
                .replaceFirst(PREFIX, StringUtils.EMPTY)
                .replaceAll(OPEN_PARENTHESIS, REGEX_OPEN_PARENTHESIS)
                .replaceAll(CLOSE_PARENTHESIS, REGEX_CLOSE_PARENTHESIS)
                .concat(REGEX_DIGIT);
    }
}
