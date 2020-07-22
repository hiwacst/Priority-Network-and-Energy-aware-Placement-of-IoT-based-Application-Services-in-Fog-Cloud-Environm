/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Run;

import Datasets.Read_Dataset;
import dijkstra.Edge;
import dijkstra.Graph;
import java.util.ArrayList;
import java.util.Scanner;
import pamed_implementation.Cloud_Topology;   // S_cr
import pamed_implementation.Fog_Topology;
import Utilities.Variables;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import pamed_implementation.MDRM_Algo;
import pamed_implementation.Method;
import pamed_implementation.ServicePlacement;
import pamed_implementation.TaskScheduling;
import MySQL_DB.MySQLDB;
import Utilities.Random_Generation;

/**
 *
 * @author hiwa_cst
 */
public class Main {

    static Read_Dataset read_Dataset;
    Cloud_Topology cloud_topo;
    Fog_Topology fog_topo;
    //  Method method;
    ServicePlacement servicePlacement;
    Variables var;
    TaskScheduling taskSchedule;
    DecimalFormat formatter = new DecimalFormat("#0.0000");
    DecimalFormat formatter1 = new DecimalFormat("#0.");
    static ArrayList<ArrayList> resultForAllRuningTime;
    static ArrayList satsifiedResultForEachRunningTime;
    static ArrayList brokerToCloudDelayForEachRuninTIme;
 // ama arrayanay xwarawa bo kokrdnway anjamy hamw runing timeakana
    static ArrayList totalResponseTime, maxResponseTime, minResponseTime, avgResponseTime;   // response time = delay
    //static ArrayList responseTimeForachRunTime;   // response time = delay    

    static int mehtodNumber = 0;
    static int countNoOfRunningTime = 0;
    static int numberOfRuningTime = 11;

