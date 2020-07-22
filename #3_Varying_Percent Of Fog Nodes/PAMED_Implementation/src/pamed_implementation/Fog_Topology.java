/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import Datasets.Read_Dataset;
import Utilities.Parameters;
import Utilities.Random_Generation;
import Utilities.Variables;
import dijkstra.Edge;
import dijkstra.Graph;
import java.util.ArrayList;   // rand
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class Fog_Topology {

    public int methodNumber, topology_type, iot_devices;
    //  public static ArrayList<ArrayList> fog_numbers, fog_numbers_c1, fog_numbers_c2, iot_numbers, iot_numbers_c1, iot_numbers_c2;
    public static ArrayList<ArrayList> fog_numbers, iot_numbers;
    public static ArrayList access_point_numbers, access_point_numbers_c1, access_point_numbers_c2;
    Random_Generation random_gen;
    public int graph_matrix[][];
    public static int fogNumber, accessPointNumber, iotNumber, topologyNodeNumber;
    int colony_node_numbers;
    int apCounter = 0, iotCounter = 0;
    int seed;
    static int all_seed = 0;
    int colonyCounter = 0;
    Variables variables;
    int counter = 0;
    public static int seedForEachRuningTime = 0;

    public Fog_Topology(int seeds, int methodNumber, int topology_type, int iot_devices) {
        // public Fog_Topology(int seeds, int methodNumber) {

        apCounter = 0;
        iotCounter = 0;
        all_seed = 0;
        colonyCounter = 0;
        counter = 0;
        fogNumber = 0;
        accessPointNumber = 0;
        iotNumber = 0;
        topologyNodeNumber = 0;

        this.methodNumber = methodNumber;             //  only fog layer
        this.topology_type = topology_type;    //  static = 1  daynamic = 2
        this.iot_devices = iot_devices;     //  using IoT device = 1 ignore = 2    
        random_gen = new Random_Generation();
        Random rand, rand1, rand2, rand3;
        System.out.println("Enter Seed For Generating Random Delay");
        Scanner in = new Scanner(System.in);
        int seed = seedForEachRuningTime; //in.nextInt();
        // JOptionPane.showMessageDialog(null, seed);
        if (seeds == 2) {   // deactive seed
            rand = new Random(seed);
            rand1 = new Random();
            rand2 = new Random();
            rand3 = new Random();
        } else {           // active seed 
            rand = new Random(seed);
            rand1 = new Random(1);
            rand2 = new Random(2);
            rand3 = new Random(3);
        }
        if (topology_type == 1) {
            staticTopology();
        } else if (topology_type == 2) {

//            System.out.println("Enter Number Of Nodes(Fogs and Access Points) For Result Of Scenario #2  (25, 50, 100, 200 or 400) Nodes");
//            Scanner in1 = new Scanner(System.in);
//             Variables.numberOfNodesC1 = in1.nextInt();
            int numberOfTotalNodesC1 = 200;
            double numberOfFogNodesC1 = ((double) Variables.percentOfFogNodesInBothColony / (double) 100) * numberOfTotalNodesC1;
            int numberOfAccessPointC1 = numberOfTotalNodesC1 - (int) numberOfFogNodesC1;

            System.out.println("\t\t COLONY-1");

            Variables.graph_matrix_c = daynamicTopology((int) numberOfFogNodesC1, (int) numberOfAccessPointC1, "colony1", seeds, methodNumber);

            /// dway garany rozhek zyatr shwkr bo xwda mamam doziyawa ka katek lerada eshm dakrd dachw hamw fogakany try lam joray dagory
            for (ArrayList<ArrayList> fogs : fog_numbers) {
                Variables.fog_list_c1.add((ArrayList<ArrayList>) fogs.clone());
                Variables.fog_list_backup_c1.add((ArrayList<ArrayList>) fogs.clone());

            }
            for (int i = 0; i < access_point_numbers.size(); i++) {
                Variables.access_point_list_c1.add(access_point_numbers.get(i));
            }
            for (ArrayList<ArrayList> iot : iot_numbers) {
                Variables.iot_list_c1.add((ArrayList<ArrayList>) iot.clone());

            }

            int nodeNumbersC1 = Variables.fog_list_c1.size() + Variables.access_point_list_c1.size()
                    + Variables.iot_list_c1.size();
            Variables.graph_matrix_c1 = new int[nodeNumbersC1][nodeNumbersC1];
            for (int i = 0; i < Variables.graph_matrix_c.length; i++) {
                for (int j = 0; j < Variables.graph_matrix_c.length; j++) {
                    Variables.graph_matrix_c1[i][j] = Variables.graph_matrix_c[i][j];
                }
            }

//            Variables.numberOfNodesC2 = (double) Variables.numberOfNodesC1 / (double) 2;
//            double numberOfFogNodesC2 = ((double) 30 / (double) 100) * Variables.numberOfNodesC2;
//            double numberOfAccessPointC2 = Variables.numberOfNodesC2 - numberOfFogNodesC2;

            int numberOfTotalNodesC2 = 100;
            double numberOfFogNodesC2 = ((double) Variables.percentOfFogNodesInBothColony / (double) 100) * numberOfTotalNodesC2;
            int numberOfAccessPointC2 = numberOfTotalNodesC2 - (int) numberOfFogNodesC2;

            System.out.println("\t\t COLONY-2");
            Variables.graph_matrix_c = daynamicTopology((int) numberOfFogNodesC2, (int) numberOfAccessPointC2, "colony2", seeds, methodNumber);

            for (ArrayList<ArrayList> fogs : fog_numbers) {
                Variables.fog_list_c2.add((ArrayList<ArrayList>) fogs.clone());
                Variables.fog_list_backup_c2.add((ArrayList<ArrayList>) fogs.clone());

            }
            for (int i = 0; i < access_point_numbers.size(); i++) {
                Variables.access_point_list_c2.add(access_point_numbers.get(i));
            }
            for (ArrayList<ArrayList> iot : iot_numbers) {
                Variables.iot_list_c2.add((ArrayList<ArrayList>) iot.clone());
            }

            int nodeNumbersC2 = Variables.fog_list_c2.size() + Variables.access_point_list_c2.size()
                    + Variables.iot_list_c2.size();
            Variables.graph_matrix_c2 = new int[nodeNumbersC2][nodeNumbersC2];

            for (int i = 0; i < Variables.graph_matrix_c.length; i++) {
                for (int j = 0; j < Variables.graph_matrix_c.length; j++) {
                    Variables.graph_matrix_c2[i][j] = Variables.graph_matrix_c[i][j];
                }
            }

        }

        Variables.brokerc1_to_cloud_delay = rand.nextInt((Parameters.broker_to_cloud_delay_max - Parameters.broker_to_cloud_delay_min) + 1) + Parameters.broker_to_cloud_delay_min;
        Variables.brokerc2_to_cloud_delay = rand2.nextInt((Parameters.broker_to_cloud_delay_max - Parameters.broker_to_cloud_delay_min) + 1) + Parameters.broker_to_cloud_delay_min;
        Variables.brokerc1_to_brokerc2_delay = rand3.nextInt((Parameters.brokerc1_to_brokerc2_delay_max - Parameters.brokerc1_to_brokerc2_delay_min) + 1) + Parameters.brokerc1_to_brokerc2_delay_min;
        // JOptionPane.showMessageDialog(null, Variables.brokerc1_to_cloud_delay+"\n"+
        //   Variables.brokerc2_to_cloud_delay+"\n"+Variables.brokerc1_to_brokerc2_delay);
        System.out.println("delay of brokerc1 to cloud: " + Variables.brokerc1_to_cloud_delay + "\ndelay of brokerc2 to cloud: " + Variables.brokerc2_to_cloud_delay + "\ndelay of brokerc1 to brokerc2: " + Variables.brokerc1_to_brokerc2_delay);
    }

    public void staticTopology() {    // jare ba bwastet

        if (iot_devices == 1) {

        } else if (iot_devices == 2) {

        }

    }

    public int[][] daynamicTopology(int fogNumbers, int accessPointNumbers, String colony, int seeds, int methodNumber) {
        fog_numbers = new ArrayList<ArrayList>();
        access_point_numbers = new ArrayList();
        iot_numbers = new ArrayList<ArrayList>();
        Scanner in = new Scanner(System.in);

        /*am marja ke6ay bo drwstkrden w katek only cloudbw delay newan IoT bo MFR hisab nadakrd
         boya la barwary 2-4-2020 lambrd bo 4aksazy bo paperaka */
        
//        if (methodNumber == 2) // agar only cloud bw ba fog AP 0 bn w iot daxlbkret
//        {
//            fogNumber = 0;
//            accessPointNumber = 0;
//        } else {
            //   System.out.println("Enter number of Fog Nodes");
            fogNumber = fogNumbers; //10; //25; //in.nextInt();
            // System.out.println("Enter number of Access Points");
            accessPointNumber = accessPointNumbers; //15; //50; //in.nextInt();
       // }
        for (int i = 0; i < accessPointNumber; i++) {  // adding accecc point 
            access_point_numbers.add(i);
        }

        if (this.iot_devices == 1) {
            System.out.println("Enter number of IoT Devices / number of Tasks");
            if (Variables.taskPlacementCheck == true) {
                if (Variables.iotAndTask == 0) {
                    // iotNumber = Variables.nofiotAndTasks; //in.nextInt();
                    iotNumber = 200; //in.nextInt();
                    Variables.iotAndTask = iotNumber;
                    Variables.NoOfService = iotNumber;
                    //  JOptionPane.showMessageDialog(null, Variables.iotAndTask);
                } else {
                    iotNumber = Variables.iotAndTask;
                }
            } else {
                iotNumber = 10; //5; //10; //in.nextInt();
            }
            Variables.noOfIoTDeviceInEachColony.add(iotNumber); // bo har 2 colonyaka zhmaray iot devicaka haldagren w dwatr bo taskakan bo away bzanen har colonyak chanda iot devicy tyadaya
            fog_numbers = random_gen.randomGeneration(seeds, fogNumber, Read_Dataset.fog_nodes);
            iot_numbers = random_gen.randomGeneration(seeds, iotNumber, Read_Dataset.IoT_devices);
        } else if (this.iot_devices == 2) {
            fog_numbers = random_gen.randomGeneration(seeds, fogNumber, Read_Dataset.fog_nodes);
        }

        System.out.println("fogs");
        // print(fog_numbers);
        System.out.println("IoT devices");
        //  print(iot_numbers);

        int graphMatrix[][] = fog_colony(seeds);
        return graphMatrix;
    }

    public int[][] fog_colony(int seeds) {
        colonyCounter++;
        colony_node_numbers = fogNumber + accessPointNumber + iotNumber;
        graph_matrix = new int[colony_node_numbers][colony_node_numbers];
        Random rand;

        for (int i = 0; i < colony_node_numbers; i++) {

            if (seeds == 1) {   // active seed
                all_seed++;
                seed = all_seed;
            } else if (seeds == 2) {    // deactive seed 
                seed = Variables.seed;
            }
            ArrayList list = Random_Generation.random(seed, colony_node_numbers);
            for (int j = 0; j < colony_node_numbers; j++) {
                for (int k = 0; k < list.size(); k++) {             //  ex: 3 edge haya 5,6,2
                    int x = Integer.parseInt(list.get(k) + "");
                    if (i != j && j == x) {                        // agar i = j awa diagonala wata aw node relationy dabet lagal xoy boya nayalen wabet  

                        if (seeds == 1) {
                            all_seed++;
                            seed = all_seed;
                            rand = new Random(seed);
                        } else {
                            seed = Variables.seed;
                            rand = new Random();
                        }
                        int max = Parameters.fog_to_fog_delay_max;
                        if (graph_matrix[x][i] == 0) {  // bo away agar lakaty danany delay ba roandom bo [1][2] nrxeka habw ba bo [2][1] nrxy tr dananaet w 0 bet chwnka graghakaman undirecteda w yak valuy basa
                            int delay = rand.nextInt(max);
                            if (delay < 2) {
                                delay = 2;
                            }
                            graph_matrix[i][x] = delay; //rand.nextInt(delay);
                        }
                    }
                }
            }
        }

        // printGraphMatrix();
        graphEdge();
        return graph_matrix;
    }

    public void print(ArrayList<ArrayList> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public void printGraphMatrix() {
        int apIndex = fogNumber + accessPointNumber;
        apCounter = 0;
        iotCounter = 0;
        ///   column lable 
        for (int i = 0; i < graph_matrix.length; i++) {
            if (i == 0) {
                System.out.print("   ");
            }
            if (i < fogNumber) {
                System.out.print("F" + i + "  ");
            } else if (i >= fogNumber && i < apIndex) {  /////
                System.out.print("A" + apCounter + "  ");
                apCounter++;
            } else if (i >= apIndex) {
                System.out.print("I" + iotCounter + "  ");
                iotCounter++;
            }
        }
        System.out.println("");
        apCounter = 0;
        iotCounter = 0;   // reser counter for row printing lable    

        for (int i = 0; i < graph_matrix.length; i++) {

            if (i < fogNumber) {
                System.out.print("F" + i + "  ");
            } else if (i >= fogNumber && i < apIndex) {
                System.out.print("A" + apCounter + "  ");
                apCounter++;
            } else if (i >= apIndex) {
                System.out.print("I" + iotCounter + "  ");
                iotCounter++;
            }
            for (int j = 0; j < graph_matrix.length; j++) {
                System.out.print(graph_matrix[i][j] + "   ");
            }
            System.out.println("");
        }
        if (colonyCounter == 1) {
            Variables.graph_matrix_c1 = graph_matrix;   // halgrtny matrixy colony1
        } else {
            Variables.graph_matrix_c2 = graph_matrix;   // halgrnty matrixy colony2
        }
    }

    public void graphEdge() {
        ArrayList<ArrayList> list1 = new ArrayList<ArrayList>();
        int apIndex = fogNumber + accessPointNumber + iotNumber;
        int iotIndex = fogNumber + accessPointNumber;
        String x = "", y = "";
        int x1 = 0, y1 = 0;
        int apCount1 = 0, apCount2 = 0;
        /// print edges
        for (int i = 0; i < graph_matrix.length; i++) {

            if (i < fogNumber) {
                x = "F" + i;
                x1 = i;
            } else if (i >= fogNumber && i < iotIndex) {  ///
                //  } else if (i >= fogNumber) {  ///
                apCount1 = i - fogNumber;
                x = "A" + apCount1;
//                x1 = apCount1 + fogNumber;
                x1 = i; //apCount1 + fogNumber;
                //  apCount1++;
            } else if (i >= iotIndex) {
                apCount1 = i - iotIndex;
                x = "I" + apCount1;
                x1 = i; //apCount1 + iotIndex;
                // apCount1++;
            }

            for (int j = 0; j < graph_matrix.length; j++) {

                if (graph_matrix[i][j] != 0 && i < apIndex && j < apIndex) {

                    if (j < fogNumber) {
                        y = "F" + j;
                        y1 = j;   //+fogNumber;
                    } else if (j >= fogNumber && j < iotIndex) {
                        // } else if (j >= fogNumber) {
                        apCount2 = j - fogNumber;
                        y = "A" + apCount2;
//                        y1 = apCount2 + fogNumber;
                        y1 = j; //apCount2 + fogNumber;
                    } else if (j >= iotIndex) {
                        //   JOptionPane.showMessageDialog(null, iotIndex);
                        apCount2 = j - iotIndex;
                        y = "I" + apCount2;
//                        y1 = apCount2 + iotIndex;
                        y1 = j; //apCount2 + iotIndex;
                    }

                    //    System.out.print(x + ", " + y + ", " + graph_matrix[i][j] + "\t");
                    //   System.out.println(x1 + ", " + y1 + ", " + graph_matrix[i][j]);
                    ArrayList list2 = new ArrayList();
                    list2.add(x1);      // int ex: 0    ama indexy fog yan access pointaka daneret 
                    list2.add(y1);
                    list2.add(graph_matrix[i][j]);
                    list2.add(x);    // String ex: A0 or F0   dyari dakat ka F0 yan A0 wata Fog w Access dyaridakat 
                    list2.add(y);    //   dyari dakat ka F0 yan A0 wata Fog w Access dyaridakat 
                    list1.add(list2);
                }
            }
        }

        if (colonyCounter == 1) {
            Variables.edges_list_c1 = list1;       // halgrtny hamw edgakany colony1
            Variables.edges_list_backup_c1 = list1;
            Variables.edges_list_c1 = removeIoTDevices("colony1");
            //  printGraghEdges(Variables.edges_list_c1);
        } else {
            Variables.edges_list_c2 = list1;       //  halgrtny hamw edgakany colony2
            Variables.edges_list_backup_c2 = list1;
            Variables.edges_list_c2 = removeIoTDevices("colony2");
            //  printGraghEdges(Variables.edges_list_c2);

        }
    }

    public void printGraghEdges(ArrayList<ArrayList> edgeList) {
        for (int i = 0; i < edgeList.size(); i++) {

            System.out.print(edgeList.get(i).get(3) + ", ");  // ex: F0 
            System.out.print(edgeList.get(i).get(4) + ", ");  // ex: A1
            System.out.print(edgeList.get(i).get(2) + "\t");       // ex:  5  delay

            System.out.print(edgeList.get(i).get(0) + ", ");  // ex: 0 = F0
            System.out.print(edgeList.get(i).get(1) + ", ");  // ex: 4 = A1
            System.out.println(edgeList.get(i).get(2));  // ex: 5 delay

        }
    }

    public static ArrayList sendToDijkestra(ArrayList<ArrayList> edges_list, int sourceNode, int distNode) {
        Edge[] edges = new Edge[edges_list.size()];
        for (int i = 0; i < edges_list.size(); i++) {
            int sourceNodeIndex = Integer.parseInt(edges_list.get(i).get(0) + "");
            int distNodeIndex = Integer.parseInt(edges_list.get(i).get(1) + "");
            int delay = Integer.parseInt(edges_list.get(i).get(2) + "");

            String sourceNodeName = edges_list.get(i).get(3) + "";
            String distNodeName = edges_list.get(i).get(4) + "";

            edges[i] = new Edge(sourceNodeIndex, distNodeIndex, delay);
        }

        Graph ob = new Graph(edges);
        Scanner in = new Scanner(System.in);

        topologyNodeNumber = fogNumber + accessPointNumber;

//        if (sourceNode > topologyNodeNumber) {
//            JOptionPane.showMessageDialog(null, "sourceNode out of topology");
//        }
//        if (distNode > topologyNodeNumber) {
//            JOptionPane.showMessageDialog(null, "distNode out of topology");
//        }
        //   JOptionPane.showMessageDialog(null,"source node: "+sourceNode);
        ob.calculateShortestDisance(sourceNode);
        //  JOptionPane.showMessageDialog(null, ob.printResult(sourceNode, distNode));
        return ob.printResult(sourceNode, distNode);
    }

    public ArrayList<ArrayList> removeIoTDevices(String colony) // bo service placement pewest ba bwny iot device nakat la topologyakada labar awa edgakany ladabayn bo away Dijkestra hisab bo iot device nakat wak nodek
    {
        ArrayList<ArrayList> edgeLists = new ArrayList<ArrayList>();
        int topologySize = fogNumber + accessPointNumber;

        if (colony.equalsIgnoreCase("colony1")) {
            for (int i = 0; i < Variables.edges_list_c1.size(); i++) {
                int sourceNode = Integer.parseInt(Variables.edges_list_c1.get(i).get(0).toString());
                int distNode = Integer.parseInt(Variables.edges_list_c1.get(i).get(1).toString());

                if (sourceNode < topologySize && distNode < topologySize) {
                    edgeLists.add(Variables.edges_list_c1.get(i));
                }
            }
        } else {

            for (int i = 0; i < Variables.edges_list_c2.size(); i++) {
                int sourceNode = Integer.parseInt(Variables.edges_list_c2.get(i).get(0).toString());
                int distNode = Integer.parseInt(Variables.edges_list_c2.get(i).get(1).toString());

                if (sourceNode < topologySize && distNode < topologySize) {
                    edgeLists.add(Variables.edges_list_c2.get(i));

                }
            }

        }

        return edgeLists;
    }

}
