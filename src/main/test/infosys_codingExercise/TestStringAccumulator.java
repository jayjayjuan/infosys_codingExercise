package infosys_codingExercise;

import org.junit.jupiter.api.Test;

import com.infosys.exercise.StringAccumulator;

public class TestStringAccumulator {
	
	public TestStringAccumulator() {}
	
	private StringAccumulator sa = new StringAccumulator();
	private String toTest = "//^|***|%\n10\n01***2%3";
	
	@Test
	public void test1(){
		System.out.println("sum: "+sa.add(toTest));
	}
}