    /**
     * @param args the command line arguments
     */
    public void result() {

        //  System.out.println("Enter 1 for Active Seed && 2 for Deactive Seed:");
        int seed = 1; //in.nextInt();
        read_Dataset = new Read_Dataset();
        read_Dataset.initialize(seed);
        var = new Variables();

        Scanner in = new Scanner(System.in);
        //  System.out.println("Enter 1 for Cloud and Fog Topology Together && 2 for Fog Topology Only:");
        System.out.println("1: Our Proposed Algorithm >>    2: Cloud Only >>    3: Edge Ward >>    4: Random Placement>>   5: Paper1 For Evaluation");
        // int topology = in.nextInt();
        mehtodNumber = Variables.policeNumber; //5; //in.nextInt();
        // System.out.println("Enter 1 for Static Topology && 2 for Daynamic Topology:");
        // int topology_typ = in.nextInt();
        // System.out.println("Enter 1 for IoT Devices && 2 for Ignore IoT Devices:");
        // int iot_devices = in.nextInt();
        //  System.out.println("Enter 1 for Active Seed && 2 for Deactive Seed:");
        // int seed = 1; //in.nextInt();

        if (mehtodNumber == 1) {           // Our Proposed Algo
//JOptionPane.showMessageDialog(null, "nmxnxmn");
            // cloud_topo = new Cloud_Topology(seed, topology_typ);
            // fog_topo = new Fog_Topology(seed, topology, topology_typ, iot_devices);
            cloud_topo = new Cloud_Topology(seed, 2);
//            fog_topo = new Fog_Topology(seed, mehtodNumber, 2, 1);
            fog_topo = new Fog_Topology(seed, mehtodNumber, 2, 1);

        } else if (mehtodNumber == 2) {   // Cloud Only 
            cloud_topo = new Cloud_Topology(seed, 2);
            fog_topo = new Fog_Topology(seed, mehtodNumber, 2, 1);
            //  fog_topo = new Fog_Topology(seed, topology, topology_typ, iot_devices);
        } else if (mehtodNumber == 3) {   // Edgeward
            cloud_topo = new Cloud_Topology(seed, 2);
            fog_topo = new Fog_Topology(seed, mehtodNumber, 2, 1);

        } else if (mehtodNumber == 4) {  // Random Placement
            cloud_topo = new Cloud_Topology(seed, 2);
            fog_topo = new Fog_Topology(seed, mehtodNumber, 2, 1);
        } else if (mehtodNumber == 5) {  // Paper1 For Evaluation
            cloud_topo = new Cloud_Topology(seed, 2);
            fog_topo = new Fog_Topology(seed, mehtodNumber, 2, 1);
        }
        countNoOfRunningTime++;
        Fog_Topology.seedForEachRuningTime = countNoOfRunningTime;

        servicePlacement = new ServicePlacement(seed, mehtodNumber);

        System.out.println("\n\nFog List In Colony1 Before Placement");
        System.out.println("CPU,Mem,MPower,IPower");
//        for (int i = 0; i < Variables.fog_list_backup_c1.size(); i++) {
//            System.out.println(i + " c1: " + Variables.fog_list_backup_c1.get(i));
//        }

        System.out.println("\n Fog List In Colony2 Before Placement");
        System.out.println("CPU,Mem,MPower,IPower");
//        for (int i = 0; i < Variables.fog_list_backup_c2.size(); i++) {
//            System.out.println(i + " c2: " + Variables.fog_list_backup_c2.get(i));
//        }

        System.out.println("\n\nFog List In Colony1 After Placement");
        System.out.println("CPU,Mem,MPower,IPower,Resource Wastage,NoOf Service On This Fog,Power Consumption,CPU Resource Utilization,Mem Resource Utilization");
//        for (int i = 0; i < Variables.fog_list_c1.size(); i++) {
//            System.out.println(i + " c1: " + Variables.fog_list_c1.get(i));
//        }

        System.out.println("\n Fog List In Colony2 After Placement");
        System.out.println("CPU,Mem,MPower,IPower,Resource Wastage,NoOf Service On This Fog,Power Consumption,CPU Resource Utilization,Mem Resource Utilization");
//        for (int i = 0; i < Variables.fog_list_c2.size(); i++) {
//            System.out.println(i + " c2: " + Variables.fog_list_c2.get(i));
//        }

        /*       
        index 0: service index
        index 1: fog index
        index 2: delay from broker to this fog node
        index 3 --- befor last = path from broker to this fog node
        index last:  colony name  (colony1 or colony2)
         */
        System.out.println("\n\n<<< SERVICE PLACEMENT IN FOG LAYER >>>");
        if (mehtodNumber == 4) {
            System.out.println("\n Service Index,Fog Index,Service Installed Location,Service Type(Ignore),Service Power Consumption");
        } else {
            System.out.println("\n Service Index,Fog Index,Energy,Path,Colony Name,Placement Factor(Delay or Energy),Service Type(critical, other)");
        }

//        for (int i = 0; i < Variables.X_sf.size(); i++) {
//            System.out.println(i + ": " + Variables.X_sf.get(i));
//        }
        System.out.println("\n Cloud Server Before Placement");
        System.out.println("CPU,RAM,MPower,IPower");
//        for (int i = 0; i < Variables.cloud_server_list1.size(); i++) {
//            System.out.println(i + ": " + Variables.cloud_server_list1.get(i));
//        }
        /*
        cloud_server_list contains
        index 0: CPU
        index 1: RAM
        index 2: Max Power
        index 3: Idle Power
         */
        System.out.println("\n Cloud Server After Placement");
        System.out.println("CPU,RAM,MPower,IPower,Power ConSumption,Resource Wastage,NoOf Service On This Server,CPU Resource Utilization,Mem Resource Utilization");
//        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
//            System.out.println(i + ": " + Variables.cloud_server_list.get(i));
//        }

        /*
        X_sc  contains
        index 0: service index 
        index 1: server index
        index 2: CPU
        index 3: RAM
        index 4: Max Power
        index 5: Idle Power 
        index 6: AVG of CPU and RAM
        index 7: server type AD,BD,UD
         */
        System.out.println("\n\n<<< SERVICE PLACEMENT IN CLOUD LAYER >>>");
        if (mehtodNumber == 4) {
            System.out.println("\nService Index,Server Index,Service Installed Location,Service Type(critical , other or Ignore),Service Power Consumption");
        } else {
            System.out.println("\nService Index,Server Index,Server Details(CPU,RAM,Mpower,Ipower),AVG of CPU and RAM,Service Type(critical or other),Server Type(AD,BD,UD)");
        }
//        for (int i = 0; i < Variables.X_sc.size(); i++) {
//            System.out.println(i + ": " + Variables.X_sc.get(i));
//        }
        System.out.println("\n<<< WAITED SERVICE >>>");
//        for (int i = 0; i < Variables.waitedServices.size(); i++) {
//            System.out.println(Variables.waitedServices.get(i));
//        }

        System.out.println("\n\n<<< TASK SCHEDULING >>>");
        taskSchedule = new TaskScheduling(seed, mehtodNumber);

        if (Variables.checkIoTNumber == false) {
            System.out.println("CPU Cycle,Task Size,IoT Device Index,Service Type(Critical or Other),Service Index,Service Installed Location,Installed Node,Execution Time,Task Delay,Energy Consumption");
//            for (int i = 0; i < Variables.task_list.size(); i++) {
//                System.out.println(i + ") " + Variables.task_list.get(i));
//            }
        } else {   // wata agar zhmaray task la iot node zyatrbw awa ba anjamy task pishan nayat
            JOptionPane.showMessageDialog(null, "Sorry !! must be number of task is less or equal the number of IoT devices in colony1");
        }
    }

