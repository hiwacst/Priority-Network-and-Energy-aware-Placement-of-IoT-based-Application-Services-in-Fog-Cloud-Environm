/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import Datasets.Read_Dataset;
import Utilities.Parameters;
import Utilities.Variables;
import java.text.DecimalFormat;
import java.util.ArrayList;     //  C2 fogIndex: Infini power X_sc
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.objects.Global;
import static pamed_implementation.Fog_Topology.fog_numbers;

/**
 *
 * @author hiwa_cst
 */
public class Method {

    int all_seed = 0;
    ArrayList<ArrayList> list1;  // = new ArrayList<ArrayList>();
    MDRM_Algo mdrm_algo;
    DecimalFormat formatter = new DecimalFormat("#0.00");
    int mehtodNumber;
  
    public Method(int mehtodNumber) {
        all_seed = 0;
        list1 = new ArrayList<ArrayList>();
        this.mehtodNumber = mehtodNumber;
       // Variables.service_list
    }

    public void run(int seed, int mehtodNumber) {
        classifyServices(seed);
        PAMED_Algo(seed, mehtodNumber);
        // printResultColony();
    }

    /*
    F = Variables.fog_list_c1  or  Variables.fog_list_c2
    C = Variables.cloud_server_list
     */
    public void PAMED_Algo(int seed, int mehtodNumber) {

        ArrayList indexOfAdapdableFogDevices;
        ArrayList delay = new ArrayList();
        boolean checkColony1 = false, checkColony2 = false, mdrmCheck = false;

        ////   CRITICAL Service Placement 
        for (int i = 0; i < Variables.S_cri.size(); i++) {
            checkColony1 = false;
            checkColony2 = false;
            int R_s_cpu = Integer.parseInt(Variables.S_cri.get(i).get(0).toString());
            int R_s_mem = Integer.parseInt(Variables.S_cri.get(i).get(1).toString());
            indexOfAdapdableFogDevices = new ArrayList();

            ///  Check every fog device in Colony1 for placement every service   kate j=0 wata broker boya wa broker tanha bo managment 
            for (int j = 1; j < Variables.fog_list_c1.size(); j++) {

                int F_f_cpu = Integer.parseInt(Variables.fog_list_c1.get(j).get(0).toString());
                int F_f_mem = Integer.parseInt(Variables.fog_list_c1.get(j).get(1).toString());
                if (R_s_cpu <= F_f_cpu && R_s_mem <= F_f_mem) {
                    indexOfAdapdableFogDevices.add(j);   //  = L in Algo
                    checkColony1 = true;
                }
            }

            if (checkColony1 == true) {
                criticalServicPlacement("colony1", i, Variables.S_cri, indexOfAdapdableFogDevices);   // service i lasar yakek lamana dabne indexOfAdapdableFogDevices 
                Variables.printMessage.add("critical service " + i + " placement on colony1");
            } //// check fog device in Colony2 for placement every service
            else {

                if (mehtodNumber != 3) {  // wata agar edgewarman west bam colony 2 bakarnaheneret 
                    // JOptionPane.showMessageDialog(null, "wooo");
                    for (int j = 1; j < Variables.fog_list_c2.size(); j++) {                    //  kate j=0 wata broker boya wa broker tanha bo managment 
                        int F_f_cpu = Integer.parseInt(Variables.fog_list_c2.get(j).get(0).toString());
                        int F_f_mem = Integer.parseInt(Variables.fog_list_c2.get(j).get(1).toString());
                        if (R_s_cpu <= F_f_cpu && R_s_mem <= F_f_mem) {
                            indexOfAdapdableFogDevices.add(j);   //  = L in Algo
                            checkColony2 = true;
                        }
                    }
                }
            }
            if (checkColony2 == true) {
                criticalServicPlacement("colony2", i, Variables.S_cri, indexOfAdapdableFogDevices);   // service i lasar yakek lamana dabne indexOfAdapdableFogDevices 
                Variables.printMessage.add("critical service " + i + " placement on colony2");

            }

            //// if this service do not placment on colony1 and colony, then send to cloud and 
            // check cloud server for placement every service
            if (checkColony1 == false && checkColony2 == false) {

                //   if (mehtodNumber == 1) {    // wata ba cloud topolgy habet
                //   JOptionPane.showMessageDialog(null, "1: " + Variables.S_cri.get(i) + ":   " + i);
                int serviceCPU = Integer.parseInt(Variables.S_cri.get(i).get(0).toString());
                int serviceMem = Integer.parseInt(Variables.S_cri.get(i).get(1).toString());
                //  JOptionPane.showMessageDialog(null, "11:  "+Variables.multiplyServiceCapacityInCloud+"\n"+
                //     serviceCPU);
                serviceCPU = Variables.multiplyServiceCapacityInCloud * serviceCPU;   // 5 * serviceCPU 
                serviceMem = Variables.multiplyServiceCapacityInCloud * serviceMem;

                //  JOptionPane.showMessageDialog(null, "22:  "+Variables.multiplyServiceCapacityInCloud+"\n"+
                //  serviceCPU);
                Variables.S_cri.get(i).set(0, serviceCPU);
                Variables.S_cri.get(i).set(1, serviceMem);
                Variables.S_cri.clone();
                //   JOptionPane.showMessageDialog(null, "2: " + Variables.S_cri.get(i) + ":   " + i);

                mdrm_algo = new MDRM_Algo("critical", i, Variables.S_cri, Variables.cloud_server_list);
                mdrmCheck = true;
                //  JOptionPane.showMessageDialog(null, "Called mrdm in Critical");
                // call MDRM algo                   
                //  } else if (mehtodNumber == 2) {   // wata ba cloud topology tanha yak nodbet
                //     Variables.printMessage.add("critical service " + i + " placement on cloud");
                // }
            }
        }

        ////   Real_Time and Normal Service Placement 
      //  for (int i = 0; i < Variables.S_all.size(); i++) {
        for (int i = 0; i < Variables.S_normal.size(); i++) {
            checkColony1 = false;
            checkColony2 = false;

            int R_s_cpu = Integer.parseInt(Variables.S_normal.get(i).get(0).toString());
           // int R_s_cpu = Integer.parseInt(Variables.S_all.get(i).get(0).toString());
           // int R_s_mem = Integer.parseInt(Variables.S_all.get(i).get(1).toString());
            int R_s_mem = Integer.parseInt(Variables.S_normal.get(i).get(1).toString());
            indexOfAdapdableFogDevices = new ArrayList();

            ///  Check every fog device in Colony1 for placement every service
            for (int j = 1; j < Variables.fog_list_c1.size(); j++) {

                int F_f_cpu = Integer.parseInt(Variables.fog_list_c1.get(j).get(0).toString());
                int F_f_mem = Integer.parseInt(Variables.fog_list_c1.get(j).get(1).toString());
                if (R_s_cpu <= F_f_cpu && R_s_mem <= F_f_mem) {
                    indexOfAdapdableFogDevices.add(j);   //  = L in Algo
                    checkColony1 = true;
                }
            }

            if (checkColony1 == true) {
//                otherServicPlacement(seed, "colony1", i, Variables.S_all, indexOfAdapdableFogDevices);   // service i lasar yakek lamana dabne indexOfAdapdableFogDevices 
                otherServicPlacement(seed, "colony1", i, Variables.S_normal, indexOfAdapdableFogDevices);   // service i lasar yakek lamana dabne indexOfAdapdableFogDevices 
                Variables.printMessage.add("other service " + i + " placement on colony1");
            } //// check fog device in Colony2 for placement every service
            else {

                if (mehtodNumber != 3) {  // wata agar edgewarman west bam colony 2 bakarnaheneret 
                    // JOptionPane.showMessageDialog(null, "wooo");

                    for (int j = 1; j < Variables.fog_list_c2.size(); j++) {
                        int F_f_cpu = Integer.parseInt(Variables.fog_list_c2.get(j).get(0).toString());
                        int F_f_mem = Integer.parseInt(Variables.fog_list_c2.get(j).get(1).toString());
                        if (R_s_cpu <= F_f_cpu && R_s_mem <= F_f_mem) {
                            indexOfAdapdableFogDevices.add(j);   //  = L in Algo
                            checkColony2 = true;
                        }
                    }
                }
            }
            if (checkColony2 == true) {
//                otherServicPlacement(seed, "colony2", i, Variables.S_all, indexOfAdapdableFogDevices);   // service i lasar yakek lamana dabne indexOfAdapdableFogDevices 
                otherServicPlacement(seed, "colony2", i, Variables.S_normal, indexOfAdapdableFogDevices);   // service i lasar yakek lamana dabne indexOfAdapdableFogDevices 
                Variables.printMessage.add("other service " + i + " placement on colony2");

            }

            //// if this service do not placment on colony1 and colony, then send to cloud and 
            // check cloud server for placement every service
            if (checkColony1 == false && checkColony2 == false) {

                //   if (mehtodNumber == 1) {    // wata ba cloud topolgy habet
                //    JOptionPane.showMessageDialog(null, "11: " + Variables.S_all.get(i) + ":   " + i);
             //   int serviceCPU = Integer.parseInt(Variables.S_all.get(i).get(0).toString());
                int serviceCPU = Integer.parseInt(Variables.S_normal.get(i).get(0).toString());
             //   int serviceMem = Integer.parseInt(Variables.S_all.get(i).get(1).toString());
                int serviceMem = Integer.parseInt(Variables.S_normal.get(i).get(1).toString());
                serviceCPU = Variables.multiplyServiceCapacityInCloud * serviceCPU;   // 5 * serviceCPU 
                serviceMem = Variables.multiplyServiceCapacityInCloud * serviceMem;
//                Variables.S_all.get(i).set(0, serviceCPU);
//                Variables.S_all.get(i).set(1, serviceMem);
//                Variables.S_all.clone();   
                Variables.S_normal.get(i).set(0, serviceCPU);
                Variables.S_normal.get(i).set(1, serviceMem);
                Variables.S_normal.clone();
                //   JOptionPane.showMessageDialog(null, "22: " + Variables.S_all.get(i) + ":   " + i);

             //   mdrm_algo = new MDRM_Algo("other", i, Variables.S_all, Variables.cloud_server_list);
                mdrm_algo = new MDRM_Algo("other", i, Variables.S_normal, Variables.cloud_server_list);
                mdrmCheck = true;
                // JOptionPane.showMessageDialog(null, "Called mrdm in other");

                // call MDRM algo 
                //   } else if (mehtodNumber == 2) {   // wata ba cloud topology tanha yak nodbet
                //      Variables.printMessage.add("other service " + i + " placement on cloud");
                //  }
            }
        }

//        if (mehtodNumber == 1 && mdrmCheck == true) {   // wata agar cloud topology habw wa servicesh chwbeta cloud(wata bangy MDRM algo krabw wa object am classa initialize bwbw) amja ba amana rwbdat
        if (mdrmCheck == true) {   // wata agar cloud topology habw wa servicesh chwbeta cloud(wata bangy MDRM algo krabw wa object am classa initialize bwbw) amja ba amana rwbdat
            mdrm_algo.C_MEF();
            cloudResourceWastage();
            countNoOfServiceInEachServer();
            cloudResourceUtilization();
        }
        fogResourceWastage();
        countNoOfServiceInEachFog();
        fogPowerConsumption();
        fogResourceUtilization();

    }

