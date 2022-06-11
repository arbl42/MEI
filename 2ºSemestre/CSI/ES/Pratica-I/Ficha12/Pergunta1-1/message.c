#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h> // inclui bool como return
#include <string.h>
#include <limits.h> // limites do int para conversao de string para int
#include <time.h> // para buscar ano atual
#include <ctype.h> // para funcao isdigit()

#define BUF_LEN 50
#define NIF_LEN 9

// retirado de https://www.programiz.com/c-programming/examples/leap-year
bool is_leap_year(int year){
    // leap year if perfectly divisible by 400
    if (year % 400 == 0) return true;
    if (year % 100 == 0) return false;
    if (year % 4 == 0) return true;
    return false;
}

/* coloca o resultado (se tiver sucesso) no parâmetro out
(visto que long long nunca é mais pequeno que long,
que nunca é mais pequeno que int, e teremos de converter para int
o if pode ser abreviado)*/
bool string_to_int(char str[], int* out){
    char *end;
    const long i = strtol(str, &end, 10);
    if (*end != '\0' || i>=INT_MAX || i<=INT_MIN || i==0 ){
        //printf("Error converting %s to int\n", str);
        return false;
    }
    *out = i; // conversão segura devido ao if anterior
    return true;
}

// DD/MM/AAAA
bool validate_date_year(int data[]){
    // buscar o ano atual para perceber
    // se o utilizador não tem mais de
    // 100 anos
    time_t t = time(NULL);
    struct tm tm = *localtime(&t);
    int current_year = tm.tm_year + 1900;

    if((current_year - data[2])<100)
        return true;
    return false;
}

// DD/MM/AAAA
bool validate_date_month(int data[]){
    if(data[1]>=1 && data[1]<=12)
        return true;
    return false;
}

// DD/MM/AAAA
bool validate_date_day(int data[]){
    int day=data[0];
    int month=data[1];
    int year=data[2];

    // válido para todos os meses
    if(day<1 || day>31)
        return false;

    int max_day;
    if(month==2) // fevereiro
        if(is_leap_year(year)) max_day=29;
        else max_day=28;
    else // meses com 31 dias 
        if(month==1 || month==3 || month==5 || month==7 || 
        month==8 || month==10 || month==12)
            max_day=31;
        else // meses com 30 dias
            max_day=30;
    
    if(day>max_day) return false;
    return true;
}

// DD/MM/AAAA
bool validate_birth_date(char* buf){
    char* str;
    strcpy(str, buf);

    // split em /
    char *delim = "/";

    int n=0; // guarda o número atual (dia/mês/ano)
    int data[2]; // guarda os numeros da data para facilitar a sua validacao
    int i=0; // i vai guardar o número de números divididos por /
    bool error=false;

    char *ptr = strtok(str, delim); // DIA
    while(ptr != NULL)
	{
		//printf("%s\n", ptr);
        if(string_to_int(ptr, &n))
            data[i]=n;
        else {
            printf("Erro a converter a string %s!\n", ptr);
            return false;
        }

		ptr = strtok(NULL, delim);
        
        i+=1;
        if(i>=3) // se contiver muitos números
            break;
	}

    // valida o número de números dentro da data
    if(i!=3) {
        printf("A data apenas pode conter 3 números\n");
        return false;
    }

    // validar a data em si
    // por exemplo se a data errada 30/02/.. foi introduzida
    if(!validate_date_year(data)){
        printf("Ano incorreto\n");
        return false;
    } else if(!validate_date_month(data)){
        printf("Mes incorreto\n");
        return false;
    } else if(!validate_date_day(data)){
        printf("Dia incorreto\n");
        return false;
    }

    return true;
}

// adaptado de https://pt.wikipedia.org/wiki/N%C3%BAmero_de_identifica%C3%A7%C3%A3o_fiscal#Obter_d%C3%ADgito_de_controlo
int check_nif_digit(char nif[]){
    //soma = sum([int(digito) * (9 - pos) for pos, digito in enumerate(string_num)])
    int soma=0;
    for(int i=0; i<NIF_LEN-1; i++){
        //printf("%d * %d\n", nif[i] - '0',NIF_LEN - i);
        soma += (nif[i] - '0') * (NIF_LEN - i);
    }
    int resto = soma % 11;
    if(resto==0 || resto==1)
        return 0;
    return 11-resto;
}

bool validate_nif(char buf[], int *nif){
    if(strlen(buf) != NIF_LEN) return false; // ver se tem tamanho 9
    if(!string_to_int(buf, nif)) return false; // ver se apenas tem numeros
    
    //printf("NIF is %d\n", nif);
    int check_d = check_nif_digit(buf);
    printf("Check digit e %d.\n", check_d);
    
    //compare checkDigit with the last number of NIF
    if(check_d == (buf[NIF_LEN-1]-'0'))
        return true;
    return false;
}

int main(int argc, char **argv) {
    char buf[BUF_LEN];
    float f;

    // valor a pagar
    // documentação de strtof em 
    // https://www.ibm.com/docs/en/zos/2.4.0?topic=functions-strtof-convert-character-string-float
    printf("Valor a pagar: ");
    //fgets(buf, BUF_LEN, stdin);
    strcpy(buf, "22.23");
    f = strtof(buf, NULL);
    if(f == -HUGE_VALF || f == HUGE_VALF || f == 0){
        printf("Erro a inserir o valor a pagar\n");
    } else {
        printf("%.2f\n", f);
    }

    // data nascimento
    printf("Data de nascimento (DD/MM/AAAA): ");
    //fgets(buf, BUF_LEN, stdin);
    strcpy(buf, "22/3/1990");
    if(!validate_birth_date(buf))
        printf("Data de nascimento errada!\n");
    else printf("%s\n", buf);

    // NIF
    int n=0; // para guardar o nif depois de estar convertido
    printf("NIF: ");
    //fgets(buf, BUF_LEN, stdin);
    strcpy(buf, "135785413");
    if(!validate_nif(buf, &n))
        printf("NIF invalido!\n");
    else 
        printf("%d\n", n);

    // número de identificação de cidadão (NIC)
    // ...

    // numero de cartão de crédito, validade e CVC/CVV
    // ...

    return 0;
}
