package com.yashu.interview.map.linkedhashset;

import java.util.Iterator;

/**
  Collection used to store list of elements and retains insertion order.
 **/
public interface OrderedSet<T> {
		
	/**
	 Inserts elements into the collection and @throws 
	 IllegalArgumentException if the input is null.
	 **/
	public void insert(T value);
	
	/**
	 
	Deletes elements from the list and retains insertion order.
	@throws InputMismatchException if the element doesn't exist.
	 
	 **/
	public boolean delete(T value);
	
	/**
		Checks uniqueness of the list during insertion.
	 **/
	public boolean exists(T value);
	
	/**
	 * Iterates through all the elements in the list.
	 * @return Iterator Object.
	 */
	public Iterator<Object> iterator();
	
	
}
