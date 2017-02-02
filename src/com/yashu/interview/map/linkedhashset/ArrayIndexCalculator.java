package com.yashu.interview.map.linkedhashset;

public class ArrayIndexCalculator {
	public int getIndex(Object obj, int arrayLength) {
		if(arrayLength<=0 || obj == null){
			throw new IllegalArgumentException();
		}else{
		int hash = obj.hashCode();
		return Math.abs(hash%arrayLength);  
		}
	}

}