    /// dozinaway bashtriny naw listaka pashn nardny bo methody deployService
    public void criticalServicPlacement(String colony, int serviceIndex, ArrayList<ArrayList> serviceList, ArrayList indexOfAdapdableFogDevices) {

        // list shortest pathy newan broker w fogy naw ama indexOfAdapdableFogDevice  dadozetawa lagal delay w nody newan broker curretn fog node      
        ArrayList<ArrayList> allDelayAndShortestPath = new ArrayList<ArrayList>();

        for (int j = 0; j < indexOfAdapdableFogDevices.size(); j++) {

            //dozinaway shortest pathy newan broker bo haryaka la foga halbzherdrawakan
            ArrayList shortestPath;
            if (colony.equalsIgnoreCase("colony1")) {
                shortestPath = Fog_Topology.sendToDijkestra(Variables.edges_list_c1, 0, Integer.parseInt(indexOfAdapdableFogDevices.get(j).toString()));

            } else {
                shortestPath = Fog_Topology.sendToDijkestra(Variables.edges_list_c2, 0, Integer.parseInt(indexOfAdapdableFogDevices.get(j).toString()));
            }
            allDelayAndShortestPath.add(shortestPath);
        }
        /*
        la hangaway sarawada shortest path bo hamw foga halbzherdrawakan dozrawatawa
        lerada aw shortest patha haldabzheren ka kamtren delay haya
         */
        ArrayList adaptableFogWithMinDelay = minDelay("critical", colony, allDelayAndShortestPath); // = d in algo // dozinaway awayan ka kamtren delay haya lagal broker w delay w hamw pathakashy tyadaya am lista 
        deployServices(serviceIndex, adaptableFogWithMinDelay);  // service i lasar am fog device dadaneyn ka hamw zanyaray pathakashy lagal am fog devicea
        int fogIndex = Integer.parseInt(adaptableFogWithMinDelay.get(1).toString());
        updateResourceCapacity(serviceIndex, serviceList, fogIndex, colony);
    }

