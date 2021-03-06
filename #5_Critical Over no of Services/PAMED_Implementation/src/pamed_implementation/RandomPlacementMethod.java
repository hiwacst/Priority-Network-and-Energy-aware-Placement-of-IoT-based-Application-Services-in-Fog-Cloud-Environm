/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class RandomPlacementMethod {

    DecimalFormat formatter = new DecimalFormat("#0.0000");

    public RandomPlacementMethod(int seed) {
        randomServicePlacement(seed);
        powerConsumption();

//        Method.serverResourceWastage();
//        countNoOfServiceInEachServer();
//        fogResourceWastage();
//        countNoOfServiceInEachFog();
//        fogPowerConsumption();
    }

    public void randomServicePlacement(int seed) {
        int serviceCPU, serviceMem;
        int indexOfRandDevice = 0;
        for (int i = 0; i < Variables.service_list.size(); i++) {
            ArrayList listOfIndexOfAdaptableDevices = new ArrayList();
            serviceCPU = Integer.parseInt(Variables.service_list.get(i).get(0).toString());
            serviceMem = Integer.parseInt(Variables.service_list.get(i).get(1).toString());
            String location = randomLocation(seed, i, serviceCPU, serviceMem);

            if (location.equalsIgnoreCase("colony1")) {
                listOfIndexOfAdaptableDevices = listOfIndexOfAdaptableDevice(location, Variables.fog_list_c1, serviceCPU, serviceMem);
                indexOfRandDevice = indexOfRandomDevice(seed, listOfIndexOfAdaptableDevices, i, Variables.fog_list_c1.size() - 1);
                deployServices("colony1", i, indexOfRandDevice);
            } else if (location.equalsIgnoreCase("colony2")) {
                listOfIndexOfAdaptableDevices = listOfIndexOfAdaptableDevice(location, Variables.fog_list_c2, serviceCPU, serviceMem);
                indexOfRandDevice = indexOfRandomDevice(seed, listOfIndexOfAdaptableDevices, i, Variables.fog_list_c2.size() - 1);
                deployServices("colony2", i, indexOfRandDevice);

            } else if (location.equalsIgnoreCase("cloud")) {
                int serviceCPU1 = Integer.parseInt(Variables.service_list.get(i).get(0).toString());
                int serviceMem1 = Integer.parseInt(Variables.service_list.get(i).get(1).toString());
                serviceCPU1 = Variables.multiplyServiceCapacityInCloud * serviceCPU1;   // 5 * serviceCPU 
                serviceMem1 = Variables.multiplyServiceCapacityInCloud * serviceMem1;
             //   JOptionPane.showMessageDialog(null, "1: "+Variables.service_list);

                Variables.service_list.get(i).set(0, serviceCPU1);
                Variables.service_list.get(i).set(1, serviceMem1);
               // Variables.service_list.clone();
              //  JOptionPane.showMessageDialog(null, "2: "+Variables.service_list);
                listOfIndexOfAdaptableDevices = listOfIndexOfAdaptableDevice(location, Variables.cloud_server_list, serviceCPU1, serviceMem1);
                indexOfRandDevice = indexOfRandomDevice(seed, listOfIndexOfAdaptableDevices, i, Variables.cloud_server_list.size() - 1);
                deployServices("cloud", i, indexOfRandDevice);
            } else if (location.equalsIgnoreCase("not")) {
                ArrayList l = new ArrayList();
                l.add(i);
                Variables.waitedServices.add(l);
            }
        }
    }

    public String randomLocation(int seed, int serviceIndex, int serviceCPU, int serviceMem) // wata la c1 yan c2 yan cloud kamyan dabnret
    {
        String location = "";
        Random rand;
        boolean checkColony1 = chekLocation("colony1", serviceIndex, Variables.fog_list_c1, serviceCPU, serviceMem);
        boolean checkColony2 = chekLocation("colony2", serviceIndex, Variables.fog_list_c2, serviceCPU, serviceMem);
        int serviceCPU1 = Variables.multiplyServiceCapacityInCloud * serviceCPU;   // 5 * serviceCPU 
        int serviceMem1 = Variables.multiplyServiceCapacityInCloud * serviceMem;
        boolean checkCloud = chekLocation("cloud", serviceIndex, Variables.cloud_server_list, serviceCPU1, serviceMem1);
      //  JOptionPane.showMessageDialog(null, "checkCloud: " + checkCloud);

        if (seed == 1) {
            rand = new Random(serviceIndex);
        } else {
            rand = new Random();
        }
        int random = rand.nextInt(3);//(3 - 1) + 1 - 1);
        // am 6 marjaman haya bo away la har shwenekda c1,c2,cloud devicy gwnjaw nabw bo aw service awa ba hisab bo aw shwenanakt
        if (random == 0 && checkColony1 == true) {
            location = "colony1";
        } else if (random == 1 && checkColony2 == true) {
            location = "colony2";
        } else if (random == 2 && checkCloud == true) {
            location = "cloud";
        } else if (checkColony2 == true) {
            location = "colony2";
        } else if (checkCloud == true) {
            location = "cloud";
        } else if (checkColony1 == true) {
            location = "colony1";
        } else {
            location = "not";
        }
        return location;
    }

    public boolean chekLocation(String location, int serviceIndex, ArrayList<ArrayList> deviceList, int serviceCPU, int serviceMem) {
        boolean check = false;
        // int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
        //  int serviceMem = Integer.parseInt(Variables.service_list.get(serviceIndex).get(1).toString());
        int startIndex;
        if (location.equalsIgnoreCase("colony1") || location.equalsIgnoreCase("colony2")) {
            startIndex = 1;
        } else {
            startIndex = 0;
        }
        for (int i = startIndex; i < deviceList.size(); i++) {
            int deviceCPU = Integer.parseInt(deviceList.get(i).get(0).toString());
            int deviceMem = Integer.parseInt(deviceList.get(i).get(1).toString());

           // if (location.equalsIgnoreCase("cloud")) 
             //   JOptionPane.showMessageDialog(null, "checkCloud: " +deviceCPU+"\t"+deviceMem+"\n"+
                    //    serviceCPU+"\t"+serviceMem);
            
            if (serviceCPU <= deviceCPU && serviceMem <= deviceMem) {
                check = true;
                break;
            }
        }
        return check;
    }

    public ArrayList listOfIndexOfAdaptableDevice(String location, ArrayList<ArrayList> deviceList, int serviceCPU, int serviceMem) {
        int deviceCPU, deviceMem;
        ArrayList indexOfAdaptableList = new ArrayList();
        int startIndex;
        // agar la fogdabw ba f0 bakarnayat chwnka aw tanha managera
        if (location.equalsIgnoreCase("colony1") || location.equalsIgnoreCase("colony2")) {
            startIndex = 1;
        } else {
            startIndex = 0;
        }
        for (int i = startIndex; i < deviceList.size(); i++) {
            deviceCPU = Integer.parseInt(deviceList.get(i).get(0).toString());
            deviceMem = Integer.parseInt(deviceList.get(i).get(1).toString());
            if (serviceCPU <= deviceCPU && serviceMem <= deviceMem) {
                indexOfAdaptableList.add(i);
            }
        }
        return indexOfAdaptableList;
    }

    public int indexOfRandomDevice(int seed, ArrayList listOfIndexOfAdaptableDevices, int serviceIndex, int sizeOfDevices) // lakam deviceda dabnret
    {
        Random rand;
        if (seed == 1) {
            rand = new Random(serviceIndex);
        } else {
            rand = new Random();
        }
        // int size = listOfIndexOfAdaptableDevices.size() - ;
        int size = listOfIndexOfAdaptableDevices.size();
        // int random = rand.nextInt((size - 0) + 1 - 0);
        int random = rand.nextInt(size);  //(size - 0) + 1 - 0);
        int randomIndex = Integer.parseInt(listOfIndexOfAdaptableDevices.get(random).toString());
        return randomIndex;
    }

    public void deployServices(String location, int serviceIndex, int deviceIndex) {

        if (location.equalsIgnoreCase("colony1") || location.equalsIgnoreCase("colony2")) {
            ArrayList list = new ArrayList();
            list.add(serviceIndex);
            list.add(deviceIndex);
            list.add(location);
            //  list.add("no E or D");
            list.add("Ignore");
            Variables.X_sf.add(list);

        } else {
            ArrayList list1 = new ArrayList();
            list1.add(serviceIndex);
            list1.add(deviceIndex);
            //  JOptionPane.showMessageDialog(null, "nami: "+location);
            list1.add(location);

            //  ArrayList serverDetails = Variables.cloud_server_list.get(deviceIndex);
            // list1.addAll(serverDetails);
            // list1.add("avg");
            list1.add("Ignore");
            //  list1.add("no AD,BD,UD");
            Variables.X_sc.add(list1);
        }
//JOptionPane.showMessageDialog(null, "hiwa:  "+Variables.X_sf.get(0)+"\nhiwa:  "+Variables.X_sc.get(0));
        updateResourceCapacity(location, serviceIndex, deviceIndex);
    }

    public void updateResourceCapacity(String location, int serviceIndex, int deviceIndex) {

        int fogCPU = 0, fogMem = 0;
        if (location.equalsIgnoreCase("colony1")) {
            fogCPU = Integer.parseInt(Variables.fog_list_c1.get(deviceIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.fog_list_c1.get(deviceIndex).get(1).toString());
        } else if (location.equalsIgnoreCase("colony2")) {
            fogCPU = Integer.parseInt(Variables.fog_list_c2.get(deviceIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.fog_list_c2.get(deviceIndex).get(1).toString());
        } else if (location.equalsIgnoreCase("cloud")) {
            fogCPU = Integer.parseInt(Variables.cloud_server_list.get(deviceIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.cloud_server_list.get(deviceIndex).get(1).toString());
        }

        for (int i = 0; i < Variables.service_list.size(); i++) {
            if (i == serviceIndex) {
                int serviceCPU = Integer.parseInt(Variables.service_list.get(i).get(0).toString());
                int serviceMem = Integer.parseInt(Variables.service_list.get(i).get(1).toString());
                fogCPU = fogCPU - serviceCPU;      // Eq-4
                fogMem = fogMem - serviceMem;     //  Eq-4
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
        } else if (location.equalsIgnoreCase("colony2")) {
            Variables.fog_list_c2.get(deviceIndex).set(0, fogCPU);
            Variables.fog_list_c2.get(deviceIndex).set(1, fogMem);
            deviceCPU = Integer.parseInt(Variables.fog_list_backup_c2.get(deviceIndex).get(0).toString());
            int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            if (power == Global.Infinity) {
                JOptionPane.showMessageDialog(null, serviceCPU + "\t" + deviceCPU);
            }
            int size = Variables.X_sf.size() - 1;
            Variables.X_sf.get(size).add(power);
        } else if (location.equalsIgnoreCase("cloud")) {
            Variables.cloud_server_list.get(deviceIndex).set(0, fogCPU);
            Variables.cloud_server_list.get(deviceIndex).set(1, fogMem);
            deviceCPU = Integer.parseInt(Variables.cloud_server_list1.get(deviceIndex).get(0).toString());
            int serviceCPU = Integer.parseInt(Variables.service_list.get(serviceIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) deviceCPU;   // besheky yasay power la Eq:7
            int size = Variables.X_sc.size() - 1;
            Variables.X_sc.get(size).add(power);
        }

    }

    public void powerConsumption() {

//        //  kokrdanaway basheky powery hamw aw servicanay ka danrawn lasar har fogek
//        for (int i = 0; i < Variables.X_sf.size(); i++) {
//
//            int fogIndex = Integer.parseInt(Variables.X_sf.get(i).get(1).toString());
//            //    double powerOfThisService = Double.parseDouble(Variables.X_sf.get(i).get(3).toString());
//            double powerOfThisService = Double.parseDouble(Variables.X_sf.get(i).get(4).toString());
//            int size = Variables.X_sf.get(i).size();
//            //   String colony = Variables.X_sf.get(i).get(size - 2) + "";
//            String colony = Variables.X_sf.get(i).get(size - 3) + "";
//
//            if (colony.equalsIgnoreCase("colony1")) {
//
//                if (Variables.fog_list_c1.get(fogIndex).size() == 4) {
//                    Variables.fog_list_c1.get(fogIndex).add(powerOfThisService);
//                } else {
//                    double prevPower = Double.parseDouble(Variables.fog_list_c1.get(fogIndex).get(4).toString());
//                    double power = Double.sum(prevPower, powerOfThisService);
//                    Variables.fog_list_c1.get(fogIndex).set(4, power);
//                }
//            } else {
//                if (Variables.fog_list_c2.get(fogIndex).size() == 4) {
//                    Variables.fog_list_c2.get(fogIndex).add(powerOfThisService);
//                } else {
//                    double prevPower = Double.parseDouble(Variables.fog_list_c2.get(fogIndex).get(4).toString());
//                    double power = Double.sum(prevPower, powerOfThisService);
//                    Variables.fog_list_c2.get(fogIndex).set(4, power);
//                }
//            }
//
//        }
//                   ArrayList list1 = new ArrayList();
//            list1.add(serviceIndex);
//            list1.add(deviceIndex);
//            ArrayList serverDetails = Variables.cloud_server_list.get(deviceIndex);
//            list1.addAll(serverDetails);
//            list1.add("avg");
//            list1.add("Ignore");
//            list1.add("no AD,BD,UD");
//            Variables.X_sc.add(list1);
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
//
//        //  dozinaway power consumption batawawy ba psht bastn ba Eq:7 bashy dwamy
//        // colony1
//        for (int i = 0; i < Variables.fog_list_c1.size(); i++) { // i=1 bo away broker hsab nakret
//
//            if (i == 0) {
//                Variables.fog_list_c1.get(i).add("broker");
//            } else {
//                if (Variables.fog_list_c1.get(i).size() > 4) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 
//
//                    double power = Double.parseDouble(Variables.fog_list_c1.get(i).get(4).toString());
//                    double serverMaxPower = Double.parseDouble(Variables.fog_list_c1.get(i).get(2).toString());
//                    double serverIdlePower = Double.parseDouble(Variables.fog_list_c1.get(i).get(3).toString());
//                    int yp = 1;
//
//                    double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
//                    //  E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
//                    double E_s_f1 = Double.parseDouble(formatter.format(E_s_f));
//                    Variables.fog_list_c1.get(i).set(4, E_s_f1);
//                } else {
//                    Variables.fog_list_c1.get(i).add("power off");
//                }
//            }
//        }
//
//        // colony2
//        for (int i = 0; i < Variables.fog_list_c2.size(); i++) { // i=1 bo away broker hsab nakret
//            if (i == 0) {
//                Variables.fog_list_c2.get(i).add("broker");
//            } else {
//
//                if (Variables.fog_list_c2.get(i).size() > 4) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 
//
//                    double power = Double.parseDouble(Variables.fog_list_c2.get(i).get(4).toString());
//                    double serverMaxPower = Double.parseDouble(Variables.fog_list_c2.get(i).get(2).toString());
//                    double serverIdlePower = Double.parseDouble(Variables.fog_list_c2.get(i).get(3).toString());
//                    int yp = 1;
//
//                    double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
//                    // E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
//                    double E_s_f1 = Double.parseDouble(formatter.format(E_s_f));
//                    Variables.fog_list_c2.get(i).set(4, E_s_f1);
//                } else {
//                    Variables.fog_list_c2.get(i).add("power off");
//                }
//            }
//        }
        // cloud
        for (int i = 0; i < Variables.cloud_server_list.size(); i++) { // i=1 bo away broker hsab nakret

            if (Variables.cloud_server_list.get(i).size() > 4) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                double power = Double.parseDouble(Variables.cloud_server_list.get(i).get(4).toString());
                double serverMaxPower = Double.parseDouble(Variables.cloud_server_list.get(i).get(2).toString());
                double serverIdlePower = Double.parseDouble(Variables.cloud_server_list.get(i).get(3).toString());
                int yp = 1;

                double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                //  E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
                double E_s_f1 = Double.parseDouble(formatter.format(E_s_f));
                Variables.cloud_server_list.get(i).set(4, E_s_f1);
            } else {
                Variables.cloud_server_list.get(i).add("power off");
            }
        }

//        System.out.println("      XXXXXXXXXXXXX");
//        for (int i = 0; i < Variables.X_sf.size(); i++) {
//            System.out.println(Variables.X_sf.get(i));
//        }
//        System.out.println("\n\n");
//        System.out.println("      YYYYYYYYYYYYYYYYYYŸ");
//
//        for (int i = 0; i < Variables.X_sc.size(); i++) {
//            System.out.println(Variables.X_sc.get(i));
//            
//        }
//        
//        System.out.println("\n\n\n");
    }

}
