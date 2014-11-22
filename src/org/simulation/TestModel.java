package org.simulation;

import org.simulation.e02.CircusTrapeze;
import org.simulation.e03.IceCreamShopSimulator;
import org.simulation.e04.CircusTrapezeImpr;
import org.simulation.e04.MethodType;
import org.simulation.w02.MassSpringDashpot;
import org.simulation.w02.PredatorPrey;
import org.simulation.w03.SingleServerQueueSimulator;

public strictfp class TestModel {

    public strictfp static void main(String... strings) {

        /*
         * week 02
         */
        PredatorPrey pp = new PredatorPrey(2014.0, 2018.0);
        pp.start();
        // pp.outputResult();
        CircusTrapeze ct = new CircusTrapeze();
        ct.start(CircusTrapeze.STATUS_STATIC);
        // ct.outputResult();
        MassSpringDashpot msd = new MassSpringDashpot();
        msd.start();
        // msd.output();

        /*
         * week 03
         */
        SingleServerQueueSimulator sim = new SingleServerQueueSimulator();
        sim.start();
        // sim.run();
        // TestModel.clearConsole();
        IceCreamShopSimulator icSim = new IceCreamShopSimulator(0, 14400,
                IceCreamShopSimulator.MODE_SINGLESERVER_SINGLEQUEUE,
                IceCreamShopSimulator.RANDOM_NORMAL_DISTRIBUTION_2);
        if (icSim.getMode() == IceCreamShopSimulator.MODE_MULTISERVER_SINGLEQUEUE) {
            icSim.setNum_servers(2);
        }
        // icSim.start();
        // icSim.run();
        // System.out.println(Event.printTime(861.5));

        /*
         * week 04
         */
        CircusTrapezeImpr cti = new CircusTrapezeImpr(0, 0.1, 0.01,
                CircusTrapezeImpr.STATUS_STATIC, MethodType.getInstanceEULER());
        cti.setErrorThreshold(0.00001);
        cti.start();
        cti.outputResult();

    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.println("*****Exception" + e.toString());
        }
    }

}
