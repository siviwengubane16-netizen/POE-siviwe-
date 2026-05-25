/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class message {
  public enum Status { SENT, DISREGARDED, STORED }

    private String messageId; // 10-digit string
    private int messageNumber; // 0-based index of the message
    private String recipient; // recipient phone string
    private String messageText; // message content
    private String messageHash; // auto-generated
    private Status status;

    // Constructor used by runtime (generates random messageId)
    public message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = generateMessageId();
        this.messageHash = createMessageHash();
        this.status = null;
    }

    // Constructor used in tests where we want a deterministic messageId
    public message(String messageId, int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = messageId;
        this.messageHash = createMessageHash();
        this.status = null;
    }

    // Generate a 10-digit message ID (leading zeros allowed)
    public static String generateMessageId() {
        Random rnd = new Random();
        long v = Math.abs(rnd.nextLong()) % 1_000_000_0000L; // up to 10 digits
        return String.format("%010d", v);
    }

    public boolean checkMessageID() {
        return this.messageId!= null && this.messageId.matches("\\d{10}");
    }

    public boolean checkRecipientCell() {
        if (this.recipient == null) return false;
        return this.recipient.matches("^\\+27\\d{9}$");
    }

    public String createMessageHash() {
        String firstTwo = this.messageId!= null && this.messageId.length() >= 2? this.messageId.substring(0,2) : "00";
        String combined = "";
        if (this.messageText!= null &&!this.messageText.trim().isEmpty()) {
            String[] parts = this.messageText.trim().split("\\s+");
            String first = parts.length > 0? parts[0].replaceAll("[^A-Za-z0-9]", "") : "";
            String last = parts.length > 0? parts[parts.length - 1].replaceAll("[^A-Za-z0-9]", "") : "";
            combined = (first + last).toUpperCase();
        }
        return String.format("%s:%d:%s", firstTwo, this.messageNumber, combined);
    }

    public static String validateMessageLength(String text) {
        if (text == null) text = "";
        int len = text.length();
        if (len <= 250) return "Message ready to send.";
        int excess = len - 250;
        return String.format("Message exceeds 250 characters by %d, please reduce size.", excess);
    }

    public String performAction(int actionCode) {
        switch (actionCode) {
            case 0:
                this.status = Status.SENT;
                return "Message successfully sent";
            case 1:
                this.status = Status.DISREGARDED;
                return "Press 0 to delete message.";
            case 2:
                this.status = Status.STORED;
                return "Message successfully stored.";
            default:
                this.status = Status.DISREGARDED;
                return "Action cancelled, message disregarded.";
        }
    }

    // Console version - replaces sendMessageViaDialog()
    public String sendMessageViaConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose what to do with the message:");
        System.out.println("0 - Send message");
        System.out.println("1 - Disregard Message");
        System.out.println("2 - Store Message to send later");
        System.out.print("Enter choice: ");

        int choice = -1;
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
        } else {
            sc.next(); // discard bad input
        }
        return performAction(choice);
    }

    public String printMessageDetails() {
        return String.format("MessageID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s",
                this.messageId, this.messageHash, this.recipient, this.messageText);
    }

    public static int returnTotalMessages(List<message> list) {
        if (list == null) return 0;
        int count = 0;
        for (message m : list) if (m.status == Status.SENT) count++;
        return count;
    }

    public static String printMessages(List<message> list) {
        if (list == null || list.isEmpty()) return "No messages.";
        StringBuilder sb = new StringBuilder();
        for (message m : list) {
            sb.append(m.printMessageDetails());
            sb.append("\n-----------------\n");
        }
        return sb.toString();
    }

    public static void storeMessagesToJson(List<message> list, String filepath) throws IOException {
        if (list == null) list = java.util.Collections.emptyList();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < list.size(); i++) {
            message m = list.get(i);
            sb.append(" {\n");
            sb.append(" \"messageId\": \"").append(m.messageId).append("\",\n");
            sb.append(" \"messageNumber\": ").append(m.messageNumber).append(",\n");
            sb.append(" \"recipient\": \"").append(escapeJson(m.recipient)).append("\",\n");
            sb.append(" \"messageText\": \"").append(escapeJson(m.messageText)).append("\",\n");
            sb.append(" \"messageHash\": \"").append(escapeJson(m.messageHash)).append("\",\n");
            sb.append(" \"status\": \"").append(m.status == null? "UNKNOWN" : m.status.name()).append("\"\n");
            sb.append(" }");
            if (i < list.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]\n");
        try (FileWriter fw = new FileWriter(filepath, false)) {
            fw.write(sb.toString());
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

    // Getters for tests
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public Status getStatus() { return status; }
}  

