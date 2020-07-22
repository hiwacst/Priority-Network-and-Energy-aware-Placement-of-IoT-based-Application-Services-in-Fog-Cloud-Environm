/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQL_DB;

import Utilities.Variables;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class MySQLDB {

    static Connection con;
    static PreparedStatement preparedStmt;

    public MySQLDB() {
        connection();
    }

    public void connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PAMED_Algo", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Connection Error", 1);
        }
    }

    // public static void serviceUpdate(String methName, String serviceNumber, ArrayList resultList) {
    public static void serviceUpdate(String methName, String serviceNumber, ArrayList resultList, ArrayList activeDev, ArrayList typeOfService) {

        double pc = Double.parseDouble(resultList.get(0).toString());
        double fcpuu = Double.parseDouble(resultList.get(1).toString());
        double framu = Double.parseDouble(resultList.get(2).toString());
        double ccpuu = Double.parseDouble(resultList.get(3).toString());
        double cramu = Double.parseDouble(resultList.get(4).toString());
        // double td = Double.parseDouble(resultList.get(5).toString());
        // double tec = Double.parseDouble(resultList.get(6).toString());
        double frw = Double.parseDouble(resultList.get(7).toString());
        double crw = Double.parseDouble(resultList.get(8).toString());

        powerConsumptionUpdate(methName, serviceNumber, pc);
        fogCPUUtilization(methName, serviceNumber, fcpuu);
        fogRAMUtilization(methName, serviceNumber, framu);
        cloudCPUUtilization(methName, serviceNumber, ccpuu);
        cloudRAMUtilization(methName, serviceNumber, cramu);
        fogResourceWastage(methName, serviceNumber, frw);
        cloudResourceWastage(methName, serviceNumber, crw);
        activeDevices(methName, activeDev);
        if (methName.equalsIgnoreCase("Our Proposed")) {  // wata bas ba la algoy xomanda hsabaty jorayaty bkat la servicakanda 
            serviceType(typeOfService);
        }

    }

    // public static void taskUpdate(String methName, String taskNumber, ArrayList resultList) {
    public static void taskUpdate(String methName, String taskNumber, ArrayList resultList, ArrayList taskType, ArrayList averageDelayOfEachTaskType, double satsifiedDeadline, String noOfServiceInEachLocation, ArrayList<ArrayList> resultForAllRuningTime) {

        double maxTd = Double.parseDouble(resultList.get(5).toString());
        double tec = Double.parseDouble(resultList.get(6).toString());
        double minTd = Double.parseDouble(resultList.get(9).toString());
        double avgTd = Double.parseDouble(resultList.get(10).toString());
        double totalTd = Double.parseDouble(resultList.get(11).toString());

        maxTaskDelay(methName, taskNumber, maxTd);
        minTaskDelay(methName, taskNumber, minTd);
        avgTaskDelay(methName, taskNumber, avgTd);
        totalTaskDelay(methName, taskNumber, totalTd);
        taskEnergyConsumption(methName, taskNumber, tec);
        if (methName.equalsIgnoreCase("Our Proposed")) {  // wata bas ba la algoy xomanda hsabaty jorayaty bkat la servicakanda 
            tasksType(taskType);
            averageDelayOfEachTasksType(averageDelayOfEachTaskType);
        }
        satsifiedServicesDeadline(methName, taskNumber, satsifiedDeadline);
        servicePlacement(methName, taskNumber, noOfServiceInEachLocation);
        resultForAllRuningTimes(resultForAllRuningTime);
        // resultForAllRuningTimes
    }

    public static void powerConsumptionUpdate(String methName, String serviceNumber, double newData) {
        try {
            // if(methName.equalsIgnoreCase("Our Proposed"))
            // JOptionPane.showMessageDialog(null, newData);
            String query = "update PowerConsumption set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "powerConsumptionUpdate Error", 1);
        }
    }

    public static void fogCPUUtilization(String methName, String serviceNumber, double newData) {
        try {
            String query = "update Fog_CPU_Utilization set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "FogCPUUtilization Error", 1);
        }
    }

    public static void fogRAMUtilization(String methName, String serviceNumber, double newData) {
        try {
            String query = "update Fog_RAM_Utilization set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "FogRAMUtilization Error", 1);
        }
    }

    public static void cloudCPUUtilization(String methName, String serviceNumber, double newData) {
        try {
            String query = "update Cloud_CPU_Utilization set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "CloudCPUUtilization Error", 1);
        }
    }

    public static void cloudRAMUtilization(String methName, String serviceNumber, double newData) {
        try {
            String query = "update Cloud_RAM_Utilization set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "CloudRAMUtilization Error", 1);
        }
    }

    public static void fogResourceWastage(String methName, String serviceNumber, double newData) {
        try {
            String query = "update Fog_Resource_Wastage set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "fogResourceWastage Error", 1);
        }
    }

    public static void cloudResourceWastage(String methName, String serviceNumber, double newData) {
        try {
            String query = "update Cloud_Resource_Wastage set " + serviceNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "cloudResourceWastage Error", 1);
        }
    }

    public static void activeDevices(String methName, ArrayList activeDev) {

        String serviceLocation = "";
        int tableSuffix = Integer.parseInt(activeDev.get(3).toString());
        tableSuffix = tableSuffix / 10; // 100;  //100; //(tableSuffix/2) / 10;  // 100 wata kamtren zhmaray ap yan zhmaray services
        String tableName = "Active_Devices" + tableSuffix;

        try {
            for (int i = 0; i < activeDev.size() - 1; i++) {  // -1 bo away axr index ka bo zaneney nawy tablakay bakarnahenret lerada
                if (i == 0) {
                    serviceLocation = "colony1";
                } else if (i == 1) {
                    serviceLocation = "colony2";
                } else if (i == 2) {
                    serviceLocation = "cloud";
                }

                int newData = Integer.parseInt(activeDev.get(i).toString());
                String query = "update " + tableName + " set " + serviceLocation + " = " + newData + " where methodNumber = '" + methName + "'";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
            }
            // con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Active_Devices Error", 1);
        }
    }

    public static void serviceType(ArrayList typeOfService) {

        // JOptionPane.showMessageDialog(null, "serType: "+typeOfService);
        String type = "";
        int serviceNumber = Integer.parseInt(typeOfService.get(3).toString());
        serviceNumber = serviceNumber * 10;
        try {
            for (int i = 0; i < typeOfService.size() - 1; i++) {  // -1 bo away axr index ka bo zaneney nawy tablakay bakarnahenret lerada
                if (i == 0) {
                    type = "criticalService";
                } else if (i == 1) {
                    type = "realService";
                } else if (i == 2) {
                    type = "normalService";
                }

                int newData = Integer.parseInt(typeOfService.get(i).toString());
                String query = "update Service_Type set " + type + " = " + newData + " where numberOfService = " + serviceNumber + "";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();

            }
            //  con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Service_Type Error", 1);
        }
    }

 public static void maxTaskDelay(String methName, String taskNumber, double newData) {
        try {
            String query = "update Max_Task_Delay set " + taskNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "maxTaskDelay Error", 1);
        }
    }

    public static void minTaskDelay(String methName, String taskNumber, double newData) {
        try {
            String query = "update Min_Task_Delay set " + taskNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "minTaskDelay Error", 1);
        }
    }

    public static void avgTaskDelay(String methName, String taskNumber, double newData) {
        try {
            String query = "update AVG_Task_Delay set " + taskNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "avgTaskDelay Error", 1);
        }
    }

    public static void totalTaskDelay(String methName, String taskNumber, double newData) {
        try {
            String query = "update Total_Task_Delay set " + taskNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "totalTaskDelay Error", 1);
        }
    }    

    public static void taskEnergyConsumption(String methName, String taskNumber, double newData) {
        try {
            String query = "update Task_Energy_Consumption set " + taskNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "taskEnergyConsumption Error", 1);
        }
    }

    public static void tasksType(ArrayList typeOfTask) {

        String type = "";
        int taskNumber = Integer.parseInt(typeOfTask.get(3).toString());
        try {
            for (int i = 0; i < typeOfTask.size() - 1; i++) {  // -1 bo away axr index ka bo zaneney nawy tablakay bakarnahenret lerada
                if (i == 0) {
                    type = "criticalRequest";
                } else if (i == 1) {
                    type = "realRequest";
                } else if (i == 2) {
                    type = "normalRequest";
                }

                int newData = Integer.parseInt(typeOfTask.get(i).toString());
                String query = "update Task_Type set " + type + " = " + newData + " where numberOfTask = " + taskNumber + "";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();

            }
            // con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Task_Type Error", 1);
        }
    }

    public static void averageDelayOfEachTasksType(ArrayList averageDelayOfEachTaskType) {

        String type = "";
        int taskNumber = Integer.parseInt(averageDelayOfEachTaskType.get(3).toString()); // indexy 3 sajamy delay har se jorakay tyadaya
        try {
            for (int i = 0; i < averageDelayOfEachTaskType.size() - 1; i++) {  // -1 bo away axr index ka bo zaneney nawy tablakay bakarnahenret lerada
                if (i == 0) {
                    type = "criticalRequestAverageDelay";
                } else if (i == 1) {
                    type = "realRequestAverageDelay";
                } else if (i == 2) {
                    type = "normalRequestAverageDelay";
                }

                double newData = Double.parseDouble(averageDelayOfEachTaskType.get(i).toString());
                String query = "update Task_Average_Delay_Of_Each_Type set " + type + " = " + newData + " where numberOfTask = " + taskNumber + "";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
            }
            //con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Task_Average_Delay_Of_Each_Type Error", 1);
        }

    }

    public static void satsifiedServicesDeadline(String methName, String taskNumber, double newData) {
        try {

            // JOptionPane.showMessageDialog(null, newData);
            String query = "update DeadlineSatsified set " + taskNumber + " = " + newData + " where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "DeadlineSatsified Error", 1);
        }
        //JOptionPane.showMessageDialog(null, newData);

    }

    public static void servicePlacement(String methName, String taskNumber, String newData) {
        try {

            // JOptionPane.showMessageDialog(null, newData);
            String query = "update ServicePlacement set " + taskNumber + " = '" + newData + "' where methodNumber = '" + methName + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "DeadlineSatsified Error", 1);
        }
        //JOptionPane.showMessageDialog(null, newData);

    }

    public static void resultForAllRuningTimes(ArrayList<ArrayList> resultForAllRuningTime) {
        String methName = resultForAllRuningTime.get(0).get(0).toString();
        int fogPercent = Integer.parseInt(resultForAllRuningTime.get(0).get(1).toString());
        ArrayList allDelayForAllRuningTime = resultForAllRuningTime.get(1);
        int noOfServices = 0;
        if (fogPercent == 20) {
            noOfServices = 25;
        } else if (fogPercent == 40) {
            noOfServices = 50;
        } else if (fogPercent == 60) {
            noOfServices = 100;
        } else if (fogPercent == 80) {
            noOfServices = 200;
        } else if (fogPercent == 100) {
            noOfServices = 400;
        }
        String allDelay = "";
        for (int i = 0; i < allDelayForAllRuningTime.size(); i++) {
            if (allDelay == null) {
                allDelay = allDelay + "" + allDelayForAllRuningTime.get(i);
            } else {
                allDelay = allDelay + "," + allDelayForAllRuningTime.get(i);
            }
        }

        ArrayList allSatsifiedForAllRuningTime = resultForAllRuningTime.get(2);

        String allSatsified = "";
        for (int i = 0; i < allSatsifiedForAllRuningTime.size(); i++) {
            double satsified = Double.parseDouble(allSatsifiedForAllRuningTime.get(i).toString());

            if (allSatsified == null) {
                allSatsified = allSatsified + "" + (int) satsified;
            } else {
                allSatsified = allSatsified + "," + (int) satsified;
            }
        }

       // JOptionPane.showMessageDialog(null, methName + "\n" + noOfServices + "\n" + allDelayForAllRuningTime + "\n" + allSatsifiedForAllRuningTime);

        try {

            String query = "update All_Delay_And_Satsified_For_10_Time_Runing set delay  = '" + allDelay + "' , satsified = '" + allSatsified + "' where methodName = '" + methName + "' and numberOfService = " + noOfServices + "";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            //    con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "All_Delay_And_Satsified_For_10_Time_Runing Error", 1);
        }

    }
}
