/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author Student
 */
public class MessageStorage {
    private static final String FILE_PATH = "messages.json"; 

    // Operational context tracking arrays
    public static List<String> sentMessagesArray = new ArrayList<>();
    public static List<String> disregardedMessagesArray = new ArrayList<>();
    public static List<message> storedMessagesArray = new ArrayList<>();
    public static List<String> messageHashArray = new ArrayList<>();
    public static List<String> messageIDArray = new ArrayList<>();
    public static List<message> allMessages = new ArrayList<>();

    // Saves a list of messages to the local flat file 
    public static void saveMessages(List<message> messages) { 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) { 
            writer.write("[\n"); 
            for (int i = 0; i < messages.size(); i++) {
                writer.write(messages.get(i).toJSON()); 
                if (i < messages.size() - 1) writer.write(",\n"); 
            }
            writer.write("\n]"); 
        } catch (IOException e) {
            System.out.println("Error saving messages to storage system file: " + e.getMessage()); 
        }
    }

    // Load active record parameters out of structural runtime elements 
    public static List<message> loadMessages() {
        List<message> messages = new ArrayList<>(); 
        File file = new File(FILE_PATH);
        if (!file.exists()) return messages;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line); 

            String json = sb.toString().trim();
            if (json.startsWith("[") && json.endsWith("]")) { 
                json = json.substring(1, json.length() - 1).trim(); 
                if (!json.isEmpty()) {
                    String[] items = json.split("\\},\\s*\\{"); 
                    for (String item : items) {
                        String cleanItem = item;
                        if (!cleanItem.startsWith("{")) cleanItem = "{" + cleanItem; 
                        if (!cleanItem.endsWith("}")) cleanItem = cleanItem + "}"; 
                        messages.add(parseMessage(cleanItem)); 
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not parse file successfully: " + e.getMessage());
        }
        return messages; 
    }

    private static message parseMessage(String json) {
        String id = json.replaceAll(".\"messageID\"\\s:\\s*\"([^\"]+)\".*", "$1"); 
        String recipient = json.replaceAll(".\"recipient\"\\s:\\s*\"([^\"]+)\".*", "$1"); 
        String content = json.replaceAll(".\"content\"\\s:\\s*\"([^\"]+)\".*", "$1"); 
        String status = json.replaceAll(".\"status\"\\s:\\s*\"([^\"]+)\".*", "$1"); 
        String hash = json.replaceAll(".\"messageHash\"\\s:\\s*\"([^\"]+)\".*", "$1"); 
        
        // Clean up fallback matches in case regex didn't clean correctly
        if (id.contains("{")) id = "";
        if (recipient.contains("{")) recipient = "";
        if (content.contains("{")) content = "";
        if (hash.contains("{")) hash = "";
        if (status.isEmpty() || status.contains("{")) status = "SENT"; 
        
        return new message(id, recipient, content, status, hash); 
    }

    public static String displaySentMessages(String registeredUsername) {
        StringBuilder sb = new StringBuilder("--- All Sent Messages ---\n");
        int count = 0;
        String sender = (registeredUsername != null) ? registeredUsername : "Unknown User";
        for (message m : allMessages) {
            if (m.getStatus().equalsIgnoreCase("Sent")) { 
                sb.append("Sender: ").append(sender)
                  .append(" | Recipient: ").append(m.getRecipient())
                  .append(" | Message: ").append(m.getContent()).append("\n");
                count++;
            }
        }
        if (count == 0) return "No sent messages found.";
        return sb.toString().trim();
    }

    public static String displayLongestMessage() {
        message longest = allMessages.stream()
            .max(Comparator.comparingInt(m -> m.getContent().length()))
            .orElse(null);

        if (longest == null) return "No messages found.";
        return longest.getContent();
    }

    public static String searchMessageID(String messageID) {
        for (message m : allMessages) {
            if (m.getMessageID().equals(messageID)) {
                return "Recipient: " + m.getRecipient() + "\nMessage: " + m.getContent();
            }
        }
        return "Message ID not found.";
    }

    public static String searchRecipientMessages(String recipientNumber) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (message m : allMessages) {
            if (m.getRecipient().equals(recipientNumber) && !m.getStatus().equalsIgnoreCase("Disregard")) {
                sb.append("- ").append(m.getContent()).append(" (Hash: ").append(m.getMessageHash()).append(")\n");
                count++;
            }
        }
        if (count == 0) return "No messages found for this recipient.";
        return sb.toString().trim();
    }

    public static String deleteMessage(String messageHash) {
        message messageToDelete = null;
        for (message m : allMessages) {
            if (m.getMessageHash().equals(messageHash)) {
                messageToDelete = m;
                break;
            }
        }
        if (messageToDelete != null) {
            allMessages.remove(messageToDelete);
            sentMessagesArray.remove(messageToDelete.getContent());
            disregardedMessagesArray.remove(messageToDelete.getContent());
            storedMessagesArray.remove(messageToDelete);
            messageHashArray.remove(messageToDelete.getMessageHash());
            messageIDArray.remove(messageToDelete.getMessageID());
            
            List<message> fileMessages = loadMessages();
            fileMessages.removeIf(m -> m.getMessageHash().equals(messageHash));
            saveMessages(fileMessages);
            return "Message \"" + messageToDelete.getContent() + "\" successfully deleted.";
        }
        return "Message not found for the given hash.";
    }

    public static String displayReport() {
        StringBuilder sb = new StringBuilder("--- SENT MESSAGES REPORT ---\n");
        sb.append("Message Hash\t\tRecipient\t\tMessage\n");
        sb.append("-------------------------------------------------------------\n");
        int count = 0;
        for (message m : allMessages) {
            if (m.getStatus().equalsIgnoreCase("Sent")) {
                sb.append(m.getMessageHash()).append("\t\t").append(m.getRecipient()).append("\t\t").append(m.getContent()).append("\n");
                count++;
            }
        }
        if (count == 0) return "No sent messages to report.";
        return sb.toString().trim();
    }
}

