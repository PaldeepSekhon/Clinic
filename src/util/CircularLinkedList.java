package util;

/**
 * Circular linked list for the providers
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */

import ruclinic.Radiology;
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

    // Add a technician to the circular linked list at the beginning
    public void addTechnician(Technician technician) {
        Node newNode = new Node(technician);
        if (tail == null) {
            tail = newNode;
            tail.next = tail; // Points to itself
            current = tail; // Initialize current pointer
        } else {
            newNode.next = tail.next; // New node points to head
            tail.next = newNode; // Tail points to new node
        }
        size++;
    }

    // Remove a technician from the list
    public boolean removeTechnician(Technician technician) {
        if (tail == null) {
            return false; // List is empty
        }

        Node prev = tail;
        Node curr = tail.next;

        do {
            if (curr.technician.equals(technician)) {
                // If removing the current pointer, move it to next
                if (curr == current) {
                    current = curr.next;
                }

                // If removing the only node
                if (curr == tail && curr.next == tail) {
                    tail = null;
                    current = null;
                }
                // If removing the tail
                else if (curr == tail) {
                    tail = prev;
                    tail.next = curr.next;
                }
                // If removing any other node
                else {
                    prev.next = curr.next;
                }

                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != tail.next);

        return false;
    }

    // Get the next technician in the round-robin order
    public Technician getNextTechnician() {
        if (tail == null) {
            return null; // List is empty
        }

        // Move to next technician
        current = current.next;
        return current.technician;
    }

    // Return the first technician (head of list)
    public Technician getFirstTechnician() {
        if (tail == null) {
            return null; // List is empty
        }
        current = tail.next; // Set current to head
        return current.technician;
    }

    // Print the current rotation list
    public void printTechnicianList() {
        if (tail == null) {
            System.out.println("Technician list is empty.");
            return;
        }

        StringBuilder rotationList = new StringBuilder();
        Node temp = tail.next; // Start from head

        do {
            // Mark current position with * for visualization
            String marker = (temp == current) ? "*" : "";
            rotationList.append(String.format("%s%s %s (%s)%s --> ",
                    marker,
                    temp.technician.getProfile().getFirstName(),
                    temp.technician.getProfile().getLastName(),
                    temp.technician.getLocation().getCity(),
                    marker));

            temp = temp.next;
        } while (temp != tail.next);

        // Remove the last arrow
        if (rotationList.length() > 0) {
            rotationList.setLength(rotationList.length() - 5);
        }

        System.out.println(rotationList.toString());
    }

    // Helper method to get current technician without moving pointer
    public Technician getCurrentTechnician() {
        if (current == null || tail == null) {
            return null;
        }
        return current.technician;
    }

    // Get the size of the list
    public int size() {
        return size;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the enum for the imaging service type
    public Radiology getRoomForService(String imagingService) {
        switch (imagingService.toUpperCase()) {
            case "XRAY":
                return Radiology.XRAY;
            case "ULTRASOUND":
                return Radiology.ULTRASOUND;
            case "CATSCAN":
                return Radiology.CATSCAN;
            default:
                throw new IllegalArgumentException("Invalid imaging service: " + imagingService);
        }
    }
}