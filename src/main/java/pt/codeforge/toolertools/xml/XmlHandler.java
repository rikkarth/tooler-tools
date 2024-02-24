package pt.codeforge.toolertools.xml;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class for handling operations on XML files or XPath expressions. This class provides methods for querying and
 * manipulating XML documents using XPath expressions.
 */
public class XmlHandler {

    private static final XPathFactory xPathFactory = XPathFactory.newInstance();

    private XmlHandler() {
        throw new AssertionError("XmlHandler should not be instantiated.");
    }

    /**
     * Retrieves a string value from the specified XPath expression within the given XML document.
     *
     * @param expression The XPath expression.
     * @param document   The XML document.
     * @return The string value corresponding to the XPath expression, or an empty string if an error occurs.
     */
    public static String getStringFromXPath(String expression, Document document) {
        try {
            XPathExpression xPathExpression = createXPathExpression(expression);
            return xPathExpression.evaluate(document);
        } catch (XPathExpressionException | NullPointerException e) {
            return "";
        }
    }

    /**
     * Retrieves a NodeList from the specified XPath expression within the given XML document.
     *
     * @param expression The XPath expression.
     * @param document   The XML document.
     * @return The NodeList corresponding to the XPath expression, or an empty NodeList if an error occurs.
     */
    public static NodeList getNodeListFromXPath(String expression, Document document) {
        try {
            XPathExpression xPathExpression = createXPathExpression(expression);
            return Objects.requireNonNull((NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET));
        } catch (XPathExpressionException | NullPointerException e) {
            Document doc = createEmptyDocument();
            return doc.createDocumentFragment().getChildNodes();
        }
    }

    /**
     * Retrieves a Node from the specified XPath expression within the given XML document.
     *
     * @param expression The XPath expression.
     * @param document   The XML document.
     * @return The Node corresponding to the XPath expression, or a new 'null' Node if an error occurs.
     */
    public static Node getNodeFromXPath(String expression, Document document) {
        try {
            XPathExpression xPathExpression = createXPathExpression(expression);
            return Objects.requireNonNull((Node) xPathExpression.evaluate(document, XPathConstants.NODE));
        } catch (XPathExpressionException | NullPointerException | ClassCastException e) {
            return getEmptyElement();
        }
    }

    /**
     * Retrieves a Node from the specified XPath expression within the given XML document, with an option to return null
     * on error.
     *
     * @param expression        The XPath expression.
     * @param document          The XML document.
     * @param onErrorReturnNull If true, returns null on error; otherwise, returns a new 'null' Node.
     * @return The Node corresponding to the XPath expression, or null/a new 'null' Node based on the error-handling
     * option.
     */
    public static Node getNodeFromXPath(String expression, Document document, boolean onErrorReturnNull) {
        try {
            XPathExpression xPathExpression = createXPathExpression(expression);
            return Objects.requireNonNull((Node) xPathExpression.evaluate(document, XPathConstants.NODE));
        } catch (XPathExpressionException | NullPointerException e) {
            if (onErrorReturnNull) {
                return null;
            }
            return getEmptyElement();
        }
    }

    /**
     * Retrieves a Node from the specified XPath expression within the given XML document.
     *
     * @param expression The XPath expression.
     * @param document   The XML document.
     * @return The Element corresponding to the XPath expression, or a new 'null' Element if an error occurs.
     * @throws IllegalStateException if expression is not type Node.
     */
    public static Element getElementFromXPath(String expression, Document document) {
        return (Element) getNodeFromXPath(expression, document);
    }

    /**
     * Retrieves an ELement from the specified XPath expression within the given XML document, with an option to return
     * null on error.
     *
     * @param expression        The XPath expression.
     * @param document          The XML document.
     * @param onErrorReturnNull If true, returns null on error; otherwise, returns a new 'null' Node.
     * @return The Element corresponding to the XPath expression, or null/a new 'null' Node.
     */
    public static Element getElementFromXPath(String expression, Document document, boolean onErrorReturnNull) {
        return (Element) getNodeFromXPath(expression, document, onErrorReturnNull);
    }

    /**
     * Reads an XML file and returns an Optional containing the corresponding Document.
     *
     * @param file The XML file to read.
     * @return Optional containing the Document if successful, otherwise empty Optional.
     */
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

    private static Element getEmptyElement() {
        Document doc = createEmptyDocument();
        return doc.createElement("null");
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
