/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// am versiona ba sheway eshy dwam(appy chand servicy) krawa w 
// random hisaby bo nakrawa w ba sheway FF darwat sarata hawly c1 dadat 
// w dwatr hawly cloud dadat
package pamed_implementation;

import Utilities.Variables;
import com.sun.jndi.ldap.EntryChangeResponseControl;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.objects.Global;  // Broker power
import sun.java2d.pipe.ValidatePipe;

/**
 *
 * @author hiwa_cst
 */
public class Edgeward1 {

    DecimalFormat formatter = new DecimalFormat("#0.0000");
    ArrayList listOfAdaptableDeviceForCurrentServicesInColony1, listOfAdaptableDeviceForCurrentServicesInColony2, listOfAdaptableDeviceForCurrentServicesInCloud;

    public Edgeward1(int seed) {
        edgeWardServicePlacement(seed);
        cloudPowerConsumption();
    }

    public void edgeWardServicePlacement(int seed) {
        int serviceCPU, serviceMem;
        boolean checkColony1ForPlacementOfCurrentService  = false, checkCloudForPlacementOfCurrentService = false;

        for (int i = 0; i < Variables.service_list.size(); i++) {

            checkColony1ForPlacementOfCurrentService = checkServiceForWaitingTime(i, "colony1");
            checkCloudForPlacementOfCurrentService = checkServiceForWaitingTime(i, "cloud");

           // if(i >= 84)
            //    JOptionPane.showMessageDialog(null, checkColony1ForPlacementOfCurrentService+"\n"+checkCloudForPlacementOfCurrentService);
            if (checkColony1ForPlacementOfCurrentService == true) { // first colony1
                int fogIndex = Integer.parseInt(listOfAdaptableDeviceForCurrentServicesInColony1.get(0).toString());
                deployServices("colony1", i, fogIndex);
            } else
                /*
                
             */ 
                if (checkColony1ForPlacementOfCurrentService == false && checkCloudForPlacementOfCurrentService == true) {  // second cloud
                serviceCPU = Integer.parseInt(Variables.service_list.get(i).get(0).toString());
                serviceMem = Integer.parseInt(Variables.service_list.get(i).get(1).toString());
                serviceCPU = Variables.multiplyServiceCapacityInCloud * serviceCPU;   // 5 * serviceCPU 
                serviceMem = Variables.multiplyServiceCapacityInCloud * serviceMem;
                Variables.service_list.get(i).set(0, serviceCPU);
                Variables.service_list.get(i).set(1, serviceMem);
                int serverIndex = Integer.parseInt(listOfAdaptableDeviceForCurrentServicesInCloud.get(0).toString());
                deployServices("cloud", i, serverIndex);
            } /*
            
            
             */ else {   // third added htis app to the waited list
                ArrayList l = new ArrayList();
                l.add(i);
                Variables.waitedServices.add(l);
            }

        }
    }

    public boolean checkServiceForWaitingTime(int serviceIndex, String location) // checky hamw servicy har appek dakayn bzanen hamwy jey dabetawa yan na chwnka agar yak danayayan jey nabetawa awa appaka ba tawawy dacheta waitingawa
    {
        boolean check = false;
        int startIndex = 0;
        // copy server list  bo away agar hatw am app jeynabwyawa awa ba sizey server dagernakat bahala 
        ArrayList<ArrayList> deviceList = new ArrayList<ArrayList>();
        if (location.equalsIgnoreCase("colony1")) {
            listOfAdaptableDeviceForCurrentServicesInColony1 = new ArrayList();
            for (ArrayList<ArrayList> fogs : Variables.fog_list_c1) {
                deviceList.add((ArrayList<ArrayList>) fogs.clone());
            }
            startIndex = 1;
        } 

        else if (location.equalsIgnoreCase("cloud")) {
            listOfAdaptableDeviceForCurrentServicesInCloud = new ArrayList();
            for (ArrayList<ArrayList> servers : Variables.cloud_server_list) {
                deviceList.add((ArrayList<ArrayList>) servers.clone());
            }
            startIndex = 0;
        }

        int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
        int serviceMem = Integer.parseInt(Variables.service_list.get(serviceIndex).get(1).toString());

        if (location.equalsIgnoreCase("cloud")) { // chwnka tanha la cloud da sizy serviceakan zyad dabet 
            serviceCPU = Variables.multiplyServiceCapacityInCloud * serviceCPU;   // 5 * serviceCPU 
            serviceMem = Variables.multiplyServiceCapacityInCloud * serviceMem;
        }
        for (int j = startIndex; j < deviceList.size(); j++) {
           // int deviceIndex = Integer.parseInt(deviceList.get(j).get(0).toString());
            int deviceCPU = Integer.parseInt(deviceList.get(j).get(0).toString());
            int deviceMem = Integer.parseInt(deviceList.get(j).get(1).toString());

            if (serviceCPU <= deviceCPU && serviceMem <= deviceMem) {
                deviceCPU = deviceCPU - serviceCPU;
                deviceMem = deviceMem - serviceMem;
                deviceList.get(j).set(0, deviceCPU);
                deviceList.get(j).set(1, deviceMem);
                deviceList.clone();
                check = true;
                if (location.equalsIgnoreCase("colony1")) {
                    listOfAdaptableDeviceForCurrentServicesInColony1.add(j);
                } 
                else if (location.equalsIgnoreCase("cloud")) {
                    listOfAdaptableDeviceForCurrentServicesInCloud.add(j);
                }
                break;
            }
        }

        if (check == true) {
            return true;
        } else {
            return false;
        }
    }
    public void deployServices(String location, int serviceIndex, int deviceIndex) {

        if (location.equalsIgnoreCase("colony1")) { // || location.equalsIgnoreCase("colony2")) {
            ArrayList list = new ArrayList();
            list.add(serviceIndex);
            list.add(deviceIndex);
            list.add(location);
            list.add("Ignore");
            Variables.X_sf.add(list);

        } 
        else {
            ArrayList list1 = new ArrayList();
            list1.add(serviceIndex);
            list1.add(deviceIndex);
            list1.add(location);
            list1.add("Ignore");
            Variables.X_sc.add(list1);
        }
        updateResourceCapacity(location, serviceIndex, deviceIndex);
    }
 
