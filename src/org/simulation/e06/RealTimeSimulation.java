package org.simulation.e06;

import java.util.Date;

public class RealTimeSimulation {

    public static void main(String... strings) {
        System.out.println("Start at: " + (new Date()));
        SimpleCircusTrapeze sc = new SimpleCircusTrapeze(0,10);
        sc.setStepSize(1);
        sc.start();
        System.out.println("End at: " + (new Date()));
    }

}
