/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Student
 */
public class message {
  private String messageID; 
    private String messageHash; 
    private String recipient; 
    private String content; 
    private String status; // SENT, PENDING, or Disregard

    // Constructor to initialize message details
    public message(String messageID, String recipient, String content, String status, String messageHash) {
        this.messageID = messageID; 
        this.recipient = recipient; 
        this.content = content; 
        this.status = status; 
        this.messageHash = messageHash; 
    }

    // Validation for message length limits
    public static String validateMessageLength(String message) {
        int len = message.length(); 
        if (len <= 250) { 
            return "Message ready to send."; 
        } else {
            int extra = len - 250; 
            return "Message exceeds 250 characters by " + extra + ", please reduce size."; 
        }
    }

    // Validation for recipient string construction
    public static String validateRecipientNumber(String number) {
        if (number != null && number.matches("\\+27\\d{9}")) { 
            return "Cell phone number successfully captured."; 
        } else { 
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again."; 
        }
    }

    // Generate 10-digit random message ID
    public static String generateMessageID() {
        Random rand = new Random(); 
        long id = 1000000000L + rand.nextInt(900000000); 
        return String.valueOf(id); 
    }

    // Generate complete structural message hash string
    public static String generateMessageHash(String messageID, int messageNumber, String message) {
        if (messageID == null || messageID.length() < 2) messageID = "00";
        String idPart = messageID.substring(0, 2); 
        String[] words = message.trim().split("\\s+"); 
        String firstWord = words.length > 0 ? words[0] : ""; 
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord; 
        return (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase(); 
    }

    // Convert message object data parameters into JSON strings
    public String toJSON() {
        return "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"recipient\":\"" + recipient + "\","
                + "\"content\":\"" + content.replace("\"", "\\\"") + "\","
                + "\"status\":\"" + status + "\","
                + "\"messageHash\":\"" + messageHash + "\""
                + "}";
    }
    

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public String getMessageHash() { return messageHash; }

    public void setStatus(String status) {
        this.status = status; 
    }
    
    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }
}