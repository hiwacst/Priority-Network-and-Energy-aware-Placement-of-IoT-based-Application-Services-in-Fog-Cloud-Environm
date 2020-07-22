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
import java.text.DecimalFormat;    // S_cri  power  rand
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import sun.java2d.pipe.ValidatePipe;

/**
 *
 * @author hiwa_cst
 */
public class TaskScheduling {

    Random_Generation random_gen;
    int mehtodNumber;
    ArrayList iotList; //= new ArrayList();
    DecimalFormat formatter = new DecimalFormat("#0.0000");
    DecimalFormat formatter1 = new DecimalFormat("#0.00");
    HashSet<ArrayList> serviceIndexForEachTaskHashSet;
    int allSeed = 0;

    public TaskScheduling(int seed, int mehtodNumber) {
        iotList = new ArrayList();
        this.mehtodNumber = mehtodNumber;
        random_gen = new Random_Generation();
        generateTasks(seed);
        defineServiceLocation();
        taskPlacement(seed);
    }

    public void generateTasks(int seed) {
        Random rand, rand1, rand2, rand3;
        ArrayList<ArrayList> task = new ArrayList<ArrayList>();
        task = random_gen.randomGeneration(seed, 1, Read_Dataset.tasks);   //  lerada aw 1 taskay naw datasetaka daxwenenawa w pashan lasar ama ba randomy task drwst dakayn

        int minCpuCycleOfCriticalTask = Integer.parseInt(task.get(0).get(0).toString());
        int maxCpuCycleOfCriticalTask = Integer.parseInt(task.get(0).get(1).toString());
        int minCpuCycleOfOtherTask = Integer.parseInt(task.get(0).get(2).toString());
        int maxCpuCycleOfOtherTask = Integer.parseInt(task.get(0).get(3).toString());
        int minSizeOfTask = Integer.parseInt(task.get(0).get(4).toString());
        int maxSizeOfTask = Integer.parseInt(task.get(0).get(5).toString());

        Scanner in = new Scanner(System.in);
        System.out.println("Enter noOf Tasks: ");
        int noOfTasks; //= in.nextInt();
        if (Variables.taskPlacementCheck == true) // lakaty service placement da
        {
            if (Variables.iotAndTask == 0) {
                noOfTasks = Variables.NoOfService; //in.nextInt();
            } else {
                noOfTasks = Variables.iotAndTask;
            }
        } else // lakaty task placement da
        {
            noOfTasks = 10; //5; //10; //5;
        }

        Variables.NoOfTask = noOfTasks;

        if (noOfTasks > Variables.iot_list_c1.size()) {
            Variables.checkIoTNumber = true;
        } else {

            serviceIndexForEachTaskHashSet = new HashSet<ArrayList>();
            /*
            calling the follow method and result is adding the service
            for this hashset and each index of hashset contain the random
            service for the task of hashset index (hashset index 0 = task 0 and 
            conatian the random service for task 0)
             */

            defineServiceForEachTasks(seed, Variables.NoOfTask);
            // JOptionPane.showMessageDialog(null, "Stop and Stop");

            ArrayList<ArrayList> listOfServiceForEachTask = new ArrayList<ArrayList>();
            Iterator it = serviceIndexForEachTaskHashSet.iterator();
            for (int i = 0; i < serviceIndexForEachTaskHashSet.size(); i++) {
                ArrayList list = (ArrayList) it.next();
                listOfServiceForEachTask.add(list);

                // System.out.println(list);
            }
            for (int i = 0; i < noOfTasks; i++) {
                ArrayList list = new ArrayList();
                if (seed == 1) {
                    rand = new Random(i);
                    rand1 = new Random(i);
                } else {
                    rand = new Random();
                    rand1 = new Random();
                }
                //  int randomDeadline = (rand.nextInt((Parameters.normal_Service_Deadline_Max - Parameters.normal_Service_Deadline_Min) + 1) + Parameters.normal_Service_Deadline_Min);
                //Variables.servicesDeadline.add(randomDeadline);
                int randomTaskCpuCycle, randomTaskSize;
                do {
                    randomTaskCpuCycle = rand.nextInt((maxCpuCycleOfOtherTask - minCpuCycleOfOtherTask) + 1 + minCpuCycleOfOtherTask);
                } while (list.contains(randomTaskCpuCycle));              // bo away la randomakada dwbarabwnawa rwnadat
                list.add(randomTaskCpuCycle);

                do {
                    randomTaskSize = rand1.nextInt((maxSizeOfTask - minSizeOfTask) + 1 + minSizeOfTask);
                } while (list.contains(randomTaskSize));              // bo away la randomakada dwbarabwnawa rwnadat
                list.add(randomTaskSize);

                list.add(defineIoTDeviceForEachTask(seed, i));  // dyarikrdny iot devicek bo am taska
                // ArrayList defineServiceForEachTask = defineServiceForEachTask(seed, i);
                // list.addAll(defineServiceForEachTask);
                if (Variables.NoOfService < Variables.NoOfTask) {   // bo away har appek yakjar la layan iot devicekawa bakarbhenret 
                    JOptionPane.showMessageDialog(null, "Sorry!!  mustbe number of Service equals or more than the number of task ");
                    System.exit(0);
                } else {
                    list.addAll(listOfServiceForEachTask.get(i));
                }
                /*
                amay xwarawa tanha bo ehsakay xomana, lerada deyn 
                agar aw serviceay ka dyarikrawa bo am task la jory
                critical bw awa deyn awa randomTaskCpuCyclay ka boy generatkrawa
                day goren ba jory delay balam
                * har la eshakay xomanda agar jorey aw servicay ka dyarikrawa
                bo am taska la jory other (real w normal)bw 
                awa randomTaskCpuCycle na goren ba har awabet kala sarawa boy generatekrawa
                chwnka away saro ka bo hamw halatakantr la other drwstkrawa
               
                 */
                if (mehtodNumber == 1) {
                    String serviceType = listOfServiceForEachTask.get(i).get(0).toString();
                    //  String serviceType = defineServiceForEachTask.get(0).toString();
                    if (serviceType.equalsIgnoreCase("critical")) {
                        do {
                            randomTaskCpuCycle = (rand.nextInt((maxCpuCycleOfCriticalTask - minCpuCycleOfCriticalTask) + 1) + minCpuCycleOfCriticalTask);
                        } while (list.contains(randomTaskCpuCycle));              // bo away la randomakada dwbarabwnawa rwnadat
                        list.set(0, randomTaskCpuCycle);

                        //  /// update deadline from other to critical if using our method 
                        // int randomCriticalDeadline = (rand.nextInt((Parameters.critical_Service_Deadline_Max - Parameters.critical_Service_Deadline_Min) + 1) + Parameters.critical_Service_Deadline_Min);
                        //   Variables.servicesDeadline.set(i, randomCriticalDeadline + " critical");
                    }
                }

                Variables.task_list.add(list);
                Variables.task_list_backup.add(list);
            }

            System.out.println("<<<<<<<<<<<   >>>>>>>>>>>>>>");
            if (mehtodNumber != 1) {
                // ama la 2-4-2020 zyadkra bo away ka la algokanitrda hisab bo critical w normal bkayn
                // wak 4on la kamek sarwtrda bo algoy xoman hisaby bo dakayn 
                // wata cpu cycley taskakan har cpu cycle normal war nagren balkw wak algoy xoman
                // bo aw joranay ka criticaln awa ba cpu cycley criticaly bo dabneyn ba am algoyana
                // hisab bo critical w normale6 nakan
                ArrayList indexOfCriticalServiceForOtherAlgo = new ArrayList();
                ArrayList valueOfCriticalCPUCycleForOtherAlgo = new ArrayList();

                for (int i = 0; i < Variables.number_Of_Critical_Services; i++) {
                    if (seed == 1) {
                        rand2 = new Random(i);
                    } else {
                        rand2 = new Random();
                    }
                    int index;
                    int maxValue = Variables.NoOfTask - 1; // bo away bbet ba index w 200 warnagret 4wnka 200 app man haya w last index = 199

                    if (Variables.NoOfTask == Variables.number_Of_Critical_Services) {
                        indexOfCriticalServiceForOtherAlgo.add(i);
                    } else {
                        do {
                            index = (rand2.nextInt((maxValue - 1) + 1) + 1);
                            //      System.out.println("111");
                        } while (indexOfCriticalServiceForOtherAlgo.contains(index));              // bo away la randomakada dwbarabwnawa rwnadat
                        indexOfCriticalServiceForOtherAlgo.add(index);
                    }
//                    do {
                    index = (rand2.nextInt((maxCpuCycleOfCriticalTask - minCpuCycleOfCriticalTask) + 1) + minCpuCycleOfCriticalTask);
//                        System.out.println("2222   "+index+""+valueOfCriticalCPUCycleForOtherAlgo);
//                        JOptionPane.showMessageDialog(null, "stop: "+valueOfCriticalCPUCycleForOtherAlgo.size());
//                    } while (valueOfCriticalCPUCycleForOtherAlgo.contains(index));              // bo away la randomakada dwbarabwnawa rwnadat
                    valueOfCriticalCPUCycleForOtherAlgo.add(index);
                }

                //JOptionPane.showMessageDialog(null, indexOfCriticalServiceForOtherAlgo.size()+"\n"+Variables.number_Of_Critical_Services);
                for (int i = 0; i < indexOfCriticalServiceForOtherAlgo.size(); i++) {

                    int taskIndex = Integer.parseInt(indexOfCriticalServiceForOtherAlgo.get(i).toString());
                    //System.out.println(">>>: "+taskIndex);
                    int value = Integer.parseInt(valueOfCriticalCPUCycleForOtherAlgo.get(i).toString());
                    Variables.task_list.get(taskIndex).set(0, value);
                    Variables.task_list_backup.get(taskIndex).set(0, value);

                }
            }
        }

//        for (int i = 0; i < Variables.taskDeadline.size(); i++) {
//
//            System.out.println(i + "\t\t" + Variables.taskDeadline.get(i));
//        }
        // JOptionPane.showMessageDialog(null, "deadline");
    }

