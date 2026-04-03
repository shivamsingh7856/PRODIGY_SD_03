import java.io.*;
import java.util.*;

class Contact implements Serializable {
    String name;
    String phone;
    String email;

    Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

public class ContactManager {
    static ArrayList<Contact> contacts = new ArrayList<>();
    static final String FILE_NAME = "contacts.dat";

    public static void main(String[] args) {
        loadContacts();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Contact Management System ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addContact(sc); break;
                case 2: viewContacts(); break;
                case 3: editContact(sc); break;
                case 4: deleteContact(sc); break;
                case 5: saveContacts(); System.out.println("Contacts saved. Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    static void addContact(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact Added!");
    }

    static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            System.out.println((i+1) + ". " + c.name + " | " + c.phone + " | " + c.email);
        }
    }

    static void editContact(Scanner sc) {
        viewContacts();
        System.out.print("Enter contact number to edit: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index >= 0 && index < contacts.size()) {
            System.out.print("Enter New Name: ");
            contacts.get(index).name = sc.nextLine();
            System.out.print("Enter New Phone: ");
            contacts.get(index).phone = sc.nextLine();
            System.out.print("Enter New Email: ");
            contacts.get(index).email = sc.nextLine();
            System.out.println("Contact Updated!");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    static void deleteContact(Scanner sc) {
        viewContacts();
        System.out.print("Enter contact number to delete: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("Contact Deleted!");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    static void saveContacts() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(contacts);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }

    static void loadContacts() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            contacts = (ArrayList<Contact>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            contacts = new ArrayList<>();
        }
    }
}
