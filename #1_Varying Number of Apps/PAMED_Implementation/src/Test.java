
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hiwa_cst
 */
public class Test {

    public static void checkLoop() {
        int x = 20;
        do {
            System.out.println(x);
            x--;
        } while (x >= 10);
        System.out.println("finish");
        int xx = 20;
        System.out.println(xx / 3);
    }

    public static void checkRandom() {
        Random r = new Random();
        for (int i = 0; i < 40; i++) {
            int rr = r.nextInt(6);//(5 - 3) + 1 + 3);
            System.out.println(rr);
        }
    }

    public static void main(String[] args) {
//        double x = 2000;
//        double y = 10000;
//        double z = x / y;
//        System.out.println(z);
//
//        double a = 0.3;
//        double b = 0.1;
//        double xx = Double.sum(a, b);
//
//        //  double xx = a * b;
//        System.out.println(a + "\t\t" + b + "\t\t\t\t" + xx);
//        if (a == b) {
//            System.out.println("Equal");
//        } else {
//            System.out.println("Not Equal");
//        }

       // checkLoop();
        checkRandom();
    }

}
