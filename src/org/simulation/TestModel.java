package org.simulation;

import org.simulation.w2.CircusTrapeze;
import org.simulation.w2.MassSpringDashpot;
import org.simulation.w2.PredatorPrey;

public strictfp class TestModel {

    public strictfp static void main(String... strings) {
        PredatorPrey pp = new PredatorPrey(2014.0, 2018.0);
        pp.start();
        // pp.outputResult();
        CircusTrapeze ct = new CircusTrapeze();
        ct.start(CircusTrapeze.STATUS_STATIC);
        // ct.outputResult();
        MassSpringDashpot msd = new MassSpringDashpot();
        msd.start();
        msd.output();
    }

}
