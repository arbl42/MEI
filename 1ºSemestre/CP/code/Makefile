COMPILER = gcc
FLAGS = -O2 -c -g -Wall -fno-omit-frame-pointer -I/share/apps/papi/5.4.1/include -ftree-vectorize -msse4
FLAGS_OPENMP = -fopenmp -lm -fno-omit-frame-pointer
FLAGS_LINK = -O2 -g -Wall -lm

LIBS = -L/share/apps/papi/5.4.1/lib -lm -lpapi

aux: 
	$(COMPILER) $(FLAGS) src/aux.c -o aux.o

parallel: 
	$(COMPILER) $(FLAGS) $(FLAGS_OPENMP) src/parallel.c -o parallel.o

sequential: 
	$(COMPILER) $(FLAGS) src/sequential.c -o sequential.o

sort:
	$(COMPILER) $(FLAGS) main.c -o sort.o

main: sequential sort parallel aux
	$(COMPILER) $(FLAGS_LINK) $(FLAGS_OPENMP) $(LIBS) sort.o sequential.o parallel.o aux.o -o sort

run: main
	./sort $(size) $(max_number) $(running_type)


clean: 
	rm -rf *.o sort