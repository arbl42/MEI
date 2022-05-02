#include <sys/time.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>

long int get_current_time();
void initialize_buckets(int * input_array, int size, int *number_buckets, int *** buckets, int *min, int *max);
void quick_sort(int *bucket, int start, int bucket_size);
void merge_sort(int *bucket, int start, int bucket_size);
void radix_sort(int *bucket, int start, int bucket_size);