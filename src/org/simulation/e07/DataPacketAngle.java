package org.simulation.e07;

import java.io.Serializable;

public class DataPacketAngle implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 7447536268201615882L;
    /**
     * Store the angle for transmission.
     */
    private double values;

    public DataPacketAngle() {
    }

    public DataPacketAngle(double values) {
        this.values = values;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }

}
