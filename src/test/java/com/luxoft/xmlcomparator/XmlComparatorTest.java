package com.luxoft.xmlcomparator;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.luxoft.xmlcomparator.XmlMatcherType.any;
import static com.luxoft.xmlcomparator.XmlMatcherType.anyOf;
import static com.luxoft.xmlcomparator.XmlMatcherType.noneOf;

public class XmlComparatorTest {

    private XmlComparator xmlComparator = new XmlComparator();

    @Test
    public void testMatchers() throws IOException, SAXException {

        //given
        String xml1 = FileUtils.readFileToString(new File("src/test/resources/file_1.xml"));
        String xml2 = FileUtils.readFileToString(new File("src/test/resources/file_2.xml"));

        XmlMatcher anyOf = new XmlMatcher("/transaction/sec-id/text()", anyOf("600", "500"));
        XmlMatcher noneOf = new XmlMatcher("/transaction/tran-id/text()", noneOf("1000", "2000"));
        XmlMatcher any = new XmlMatcher("/transaction/price/text()", any());
        XmlMatcher anyOrder = new XmlMatcher("/transaction/orders/order/text()", any());

        List<XmlMatcher> xmlMatchers = Arrays.asList(noneOf, any, anyOf, anyOrder);

        //when
        List result = xmlComparator.compareXml(xml1, xml2, xmlMatchers, true);

        //then
        Assert.assertEquals("Differences found: " + result, 0, result.size());

    }
}
