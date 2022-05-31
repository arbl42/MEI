#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char **argv) {
    char *dummy = (char *) malloc (sizeof(char) * 10);
    char *readonly = (char *) malloc (sizeof(char) * 10);
    
    strcpy(readonly, "laranjas");
    if(argc > 1){
        if(strlen(argv[1]) > 10){
            printf("Na versao antiga causaria overflow...\n");
        }
        strncpy(dummy, argv[1], 5); // só copia se argv[1] existir
    }
    
    printf("%s\n", readonly);
}