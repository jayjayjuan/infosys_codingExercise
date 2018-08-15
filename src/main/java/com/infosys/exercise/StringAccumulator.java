package com.infosys.exercise;

import java.util.regex.Pattern;

public class StringAccumulator {

	public static void main(String[] args) {
		System.out.println("sum: "+add("10\n01,2"));
	}
	
	public static int add(String numbers) {
		System.out.println("numbers: "+numbers);
		int sum = 0;
		String otherDelim = "//";
		Pattern pattern = Pattern.compile(","); //default delimeter
		Pattern newLinePat = Pattern.compile("\n"); //new line delimeter
		if(numbers != null && !numbers.equals("")) {
			System.out.println("index of //: "+numbers.indexOf(otherDelim));
			//Check if not using the default delimeter
			if(numbers.indexOf(otherDelim) < 0) {
				//If contains \n
				System.out.println("contains \\n: "+ numbers.contains("\n"));
				if(numbers.contains("\n")) {
					sum = pattern.splitAsStream(numbers)
									.filter(i -> i.contains("\n"))
									.map(j -> newLinePat.splitAsStream(j))
									.map(k -> k.mapToInt(Integer::valueOf).filter(l -> l<=1000).sum()).mapToInt(Integer::intValue).sum();
					sum += pattern.splitAsStream(numbers).filter(i -> !i.contains("\n")).mapToInt(Integer::valueOf).filter(i -> i<=1000).sum();
				} else {
					sum = pattern.splitAsStream(numbers).mapToInt(Integer::valueOf).filter(i -> i<=1000).sum();
				}		
			} else {
				
			}
		}
		
		return sum;
	}

}