    public int defineIoTDeviceForEachTask(int seed, int taskIndex) {
        Random rand;
        int noOfIoTDeviceInColony1 = Integer.parseInt(Variables.noOfIoTDeviceInEachColony.get(0).toString());    // wa daman nawa ka colony yakam hsaba bo IoT device lerawa requestakan den wata IoT devicey c2 requestyan nya 
        if (seed == 1) {
            rand = new Random(taskIndex);
        } else {
            rand = new Random();
        }

        /*
        lam methodada index haldabzheret w sayri listaka dakayn bzanen aya dwbara botawa yan na 
        bo away iot device dwbaranabetawa
         */
        int iotIndex;
        do {
            iotIndex = rand.nextInt(noOfIoTDeviceInColony1); // - 1) + 1 + 1);
        } while (iotList.contains(iotIndex));              // bo away la randomakada dwbarabwnawa rwnadat
        //  JOptionPane.showMessageDialog(null, "iot:  "+iotIndex);
        iotList.add(iotIndex);

        int iotIndex1 = Integer.parseInt(iotList.get(iotList.size() - 1).toString());
        return iotIndex1;
    }

    public void defineServiceForEachTasks(int seed, int noOfTasks) {
        /*
        in here using Hashset for avoiding the duplicatio of using one app
        for more than one task, that means mustbe every task requested
        for only app, 
        
        in here we have a wihle by size of number of tasks and 
        using hashset for adding the arraylist with to value for each
        index of hashset (first value is app type and second value is appIndex)
        
         */

        Random rand;
        int noOfServices = Variables.service_list.size() - 1;
        if (this.mehtodNumber == 2 || this.mehtodNumber == 3 || this.mehtodNumber == 4 || this.mehtodNumber == 5) {   // agar random placment bw
            while (serviceIndexForEachTaskHashSet.size() < noOfTasks) {
                ArrayList list = new ArrayList();
                if (seed == 1) {
                    allSeed++;
                    rand = new Random(allSeed);
                } else {
                    rand = new Random();
                }

                int serviceIndex = (rand.nextInt((noOfServices - 0) + 1) + 0);
                list.add("Ignore");
                list.add(serviceIndex);
                serviceIndexForEachTaskHashSet.add(list);
                //  System.out.println(noOfTasks+"\nhiwa\t\t"+serviceIndexForEachTaskHashSet.size());

            }
            // JOptionPane.showMessageDialog(null, "here");

        } else if (this.mehtodNumber == 1) {   // agar random placment bw
            // int noOfApp = Variables.applications.size() - 1;
            // int noOfCriticalServices = noOfServices / 3;
            // int noOfCriticalServices = Variables.number_Of_Critical_Services;
            while (serviceIndexForEachTaskHashSet.size() < noOfTasks) {
                ArrayList list = new ArrayList();
                if (seed == 1) {
                    allSeed++;
                    rand = new Random(allSeed);
                } else {
                    rand = new Random();
                }
                int serviceIndex = (rand.nextInt((noOfServices - 0) + 1) + 0);
                int deadline = Integer.parseInt(Variables.servicesDeadline.get(serviceIndex).toString());

                //  if (serviceIndex <= noOfCriticalServices) {
                if (deadline <= Parameters.critical_Service_Deadline_Max) {
                    int criticalServiceIndex = (rand.nextInt((Variables.S_cri.size() - 1 - 0) + 1) + 0);
                    list.add("critical");
                    list.add(criticalServiceIndex);
                    serviceIndexForEachTaskHashSet.add(list);
                } else {
                    int normalServiceIndex = (rand.nextInt((Variables.S_normal.size() - 1 - 0) + 1) + 0);
                    list.add("other");
                    // if(normalServiceIndex == 100)
                    //  {
                    // System.out.println("\t\t"+normalServiceIndex);
                    //  }
                    list.add(normalServiceIndex);
                    serviceIndexForEachTaskHashSet.add(list);
                }
            }
        }
        //   JOptionPane.showMessageDialog(null, "OK");

    }

//    public ArrayList defineServiceForEachTask(int seed, int taskIndex) {
//        Random rand;
//        ArrayList list = new ArrayList();
//        int noOfService = Variables.service_list.size();   // wargrtny zhmaray service bo away randomly dyaribkay ka am taska bo kam serviceaya
//        if (seed == 1) {
//            rand = new Random(taskIndex);
//        } else {
//            rand = new Random();
//        }
//        int serviceIndex = rand.nextInt(noOfService); // - 0) + 1 + 0);
//        if (this.mehtodNumber == 4 || this.mehtodNumber == 3 || mehtodNumber == 5) {   // agar random placment bw
//            list.add("Ignore");
//            boolean check;
//            int serviceIndex1;
//            do {
////                serviceIndex1 = rand.nextInt((noOfService - 0) + 1 + 0);
//                serviceIndex1 = rand.nextInt(noOfService); // - 0) + 1 + 0);
//                check = chekWaitedService(serviceIndex1, "Ignore");
//            } while (check == true);
//            list.add(serviceIndex1);
//
//        } else {
//            /// DEFINE SERVICE TYPE (CRITICAL OR OTHER) 
//            int noOfCriticalService = Variables.S_cri.size() - 1;
//            if (serviceIndex <= noOfCriticalService) {    // . agar service index bchwktrbw la sizey critical awa manay waya am serviceay ka dawakrawa la layan am taskawa critical w aw indexay haya la newan criticalakanda 
//                list.add("critical");
//                boolean check;
//                int serviceIndex1;
//                serviceIndex++;  // bo away axr indexy critical chancy halbzhardny habet
//                do {
//                    serviceIndex1 = rand.nextInt(serviceIndex);  // - 0) + 1 + 0);
//                    check = chekWaitedService(serviceIndex1, "critical");
//                } while (check == true);
//                list.add(serviceIndex1);
//            } else {
//                list.add("other");
//                noOfCriticalService++;   // bo away la other da la indexy other list teparnakat
//                serviceIndex = serviceIndex - noOfCriticalService;
//                boolean check;
//                int serviceIndex1;
//                do {
//                    serviceIndex1 = rand.nextInt((serviceIndex - 0) + 1 + 0);
//                    check = chekWaitedService(serviceIndex1, "other");
//                } while (check == true);
//                list.add(serviceIndex1);
//            }
//        }
//        return list;
//    }
//    /*
//    checking the list of waited service and task canot send the request for any one of them 
//     */
    public boolean chekWaitedService(int randomServiceIndex, String serviceType) {
        boolean check = false;
        if (serviceType.equalsIgnoreCase("Ignore")) {   // awar random placement bw

            for (int i = 0; i < Variables.waitedServices.size(); i++) {
                int indexOfService = Integer.parseInt(Variables.waitedServices.get(i).get(0).toString());
                if (randomServiceIndex == indexOfService) { // && serviceType.equalsIgnoreCase(serviceType1)) {
                    check = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < Variables.waitedServices.size(); i++) {
                String serviceType1 = Variables.waitedServices.get(i).get(0).toString();
                int indexOfService = Integer.parseInt(Variables.waitedServices.get(i).get(2).toString());
                if (randomServiceIndex == indexOfService && serviceType.equalsIgnoreCase(serviceType1)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public void defineServiceLocation() // bzanen aw serviceay ka am taska daway dakat lakweda install krawa wata kam layera w kam noda
    {

        int fogIndex = 0, serverIndex = 0;
        String fogColony = "";
        for (int i = 0; i < Variables.task_list.size(); i++) {
            //  JOptionPane.showMessageDialog(null, "1:   " + Variables.task_list.get(i)); // + "\n" + Variables.X_sf.get(0) + "\n" + Variables.X_sc.get(0));

            String serviceType = Variables.task_list.get(i).get(3).toString();
            int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());
            boolean check = false, check1 = false;

            // CHECK FOG LAYER FOR FIND THE LOCATION OF THIS SERVICE
            for (int j = 0; j < Variables.X_sf.size(); j++) {
                int serviceIndex1 = Integer.parseInt(Variables.X_sf.get(j).get(0).toString());
                int index;//= Variables.X_sf.get(j).size() - 1;
                if (this.mehtodNumber == 4 || this.mehtodNumber == 3 || mehtodNumber == 5) {
                    index = Variables.X_sf.get(j).size() - 2;
                } else {
                    index = Variables.X_sf.get(j).size() - 1;
                }
                String serviceType1 = Variables.X_sf.get(j).get(index).toString();

                if (serviceIndex == serviceIndex1 && serviceType.equalsIgnoreCase(serviceType1)) {
                    fogIndex = Integer.parseInt(Variables.X_sf.get(j).get(1).toString());
                    int index1;//= Variables.X_sf.get(j).size() - 1;
                    if (this.mehtodNumber == 4 || this.mehtodNumber == 3 || mehtodNumber == 5) {
                        index1 = Variables.X_sf.get(j).size() - 3;
                    } else {
                        index1 = Variables.X_sf.get(j).size() - 3;
                    }
                    fogColony = Variables.X_sf.get(j).get(index1).toString();  // colony1 or colony2

                    // JOptionPane.showMessageDialog(null, Variables.X_sf.get(j)+"\n Hiwa\n"+fogColony);
                    check = true;
                }
            }

            if (check == true) {
                Variables.task_list.get(i).add(fogColony);
                Variables.task_list.get(i).add(fogIndex);
            } else {

                // CHECK CLOUD FOR FIND THE LOCATION OF THIS SERVICE            
                //   if (topology == 1) {    // wata agar cloud topo habw amja X_sc check bkat 
                for (int j = 0; j < Variables.X_sc.size(); j++) {

                    int serviceIndex1 = Integer.parseInt(Variables.X_sc.get(j).get(0).toString());
                    int index = Variables.X_sc.get(j).size() - 2;
                    String serviceType1 = Variables.X_sc.get(j).get(index).toString();
                    // JOptionPane.showMessageDialog(null, serviceType1+"\n"+serviceType);
                    if (serviceIndex == serviceIndex1 && serviceType.equalsIgnoreCase(serviceType1)) {
                        serverIndex = Integer.parseInt(Variables.X_sc.get(j).get(1).toString());
                        check1 = true;
                    }

                    //   System.out.println(Variables.X_sc.get(j));
                }
                //  JOptionPane.showMessageDialog(null, "else\n"+Variables.X_sc.get(0)+"\n"+
                //  Variables.task_list.get(i));
                // JOptionPane.showMessageDialog(null, check1);
                if (check1 == true) {
                    Variables.task_list.get(i).add("server");
                    Variables.task_list.get(i).add(serverIndex);
                }
            }
            //   JOptionPane.showMessageDialog(null, "2:   " + Variables.task_list.get(i)); // + "\n" + Variables.X_sf.get(0) + "\n" + Variables.X_sc.get(0));
        }
    }

    public void taskPlacement(int seed) {

        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or service . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
         */
        taskExecutionTime();
        //  JOptionPane.showMessageDialog(null, mehtodNumber + "\n" + Variables.task_list.get(0));
        taskDelay();
        taskEnergy(seed);

    }

    public void taskExecutionTime() {

        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or service . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
         */
        for (int i = 0; i < Variables.task_list.size(); i++) {

            double executionTime = 0.0;
            int taskCPUCycle = Integer.parseInt(Variables.task_list.get(i).get(0).toString());
            String serviceType = Variables.task_list.get(i).get(3).toString();
            int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());

            //  System.out.println(serviceType +"\t"+serviceIndex);
            if (serviceType.equalsIgnoreCase("critical")) {
                int serviceCPU = Integer.parseInt(Variables.S_cri.get(serviceIndex).get(0).toString());
                executionTime = (double) taskCPUCycle / (double) serviceCPU;
                executionTime = executionTime * 1000;
                Variables.task_list.get(i).add(executionTime);
            } else if (serviceType.equalsIgnoreCase("other")) {
                //  int serviceCPU = Integer.parseInt(Variables.S_all.get(serviceIndex).get(0).toString());
                int serviceCPU = Integer.parseInt(Variables.S_normal.get(serviceIndex).get(0).toString());
                executionTime = (double) taskCPUCycle / (double) serviceCPU;
                executionTime = executionTime * 1000;
                Variables.task_list.get(i).add(executionTime);
            } else if (serviceType.equalsIgnoreCase("Ignore")) {
                int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
                executionTime = (double) taskCPUCycle / (double) serviceCPU;
                executionTime = executionTime * 1000;
                Variables.task_list.get(i).add(executionTime);
            }

        }

        // JOptionPane.showMessageDialog(null, "oooooo");
    }

    public void taskDelay() {
        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or server . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
        index 7: execution time 
        index 8: in here adding delay 
        
         */

//        for (int i = 0; i < Variables.task_list.size(); i++) {
//            System.out.println(Variables.task_list.get(i));
//        }
        //  JOptionPane.showMessageDialog(null, "Stop");
        for (int i = 0; i < Variables.task_list.size(); i++) {
            int iotNodeIndex = Integer.parseInt(Variables.task_list.get(i).get(2).toString());
            String serviceLocation = Variables.task_list.get(i).get(5).toString();
            int fogOrServerIndex = Integer.parseInt(Variables.task_list.get(i).get(6).toString());

            if (serviceLocation.equalsIgnoreCase("colony1")) {
                int fogSize = Variables.fog_list_c1.size();
                int accessPointSize = Variables.access_point_list_c1.size();
                int sourceNode;
                int lastIndex = fogSize + accessPointSize - 1;
                if (iotNodeIndex == 0) {
                    sourceNode = fogSize + accessPointSize + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                } else {
                    sourceNode = fogSize + accessPointSize + iotNodeIndex + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                }
                sourceNode--;   // . bo away bbeta index 
                ArrayList<ArrayList> edges = addCurrentIoTNodeEdgesToEdgeList(lastIndex, sourceNode, Variables.edges_list_c1, Variables.edges_list_backup_c1);
                lastIndex++;                        // wata  fog + ap + 1 yani hamo iotyak har dakata fog+ap+1 balam ba haman delay naw topologyakawa
                ArrayList iotNodeToBrokerPath = Fog_Topology.sendToDijkestra(edges, lastIndex, 0);
                //  ArrayList iotNodeToBrokerPath = Fog_Topology.sendToDijkestra(edges, sourceNode, 0);
                //  int delay1 = 11;                      // Integer.parseInt(iotNodeToBrokerPath.get(2).toString());
                int delay1 = Integer.parseInt(iotNodeToBrokerPath.get(2).toString());
                // JOptionPane.showMessageDialog(null, delay1);
                delay1 = delay1 * 2;
                if (delay1 < 0) {
                    delay1 = delay1 * -1;
                }
                ArrayList brokerToFogNodePath = Fog_Topology.sendToDijkestra(edges, fogOrServerIndex, 0);
                int delay2 = Integer.parseInt(brokerToFogNodePath.get(2).toString());
                delay2 = delay2 * 2;
                if (delay2 < 0) {
                    delay2 = delay2 * -1;
                }
                int delay = delay1 + delay2;

                double exeTime = Double.parseDouble(Variables.task_list.get(i).get(7).toString());
                // double exeAndWaitedTime = addTaskExecutionAndWitedTime(i, serviceLocation);
                double allDelay = (double) delay + exeTime;
                double allDelay1 = Double.parseDouble(formatter.format(allDelay));
                Variables.task_list.get(i).add(allDelay1);
//                if (allDelay1 <= 0) {
//                    JOptionPane.showMessageDialog(null, "Delay is less than 0 "
//                            + Fog_Topology.seedForEachRuningTime);
//                }
            } /*
            
            
             */ else if (serviceLocation.equalsIgnoreCase("colony2")) {
                /*
                    ba bcheta colony2sh dabet ha delay newan iot bo brokery c1 wa garanawashy hisab bkret
                    basht delay newa hardw brokeraka w delay naw c2shy bo hisab dakayn
                 */
                /// DELAY IN COLONY1
                int fogSize = Variables.fog_list_c1.size();
                int accessPointSize = Variables.access_point_list_c1.size();
                int sourceNode;
                int lastIndex = fogSize + accessPointSize - 1;
                if (iotNodeIndex == 0) {
                    sourceNode = fogSize + accessPointSize + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                } else {
                    sourceNode = fogSize + accessPointSize + iotNodeIndex + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                }
                sourceNode--;   // . bo away bbeta index 
                ArrayList<ArrayList> edges = addCurrentIoTNodeEdgesToEdgeList(lastIndex, sourceNode, Variables.edges_list_c1, Variables.edges_list_backup_c1);
                lastIndex++;
                ArrayList iotNodeToBrokerPath = Fog_Topology.sendToDijkestra(edges, lastIndex, 0);
                int delay1 = Integer.parseInt(iotNodeToBrokerPath.get(2).toString());
                delay1 = delay1 * 2;
                if (delay1 < 0) {
                    delay1 = delay1 * -1;
                }
                /// DELAY IN COLONY2
                int fogSize1 = Variables.fog_list_c2.size();
                int accessPointSize1 = Variables.access_point_list_c2.size();
                int sourceNode1;
                int lastIndex1 = fogSize + accessPointSize - 1;
                if (iotNodeIndex == 0) {
                    sourceNode1 = fogSize + accessPointSize + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                } else {
                    sourceNode1 = fogSize + accessPointSize + iotNodeIndex + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                }

                sourceNode1--;   // bo away bbeta index
                ArrayList<ArrayList> edges1 = addCurrentIoTNodeEdgesToEdgeList(lastIndex1, sourceNode1, Variables.edges_list_c2, Variables.edges_list_backup_c2);
                ArrayList brokerToFogNodePath = Fog_Topology.sendToDijkestra(edges1, fogOrServerIndex, 0);
                int delay2 = Integer.parseInt(brokerToFogNodePath.get(2).toString());
                delay2 = delay2 * 2;
                if (delay2 < 0) {
                    delay2 = delay2 * -1;
                }
                int delay = delay1 + delay2 + Variables.brokerc1_to_brokerc2_delay;
                double exeTime = Double.parseDouble(Variables.task_list.get(i).get(7).toString());
//                double exeAndWaitedTime = addTaskExecutionAndWitedTime(i, serviceLocation);
                double allDelay = (double) delay + exeTime;
                double allDelay1 = Double.parseDouble(formatter.format(allDelay));
                Variables.task_list.get(i).add(allDelay1);
//                if (allDelay1 <= 0) {
//                    JOptionPane.showMessageDialog(null, "Delay is less than 0 "
//                            + Fog_Topology.seedForEachRuningTime);
//                }

            } else if (serviceLocation.equalsIgnoreCase("server")) {

                int fogSize = Variables.fog_list_c1.size();
                int accessPointSize = Variables.access_point_list_c1.size();
                int sourceNode;
                int lastIndex = fogSize + accessPointSize - 1;
                if (iotNodeIndex == 0) {
                    sourceNode = fogSize + accessPointSize + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                } else {
                    sourceNode = fogSize + accessPointSize + iotNodeIndex + 1;               // 1 = iotNode; wata ba topologyaka 1 dany bo zyadbbet lajyaty iot nodaka
                }
                sourceNode--;   // . bo away bbeta index 
                // Variables.edges_list_c1 = Variables.edges_list_backup_c1 ;
                ArrayList<ArrayList> edges = addCurrentIoTNodeEdgesToEdgeList(lastIndex, sourceNode, Variables.edges_list_c1, Variables.edges_list_backup_c1);
                lastIndex++;                        // wata  fog + ap + 1 yani hamo iotyak har dakata fog+ap+1 balam ba haman delay naw topologyakawa
                // JOptionPane.showMessageDialog(null, Variables.edges_list_c1+"\n"+edges+"\n"+Variables.edges_list_backup_c1);

                ArrayList iotNodeToBrokerPath = Fog_Topology.sendToDijkestra(edges, lastIndex, 0);
                int delay1 = Integer.parseInt(iotNodeToBrokerPath.get(2).toString());
                delay1 = delay1 * 2;

                int delay = Variables.brokerc1_to_cloud_delay * 2;
                double exeTime = Double.parseDouble(Variables.task_list.get(i).get(7).toString());
                //  double exeAndWaitedTime = addTaskExecutionAndWitedTime(i, serviceLocation);
                double allDelay = (double) delay + delay1 + exeTime;
                double allDelay1 = Double.parseDouble(formatter.format(allDelay));
                Variables.task_list.get(i).add(allDelay1);
//                if (allDelay1 <= 0) {
//                    JOptionPane.showMessageDialog(null, "Delay is less than 0 "
//                            + Fog_Topology.seedForEachRuningTime);
//                }
            }
        }
    }

//    /*  bo halateka ka agar yak service kalasar deviceka zyatr 
//    la taskek daway amyan krdbw
//     awa delay t2 = exeTimet1 + Dijkerstra delay of t2
//     wa delay t3 = exeTimet1 + exeTimet2 + Dijkerstra delay of t3 
//     */
//    public double addTaskExecutionAndWitedTime(int taskIndex, String colony) {
//        double executionAndWaitedTimes = 0.0;
//        int serviceIndexofCurrentTask = Integer.parseInt(Variables.task_list.get(taskIndex).get(4).toString());
//
//        if (colony.equalsIgnoreCase("colony1")) {
////            for (int i = 0; i <= taskIndex; i++) {  // wata cheky taskakany peshtr bka w katakayan hisb bka bo am taska taza agar daway haman servicyankrdwa
////                int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());
////                double exeTime = Double.parseDouble(Variables.task_list.get(i).get(7).toString());
////                if (serviceIndexofCurrentTask == serviceIndex && colony.equalsIgnoreCase("colony1")) {
////                    executionAndWaitedTimes = Double.sum(executionAndWaitedTimes, exeTime);
////                }
////            }
//        } else if (colony.equalsIgnoreCase("colony2")) {
//            for (int i = 0; i <= taskIndex; i++) {  // wata cheky taskakany peshtr bka w katakayan hisb bka bo am taska taza agar daway haman servicyankrdwa
//                int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());
//                double exeTime = Double.parseDouble(Variables.task_list.get(i).get(7).toString());
//                if (serviceIndexofCurrentTask == serviceIndex && colony.equalsIgnoreCase("colony2")) {
//                    executionAndWaitedTimes = Double.sum(executionAndWaitedTimes, exeTime);
//                }
//            }
//
//        } else if (colony.equalsIgnoreCase("server")) {
//            for (int i = 0; i <= taskIndex; i++) {  // wata cheky taskakany peshtr bka w katakayan hisb bka bo am taska taza agar daway haman servicyankrdwa
//                int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());
//                double exeTime = Double.parseDouble(Variables.task_list.get(i).get(7).toString());
//                if (serviceIndexofCurrentTask == serviceIndex && colony.equalsIgnoreCase("server")) {
//                    executionAndWaitedTimes = Double.sum(executionAndWaitedTimes, exeTime);
//                }
//            }
//
//        }
//
//        return executionAndWaitedTimes;
//    }
    public void taskEnergy(int seed) {

        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or service . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
        index 7: execution time
        index 8: task delay
        index 9: added in here (task energy)
         */
        for (int i = 0; i < Variables.task_list.size(); i++) {
            // int taskCpuCycle = Integer.parseInt(Variables.task_list.get(i).get(0).toString());
            //  int taskSize = Integer.parseInt(Variables.task_list.get(i).get(1).toString());

            String serviceType = Variables.task_list.get(i).get(3).toString();
            int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());
            String serviceLocation = Variables.task_list.get(i).get(5).toString();
            int fogOrServerIndex = Integer.parseInt(Variables.task_list.get(i).get(6).toString());

            int serviceCPU = 0, deviceCPU = 0;
            if (serviceType.equalsIgnoreCase("critical")) {
                serviceCPU = Integer.parseInt(Variables.S_cri.get(serviceIndex).get(0).toString());
            } else if (serviceType.equalsIgnoreCase("other")) {
                //  serviceCPU = Integer.parseInt(Variables.S_all.get(serviceIndex).get(0).toString());
                serviceCPU = Integer.parseInt(Variables.S_normal.get(serviceIndex).get(0).toString());
            } else if (serviceType.equalsIgnoreCase("Ignore")) {
                serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            }

            double deviceMaxPower = 0; // Double.parseDouble(Variables.fog_list_c1.get(i).get(3).toString());
            double deviceIdlePower = 0; //Double.parseDouble(Variables.fog_list_c1.get(i).get(4).toString());

            if (serviceLocation.equalsIgnoreCase("colony1")) {
                if (mehtodNumber == 5) {
                    deviceCPU = Integer.parseInt(Variables.fog_list_backup_c1.get(fogOrServerIndex).get(1).toString());
                    deviceMaxPower = Double.parseDouble(Variables.fog_list_backup_c1.get(fogOrServerIndex).get(3).toString());
                    deviceIdlePower = Double.parseDouble(Variables.fog_list_backup_c1.get(fogOrServerIndex).get(4).toString());

                } else {
                    deviceCPU = Integer.parseInt(Variables.fog_list_backup_c1.get(fogOrServerIndex).get(0).toString());
                    deviceMaxPower = Double.parseDouble(Variables.fog_list_backup_c1.get(fogOrServerIndex).get(2).toString());
                    deviceIdlePower = Double.parseDouble(Variables.fog_list_backup_c1.get(fogOrServerIndex).get(3).toString());
                }

            } else if (serviceLocation.equalsIgnoreCase("colony2")) {
                if (mehtodNumber == 5) {
                    deviceCPU = Integer.parseInt(Variables.fog_list_backup_c2.get(fogOrServerIndex).get(1).toString());
                    deviceMaxPower = Double.parseDouble(Variables.fog_list_backup_c2.get(fogOrServerIndex).get(3).toString());
                    deviceIdlePower = Double.parseDouble(Variables.fog_list_backup_c2.get(fogOrServerIndex).get(4).toString());

                } else {
                    deviceCPU = Integer.parseInt(Variables.fog_list_backup_c2.get(fogOrServerIndex).get(0).toString());
                    deviceMaxPower = Double.parseDouble(Variables.fog_list_backup_c2.get(fogOrServerIndex).get(2).toString());
                    deviceIdlePower = Double.parseDouble(Variables.fog_list_backup_c2.get(fogOrServerIndex).get(3).toString());

                }

            } else if (serviceLocation.equalsIgnoreCase("server")) {
                if (mehtodNumber == 5) {
                    deviceCPU = Integer.parseInt(Variables.cloud_server_list1.get(fogOrServerIndex).get(1).toString());
                    deviceMaxPower = Double.parseDouble(Variables.cloud_server_list1.get(fogOrServerIndex).get(3).toString());
                    deviceIdlePower = Double.parseDouble(Variables.cloud_server_list1.get(fogOrServerIndex).get(4).toString());

                } else {
                    deviceCPU = Integer.parseInt(Variables.cloud_server_list1.get(fogOrServerIndex).get(0).toString());
                    deviceMaxPower = Double.parseDouble(Variables.cloud_server_list1.get(fogOrServerIndex).get(2).toString());
                    deviceIdlePower = Double.parseDouble(Variables.cloud_server_list1.get(fogOrServerIndex).get(3).toString());

                }

            }

            double energy = F_MEF_Algo(i, seed, serviceCPU, deviceCPU, deviceMaxPower, deviceIdlePower);
            double energy1 = Double.parseDouble(formatter.format(energy));
            Variables.task_list.get(i).add(energy1);
        }
    }

    public double F_MEF_Algo(int taskIndex, int seed, int serviceCPU, int deviceCPU, double deviceMaxPower, double deviceIdlePower) {
        Random rand;
        if (seed == 2) {            // deactive seed
            rand = new Random();
        } else {
            rand = new Random(taskIndex);
        }
        /// yasakaman gorewa bo yasay taza 8-11-2019
        double power = (double) serviceCPU / (double) deviceCPU;
        double powerConsumption = (deviceMaxPower - deviceIdlePower) * power + deviceIdlePower;
        double taskExeTime = Double.parseDouble(Variables.task_list.get(taskIndex).get(7).toString());
        double E_s_f = powerConsumption * taskExeTime;  // (kWms)
        return E_s_f;

    }

    /*
    fog topo conatain the number of fogs and access points
    but in here try to add the current iot node to fog topo 
    
    hata era edgelistc1 w edgelistc2 tanha edgey newan fog w access pointakany tyadaya
    balem lerada dabet edgeakany aw iot nodeay kahaman laga fog w ap zyadbkayn bo listaka
    bo away btwanen beneren bo dijkestra w la iot nodakawa bo broker shortest path yan delay man peblet
     */
    public ArrayList<ArrayList> addCurrentIoTNodeEdgesToEdgeList(int lastIndex, int iotNodeIndex, ArrayList<ArrayList> edgeList1, ArrayList<ArrayList> edgeList2) {
        ArrayList<ArrayList> edgeList = new ArrayList<ArrayList>();
        edgeList.addAll(edgeList1);   //   edgeList1 am lista hamw edgakany newan fog Apy tyadaya w la xwarawash edgy aw iot noday bo zyad dakayn ka taskakay daneren
        for (int i = 0; i < edgeList2.size(); i++) {   //  edgeList2 ama backupy hamw edgekana ka edy newan fog w AP w IoT nody tyadaya
//JOptionPane.showMessageDialog(null, "eee:  "+edgeList2.get(0));
            int source = Integer.parseInt(edgeList2.get(i).get(0).toString());   // node1 har edgek
            int dist = Integer.parseInt(edgeList2.get(i).get(1).toString());     // node2 har edgek
            int delay = Integer.parseInt(edgeList2.get(i).get(2).toString());     // node2 har edgek

            if (source == iotNodeIndex || dist == iotNodeIndex) {

                // iotNodeIndex = 29
                if (source == iotNodeIndex) {
                    source = lastIndex + 1;   //  lastIndex = 24 + 1
                } else if (source > lastIndex) {
                    do {                          // bo away agar zor gawrabw bardwam bchwky bkatawa hata la last index bchwktrbbet
                        source = source - lastIndex;
                        source = Math.abs(source);
                    } while (source > lastIndex);
                }

                if (dist == iotNodeIndex) {
                    dist = lastIndex + 1;
                } else if (dist > lastIndex) {
                    do {                          // bo away agar zor gawrabw bardwam bchwky bkatawa hata la last index bchwktrbbet
                        dist = dist - lastIndex;
                        dist = Math.abs(dist);
                    } while (dist > lastIndex);
                }

//                edgeList2.get(i).set(0, source);
//                edgeList2.get(i).set(1, dist);
//                edgeList.add(edgeList2.get(i));
                ArrayList list = new ArrayList();
                list.add(source);
                list.add(dist);
                list.add(delay);
                list.add("E");
                list.add("E");
                edgeList.add(list);

            }
        }

        return edgeList;
    }

    /*
    only fog our proposed algo
     */
    public ArrayList taskType() {

        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or service . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
        index 7: execution time
        index 8: task delay
        index 9: added in here (task energy)
         */
        ArrayList taskTypeCounter = new ArrayList();
        // int noOfServices = Variables.service_list.size();
        //int noOfCriticalServices = noOfServices / 3;
        // int noOfCriticalServices = Variables.number_Of_Critical_Services;
        // int noOfRealServices = noOfCriticalServices * 2;
        // int noOfRealServices = noOfCriticalServices ;

        int criticalCounter = 0, realCounter = 0, normalCounter = 0;
        for (int i = 0; i < Variables.task_list.size(); i++) {
            String ServiceType = Variables.task_list.get(i).get(3).toString();

            //  int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString().toString());  // appName ex: app0
            if (ServiceType.equalsIgnoreCase("critical")) {
                criticalCounter++;
            } else if (ServiceType.equalsIgnoreCase("other")) {
                //  if (serviceIndex < noOfRealServices) // Real Service
                //  {
                // realCounter++;
                // } else // Normal Apps
                // {
                normalCounter++;
                // }
            }
        }
        taskTypeCounter.add(criticalCounter);
        taskTypeCounter.add(realCounter);
        taskTypeCounter.add(normalCounter);

        return taskTypeCounter;

    }

    public ArrayList averageDelayforEveryTaskType() {

        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or service . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
        index 7: execution time
        index 8: task delay
        index 9: added in here (task energy)
         */
        ArrayList averageDelay = new ArrayList();

        // int noOfServices = Variables.service_list.size();
        // int noOfCriticalServices = Variables.number_Of_Critical_Services; //noOfServices / 3;
        //  int noOfRealServices = noOfCriticalServices * 2;
        int criticalCounter = 0, realCounter = 0, normalCounter = 0;
        double sumDelayOfCriticalTask = 0, sumDelayOfRealTask = 0, sumDelayOfnormalTask = 0;

        for (int i = 0; i < Variables.task_list.size(); i++) {

            String ServiceType = Variables.task_list.get(i).get(3).toString();

            int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString().toString());  // appName ex: app0

            double taskDelay = Double.parseDouble(Variables.task_list.get(i).get(8).toString());

            if (ServiceType.equalsIgnoreCase("critical")) {
                criticalCounter++;
                sumDelayOfCriticalTask = sumDelayOfCriticalTask + taskDelay;
            } else if (ServiceType.equalsIgnoreCase("other")) {
                // if (serviceIndex < noOfRealServices) // Real Apps
                // {
                //  realCounter++;
                //  sumDelayOfRealTask = sumDelayOfRealTask + taskDelay;
                //  Variables.task_list.get(i).set(3, "real");
                // } else // Normal Apps
                //  {
                normalCounter++;
                sumDelayOfnormalTask = sumDelayOfnormalTask + taskDelay;
                Variables.task_list.get(i).set(3, "normal");
                //  }
            }
        }
        double averageDelayOfCritical = 0, averageDelayOfReal = 0, averageDelayOfNormal = 0;
        if (criticalCounter > 0) {
            averageDelayOfCritical = sumDelayOfCriticalTask / (double) criticalCounter;
        }
//        if (realCounter > 0) {
//
//            averageDelayOfReal = sumDelayOfRealTask / (double) realCounter;
//        }
        if (normalCounter > 0) {
            averageDelayOfNormal = sumDelayOfnormalTask / (double) normalCounter;
        }
        averageDelayOfCritical = Double.parseDouble(formatter1.format(averageDelayOfCritical));
        // averageDelayOfReal = Double.parseDouble(formatter1.format(averageDelayOfReal));
        averageDelayOfNormal = Double.parseDouble(formatter1.format(averageDelayOfNormal));

        averageDelay.add(averageDelayOfCritical);
        averageDelay.add(averageDelayOfReal);
        averageDelay.add(averageDelayOfNormal);
        return averageDelay;
    }

    public double deadlineSatisfied() {
        /*
        task_list contains:
        index 0: cpu cycle of task
        index 1: size of task
        index 2: IoT device for send this task
        index 3: service type (critical or other or Ignore) . // jory aw servicay ka am taska dawaykrdwa
        index 4: service index // .  indexy aw serviceay ka am taska dawaykrdwa 
        index 5: location of installed service(colony1, colony2, server) // aw servicay ka am taska daway dakat la kwedaya la foga yan la coluda
        index 6: fog or service . // zhmaray fogaka yan serveraka .  wat aw servica lasar kam foga yan servera danrawa
        index 7: execution time
        index 8: task delay
        index 9: task energy
         */
        int satsifiedCount = 0;
        for (int i = 0; i < Variables.task_list.size(); i++) {
//            int taskSize = Variables.task_list.size();
//            int delayIndex = taskSize - 2;
            double deadline = 0;
            String serviceType = Variables.task_list.get(i).get(3).toString();
            int serviceIndex = Integer.parseInt(Variables.task_list.get(i).get(4).toString());
            double dealy = Double.parseDouble(Variables.task_list.get(i).get(8).toString());

            if (mehtodNumber == 1) {
                if (serviceType.equalsIgnoreCase("critical")) {
                    deadline = Double.parseDouble(Variables.criticalServicesDeadline.get(serviceIndex).toString());
                } else {
                    deadline = Double.parseDouble(Variables.realAndNormalServicesDeadline.get(serviceIndex).toString());
                }

            } else {
                deadline = Integer.parseInt(Variables.servicesDeadline.get(serviceIndex).toString());
            }
            if (dealy > deadline) {
                satsifiedCount++;
            }
        }
        int taskSize = Variables.task_list.size();
        double satsifiedPercentage = ((double) satsifiedCount / (double) taskSize) * 100;
        satsifiedPercentage = 100 - satsifiedPercentage;
// JOptionPane.showMessageDialog(null, Variables.servicesDeadline.size()+"\nhiwa: "+satsifiedCount+"\n"+
        //        taskSize+"\n"+
        //       satsifiedPercentage);
        return satsifiedPercentage;
    }

    public String servicePlacementResultForOurPaper() {
        int noOfServiceInC1 = 0, noOfServiceInC2 = 0, noOfServiceInCloud = 0;
        int size = 0;

        if (mehtodNumber == 5) {
            for (int i = 1; i < Variables.fog_list_c1.size(); i++) {
                size = Variables.fog_list_c1.get(i).size();
                if (size >= 7) {
                    int noOfServiceOnThisFog = Integer.parseInt(Variables.fog_list_c1.get(i).get(6).toString());
                    noOfServiceInC1 = noOfServiceInC1 + noOfServiceOnThisFog;
                }
            }
            for (int j = 1; j < Variables.fog_list_c2.size(); j++) {
                size = Variables.fog_list_c2.get(j).size();
                if (size >= 7) {

                    int noOfServiceOnThisFog = Integer.parseInt(Variables.fog_list_c2.get(j).get(6).toString());
                    noOfServiceInC2 = noOfServiceInC2 + noOfServiceOnThisFog;
                }
            }

            for (int k = 0; k < Variables.cloud_server_list.size(); k++) {
                size = Variables.cloud_server_list.get(k).size();
                if (size >= 8) {
                    int noOfServiceOnThisServer = Integer.parseInt(Variables.cloud_server_list.get(k).get(7).toString());
                    noOfServiceInCloud = noOfServiceInCloud + noOfServiceOnThisServer;
                }
            }

        } else {
            for (int i = 1; i < Variables.fog_list_c1.size(); i++) {
                size = Variables.fog_list_c1.get(i).size();
                if (size >= 6) {

                    int noOfServiceOnThisFog = Integer.parseInt(Variables.fog_list_c1.get(i).get(5).toString());
                    noOfServiceInC1 = noOfServiceInC1 + noOfServiceOnThisFog;
                }
            }
            for (int j = 1; j < Variables.fog_list_c2.size(); j++) {
                size = Variables.fog_list_c2.get(j).size();
                if (size >= 6) {
                    int noOfServiceOnThisFog = Integer.parseInt(Variables.fog_list_c2.get(j).get(5).toString());
                    noOfServiceInC2 = noOfServiceInC2 + noOfServiceOnThisFog;
                }
            }

            for (int k = 0; k < Variables.cloud_server_list.size(); k++) {
                size = Variables.cloud_server_list.get(k).size();
                if (size >= 7) {
                    int noOfServiceOnThisServer = Integer.parseInt(Variables.cloud_server_list.get(k).get(6).toString());
                    noOfServiceInCloud = noOfServiceInCloud + noOfServiceOnThisServer;
                }
            }

        }

        double noOfServiceInC1Percentage = ((double) noOfServiceInC1 / (double) Variables.NoOfService) * 100;
        double noOfServiceInC2Percentage = ((double) noOfServiceInC2 / (double) Variables.NoOfService) * 100;
        double noOfServiceInCloudPercentage = ((double) noOfServiceInCloud / (double) Variables.NoOfService) * 100;

        String result = noOfServiceInC1Percentage + "," + noOfServiceInC2Percentage + "," + noOfServiceInCloudPercentage;
        return result;

    }

}
