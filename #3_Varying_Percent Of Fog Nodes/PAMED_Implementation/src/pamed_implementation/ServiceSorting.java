/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pamed_implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

class Service1 {

    int cpu;
    int ram;
   // int avg;

    Service1(int cpu1, int ram1) {
        cpu = cpu1;
        ram = ram1;
    }

    public int getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }



    @Override
    public String toString() {
        return (this.getCpu()
                + "," + this.getRam()
                + "");
    }
}

public class ServiceSorting {

    public ArrayList<ArrayList> serviceSorting(ArrayList<ArrayList> serviceBeforeSort) {
        ArrayList<ArrayList> after_sort = new ArrayList<ArrayList>();

        List<Service1> service_list = new ArrayList<Service1>();
        // String pms_name;
        int serviceIndex, cpu, ram; //, avg;
        double idle, busy;
        for (int i = 0; i < serviceBeforeSort.size(); i++) {

            cpu = Integer.parseInt(serviceBeforeSort.get(i).get(0).toString());
            ram = Integer.parseInt(serviceBeforeSort.get(i).get(1).toString());
            service_list.add(new Service1(cpu, ram));

        }
 
        service_list.sort((Service1 s1, Service1 s2) -> s1.getCpu()- s2.getCpu());
//        //java 8 forEach for printing the list
        //  PMs_list.forEach((s) -> System.out.println(s));
        //  System.out.println("");
        service_list.forEach(item -> {
            ArrayList list = new ArrayList();
            StringTokenizer st = new StringTokenizer(item.toString());
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
            /// toknizing and removing ","        
            for (int i = 0; i < list.size(); i++) {
                ArrayList service = new ArrayList();
                for (String value : list.get(i).toString().split(",")) {
                    service.add(value);
                }
                after_sort.add(service);
            }
        });
        return after_sort;
    }

}