    public void finalResultValues() {

        ArrayList finalValue = new ArrayList();
        double powerConsumptionColony1 = 0, powerConsumptionColony2 = 0, powerConsumptionCloud = 0;
        double resourceWastageColony1 = 0, resourceWastageColony2 = 0, resourceWastageCloud = 0;
        double resourceUtilizationCPUColony1 = 0, resourceUtilizationCPUColony2 = 0, resourceUtilizationCPUCloud = 0;
        double resourceUtilizationMemColony1 = 0, resourceUtilizationMemColony2 = 0, resourceUtilizationMemCloud = 0;
        double taskEnergyConsumption = 0, totalTaskDelay = 0, maxTaskDelay = 0, minTaskDelay = 0, avgTaskDelay = 0;

        int fogCountForUtilization = 0, cloudCountForUtilization = 0;
        int colony1 = 0, colony2 = 0, cloud = 0;
        double power = 0, resourceWastage = 0, resourceUtilizationCPU = 0, resourceUtilizationMem = 0;

        for (int i = 1; i < Variables.fog_list_c1.size(); i++) {
            int listSize = Variables.fog_list_c1.get(i).size();
            //   JOptionPane.showMessageDialog(null, "1111: " + Variables.fog_list_c1.get(i));

            if (listSize >= 9) {
                // POWER CONSUMPTION
                if (mehtodNumber == 5) {
                    power = Double.parseDouble(Variables.fog_list_c1.get(i).get(7).toString());
                } else {
                    power = Double.parseDouble(Variables.fog_list_c1.get(i).get(6).toString());
                }
                powerConsumptionColony1 = Double.sum(powerConsumptionColony1, power);

                // RESOURCE WASTAGE
                if (mehtodNumber == 5) {
                    resourceWastage = Double.parseDouble(Variables.fog_list_c1.get(i).get(5).toString());
                } else {
                    resourceWastage = Double.parseDouble(Variables.fog_list_c1.get(i).get(4).toString());
                }
                resourceWastageColony1 = Double.sum(resourceWastageColony1, resourceWastage);

                // RESOURCE UTILIZATION CPU
                if (mehtodNumber == 5) {
                    resourceUtilizationCPU = Double.parseDouble(Variables.fog_list_c1.get(i).get(8).toString());
                } else {
                    resourceUtilizationCPU = Double.parseDouble(Variables.fog_list_c1.get(i).get(7).toString());
                }
                resourceUtilizationCPUColony1 = Double.sum(resourceUtilizationCPUColony1, resourceUtilizationCPU);

                if (mehtodNumber == 5) {
                    resourceUtilizationMem = Double.parseDouble(Variables.fog_list_c1.get(i).get(9).toString());
                } else {
                    resourceUtilizationMem = Double.parseDouble(Variables.fog_list_c1.get(i).get(8).toString());
                }
                resourceUtilizationMemColony1 = Double.sum(resourceUtilizationMemColony1, resourceUtilizationMem);
                fogCountForUtilization++;
                colony1++;
            }

        }

        for (int i = 1; i < Variables.fog_list_c2.size(); i++) {
            int listSize = Variables.fog_list_c2.get(i).size();
            if (listSize >= 9) {  // == bo hamw halatakanytr w > tanha boa 5
                // POWER CONSUMPTION
                if (mehtodNumber == 5) {
                    power = Double.parseDouble(Variables.fog_list_c2.get(i).get(7).toString());
                } else {
                    power = Double.parseDouble(Variables.fog_list_c2.get(i).get(6).toString());
                }
                powerConsumptionColony2 = Double.sum(powerConsumptionColony2, power);

                // RESOURCE WASTAGE
                if (mehtodNumber == 5) {
                    resourceWastage = Double.parseDouble(Variables.fog_list_c2.get(i).get(5).toString());
                } else {
                    resourceWastage = Double.parseDouble(Variables.fog_list_c2.get(i).get(4).toString());
                }
                resourceWastageColony2 = Double.sum(resourceWastageColony2, resourceWastage);

                // RESOURCE UTILIZATION CPU
                if (mehtodNumber == 5) {
                    resourceUtilizationCPU = Double.parseDouble(Variables.fog_list_c2.get(i).get(8).toString());
                } else {
                    resourceUtilizationCPU = Double.parseDouble(Variables.fog_list_c2.get(i).get(7).toString());
                }
                resourceUtilizationCPUColony2 = Double.sum(resourceUtilizationCPUColony2, resourceUtilizationCPU);

                if (mehtodNumber == 5) {
                    resourceUtilizationMem = Double.parseDouble(Variables.fog_list_c2.get(i).get(9).toString());
                } else {
                    resourceUtilizationMem = Double.parseDouble(Variables.fog_list_c2.get(i).get(8).toString());
                }
                resourceUtilizationMemColony2 = Double.sum(resourceUtilizationMemColony2, resourceUtilizationMem);
                fogCountForUtilization++;
                colony2++;
            }
        }

        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
            int listSize = Variables.cloud_server_list.get(i).size();
            if (listSize >= 9) {  // lam marjada = bo hamw halatakanytr w > tanha boa 5
                // POWER CONSUMPTION
                if (mehtodNumber == 5) {
                    power = Double.parseDouble(Variables.cloud_server_list.get(i).get(5).toString());
                } else {
                    power = Double.parseDouble(Variables.cloud_server_list.get(i).get(4).toString());
                }
                powerConsumptionCloud = Double.sum(powerConsumptionCloud, power);

                // RESOURCE WASTAGE
                if (mehtodNumber == 5) {
                    resourceWastage = Double.parseDouble(Variables.cloud_server_list.get(i).get(6).toString());
                } else {
                    resourceWastage = Double.parseDouble(Variables.cloud_server_list.get(i).get(5).toString());
                }
                resourceWastageCloud = Double.sum(resourceWastageCloud, resourceWastage);

                // RESOURCE UTILIZATION CPU
                if (mehtodNumber == 5) {
                    resourceUtilizationCPU = Double.parseDouble(Variables.cloud_server_list.get(i).get(8).toString());
                } else {
                    resourceUtilizationCPU = Double.parseDouble(Variables.cloud_server_list.get(i).get(7).toString());
                }
                resourceUtilizationCPUCloud = Double.sum(resourceUtilizationCPUCloud, resourceUtilizationCPU);

                if (mehtodNumber == 5) {
                    resourceUtilizationMem = Double.parseDouble(Variables.cloud_server_list.get(i).get(9).toString());
                } else {
                    resourceUtilizationMem = Double.parseDouble(Variables.cloud_server_list.get(i).get(8).toString());
                }
                resourceUtilizationMemCloud = Double.sum(resourceUtilizationMemCloud, resourceUtilizationMem);
                cloudCountForUtilization++;
                cloud++;
            }
        }


