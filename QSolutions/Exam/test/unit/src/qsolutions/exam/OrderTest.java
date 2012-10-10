/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.exam;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import junit.framework.TestCase;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import property.Marks;
import qsolutions.api.FormatApi;
import qsolutions.api.FormatApi.Property;

public class OrderTest extends TestCase {
    
    public OrderTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of addOrderChoice method, of class Order.
     */
    public void testAddOrderChoice() {
        System.out.println("addOrderChoice");
        Order instance = new Order();
        instance.removeChoices();
        instance.addOrderChoice("1776", true);
        assertEquals("1776", instance.getOrderText(0));
        assertTrue(instance.isActive(0));
        instance.addOrderChoice("1783", false);
        assertEquals("1783", instance.getOrderText(1));
        assertFalse(instance.isActive(1));
    }

    /**
     * Test of removeOrderChoice method, of class Order.
     */
    public void testRemoveOrderChoice() {
        System.out.println("removeOrderChoice");
        Order instance = new Order();
        
        instance.removeOrderChoice(0);
        instance.addOrderChoice("1776", true);
        assertEquals(1, instance.getNumOrders());
        instance.removeOrderChoice(0);
        assertEquals(0, instance.getNumOrders());
    }

    /**
     * Test of getDisplayIndex method, of class Order.
     */
    public void testGetDisplayIndex() {
        System.out.println("getDisplayIndex");
        Order instance = new Order();
        
        assertEquals(-1, instance.getDisplayIndex(0));
        instance.addOrderChoice("1776", true);
        assertEquals(0, instance.getDisplayIndex(0));
        instance.addOrderChoice("1783", true);
        assertEquals(1, instance.getDisplayIndex(1));
    }

    /**
     * Test of getOrderText method, of class Order.
     */
    public void testGetOrderText() {
        System.out.println("getOrderText");
        Order instance = new Order();
        
        assertEquals("", instance.getOrderText(1));
        instance.addOrderChoice("1776", true);
        instance.addOrderChoice("1783", true);
        assertEquals("1776", instance.getOrderText(0));
        assertEquals("1783", instance.getOrderText(1));
    }

    /**
     * Test of setOrderText method, of class Order.
     */
    public void testSetOrderText() {
        System.out.println("setOrderText");
        Order instance = new Order();
        
        instance.setOrderText(10, "noEffect");
        instance.addOrderChoice("1775", true);
        instance.setOrderText(0, "1776");
        assertEquals("1776", instance.getOrderText(0));
    }

    /**
     * Test of getNumOrders method, of class Order.
     */
    public void testGetNumOrders() {
        System.out.println("getNumOrders");
        Order instance = new Order();
        
        assertEquals(0, instance.getNumOrders());
        instance.addOrderChoice("1776", true);
        instance.addOrderChoice("1783", true);
        assertEquals(2, instance.getNumOrders());
    }

    /**
     * Test of isActive method, of class Order.
     */
    public void testIsActive() {
        System.out.println("isActive");
        Order instance = new Order();
        
        assertFalse(instance.isActive(0));
        instance.addOrderChoice("1776", true);
        instance.addOrderChoice("1783", false);
        assertTrue(instance.isActive(0));
        assertFalse(instance.isActive(1));
    }

    /**
     * Test of setActive method, of class Order.
     */
    public void testSetActive() {
        System.out.println("setActive");
        Order instance = new Order();
        
        instance.setActive(3, true);
        instance.addOrderChoice("1776", true);
        instance.setActive(0, false);
        assertFalse(instance.isActive(0));
        instance.setActive(0, true);
        assertTrue(instance.isActive(0));
        
        
    }

    /**
     * Test of copy method, of class Order.
     */
    public void testCopy() {
        System.out.println("copy");
        Order instance = new Order();
        
        assertEquals(instance, instance.copy());
        instance.addOrderChoice("1776", true);
        assertEquals(instance, instance.copy());
    }

    /**
     * Test of equals method, of class Order.
     */
    public void testEquals() {
        System.out.println("equals");
        Order instance = new Order();
        Order instanceCheck = new Order();
        assertFalse(instance.equals(new String("")));
        instance.addOrderChoice("1776", true);
        assertFalse(instance.equals(instanceCheck));
        instanceCheck.addOrderChoice("1776", false);
        assertFalse(instance.equals(instanceCheck));
        instanceCheck.setActive(0, true);
        instance.setActive(false);
        assertFalse(instance.equals(instanceCheck));
        instance.setActive(true);
        instance.setOrderText(0, "1783");
        assertFalse(instance.equals(instanceCheck));
        instance.setOrderText(0, "1776");
        instance.addOrderChoice("1783", true);
        instanceCheck.addOrderChoice("1783", true);
        instance.addOrderChoice("1800", true);
        instanceCheck.addOrderChoice("1800", true);
        while (instance.getDisplayIndex(0) == instanceCheck.getDisplayIndex(0))
            instance.randomizeAnswers();
        assertFalse(instance.equals(instanceCheck));
    }

    /**
     * Test of getHTML method, of class Order.
     */
    public void testGetHTML() {
        System.out.println("getHTML");
        FormatApi format = null;
        boolean isAnswerDoc = false;
        Order instance = new Order();
    }

    /**
     * Test of randomizeAnswers method, of class Order.
     */
    public void testRandomizeAnswers() {
        System.out.println("randomizeAnswers");
        Order instance = new Order();
    }

    /**
     * Test of newItem method, of class Order.
     */
    public void testNewItem() {
        System.out.println("newItem");
        Order instance = new Order();
        assertTrue((instance.newItem() instanceof Order));
    }
    
    public void testOrderGetHtml()
    {
        Order instance = new Order();
        instance.addOrderChoice("a", true);
        instance.addOrderChoice("b", true);
        instance.addOrderChoice("c", true);
        instance.getHTML(new Format(), false);
        instance.getHTML(new Format(), true);
        Format f = new Format();
        f.setProperty(Property.MarksPosition, Marks.MarksEnum.Left);
        instance.setActive(false);
        instance.getHTML(f, true);
    }

    public void testMoodle()
    {
        final String kQuestion = "This question is multiple choice";
        final String kAnswer1 = "ABC";
        final String kAnswer2 = "DEFGHI";
        
        Document document = newDocument();
        Element quizRoot = document.createElement("quiz");

        Order ord = new Order();
        ord.setText(kQuestion);
        ord.addOrderChoice(kAnswer1, true);
        ord.addOrderChoice(kAnswer2, true);

        quizRoot.appendChild(ord.serializeMoodle(document));
        document.appendChild(quizRoot);

        String out = xmlToString(document);
        // Stores the question
        assertTrue("Question text does not exist in question.",out.contains(kQuestion));
        // Is the right type
        assertTrue("Incorrect question type",out.contains("type=\"essay\""));
        // Has the correct true / false fields (case matters!)
        assertTrue("Order choice Text1",out.contains(kAnswer1));
        assertTrue("Order choice Text2",out.contains(kAnswer2));
        System.out.println(xmlToString(document));
    }

    public static Document newDocument() {
        DocumentBuilderFactory docBuilder = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try{
            db = docBuilder.newDocumentBuilder();
        }catch(Exception e){
            Exceptions.printStackTrace(e);
        }
        return db.newDocument();
    }

    public static String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
