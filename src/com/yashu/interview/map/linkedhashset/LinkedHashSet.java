package com.yashu.interview.map.linkedhashset;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedHashSet<T> implements OrderedSet<T>{

	private	Node<T>[] array;
	private ArrayIndexCalculator indexCalculator;
	private LinkedList<T> linkedList;
	private int listCount = 0;
	


	/**
	 * Parameterized constructor
	 * @param initialSize - Initial size of the set
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashSet(int initialSize){
		if(initialSize<=0){
			throw new IllegalArgumentException();
		}else{
		array = new Node[initialSize];
		indexCalculator = new ArrayIndexCalculator();
		linkedList = new LinkedList<T>();
		}
	
	}
	 
	// Package visible only - for testing purposes
	@SuppressWarnings("unchecked")
	LinkedHashSet(int initialSize, ArrayIndexCalculator indexCalculator) {
		array = new Node[initialSize];
		this.indexCalculator = indexCalculator;
		linkedList = new LinkedList<T>();
	}
	// Returns the size of the Array - for testing purposes
        int getLengthOfArray() {
		return array.length;
	}

	@SuppressWarnings("unchecked")
	private void createNewArray(int length) {
		array = new Node[length];
	}
	
	//Iterator
	 class LinkedHashSetIterator implements Iterator<Object>{
		private Node<T> temp ;
		
		public LinkedHashSetIterator(){
		temp = null;
		}
		
		public boolean hasNext() {
			return temp!=linkedList.getTail();
		}
		
		public Object next() {
			if(temp == null){
				temp = linkedList.getHead();
				if(temp==null){
					throw new NoSuchElementException();
				}
				return temp.getData();
			}else{
				if(temp.getNext() == null){
					throw new NoSuchElementException();
				}
			temp = temp.getNext();
			}
			return temp.getData();
		  }
		
		 public void remove() {
                 throw new UnsupportedOperationException();
         }
		
	}
	
	public Iterator<Object> iterator() {
		return new LinkedHashSetIterator();
	}
	
	public void insert(T value) {
		if(value == null){
			throw new IllegalArgumentException();
		}
		//Checks uniqueness of the set.
		if(exists(value)){return;}
		//Doubles the size of Array if it is full.
		if(listCount == (2*array.length)){
			doubleArray(array.length);
		}
		listCount++;
		Node<T> newNode = new Node<T>(value);
		//Using the Hash Function the index of the node is determined.
		int indexOfString = hashFunction(value);
		//If the index is not null, collision is avoided using LinkedList. 
		if(collision(value)){
			avoidCollision(newNode,linkedList.getTail(),indexOfString);
			return;
		}
		insertIntoArray(indexOfString, newNode);
		linkedList.insert(newNode);
	}


	void doubleArray(int length) {
		Node<T>[] temp = array;
		createNewArray(2*length);
		for(int i =0;i<length;i++){
			if(temp[i]!=null){
			int tempIndex = hashFunction(temp[i].getData());
			array[tempIndex] = temp[i];
			}
		}
	}

	void avoidCollision(Node<T> newNode, Node<T> tail, int indexOfString) {
		Node<T> temp = array[indexOfString];
		while(temp.getRight()!=null){
			temp = temp.getRight();
		}
		temp.setRight(newNode);
		linkedList.getTail().setNext(newNode);
		newNode.setPrevious(tail);
		linkedList.setTail(linkedList.getTail().getNext());
	
	}

	private boolean collision(T value) {
		return array[hashFunction(value)]!=null;
	}

	 public boolean exists(T value) {
		if(array[hashFunction(value)]==null){
			return false;
		}else{
			Node<T> temp = array[hashFunction(value)];
			while(temp!= null){
				if(temp.getData().equals(value)){
					return true;
				}
				temp =temp.getRight();
			}
			return false;
		}
	}

	private void insertIntoArray(int indexOfString, Node<T> newNode) {
		array[indexOfString] = newNode;
	}

	int hashFunction(T value) {
		return indexCalculator.getIndex(value,array.length);
	}
	
   //Returns true if set is empty
	boolean isEmpty(){
		return linkedList.getHead() == null;
	}
	/**
	 * Removes specified element from the set if it is present.
	 */
	public boolean delete(T value) {
		
		if(value == null){
			throw new IllegalArgumentException();
		}
		/**
		 * If the element doesn't exists returns false.
		 */
		if(!exists(value)){
			return false;
		}else{
			int index = hashFunction(value);
			Node<T> element = array[index];
			if(isCollisionLinkedList(element)){
				Node<T> newNode = deleteLinkedList(element,value);
				array[index] = newNode;
				return true;
			}else{
				array[index] = null;
				linkedList.delete(element);
			} 
		}
		return true;
				 
	}

	private Node<T> deleteLinkedList(Node<T> element,T value) {
		if(element.getData().equals(value)){
			linkedList.delete(element);
			return element.getNext();
		}else{
			Node<T> previous = null;
			Node<T> current = element;
			while(current!=null){
				if(current.getData().equals(value)){
					linkedList.delete(current);
					previous.setRight(current.getRight());
				}
				previous = current;
				current = current.getNext();
			}
			return element;
		}
		
	}

	//Checks if the element is in collision list.
	private boolean isCollisionLinkedList(Node<T> element) {
		return element.getRight()!=null;
		
	}

	
	
}