        /*
        dozinaway gawratrin (maxDelay) delal (aw appy ka zortrinkaty pechwa)
         */
        maxTaskDelay = Double.parseDouble(Variables.task_list.get(0).get(8).toString());
        for (int i = 0; i < Variables.task_list.size(); i++) {
            // TASK Delay
            double taskDelay1 = Double.parseDouble(Variables.task_list.get(i).get(8).toString());
           // System.out.println("HIWA:  " + taskDelay1);
            if (taskDelay1 > maxTaskDelay) {
                maxTaskDelay = taskDelay1;
            }
            // TASK ENERGY
            double taskEnergy = Double.parseDouble(Variables.task_list.get(i).get(9).toString());
            taskEnergyConsumption = Double.sum(taskEnergyConsumption, taskEnergy);
        }

        /*
        dozinaway b4wktren (minDelay) delal (aw appy ka zortrinkaty pechwa)
         */
        minTaskDelay = Double.parseDouble(Variables.task_list.get(0).get(8).toString());
        for (int i = 0; i < Variables.task_list.size(); i++) {
            double taskDelay1 = Double.parseDouble(Variables.task_list.get(i).get(8).toString());
            if (taskDelay1 < minTaskDelay) {
                minTaskDelay = taskDelay1;
            }
        }

        /*
        dozinaway sarjam (totalDelay) delal (hamw appakan)
         */
        totalTaskDelay = 0.0;  // Double.parseDouble(Variables.task_list.get(0).get(8).toString());
        for (int i = 0; i < Variables.task_list.size(); i++) {
            double taskDelay1 = Double.parseDouble(Variables.task_list.get(i).get(8).toString());
            totalTaskDelay += taskDelay1;
        }

