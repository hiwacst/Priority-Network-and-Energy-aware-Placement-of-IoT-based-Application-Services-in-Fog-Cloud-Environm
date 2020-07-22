/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import Utilities.Variables;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class MDRM_Algo {

    ArrayList<ArrayList> ADServers, ADServersAVG, ADServersSort,
            BDServers, BDServersAVG, BDServersSort,
            UDServers, UDServersAVG, UDServersSort;

    String acceptance_domain[] = {
        "1.0,0.6", "0.9,0.7", "1.0,0.7", "0.8,0.8", "0.9,0.8",
        "1.0,0.8", "0.7,0.9", "0.8,0.9", "0.9,0.9", "1.0,0.9",
        "0.6,1.0", "0.7,1.0", "0.8,1.0", "0.9,1.0", "1.0,1.0"};
    String unacceptance_domain[] = {
        "0.1,0.6", "0.1,0.7", "0.2,0.7", "0.1,0.8", "0.2,0.8",
        "0.3,0.8", "0.1,0.9", "0.2,0.9", "0.3,0.9", "0.4,0.9",
        "0.1,1.0", "0.2,1.0", "0.3,1.0", "0.4,1.0", "0.5,1.0",
        "0.6,0.1", "0.7,0.1", "0.8,0.1", "0.9,0.1", "1.0,0.1",
        "0.7,0.2", "0.8,0.2", "0.9,0.2", "1.0,0.2", "0.8,0.3",
        "0.9,0.3", "1.0,0.3", "0.9,0.4", "1.0,0.4", "1.0,0.5"};

      //  DecimalFormat formatter = new DecimalFormat("#0.000000");

    public MDRM_Algo(String serviceType, int serviceIndex, ArrayList<ArrayList> servicList, ArrayList<ArrayList> cloudServerList) {
        classifyCloudServers(serviceIndex, servicList, cloudServerList);
        sorting();
        servicePalcements(serviceType, serviceIndex, servicList);
    }

    public void classifyCloudServers(int serviceIndex, ArrayList<ArrayList> servicList, ArrayList<ArrayList> cloudServerList) {

        ADServers = new ArrayList<ArrayList>();
        BDServers = new ArrayList<ArrayList>();
        UDServers = new ArrayList<ArrayList>();

        int serviceCPU = Integer.parseInt(servicList.get(serviceIndex).get(0).toString());
        int serviceMem = Integer.parseInt(servicList.get(serviceIndex).get(1).toString());

        for (int i = 0; i < cloudServerList.size(); i++) {
            int cloudCPU = Integer.parseInt(cloudServerList.get(i).get(0).toString());
            int cloudMem = Integer.parseInt(cloudServerList.get(i).get(1).toString());

            if (cloudCPU >= serviceCPU && cloudMem >= serviceMem) {
                double cpu_check = ((serviceCPU * 10) / cloudCPU) * 0.1;//* 0.1;  // wata 1/10  // bo away bzanen la matrixakada dacheta kweya wa dashixayna newan 0 bo 1
                double ram_check = ((serviceMem * 10) / cloudMem) * 0.1;//* 0.1;

                cpu_check = Double.parseDouble(String.format("%.1g%n", cpu_check));
                ram_check = Double.parseDouble(String.format("%.1g%n", ram_check));
                String result = cpu_check + "," + ram_check;

                int check = 0;
                ///// . CHECK AD,BD,UD
                for (int j = 0; j < acceptance_domain.length; j++) {
                    if (result.equalsIgnoreCase(acceptance_domain[j])) {
                        check = 1;
                    }
                }
                for (int j = 0; j < unacceptance_domain.length; j++) {
                    if (result.equalsIgnoreCase(unacceptance_domain[j])) {
                        check = 2;
                    }
                }

                if (check == 1) {
                    ArrayList list = new ArrayList();
                    list.add(0);    // service index
                    list.add(i);    // server index 
                    list.addAll(cloudServerList.get(i));   // server details
                    ADServers.add(list);
                } else if (check == 2) {
                    ArrayList list = new ArrayList();
                    list.add(0);    // service index
                    list.add(i);    // server index 
                    list.addAll(cloudServerList.get(i));   // server details                   
                    UDServers.add(list);
                } else {
                    ArrayList list = new ArrayList();
                    list.add(0);    // service index
                    list.add(i);    // server index 
                    list.addAll(cloudServerList.get(i));   // server details                    
                    BDServers.add(list);
                }

            }
        }
    }

    public void sorting() {
        ADServersAVG = cloudServerAVG(ADServers);
        BDServersAVG = cloudServerAVG(BDServers);
        UDServersAVG = cloudServerAVG(UDServers);

        ADServersSort = CloudServerSort(ADServersAVG);
        BDServersSort = CloudServerSort(BDServersAVG);
        UDServersSort = CloudServerSort(UDServersAVG);

    }
///////////////////// CloudServer  AVG  

    public void servicePalcements(String serviceType, int serviceIndex, ArrayList<ArrayList> serviceList) {

        if (ADServersSort.size() > 0) {
            //  ArrayList adaptableADServer = ADServersSort.get(ADServersSort.size() - 1);// wata axr dana danene ka zorbay shenakay girawa
            ArrayList adaptableADServer = ADServersSort.get(0);   // wata axr dana danene ka zorbay shenakay girawa
          // JOptionPane.showMessageDialog(null, adaptableADServer);
            deployServices(serviceType, serviceIndex, adaptableADServer, "AD");
            updateResourceCapacity(serviceIndex, serviceList, adaptableADServer);
        } else if (BDServersSort.size() > 0) {
            //  JOptionPane.showMessageDialog(null, BDServersSort.get(0) + "\n" + BDServersSort.get(BDServersSort.size() - 1));
//            ArrayList adaptableBDServer = BDServersSort.get(BDServersSort.size() - 1);// wata axr dana danene ka zorbay shenakay girawa
            ArrayList adaptableBDServer = BDServersSort.get(0);// wata axr dana danene ka zorbay shenakay girawa
            deployServices(serviceType, serviceIndex, adaptableBDServer, "BD");  // wata axr dana danene ka zorbay shenakay girawa 
            updateResourceCapacity(serviceIndex, serviceList, adaptableBDServer);

        } else if (UDServersSort.size() > 0) {
//            ArrayList adaptableUDServer = UDServersSort.get(UDServersSort.size() - 1);// wata axr dana danene ka zorbay shenakay girawa
            ArrayList adaptableUDServer = UDServersSort.get(0);    // wata axr dana danene ka zorbay shenakay girawa
            deployServices(serviceType, serviceIndex, adaptableUDServer, "UD");  // wata axr dana danene ka zorbay shenakay girawa 
            updateResourceCapacity(serviceIndex, serviceList, adaptableUDServer);

        } else {
           // JOptionPane.showMessageDialog(null, "service index: "+serviceIndex);
            ArrayList list =new ArrayList();
            list.add(serviceType);
            list.add(":");
            list.add(serviceIndex);
            Variables.waitedServices.add(list);
        }

    }

    public ArrayList<ArrayList> cloudServerAVG(ArrayList<ArrayList> cloudBeforeAVG) {
        ArrayList<ArrayList> cloudAVG = new ArrayList<ArrayList>();
        int avg;
        for (int i = 0; i < cloudBeforeAVG.size(); i++) {
            ArrayList list = new ArrayList();
            int serviceIndex = Integer.parseInt(cloudBeforeAVG.get(i).get(0).toString());
            int serverIndex = Integer.parseInt(cloudBeforeAVG.get(i).get(1).toString());
            int cpu = Integer.parseInt(cloudBeforeAVG.get(i).get(2).toString());
            int ram = Integer.parseInt(cloudBeforeAVG.get(i).get(3).toString());
            double maximum = Double.parseDouble(cloudBeforeAVG.get(i).get(4).toString());
            double idle = Double.parseDouble(cloudBeforeAVG.get(i).get(5).toString());
            avg = (cpu + ram) / 2;

            list.add(serviceIndex);
            list.add(serverIndex);
            list.add(cpu);
            list.add(ram);
            list.add(maximum);
            list.add(idle);
            list.add(avg);
            cloudAVG.add(list);
        }
        return cloudAVG;
    }

    //////////////////////  CloudServer SORTING
    public ArrayList<ArrayList> CloudServerSort(ArrayList<ArrayList> cloudServerBeforeSort) {
        CloudServerSorting sort = new CloudServerSorting();
        ArrayList<ArrayList> cloudServerSorted = sort.cloudSorting(cloudServerBeforeSort);

        return cloudServerSorted;
    }

    public void deployServices(String serviceType, int serviceIndex, ArrayList adaptableCloudServer, String mdrmType) {
        /*
        adaptableCloudServer contains the following
        index 0 = serviceIndex
        index 1 = serverIndex
        index 2 = cpu
        index 3 = ram
        index 4 = maximum power
        index 5 = idle power
         */
        if (serviceType.equalsIgnoreCase("critical")) {
            adaptableCloudServer.add("critical");
        } else {
            adaptableCloudServer.add("other");
        }
        adaptableCloudServer.set(0, serviceIndex);
        if (mdrmType.equalsIgnoreCase("AD")) {
            adaptableCloudServer.add("AD");
        } else if (mdrmType.equalsIgnoreCase("BD")) {
            adaptableCloudServer.add("BD");
        } else if (mdrmType.equalsIgnoreCase("UD")) {
            adaptableCloudServer.add("UD");
        }

        Variables.X_sc.add(adaptableCloudServer);
    }

    public void updateResourceCapacity(int serviceIndex, ArrayList<ArrayList> serviceList, ArrayList adaptableCloudServer) {

        int serverIndex = Integer.parseInt(adaptableCloudServer.get(1).toString());
        int serverCPU = Integer.parseInt(adaptableCloudServer.get(2).toString());
        int serverMem = Integer.parseInt(adaptableCloudServer.get(3).toString());
        int serviceCPU = 0, serviceMem = 0, Xvp = 1;

        for (int i = 0; i < serviceList.size(); i++) {
            if (i == serviceIndex) {
                serviceCPU = Integer.parseInt(serviceList.get(i).get(0).toString());
                serviceMem = Integer.parseInt(serviceList.get(i).get(1).toString());
                serverCPU = serverCPU - serviceCPU;      // Eq-5
                serverMem = serverMem - serviceMem;     //  Eq-5
            }
        }

        double server_CPU = Integer.parseInt(Variables.cloud_server_list1.get(serverIndex).get(0).toString());   // law lista wardagren 
        double energy = (Xvp * serviceCPU) / server_CPU;   // besheky yasay power la Eq:7

        if (Variables.cloud_server_list.get(serverIndex).size() < 5) {
            Variables.cloud_server_list.get(serverIndex).add(energy); // lerada indexy 4 zyad dakayn w energy tyada dadaneyn
        } else {
            double en = Double.parseDouble(Variables.cloud_server_list.get(serverIndex).get(4).toString());
            energy = energy + en;
            Variables.cloud_server_list.get(serverIndex).set(4, energy);
        }

        Variables.cloud_server_list.get(serverIndex).set(0, serverCPU);
        Variables.cloud_server_list.get(serverIndex).set(1, serverMem);

    }

    public void C_MEF() {

        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {

            if (Variables.cloud_server_list.get(i).size() > 4) {   // wata agar am server service lasar danrabw awa wata energy haya w indexy 4y bo zyadbwa 

                double power = Double.parseDouble(Variables.cloud_server_list.get(i).get(4).toString());
                double serverMaxPower = Double.parseDouble(Variables.cloud_server_list.get(i).get(2).toString());
                double serverIdlePower = Double.parseDouble(Variables.cloud_server_list.get(i).get(3).toString());
                int yp = 1;

                double E_s_c = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                E_s_c = Double.parseDouble(String.format("%.4g%n", E_s_c));

                Variables.cloud_server_list.get(i).set(4, E_s_c);
            }
            else
            {
               Variables.cloud_server_list.get(i).add("power off");               
            }
            
        }
    }

}
