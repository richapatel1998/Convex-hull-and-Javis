package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Comparator;

/**
 *  
 * @author Richa Patel
 *
 */

/**
 * This class sorts an array of Point objects using a provided Comparator.  For the purpose
 * you may adapt your implementation of quicksort from Project 2.  
 */

public class QuickSortPoints
{
	private Point[] points;  	// Array of points to be sorted.
	

	/**
	 * Constructor takes an array of Point objects. 
	 * 
	 * @param pts
	 */
	QuickSortPoints(Point[] pts)
	{
		//most of this was used in from the homework 2
		this.points = new Point[pts.length];
		int i;
		for(i =0; i<pts.length; i++) {
			this.points[i] = pts[i];
		}
	}
	
	
	/**
	 * Copy the sorted array to pts[]. 
	 * 
	 * @param pts  array to copy onto
	 */
	void getSortedPoints(Point[] pts)
	{	
		int i;
		for( i =0; i<pts.length; i++) {
			pts[i] = points[i];
		}
	}

	
	/**
	 * Perform quicksort on the array points[] with a supplied comparator. 
	 * 
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		quickSortRec(0, points.length -1, comp);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		if (first >= last)
			return;
		int part = partition(first, last, comp);
		quickSortRec(first, part -1, comp);
		quickSortRec(part+1, last, comp);
	}
	

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{
		Point pivot = points[last];
		int i = first - 1;
		for (int j = first; j < last; j++) {
			if (comp.compare(points[j], pivot) <1) {
				i++;
				Point temporary = points[i];
				points[i] = points[j]; //swaps point i and j
				points[j] = temporary;
			}
		}
		Point temporary = points[i + 1];
		points[i + 1] = points[last]; 	// swap [i+1] and last one
		points[last] = temporary;
		return i + 1;
	
		
		
	}
}
