/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class Random_Generation {

    int seed = 0;
    static int all_seed = 0;

    public ArrayList<ArrayList> randomGeneration(int seeds, int generated_number, ArrayList<ArrayList> list) {
        // seed = seeds;
        all_seed += generated_number;
        ArrayList<ArrayList> random_list = new ArrayList<ArrayList>();
        for (int i = 0; i < generated_number; i++) {
            if (seeds == 1) {    // active seed
                seed = all_seed;
            } else {                  // deactive seed
                seed = Variables.seed;
            }
            int index = rand(seed, list.size());
            all_seed++;
            random_list.add(list.get(index));
        }
        return random_list;
    }

    public static int rand(int seed, int max) {
        Random rand;
        if (seed == Variables.seed) {
            rand = new Random();
        } else {
            rand = new Random(seed);
        }
        int index = rand.nextInt(max);
        return index;
    }

    public static ArrayList random(int seed, int colony_node_number) {   // ba randamy dyarydakayn har nodek chand edgey habet lanewan 2-4 ,  //  wa  aw edgana lagal kam nodanada drwstbkat
        Random rand1, rand2;

        ArrayList list = new ArrayList();
        // seed =-1;
        if (seed == Variables.seed) {   // deactive seed
            rand1 = new Random();
        } else {
            rand1 = new Random(seed);   // active seed
        }
        //  int noOfEdge = rand1.nextInt((5 - 2) + 1) + 2;        // am node awanda edgey haya 
        int noOfEdge = rand1.nextInt(5);        // am node awanda edgey haya 
        if (noOfEdge < 3) // bo away har nodek balayany kamawa 2 edgey habet
        {
            noOfEdge = 3;
        }
        all_seed += noOfEdge;
        for (int i = 0; i < noOfEdge; i++) {                 // lagal amanada edgey haya ka 0 nen
            if (seed == Variables.seed) {
                seed = Variables.seed;
                rand2 = new Random();
            } else {
                rand2 = new Random(all_seed);
                all_seed++;
            }
            int index;
            do {
                index = rand2.nextInt(colony_node_number);
            } while (list.contains(index));              // bo away la randomakada dwbarabwnawa rwnadat
            list.add(index);
        }
        return list;
    }

}
