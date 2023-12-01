package org.toolertools.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

class XmlHandlerTest {

    private static final String ELEMENT_1_X_PATH = "/root/element1/text()";
    private static final String EMPTY_ELEMENT_X_PATH = "/root/emptyElement/text()";
    private static final String COMPLIANT_X_PATH = ELEMENT_1_X_PATH;
    private static final String MALFORMED_X_PATH = "/root/element1/tet()";

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void testGetStringFromXPath_success(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        String element1 = XmlHandler.getStringFromXPath(ELEMENT_1_X_PATH, doc);
        String emptyElement = XmlHandler.getStringFromXPath(EMPTY_ELEMENT_X_PATH, doc);

        assertFalse(element1.isEmpty(), ELEMENT_1_X_PATH + " should not be empty");
        assertTrue(emptyElement.isEmpty(), EMPTY_ELEMENT_X_PATH + " should be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadValues_getStringFromXPath_shouldReturnEmptyString(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        String element1 = XmlHandler.getStringFromXPath(ELEMENT_1_X_PATH, null);
        String emptyElement = XmlHandler.getStringFromXPath(EMPTY_ELEMENT_X_PATH, null);
        String malformed = XmlHandler.getStringFromXPath(MALFORMED_X_PATH, doc);
        String malformed2 = XmlHandler.getStringFromXPath(null, doc);

        assertTrue(element1.isEmpty(), ELEMENT_1_X_PATH + " should be empty");
        assertTrue(emptyElement.isEmpty(), EMPTY_ELEMENT_X_PATH + "should be empty");
        assertTrue(malformed.isEmpty(), MALFORMED_X_PATH + "should be empty");
        assertTrue(malformed2.isEmpty(), "provided null expression should be empty");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenCompliantValues_testNodeList_success(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        NodeList nlCompliant = XmlHandler.getNodeListFromXPath(COMPLIANT_X_PATH, doc);

        assertEquals(1, nlCompliant.getLength(), "NodeList should be size 1");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadValues_testNodeList_failure(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        NodeList nlBad = XmlHandler.getNodeListFromXPath(COMPLIANT_X_PATH, null);
        NodeList nlBad2 = XmlHandler.getNodeListFromXPath(MALFORMED_X_PATH, doc);
        NodeList nlBad3 = XmlHandler.getNodeListFromXPath(null, doc);

        assertEquals(0, nlBad.getLength(), "NodeList should be be size 0");
        assertEquals(0, nlBad2.getLength(), "NodeList should be be size 0");
        assertEquals(0, nlBad3.getLength(), "NodeList should be be size 0");
    }
}
