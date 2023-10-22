package org.toolertools.xml;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Optional;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlHandlerTest {

    private final String element1XPath = "/root/element1/text()";
    private final String emptyElementXPath = "/root/emptyElement/text()";
    private final String compliantXPath = this.element1XPath;
    private final String malformedXPath = "/root/element1/tet()";


    @Test
    public void testGetStringFromXPath_success() {
        Optional<Document> optionalDocument = XmlHandler.getOptionalDomFromFile(
            new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                XPathExpression xpeElement1 = XmlHandler.createXPathExpression(this.element1XPath);
                XPathExpression xpeEmptyElement = XmlHandler.createXPathExpression(
                    this.emptyElementXPath);

                String element1 = XmlHandler.getStringFromXPath(xpeElement1, doc);
                String emptyElement = XmlHandler.getStringFromXPath(xpeEmptyElement, doc);

                assertFalse(element1.isEmpty(), element1XPath + " should not be empty");
                assertTrue(emptyElement.isEmpty(), emptyElementXPath + " should be empty");
            } catch (XPathExpressionException xpee) {
                throw new RuntimeException(xpee);
            }
        });
    }

    @Test
    public void testGetStringFromXPath_failure() {
        Optional<Document> optionalDocument = XmlHandler.getOptionalDomFromFile(
            new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                XPathExpression xpeElement1 = XmlHandler.createXPathExpression(this.element1XPath);
                XPathExpression xpeEmptyElement = XmlHandler.createXPathExpression(
                    this.emptyElementXPath);
                String element1 = XmlHandler.getStringFromXPath(xpeElement1, null);
                String emptyElement = XmlHandler.getStringFromXPath(xpeEmptyElement, null);
                assertTrue(element1.isEmpty(), element1XPath + " should not be empty");
                assertTrue(emptyElement.isEmpty(), emptyElementXPath + "should be empty");
            } catch (XPathExpressionException xpee) {
                throw new RuntimeException(xpee);
            }
        });
    }

    @Test
    public void testCreateXPathExpression() {
        Optional<Document> optionalDocument = XmlHandler.getOptionalDomFromFile(
            new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                assertDoesNotThrow(() -> XmlHandler.createXPathExpression(this.compliantXPath),
                    "should not throw");
                assertThrows(XPathExpressionException.class,
                    () -> XmlHandler.createXPathExpression(this.malformedXPath),
                    "should throw " + XPathExpressionException.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void testGetNodeListFromXPath() {
        Optional<Document> optionalDocument = XmlHandler.getOptionalDomFromFile(
            new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                XPathExpression xpeCompliant = XmlHandler.createXPathExpression(
                    this.compliantXPath);
                NodeList nlCompliant = XmlHandler.getNodeListFromXPath(xpeCompliant, doc);
                NodeList nlBad = XmlHandler.getNodeListFromXPath(xpeCompliant, null);

                assertEquals(0, nlBad.getLength(), "NodeList should be be size 0");
                assertEquals(1, nlCompliant.getLength(), "NodeList should be size 1");

            } catch (XPathExpressionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