        /*
        dozinaway nawand (avgDelay) delal (hamw appakan)
         */
        avgTaskDelay = totalTaskDelay / Variables.task_list.size();

        // taskDelay = maxDelay;
        // JOptionPane.showMessageDialog(null, "Max Delay: "+taskDelay);
        double allPowerConsumption = Double.sum(powerConsumptionColony1, powerConsumptionColony2);
        allPowerConsumption = Double.sum(allPowerConsumption, powerConsumptionCloud);
        allPowerConsumption = Double.parseDouble(formatter.format(allPowerConsumption));
        finalValue.add(allPowerConsumption);  // index:0

        /// FOG CPU AND RAM UTILIZATION
        double fogCPUUtilization = Double.sum(resourceUtilizationCPUColony1, resourceUtilizationCPUColony2);
        //  JOptionPane.showMessageDialog(null, "fogCPUUtilization:  "+fogCPUUtilization);

        if (fogCountForUtilization != 0) {
            fogCPUUtilization = fogCPUUtilization / (double) fogCountForUtilization;
        }
        fogCPUUtilization = Double.parseDouble(formatter1.format(fogCPUUtilization * 100));
        finalValue.add(fogCPUUtilization);  // index:1

        //  resourceUtilizationMemColony1 = resourceUtilizationMemColony1 / (double)colony1;
        //  resourceUtilizationMemColony2 = resourceUtilizationMemColony2 / (double)colony2;
        double fogMemUtilization = Double.sum(resourceUtilizationMemColony1, resourceUtilizationMemColony2);

        if (fogCountForUtilization != 0) {
            fogMemUtilization = fogMemUtilization / (double) fogCountForUtilization;
        }
        fogMemUtilization = Double.parseDouble(formatter1.format(fogMemUtilization * 100));
        finalValue.add(fogMemUtilization);  // index:2
        /// CLOUD CPU AND RAM UTILIZATION
        if (cloudCountForUtilization != 0) {
            resourceUtilizationCPUCloud = resourceUtilizationCPUCloud / cloudCountForUtilization;
            resourceUtilizationMemCloud = resourceUtilizationMemCloud / cloudCountForUtilization;

        }
        resourceUtilizationCPUCloud = Double.parseDouble(formatter1.format(resourceUtilizationCPUCloud * 100));
        finalValue.add(resourceUtilizationCPUCloud); // index:3

