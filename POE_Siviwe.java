
//package poe_siviwe;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Student
 */
public class POE_Siviwe {
private static int messageCounter = 0;

    public static void main(String[] args) {
        // Sync working arrays from saved data records on start
        List<message> loaded = MessageStorage.loadMessages();
        for (message m : loaded) {
            MessageStorage.allMessages.add(m);
            if (m.getStatus().equalsIgnoreCase("SENT")) {
                MessageStorage.sentMessagesArray.add(m.getContent());
            } else if (m.getStatus().equalsIgnoreCase("PENDING")) {
                MessageStorage.storedMessagesArray.add(m);
            } else if (m.getStatus().equalsIgnoreCase("Disregard")) {
                MessageStorage.disregardedMessagesArray.add(m.getContent());
            }
            MessageStorage.messageHashArray.add(m.getMessageHash());
            MessageStorage.messageIDArray.add(m.getMessageID());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Welcome to QuickChat Authorization Systems ===");
        
        // Account Access Flow Sequence Loop Loop
        runAuthenticationLoop(scanner);

        // Active Application Content Loop
        if (login.getRegisteredUser() != null) {
            System.out.println("\nWelcome to QuickChat.");
            runQuickChatLoop(scanner);
        }
        
        scanner.close();
    }

    private static void runAuthenticationLoop(Scanner scanner) {
        while (login.getRegisteredUser() == null) {
            System.out.println("\n1. Register Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select choice options: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter username (must contain '_' and <= 5 chars): ");
                String user = scanner.nextLine().trim();
                System.out.print("Enter password (min 8 chars, 1 uppercase, 1 digit, 1 symbol): ");
                String pass = scanner.nextLine().trim();
                System.out.print("Enter cellphone (+27 followed by 9 digits): ");
                String cell = scanner.nextLine().trim();

                if (login.registerUser(user, pass, cell)) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed! Check your input constraints layout.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter username: ");
                String user = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String pass = scanner.nextLine().trim();

                if (login.loginuser(user, pass)) {
                    System.out.println("Welcome " + user + "!");
                    break;
                } else {
                    System.out.println("Incorrect username or password validation mismatch.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Exiting System.");
                System.exit(0);
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    private static void runQuickChatLoop(Scanner scanner) {
        System.out.print("How many messages would you like to process in total? ");
        int maxMessages;
        try {
            maxMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Access tracking fallback explicitly set to 1.");
            maxMessages = 1;
        }

        int entriesCount = 0;

        // Configured Menu Options inside an ArrayList
        List<String> mainMenuOptions = new ArrayList<>();
        mainMenuOptions.add("1) Send Messages");
        mainMenuOptions.add("2) Show Recently Sent Messages");
        mainMenuOptions.add("3) View Stored Messages");
        mainMenuOptions.add("4) View Longest Message");
        mainMenuOptions.add("5) View Sent Messages Report");
        mainMenuOptions.add("6) Disregarded Messages (Search/Delete Tools)");
        mainMenuOptions.add("7) Quit");

        while (true) {
            System.out.println("\n--- QUICKCHAT MAIN MENU ---");
            for (String option : mainMenuOptions) {
                System.out.println(option);
            }
            System.out.print("Select choice selection entry (1-7): ");
            String select = scanner.nextLine().trim();

            switch (select) {
                case "1" -> {
                    if (entriesCount >= maxMessages) {
                        System.out.println("Transaction operation limit met (" + maxMessages + ").");
                        break;
                    }
                    sendMessagesWorkflow(scanner);
                    entriesCount++;
                }
                case "2" -> {
                    String username = login.getRegisteredUser().getUsername();
                    System.out.println("\n" + MessageStorage.displaySentMessages(username));
                }
                case "3" -> viewStoredMessagesWorkflow(scanner);
                case "4" -> System.out.println("\nLongest Message Content: " + MessageStorage.displayLongestMessage());
                case "5" -> System.out.println("\n" + MessageStorage.displayReport());
                case "6" -> manageDisregardedSubMenuWorkflow(scanner);
                case "7" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice selection code verification.");
            }
        }
    }

    private static void sendMessagesWorkflow(Scanner scanner) {
        System.out.print("Enter recipient cellphone (+27...): ");
        String recipient = scanner.nextLine().trim();
        user us = new user("", "",recipient);
        if (!us.validateCellphone(recipient)) {
            System.out.println("Invalid cellphone format verification status.");
            return;
        }

        System.out.print("Enter message text contents (max 250 chars): ");
        String text = scanner.nextLine();
        if (text.length() > 250) {
            System.out.println("Message processing terminated: content length overflows constraints limit.");
            return;
        }

        System.out.println("\nChoose action layout destination choice flag:");
        System.out.println("1. Send Message");
        System.out.println("2. Discard Message");
        System.out.println("3. Store to Send Later");
        System.out.print("Select entry number (1-3): ");
        String action = scanner.nextLine().trim();

        List<message> fileList = MessageStorage.loadMessages();
        String mID = message.generateMessageID();
        message mObj;

        switch (action) {
            case "1" -> {
                String hash = message.generateMessageHash(mID, messageCounter, text);
                mObj = new message(mID, recipient, text, "SENT", hash);
                fileList.add(mObj);
                MessageStorage.saveMessages(fileList);
                MessageStorage.allMessages.add(mObj);
                MessageStorage.sentMessagesArray.add(text);
                MessageStorage.messageHashArray.add(hash);
                MessageStorage.messageIDArray.add(mID);
                messageCounter++;
                System.out.println("\nMessage sent successfully!\nID: " + mID + "\nHash: " + hash);
            }
            case "2" -> {
                String hash = message.generateMessageHash(mID, messageCounter, text);
                mObj = new message(mID, recipient, text, "Disregard", hash);
                MessageStorage.allMessages.add(mObj);
                MessageStorage.disregardedMessagesArray.add(text);
                MessageStorage.messageHashArray.add(hash);
                MessageStorage.messageIDArray.add(mID);
                System.out.println("Message successfully flagged as disregarded.");
            }
            case "3" -> {
                String hash = message.generateMessageHash(mID, messageCounter, text);
                mObj = new message(mID, recipient, text, "PENDING", hash);
                fileList.add(mObj);
                MessageStorage.saveMessages(fileList);
                MessageStorage.allMessages.add(mObj);
                MessageStorage.storedMessagesArray.add(mObj);
                MessageStorage.messageHashArray.add(hash);
                MessageStorage.messageIDArray.add(mID);
                System.out.println("\nMessage successfully stored contextually to system file layers!\nID: " + mID + "\nHash: " + hash);
            }
            default -> System.out.println("Action parameters execution canceled.");
        }
    }

    private static void viewStoredMessagesWorkflow(Scanner scanner) {
        List<message> allFileMessages = MessageStorage.loadMessages();
        List<message> pendingList = new ArrayList<>();
        
        for (message m : allFileMessages) {
            if (m.getStatus().equalsIgnoreCase("PENDING")) {
                pendingList.add(m);
            }
        }

        if (pendingList.isEmpty()) {
            System.out.println("No stored messages matching status rules found.");
            return;
        }

        System.out.println("\n--- PENDING STORED MESSAGES ---");
        for (int i = 0; i < pendingList.size(); i++) {
            message m = pendingList.get(i);
            System.out.println((i + 1) + ". To: " + m.getRecipient() + " | Text: " + m.getContent() + " [ID: " + m.getMessageID() + "]");
        }
        System.out.print("Enter index item index value location number to finalize sending tracking (or 0 to exit back): ");
        
        try {
            int entrySelection = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (entrySelection == -1) return;
            if (entrySelection < 0 || entrySelection >= pendingList.size()) {
                System.out.println("Index array out of bounds scope execution layout.");
                return;
            }

            message choiceMessage = pendingList.get(entrySelection);
            for (message fileMsg : allFileMessages) {
                if (fileMsg.getMessageID().equals(choiceMessage.getMessageID())) {
                    fileMsg.setStatus("SENT");
                    messageCounter++;
                    fileMsg.setMessageHash(message.generateMessageHash(fileMsg.getMessageID(), messageCounter, fileMsg.getContent()));
                    
                    // Match object inside operational collections arrays
                    for (message operationalMsg : MessageStorage.allMessages) {
                        if (operationalMsg.getMessageID().equals(fileMsg.getMessageID())) {
                            operationalMsg.setStatus("SENT");
                            operationalMsg.setMessageHash(fileMsg.getMessageHash());
                        }
                    }
                    
                    MessageStorage.sentMessagesArray.add(fileMsg.getContent());
                    MessageStorage.storedMessagesArray.remove(choiceMessage);
                    break;
                }
            }
            MessageStorage.saveMessages(allFileMessages);
            System.out.println("Stored record items executed to SENT condition flags.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid digital formatting selection identifier context layout values.");
        }
    }

    private static void manageDisregardedSubMenuWorkflow(Scanner scanner) {
        List<String> toolOptions = new ArrayList<>();
        toolOptions.add("a. Search by Message ID");
        toolOptions.add("b. Search by Recipient");
        toolOptions.add("c. Delete by Message Hash");
        toolOptions.add("d. Back to Main Menu");

        while (true) {
            System.out.println("\n--- Message Tools Loop Options ---");
            for (String tool : toolOptions) {
                System.out.println(tool);
            }
            System.out.print("Select sub option entry flag character (a-d): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("a")) {
                System.out.print("Enter Message ID tracking string: ");
                String id = scanner.nextLine().trim();
                System.out.println("\n" + MessageStorage.searchMessageID(id));
            } else if (input.equals("b")) {
                System.out.print("Enter Recipient Cellphone tracking string (+27...): ");
                String rec = scanner.nextLine().trim();
                System.out.println("\n" + MessageStorage.searchRecipientMessages(rec));
            } else if (input.equals("c")) {
                System.out.print("Enter message unique verification tracking Hash index value: ");
                String hash = scanner.nextLine().trim();
                System.out.println("\n" + MessageStorage.deleteMessage(hash));
            } else if (input.equals("d")) {
                break;
            } else {
                System.out.println("Option validation verification fallback match failure logic.");
            }
        }
    }
}
    