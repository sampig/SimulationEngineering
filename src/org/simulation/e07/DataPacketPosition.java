package org.simulation.e07;

import java.io.Serializable;

public class DataPacketPosition implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 2224840264897612801L;
    /**
     * Store the position for transmission.
     */
    private double values;

    public DataPacketPosition() {
    }

    public DataPacketPosition(double values) {
        this.values = values;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }
}
