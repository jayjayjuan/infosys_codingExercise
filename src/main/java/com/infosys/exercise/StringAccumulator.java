package com.infosys.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

public class StringAccumulator {
	
	public StringAccumulator() {}
	
	public final String[] reservedRegexChars = {"[","\\","^","$",".","|","?","*","+","(",")"};
	public final Pattern pattern = Pattern.compile(","); //default delimeter
	public final Pattern newLinePat = Pattern.compile("\n"); //new line delimeter
	public final Pattern newDelimPat = Pattern.compile("\\|"); //separator for the new delimeters
	
	public int add(String numbers) {
		int sum = 0;
		String otherDelim = "//";
		
		if(numbers != null && !numbers.equals("")) {
			//Check if not using the default delimeter
			if(numbers.indexOf(otherDelim) < 0) {
				//If contains \n
				if(numbers.contains("\n")) {
					//Add first those strings with \n
					sum += addWithNewLine(pattern.splitAsStream(numbers));
					
					//Then add those strings w/o \n
					sum += addWithoutNewLine(pattern.splitAsStream(numbers).filter(i -> !i.contains("\n")));
				} else {
					sum += addWithoutNewLine(pattern.splitAsStream(numbers));
				}		
			} else {
				String newDelim = numbers.substring(2, numbers.indexOf("\n")); 
				String newNumbers = numbers.substring((numbers.indexOf("\n")+1), numbers.length());
				List<String> numbs = new ArrayList<String>();
				List<String> tNumbs = new ArrayList<String>();
				List<Pattern> allNewDelims = new ArrayList<Pattern>();
				List<String> tNewDelims = new ArrayList<String>();
				numbs.add(newNumbers);
				
				//Get those one char delimeter which is not reserved
				newDelimPat.splitAsStream(newDelim).filter(i -> (i.length()==1 && !ArrayUtils.contains(reservedRegexChars, i)))
								.map(i -> Pattern.compile(i)).collect(Collectors.toCollection(() -> allNewDelims));
				//Get those one char delimeter which is reserved
				newDelimPat.splitAsStream(newDelim).filter(i -> (i.length()==1 && ArrayUtils.contains(reservedRegexChars, i)))
								.map(i -> Pattern.compile("\\"+i)).collect(Collectors.toCollection(() -> allNewDelims));
				//Get those more than one char delimeter
				newDelimPat.splitAsStream(newDelim).filter(i -> i.length()>1).collect(Collectors.toCollection(() -> tNewDelims));
				for(String tNewDelim : tNewDelims) {
					StringBuilder tDelim = new StringBuilder();
					for(int i=0; i<tNewDelim.length(); i++) {
						String tt = tNewDelim.substring(i, (i+1));
						if(ArrayUtils.contains(reservedRegexChars, tt)) {
							tDelim.append("\\");
						}
						tDelim.append(tNewDelim.charAt(i));
					}
					allNewDelims.add(Pattern.compile(tDelim.toString()));
				}		
				
				Iterator<Pattern> newDelimList = allNewDelims.iterator();
				while(newDelimList.hasNext()) {					
					Pattern tPat = newDelimList.next();
					for(String numb : numbs) {
						tNumbs.addAll(Arrays.asList(tPat.split(numb)));
					}
					numbs.clear();
					numbs.addAll(tNumbs);
					tNumbs.clear();
				}
				
				//Add first those strings with \n
				sum += addWithNewLine(numbs.stream());
				
				//Add those w/o \n
				sum += addWithoutNewLine(numbs.stream().filter(i -> !i.contains("\n")));
			}
		}
		
		return sum;
	}
	
	private int addWithNewLine(Stream<String> numbers) {
		return numbers.filter(i -> i.contains("\n")).map(j -> newLinePat.splitAsStream(j))
						.map(k -> k.mapToInt(Integer::valueOf).filter(l -> l<=1000).sum())
						.mapToInt(Integer::intValue).sum();
	}
	
	private int addWithoutNewLine(Stream<String> numbers) {
		return numbers.mapToInt(Integer::valueOf).filter(i -> i<=1000).sum();
	}

}
