/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
 
/**
 *
 * @author hiwa_cst
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hiwa_cst
 */


class CloudServer2 {

    int serverIndex;
    int cpu;
    int ram;
    double maximum;
    double idle;

    CloudServer2(int serverIndex1, int cpu1, int ram1, double maximum1, double idle1) {
        serverIndex = serverIndex1;
        cpu = cpu1;
        ram = ram1;
        maximum = maximum1;
        idle = idle1;
        
    }


    public int getServerIndex() {
        return serverIndex;
    }

    public int getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getIdle() {
        return idle;
    }

    
    @Override
    public String toString() {
        return (this.getServerIndex()
                + "," + this.getCpu()
                + "," + this.getRam()
                + "," + this.getMaximum()
                + "," + this.getIdle()+ "");
    }
}

public class CloudServerSorting2 {

    public ArrayList<ArrayList> cloudSorting(ArrayList<ArrayList> cloudServerBeforeSort) {
        ArrayList<ArrayList> after_sort = new ArrayList<ArrayList>();

        List<CloudServer2> cloudSerevr_list = new ArrayList<CloudServer2>();
        int serverIndex, cpu, ram;
        double idle, busy;
        for (int i = 0; i < cloudServerBeforeSort.size(); i++) {

            serverIndex = Integer.parseInt(cloudServerBeforeSort.get(i).get(0).toString());
            cpu = Integer.parseInt(cloudServerBeforeSort.get(i).get(1).toString());
            ram = Integer.parseInt(cloudServerBeforeSort.get(i).get(2).toString());
            busy = Double.parseDouble(cloudServerBeforeSort.get(i).get(3).toString());
            idle = Double.parseDouble(cloudServerBeforeSort.get(i).get(4).toString());
            cloudSerevr_list.add(new CloudServer2(serverIndex, cpu, ram, busy, idle));

        }

        //   System.out.println("Before Sorting the student data:");
        //java 8 forEach for printing the list 
        //   PMs_list.forEach((s) -> System.out.println(s));
        // System.out.println("");
        // System.out.println("After Sorting the student data by Age:");
//        //Lambda expression for sorting by age 
        cloudSerevr_list.sort((CloudServer2 s1, CloudServer2 s2) -> s1.getCpu() - s2.getCpu());
//        //java 8 forEach for printing the list
        //  PMs_list.forEach((s) -> System.out.println(s));
        //  System.out.println("");
        cloudSerevr_list.forEach(item -> {
            ArrayList list = new ArrayList();
            StringTokenizer st = new StringTokenizer(item.toString());
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
            /// toknizing and removing ","        
            for (int i = 0; i < list.size(); i++) {
                ArrayList servers = new ArrayList();
                for (String value : list.get(i).toString().split(",")) {
                    servers.add(value);
                }
                after_sort.add(servers);
            }
        });
        return after_sort;
    }

}
