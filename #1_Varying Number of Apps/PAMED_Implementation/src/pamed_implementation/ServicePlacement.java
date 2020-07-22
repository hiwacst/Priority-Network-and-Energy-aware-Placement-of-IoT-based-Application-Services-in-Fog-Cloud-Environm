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
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import static pamed_implementation.Fog_Topology.fog_numbers;
import pamed_implementation.Method;

/**
 *
 * @author hiwa_cst
 */
public class ServicePlacement {

    Random_Generation random_gen;
    Method meth;
    int all_Seed = 0;
    // Variables vb;

    public ServicePlacement(int seed, int mehtodNumber) {

        random_gen = new Random_Generation();
        generateService(seed, mehtodNumber);    // seed =1   or 2
        meth = new Method(mehtodNumber);

        if (mehtodNumber == 1) {
            meth.run(seed, mehtodNumber);
            printServices();
            //  printPlacementResult();
        }
        if (mehtodNumber == 2) {
            CloudOnly cloudOnly = new CloudOnly(mehtodNumber);
            meth.cloudResourceWastage();
            meth.countNoOfServiceInEachServer();
            meth.cloudResourceUtilization();
        }
        if (mehtodNumber == 3) {
            Edgeward1 randomPlacement = new Edgeward1(seed);
            meth.cloudResourceWastage();
            meth.countNoOfServiceInEachServer();
            meth.fogResourceWastage();
            meth.countNoOfServiceInEachFog();
            meth.fogPowerConsumption();
            meth.cloudResourceUtilization();
            meth.fogResourceUtilization();
            printServices();

        } else if (mehtodNumber == 4) {
            RandomPlacementMethod randomPlacement = new RandomPlacementMethod(seed);
            meth.cloudResourceWastage();
            meth.countNoOfServiceInEachServer();
            meth.fogResourceWastage();
            meth.countNoOfServiceInEachFog();
            meth.fogPowerConsumption();
            meth.cloudResourceUtilization();
            meth.fogResourceUtilization();
            printServices();

        } else if (mehtodNumber == 5) {
            Paper1_For_Evaluation paper1_for_evaluation = new Paper1_For_Evaluation(seed);
            meth.cloudResourceWastage();
            meth.countNoOfServiceInEachServer();
            meth.fogResourceWastage();   // 
            meth.countNoOfServiceInEachFog();  //
            meth.fogPowerConsumption();
            meth.cloudResourceUtilization();
            meth.fogResourceUtilization();
        }
    }

    public void generateService(int seed, int methNumber) {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter noOf Services: ");
        int noOfServices; //= in.nextInt();
        if (Variables.servicePlacementCheck == true) {
            // noOfServices = Variables.noOfServices; //in.nextInt();
           // noOfServices = in.nextInt();
        } else {
           // noOfServices = in.nextInt(); //400; //100; //5; //10 //60;
        }

        noOfServices = Variables.NoOfService ;
        Variables.service_list1 = random_gen.randomGeneration(seed, noOfServices, Read_Dataset.services);

        System.out.println("Enter Percentage Number Of Critical Services >>> ex Critical = %20 of Services  ");
        Variables.percentage_Number_Of_Critical_Services = 30;//in.nextInt();

        for (int i = 0; i < Variables.service_list1.size(); i++) {
            ArrayList l = Variables.service_list1.get(i);
            Variables.service_list.add((ArrayList) l.clone());   // bamjor addy dakayn bo away agar la dwatrda bmanawet yak index bgoren keshaman bo drwstnabet w hamw aw datayana nagoret la indexakany trda  // ex: wa daman nawa har servicek bcheta cloud debet cpu ramy 10 awandabwte boya law katada ka awservice cpu ramy bo zyad dakayn ba nachet  hame jora serviceakany lamjora bgoret
        }
        if (methNumber != 5) {  // balam bo methody 5 har lanaw classakay xoida addy deadline dakayn in shaa allah
            addingServicesDeadline(seed, methNumber);
        }

        System.out.println("Services List: " + Variables.service_list.size());
    }

    public void addingServicesDeadline(int seed, int methNumber) {

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

    public void printServices() {
        for (int i = 0; i < Variables.S_cri.size(); i++) {
            System.out.println("critical service " + i + ":  " + Variables.S_cri.get(i));
        }
//        for (int i = 0; i < Variables.S_real_time.size(); i++) {
//            System.out.println("real-time service " + i + ":  " + Variables.S_real_time.get(i));
//        }
        for (int i = 0; i < Variables.S_normal.size(); i++) {
            System.out.println("normal service " + i + ":  " + Variables.S_normal.get(i));
        }

        for (int i = 0; i < Variables.service_list.size(); i++) {
            System.out.println("service " + i + ":  " + Variables.service_list.get(i));

        }
    }

    public void printPlacementResult() {
        System.out.println("\n\n");
        for (int i = 0; i < Variables.printMessage.size(); i++) {
            System.out.println(Variables.printMessage.get(i));
        }
    }

}
