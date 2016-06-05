package com.luxoft.xmlcomparator;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class XmlComparator extends XMLTestCase {

    public List compareXml(String control, String test, List<XmlMatcher> matchers) throws IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        List allDifs = new DetailedDiff(compareXML(control, test)).getAllDifferences();

        for(XmlMatcher xmlMatcher : matchers) {
            allDifs = (List) allDifs.stream().filter(d -> xmlMatcher.filter((Difference) d)).collect(Collectors.toList());
        }

        return allDifs;
    }

}
