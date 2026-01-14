package shortest_path;

// A generic class that implements a node-based stack.
public class CustomStack<E> { // E is called a generic type.
// Inner class for stack nodes
private static class StackNode<E> {
   E data; // Data stored in the node called data
   StackNode<E> next; // The reference/pointer to the next node called next

   StackNode(E data) {
      this.data = data; // Stores the element held by this node
      this.next = null; // Initializes next to null to point to the other node (will be linked when the stack grows) 
   }
}

// Private data fields called top,size
private StackNode<E> top; // Points to the top of the stack
private int size = 0; // The number of stored elements initialized to 0 as default

// A constructor that creates an empty stack
public CustomStack() {
   top = null; // Stack starts as empty 
   // Time Complexity: O(1) 
   // (Accesses the top element and assigns to null, both takes constant-time.)
}

// Returns true if the stack is empty and false otherwise
public boolean isEmpty() {
   return top == null;
   // Time Complexity: O(1) 
   // (Accesses top and compares it with null, both takes constant-time.)
}

// Returns the top element without removing it
public E peek() {
   if (isEmpty()) 
      return null; // Return null if the stack is empty
      return top.data; // Return the data stored at the top of the stack
   // Time Complexity: O(1)
   // (Accesses the data field of the top node takes constant time.)
}

// Removes and returns the top element 
public E pop() {
   if (isEmpty()) 
      return null; // Returns null if the stack is empty
   E data = top.data; // Store the top element to return
   top = top.next; // Move the top pointer to the next node
   size--; // Decrease the size because of removal one by one
   return data; // Return the removed data 
   // Time Complexity: O(1)
   // (isEmpty method, returning null, assigning top element into a new variable, moving the top pointer and decrementing stack's size, takes constant-time.)
}

// Adds an element to the top of the stack
public void push(E e) {
   StackNode<E> newNode = new StackNode<>(e); // Create a new node with the element
   newNode.next = top; // New node pointer to the current top
   top = newNode; // Assign the new node as top
   size++; // Increase the size one by one
   // Time Complexity: O(1)
   // (Creating a new node, reassigning the pointer, and incrementing the size, all takes constant-time O(1).)
   }

public CustomStack<E> clone() { 
    CustomStack<E> clonedStack = new CustomStack<>(); // Creates a new stack instance that will temporarily store copied elements
    StackNode<E> currentNode = this.top; // Starts traversal from the top of the original stack

    while (currentNode != null) { // Traverses each node in the original stack from top to bottom
        clonedStack.push(currentNode.data); // Copies the data of the current node into the new stack with push operation
        currentNode = currentNode.next; // Moves to the next node in the original stack
       // Time Complexity: O(n)
    // (Iterates through all n elements in the original stack, performs a push operation with O(n) time complexity.)
    }
   
    // Creates a second stack to preserve the original order of elements in the stack
    CustomStack<E> reversedClonedStack = new CustomStack<>();
    while (!clonedStack.isEmpty()) { 
        reversedClonedStack.push(clonedStack.pop()); // Transfers elements from the temporary stack to the final stack
    }
    // Time Complexity: O(n)
    // (We pop each of the n elements from the intermediate stack and push them into the reversed stack, each operation being O(1).)

    return reversedClonedStack; // Returns a deep copy of the original stack with the original order
    // Total Time Complexity: O(n)
}

public int size() {
    return size;
    // Time Complexity: O(1)
    // (Returning the value of the size takes constant-time.)
}

// Returns the stack's elements in [ ] form
@Override // Overriding the toString method in the Object class
public String toString() {
   if (isEmpty())
      return "[]"; // Return "[]" if the stack is empty 
   // Time Complexity: O(1)

   StringBuilder str = new StringBuilder("["); // StringBuilder variable to build the string
   StackNode<E> current = top; // Pointer to traverse the stack from top to bottom

   while (current != null) { // Iterates through the stack from top to bottom
      str.append(current.data); //Appends the string representation of the current element
      if (current.next != null)
         str.append(", "); // Append ", " if current is not the last element
      current = current.next; // Go to the next node from current
   }
   str.append("]"); // Close the string with ]
   return str.toString();
   // Time Complexity: O(n)
   // (Appending all n elements in the stack takes O(n).)
}
}