        resourceUtilizationMemCloud = Double.parseDouble(formatter1.format(resourceUtilizationMemCloud * 100));
        finalValue.add(resourceUtilizationMemCloud); // index:4

        // . TASK DELAY
        totalTaskDelay = Double.parseDouble(formatter.format(totalTaskDelay));
        maxTaskDelay = Double.parseDouble(formatter.format(maxTaskDelay));
        minTaskDelay = Double.parseDouble(formatter.format(minTaskDelay));
        avgTaskDelay = Double.parseDouble(formatter.format(avgTaskDelay));
        finalValue.add(maxTaskDelay);  // index:5
          if(minTaskDelay > 0){
        totalResponseTime.add(totalTaskDelay);
        maxResponseTime.add(maxTaskDelay);
        minResponseTime.add(minTaskDelay);
        avgResponseTime.add(avgTaskDelay);
        //JOptionPane.showMessageDialog(null, totalTaskDelay + "\n" + maxTaskDelay + "\n" + minTaskDelay + "\n" + avgTaskDelay);
         }
        // finalValue.add("delay");
        // . TASK ENERGY
        taskEnergyConsumption = Double.parseDouble(formatter.format(taskEnergyConsumption));
        finalValue.add(taskEnergyConsumption);  // index:6
        // finalValue.add("energy");

        // FOG RSOURCE WASTAGE 
        double fogResourceWastage = Double.sum(resourceWastageColony1, resourceWastageColony2);
        fogResourceWastage = Double.parseDouble(formatter.format(fogResourceWastage));
        finalValue.add(fogResourceWastage);  // index:7

        // CLOUD RESOURCE WASTAGE
        resourceWastageCloud = Double.parseDouble(formatter.format(resourceWastageCloud));
        finalValue.add(resourceWastageCloud);  // index:8

        System.out.println("\n");
        System.out.println("Power Consumption = PC\n"
                + "Fog CPU Utilization = F(CPU)U\n"
                + "Fog RAM Utilization = F(RAM)U\n"
                + "CLoud CPU Utilization = C(CPU)U\n"
                + "Cloud RAM Utilization = C(RAM)U\n"
                + "Task Delay = TD\n"
                + "Task Energy Consumption = TEC\n"
                + "Fog Resource Wastage = FRW\n"
                + "Cloud Resource wastage = CRW");

        System.out.println("\n\n");
        System.out.println("PC , F(CPU)U , F(RAM)U , C(CPU)U , C(RAM)U , TD , TEC , FRW , CRW");
        System.out.println(finalValue);

        String methodName = "";
        if (mehtodNumber == 1) {
            methodName = "Our Proposed";
        } else if (mehtodNumber == 2) {
            methodName = "Only CLoud";
        } else if (mehtodNumber == 3) {
            methodName = "Edgeward";
        } else if (mehtodNumber == 4) {
            methodName = "Random Placement";
        } else if (mehtodNumber == 5) {
            methodName = "Paper1 For Evaluation";
        }

        ArrayList activeDevices = new ArrayList();
        activeDevices.add(colony1);
        activeDevices.add(colony2);
        activeDevices.add(cloud);

        ArrayList appType = new ArrayList();
        ArrayList taskType = new ArrayList();
        double satsifiedServiceDeadline = 0.0;
        String noOfServiceInEachLocation = ""; // wata bo c1,c2,cloud
        ArrayList averageDelayOfEachTaskType = new ArrayList();
        if (mehtodNumber == 1) {
            int criticalAppSize = Variables.service_list.size() / 3;
            int realAppSize = criticalAppSize; //* 2;
            int normalAppSize = Variables.service_list.size() - (realAppSize + criticalAppSize);

            appType.add(criticalAppSize);
            appType.add(realAppSize);
            appType.add(normalAppSize);

            taskType = taskSchedule.taskType();
            averageDelayOfEachTaskType = taskSchedule.averageDelayforEveryTaskType();
        }
        satsifiedServiceDeadline = taskSchedule.deadlineSatisfied();
        noOfServiceInEachLocation = taskSchedule.servicePlacementResultForOurPaper();
        satsifiedResultForEachRunningTime.add(satsifiedServiceDeadline);
        brokerToCloudDelayForEachRuninTIme.add(Variables.brokerc1_to_cloud_delay);
        // JOptionPane.showMessageDialog(null, noOfServiceInEachLocation);

