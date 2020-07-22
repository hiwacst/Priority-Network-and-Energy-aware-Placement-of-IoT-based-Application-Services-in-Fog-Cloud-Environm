/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import Datasets.Read_Dataset;
import Utilities.Random_Generation;
import Utilities.Variables;
import java.util.ArrayList;
import java.util.Scanner;
import static pamed_implementation.Fog_Topology.fog_numbers;

/**
 *
 * @author hiwa_cst
 */
public class Cloud_Topology {

    Random_Generation random_gen;
    int seed = 0;

    public Cloud_Topology(int seeds, int topology_type) {
        seed = 0;
        random_gen = new Random_Generation();
        if (topology_type == 1) {
            staticTopology();
            
        } else if (topology_type == 2) {
            daynamicTopology(seeds);
        }
    }

    public void staticTopology() {

    }

    public void daynamicTopology(int seeds) {
        
        Scanner in = new Scanner(System.in);
      //  System.out.println("Enter Number of Cloud Servers");
        int noOfCloudServers = 200; //10 //25;// in.nextInt();
        Variables.cloud_server_list1 = random_gen.randomGeneration(seeds, noOfCloudServers, Read_Dataset.cloud_servers);
//        for (int i = 0; i < Variables.cloud_server_list1.size(); i++) {
//            System.out.println("Servers: " + Variables.cloud_server_list1.get(i));
//        }

        for (ArrayList<ArrayList> cloudServer : Variables.cloud_server_list1) {
            Variables.cloud_server_list.add((ArrayList<ArrayList>) cloudServer.clone());
        }

    }
}