    public ArrayList minDelay(String serviceType, String colony, ArrayList<ArrayList> listOfShortestPath) {
        int indexOfMinDelayFog = 0;
        int x = 0, y = 1;
        for (int i = 0; i < listOfShortestPath.size() - 1; i++) {
            int delay1 = Integer.parseInt(listOfShortestPath.get(x).get(2).toString());
            int delay2 = Integer.parseInt(listOfShortestPath.get(y).get(2).toString());

            if (delay1 <= delay2) {
                indexOfMinDelayFog = x;
                y++;
            } else {
                indexOfMinDelayFog = y;
                x = y + 1;
            }
        }
        ArrayList adaptableFogWithMinDelay = listOfShortestPath.get(indexOfMinDelayFog);
        /* agar am servicea chw bo colony2 awa dabet delay newan b1 bo sdn w sdn bo 
        b2 zayd bkayn bo dalay newan b2 bo bashtren fog node lanaw colony2 da
         */

        int Xvp = 1;
        int serviceIndex = Integer.parseInt(adaptableFogWithMinDelay.get(0).toString());
        int fogIndex = Integer.parseInt(adaptableFogWithMinDelay.get(1).toString());
        int serviceCPU = Integer.parseInt(Variables.S_cri.get(serviceIndex).get(0).toString());

        if (colony.equalsIgnoreCase("colony2")) {
            // int delayB1ToB2 = Variables.brokerc1_to_brokerc2_delay;
            // int colony2Dealy = Integer.parseInt(adaptableFogWithMinDelay.get(2).toString());
            // int delay = delayB1ToB2 + colony2Dealy;
            // adaptableFogWithMinDelay.set(2, delay);
            /*
            la criticalda harchanda lasar asasy daely bashtren fog device 
            dyaridakayn balam labar away service placementa awa pewestamn ba 
            delay nya boya le shweny delay aw devica ka durimankrdwa aw enrgya 
            dadaneyn ka am device pewestyaty bo danany am service lasary 
             */
//            int fogCPU = Integer.parseInt(Variables.fog_list_c2.get(fogIndex).get(0).toString());
            int fogCPU = Integer.parseInt(Variables.fog_list_backup_c2.get(fogIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) fogCPU;   // besheky yasay power la Eq:7
            //   power = Double.parseDouble(String.format("%.2g%n", power));
            adaptableFogWithMinDelay.set(2, power);
            adaptableFogWithMinDelay.add("colony2");
            adaptableFogWithMinDelay.add("DelayReduction");
            adaptableFogWithMinDelay.add(serviceType);
            //   Variables.fog_list_c1.get(fogIndex).add("hiwa");
        } else {
//            int fogCPU = Integer.parseInt(Variables.fog_list_c1.get(fogIndex).get(0).toString());
            int fogCPU = Integer.parseInt(Variables.fog_list_backup_c1.get(fogIndex).get(0).toString());
            double power = (Xvp * (double) serviceCPU) / (double) fogCPU;   // besheky yasay power la Eq:7
            //  power = Double.parseDouble(String.format("%.2g%n", power));
            adaptableFogWithMinDelay.set(2, power);
            adaptableFogWithMinDelay.add("colony1");  // bo away bzanen aw foga la colony1a yan colony2a
            adaptableFogWithMinDelay.add("DelayReduction");
            adaptableFogWithMinDelay.add(serviceType);

        }
        return adaptableFogWithMinDelay;
    }

    public void deployServices(int serviceIndex, ArrayList adaptableFogWithMinDelayOrEnergy) {
        /*
        adaptableFogWithMinDelay contains the following
        index 0 = broker or f0
        index 1 = fog node
        index 2 = delay for placement this service on this fog node
                  balam am delayaman gorewa bo energy danany aw servica lasar aw devica
        index 3 to before last = path node from broker to current fog node
        index last = colony name (colony1 or colony2),
        also in here i repalce the broker node bt service and that is means
        this service placment on this fog by this delay through this path by Dijkestra algorithm
         */
        adaptableFogWithMinDelayOrEnergy.set(0, serviceIndex);
        Variables.X_sf.add(adaptableFogWithMinDelayOrEnergy);
    }

    public void updateResourceCapacity(int serviceIndex, ArrayList<ArrayList> serviceList, int fogIndex, String colony) {

        int fogCPU = 0, fogMem = 0;
        if (colony.equalsIgnoreCase("colony1")) {
            fogCPU = Integer.parseInt(Variables.fog_list_c1.get(fogIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.fog_list_c1.get(fogIndex).get(1).toString());
        } else {
            fogCPU = Integer.parseInt(Variables.fog_list_c2.get(fogIndex).get(0).toString());
            fogMem = Integer.parseInt(Variables.fog_list_c2.get(fogIndex).get(1).toString());
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (i == serviceIndex) {
                int serviceCPU = Integer.parseInt(serviceList.get(i).get(0).toString());
                int serviceMem = Integer.parseInt(serviceList.get(i).get(1).toString());
                fogCPU = fogCPU - serviceCPU;      // Eq-4
                fogMem = fogMem - serviceMem;     //  Eq-4
            }
        }

        if (colony.equalsIgnoreCase("colony1")) {
            Variables.fog_list_c1.get(fogIndex).set(0, fogCPU);
            Variables.fog_list_c1.get(fogIndex).set(1, fogMem);
            //  Variables.fog_list_c1.get(fogIndex).add("C1 fogIndex: " + fogIndex);
            list1.add(Variables.fog_list_c1.get(fogIndex));

        } else {
            Variables.fog_list_c2.get(fogIndex).set(0, fogCPU);
            Variables.fog_list_c2.get(fogIndex).set(1, fogMem);
            //  Variables.fog_list_c2.get(fogIndex).add("C2 fogIndex: " + fogIndex);
            list1.add(Variables.fog_list_c2.get(fogIndex));

        }
    }

    /// dozinaway bashtriny naw listaka pashn nardny bo methody deployService
    public void otherServicPlacement(int seed, String colony, int serviceIndex, ArrayList<ArrayList> serviceList, ArrayList indexOfAdaptableFogDevices) {

        //dozinaway energy  bo danany am service (serviceIndex) lasar hamw fogy naw ama indexOfAdaptableFogDevices 
        ArrayList<ArrayList> listOfAllFogWithEnergy = new ArrayList<ArrayList>();

        for (int j = 0; j < indexOfAdaptableFogDevices.size(); j++) {
            ArrayList energy = new ArrayList();
            energy.add(0);    //   wata la node 0 broker bo aw fog noday xwarawa chanda energy dawt 
            energy.add(indexOfAdaptableFogDevices.get(j));
            int fogCPU = 0;
            int Xvp = 1;
            if (colony.equalsIgnoreCase("colony1")) {
                // fogCPU = Integer.parseInt(Variables.fog_list_c1.get(j).get(0).toString()); //
                fogCPU = Integer.parseInt(Variables.fog_list_backup_c1.get(j).get(0).toString()); //
            } else {
                //    fogCPU = Integer.parseInt(Variables.fog_list_c2.get(j).get(0).toString()); //
                fogCPU = Integer.parseInt(Variables.fog_list_backup_c2.get(j).get(0).toString()); //
            }
            int serviceCPU = Integer.parseInt(serviceList.get(serviceIndex).get(0).toString()); //
            double power = (Xvp * (double) serviceCPU / (double) fogCPU);   // besheky yasay power la Eq:7
            //double power = F_MEF_Algo(seed);
            //  power = Double.parseDouble(String.format("%.2g%n", power));
            // power = Double.parseDouble(formatter.format(power));           
            //Variables.cloud_server_list.get(i).add(formatter.format(resourceWastage));

            energy.add(power);
            listOfAllFogWithEnergy.add(energy);
        }

        /*
        la hangaway sarawada energy bo hamw foga halbzherdrawakan dozrawatawa
        lerada aw fog nodea  haldabzheren ka kamtren energy haya
         */
        ArrayList adaptableFogWithMinEnergy = minEnergy(colony, listOfAllFogWithEnergy); // = d in algo // dozinaway awayan ka kamtren delay haya lagal broker w delay w hamw pathakashy tyadaya am lista 

        //  dozinaway shortest pathy newan broker bo aw fog devicay ka kamtrin energy haya  adaptableFogWithMinEnergy
        ArrayList shortestPathWithMinEnergy;
        if (colony.equalsIgnoreCase("colony1")) {
            /*
            shortestPathWithMinEnergy path amanay tyadaya
            index 0: broker f0
            index 1: fog index
            index 2: dealy
            index 3 or >3 : path from broker to current fog node,
            laxwarawa aman dakay
            1- goreny delay ba energy 
            2- la kotaida colony (colony1 or colony2) zyad dakayn
             */
            shortestPathWithMinEnergy = Fog_Topology.sendToDijkestra(Variables.edges_list_c1, 0, Integer.parseInt(adaptableFogWithMinEnergy.get(1).toString()));   //  amanay tyadaya source, dist, (delay or energy)
            shortestPathWithMinEnergy.set(2, adaptableFogWithMinEnergy.get(2));
            shortestPathWithMinEnergy.add("colony1");
            shortestPathWithMinEnergy.add("EnergyConsumption");
            shortestPathWithMinEnergy.add("other");

        } else {
            shortestPathWithMinEnergy = Fog_Topology.sendToDijkestra(Variables.edges_list_c2, 0, Integer.parseInt(adaptableFogWithMinEnergy.get(1).toString()));
            shortestPathWithMinEnergy.set(2, adaptableFogWithMinEnergy.get(2));
            shortestPathWithMinEnergy.add("colony2");
            shortestPathWithMinEnergy.add("EnergyConsumption");
            shortestPathWithMinEnergy.add("other");

        }

        deployServices(serviceIndex, shortestPathWithMinEnergy);  // service i lasar am fog device dadaneyn ka hamw zanyaray pathakashy lagal am fog devicea
        int fogIndex = Integer.parseInt(shortestPathWithMinEnergy.get(1).toString());
        updateResourceCapacity(serviceIndex, serviceList, fogIndex, colony);
    }

    public ArrayList minEnergy(String colony, ArrayList<ArrayList> listOfAllFogWithEnergy) {
        int indexOfMinEnergyFog = 0;
        int x = 0, y = 1;
        for (int i = 0; i < listOfAllFogWithEnergy.size() - 1; i++) {

            double energy1 = Double.parseDouble(listOfAllFogWithEnergy.get(x).get(2).toString());
            double energy2 = Double.parseDouble(listOfAllFogWithEnergy.get(y).get(2).toString());

            if (energy1 <= energy2) {
                indexOfMinEnergyFog = x;
                y++;
            } else {
                indexOfMinEnergyFog = y;
                x = y + 1;
            }
        }
        ArrayList adaptableFogWithMinEnergy = listOfAllFogWithEnergy.get(indexOfMinEnergyFog);

        return adaptableFogWithMinEnergy;
    }

    public void classifyServices(int seed) {

        /*
    generating random value betwwen 0 and serviceList size
    if random value <=  1/3 service list >>> this is the critical service and
    put in S_cri list
    other wise >>>>>  real-time and normal services, then put all of them 
    in S_all list
         */
        Random rand;
        // int critical = Variables.service_list.size() / 3;      //  15/3 = 5
        int critical = Variables.number_Of_Critical_Services;
       // int real_time = critical;                          //   5*2  = 10 
        for (int i = 0; i < Variables.service_list.size(); i++) {

            ArrayList service = Variables.service_list.get(i);
            int serviceDeadline = Integer.parseInt(Variables.servicesDeadline.get(i).toString());

            if (serviceDeadline <= Parameters.critical_Service_Deadline_Max) {
                Variables.S_cri.add((ArrayList) service.clone());   // bamjora addy dakayn bo away agar la dwatrda bmanawet yak index bgoren keshaman bo drwstnabet w hamw aw datayana nagoret la indexakany trda  // ex: wa daman nawa har servicek bcheta cloud debet cpu ramy 10 awandabwte boya law katada ka awservice cpu ramy bo zyad dakayn ba nachet  hame jora serviceakany lamjora bgoret
                Variables.criticalServicesDeadline.add(serviceDeadline);
                Variables.criticalServicesDeadline.clone();
            } 
            else {
//                if (Variables.S_real_time.size() < real_time) {
//                    Variables.S_real_time.add((ArrayList) service.clone());
//                    Variables.realAndNormalServicesDeadline.add(serviceDeadline);
//                    Variables.realAndNormalServicesDeadline.clone();                            
//                } 
//                else {
                    Variables.S_normal.add((ArrayList) service.clone());
                    Variables.realAndNormalServicesDeadline.add(serviceDeadline);
                    Variables.realAndNormalServicesDeadline.clone();                    
              //  }
            }


//                if (serviceDeadline > Parameters.critical_Service_Deadline_Max && i > critical && i <= real_time) {
//                Variables.S_real_time.add((ArrayList) service.clone());
//            } else if (i > real_time) {
//                Variables.S_normal.add((ArrayList) service.clone());
            // }  
////
//            ArrayList service = Variables.service_list.get(i);
////
//            if (i <= critical) {
//                Variables.S_cri.add((ArrayList) service.clone());   // bamjor addy dakayn bo away agar la dwatrda bmanawet yak index bgoren keshaman bo drwstnabet w hamw aw datayana nagoret la indexakany trda  // ex: wa daman nawa har servicek bcheta cloud debet cpu ramy 10 awandabwte boya law katada ka awservice cpu ramy bo zyad dakayn ba nachet  hame jora serviceakany lamjora bgoret
//            } else if (i > critical && i <= real_time) {
//                Variables.S_real_time.add((ArrayList) service.clone());
//            } else if (i > real_time) {
//                Variables.S_normal.add((ArrayList) service.clone());
//            }
            /*
            if (seed == Variables.seed) {
                rand = new Random();
            } else {
                rand = new Random(i);
            }

            int randomValue = rand.nextInt(Variables.service_list.size() - 1);
            int indexOfServiceInReadedDataset = 0;

           // for (int j = 0; j < Read_Dataset.services.size(); j++) {
            for (int j = 0; j < Read_Dataset.servicesOnlyForOurPropose.size(); j++) {
                if (Variables.service_list.get(i).equals(Read_Dataset.servicesOnlyForOurPropose.get(j))) // indexOfServiceInReadedDataset
                {
                    indexOfServiceInReadedDataset = j;
                }
            }

         //   if (randomValue <= critical && indexOfServiceInReadedDataset <= 3) {
            if (randomValue <= critical && indexOfServiceInReadedDataset < 2) {
                ArrayList l = Variables.service_list.get(i);
                Variables.S_cri.add((ArrayList) l.clone());   // bamjor addy dakayn bo away agar la dwatrda bmanawet yak index bgoren keshaman bo drwstnabet w hamw aw datayana nagoret la indexakany trda  // ex: wa daman nawa har servicek bcheta cloud debet cpu ramy 10 awandabwte boya law katada ka awservice cpu ramy bo zyad dakayn ba nachet  hame jora serviceakany lamjora bgoret
        //    } else if (randomValue > critical && randomValue <= real_time && (indexOfServiceInReadedDataset > 0 && indexOfServiceInReadedDataset <= 4)) {
            } else if (randomValue > critical && randomValue <= real_time && (indexOfServiceInReadedDataset > 0 && indexOfServiceInReadedDataset < 3)) {
                ArrayList l = Variables.service_list.get(i);
                Variables.S_real_time.add((ArrayList) l.clone());
           // } else if (indexOfServiceInReadedDataset > 1) {
            } else if (indexOfServiceInReadedDataset > 0) {
                ArrayList l = Variables.service_list.get(i);
                Variables.S_normal.add((ArrayList) l.clone());
            } //  am dwhalatay xwarawa dwbara dakaynawa bo away service haya law 3 marjay sarawa ladadat w zhmaray serviceakan kamdakan lanaw listy S_cri , S_real w S_norm
         //   else if (indexOfServiceInReadedDataset <= 3) {
            else if (indexOfServiceInReadedDataset < 2) {
                ArrayList l = Variables.service_list.get(i);
                Variables.S_cri.add((ArrayList) l.clone());   // bamjor addy dakayn bo away agar la dwatrda bmanawet yak index bgoren keshaman bo drwstnabet w hamw aw datayana nagoret la indexakany trda  // ex: wa daman nawa har servicek bcheta cloud debet cpu ramy 10 awandabwte boya law katada ka awservice cpu ramy bo zyad dakayn ba nachet  hame jora serviceakany lamjora bgoret
            } else if (randomValue > critical && randomValue <= real_time) {
                ArrayList l = Variables.service_list.get(i);
                Variables.S_real_time.add((ArrayList) l.clone());
            }
             */
        }
        
                //   JOptionPane.showMessageDialog(null, Variables.S_cri.size()+"\n"+Variables.S_normal.size());
 
//                    for (int j = 0; j < Variables.S_cri.size(); j++) {
//                System.out.println(Variables.S_cri.get(j)+"\t\t\t"+Variables.criticalServicesDeadline.get(j));
//                
//            }
//                            JOptionPane.showMessageDialog(null, "Stop");

//        for (int j = 0; j < Variables.S_cri.size(); j++) {
//            System.out.println(Variables.S_cri.get(j) + "\t\t" + Variables.service_list.get(j));
//        }
        //  JOptionPane.showMessageDialog(null, "jkjj HIwa");
       // joinRealAndNormalService();
    }

//    public void joinRealAndNormalService() {
//        for (int i = 0; i < Variables.S_real_time.size(); i++) {
//            Variables.S_all.add(Variables.S_real_time.get(i));
//        }
//
//        for (int i = 0; i < Variables.S_normal.size(); i++) {
//            Variables.S_all.add(Variables.S_normal.get(i));
//        }
//
////        for (int i = 0; i < Variables.S_all.size(); i++) {
////            
////            System.out.println(i+") .  :  "+Variables.S_all.get(i));
////        }
////        
////        JOptionPane.showMessageDialog(null, "HHHHHHHHHH");
//    }

    public void cloudResourceWastage() {

//                                        System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
//            
//            System.out.println("\t\t" + Variables.cloud_server_list.get(i));
//        }
        // JOptionPane.showMessageDialog(null, "stop");        
        double serverCPU1 = 0, serverMem1 = 0, serverCPU2 = 0, serverMem2 = 0;
        int currentServerIndex = 0;
        int index11 = 0;
        for (int i = 0; i < Variables.cloud_server_list1.size(); i++) {

            if (mehtodNumber == 5) {
                serverCPU1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(1).toString());
                serverMem1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(2).toString());

                for (int ii = 0; ii < Variables.cloud_server_list.size(); ii++) {
                    currentServerIndex = Integer.parseInt(Variables.cloud_server_list.get(ii).get(0).toString());
                    if (currentServerIndex == i) {
                        index11 = ii;
                        serverCPU2 = Double.parseDouble(Variables.cloud_server_list.get(ii).get(1).toString());
                        serverMem2 = Double.parseDouble(Variables.cloud_server_list.get(ii).get(2).toString());
                        break;
                    }
                }

            } else {
                serverCPU1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(0).toString());
                serverMem1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(1).toString());
                serverCPU2 = Double.parseDouble(Variables.cloud_server_list.get(i).get(0).toString());
                serverMem2 = Double.parseDouble(Variables.cloud_server_list.get(i).get(1).toString());
            }

