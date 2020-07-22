/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class Variables {

    public static int seed = -1;

    // public static int graph_matrix_colony1[][];
    // public static int graph_matrix_colony2[][];
    public static ArrayList<ArrayList> fog_list_c1, fog_list_backup_c1, iot_list_c1, fog_list_c2, fog_list_backup_c2, iot_list_c2, cloud_server_list, cloud_server_list1,
            service_list, service_list1, S_cri, S_real_time1, S_normal, S_all1, edges_list_c1, edges_list_backup_c1, edges_list_c2, edges_list_backup_c2, X_sf, X_sc, task_list, task_list_backup, temp,
            waitedServices;
    public static int graph_matrix_c[][], graph_matrix_c1[][], graph_matrix_c2[][];
    // public static int nodeNumbersC1=0,nodeNumbersC2=0;
    public static ArrayList access_point_list_c1, access_point_list_c2, printMessage, noOfIoTDeviceInEachColony, servicesDeadline, criticalServicesDeadline, realAndNormalServicesDeadline;

    public static int brokerc1_to_cloud_delay;
    public static int brokerc2_to_cloud_delay;
    public static int brokerc1_to_brokerc2_delay;
    public static int multiplyServiceCapacityInCloud = 1; //3; //5; //5; //10; //10;
    public static boolean checkIoTNumber = false;
    public static int NoOfService, NoOfTask, iotAndTask = 0, nofiotAndTasks;
    public static boolean servicePlacementCheck = false;
    public static boolean taskPlacementCheck = false;
    public static int percentOfFogNodesInBothColony = 0;
    public static double numberOfNodesC2 = 0.0;
    public static int policeNumber = 0;
    // public static boolean checkPlacmentType =false;

    public static int percentage_Number_Of_Critical_Services, number_Of_Critical_Services;

    //  public Integer[][] X_sf; // x_sf
    //  public Integer[][] X_sc; // x_sc
    public Variables() {

        //  int numServices = service_list.size();
        // int numFogNodes = fog_list_c1.size() + fog_list_c2.size();
        // int numCloudServers = cloud_server_list.size();
        // X_sf = new Integer[numServices][numFogNodes];
        // X_sc = new Integer[numServices][numCloudServers];
//       Random rand ;
//       if(seed == 1)
//       {
//         rand = new Random();
//         brokerc1_to_brokerc2_delay
//       }
//       else
//       {
//         rand = new Random();
//       }
        // nodeNumbersC1 = Variables.fog_list_c1.size() + Variables.access_point_list_c1.size()
        //       + Variables.iot_list_c1.size();
        // nodeNumbersC2 = Variables.fog_list_c2.size() + Variables.access_point_list_c2.size()
        //        + Variables.iot_list_c2.size();
        X_sf = new ArrayList<ArrayList>();
        X_sc = new ArrayList<ArrayList>();

        service_list = new ArrayList<ArrayList>();
        S_cri = new ArrayList<ArrayList>();
        // S_real_time = new ArrayList<ArrayList>();
        S_normal = new ArrayList<ArrayList>();
        // S_all = new ArrayList<ArrayList>();

        fog_list_c1 = new ArrayList<ArrayList>();
        fog_list_backup_c1 = new ArrayList<ArrayList>();
        fog_list_c2 = new ArrayList<ArrayList>();
        fog_list_backup_c2 = new ArrayList<ArrayList>();

        access_point_list_c1 = new ArrayList<ArrayList>();
        access_point_list_c2 = new ArrayList<ArrayList>();

        iot_list_c1 = new ArrayList<ArrayList>();
        iot_list_c2 = new ArrayList<ArrayList>();

        cloud_server_list = new ArrayList<ArrayList>();
        cloud_server_list1 = new ArrayList<ArrayList>();

        task_list = new ArrayList<ArrayList>();
        task_list_backup = new ArrayList<ArrayList>();

        // edges_list_c1 = new ArrayList<ArrayList>();
        // edges_list_c2 = new ArrayList<ArrayList>();
        //  graph_matrix_c1 = new int[nodeNumbersC1][nodeNumbersC1];
        // graph_matrix_c2 = new int[nodeNumbersC2][nodeNumbersC2];
        printMessage = new ArrayList();
        waitedServices = new ArrayList<ArrayList>();
        noOfIoTDeviceInEachColony = new ArrayList();
        temp = new ArrayList<ArrayList>();

        servicesDeadline = new ArrayList();
        criticalServicesDeadline = new ArrayList();
        realAndNormalServicesDeadline = new ArrayList();
    }

}
