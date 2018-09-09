package edu.iastate.cs228.hw4;

/**
 *  
 * @author Richa Patel
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException; 
import java.io.PrintWriter;
import java.util.Random; 
import java.util.Scanner;



/**
 * 
 * This class implements construction of the convex hull of a finite number of points. 
 *
 */

public abstract class ConvexHull 
{
	// ---------------
	// Data Structures 
	// ---------------
	protected String algorithm;  // Its value is either "Graham's scan" or "Jarvis' march". 
	                             // Initialized by a subclass.
	
	protected long time;         // execution time in nanoseconds
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates are possible. 
	 */
	private Point[] points;    
	

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by a constructor. 
	 */
	protected Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 * These are the points on which Graham's scan and Jarvis' march will be performed. 
	 */
	protected Point[] pointsNoDuplicate; 
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	protected Point[] hullVertices;
	
	
	protected QuickSortPoints quicksorter;  // used (and reset) by this class and its subclass GrahamScan

	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Constructor over an array of points.  
	 * 
	 *    1) Store the points in the private array points[].
	 *    
	 *    2) Initialize quicksorter. 
	 *    
	 *    3) Call removeDuplicates() to store distinct points from the input in pointsNoDuplicate[].
	 *    
	 *    4) Set lowestPoint to pointsNoDuplicate[0]. 
	 * 
	 * @param pts
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public ConvexHull(Point[] pts) throws IllegalArgumentException 
	{
		
		// TODO 
		if(pts.length == 0) { 
			throw new IllegalArgumentException();
			
		} points = new Point[pts.length];
		  for(int i = 0; i < pts.length; i++) {
			  points[i] = pts[i];
		  }
		  quicksorter = new QuickSortPoints(points);
		  removeDuplicates();
		  lowestPoint = pointsNoDuplicate[0];
	}
	
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * Perform the operations 1)-4) described for the previous constructor. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		
		// TODO 
		
		File readingFile = new File(inputFileName);
		int count  = 0;
		int index = 0;
		try {
			Scanner scan = new Scanner(readingFile);
			
			while(scan.hasNextInt()) {
				scan.nextInt();
				count++;
				
			}
			scan.close(); //close
			if(count %2 != 0) {
				throw new InputMismatchException();
			}
			int pointSize = (count/2); // we do count divided by 2 to get the point size
			points = new Point[pointSize];
			
			scan = new Scanner(readingFile);
			while(index<pointSize) {
				points[index] = new Point(scan.nextInt(), scan.nextInt());
				index++;
			}
//			do {
//				points[index] = new Point(scan.nextInt(), scan.nextInt());
//				index++;
//			}while(index < pointSize);
		scan.close();
		
		quicksorter = new QuickSortPoints(points);
		removeDuplicates();
		//System.out.println("No dupes: " + Arrays.toString(pointsNoDuplicate));
		lowestPoint = pointsNoDuplicate[0];
		}
		catch(IOException e) {
			throw new FileNotFoundException();
		}
		
			
		
		
	}

	
	/**
	 * Construct the convex hull of the points in the array pointsNoDuplicate[]. 
	 */
	public abstract void constructHull(); 

	
		
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <convex hull algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * Graham's scan   1000	  9200867
	 *  
	 * Use the spacing in the sample run in Section 5 of the project description. 
	 */
	public String stats()
	{
		//creating a row for each sorting algorithm in the table
		
		String string = algorithm+"\t"+points.length+"\t"+time;
		
		return string;
		
		
		
		// TODO 
	}
	
	
	/**
	 * The string displays the convex hull with vertices in counterclockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE. 
	 *  
	 * Called only after constructHull().  
	 */
	public String toString()
	{
		String solution = "";
		int counter = 0;
		for (int i = 0; i < points.length; i++) {
			
			if(counter < 6) { //as long as it is less than 6 then it should work
				counter++;
				solution = solution + points[i].toString()+" ";
			}
			else {
				solution = solution + "\n";
				counter = 0;
			}
			
		}
		// TODO 
		return solution; 
	}
	
	
	/** 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order.  These vertices are in the array hullVertices[], starting with lowestPoint.  Every line
	 * in the file displays the x and y coordinates of only one point.  
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
	 * 
	 * The generated file is useful for debugging as well as grading. 
	 * 
	 * Called only after constructHull().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 * @throws FileNotFoundException 
	 */
	public void writeHullToFile() throws IllegalStateException, FileNotFoundException 
	{
		// TODO 
		if(hullVertices == null) {
			throw new IllegalStateException();
		}
		
		File file = new File("hull.txt"); 
		PrintStream printwriter = new PrintStream(file);
		int i = 0;
		do {
			printwriter.println(hullVertices[i].toString()); //stuck here
			i++;
		}while(i < hullVertices.length);
		printwriter.close();
	}
	

	/**
	 * Draw the points and their convex hull.  This method is called after construction of the 
	 * convex hull.  You just need to make use of hullVertices[] to generate a list of segments 
	 * as the edges. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw 

		// Based on Section 4, generate the line segments to draw for display of the convex hull.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs]; 
		
		// TODO 
		numSegs = hullVertices.length;
		segments = new Segment[numSegs];
		int i = 0;
		do {
			Segment segs = new Segment(hullVertices[i], hullVertices[i+1]);
			segments[i] = segs;
			i++;
		}while(i < numSegs -1);

		segments[numSegs -1] = new Segment(hullVertices[numSegs - 1], lowestPoint);
		// The following statement creates a window to display the convex hull.
		Plot.myFrame(pointsNoDuplicate, segments, getClass().getName());
		
	}

		
	/**
	 * Sort the array points[] by y-coordinate in the non-decreasing order.  Have quicksorter 
	 * invoke quicksort() with a comparator object which uses the compareTo() method of the Point 
	 * class. Copy the sorted sequence onto the array pointsNoDuplicate[] with duplicates removed.
	 *     
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void removeDuplicates() //this looks gucci
	{ //create points no duplicate with correct length
		//create a comparator that sorts by the public int compareTo(Point q) method 
		//in point.java
		//give that comparator to quick sort
		ArrayList<Point> ret=new ArrayList<Point>();
	
		//Create comparator CREATE COMPARATOR IS THE LAST ONE (quicksorter
		//quicksorter.quickSort(comparing);
		Comparator<Point> comparing=(Point p1, Point p2)->p1.compareTo(p2);
		quicksorter.quickSort(comparing);
		quicksorter.getSortedPoints(points);
		//any duplicates will be right next to each other because they are sorted
		//2 duplicates will never be in the opposite side of array
		int i = 0; int j = 0; //j is the length of the new array
		for(i = 0; i <points.length; i++) { 
			if(ret.contains(points[i])==false) {
				ret.add(points[i]);
			}
		}
		        pointsNoDuplicate=ret.toArray(new Point[ret.size()]);
				
			//	System.out.println(Arrays.toString( pointsNoDuplicate));
				//copies over the elements in the point array which we give back in points no duplicate
			
		
			
		}
		
		// TODO 
	}