        if (countNoOfRunningTime == numberOfRuningTime - 1) {

            // JOptionPane.showMessageDialog(null, brokerToCloudDelayForEachRuninTIme + "\n" + satsifiedResultForEachRunningTime);
            double sum = 0;
            for (int i = 0; i < satsifiedResultForEachRunningTime.size(); i++) {
                double currentValue = Double.parseDouble(satsifiedResultForEachRunningTime.get(i).toString());
                sum = sum + currentValue;
            }
            double sum1 = sum / (double) satsifiedResultForEachRunningTime.size();
            satsifiedServiceDeadline = sum1;

                //find MAX (response time) 
            double avgBetweenMaxOfAllRuningTime = 0.0;
            for (int i = 0; i < maxResponseTime.size(); i++) {
                double responseTime = Double.parseDouble(maxResponseTime.get(i).toString());
                avgBetweenMaxOfAllRuningTime += responseTime;
            }
            avgBetweenMaxOfAllRuningTime = avgBetweenMaxOfAllRuningTime / maxResponseTime.size();
            // JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + maxResponseTime);
            avgBetweenMaxOfAllRuningTime = Double.parseDouble(formatter.format(avgBetweenMaxOfAllRuningTime));
            finalValue.set(5, avgBetweenMaxOfAllRuningTime);
            //JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + maxResponseTime);

            //find MIN (response time)            
            double avgBetweenMinOfAllRuningTime = 0.0;
            for (int i = 0; i < minResponseTime.size(); i++) {
                double responseTime = Double.parseDouble(minResponseTime.get(i).toString());
                avgBetweenMinOfAllRuningTime += responseTime;
            }
            avgBetweenMinOfAllRuningTime = avgBetweenMinOfAllRuningTime / minResponseTime.size();
            //JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + minResponseTime);
            avgBetweenMinOfAllRuningTime = Double.parseDouble(formatter.format(avgBetweenMinOfAllRuningTime));
            finalValue.add(avgBetweenMinOfAllRuningTime);   // index:9
            //JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + minResponseTime);

            //find AVG (response time)                        
            double avgBetweenAvgOfAllRuningTime = 0;
            for (int i = 0; i < avgResponseTime.size(); i++) {
                double responseTime = Double.parseDouble(avgResponseTime.get(i).toString());
                avgBetweenAvgOfAllRuningTime += responseTime;
            }
            avgBetweenAvgOfAllRuningTime = avgBetweenAvgOfAllRuningTime / avgResponseTime.size();
            // JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + avgResponseTime);
            avgBetweenAvgOfAllRuningTime = Double.parseDouble(formatter.format(avgBetweenAvgOfAllRuningTime));
            finalValue.add(avgBetweenAvgOfAllRuningTime); // index:10
            // JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + avgResponseTime);

            //find TOTAL (total time)                        
            double totalForAllRuningTime = 0;
            for (int i = 0; i < totalResponseTime.size(); i++) {
                double responseTime = Double.parseDouble(totalResponseTime.get(i).toString());
                totalForAllRuningTime += responseTime;
            }
            // JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + avgResponseTime);
            totalForAllRuningTime = Double.parseDouble(formatter.format(totalForAllRuningTime));
            finalValue.add(totalForAllRuningTime); // index:11
            // JOptionPane.showMessageDialog(null, finalValue + "\n" + responseTimeForachRunTime + "\n" + avgResponseTime);

            /*
             Saving all result for all runing time
             */
            ArrayList l = new ArrayList();
            l.add(methodName);
            l.add(Variables.numberOfCriticalOverAllServices);
            resultForAllRuningTime.add(l);
            resultForAllRuningTime.add(brokerToCloudDelayForEachRuninTIme);
            resultForAllRuningTime.add(satsifiedResultForEachRunningTime);

            if (Variables.servicePlacementCheck == true && Variables.taskPlacementCheck == false) {
                activeDevices.add(Variables.NoOfService);
                if (mehtodNumber == 1) {
                    appType.add(Variables.NoOfService);
                }

                int serviceNumber = Variables.NoOfService / 10; // / 10; //- 50; // + Variables.NoOfService;
                MySQLDB.serviceUpdate(methodName, "service" + serviceNumber, finalValue, activeDevices, appType);
            }
            if (Variables.servicePlacementCheck == false && Variables.taskPlacementCheck == true) {

                if (mehtodNumber == 1) {
                    taskType.add(Variables.NoOfTask);
                    averageDelayOfEachTaskType.add(Variables.NoOfTask);
                }

                int taskNumber = 0; //= Variables.NoOfTask / 10;  // 10; // - 50; //+ Variables.NoOfTask;
                if (Variables.numberOfCriticalOverAllServices == 0) {
                    taskNumber = 10;
                } else if (Variables.numberOfCriticalOverAllServices == 40) {
                    taskNumber = 20;
                } else if (Variables.numberOfCriticalOverAllServices == 60) {
                    taskNumber = 30;
                } else if (Variables.numberOfCriticalOverAllServices == 80) {
                    taskNumber = 40;
                } else if (Variables.numberOfCriticalOverAllServices == 100) {
                    taskNumber = 50;
                }
                MySQLDB.taskUpdate(methodName, "task" + taskNumber, finalValue, taskType, averageDelayOfEachTaskType, satsifiedServiceDeadline, noOfServiceInEachLocation, resultForAllRuningTime);

            }
        }
        /*
        
        
        
        
         */

