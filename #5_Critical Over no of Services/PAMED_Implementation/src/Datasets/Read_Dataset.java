/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datasets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class Read_Dataset {

    BufferedReader bufferedReader;
    StringBuffer stringBuffer, stringBuffer1;
    FileReader fileReader;
    public static ArrayList<ArrayList> cloud_servers, fog_nodes, IoT_devices, services,servicesOnlyForOurPropose, tasks, temp;

    public void initialize(int seed) {
        cloud_servers = new ArrayList<ArrayList>();
        fog_nodes = new ArrayList<ArrayList>();
        IoT_devices = new ArrayList<ArrayList>();
        services = new ArrayList<ArrayList>();
        tasks = new ArrayList<ArrayList>();
        servicesOnlyForOurPropose = new ArrayList<ArrayList>();
        cloud_Servers(seed);
        fog_Nodes(seed);
        services(seed);
        iot_Devices(seed);
        tasks(seed);
    }

    public String read(int seed, String path) {
        try {
            stringBuffer1 = new StringBuffer();
            File file = new File(path + ".txt");
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            fileReader.close();
            stringBuffer1.append(stringBuffer.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
            e.printStackTrace();
        }
        return stringBuffer1.toString();
    }

    public ArrayList<ArrayList> tokinize(int seed, String path) {
        temp = new ArrayList<ArrayList>();
        String str = read(seed, path);
        ArrayList list = new ArrayList();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        temp.clear();

        /// toknizing and removing ","        
        for (int i = 0; i < list.size(); i++) {
            ArrayList data = new ArrayList();
            for (String value : list.get(i).toString().split(",")) {
                data.add(value);
            }
            temp.add(data);
        }
        return temp;
    }

    public void cloud_Servers(int seed) {
        cloud_servers = tokinize(seed, "dataset/Cloud_Servers");
        System.out.println("cloud server: " + cloud_servers.size());
    }

    public void fog_Nodes(int seed) {
        Random rand;
        if (seed == 1) {
            rand = new Random(seed);
        } else {
            rand = new Random();
        }
        fog_nodes = tokinize(seed, "dataset/Fog_Nodes");
        int fogSize = fog_nodes.size();
        int indexOfNewFogDevice = rand.nextInt(fogSize);
        ArrayList newFogDevice = fog_nodes.get(indexOfNewFogDevice);
        fog_nodes.add(newFogDevice);
        System.out.println("fog nodes: " + fog_nodes.size());

    }

    public void services(int seed) {
        Random rand;
        if (seed == 1) {
            rand = new Random(seed);
        } else {
            rand = new Random();
        }
        services = tokinize(seed, "dataset/Services");
        servicesOnlyForOurPropose = tokinize(seed, "dataset/Services");

        ArrayList serviceMem = new ArrayList();
        for (int i = 0; i < services.size(); i++) {
            serviceMem.add(services.get(i).get(1));
        }

      //  JOptionPane.showMessageDialog(null, services);
        int serviceSize = serviceMem.size();
        for (int i = 0; i < services.size(); i++) {
            int randMemforEachService = rand.nextInt(serviceSize);
            services.get(i).set(1, serviceMem.get(randMemforEachService));
            servicesOnlyForOurPropose.get(i).set(1, serviceMem.get(randMemforEachService));
        }
        int indexOfNewService = rand.nextInt(serviceSize);
        ArrayList newService = services.get(indexOfNewService);
        services.add(newService);
        System.out.println("services number: " + services.size());
       // JOptionPane.showMessageDialog(null, services);

    }

    public void iot_Devices(int seed) {
        IoT_devices = tokinize(seed, "dataset/IoT_Devices");
        System.out.println("IoT devices: " + IoT_devices.size());
    }

    public void tasks(int seed) {
        tasks = tokinize(seed, "dataset/Tasks");
        System.out.println("tasks: " + tasks.size());
    }

    public static void main(String[] args) {
        Read_Dataset ob = new Read_Dataset();

    }
}
