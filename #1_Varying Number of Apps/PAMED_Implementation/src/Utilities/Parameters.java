/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author hiwa_cst
 */
public class Parameters {

    public static int fog_to_fog_delay_min = 2;  // ms
    public static int fog_to_fog_delay_max = 9;

    public static int brokerc1_to_brokerc2_delay_min = 20;  // ms
    public static int brokerc1_to_brokerc2_delay_max = 100;   // ms

    public static int broker_to_cloud_delay_min = 200;   // ms
    public static int broker_to_cloud_delay_max = 1500; //750; //1000;  // ms

    public static int cpu_cycle_min = 10;   // MI
    public static int cpu_cycle_max = 1000;

    public static double cpu_frequency_min = 2.9;   // GH
    public static double cpu_frequency_max = 4.2;

    public static double power_coeifecient_min = 0.3;  //Watts
    public static double power_coeifecient_max = 0.9;

    public static int critical_Service_Deadline_Min = 100;  // ms
    public static int critical_Service_Deadline_Max = 500;   // ms

    public static int normal_Service_Deadline_Min = 750;  // ms
    public static int normal_Service_Deadline_Max = 1500;   // ms
    public void initialize() {

    }

}
