package CZ2001;

import java.util.Random;
import java.util.Scanner;

public class Lab3_Final {
	public static void main (String [] args) {
		int i, IntSize = 0, randomNumber;
		String type = "";
		Scanner sc = new Scanner (System.in);
		Random ram = new Random();
		
		System.out.println("Welcome to sort! Please enter the number of integers you want to sort.");
		System.out.println("1. 100 \n2. 1000 \n3. 10,000 \n4. 100,000 \n5. 1,000,000 \n6. Input a size more than 1");
		int option = sc.nextInt();
		
		switch (option) {
		case (1): 
			IntSize = 100;
			break;
		case (2):
			IntSize = 1000;
			break;
		case (3):
			IntSize = 10000;
			break;
		case (4): 
			IntSize = 100000;
			break;
		case (5):
			IntSize = 1000000;
			break;
		case 6:
			System.out.println("Please enter the size");
			IntSize = sc.nextInt();
			break;
		default:
			IntSize = 1000;
		}

		System.out.println("Please indicate type of input array");
		System.out.println("1. Random \n2. Ascending \n3. Descending");
		int input_type = sc.nextInt();

		int [] unsorted = new int[IntSize];
		int [] to_sort_insert = new int[IntSize];
		int [] to_sort_merge = new int[IntSize];
		
		switch (input_type) {
		case (1): // create an array of random numbers
			for (i=1; i<=IntSize; i++) {
				randomNumber = ram.nextInt(IntSize);
				unsorted[i-1] = randomNumber;
			}
			type = "random";
			break;
		case (2): // create an array of numbers from 1 to n
			for (i=1; i<=IntSize; i++) {
				unsorted[i-1] = i;
			}
			type = "ascending";
			break;
		case (3): // create an array of numbers from n to 1
			int j = 0;
			for (i=IntSize; i>0; i--) {
				unsorted[j] = i;
				j++;
			}
			type = "descending";
			break;
		default:
			for (i=1; i<=IntSize; i++) {
				randomNumber = ram.nextInt(IntSize);
				unsorted[i-1] = randomNumber;
			}
		}
		
		for (i=1; i<=IntSize; i++) { // copy the elements from the unsorted array to the arrays to be passed on to sorting functions
			to_sort_insert[i-1] = unsorted[i-1];
			to_sort_merge[i-1] = unsorted[i-1];
		}
		
		System.out.println("\n\nFor an array size of " + IntSize + " and the " + type + " input type, the sorting algorithms spent the "
				+ "following time:");
		
		// if the input size is large, do not print the elements in the array
		if (IntSize<10000){
			System.out.println("\nThe " + type + " order input is: ");
			for (i=0; i<IntSize; i++)
				System.out.print(to_sort_insert[i] + " ");
			System.out.println("");
			for (i=0; i<IntSize; i++)
				System.out.print(to_sort_merge[i] + " ");
		}

		//long InsertionSort_Start = System.nanoTime();
		long InsertionSort_Total = insertionSort(to_sort_insert, IntSize);
		//long InsertionSort_End = System.nanoTime();
		
		if (IntSize<10000) {
			System.out.println("\n\nThe sorted list is: ");
			for (i=0; i<IntSize; i++)
				System.out.print(to_sort_insert[i] + " ");
		}
		
		// calculate the runtime
		// long InsertionSort_Total = InsertionSort_End - InsertionSort_Start;
		System.out.println("\nTotal time spent at Insertion Sort is " + InsertionSort_Total/Math.pow(10, 6) + " ms");
		
		long MergeSort_Start = System.nanoTime();
		System.out.println("\nThe number of comparisons in merge is " + mergeSort(to_sort_merge, 0, IntSize-1) + ".");
		long MergeSort_End = System.nanoTime();
		
		if (IntSize<10000) {
			System.out.println("\nThe sorted list is: ");
			for (i=0; i<IntSize; i++)
				System.out.print(to_sort_merge[i] + " ");
		}
		
		long MergeSort_Total = MergeSort_End - MergeSort_Start;
		System.out.println("\nTotal time spent at Merge Sort is " + MergeSort_Total/Math.pow(10, 6) + " ms");
		
		sc.close();
	}
	
	public static long insertionSort(int [] array, int size) {
		long InsertionSort_Start = System.nanoTime();
		int i, j, temp;
		long numOfComparisons = 0;
		
		for (i=1; i<size; i++) {
			for (j=i; j>0; j--) {
				temp = array[j];
				numOfComparisons++;
				if (array[j] < array[j-1]) {
					array[j] = array[j-1];
					array[j-1] = temp;
				}
				else break;
			}
		}
		long InsertionSort_End = System.nanoTime();
		System.out.println("\nThe number of comparisons in insertion is " + numOfComparisons + ".");
		return (InsertionSort_End - InsertionSort_Start);
	}

	public static int mergeSort(int[] arr, int start, int end) {
		int comparisons = 0;
	    int mid = (start + end) / 2;

	    if (end - start <= 0) return 0;
	    else if ((end - start) > 0) {
//	    	comparisons++;
//	    	int a = mergeSort(arr, start, mid);
//	        int b = mergeSort(arr, (mid + 1), end);
	        comparisons = (1 + mergeSort(arr, start, mid)) + (1 + mergeSort(arr, (mid + 1), end))  + merge(arr, start, mid, end);
	    }
	     return comparisons;
	}

	public static int merge(int[] arr, int start, int mid, int end) {
		int a = start;
	    int b = mid + 1;
	    int i, tmp, comparisons = 0;

	    if (end - start <= 0) return 0;

	    while ((a <= mid) && (b <= end)) {
			comparisons++;
	    	if (arr[a] > arr[b]) {
	    		tmp = arr[b++];
	            for (i = ++mid; i > a; i--) arr[i] = arr[i - 1];
					arr[a++] = tmp;
	        }
	        else if (arr[a] < arr[b]) {
				a++;
			}
	        else {
	           	if ((a == mid) && (b == end)) 
					   break;
	           	tmp = arr[b++];
	           	a++;
	           	for (i = ++mid; i > a; i--) arr[i] = arr[i - 1];
			   	arr[a++] = tmp;
			   
	        }
	    }
	    return comparisons;
	  }
	
}