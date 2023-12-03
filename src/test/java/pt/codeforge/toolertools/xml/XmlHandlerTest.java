package pt.codeforge.toolertools.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XmlHandlerTest {

    private static final String TEST_ELEMENT_TEXT = "//test-element/text()";
    private static final String NESTED_ELEMENT_TEXT = "//nested-element/text()";
    private static final String EMPTY_ELEMENT_TEXT = "//empty-element/text()";
    private static final String SELF_CLOSING_TEXT = "//self-closing/text()";
    private static final String ELEMENT_GROUP_TEXT = "//element-group/text()";
    private static final String ELEMENT_GROUP_NODE = "//element-group";
    private static final String COMPLIANT_X_PATH = TEST_ELEMENT_TEXT;
    private static final String MALFORMED_X_PATH = "/root/malformed/text()";

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenElementsWithText_getStringFromXPath_shouldReturnText(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        String testElement = XmlHandler.getStringFromXPath(TEST_ELEMENT_TEXT, doc);
        String nestedElement = XmlHandler.getStringFromXPath(NESTED_ELEMENT_TEXT, doc);
        String elementGroup = XmlHandler.getStringFromXPath(ELEMENT_GROUP_TEXT, doc);

        assertPresentElements(testElement, nestedElement, elementGroup);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenEmptyElements_getStringFromXPath_shouldReturnEmptyString(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        String emptyElement = XmlHandler.getStringFromXPath(EMPTY_ELEMENT_TEXT, doc);
        String selfClosing = XmlHandler.getStringFromXPath(SELF_CLOSING_TEXT, doc);

        assertEmptyElements(emptyElement, selfClosing);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadParams_getStringFromXPath_shouldReturnEmptyString(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        String testElement = XmlHandler.getStringFromXPath(TEST_ELEMENT_TEXT, null);
        String emptyElement = XmlHandler.getStringFromXPath(EMPTY_ELEMENT_TEXT, null);
        String malformed = XmlHandler.getStringFromXPath(MALFORMED_X_PATH, doc);
        String nullExpressionCase = XmlHandler.getStringFromXPath(null, doc);

        assertEmptyElements(testElement, emptyElement, malformed, nullExpressionCase);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenCompliantParams_getNodeListFromXPath_shouldReturnMappedList(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        NodeList elementGroup = XmlHandler.getNodeListFromXPath(NESTED_ELEMENT_TEXT, doc);

        assertEquals(3, elementGroup.getLength(), "NodeList should be size 3");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadValues_testNodeList_shouldReturnEmptyNodeList(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        NodeList nullDocumentCase = XmlHandler.getNodeListFromXPath(COMPLIANT_X_PATH, null);
        NodeList malformedXPathCase = XmlHandler.getNodeListFromXPath(MALFORMED_X_PATH, doc);
        NodeList nullXPathCase = XmlHandler.getNodeListFromXPath(null, doc);

        assertEmptyNodeLists(nullDocumentCase, malformedXPathCase, nullXPathCase);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenCompliantValues_testGetNodeFromXPath_shouldReturnValidNode(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        Node groupElement = XmlHandler.getNodeFromXPath(ELEMENT_GROUP_NODE, doc);

        String expectedNodeName = "element-group";

        assertEquals(Document.ELEMENT_NODE, groupElement.getNodeType(), "Should be element node");
        assertEquals(expectedNodeName, groupElement.getNodeName(), "Should be element node");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadParams_getNodeFromXPath_nodesShouldBeInvalidButNotNull(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        Node groupElement = XmlHandler.getNodeFromXPath(MALFORMED_X_PATH, doc);
        Node groupElementNullExpression = XmlHandler.getNodeFromXPath(null, doc);
        Node groupElementNullDoc = XmlHandler.getNodeFromXPath(MALFORMED_X_PATH, null);

        assertNotNullNodes(groupElement, groupElementNullExpression, groupElementNullDoc);
        assertInvalidNode(groupElement, groupElementNullExpression, groupElementNullDoc);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadParams_getNodeFromXPathOverloaded_shouldReturnNull(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        Node groupElement = XmlHandler.getNodeFromXPath(MALFORMED_X_PATH, doc, true);

        assertNull(groupElement, "Should be null");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenBadParams_getNodeFromXPathOverloaded_shouldNotReturnNull(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        Node groupElement = XmlHandler.getNodeFromXPath(MALFORMED_X_PATH, doc, false);

        assertNotNull(groupElement, "Should not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/test_resource_1.xml")
    void givenValidInput_getOptionalDomFromFile_shouldReturnValidDomFromFile(String input) {
        Document doc = XmlHandler.getOptionalDomFromFile(new File(input)).orElseThrow(IllegalStateException::new);

        assertDocIsValid(doc);
    }

    @ParameterizedTest
    @ValueSource(strings = "src/test/resources/notFound.xml")
    void givenBadInput_getOptionalDomFromFile_shouldBeEmpty(String input) {
        Optional<Document> optionalDocument = XmlHandler.getOptionalDomFromFile(new File(input));

        assertFalse(optionalDocument.isPresent());
        assertThrows(IllegalStateException.class, () -> optionalDocument.orElseThrow(IllegalStateException::new),
            "Should throw IllegalStateException");
    }

    private static void assertDocIsValid(Document doc) {
        assertNotNull(doc, "Document should not be null.");
        assertNotNull(doc.getFirstChild(), "Document's first child should not be null.");
        assertTrue(doc.getFirstChild().getChildNodes().getLength() > 0, "Root should have more than 0 childs.");
    }

    private static void assertEmptyElements(String... strings) {
        Arrays.stream(strings).forEach(string -> assertTrue(string.isEmpty(), "Should be empty"));
    }

    private static void assertPresentElements(String... strings) {
        Arrays.stream(strings).forEach(string -> assertFalse(string.isEmpty(), "Should not be empty"));
    }

    private static void assertEmptyNodeLists(NodeList... strings) {
        Arrays.stream(strings)
            .forEach(nodeList -> assertEquals(0, nodeList.getLength(), "NodeList should be be size 0"));
    }

    private static void assertNotNullNodes(Node... nodes) {
        Arrays.stream(nodes).forEach(node -> assertNotNull(node, "Should not be null"));
    }

    private static void assertInvalidNode(Node... nodes) {
        Arrays.stream(nodes).forEach(node -> assertEquals("null", node.getNodeName(), "Node name should be 'null'"));
    }
}
