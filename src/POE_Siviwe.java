
//package poe_siviwe;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Student
 */
public class POE_Siviwe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        login login = new login();

        // === LOGO AND WELCOME SCREEN ===
        showWelcomeScreen();

        // === REGISTRATION ===
        while (true) {
            System.out.println("\n📝 Let's get you started!");
            System.out.println("Please register your details to create your ME2YouChat account.");

            System.out.print("👤 Enter a username (must include '_' and be 5 characters or fewer): ");
            String username = input.nextLine();

            System.out.print("🔒 Create a password (at least 8 characters, includes uppercase, number & symbol): ");
            String password = input.nextLine();

            System.out.print("📱 Enter your cellphone (with South African code, e.g. +27838968976): ");
            String cellphone = input.nextLine();

            String regMessage = login.registerUser(username.trim(), password, cellphone.trim());
            System.out.println(regMessage);

            if (regMessage.equals("Registration successful.")) break;
        }

        // === LOGIN ===
        boolean loggedIn = false;
        String loggedUsername = null;
        while (!loggedIn) {
            System.out.println("\n🔑 Login to continue");
            
            System.out.print("👤 Username: ");
            String username = input.nextLine();

            System.out.print("🔒 Password: ");
            String password = input.nextLine();

            boolean ok = login.loginUser(username.trim(), password);
            String msg = login.returnLoginStatus(ok, username.trim());
            System.out.println(msg);

            if (ok) {
                loggedIn = true;
                loggedUsername = username.trim();
            }
        }
        
        input.close();
        System.out.println("\n✅ Logged in successfully as: " + loggedUsername);
    }

    private static void showWelcomeScreen() {
        System.out.println("================================");
        System.out.println("      💬 Me2Youchat 💬");
        System.out.println("   Welcome to ME2YOUChat");
        System.out.println("================================");
    }
}
    
//the messege parts 


 public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // === WELCOME SCREEN ===
        showWelcomeScreen();

        // === MESSAGE COUNT INPUT ===
        int messagesToEnter = 0;
        while (messagesToEnter <= 0) {
            System.out.print("\n✉️ How many messages would you like to send today? ");
            String nm = input.nextLine();
            try {
                messagesToEnter = Integer.parseInt(nm.trim());
                if (messagesToEnter <= 0)
                    System.out.println("⚠️ Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("🚫 Please enter a valid number.");
            }
        }

        List<message> allMessages = new ArrayList<>();
        boolean quit = false;

        while (!quit) {
            String menu = """
                    
                    📋 What would you like to do next?
                    1️⃣ Send new messages
                    2️⃣ Coming soon...
                    3️⃣ Exit
                    Enter choice: """;
            System.out.print(menu);
            String choice = input.nextLine();
            if (choice == null) return;

            switch (choice.trim()) {
                case "1":
                    if (allMessages.size() >= messagesToEnter) {
                        System.out.println("🚫 You’ve already sent your " + messagesToEnter + " message(s).");
                        break;
                    }

                    int remaining = messagesToEnter - allMessages.size();
                    for (int i = 0; i < remaining; i++) {
                        System.out.printf("%n📨 Enter recipient for message %d (include +27): ", i + 1);
                        String recipient = input.nextLine();
                        if (recipient.isBlank()) {
                            System.out.println("Cancelled.");
                            break;
                        }

                        System.out.print("💬 Type your message (max 250 characters): ");
                        String messageText = input.nextLine();
                        if (messageText.isBlank()) {
                            System.out.println("Cancelled.");
                            break;
                        }

                        // Validate message length
                        String lengthCheck = message.validateMessageLength(messageText);
                        if (messageText.length() > 250) {
                            System.out.println("⚠️ " + lengthCheck);
                            i--;
                            continue;
                        }

                        System.out.println("✅ Message ready to send!");

                        // Create and process message
                        message m = new message(allMessages.size(), recipient.trim(), messageText);
                        
                        // Use the new console method from Message class
                        String actionResult = m.sendMessageViaConsole();
                        System.out.println(actionResult);

                        if (m.getStatus() == message.Status.STORED) {
                            try {
                                message.storeMessagesToJson(
                                        Collections.singletonList(m),
                                        System.getProperty("user.home") + "/stored_messages.json");
                                System.out.println("💾 Message saved to JSON at " + System.getProperty("user.home") + "/stored_messages.json");
                            } catch (Exception ex) {
                                System.out.println("❌ Failed to save message: " + ex.getMessage());
                            }
                        }

                        System.out.println("\n" + m.printMessageDetails());
                        allMessages.add(m);
                    }

                    //int totalSent = message.returnTotalMessages(allmessages);
                    //System.out.println("\n📊 Total messages sent: " + totalSent);
                    break;

                case "2":
                    System.out.println("🕒 Feature coming soon! You’ll be able to view your message history here.");
                    if (!allMessages.isEmpty()) {
                        System.out.println("\n=== Recent Messages ===");
                        //System.out.println(message.printMessages(allmessages));
                    }
                    break;

                case "3":
                    quit = true;
                    break;

                default:
                    System.out.println("⚠️ Please choose 1, 2, or 3.");
            }
        }

        System.out.println("\n👋 Thank you for using RMQuickChat!");
        System.out.println("💖 “Stay connected. Stay real.”");
        System.out.println("See you soon!");
        
        input.close();
    }

    private static void showWelcomeScreen() {
        System.out.println("==============================================");
        System.out.println("             💬 RMQuickChat 💬");
        System.out.println("   “Where Real Messages Make Real Connections.”");
        System.out.println("==============================================");
    }

    

