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
import org.toolertools.internal.DocConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHandler {

    private static final XPathFactory xPathFactory = XPathFactory.newInstance();

    private XmlHandler() {
        throw new AssertionError("XmlHandler should not be instantiated.");
    }

    public static String getStringFromXPath(String expression, Document document) {
        try {
            XPathExpression xPathExpression = createXPathExpression(expression);
            return xPathExpression.evaluate(document);
        } catch (XPathExpressionException | NullPointerException e) {
            return "";
        }
    }

    public static NodeList getNodeListFromXPath(String expression, Document document) {
        try {
            XPathExpression xPathExpression = createXPathExpression(expression);
            return (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException | NullPointerException e) {
            Document doc = createEmptyDocument();
            return doc.createDocumentFragment().getChildNodes();
        }
    }

    public static Optional<Document> getOptionalDomFromFile(final File file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            documentBuilderFactory.setFeature(DocConstants.NAMESPACES, false);
            documentBuilderFactory.setFeature(DocConstants.VALIDATION, false);
            documentBuilderFactory.setFeature(DocConstants.LOAD_DTD_GRAMMAR, false);
            documentBuilderFactory.setFeature(DocConstants.LOAD_EXTERNAL_DTD, false);

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(file);

            return Optional.of(document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            return Optional.empty();
        }
    }

    private static XPathExpression createXPathExpression(String expression) throws XPathExpressionException {
        XPath xpath = xPathFactory.newXPath();
        return xpath.compile(expression);
    }

    private static Document createEmptyDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException pce) {
            throw new IllegalStateException(pce);
        }
    }
}
