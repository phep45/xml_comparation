package com.luxoft.xmlcomparator;


import org.custommonkey.xmlunit.Difference;

public class XmlMatcher {

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
        return xpath.replaceAll("/", "\\\\[\\\\d*\\\\]\\\\/").replaceFirst("\\\\\\[\\\\d\\*\\\\]", "").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)").concat("\\[\\d*\\]");
    }
}
