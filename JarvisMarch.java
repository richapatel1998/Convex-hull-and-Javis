package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;

import java.util.InputMismatchException;
import java.util.ArrayList; 

/**
 * 
 * @author Richa Patel
 *
 */

public class JarvisMarch extends ConvexHull
{
	// last element in pointsNoDuplicate(), i.e., highest of all points (and the rightmost one in case of a tie)
	private Point highestPoint; 
	
	// left chain of the convex hull counterclockwise from lowestPoint to highestPoint
	private PureStack<Point> leftChain; 
	
	// right chain of the convex hull counterclockwise from highestPoint to lowestPoint
	private PureStack<Point> rightChain; 
		

	/**
	 * Call corresponding constructor of the super class.  Initialize the variable algorithm 
	 * (from the class ConvexHull). Set highestPoint. Initialize the two stacks leftChain 
	 * and rightChain. 
	 * 
	 * @throws IllegalArgumentException  when pts.length == 0
	 */
	public JarvisMarch(Point[] pts) throws IllegalArgumentException 
	{
		
		super(pts); 
		if(pts.length == 0) {
			throw new IllegalArgumentException();
		}
		algorithm = "Jarvis' March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length -1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
		// TODO 
	}
//the algorithm builds the right chain from the lowest point to the highest point
	//and then the left chain from the highest Point downward to lowest point
	
	/**
	 * Call corresponding constructor of the superclass.  Initialize the variable algorithm.
	 * Set highestPoint.  Initialize leftChain and rightChain.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public JarvisMarch(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		// TODO 
		algorithm = "Jarvis' March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length-1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
		
			
		
	}


	// ------------
	// Javis' march
	// ------------

	/**
	 * Calls createRightChain() and createLeftChain().  Merge the two chains stored on the stacks  
	 * rightChain and leftChain into the array hullVertices[].
	 * 
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points from them.   
	 */
	public void constructHull()
	{
		// TODO
		long starting = System.nanoTime(); 
		if(pointsNoDuplicate.length == 1) {
			rightChain.push(pointsNoDuplicate[0]);
			
		}
		if(pointsNoDuplicate.length == 2) {
			rightChain.push(pointsNoDuplicate[0]);
			leftChain.push(pointsNoDuplicate[1]);
			
		}else {
			createRightChain();
			createLeftChain();
		}
		int left = leftChain.size(); int right = rightChain.size();
		hullVertices = new Point[right + left];
		int total = right + left;
		int i = total -1;
		int j = (total-1)-(left);
		do {
			hullVertices[i] = leftChain.pop();
			i--;
			
		}while(i > ((total-1)- (left)));
		do {
			hullVertices[j] = rightChain.pop();
			j--;
			
		}while(j>=0);
		time = System.nanoTime() - starting;
		

	}
	
	
	/**
	 * Construct the right chain of the convex hull.  Starts at lowestPoint and wrap around the 
	 * points counterclockwise.  For every new vertex v of the convex hull, call nextVertex()
	 * to determine the next vertex, which has the smallest polar angle with respect to v.  Stop 
	 * when the highest point is reached.  
	 * 
	 * Use the stack rightChain to carry out the operation.  
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createRightChain()
	{//return pointer to points noduplicate
		Point point = lowestPoint;
		do {
			rightChain.push(point);
			point = nextVertex(point);
			
		}while(!(point.equals(highestPoint)));
		
		// TODO 
		
	}
	
	
	/**
	 * Construct the left chain of the convex hull.  Starts at highestPoint and continues the 
	 * counterclockwise wrapping.  Stop when lowestPoint is reached.  
	 * 
	 * Use the stack leftChain to carry out the operation. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createLeftChain()
	{
		Point point = highestPoint;
		do {
			leftChain.push(point);
			point = nextVertex(point);
		}while(!point.equals(lowestPoint));
		// TODO 
	}
	
	
	/**
	 * Return the next vertex, which is less than all other points by polar angle with respect
	 * to the current vertex v. When there is a tie, pick the point furthest from v. Comparison 
	 * is done using a PolarAngleComparator object created by the constructor call 
	 * PolarAngleCompartor(v, false).
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param v  current vertex 
	 * @return
	 */
	public Point nextVertex(Point v)
	{
		//vertex has the smallest distance possible
		//never want to compare the minimum value to vertex(v)
		PolarAngleComparator comparing = new PolarAngleComparator(v, false);
		Point nextVert  = pointsNoDuplicate[0];
		int i = 0;
		do {
			if(comparing.compare(nextVert, pointsNoDuplicate[i]) > 0 && !pointsNoDuplicate[i].equals(v)) {
				nextVert = pointsNoDuplicate[i];
			}
			i++;
		}while(i < pointsNoDuplicate.length);
		
		
		return nextVert; 
	}
}