package org.yearup;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        // Create object array called inventory to store books
        Book[] inventory = new Book[5];

        // Populate the inventory with some books
        inventory[0] = new Book(
                1,"0-306-45415","Bible",false,""
        );
        inventory[1] = new Book(
                2,"0-406-40635","Ender's Game",true,"Willie"
        );
        inventory[2] = new Book(
                3,"0-106-98615","48 Laws of Power",false,""
        );
        inventory[3] = new Book(
                4,"0-706-76615","Just Mercy",false,""
        );
        inventory[4] = new Book(
                5,"0-806-40235","Maze Runner",true,"Lauren"
        );

        // Display the home screen and collect the command
        displayHomeScreen(userInput,inventory);
    }

    public static void displayHomeScreen(Scanner userInput,Book[] inventory){
        // List the options
        System.out.println("------------------------------");
        System.out.println("Select an option from below: ");
        System.out.println("1 | Show available books");
        System.out.println("2 | Show checked out books");
        System.out.println("3 | Exit");

        // Collect user input of command
        System.out.print("Enter: ");
        int command;
        command = userInput.nextInt();

        // Issue a command and display the next screen
        issueCommand(command,inventory,userInput);
    }

    public static void issueCommand(int command,Book[] array, Scanner userInput){
        // Create an empty bin to collect the books available / unavailable
        Book[] bin;
        int command2 = command;

        // Send each command to the proper method / function
        boolean exit = false;
        while (!exit){
            switch (command2){
                case 1:
                    // Show available books
                    System.out.println("\n[1] Showing available books...\n");
                    bin = displayAvailableBooks(array);
                    promptAvailable(bin,userInput,array);

                    exit = true;
                    break;
                case 2:
                    // Show checked out books
                    System.out.println("\n[2] Showing checked out books...\n");
                    bin = displayUnavailableBooks(array);
                    promptUnavailable(bin,userInput,array);

                    exit = true;
                    break;
                case 3:
                    // Quit
                    System.out.println("\n[3] Quitting...\n");

                    exit = true;
                    break;
                default:
                    // Error catcher
                    System.out.print("\nEnter a valid command please (1-3): \n");

                    command2 = userInput.nextInt();
                    exit = false;
                    break;
            }
        }
    }

    public static void displayAvailableCheckout(Book[] bin, Scanner userInput,Book[] inventory){
        System.out.println("------------------------------");
        System.out.println("Select a book's ID to check-out from the options below: ");

        for (int i = 0; i < bin.length; i++) {
            Book book = bin[i];
            if (book != null){
                System.out.println("ID(" + book.getId() + ")" + " | " + book.getTitle());
            }
        }

        System.out.println("0 | Exit");

        System.out.print("Enter: ");
        int command = 0;

        boolean exit = false;
        while (!exit) {
            System.out.print("Enter: ");
            command = userInput.nextInt();

            Book book = null;
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i].getId() == command){
                    book = inventory[i];
                }
            }

            if (book != null && book.isCheckedOut() == false){
                break;
            }else {
                System.out.println("Invalid response, try again...");
            }
        }

        userInput.nextLine();

        if (command != 0) {
            // Proceed to checking out the book & prompt for name
            System.out.print("Enter your name: ");
            // Collect the user's name
            String name;
            name = userInput.nextLine();
            // Get the book with the same ID as the command
            Book selectedBook = null;
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i].getId() == command){
                    selectedBook = inventory[i];
                }
            }
            // Display book checked out
            System.out.println("\nChecking out [" + selectedBook.getTitle() + "]...\n");
            // Checkout the book
            selectedBook.checkOut(name);
            // Go back to home screen
            displayHomeScreen(userInput,inventory);
        }else {
            // Quit the program
            System.out.println("\nQuitting...\n");
        }
    }

    public static void displayUnavailableCheckout(Book[] bin, Scanner userInput,Book[] inventory){
        System.out.println("------------------------------");
        System.out.println("Select a book's ID to check-in from the options below: ");

        for (int i = 0; i < bin.length; i++) {
            Book book = bin[i];
            if (book != null){
                System.out.println("ID(" + book.getId() + ")" + " | " + book.getTitle());
            }
        }

        System.out.println("0 | Exit");
        int command = 0;

        boolean exit = false;
        while (!exit) {
            System.out.print("Enter: ");
            command = userInput.nextInt();

            Book book = null;
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i].getId() == command){
                    book = inventory[i];
                }
            }

            if (book != null && book.isCheckedOut()){
                break;
            }else {
                System.out.println("Invalid response, try again...");
            }
        }
        // Bug fix
        userInput.nextLine();

        if (command != 0) {
            // Get the book with the same ID as the command
            Book selectedBook = null;
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i].getId() == command){
                    selectedBook = inventory[i];
                }
            }
            // Display book checked in
            System.out.println("\nChecking in [" + selectedBook.getTitle() + "]...\n");
            // Checkout the book
            selectedBook.checkIn();
            // Go back to home screen
            displayHomeScreen(userInput,inventory);
        }else {
            // Quit the program
            System.out.println("\nQuitting...\n");
        }
    }
    public static void promptUnavailable(Book[] bin, Scanner userInput,Book[] inventory){
        System.out.println("------------------------------");

        String command;
        boolean exit = false;

        while (!exit){
            // Print out commands
            System.out.println("\nDo you want to check-in a book?");
            System.out.println("C | Check in a book");
            System.out.println("X | Go to home screen");

            //Bug fix
            userInput.nextLine();
            // Prompt user for command
            System.out.print("Enter: ");
            command = userInput.nextLine().strip();


            switch (command.toUpperCase()){
                case "C":
                    System.out.println("\nContinuing to check-in...\n");
                    displayUnavailableCheckout(bin,userInput,inventory);
                    exit = true;
                    break;
                case "X":
                    System.out.println("\nGoing to home screen...\n");
                    displayHomeScreen(userInput,inventory);
                    exit = true;
                    break;
                default:
                    System.out.println("\nEnter a valid command please ('C' or 'X')\n");
                    exit = false;
                    break;
            }
        }
    }

    public static void promptAvailable(Book[] bin, Scanner userInput,Book[] inventory){
        System.out.println("------------------------------");

        int command;
        boolean exit = false;

        while (!exit){
            System.out.println("\nDo you want to check out a book?");
            System.out.println("0 | No");
            System.out.println("1 | Yes");

            System.out.print("Enter: ");
            command = userInput.nextInt();

            switch (command){
                case 0:
                    System.out.println("\nGoing to home screen...\n");
                    displayHomeScreen(userInput,inventory);
                    exit = true;
                    break;
                case 1:
                    System.out.println("\nContinuing to checkout...\n");
                    displayAvailableCheckout(bin,userInput,inventory);
                    exit = true;
                    break;
                default:
                    System.out.println("\nEnter a valid command please (0-1)\n");
                    exit = false;
                    break;
            }
        }
    }

    public static Book[] displayAvailableBooks(Book[] array){
        System.out.println("------------------------------");
        System.out.println("Available books: ");

        // Create a bin to hold all the selected books
        Book[] bin = new Book[array.length];
        // Create a counter to know the current index for the bin
        int counter = 0;

        // Loop through every book, display information of those available
        for (int i = 0; i < array.length; i++) {
            Book book = array[i];
            if (!book.isCheckedOut()) {
                // Print out the information
                System.out.println("ID: " + book.getId());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("TITLE: " + book.getTitle() + "\n");
                // Collect the book(s) for later use
                bin[counter] = book;
                counter ++;
            }
        }

        return bin;
    }

    public static Book[] displayUnavailableBooks(Book[] array){
        System.out.println("------------------------------");
        System.out.println("Checked out books: ");

        // Create a bin to hold all the selected books
        Book[] bin = new Book[array.length];
        // Create a counter to know the current index for the bin
        int counter = 0;

        // Loop through every book, display information of those available
        for (int i = 0; i < array.length; i++) {
            Book book = array[i];
            if (book.isCheckedOut()) {
                // Print out the information
                System.out.println("ID: " + book.getId());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("TITLE: " + book.getTitle() + "\n");
                System.out.println("CHECKED OUT BY: " + book.getCheckedOutTo() + "\n");
                // Collect the book(s) for later use
                bin[counter] = book;
                counter ++;
            }
        }

        return bin;
    }
}