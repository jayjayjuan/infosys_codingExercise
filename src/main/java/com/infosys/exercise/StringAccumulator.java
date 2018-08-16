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
	
	public final String[] reservedRegexChars = {"[","\\","^","$",".","|","?","*","+","(",")"};
	
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
				List<String> tNumbs = new ArrayList<String>();
				List<Pattern> allNewDelims = new ArrayList<Pattern>();
				List<String> tNewDelims = new ArrayList<String>();
				numbs.add(newNumbers);
				
				//Get those one char delimeter which is not reserved
				newDelimPat.splitAsStream(newDelim).filter(i -> (i.length()==1 && Arrays.binarySearch(reservedRegexChars, i) < 0))
								.map(i -> Pattern.compile(i)).collect(Collectors.toCollection(() -> allNewDelims));
				//Get those one char delimeter which is reserved
				newDelimPat.splitAsStream(newDelim).filter(i -> (i.length()==1 && Arrays.binarySearch(reservedRegexChars, i) >= 0))
								.map(i -> Pattern.compile("\\"+i)).collect(Collectors.toCollection(() -> allNewDelims));
				//Get those more than one char delimeter
				newDelimPat.splitAsStream(newDelim).filter(i -> i.length()>1).collect(Collectors.toCollection(() -> tNewDelims));
				for(String tNewDelim : tNewDelims) {
					char[] tNewDelimChars = tNewDelim.toCharArray();
					StringBuilder tDelim = new StringBuilder();
					for(int i=0; i<tNewDelimChars.length; i++) {
						System.out.println(i+": "+tNewDelimChars[i]);
						String tt = String.valueOf(tNewDelimChars[i]);
						System.out.println("tt:"+tt);
						if(Arrays.binarySearch(reservedRegexChars, tt) >= 0) {
							tDelim.append("\\");
						}
						tDelim.append(tNewDelimChars[i]);
					}
					System.out.println("tDelim.toString(): "+tDelim.toString());
					allNewDelims.add(Pattern.compile(tDelim.toString()));
				}
				System.out.println("tNewDelims: "+tNewDelims);
				System.out.println("allNewDelims: "+allNewDelims);
//				Iterator<Pattern> newDelimList = newDelimPat.splitAsStream(newDelim)
//															.filter(i -> (i.length()==1 && Arrays.binarySearch(reservedRegexChars, i) < 0))
//															.map(i -> Pattern.compile(i))
//															.collect(Collectors.toList())
//															.iterator();				
//				while(newDelimList.hasNext()) {					
//					Pattern tPat = newDelimList.next();
//					System.out.println("tpat: "+tPat);
//					System.out.println("numbs: "+numbs);
//					Arrays.stream(numbs.toArray()).map(i -> tPat.splitAsStream(i.toString())).map(k -> tNumbs.add(k.toString()));
//					numbs = new ArrayList<String>();
//					numbs.addAll(tNumbs);
////					tNumbs.addAll(Arrays.stream(numbs.toArray()).map(i -> tPat.splitAsStream(i.toString())).map(k -> k.toString()).collect(Collectors.toList()));
////					System.out.println("tNumbs: "+tNumbs);
//					System.out.println("tNumbs after: "+tNumbs);
//					System.out.println("numbs after: "+numbs);
////					newDelimList.next().spli;
//				}
				
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
	
	private int forPrint(String k) {
		System.out.println("******* for printing k: "+k);
		
		return 0;
	}

}