    public void updateResourceCapacity(String location, int serviceIndex, int deviceIndex) {

        int fogCPU = 0, fogMem = 0;
        if (location.equalsIgnoreCase("colony1")) {
            fogCPU = Integer.parseInt(Variables.fog_list_c1.get(deviceIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.fog_list_c1.get(deviceIndex).get(1).toString());
        } 
  
        else if (location.equalsIgnoreCase("cloud")) {
            fogCPU = Integer.parseInt(Variables.cloud_server_list.get(deviceIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.cloud_server_list.get(deviceIndex).get(1).toString());
        }

        for (int i = 0; i < Variables.service_list.size(); i++) {
            if (i == serviceIndex) {
                int serviceCPU = Integer.parseInt(Variables.service_list.get(i).get(0).toString());
                int serviceMem = Integer.parseInt(Variables.service_list.get(i).get(1).toString());
                fogCPU = fogCPU - serviceCPU;      // Eq-4
                fogMem = fogMem - serviceMem;     //  Eq-4
                break;
            }
        }

        // UPDATE RESOURCE AND POWER FOR EVERY SERVICE
        int Xvp = 1, deviceCPU = 0;
        if (location.equalsIgnoreCase("colony1")) {
            Variables.fog_list_c1.get(deviceIndex).set(0, fogCPU);
            Variables.fog_list_c1.get(deviceIndex).set(1, fogMem);
            deviceCPU = Integer.parseInt(Variables.fog_list_backup_c1.get(deviceIndex).get(0).toString());
            int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            int size = Variables.X_sf.size() - 1;
            Variables.X_sf.get(size).add(power);
        } 
 
        else if (location.equalsIgnoreCase("cloud")) {
            Variables.cloud_server_list.get(deviceIndex).set(0, fogCPU);
            Variables.cloud_server_list.get(deviceIndex).set(1, fogMem);
            deviceCPU = Integer.parseInt(Variables.cloud_server_list1.get(deviceIndex).get(0).toString());
            int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            int size = Variables.X_sc.size() - 1;
            Variables.X_sc.get(size).add(power);
        }

    }

    public void cloudPowerConsumption() {

        for (int i = 0; i < Variables.X_sc.size(); i++) {

            int serverIndex = Integer.parseInt(Variables.X_sc.get(i).get(1).toString());
            double powerOfThisService = Double.parseDouble(Variables.X_sc.get(i).get(4).toString());
            if (Variables.cloud_server_list.get(serverIndex).size() == 4) {
                Variables.cloud_server_list.get(serverIndex).add(powerOfThisService);
            } else {
                double prevPower = Double.parseDouble(Variables.cloud_server_list.get(serverIndex).get(4).toString());
                double power = Double.sum(prevPower, powerOfThisService);
                Variables.cloud_server_list.get(serverIndex).set(4, power);
            }

        }
 
        // cloud
        for (int i = 0; i < Variables.cloud_server_list.size(); i++) { // i=1 bo away broker hsab nakret

            if (Variables.cloud_server_list.get(i).size() > 4) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                double power = Double.parseDouble(Variables.cloud_server_list.get(i).get(4).toString());
                double serverMaxPower = Double.parseDouble(Variables.cloud_server_list.get(i).get(2).toString());
                double serverIdlePower = Double.parseDouble(Variables.cloud_server_list.get(i).get(3).toString());
                int yp = 1;

                double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                 double E_s_f1 = Double.parseDouble(formatter.format(E_s_f));
                Variables.cloud_server_list.get(i).set(4, E_s_f1);
            } else {
                Variables.cloud_server_list.get(i).add("power off");
            }
        }
    }

}
