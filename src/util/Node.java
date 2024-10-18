package util;

import ruclinic.Technician;

/**
 * Represents a node for the circular linked list
 * 
 * 
 * 
 * @author Paldeep Sekhon
 * @author Aditya Ponni
 */

class Node {
    Technician technician;
    Node next;

    public Node(Technician technician) {
        this.technician = technician;
    }
}
