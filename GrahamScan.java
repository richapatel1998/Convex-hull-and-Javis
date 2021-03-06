package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.ArrayList; 

/**
 *  
 * @author Richa Patel
 *
 */
public class GrahamScan extends ConvexHull
{
	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the points 
	 * scanned so far.  At the end of the scan, it stores the hull vertices in the 
	 * counterclockwise order. 
	 */
	private PureStack<Point> vertexStack;  


	/**
	 * Call corresponding constructor of the super class.  Initialize two variables: algorithm 
	 * (from the class ConvexHull) and vertexStack. 
	 * 
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public GrahamScan(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		algorithm = "Graham's Scan";
		vertexStack = new ArrayBasedStack<Point>();

			
			
		//}
		// TODO 
	}
	

	/**
	 * Call corresponding constructor of the super class.  Initialize algorithm and vertexStack.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public GrahamScan(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		vertexStack = new ArrayBasedStack<Point>();
		algorithm = "Graham's Scan";
		
		// TODO 
	}

	
	// -------------
	// Graham's scan
	// -------------
	
	/**
	 * This method carries out Graham's scan in several steps below: 
	 * 
	 *     1) Call the private method setUpScan() to sort all the points in the array 
	 *        pointsNoDuplicate[] by polar angle with respect to lowestPoint.    
	 *        
	 *     2) Perform Graham's scan. To initialize the scan, push pointsNoDuplicate[0] and 
	 *        pointsNoDuplicate[1] onto vertexStack.  
	 * 
     *     3) As the scan terminates, vertexStack holds the vertices of the convex hull.  Pop the 
     *        vertices out of the stack and add them to the array hullVertices[], starting at index
     *        vertexStack.size() - 1, and decreasing the index toward 0.    
     *        
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains only collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points.   
	 */
	public void constructHull()
	{
		time = System.nanoTime();
		setUpScan();
		while(pointsNoDuplicate.length == 1) {
			hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
			break;
		}  if(pointsNoDuplicate.length == 2) {
			hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
			hullVertices[1] = pointsNoDuplicate[1];
		} else {
			
			
			Point[] newpoints = new Point[pointsNoDuplicate.length];
			quicksorter.getSortedPoints(newpoints);
			
			vertexStack.push(newpoints[0]);
			vertexStack.push(newpoints[1]);
			vertexStack.push(newpoints[2]);

			//scan
			Point temporary = new Point();
			int i=3;
			
			do {
				temporary = newpoints[i];
				Point topValue = vertexStack.pop();
				Point nextTopValue = vertexStack.pop();
				PolarAngleComparator comparing = new PolarAngleComparator(nextTopValue, true);
				
					while(0 > comparing.compare(temporary, topValue)) {
					topValue = nextTopValue;
					nextTopValue = vertexStack.pop();
					comparing = new PolarAngleComparator(nextTopValue, true);
					
				}
				vertexStack.push(nextTopValue);
				vertexStack.push(topValue);
				vertexStack.push(temporary); 
			//}
				i++;
			}while(i< newpoints.length);
			hullVertices = new Point[vertexStack.size()];
			int verStackSize = vertexStack.size();
			int j = verStackSize - 1;
			do {
				hullVertices[j] = vertexStack.pop();
				j--;
			}while(0 <= j); 
			time = System.nanoTime() - time;}
			
	}
		
		
	
	
	
	
	
	
	
	/**
	 * Set the variable quicksorter from the class ConvexHull to sort by polar angle with respect 
	 * to lowestPoint, and call quickSort() from the QuickSortPoints class on pointsNoDupliate[]. 
	 * The argument supplied to quickSort() is an object created by the constructor call 
	 * PolarAngleComparator(lowestPoint, true).       
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 *
	 */
	public void setUpScan()
	{
		quicksorter = new QuickSortPoints(pointsNoDuplicate);
		PolarAngleComparator comparing = new PolarAngleComparator(lowestPoint,true);
		quicksorter.quickSort(comparing);
		// TODO 
		
	}	
}