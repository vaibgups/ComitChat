package com.example.comitchat.testjava;

public class OddEven {

    public static void main(String[] args) {
        /*for (int i = 1; i<=100; i++){
            if (i%2 == 0){
                System.out.println("Even Number "+ i);
            }else {
                System.out.println("Odd number "+i);
            }
        }*/

        int temp = 0;

        for (int i = 0; i<=100; i++){

            for (int j = 2; j<i ; j++){


                if (i%j==0){

                }else {
                    System.out.println(" Prime Number "+i);

                }

                break;
            }

        }

    }

}
