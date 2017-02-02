package com.yashu.interview.map.linkedhashset;

import static org.mockito.Mockito.when;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class LinkedHashSetTest {

	private com.yashu.interview.map.linkedhashset.LinkedHashSet<String> actualList;
	private java.util.LinkedHashSet<String> expectedList;
	
	@Before
	public void setUp() {
		actualList = Mockito.spy(new com.yashu.interview.map.linkedhashset.LinkedHashSet<String>(4));
		expectedList = new LinkedHashSet<String>();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArraySize(){
		ArrayIndexCalculator indexCalculator = new ArrayIndexCalculator();
		indexCalculator.getIndex("yashu", 0);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullObject(){
		ArrayIndexCalculator indexCalculator = new ArrayIndexCalculator();
		indexCalculator.getIndex(null, 4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInitialArraySize(){
		actualList = new com.yashu.interview.map.linkedhashset.LinkedHashSet<String>(-1);
	}

	
	@Test
	public void testInsertionOrder(){
		insertIntoActualList("yashu","uttam","samatha");
		InOrder inOrder = Mockito.inOrder(actualList);
		inOrder.verify(actualList).insert("yashu");
		inOrder.verify(actualList).insert("uttam");
		inOrder.verify(actualList).insert("samatha");
	}
	

	@Test
	public void testDuplicates(){
		
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(2);

		insertIntoActualList("yashu","yashu","samatha");
		
		Mockito.verify(actualList,Mockito.times(5)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		insertIntoExpectedList("yashu","yashu","samatha");
		verifyLists(expectedList,actualList);
	}

	@Test
	public void testEmptyList(){
		assertTrue(actualList.isEmpty());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorNextWhenEmpty(){
		Iterator<?> iter = actualList.iterator();
		iter.next();
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorNextSingleElement(){
		Iterator<?> iter = actualList.iterator();
		actualList.insert("yashu");
		iter.next();
		iter.next();
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testNull(){
		actualList.insert(null);
	}
	
	
	@Test
	public void testDeletingAllElements(){
		insertIntoActualList("yashu","uttam","sam");
		deleteElementsFromActualList("sam","uttam","yashu");
		assertTrue(actualList.isEmpty());
	}
	
	@Test
	public void testDeletionOfNonexistentElement(){
		insertIntoActualList("yashu","yash");
		assertFalse(actualList.delete("uttam")); 
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeletingNull(){
		actualList.delete(null);
	}
	@Test
	public void testInsert(){
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		actualList.insert("yashu");
		Mockito.verify(actualList).insert(arg.capture());
		assertEquals("yashu",arg.getValue());
	}
	@Test
	public void testDeletionFromCollsionListTail(){
		
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(2);
		
		insertIntoActualList("yashu","uttam","samatha");
		mockitoVerify(3,"yashu","samatha");
		Mockito.verify(actualList, Mockito.times(4)).hashFunction(Matchers.eq("uttam"));
		
		
		
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);	
	}
	
	@Test
	public void testDeletionFromCollsionListTail2(){
		ArrayIndexCalculator indexCalculator = Mockito.mock(ArrayIndexCalculator.class);
		actualList = new com.yashu.interview.map.linkedhashset.LinkedHashSet<String>(4, indexCalculator);
		
		when(indexCalculator.getIndex("yashu", 4)).thenReturn(1);
		when(indexCalculator.getIndex("uttam", 4)).thenReturn(1);
		when(indexCalculator.getIndex("samatha", 4)).thenReturn(2);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		
		Mockito.verify(indexCalculator,Mockito.times(3)).getIndex("yashu",4);
		Mockito.verify(indexCalculator,Mockito.times(4)).getIndex("uttam",4);
		Mockito.verify(indexCalculator,Mockito.times(3)).getIndex("samatha",4);
		
		
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);	
	}
	
	@Test
	public void testDeletionFromCollsionListMiddle(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(1);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		mockitoVerify(4,"uttam","samatha");
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromCollsionListHead(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(1);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		mockitoVerify(4,"uttam","samatha");
		
		actualList.delete("yashu");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("yashu");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListTail(){
		mockitoWhen("yashu","uttam","samatha");
		
		insertIntoActualList("yashu","uttam","samatha");
		
		mockitoVerify(3,"yashu","uttam","samatha");
		
		actualList.delete("samatha");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("samatha");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListMiddle(){
		mockitoWhen("yashu","uttam","samatha");
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(2);
		when(actualList.hashFunction("samatha")).thenReturn(3);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		mockitoVerify(3,"yashu","uttam","samatha");
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListHead(){
		mockitoWhen("yashu","uttam","samatha");
		
		insertIntoActualList("yashu","uttam","samatha");
		
		mockitoVerify(3,"yashu","uttam","samatha");
		
		actualList.delete("yashu");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("yashu");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletingOrderingLinkedListElements(){
		mockitoWhen("yashu","uttam","samatha");
		
		insertIntoActualList("yashu","uttam","samatha");
		mockitoVerify(3,"yashu","uttam","samatha");
		
		deleteElementsFromActualList("yashu","uttam","samatha");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("yashu");		
		expectedList.remove("uttam");
		expectedList.remove("samatha");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testParameterType(){
		actualList.insert("yashu");
		Mockito.verify(actualList).insert(Mockito.anyString());
	}
	
	@Test
	public void testDoubleArraySize(){
		insertIntoActualList("yashu","uttam","samatha","cherry","divya","kumar","prabha","rahul","jasdnk");
		insertIntoExpectedList("yashu","uttam","samatha","cherry","divya","kumar","prabha","rahul","jasdnk");
		Mockito.verify(actualList).doubleArray(4);
		assertEquals(8,actualList.getLengthOfArray());
		verifyLists(expectedList,actualList);
		
	}
	
	@Test
	public void testHashValueAfterDoublingSize(){
		insertIntoActualList("yashu","uttam","samatha","cherry","divya","kumar","prabha");
		int hashValueWithInitialSize = actualList.hashFunction("yashu");
		insertIntoExpectedList("yashu","uttam","samatha","cherry","divya","kumar","prabha");
		insertIntoActualList("rahul","iowa","yashu");
		insertIntoExpectedList("rahul","iowa","yashu");
		Mockito.verify(actualList).doubleArray(4);
		assertEquals(8,actualList.getLengthOfArray());
		int hashValueWithDoubleSize = actualList.hashFunction("yashu");
		assertEquals(hashValueWithInitialSize,hashValueWithDoubleSize);
		verifyLists(expectedList,actualList);
	}
	

	
	private void verifyLists(Collection<String> expected, com.yashu.interview.map.linkedhashset.LinkedHashSet<String> actual) {
		Iterator<String> expectedIterator = expected.iterator();
		Iterator<?> actualIterator = actual.iterator();
		
		while(expectedIterator.hasNext()) {
			assertEquals(expectedIterator.next(), actualIterator.next());			
		}
	}

	private void insertIntoActualList(String ... strings) {
		for(int i = 0; i < strings.length; i++) {
			actualList.insert(strings[i]);
		}
	}
	private void mockitoWhen(String ... strings) {
		for(int i = 0; i < strings.length; i++) {
			when(actualList.hashFunction(strings[i])).thenReturn(i);
		}
	}
	
	private void mockitoVerify(int repetitions,String ... strings) {
		for(int i = 0; i < strings.length; i++) {
			Mockito.verify(actualList,Mockito.times(repetitions)).hashFunction(Matchers.eq(strings[i]));
		}
	}
	
	private void deleteElementsFromActualList(String ...string){
		for(int i=0;i<string.length;i++){
			actualList.delete(string[i]);
		}
	}
	

	private void insertIntoExpectedList(String ... strings) {
		for(int i = 0; i < strings.length; i++) {
			expectedList.add(strings[i]);
		}
	}
	
}
