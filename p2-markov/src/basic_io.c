#include <stdio.h>
#include <stdlib.h>

int main() {



    char name[100];
    int height;
    int avgPoints;
    float ppi;

    printf("Enter player name: ");
    scanf("%s", name);
    printf("Enter player height in inches: ");
    scanf("%d", &height);
    printf("Enter player avg. pts. per game: ");
    scanf("%d", &avgPoints);

    ppi = (float) avgPoints / height;

    // printf()


}