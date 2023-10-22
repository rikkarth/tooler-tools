package org.toolertools;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class XMLHandlerTest {

    @Test
    public void testGetStringFromXPath_success() {
        Optional<Document> optionalDocument = XMLHandler.getOptionalDomFromFile(new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                String element1XPath = "/root/element1/text()";
                String emptyElementXPath = "/root/emptyElement/text()";

                XPathExpression xpeElement1 = XMLHandler.createXPathExpression(element1XPath);
                XPathExpression xpeEmptyElement = XMLHandler.createXPathExpression(emptyElementXPath);

                String element1 = XMLHandler.getStringFromXPath(xpeElement1, doc);
                String emptyElement = XMLHandler.getStringFromXPath(xpeEmptyElement, doc);

                assertFalse(element1.isEmpty(), element1XPath + " should not be empty");
                assertTrue(emptyElement.isEmpty(), emptyElementXPath + " should be empty");
            } catch (XPathExpressionException xpee) {
                throw new RuntimeException(xpee);
            }
        });
    }

    @Test
    public void testGetStringFromXPath_failure() {
        Optional<Document> optionalDocument = XMLHandler.getOptionalDomFromFile(new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                String element1XPath = "/root/element1/text()";
                String emptyElementXPath = "/root/emptyElement/text()";
                XPathExpression xpeElement1 = XMLHandler.createXPathExpression(element1XPath);
                XPathExpression xpeEmptyElement = XMLHandler.createXPathExpression(emptyElementXPath);
                String element1 = XMLHandler.getStringFromXPath(xpeElement1, null);
                String emptyElement = XMLHandler.getStringFromXPath(xpeEmptyElement, null);
                assertTrue(element1.isEmpty(), element1XPath + " should not be empty");
                assertTrue(emptyElement.isEmpty(), emptyElementXPath + "should be empty");
            } catch (XPathExpressionException xpee) {
                throw new RuntimeException(xpee);
            }
        });
    }

    @Test
    public void testCreateXPathExpression() {
        Optional<Document> optionalDocument = XMLHandler.getOptionalDomFromFile(new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                String malformedXPath = "/root/element1/tet()";
                String compliantXPath = "/root/emptyElement/text()";
                assertDoesNotThrow(() -> XMLHandler.createXPathExpression(compliantXPath), "should not throw");
                assertThrows(XPathExpressionException.class, () -> XMLHandler.createXPathExpression(malformedXPath), "should throw " + XPathExpressionException.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
