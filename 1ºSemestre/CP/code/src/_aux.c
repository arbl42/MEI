#include "../include/aux.h"

// function to swap elements
void swap(int *a, int *b) {
  int t = *a;
  *a = *b;
  *b = t;
}

// function to find the partition position
int partition(int array[], int low, int high) {
  
  // select the rightmost element as pivot
  int pivot = array[high];
  
  // pointer for greater element
  int i = (low - 1);

  // traverse each element of the array
  // compare them with the pivot
  int j;
  for (j = low; j < high; j++) {
    if (array[j] <= pivot) {
        
      // if element smaller than pivot is found
      // swap it with the greater element pointed by i
      i++;
      
      // swap element at i with element at j
      swap(&array[i], &array[j]);
    }
  }

  // swap the pivot element with the greater element at i
  swap(&array[i + 1], &array[high]);
  
  // return the partition point
  return (i + 1);
}

void quick_sort(int array[], int low, int high) {
  if (low < high) {
    
    // find the pivot element such that
    // elements smaller than pivot are on left of pivot
    // elements greater than pivot are on right of pivot
    int pi = partition(array, low, high);
    
    // recursive call on the left of pivot
    quick_sort(array, low, pi - 1);
    
    // recursive call on the right of pivot
    quick_sort(array, pi + 1, high);
  }
}

// Merges two subarrays of arr[].
// First subarray is arr[l..m]
// Second subarray is arr[m+1..r]
void merge(int arr[], int l, int m, int r)
{
    int i, j, k;
    int n1 = m - l + 1;
    int n2 = r - m;
  
    /* create temp arrays */
    int L[n1], R[n2];
  
    /* Copy data to temp arrays L[] and R[] */
    for (i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (j = 0; j < n2; j++)
        R[j] = arr[m + 1 + j];
  
    /* Merge the temp arrays back into arr[l..r]*/
    i = 0; // Initial index of first subarray
    j = 0; // Initial index of second subarray
    k = l; // Initial index of merged subarray
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        }
        else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }
  
    /* Copy the remaining elements of L[], if there
    are any */
    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }
  
    /* Copy the remaining elements of R[], if there
    are any */
    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}
  
/* l is for left index and r is right index of the
sub-array of arr to be sorted */
void merge_sort(int arr[], int l, int r)
{
    if (l < r) {
        // Same as (l+r)/2, but avoids overflow for
        // large l and h
        int m = l + (r - l) / 2;
  
        // Sort first and second halves
        merge_sort(arr, l, m);
        merge_sort(arr, m + 1, r);
  
        merge(arr, l, m, r);
    }
}

int getMax(int a[], int n) {  
   int max = a[0];
   int i;  
   for(i = 1; i<n; i++) {  
      if(a[i] > max)  
         max = a[i];  
   }  
   return max; //maximum element from the array  
}  
  
void countingSort(int a[], int n, int place) // function to implement counting sort  
{  
  int output[n + 1];  
  int count[10] = {0};
  int i;    
  
  // Calculate count of elements  
  for (i = 0; i < n; i++)  
    count[(a[i] / place) % 10]++;  
      
  // Calculate cumulative frequency  
  for (i = 1; i < 10; i++)  
    count[i] += count[i - 1];  
  
  // Place the elements in sorted order  
  for (i = n - 1; i >= 0; i--) {  
    output[count[(a[i] / place) % 10] - 1] = a[i];  
    count[(a[i] / place) % 10]--;  
  }  
  
  for (i = 0; i < n; i++)  
    a[i] = output[i];  
}  
  
// function to implement radix sort  
void radix_sort(int a[], int start, int n) {  
   
  // get maximum element from array  
  int max = getMax(a, n),place;  
  
  // Apply counting sort to sort elements based on place value  
  for (place = 1; max / place > 0; place *= 10)  
    countingSort(a, n, place);  
}

void initialize_buckets(int * input_array, int size, int *number_buckets, int *** buckets, int *min, int *max) {
    *max=input_array[0], *min=input_array[0];
    int i;
    for (i = 1; i < size; i++) {
        if (input_array[i] > *max) {
            *max = input_array[i];
        }
        else if (input_array[i] < *min) {
            *min = input_array[i];
        }
    }
    //Creating buckets
    *number_buckets = 40;
    *buckets = malloc(*number_buckets*sizeof(int*));

    for (i =0 ;i <*number_buckets; i++) {
        (*buckets)[i]=malloc(0.5*size*sizeof(int));
    }
    //printf("Buckets created\n");
}