            if (serverCPU1 == serverCPU2) {
//                if (mehtodNumber == 5) {
//                    Variables.cloud_server_list.get(index11).add("Powerrr Off");  // ba dwbaranabetawa chwnka la powerda ama dyarikrawa
//                } else {
//                    Variables.cloud_server_list.get(i).add("Powerrr Off");  // ba dwbaranabetawa chwnka la powerda ama dyarikrawa
//                }
            } else {
                double serviceCPU = serverCPU1 - serverCPU2; // wat cpuy hamw servicakany sar har serverek = cpu of backup server - cpu of used server 
                double serviceMem = serverMem1 - serverMem2;

                double e = 0.0001;
                int yp = 1;
                double cpu = (serverCPU1 - serviceCPU) / serverCPU1;
                double mem = (serverMem1 - serviceMem) / serverMem1;
                double over = Double.sum(Math.abs(cpu - mem), e);
                double under = Double.sum((serviceCPU / serverCPU1), (serviceMem / serverMem1));
                double resourceWastage = yp * over / under;

                if (mehtodNumber == 5) {
                    int index = 0;
                    for (int ii = 0; ii < Variables.cloud_server_list.size(); ii++) {
                        int currentServerIndex1 = Integer.parseInt(Variables.cloud_server_list.get(ii).get(0).toString());
                        if (currentServerIndex1 == i) {
                            index = ii;
                            break;
                        }
                    }
                    Variables.cloud_server_list.get(index).add(formatter.format(resourceWastage));
                } else {
                    Variables.cloud_server_list.get(i).add(formatter.format(resourceWastage));
                }

            }
        }