        //// dwbar printkrdnaway task kate otherm goriwa bo real w normal
        System.out.println("\n\nCPU Cycle,Task Size,IoT Device Index,Service Type(Critical or Other),Service Index,Service Installed Location,Installed Node,Execution Time,Task Delay,Energy Consumption");
        for (int i = 0; i < Variables.task_list.size(); i++) {
            System.out.println(Variables.task_list.get(i));
        }

    }

    public static void main(String[] args) {
        Main ob = new Main();
        MySQLDB mysql = new MySQLDB();
        Scanner in = new Scanner(System.in);
        resultForAllRuningTime = new ArrayList<ArrayList>();
        satsifiedResultForEachRunningTime = new ArrayList();
        brokerToCloudDelayForEachRuninTIme = new ArrayList();
        // responseTimeForachRunTime = new ArrayList();
        totalResponseTime = new ArrayList();
        maxResponseTime = new ArrayList();
        minResponseTime = new ArrayList();
        avgResponseTime = new ArrayList();
        /*
        
        
         */
        Scanner in1 = new Scanner(System.in);
        System.out.println("1: Our Proposed Algorithm >>    2: Cloud Only >>    3: Edge Ward >>    4: Random Placement>>   5: Paper1 For Evaluation");       
        Variables.policeNumber = in1.nextInt();
        
        System.out.println("Enter |number of Critical Services| Over |All Services| for Scenario #5  (0%, 20%, 40%, 60%, 80% or 100%) Fog Nodes");
        Variables.numberOfCriticalOverAllServices = in1.nextInt();

        /*
        
        
         */
        while (countNoOfRunningTime < numberOfRuningTime) {
            System.out.println("1: Service Placement         2: Task Placement");
            int value = 2; //in.nextInt();
            if (value == 1) {
                Variables.servicePlacementCheck = true;
                Variables.taskPlacementCheck = false;
            } else if (value == 2) {
                Variables.servicePlacementCheck = false;
                Variables.taskPlacementCheck = true;
            }

            Variables.iotAndTask = 0;
            ob.result();
            ob.finalResultValues();
            // countNoOfRunningTime++;
        }

    }

}
