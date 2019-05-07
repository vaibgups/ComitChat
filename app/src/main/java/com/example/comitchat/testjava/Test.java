package com.example.comitchat.testjava;

public class Test {

    public static void main(String[] args) {



      /*  String name [] = {"Ram","Shyam","Test","Mohan"};

        for (String i : name){
            System.out.println(""+i);
        }
*/
        for(int i=1;i<=3;i++)

        {

            for(int j=1;j<=3;j++)

            {

                if(i==j) {
                    continue;

                }
                else{
                    System.out.println("Value of i is "+i + " Value of j is "+j);
                }



            }

        }




     /*   switch (2) {

            case 1:
                System.out.println("Red");
            default:
                System.out.println("Green");
            case 3:
                System.out.println("Blue");

        }*/

    }


}

