package CZ2001;

import java.util.Random;
import java.util.Scanner;

public class Lab3_Sort_Descending {
	public static void main (String [] args) {
		int i, IntSize = 0, randomNumber;
		Scanner sc = new Scanner (System.in);
		Random ram = new Random();
		
		System.out.println("Welcome to merge sort! Please enter the number of integrs you want to sort.");
		System.out.println("1. 1000 \n2. 10,000 \n3. 100,000 \n4. 10,000,000");
		int option = sc.nextInt();
		
		switch (option) {
		case (1):
			IntSize = 1000;
			break;
		case (2):
			IntSize = 10000;
			break;
		case (3):
			IntSize = 100000;
			break;
		case (4): default:
			IntSize = 10000000;
		}
		
		int [] intData = new int[IntSize];
		int [] intData2 = new int[IntSize];
		int [] num_list = new int[IntSize];
		
		for (i=1; i<=IntSize; i++) {
			num_list[i-1] = i;
		}
		for (i=1; i<=IntSize; i++) {
			randomNumber = ram.nextInt(num_list.length);
			intData[i-1] = randomNumber;
			intData2[i-1] = randomNumber;
		}
		
		if (IntSize>10000){
			System.out.println("\nYou have entered");
			for (i=0; i<IntSize; i++)
				System.out.print(intData[i] + " ");
			System.out.println("");
			for (i=0; i<IntSize; i++)
				System.out.print(intData2[i] + " ");
		}
		
		long InsertionSort_Start = System.nanoTime();
		insertionSort_Descending(intData, IntSize);
		long InsertionSort_End = System.nanoTime();
		
		if (IntSize>10000){
			System.out.println("\n\nThe sorted list is: ");
			for (i=0; i<IntSize; i++) {
				System.out.print(intData[i] + " ");
			}
		}
		
		long InsertionSort_Total = InsertionSort_End - InsertionSort_Start;
		System.out.println("\nTotal time spent at Insertion Sort is " + InsertionSort_Total + " ns");
		
		long MergeSort_Start = System.nanoTime();
		mergeSort_Descending(intData2, 0, IntSize-1);
		long MergeSort_End = System.nanoTime();
		
		if (IntSize>10000) {
			System.out.println("\nThe sorted list is: ");
			for (i=0; i<IntSize; i++) {
				System.out.print(intData2[i] + " ");
			}
		}
		
		long MergeSort_Total = MergeSort_End - MergeSort_Start;
		System.out.println("\nTotal time spent at Merge Sort is " + MergeSort_Total + " ns");
		
		sc.close();
	}
	
	public static void insertionSort_Descending(int [] array, int size) {
		int i, j, temp, numOfComparisons = 0;
		
		for (i=1; i<size; i++) {
			for (j=i; j>0; j--) {
				temp = array[j];
				if (array[j] > array[j-1]) {
					numOfComparisons++;
					array[j] = array[j-1];
					array[j-1] = temp;
				}
				else break;
			}
		}
		System.out.println("\nThe number of comparisons in insertion is " + numOfComparisons);
	}

	public static int mergeSort_Descending(int[] arr, int start, int end) {
		int comparisons = 0;
	    int mid = (start + end) / 2;

	    if (end - start <= 0) return 0;
	    else if ((end - start) > 0) {
	    	mergeSort_Descending(arr, start, mid);
	    	mergeSort_Descending(arr, (mid + 1), end);
	        comparisons = comparisons + merge(arr, start, mid, end);
	    }
	     return comparisons;
	}

	public static int merge(int[] arr, int start, int mid, int end) {
		int a = start;
	    int b = mid + 1;
	    int i, tmp, comparisons = 0;

	    if (end - start <= 0) return 0;

	    while ((a <= mid) && (b <= end)) {
	    	if (arr[a] < arr[b]) {
	    		tmp = arr[b++];
	            for (i = ++mid; i > a; i--) arr[i] = arr[i - 1];
	            	arr[a++] = tmp;
	        }
	        else if (arr[a] > arr[b]) a++;
	        else {
	           if ((a == mid) && (b == end)) break;
	           tmp = arr[b++];
	           a++;
	           for (i = ++mid; i > a; i--) arr[i] = arr[i - 1];
	           arr[a++] = tmp;
	        }
	            comparisons++;
	    }
	    return comparisons;
	  }
}
