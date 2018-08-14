package com.infosys.exercise;

import java.util.regex.Pattern;

public class StringAccumulator {

	public static void main(String[] args) {
		System.out.println("sum: "+add("1001,2"));
	}
	
	public static int add(String numbers) {
		System.out.println("numbers: "+numbers);
		int sum = 0;
		String otherDelim = "//";
		Pattern pattern = Pattern.compile(","); //default delimeter
		if(numbers != null && !numbers.equals("")) {
			System.out.println("index of //: "+numbers.indexOf(otherDelim));
			//Check if not using the default delimeter
			if(numbers.indexOf(otherDelim) < 0) {
				
				sum = pattern.splitAsStream(numbers).mapToInt(Integer::valueOf).sum();
						
			} else {
				
			}
		}
		
		return sum;
	}

}
