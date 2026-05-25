/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class messageIT {
    
    public messageIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of generateMessageId method, of class message.
     */
    @Test
    public void testGenerateMessageId() {
        System.out.println("generateMessageId");
        String expResult = "";
        String result = message.generateMessageId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkMessageID method, of class message.
     */
    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        message instance = null;
        boolean expResult = false;
        boolean result = instance.checkMessageID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkRecipientCell method, of class message.
     */
    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        message instance = null;
        boolean expResult = false;
        boolean result = instance.checkRecipientCell();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createMessageHash method, of class message.
     */
    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        message instance = null;
        String expResult = "";
        String result = instance.createMessageHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateMessageLength method, of class message.
     */
    @Test
    public void testValidateMessageLength() {
        System.out.println("validateMessageLength");
        String text = "";
        String expResult = "";
        String result = message.validateMessageLength(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of performAction method, of class message.
     */
    @Test
    public void testPerformAction() {
        System.out.println("performAction");
        int actionCode = 0;
        message instance = null;
        String expResult = "";
        String result = instance.performAction(actionCode);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessageViaConsole method, of class message.
     */
    @Test
    public void testSendMessageViaConsole() {
        System.out.println("sendMessageViaConsole");
        message instance = null;
        String expResult = "";
        String result = instance.sendMessageViaConsole();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printMessageDetails method, of class message.
     */
    @Test
    public void testPrintMessageDetails() {
        System.out.println("printMessageDetails");
        message instance = null;
        String expResult = "";
        String result = instance.printMessageDetails();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnTotalMessages method, of class message.
     */
    @Test
    public void testReturnTotalMessages() {
        System.out.println("returnTotalMessages");
        List<message> list = null;
        int expResult = 0;
        int result = message.returnTotalMessages(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printMessages method, of class message.
     */
    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        List<message> list = null;
        String expResult = "";
        String result = message.printMessages(list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeMessagesToJson method, of class message.
     */
    @Test
    public void testStoreMessagesToJson() throws Exception {
        System.out.println("storeMessagesToJson");
        List<message> list = null;
        String filepath = "";
        message.storeMessagesToJson(list, filepath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageId method, of class message.
     */
    @Test
    public void testGetMessageId() {
        System.out.println("getMessageId");
        message instance = null;
        String expResult = "";
        String result = instance.getMessageId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageNumber method, of class message.
     */
    @Test
    public void testGetMessageNumber() {
        System.out.println("getMessageNumber");
        message instance = null;
        int expResult = 0;
        int result = instance.getMessageNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecipient method, of class message.
     */
    @Test
    public void testGetRecipient() {
        System.out.println("getRecipient");
        message instance = null;
        String expResult = "";
        String result = instance.getRecipient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageText method, of class message.
     */
    @Test
    public void testGetMessageText() {
        System.out.println("getMessageText");
        message instance = null;
        String expResult = "";
        String result = instance.getMessageText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageHash method, of class message.
     */
    @Test
    public void testGetMessageHash() {
        System.out.println("getMessageHash");
        message instance = null;
        String expResult = "";
        String result = instance.getMessageHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class message.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        message instance = null;
        message.Status expResult = null;
        message.Status result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
