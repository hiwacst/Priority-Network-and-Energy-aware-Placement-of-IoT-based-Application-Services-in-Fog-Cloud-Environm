/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

class CloudServer1 {

    int serviceIndex;
    int serverIndex;
    int cpu;
    int ram;
    double maximum;
    double idle;
    int avg;

    CloudServer1(int serviceIndex1, int serverIndex1, int cpu1, int ram1, double maximum1, double idle1, int avg1) {
        serviceIndex = serviceIndex1;
        serverIndex = serverIndex1;
        cpu = cpu1;
        ram = ram1;
        maximum = maximum1;
        idle = idle1;
        avg = avg1;
    }

    public int getServiceIndex() {
        return serviceIndex;
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

    public int getAvg() {
        return avg;
    }

    @Override
    public String toString() {
        return (this.getServiceIndex()
                + "," + this.getServerIndex()
                + "," + this.getCpu()
                + "," + this.getRam()
                + "," + this.getMaximum()
                + "," + this.getIdle()
                + "," + this.getAvg() + "");
    }
}

public class CloudServerSorting {

//    public ArrayList<ArrayList> pms_after_sort(ArrayList<ArrayList> pms_befor_sort) {
    public ArrayList<ArrayList> cloudSorting(ArrayList<ArrayList> cloudServerBeforeSort) {
        ArrayList<ArrayList> after_sort = new ArrayList<ArrayList>();

        List<CloudServer1> cloudSerevr_list = new ArrayList<CloudServer1>();
        // String pms_name;
        int serviceIndex, serverIndex, cpu, ram, avg;
        double idle, busy;
        for (int i = 0; i < cloudServerBeforeSort.size(); i++) {

            serviceIndex = Integer.parseInt(cloudServerBeforeSort.get(i).get(0).toString());
            serverIndex = Integer.parseInt(cloudServerBeforeSort.get(i).get(1).toString());
            cpu = Integer.parseInt(cloudServerBeforeSort.get(i).get(2).toString());
            ram = Integer.parseInt(cloudServerBeforeSort.get(i).get(3).toString());
            idle = Double.parseDouble(cloudServerBeforeSort.get(i).get(4).toString());
            busy = Double.parseDouble(cloudServerBeforeSort.get(i).get(5).toString());
            avg = Integer.parseInt(cloudServerBeforeSort.get(i).get(6).toString());
            cloudSerevr_list.add(new CloudServer1(serviceIndex, serverIndex, cpu, ram, idle, busy, avg));

        }

        //   System.out.println("Before Sorting the student data:");
        //java 8 forEach for printing the list 
        //   PMs_list.forEach((s) -> System.out.println(s));
        // System.out.println("");
        // System.out.println("After Sorting the student data by Age:");
//        //Lambda expression for sorting by age 
        cloudSerevr_list.sort((CloudServer1 s1, CloudServer1 s2) -> s1.getAvg() - s2.getAvg());
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
                ArrayList pms = new ArrayList();
                for (String value : list.get(i).toString().split(",")) {
                    pms.add(value);
                }
                after_sort.add(pms);
            }
        });
        return after_sort;
    }

}
