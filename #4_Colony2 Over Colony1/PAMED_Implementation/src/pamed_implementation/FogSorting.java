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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

class Fog1 {

    // int serviceIndex;
    // int serverIndex;
    int fogIndex;
    int cpu;
    int ram;
    double maximum;
    double idle;
    // int avg;

    Fog1(int fogIndex1, int cpu1, int ram1, double maximum1, double idle1) {
        //  serviceIndex = serviceIndex1;
        // serverIndex = serverIndex1;
        fogIndex = fogIndex1;
        cpu = cpu1;
        ram = ram1;
        maximum = maximum1;
        idle = idle1;
        // avg = avg1;
    }

//    public int getServiceIndex() {
//        return serviceIndex;
//    }
//
//    public int getServerIndex() {
//        return serverIndex;
//    }
    public int getFogIndex() {
        return fogIndex;
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

//    public int getAvg() {
//        return avg;
//    }
    @Override
    public String toString() {
        return (this.getFogIndex()
                + "," + this.getCpu()
                + "," + this.getRam()
                + "," + this.getMaximum()
                + "," + this.getIdle()
                + "");
    }
}

public class FogSorting {

//    public ArrayList<ArrayList> pms_after_sort(ArrayList<ArrayList> pms_befor_sort) {
    public ArrayList<ArrayList> fogSorting(String location, ArrayList<ArrayList> fogBeforeSort) {
        ArrayList<ArrayList> after_sort = new ArrayList<ArrayList>();
        //JOptionPane.showMessageDialog(null, location + "\nfdf:  " + fogBeforeSort);
        List<Fog1> fog_list = new ArrayList<Fog1>();
        // String pms_name;
        int serviceIndex, fogIndex, cpu, ram;
        double idle, busy;
        for (int i = 0; i < fogBeforeSort.size(); i++) {

            fogIndex = Integer.parseInt(fogBeforeSort.get(i).get(0).toString());
            cpu = Integer.parseInt(fogBeforeSort.get(i).get(1).toString());
            ram = Integer.parseInt(fogBeforeSort.get(i).get(2).toString());
            busy = Double.parseDouble(fogBeforeSort.get(i).get(3).toString());
            idle = Double.parseDouble(fogBeforeSort.get(i).get(4).toString());
            fog_list.add(new Fog1(fogIndex, cpu, ram, busy, idle));

        }
        //   System.out.println("Before Sorting the student data:");
        //java 8 forEach for printing the list 
        //   PMs_list.forEach((s) -> System.out.println(s));
        // System.out.println("");
        // System.out.println("After Sorting the student data by Age:");
//        //Lambda expression for sorting by age 
        fog_list.sort((Fog1 s1, Fog1 s2) -> s1.getCpu() - s2.getCpu());

        //java 8 forEach for printing the list
        //  PMs_list.forEach((s) -> System.out.println(s));
        //  System.out.println("");
        fog_list.forEach(item -> {
            ArrayList list = new ArrayList();
            StringTokenizer st = new StringTokenizer(item.toString());
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
            /// toknizing and removing ","        
            for (int i = 0; i < list.size(); i++) {
                ArrayList fog = new ArrayList();
                for (String value : list.get(i).toString().split(",")) {
                    fog.add(value);
                }
                after_sort.add(fog);
            }
        });
        return after_sort;
    }

}
