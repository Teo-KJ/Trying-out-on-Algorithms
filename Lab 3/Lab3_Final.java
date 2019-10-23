import java.util.Random;
import java.util.Scanner;

public class Lab3_Final {
	public static void main (String [] args) {
		int i, IntSize = 0, randomNumber;
		long numOfComparisons;
		String type = "";
		Scanner sc = new Scanner (System.in);
		Random ram = new Random();
		
		System.out.println("Welcome to sort! Please enter the number of integers you want to sort.");
		System.out.println("1. 100 \n2. 1000 \n3. 10,000 \n4. 100,000 \n5. 1,000,000");
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
		default:
			IntSize = 1000;
		}

		System.out.println("Please indicate type of input array");
		System.out.println("1. Random\n2. Ascending\n3. Descending");
		int option2 = sc.nextInt();

		int [] intData = new int[IntSize];
		int [] intData2 = new int[IntSize];
		
		switch (option2) {
		case (1): 
			for (i=1; i<=IntSize; i++) {
				randomNumber = ram.nextInt(IntSize);
				intData[i-1] = randomNumber;
			}
			type = "random";
			break;
		case (2):
			for (i=1; i<=IntSize; i++) {
				intData[i-1] = i;
			}
			type = "ascending";
			break;
		case (3):
			int j = 0;
			for (i=IntSize; i>0; i--) {
				intData[j] = i;
				j++;
			}
			type = "descending";
			break;
		default:
			for (i=1; i<=IntSize; i++) {
				randomNumber = ram.nextInt(IntSize);
				intData[i-1] = randomNumber;
			}
		}
		
		for(i=0; i<IntSize; i++)
		{
			intData2[i] = intData[i];
		}
	
		
		System.out.println("\n\nFor an array size of " + IntSize + ", the sorting algorithms spent the following time:");

		for(i=0; i<IntSize; i++)
			intData2[i] = intData[i];

		if (IntSize<10000){
			System.out.println("\nThe " + type + " order input is: ");
			for (i=0; i<IntSize; i++) {
				System.out.print(intData[i] + " ");
			}
			System.out.println("\n");
			for (i=0; i<IntSize; i++) {
				System.out.print(intData2[i] + " ");
			}
		}

		long InsertionSort_Start = System.nanoTime();
		insertionSort(intData, IntSize);
		long InsertionSort_End = System.nanoTime();
		
		System.out.println("\n\nThe sorted list is: ");
		for (i=0; i<IntSize; i++) {
			System.out.print(intData[i] + " ");
		}
		
		long InsertionSort_Total = InsertionSort_End - InsertionSort_Start;
		System.out.println("\nTotal time spent at Insertion Sort is " + InsertionSort_Total + " ns");
		
		long MergeSort_Start = System.nanoTime();
		System.out.println("\nThe number of comparisons in merge is " + mergeSort(intData2, 0, IntSize-1) + ".");
		long MergeSort_End = System.nanoTime();
		
		System.out.println("\nThe sorted list is: ");
		for (i=0; i<IntSize; i++) {
			System.out.print(intData2[i] + " ");
		}
		
		long MergeSort_Total = MergeSort_End - MergeSort_Start;
		System.out.println("\nTotal time spent at Merge Sort is " + MergeSort_Total + " ns");
		
		sc.close();
	}
	public static void insertionSort(int [] array, int size) {
		int i, j, temp, numOfComparisons = 0;
		
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
		System.out.println("\nThe number of comparisons in insertion is " + numOfComparisons + ".");
	}

	public static int mergeSort(int[] arr, int start, int end) {
		int comparisons = 0;
	    int mid = (start + end) / 2;

	    if (end - start <= 0) return 0;
	    else if ((end - start) > 0) {
	    	mergeSort(arr, start, mid);
	        mergeSort(arr, (mid + 1), end);
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