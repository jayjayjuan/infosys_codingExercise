package com.infosys.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringAccumulator {
	
	public StringAccumulator() {}
	
	
	public int add(String numbers) {
		System.out.println("numbers: "+numbers);
		int sum = 0;
		String otherDelim = "//";
		Pattern pattern = Pattern.compile(","); //default delimeter
		Pattern newLinePat = Pattern.compile("\n"); //new line delimeter
		Pattern newDelimPat = Pattern.compile("\\|"); //separator for the new delimeters
		if(numbers != null && !numbers.equals("")) {
			//Check if not using the default delimeter
			if(numbers.indexOf(otherDelim) < 0) {
				//If contains \n
				if(numbers.contains("\n")) {
					//Add first those strings with \n
					sum = pattern.splitAsStream(numbers)
									.filter(i -> i.contains("\n"))
									.map(j -> newLinePat.splitAsStream(j))
									.map(k -> k.mapToInt(Integer::valueOf).filter(l -> l<=1000).sum())
									.mapToInt(Integer::intValue).sum();
					//Then add those strings w/o \n
					sum += pattern.splitAsStream(numbers).filter(i -> !i.contains("\n")).mapToInt(Integer::valueOf).filter(i -> i<=1000).sum();
				} else {
					sum = pattern.splitAsStream(numbers).mapToInt(Integer::valueOf).filter(i -> i<=1000).sum();
				}		
			} else {
				System.out.println("first index of \\n: "+numbers.indexOf("\n"));
				String newDelim = numbers.substring(2, numbers.indexOf("\n"));
				String newNumbers = numbers.substring((numbers.indexOf("\n")+1), numbers.length());
				System.out.println("newdelim: "+newDelim);
				System.out.println("newnumbers: "+newNumbers);
				List<String> numbs = new ArrayList<String>();
				numbs.add(newNumbers);
				Object[] temp;
				Iterator<Pattern> newDelimList = newDelimPat.splitAsStream(newDelim).map(i -> Pattern.compile("\\"+i)).collect(Collectors.toList()).iterator();
				
				while(newDelimList.hasNext()) {
					Pattern tPat = newDelimList.next();
					System.out.println("tpat: "+tPat);
					temp = numbs.toArray();
					numbs.clear();
					System.out.println("temp: "+temp);
					System.out.println("numbs: "+numbs);
					Arrays.stream(temp).map(i -> tPat.splitAsStream(i.toString())).map(k -> numbs.add(k.toString()));
					System.out.println("numbs after: "+numbs);
//					newDelimList.next().spli;
				}
				
//				Arrays.stream(newDelimList.toArray());
//								 .map(i -> Pattern.compile(i).splitAsStream(newNumbers)
//												.filter(l -> l.contains("\n"))
//												.map(j -> newLinePat.splitAsStream(j))
//												.map(k -> k.mapToInt(Integer::valueOf).filter(l -> l<=1000).sum())
//												.mapToInt(Integer::intValue).sum());

				
			}
		}
		
		return sum;
	}

}
