/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import Utilities.Parameters;
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
public class Paper1_For_Evaluation {

    DecimalFormat formatter = new DecimalFormat("#0.0000");
    ArrayList listOfAdaptableDeviceForCurrentServicesInColony1, listOfAdaptableDeviceForCurrentServicesInColony2, listOfAdaptableDeviceForCurrentServicesInCloud;
    ServiceSorting serviceSorting;
    CloudServerSorting2 cloudServerSorting;
    FogSorting fogSorting;
    int all_Seed = 0;

    public Paper1_For_Evaluation(int seed) {
        addIndex();
        serviceSorting = new ServiceSorting();
        fogSorting = new FogSorting();
        cloudServerSorting = new CloudServerSorting2();

        moduleMap(seed);
        powerConsumption();

    }

    public void addIndex() {
        /*
        lakaty sortkrdny devicakanda rezbandy devicakan lagal backupakanda
        tek dachet w dwatr nazanen kam indexa barambar kam indexay
        backupakana boya deyn bo hamw devicek indexy 0 dakayn ba indexy aw 
        devica
        pashan bamada dazanen ka aw devica dakat kam devicay naw backupaka
         */

        for (int i = 0; i < Variables.fog_list_c1.size(); i++) {
            Variables.fog_list_c1.get(i).add(0, i);
            Variables.fog_list_backup_c1.get(i).add(0, i);
        }
        for (int i = 0; i < Variables.fog_list_c2.size(); i++) {
            Variables.fog_list_c2.get(i).add(0, i);
            Variables.fog_list_backup_c2.get(i).add(0, i);
        }

        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
            Variables.cloud_server_list.get(i).add(0, i);
        }

