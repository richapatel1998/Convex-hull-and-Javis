package edu.iastate.cs228.hw4;

/**
 *  
 * @author Richa Patel
 *
 */

/**
 * 
 * This class executes two convex hull algorithms: Graham's scan and Jarvis' march, over randomly
 * generated integers as well integers from a file input. It compares the execution times of 
 * these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws IllegalStateException 
	 **/
	public static void main(String[] args) throws IllegalStateException, FileNotFoundException 
	{		
		// TODO 
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 5 of the project description. 
		// 	
		//ConvexHull[] algorithms = new ConvexHull[2]; (THIS WAS GIVEN FOR SKELETON CODE)
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 5). 
		//Scanner scan = new Scanner(System.in); (THIS WAS GIVEN FOR SKELETTON CODE)
		
		int trials = 1;
		int userChoice;
		int i = 0;
		//just remember that one of the things that the user will input will be 
		//whether to take an input from a file or randomly generate points
		Scanner scan = new Scanner(System.in);
		ConvexHull[]algorithms = new ConvexHull[2];
		System.out.println("Comparison of Convex Hull Algorithms ");
		System.out.println("keys: 1 (random values) 2 (file input) 3 (exit)");
		while(true) {
			System.out.print("Trial"+ " "+trials+ ": ");
			userChoice = scan.nextInt();
			
			switch(userChoice) {
			case 1:
				System.out.print("Enter number of random points: ");
				int length = scan.nextInt();
				Point[]randomPoints = generateRandomPoints(length, new Random());
				algorithms[0] = new GrahamScan(randomPoints);
				algorithms[1] = new JarvisMarch(randomPoints);
				
				
				System.out.println("\nalgorithm\tsize\ttime (ns)");
				System.out.println("\n");
				
				for(i = 0; i < algorithms.length; i++ ) {
					algorithms[i].constructHull();
					algorithms[i].draw();
					algorithms[i].writeHullToFile();
					System.out.println(algorithms[i].stats());
				}
				System.out.println("\n");
				trials++;
				break;
				
			case 2: 
				System.out.println("Points from a file ");
				System.out.print("File name: ");
				String newFile = scan.next();
				System.out.println("\nalgorithm\tsize\ttime (ns)");
				
				algorithms[0] = new GrahamScan(newFile);
				algorithms[1] = new JarvisMarch(newFile);
				i = 0;
				do {
					algorithms[i].constructHull();
					algorithms[i].draw();
					algorithms[i].writeHullToFile();
					System.out.println(algorithms[i].stats());
					i++;
				}while(i < algorithms.length);
				
			}
			if(userChoice !=1 && userChoice !=2) {
			System.out.println("\n");
			trials++;
			break;
			
		}
		
			
		}
	
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50] × [-50,50]. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if(numPts < 1) {
			throw new IllegalArgumentException();
		}
		Point[] randompoints = new Point[numPts];
		
		for(int i = 0; i < numPts; i++) {
			Point p = new Point(rand.nextInt(101)-50, rand.nextInt(101)-50);
			//this will generate pseudo-random number between -50 and 50 
			//every time it is executed.
			randompoints[i] = p;
		}
		
		return randompoints; 
		// TODO 
	}
}