//                                              System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
//            
//            System.out.println("\t\t" + Variables.cloud_server_list.get(i));
//        }
//        JOptionPane.showMessageDialog(null, "stop"); 
    }

    public void fogResourceWastage() {
        double fogCPU1 = 0, fogMem1 = 0, fogCPU2 = 0, fogMem2 = 0;

        if (this.mehtodNumber != 2) {      // bo kateka ka tanha cloud man haya w fog har nya ta blayn ba broker dyaribkayn
            Variables.fog_list_c1.get(0).add("Broker");
        }
        for (int j = 1; j < Variables.fog_list_backup_c1.size(); j++) {
            int index11 = 0;

            if (mehtodNumber == 5) {

                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c1.get(j).get(1).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c1.get(j).get(2).toString());
                for (int ii = 0; ii < Variables.fog_list_c1.size(); ii++) {
                    int currentServerIndex = Integer.parseInt(Variables.fog_list_c1.get(ii).get(0).toString());
                    if (currentServerIndex == j) {
                        index11 = ii;
                        fogCPU2 = Double.parseDouble(Variables.fog_list_c1.get(ii).get(1).toString());
                        fogMem2 = Double.parseDouble(Variables.fog_list_c1.get(ii).get(2).toString());
                        break;
                    }
                }
            } else {
                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c1.get(j).get(0).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c1.get(j).get(1).toString());
                fogCPU2 = Double.parseDouble(Variables.fog_list_c1.get(j).get(0).toString());
                fogMem2 = Double.parseDouble(Variables.fog_list_c1.get(j).get(1).toString());
            }

            if (fogCPU1 == fogCPU2 && fogMem1 == fogMem2) {
//                JOptionPane.showMessageDialog(null, j + "\t"
//                        + fogCPU1 + "\t" + fogMem1 + "\t" + fogCPU2 + "\t" + fogMem2);
                if (mehtodNumber == 5) {
                    Variables.fog_list_c1.get(index11).add("Power Off");
                } else {
                    Variables.fog_list_c1.get(j).add("Power Off");
                }

            } else {
                double serviceCPU = fogCPU1 - fogCPU2;
                double serviceMem = fogMem1 - fogMem2;

                double e = 0.0001;
                int yp = 1;

                double cpu = (fogCPU1 - serviceCPU) / fogCPU1;
                double mem = (fogMem1 - serviceMem) / fogMem1;

                double over = Double.sum(Math.abs(cpu - mem), e);

                double under = Double.sum((serviceCPU / fogCPU1), (serviceMem / fogMem1));
                double resourceWastage = yp * over / under;

                if (mehtodNumber == 5) {
                    int index = 0;
                    for (int ii = 0; ii < Variables.fog_list_c1.size(); ii++) {
                        int currentServerIndex1 = Integer.parseInt(Variables.fog_list_c1.get(ii).get(0).toString());
                        if (currentServerIndex1 == j) {
                            index = ii;
                            break;
                        }
                    }
                    Variables.fog_list_c1.get(index).add(formatter.format(resourceWastage));
                    // JOptionPane.showMessageDialog(null, "1: "+Variables.fog_list_c1.get(index));
                } else {

                    Variables.fog_list_c1.get(j).add(formatter.format(resourceWastage));
                    //      JOptionPane.showMessageDialog(null, "2: "+Variables.fog_list_c1.get(j));

                }
            }
        }