        Variables.cloud_server_list1.clear();
        for (ArrayList<ArrayList> servers : Variables.cloud_server_list) {
            Variables.cloud_server_list1.add((ArrayList<ArrayList>) servers.clone());
        }
    }

    public void moduleMap(int seed) {
        int serviceCPU, serviceMem;
        boolean checkColony1ForPlacementOfCurrentService = false, checkColony2ForPlacementOfCurrentService = false, checkCloudForPlacementOfCurrentService = false;
        sortingFogListColony1();
        sortingFogListColony2();
        sortingCloudServers();

        Variables.service_list = serviceSorting.serviceSorting(Variables.service_list);

        addingServicesDeadlineForResourceAware(seed);
        
        for (int i = 0; i < Variables.service_list.size(); i++) {

            checkColony1ForPlacementOfCurrentService = checkServiceForWaitingTime(i, "colony1");
           // checkColony2ForPlacementOfCurrentService = checkServiceForWaitingTime(i, "colony2");
            checkCloudForPlacementOfCurrentService = checkServiceForWaitingTime(i, "cloud");

            if (checkColony1ForPlacementOfCurrentService == true) { // first colony1
                int fogIndex = Integer.parseInt(listOfAdaptableDeviceForCurrentServicesInColony1.get(0).toString());
                deployServices("colony1", i, fogIndex);
                sortingFogListColony1();
            } else /*
                
                
             */ if (checkColony2ForPlacementOfCurrentService == true && checkColony1ForPlacementOfCurrentService == false) { // first colony1
                int fogIndex = Integer.parseInt(listOfAdaptableDeviceForCurrentServicesInColony2.get(0).toString());
                deployServices("colony2", i, fogIndex);
                sortingFogListColony2();
            } else /*
             
                                  
             */ if (checkColony1ForPlacementOfCurrentService == false && checkColony2ForPlacementOfCurrentService == false && checkCloudForPlacementOfCurrentService == true) {  // second cloud
                serviceCPU = Integer.parseInt(Variables.service_list.get(i).get(0).toString());
                serviceMem = Integer.parseInt(Variables.service_list.get(i).get(1).toString());
                serviceCPU = Variables.multiplyServiceCapacityInCloud * serviceCPU;   // 5 * serviceCPU 
                serviceMem = Variables.multiplyServiceCapacityInCloud * serviceMem;
                Variables.service_list.get(i).set(0, serviceCPU);
                Variables.service_list.get(i).set(1, serviceMem);
                int serverIndex = Integer.parseInt(listOfAdaptableDeviceForCurrentServicesInCloud.get(0).toString());
                deployServices("cloud", i, serverIndex);
                sortingCloudServers();
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
        // int noOfServiceOfCurrentApp = Variables.applications.get(appIndex).servicesForEachapplications.size();
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
        } else if (location.equalsIgnoreCase("colony2")) {
            listOfAdaptableDeviceForCurrentServicesInColony2 = new ArrayList();
            for (ArrayList<ArrayList> fogs : Variables.fog_list_c2) {
                deviceList.add((ArrayList<ArrayList>) fogs.clone());
            }
            startIndex = 1;
        } else if (location.equalsIgnoreCase("cloud")) {
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
            int deviceIndex = Integer.parseInt(deviceList.get(j).get(0).toString());
            int deviceCPU = Integer.parseInt(deviceList.get(j).get(1).toString());
            int deviceMem = Integer.parseInt(deviceList.get(j).get(2).toString());

            if (serviceCPU <= deviceCPU && serviceMem <= deviceMem) {
                deviceCPU = deviceCPU - serviceCPU;
                deviceMem = deviceMem - serviceMem;
                deviceList.get(j).set(1, deviceCPU);
                deviceList.get(j).set(2, deviceMem);
                deviceList.clone();
                check = true;
                if (location.equalsIgnoreCase("colony1")) {
                    listOfAdaptableDeviceForCurrentServicesInColony1.add(deviceIndex);
                } else if (location.equalsIgnoreCase("colony2")) {
                    listOfAdaptableDeviceForCurrentServicesInColony2.add(deviceIndex);
                } else if (location.equalsIgnoreCase("cloud")) {
                    listOfAdaptableDeviceForCurrentServicesInCloud.add(deviceIndex);
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

    public void sortingFogListColony1() {

        ArrayList fogListC1Broker = Variables.fog_list_backup_c1.get(0);
        fogListC1Broker.clone();
        Variables.fog_list_c1.remove(0).clone();
        //  JOptionPane.showMessageDialog(null, "hiwa1:   " + Variables.fog_list_c1);
        Variables.fog_list_c1 = fogSorting.fogSorting("c1", Variables.fog_list_c1);
        Variables.fog_list_c1.add(0, fogListC1Broker);
        //  JOptionPane.showMessageDialog(null, "hiwa2:   " + Variables.fog_list_c1);
    }

    public void sortingFogListColony2() {

        ArrayList fogListC2Broker = Variables.fog_list_backup_c2.get(0);
        fogListC2Broker.clone();
        Variables.fog_list_c2.remove(0).clone();
        //  JOptionPane.showMessageDialog(null, "nami:   " + Variables.fog_list_c2);
        Variables.fog_list_c2 = fogSorting.fogSorting("c2", Variables.fog_list_c2);
        Variables.fog_list_c2.add(0, fogListC2Broker);
        // JOptionPane.showMessageDialog(null, "nami:   " + Variables.fog_list_c2);

    }

    public void sortingCloudServers() {
        Variables.cloud_server_list = cloudServerSorting.cloudSorting(Variables.cloud_server_list);
    }

    public void deployServices(String location, int serviceIndex, int deviceIndex) {

        if (location.equalsIgnoreCase("colony1")) { // || location.equalsIgnoreCase("colony2")) {
            ArrayList list = new ArrayList();
            list.add(serviceIndex);
            list.add(deviceIndex);
            list.add(location);
            list.add("Ignore");
            Variables.X_sf.add(list);

        } else if (location.equalsIgnoreCase("colony2")) { // || location.equalsIgnoreCase("colony2")) {
            ArrayList list = new ArrayList();
            list.add(serviceIndex);
            list.add(deviceIndex);
            list.add(location);
            list.add("Ignore");
            Variables.X_sf.add(list);

        } else {
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
        int fogCPU = 0, fogMem = 0, devIndex = 0;

        if (location.equalsIgnoreCase("colony1")) {
            for (int i = 1; i < Variables.fog_list_c1.size(); i++) {
                int backUpFogIndex = Integer.parseInt(Variables.fog_list_c1.get(i).get(0).toString());
                if (backUpFogIndex == deviceIndex) {
                    devIndex = i;
                    fogCPU = Integer.parseInt(Variables.fog_list_c1.get(i).get(1).toString());
                    fogMem = Integer.parseInt(Variables.fog_list_c1.get(i).get(2).toString());
                    break;
                }
            }
        } else if (location.equalsIgnoreCase("colony2")) {
            for (int i = 1; i < Variables.fog_list_c2.size(); i++) {
                int backUpFogIndex = Integer.parseInt(Variables.fog_list_c2.get(i).get(0).toString());
                if (backUpFogIndex == deviceIndex) {
                    devIndex = i;
                    fogCPU = Integer.parseInt(Variables.fog_list_c2.get(i).get(1).toString());
                    fogMem = Integer.parseInt(Variables.fog_list_c2.get(i).get(2).toString());
                    break;
                }
            }
        } else if (location.equalsIgnoreCase("cloud")) {
            for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
                int backUpServerIndex = Integer.parseInt(Variables.cloud_server_list.get(i).get(0).toString());
                if (backUpServerIndex == deviceIndex) {
                    devIndex = i;
                    fogCPU = Integer.parseInt(Variables.cloud_server_list.get(i).get(1).toString());
                    fogMem = Integer.parseInt(Variables.cloud_server_list.get(i).get(2).toString());
                    break;
                }
            }
        }
        int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
        int serviceMem = Integer.parseInt(Variables.service_list.get(serviceIndex).get(1).toString());
        fogCPU = fogCPU - serviceCPU;      // Eq-4
        fogMem = fogMem - serviceMem;     //  Eq-4
        /*
               
         */

        // UPDATE RESOURCE AND POWER FOR EVERY SERVICE
        int Xvp = 1, deviceCPU = 0;
        if (location.equalsIgnoreCase("colony1")) {
            for (int x = 1; x < Variables.fog_list_c1.size(); x++) {
                int backUpFogIndex = Integer.parseInt(Variables.fog_list_c1.get(x).get(0).toString());
                if (backUpFogIndex == deviceIndex) {
                    Variables.fog_list_c1.get(x).set(1, fogCPU);
                    Variables.fog_list_c1.get(x).set(2, fogMem);
                    break;
                }
            }
            deviceCPU = Integer.parseInt(Variables.fog_list_backup_c1.get(deviceIndex).get(1).toString());
            serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            int size = Variables.X_sf.size() - 1;
            Variables.X_sf.get(size).add(power);
        } else /*
            
         */ if (location.equalsIgnoreCase("colony2")) {
            for (int x = 1; x < Variables.fog_list_c2.size(); x++) {
                int backUpFogIndex = Integer.parseInt(Variables.fog_list_c2.get(x).get(0).toString());
                if (backUpFogIndex == deviceIndex) {
                    Variables.fog_list_c2.get(x).set(1, fogCPU);
                    Variables.fog_list_c2.get(x).set(2, fogMem);
                    break;
                }
            }
            deviceCPU = Integer.parseInt(Variables.fog_list_backup_c2.get(deviceIndex).get(1).toString());
            serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            int size = Variables.X_sf.size() - 1;
            Variables.X_sf.get(size).add(power);
        } else /*
                         
         */ if (location.equalsIgnoreCase("cloud")) {
            for (int x = 0; x < Variables.cloud_server_list.size(); x++) {
                int backUpFogIndex = Integer.parseInt(Variables.cloud_server_list.get(x).get(0).toString());
                if (backUpFogIndex == deviceIndex) {
                    int serverCPU = fogCPU; //Integer.parseInt(Variables.cloud_server_list.get(x).get(1).toString());
                    int serverMem = fogMem; //Integer.parseInt(Variables.cloud_server_list.get(x).get(2).toString());
                    Variables.cloud_server_list.get(x).set(1, serverCPU);
                    Variables.cloud_server_list.get(x).set(2, serverMem);
                    break;
                }
            }
            deviceCPU = Integer.parseInt(Variables.cloud_server_list1.get(deviceIndex).get(1).toString());
            serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            int size = Variables.X_sc.size() - 1;
            Variables.X_sc.get(size).add(power);
        }

    }

    public void powerConsumption1() {

//        //  kokrdanaway basheky powery hamw aw servicanay ka danrawn lasar har fogek
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

    public void powerConsumption() {

        double prevPower = 0;
        for (int i = 0; i < Variables.X_sc.size(); i++) {
            int serverIndex = Integer.parseInt(Variables.X_sc.get(i).get(1).toString());
            double powerOfThisService = Double.parseDouble(Variables.X_sc.get(i).get(4).toString());
            int devIndex = 0;
            for (int ii = 0; ii < Variables.cloud_server_list.size(); ii++) {
                int backUpServerIndex = Integer.parseInt(Variables.cloud_server_list.get(ii).get(0).toString());
                if (backUpServerIndex == serverIndex) {
                    devIndex = ii;
                    break;
                }
            }

            if (Variables.cloud_server_list.get(devIndex).size() == 5) {
                Variables.cloud_server_list.get(devIndex).add(powerOfThisService);
            } else {
                prevPower = Double.parseDouble(Variables.cloud_server_list.get(devIndex).get(5).toString());
                double power = Double.sum(prevPower, powerOfThisService);
                Variables.cloud_server_list.get(devIndex).set(5, power);
            }
        }

        /*
        
        
        
        
         */
        // cloud
        for (int i = 0; i < Variables.cloud_server_list.size(); i++) { // i=1 bo away broker hsab nakret

            if (Variables.cloud_server_list.get(i).size() > 5) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                double power = Double.parseDouble(Variables.cloud_server_list.get(i).get(5).toString());
                double serverMaxPower = Double.parseDouble(Variables.cloud_server_list.get(i).get(3).toString());
                double serverIdlePower = Double.parseDouble(Variables.cloud_server_list.get(i).get(4).toString());
                int yp = 1;

                double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                double E_s_f1 = Double.parseDouble(formatter.format(E_s_f));
                Variables.cloud_server_list.get(i).set(5, E_s_f1);
            } else {
                Variables.cloud_server_list.get(i).add("power off");
            }
        }
    }

    public void addingServicesDeadlineForResourceAware(int seed) {

        Random rand, rand1;
        if (seed == 1) {
            all_Seed++;
            rand = new Random(all_Seed);
            rand1 = new Random(all_Seed);
        } else {
            rand = new Random();
            rand1 = new Random();
        }
        /*
         laxwarawa array deadline ba 0 pr dakaynawa w pashan aupdatey dakayn 
         ba deadliny critical ya normal        
         */
        for (int i = 0; i < Variables.NoOfService; i++) {
            Variables.servicesDeadline.add(0);
        }

        double numberOfCritical = ((double) Variables.percentage_Number_Of_Critical_Services / (double) 100) * Variables.NoOfService;
        Variables.number_Of_Critical_Services = (int) numberOfCritical;

        /*
            indexOfCriticalServices ba henday zhmaray criticalakana 
            la naw servicekanda henday am indexy random haldabzheren 
            bo away aw indexana bkayn ba critical
         */
        ArrayList indexOfCriticalServices = new ArrayList();
        //JOptionPane.showMessageDialog(null, "nn : " + Variables.number_Of_Critical_Services);

        for (int i = 0; i < Variables.number_Of_Critical_Services; i++) {
            int randIndex;
            // am do whilea bakardet bo away ka indexy dwbara dyarenakat bo critical
            do {
                randIndex = rand.nextInt(Variables.service_list.size()); // 0 to 99 not get 100
            } while (indexOfCriticalServices.contains(randIndex));              // bo away la randomakada dwbarabwnawa rwnadat
            indexOfCriticalServices.add(randIndex);
        }

        boolean check = false;
        for (int i = 0; i < Variables.service_list.size(); i++) {

            for (int j = 0; j < indexOfCriticalServices.size(); j++) {
                int value = Integer.parseInt(indexOfCriticalServices.get(j).toString());
                if (value == i) {
                    check = true;
                    break;
                }
            }
            if (check == true) {
                int criticalRandomDeadline = (rand.nextInt((Parameters.critical_Service_Deadline_Max - Parameters.critical_Service_Deadline_Min) + 1) + Parameters.critical_Service_Deadline_Min);
                Variables.servicesDeadline.set(i, criticalRandomDeadline);
                check = false;
            } else {
                int normalRandomDeadline = (rand.nextInt((Parameters.normal_Service_Deadline_Max - Parameters.normal_Service_Deadline_Min) + 1) + Parameters.normal_Service_Deadline_Min);
                Variables.servicesDeadline.set(i, normalRandomDeadline);
            }
        }
    }
}
