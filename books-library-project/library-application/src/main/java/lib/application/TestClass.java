package lib.application;

import java.util.Random;

public class TestClass {

	public static void main(String[] args) 
	{
		Random random = new Random();
		System.out.println(random.nextInt(100000));
		System.out.println(" ");
		System.out.println(Math.random());
		System.out.println(" ");
		random.ints(10, 1000, 100000).forEach(System.out::println);
	}

}
