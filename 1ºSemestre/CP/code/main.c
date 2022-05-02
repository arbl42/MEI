#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include "include/sequential.h"
#include "include/parallel.h"
#include "papi.h"

void print_array(int *array, int size) {
    int i;
    for (i=0; i < size; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
}

//Chamada recebe tamanho do array a ordenar e valor mais alto do array
int sort(int argc, char *argv[]) {
    int *array;
    int size = atoi(argv[1]);
    int max_number = atoi(argv[2]);
    int running_type = atoi(argv[3]); //tipo escolhido (sequencial(1) ou paralelo(0))
    
    //Alocação de espaço para array de input e output
    array = malloc(size*sizeof(int));

    int i;
    for (i=0; i<size; i++) {
        array[i]=rand()%max_number;//randomização de um valor aleatório entre 0 e max_number
    }
    //print_array(input_array,size);
    if (running_type==1) {
        sequential(array,size);
    }
    else if (running_type==0) {
        parallel(array,size);
    }
    //print_array(output_array,size);
    free(array);

    return 0;
}

#define NUM_EVENTS 4
int Events[NUM_EVENTS] = { PAPI_TOT_CYC, PAPI_TOT_INS, PAPI_L1_DCM, PAPI_L2_DCM};

// PAPI counters' values
long long values[NUM_EVENTS], min_values[NUM_EVENTS];

// number of times the function is executed and measured
#define NUM_RUNS 10

int main (int argc, char *argv[]) {
    long long start_usec, end_usec, elapsed_usec, min_usec=0L;
    int i, run;
    int num_hwcntrs = 0;

    fprintf (stdout, "\nSetting up PAPI...");
    // Initialize PAPI 
    PAPI_library_init (PAPI_VER_CURRENT);

    /* Get the number of hardware counters available */
    if ((num_hwcntrs = PAPI_num_counters()) <= PAPI_OK)  {
        fprintf (stderr, "PAPI error getting number of available hardware counters!\n");
        return 0;
    }
    fprintf(stdout, "done!\nThis system has %d available counters.\n\n", num_hwcntrs);

    // We will be using at most NUM_EVENTS counters
    if (num_hwcntrs >= NUM_EVENTS) {
        num_hwcntrs = NUM_EVENTS;
    } else {
        fprintf (stderr, "Error: there aren't enough counters to monitor %d events!\n", NUM_EVENTS);
        return 0;
    }

    for (run=0 ; run < NUM_RUNS ; run++) { 
        srand(rand()); //Seeding random to get different arrays everytime
        fprintf (stderr, "\nrun=%d - Sorting\n", run);

        // use PAPI timer (usecs) - note that this is wall clock time
        // for process time running in user mode -> PAPI_get_virt_usec()
        // real and virtual clock cycles can also be read using the equivalent
        // PAPI_get[real|virt]_cyc()
        start_usec = PAPI_get_real_usec();

        /* Start counting events */
        if (PAPI_start_counters(Events, num_hwcntrs) != PAPI_OK) {
          fprintf (stderr, "PAPI error starting counters!\n");
          return 0;
        }

        sort(argc, argv);

        /* Stop counting events */
        if (PAPI_stop_counters(values, NUM_EVENTS) != PAPI_OK) {
          fprintf (stderr, "PAPI error stoping counters!\n");
          return 0;
        }

        end_usec = PAPI_get_real_usec();
        fprintf (stderr, "done!\n");

        elapsed_usec = end_usec - start_usec;

        if ((run==0) || (elapsed_usec < min_usec)) {
           min_usec = elapsed_usec;
           for (i=0 ; i< NUM_EVENTS ; i++) min_values[i] = values [i];
        }

    } // end runs
    printf ("\nWall clock time: %lld usecs\n", min_usec);
        
    // output PAPI counters' values
    for (i=0 ; i< NUM_EVENTS ; i++) {
    	  char EventCodeStr[PAPI_MAX_STR_LEN];
    	  if (PAPI_event_code_to_name(Events[i], EventCodeStr) == PAPI_OK) {
    		fprintf (stdout, "%s = %lld\n", EventCodeStr, min_values[i]);
    	  } else {
    		fprintf (stdout, "PAPI UNKNOWN EVENT = %lld\n", min_values[i]);
    	  }
    }

    #if NUM_EVENTS >1
      // evaluate CPI and Texec here
      if ((Events[0] == PAPI_TOT_CYC) && (Events[1] == PAPI_TOT_INS)) {
        float CPI = ((float) min_values[0]) / ((float) min_values[1]);
        fprintf (stdout, "CPI = %.2f\n", CPI);
      }
    #endif
    
    printf ("\nThat's all, folks\n");
    return 0;
}