/**
 * @author Pablo Santamarta Esteban <pablosesteban@gmail.com>
 */
package algorithms.adt.impl;

import algorithms.adt.Stack;
import java.util.Iterator;

/**
 * An implementation of the Stack ADT based on a resizing array implementation.
 * It dynamically adjust the size of the internal array so that it is both sufficiently large to hold all of the elements and
 * not so large as to waste an excessive amount of space.
 * If there is no room in the internal array, it doubles the size. If the stack size is less than one-fourth the array size,
 * it halves the array size. This way it can accommodate a substantial number of push() and pop() operations before having to
 * change the size of the array again. The stack never overflows and never becomes less than one-quarter full (unless the stack is empty).
 * The primary performance characteristic of this implementation is that the push and pop operations take time independent of the stack size.
 * null elements are not allowed.
 * The implementation avoids loitering (holding a reference to an element that is no longer needed) by setting the array entry
 * corresponding to the popped element to null, making it possible for the garbage collection system to reclaim the memory associated
 * with the popped item when the client is finished with it.
 * The order of iteration follows the FIFO, First-In-First-Out, policy, as iterates the internal array representation of the stack.
 * 
 * @param <E> type of elements stored in the stack
 */
public class ResizingArrayStack<E> implements Stack<E> {
    private E[] elements;
    private int pointer;
    
    public ResizingArrayStack(int initialCapacity) {
        // generic array creation is disallowed in Java for historical and technical reasons
        elements = (E[]) new Object[initialCapacity];
    }
    
    @Override
    public void push(E element) {
        if (element == null) {
            throw new IllegalArgumentException("null elements are not allowed");
        }
        
        if (size() == elements.length) {
            resize(length() * 2);
        }
        
        elements[pointer++] = element;
    }

    @Override
    public E pop() {
        if (size() == 0) {
            throw new IllegalStateException("stack is empty");
        }
        
        E element = elements[--pointer];
        
        elements[pointer] = null;
        
        if (size() == length()/4) {
            resize(length()/2);
        }
        
        return element;
    }

    @Override
    public boolean isEmpty() {
        return pointer == 0;
    }
    
    @Override
    public int size() {
        // returns the number of elements in the stack, not the capacity
        return pointer;
    }

    @Override
    public String toString() {
        String name = getClass().getSimpleName();
        
        if (size() == 0) {
            return name + "{elements: {}}";
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(name);
        sb.append("{elements: {");
        
        for (Iterator iterator = iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
            sb.append(", ");
        }
        
        int lastComma = sb.lastIndexOf(",");
        sb.deleteCharAt(lastComma);
        
        int lastWitheSpace = sb.lastIndexOf(" ");
        sb.deleteCharAt(lastWitheSpace);
        
        sb.append("}}");
        
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new StackIterator<>();
    }
    
    private class StackIterator<E> implements Iterator<E> {
        private int pointer = 0;
        
        @Override
        public boolean hasNext() {
            return pointer < elements.length && elements[pointer] != null;
        }

        @Override
        public E next() {
            return (E) elements[pointer++];
        }
    }
    
    /**
     * Gets the actual capacity of the internal array that holds the elements
     * 
     * @return the length of the internal array that holds the elements
     */
    private int length() {
        return elements.length;
    }
    
    private void resize(int size) {
        E[] tmp = (E[]) new Object[size];
        
        for (int i = 0; i < pointer; i++) {
            tmp[i] = elements[i];
        }
        
        elements = tmp;
    }
    
    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack(5);
        
        System.out.println("----Push Pablo");
        stack.push("Pablo");
        System.out.println("----Push Carlos");
        stack.push("Carlos");
        
        try {
            System.out.println("----Push null");
            stack.push(null);
        } catch (IllegalArgumentException iae) {
            System.out.println("\t--" + iae);
        }
        
        System.out.println("----Push Andres");
        stack.push("Andres");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        System.out.println("----length: " + stack.length());
        
        System.out.println("----Push Michico");
        stack.push("Michico");
        System.out.println("----Push Alfredo");
        stack.push("Alfredo");
        System.out.println("----Push Maria on full stack");
        stack.push("Maria");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        System.out.println("----length: " + stack.length());
        
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        System.out.println("----Pop " + stack.pop());
        
        try {
            System.out.println("----Pop on empty stack");
            stack.pop();
        } catch (IllegalStateException ise) {
            System.out.println("\t--" + ise);
        }
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        System.out.println("----length: " + stack.length());
        
        System.out.println("----Push Pablo");
        stack.push("Pablo");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        System.out.println("----length: " + stack.length());
        
        System.out.println("----Push Carlos");
        stack.push("Carlos");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        System.out.println("----length: " + stack.length());
        
        System.out.println("----Push Andres");
        stack.push("Andres");
        
        System.out.println("----stack: " + stack);
        System.out.println("----size: " + stack.size());
        System.out.println("----length: " + stack.length());
        
        System.out.println("----Iteration order");
        for (String element : stack) {
            System.out.println("\t--"+ element);
        }
    }
}
