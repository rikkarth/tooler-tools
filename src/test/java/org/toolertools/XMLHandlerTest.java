package org.toolertools;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.util.Optional;

public class XMLHandlerTest {

    @Test
    public void testGetStringFromXPath_success() {
        Optional<Document> optionalDocument = XMLHandler.getOptionalDomFromFile(new File("src/test/resources/test_resource_1.xml"));
        optionalDocument.ifPresent(doc -> {
            try {
                XPathExpression xpeElement1 = XMLHandler.createXPathExpression("/root/element1/text()");
                XPathExpression xpeEmptyElement = XMLHandler.createXPathExpression("/root/emptyElement/text()");
                String element1 = XMLHandler.getStringFromXPath(xpeElement1, doc);
                String emptyElement = XMLHandler.getStringFromXPath(xpeEmptyElement, doc);
                assertFalse(element1.isEmpty(), "Element 1 is not empty");
                assertTrue(emptyElement.isEmpty(), "emptyElement is empty");
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
                XPathExpression xpeElement1 = XMLHandler.createXPathExpression("/root/element1/text()");
                XPathExpression xpeEmptyElement = XMLHandler.createXPathExpression("/root/emptyElement/text()");
                String element1 = XMLHandler.getStringFromXPath(xpeElement1, null);
                String emptyElement = XMLHandler.getStringFromXPath(xpeEmptyElement, null);
                assertTrue(element1.isEmpty(), "Element 1 is not empty");
                assertTrue(emptyElement.isEmpty(), "emptyElement is empty");
            } catch (XPathExpressionException xpee) {
                throw new RuntimeException(xpee);
            }
        });
    }
}