//        System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.fog_list_c1.size(); i++) {
//
//            System.out.println("\t\t" + Variables.fog_list_c1.get(i));
//        }
        //  JOptionPane.showMessageDialog(null, "stop");

        /*
        
        
         */
        if (this.mehtodNumber != 2) {
            Variables.fog_list_c2.get(0).add("Broker");
        }

        // Colony2
        for (int j = 1; j < Variables.fog_list_backup_c2.size(); j++) {

            if (mehtodNumber == 5) {
                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c2.get(j).get(1).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c2.get(j).get(2).toString());

                for (int ii = 0; ii < Variables.fog_list_c2.size(); ii++) {
                    int currentServerIndex = Integer.parseInt(Variables.fog_list_c2.get(ii).get(0).toString());
                    if (currentServerIndex == j) {
                        fogCPU2 = Double.parseDouble(Variables.fog_list_c2.get(ii).get(1).toString());
                        fogMem2 = Double.parseDouble(Variables.fog_list_c2.get(ii).get(2).toString());
                        break;
                    }
                }
            } else {
                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c2.get(j).get(0).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c2.get(j).get(1).toString());
                fogCPU2 = Double.parseDouble(Variables.fog_list_c2.get(j).get(0).toString());
                fogMem2 = Double.parseDouble(Variables.fog_list_c2.get(j).get(1).toString());
            }
            if (fogCPU1 == fogCPU2) {
                //Variables.fog_list_c2.get(j).add("Power Off");
            } else {
                double serviceCPU = fogCPU1 - fogCPU2;
                double serviceMem = fogMem1 - fogMem2;

                double e = 0.0001;
                int yp = 1;

                double cpu = (fogCPU1 - serviceCPU) / fogCPU1;
                double mem = (fogMem1 - serviceMem) / fogMem1;

                double over = Double.sum(Math.abs(cpu - mem), e);

                double under = Double.sum((serviceCPU / fogCPU1), (serviceMem / fogMem1));
                double resourceWastage = yp * over / under;

                if (mehtodNumber == 5) {
                    int index = 0;
                    for (int ii = 0; ii < Variables.fog_list_c2.size(); ii++) {
                        int currentServerIndex1 = Integer.parseInt(Variables.fog_list_c2.get(ii).get(0).toString());
                        if (currentServerIndex1 == j) {
                            index = ii;
                            break;
                        }
                    }
                    Variables.fog_list_c2.get(index).add(formatter.format(resourceWastage));
                } else {
                    Variables.fog_list_c2.get(j).add(formatter.format(resourceWastage));
                }
            }
        }

    }

    public void countNoOfServiceInEachServer() {
//        
//         System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
//
//            System.out.println("\t\t" + Variables.cloud_server_list.get(i));
//        }

        for (int i = 0; i < Variables.X_sc.size(); i++) {

          //  int serviceIndex = Integer.parseInt(Variables.X_sc.get(i).get(0).toString());
            int serverIndex = Integer.parseInt(Variables.X_sc.get(i).get(1).toString());

            if (mehtodNumber == 5) {
                if (Variables.cloud_server_list.get(serverIndex).size() == 8) {    // agar servicey tr hsabkrawa bo am servera awa ba indexy 6 bxwenetawa w 1y bo zyadbkat w bixatawa haman index
                    int noOfService = Integer.parseInt(Variables.cloud_server_list.get(serverIndex).get(7).toString());
                    noOfService++;
                    Variables.cloud_server_list.get(serverIndex).set(7, noOfService);
                } else {
                    Variables.cloud_server_list.get(serverIndex).add(1);  // wata agar yakam servica heshta awa ba indexy 6 zyadbkat w 1y bo dabnet wata am server 1 servicy lasara

                }
            } else {

                if (Variables.cloud_server_list.get(serverIndex).size() == 7) {    // agar servicey tr hsabkrawa bo am servera awa ba indexy 6 bxwenetawa w 1y bo zyadbkat w bixatawa haman index
                    int noOfService = Integer.parseInt(Variables.cloud_server_list.get(serverIndex).get(6).toString());
                    noOfService++;
                    Variables.cloud_server_list.get(serverIndex).set(6, noOfService);
                } else {
                    Variables.cloud_server_list.get(serverIndex).add(1);  // wata agar yakam servica heshta awa ba indexy 6 zyadbkat w 1y bo dabnet wata am server 1 servicy lasara

                }
            }
        }
//                 System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
//
//            System.out.println("\t\t" + Variables.cloud_server_list.get(i));
//        }
//        JOptionPane.showMessageDialog(null, "stop");
    }

    public void countNoOfServiceInEachFog() {

//        System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.fog_list_c1.size(); i++) {
//
//            System.out.println("\t\t" + Variables.fog_list_c1.get(i));
//        }
        for (int i = 0; i < Variables.X_sf.size(); i++) {

            int size = Variables.X_sf.get(i).size();

            String colony = Variables.X_sf.get(i).get(size - 3).toString();

            int serviceIndex = Integer.parseInt(Variables.X_sf.get(i).get(0).toString());
            int fogIndex = Integer.parseInt(Variables.X_sf.get(i).get(1).toString());
            //  JOptionPane.showMessageDialog(null, colony);
            if (colony.equalsIgnoreCase("colony1")) {

                if (mehtodNumber == 5) {
                    int index = 0;
                    for (int j = 0; j < Variables.fog_list_c1.size(); j++) {
                        int fogIndex2 = Integer.parseInt(Variables.fog_list_c1.get(j).get(0).toString());
                        if (fogIndex == fogIndex2) {
                            index = j;
                        }
                    }
                    if (Variables.fog_list_c1.get(index).size() == 7) {    // agar servicey tr hsabkrawa bo am faga awa ba indexy 6 bxwenetawa w 1y bo zyadbkat w bixatawa haman index
                        int noOfService = Integer.parseInt(Variables.fog_list_c1.get(index).get(6).toString());
                        noOfService++;
                        Variables.fog_list_c1.get(index).set(6, noOfService);
                    } else {
                        Variables.fog_list_c1.get(index).add(1);  // wata agar yakam servica heshta awa ba indexy 6 zyadbkat w 1y bo dabnet wata am foga 1 servicy lasara

                    }

                } else {
                    if (Variables.fog_list_c1.get(fogIndex).size() == 6) {    // agar servicey tr hsabkrawa bo am faga awa ba indexy 6 bxwenetawa w 1y bo zyadbkat w bixatawa haman index
                        int noOfService = Integer.parseInt(Variables.fog_list_c1.get(fogIndex).get(5).toString());
                        noOfService++;
                        Variables.fog_list_c1.get(fogIndex).set(5, noOfService);
                    } else {
                        Variables.fog_list_c1.get(fogIndex).add(1);  // wata agar yakam servica heshta awa ba indexy 6 zyadbkat w 1y bo dabnet wata am foga 1 servicy lasara

                    }
                }
//
//                System.out.println("\n\n\n\n");
//                for (int ii = 0; ii < Variables.fog_list_c1.size(); ii++) {
//
//                    System.out.println("\t\t" + Variables.fog_list_c1.get(ii));
//                }
//                JOptionPane.showMessageDialog(null, "stop");
            } /*
            
            
            
            
             */ // colony2
            else {

                if (mehtodNumber == 5) {

                    int index = 0;
                    for (int j = 0; j < Variables.fog_list_c2.size(); j++) {
                        int fogIndex2 = Integer.parseInt(Variables.fog_list_c2.get(j).get(0).toString());
                        if (fogIndex == fogIndex2) {
                            index = j;
                        }
                    }

                    if (Variables.fog_list_c2.get(index).size() == 7) {    // agar servicey tr hsabkrawa bo am foga awa ba indexy 6 bxwenetawa w 1y bo zyadbkat w bixatawa haman index
                        int noOfService = Integer.parseInt(Variables.fog_list_c2.get(index).get(6).toString());
                        noOfService++;
                        Variables.fog_list_c2.get(index).set(6, noOfService);
                    } else {
                        Variables.fog_list_c2.get(index).add(1);  // wata agar yakam servica heshta awa ba indexy 6 zyadbkat w 1y bo dabnet wata am foga 1 servicy lasara
                    }
                } else {
                    if (Variables.fog_list_c2.get(fogIndex).size() == 6) {    // agar servicey tr hsabkrawa bo am foga awa ba indexy 6 bxwenetawa w 1y bo zyadbkat w bixatawa haman index
                        int noOfService = Integer.parseInt(Variables.fog_list_c2.get(fogIndex).get(5).toString());
                        noOfService++;
                        Variables.fog_list_c2.get(fogIndex).set(5, noOfService);
                    } else {
                        Variables.fog_list_c2.get(fogIndex).add(1);  // wata agar yakam servica heshta awa ba indexy 6 zyadbkat w 1y bo dabnet wata am foga 1 servicy lasara
                    }
                }
            }
        }
    }

    public void fogPowerConsumption() {

        //  dozinaway power consumption batawawy ba psht bastn ba Eq:7
        // colony1
        for (int i = 1; i < Variables.fog_list_c1.size(); i++) { // i=1 bo away broker hsab nakret

            if (mehtodNumber == 5) {
                if (Variables.fog_list_c1.get(i).size() > 6) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                    int currentFogIndex = Integer.parseInt(Variables.fog_list_c1.get(i).get(0).toString());

                    int cpuBeforeUsed = Integer.parseInt(Variables.fog_list_backup_c1.get(currentFogIndex).get(1).toString());
                    int cpuAfterUsed = Integer.parseInt(Variables.fog_list_c1.get(i).get(1).toString());
                    int cpuUsed = cpuBeforeUsed - cpuAfterUsed;
                    double power = (double) cpuUsed / (double) cpuBeforeUsed;

                    double fogMaxPower = Double.parseDouble(Variables.fog_list_c1.get(i).get(3).toString());
                    double fogIdlePower = Double.parseDouble(Variables.fog_list_c1.get(i).get(4).toString());
                    int yp = 1;
                    double E_s_f = yp * ((fogMaxPower - fogIdlePower) * power + fogIdlePower);
                    E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
                    Variables.fog_list_c1.get(i).add(E_s_f);
                }
            } else {
                if (Variables.fog_list_c1.get(i).size() > 5) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                    int cpuBeforeUsed = Integer.parseInt(Variables.fog_list_backup_c1.get(i).get(0).toString());
                    int cpuAfterUsed = Integer.parseInt(Variables.fog_list_c1.get(i).get(0).toString());

                    int cpuUsed = cpuBeforeUsed - cpuAfterUsed;

                    double power = (double) cpuUsed / (double) cpuBeforeUsed;

                    //  power = Double.parseDouble(Variables.fog_list_c1.get(i).get(6).toString());
                    double serverMaxPower = Double.parseDouble(Variables.fog_list_c1.get(i).get(2).toString());
                    double serverIdlePower = Double.parseDouble(Variables.fog_list_c1.get(i).get(3).toString());
                    int yp = 1;
                    double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                    E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
                    Variables.fog_list_c1.get(i).add(E_s_f);
                }
            }
        }

        // colony2
        for (int i = 1; i < Variables.fog_list_c2.size(); i++) { // i=1 bo away broker hsab nakret

            if (mehtodNumber == 5) {
                if (Variables.fog_list_c2.get(i).size() > 6) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                    int cpuBeforeUsed = 0;
                    int currentFogIndex = Integer.parseInt(Variables.fog_list_c2.get(i).get(0).toString());

                    cpuBeforeUsed = Integer.parseInt(Variables.fog_list_backup_c2.get(currentFogIndex).get(1).toString());
                    int cpuAfterUsed = Integer.parseInt(Variables.fog_list_c2.get(i).get(1).toString());

                    int cpuUsed = cpuBeforeUsed - cpuAfterUsed;

                    double power = (double) cpuUsed / (double) cpuBeforeUsed;

                    double serverMaxPower = Double.parseDouble(Variables.fog_list_c2.get(i).get(3).toString());
                    double serverIdlePower = Double.parseDouble(Variables.fog_list_c2.get(i).get(4).toString());
                    int yp = 1;
                    double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                    E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
                    Variables.fog_list_c2.get(i).add(E_s_f);
                }
            } else {
                if (Variables.fog_list_c2.get(i).size() > 5) {   // wata agar am foga servicey lasar danrabw awa wata powery haya w indexy 6y bo zyadbwa 

                    int cpuBeforeUsed = Integer.parseInt(Variables.fog_list_backup_c2.get(i).get(0).toString());
                    int cpuAfterUsed = Integer.parseInt(Variables.fog_list_c2.get(i).get(0).toString());

                    int cpuUsed = cpuBeforeUsed - cpuAfterUsed;

                    double power = (double) cpuUsed / (double) cpuBeforeUsed;

                    //  power = Double.parseDouble(Variables.fog_list_c1.get(i).get(6).toString());
                    double serverMaxPower = Double.parseDouble(Variables.fog_list_c2.get(i).get(2).toString());
                    double serverIdlePower = Double.parseDouble(Variables.fog_list_c2.get(i).get(3).toString());
                    int yp = 1;
                    double E_s_f = yp * ((serverMaxPower - serverIdlePower) * power + serverIdlePower);
                    E_s_f = Double.parseDouble(String.format("%.4g%n", E_s_f));
                    Variables.fog_list_c2.get(i).add(E_s_f);
                }
            }
        }

    }

    public void cloudResourceUtilization() {

//                System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.cloud_server_list.size(); i++) {
//            
//            System.out.println("\t\t" + Variables.cloud_server_list.get(i));
//        }
        double serverCPU1 = 0, serverMem1 = 0, serverCPU2 = 0, serverMem2 = 0;

        for (int i = 0; i < Variables.cloud_server_list1.size(); i++) {

            if (mehtodNumber == 5) {

                serverCPU1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(1).toString());
                serverMem1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(2).toString());

                for (int ii = 0; ii < Variables.cloud_server_list.size(); ii++) {
                    int currentServerIndex = Integer.parseInt(Variables.cloud_server_list.get(ii).get(0).toString());
                    if (currentServerIndex == i) {
                        serverCPU2 = Double.parseDouble(Variables.cloud_server_list.get(ii).get(1).toString());
                        serverMem2 = Double.parseDouble(Variables.cloud_server_list.get(ii).get(2).toString());
                        break;
                    }
                }

            } else {
                serverCPU1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(0).toString());
                serverMem1 = Double.parseDouble(Variables.cloud_server_list1.get(i).get(1).toString());

                serverCPU2 = Double.parseDouble(Variables.cloud_server_list.get(i).get(0).toString());
                serverMem2 = Double.parseDouble(Variables.cloud_server_list.get(i).get(1).toString());
            }

            if (serverCPU1 == serverCPU2) {
                //  Variables.cloud_server_list.get(i).add("Powerrr Off");  // ba dwbaranabetawa chwnka la powerda ama dyarikrawa
            } else {
                double serverUtilizationCPU = serverCPU1 - serverCPU2; // wat cpuy hamw servicakany sar har serverek = cpu of backup server - cpu of used server 
                double serverUtilizationMem = serverMem1 - serverMem2;

                double cloudUtilizationCPUPercentage = serverUtilizationCPU / serverCPU1;
                double cloudUtilizationMemPercentage = serverUtilizationMem / serverMem1;

                if (mehtodNumber == 5) {
                    int index = 0;
                    for (int ii = 0; ii < Variables.cloud_server_list.size(); ii++) {
                        int currentServerIndex1 = Integer.parseInt(Variables.cloud_server_list.get(ii).get(0).toString());
                        if (currentServerIndex1 == i) {
                            index = ii;
                            break;
                        }
                    }
                    Variables.cloud_server_list.get(index).add(formatter.format(cloudUtilizationCPUPercentage));
                    Variables.cloud_server_list.get(index).add(formatter.format(cloudUtilizationMemPercentage));
                } else {
                    Variables.cloud_server_list.get(i).add(formatter.format(cloudUtilizationCPUPercentage));
                    Variables.cloud_server_list.get(i).add(formatter.format(cloudUtilizationMemPercentage));
                }
            }
        }

    }

    public void fogResourceUtilization() {

        double fogCPU1 = 0, fogMem1 = 0, fogCPU2 = 0, fogMem2 = 0;

        for (int j = 1; j < Variables.fog_list_c1.size(); j++) {

            if (mehtodNumber == 5) {

                int fogIndex = Integer.parseInt(Variables.fog_list_c1.get(j).get(0).toString());

                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c1.get(fogIndex).get(1).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c1.get(fogIndex).get(2).toString());

                fogCPU2 = Double.parseDouble(Variables.fog_list_c1.get(j).get(1).toString());
                fogMem2 = Double.parseDouble(Variables.fog_list_c1.get(j).get(2).toString());

            } else {

                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c1.get(j).get(0).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c1.get(j).get(1).toString());

                fogCPU2 = Double.parseDouble(Variables.fog_list_c1.get(j).get(0).toString());
                fogMem2 = Double.parseDouble(Variables.fog_list_c1.get(j).get(1).toString());
            }

            if (fogCPU1 == fogCPU2) {  // agar yaksanbw awa manay waya ka aw servera power offa
                // Variables.fog_list_c1.get(j).add("Power Off");
            } else {
                double fogUtilizationCPU = fogCPU1 - fogCPU2;
                double fogUtilizationMem = fogMem1 - fogMem2;

                double fogUtilizationCPUPercentage = fogUtilizationCPU / fogCPU1;
                //  JOptionPane.showMessageDialog(null, j+")\n"+Variables.fog_list_backup_c1.get(j)+"\n"+Variables.fog_list_c1.get(j)+"\n"+fogCPU1+"\n"+fogUtilizationCPU+"\n  fogCPUUtilization:  "+fogUtilizationCPUPercentage);

                double fogUtilizationMemPercentage = fogUtilizationMem / fogMem1;
                Variables.fog_list_c1.get(j).add(formatter.format(fogUtilizationCPUPercentage));
                Variables.fog_list_c1.get(j).add(formatter.format(fogUtilizationMemPercentage));
            }
        }

