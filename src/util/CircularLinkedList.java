package util;
import ruclinic.Technician;


public class CircularLinkedList {
    private Node tail; // The tail of the circular linked list
    private Node current; // Pointer for round-robin traversal
    private int size;

    // Constructor for CircularLinkedList
    public CircularLinkedList() {
        tail = null;
        current = null;
        size = 0;
    }

    // Add a technician to the circular linked list
    public void addTechnician(Technician technician) {
        Node newNode = new Node(technician);
        if (tail == null) {
            tail = newNode;
            tail.next = tail; // Points to itself
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        System.out.println("Technician added: " + technician.getProfile().getFirstName() + " " + technician.getProfile().getLastName());
    }

    // Remove a technician from the list
    public boolean removeTechnician(Technician technician) {
        if (tail == null) {
            return false; // List is empty
        }
        Node prev = tail;
        Node current = tail.next;
        do {
            if (current.technician.equals(technician)) {
                if (current == tail) { // Removing the last node
                    if (current == tail.next) { // Only one element
                        tail = null;
                    } else {
                        tail = prev;
                    }
                }
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        } while (current != tail.next);
        return false;
    }

    // Get the next technician in the round-robin order
    public Technician getNextTechnician() {
        if (current == null) {
            current = tail.next; // Start from the head
        }
        Technician technician = current.technician;
        current = current.next; // Move to the next technician
        return technician;
    }
      // Return to the head for traversal
      public Technician getFirstTechnician() {
        if (tail != null) {
            return tail.next.technician; // Return the first technician (head)
        }
        return null; // No technicians available
    }

    public void printTechnicianList() {
        if (tail == null) {
            System.out.println("Technician list is empty.");
            return;
        }
        Node current = tail.next; // Start from the head (node after the tail)
        do {
            System.out.println(current.technician.getProfile().getFirstName() + " " +
                               current.technician.getProfile().getLastName());
            current = current.next;
        } while (current != tail.next); // Loop until back at the start
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the size of the list
    public int size() {
        return size;
    }
}