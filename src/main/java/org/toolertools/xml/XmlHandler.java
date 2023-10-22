package org.toolertools.xml;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHandler {

    private XmlHandler() {
        throw new AssertionError("XmlHandler should not be instantiated.");
    }

    public static Optional<Document> getOptionalDomFromFile(final File file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setValidating(true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
            documentBuilderFactory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            documentBuilderFactory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(file);

            return Optional.of(document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return Optional.empty();
        }
    }

    public static XPathExpression createXPathExpression(String expression)
        throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        return xpath.compile(expression);
    }

    public static String getStringFromXPath(XPathExpression xPathExpression, Document document) {
        try {
            return xPathExpression.evaluate(document);
        } catch (XPathExpressionException | NullPointerException e) {
            return "";
        }
    }

    public static NodeList getNodeListFromXPath(XPathExpression xPathExpression,
        Document document) {
        try {
            return (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException xpee) {
            Document doc = createEmptyDocument();
            return doc.createDocumentFragment().getChildNodes();
        }
    }

    private static Document createEmptyDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