//                                                System.out.println("\n\n\n\n");
//        for (int i = 0; i < Variables.fog_list_backup_c2.size(); i++) {
//            
//            System.out.println("\t\t" + Variables.fog_list_backup_c2.get(i));
//        }
        // colony2
        for (int j = 1; j < Variables.fog_list_c2.size(); j++) {

            if (mehtodNumber == 5) {
                int fogIndex = Integer.parseInt(Variables.fog_list_c2.get(j).get(0).toString());

                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c2.get(fogIndex).get(1).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c2.get(fogIndex).get(2).toString());

                fogCPU2 = Double.parseDouble(Variables.fog_list_c2.get(j).get(1).toString());
                fogMem2 = Double.parseDouble(Variables.fog_list_c2.get(j).get(2).toString());

            } else {
                fogCPU1 = Double.parseDouble(Variables.fog_list_backup_c2.get(j).get(0).toString());
                fogMem1 = Double.parseDouble(Variables.fog_list_backup_c2.get(j).get(1).toString());

                fogCPU2 = Double.parseDouble(Variables.fog_list_c2.get(j).get(0).toString());
                fogMem2 = Double.parseDouble(Variables.fog_list_c2.get(j).get(1).toString());
            }
            if (fogCPU1 == fogCPU2) {  // agar yaksanbw awa manay waya ka aw servera power offa
                // Variables.fog_list_c2.get(j).add("Power Off");
            } else {

                double fogUtilizationCPU = fogCPU1 - fogCPU2;
                double fogUtilizationMem = fogMem1 - fogMem2;

                double fogUtilizationCPUPercentage = fogUtilizationCPU / fogCPU1;
                double fogUtilizationMemPercentage = fogUtilizationMem / fogMem1;

                Variables.fog_list_c2.get(j).add(formatter.format(fogUtilizationCPUPercentage));
                Variables.fog_list_c2.get(j).add(formatter.format(fogUtilizationMemPercentage));
            }
        }

    }

}
