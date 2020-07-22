/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hiwa_cst
 */
import java.util.Random;
import java.util.Scanner;

public class RandomTest {

    public void t(int max) {
        for (int i = 0; i < 10; i++) {
            System.out.println(rand(i, max));
        }
        System.out.println("\n\n\n");
        for (int j = 10; j < 20; j++) {
            System.out.println(rand(j, 25));

        }
    }

    public int rand(int seed, int max) {
        //  seed = null;
        Random rand = new Random(seed);
        int n = rand.nextInt(max);
        n += 1;
        return n;
    }

    public void test() {
        Random r = new Random(1);
        for (int i = 0; i < 100; i++) {
           // int x = (r.nextInt((1000 - 200) + 1) + 200);
          //  int y = r.nextInt((1000 - 200) + 1 + 200);
                              //  randomTaskCpuCycle = rand.nextInt((maxCpuCycleOfOtherTask - minCpuCycleOfOtherTask) + 1 + minCpuCycleOfOtherTask);

           // System.out.println(x+"\t"+y);
            
            int randomInt = r.nextInt(99) + 1;
            System.out.println(randomInt);

        }
    }

    public static void main(String[] s) {
      //  while (true) {
            RandomTest ob = new RandomTest();
//            Scanner in = new Scanner(System.in);
//            System.out.print("Enter number: ");
//            int x = in.nextInt();
//            ob.t(x);
ob.test();
      //  }
    }
}
