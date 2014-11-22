package org.simulation.e04;

public class MethodType {

    private int method;

    private MethodType() {
    }

    public MethodType(int method) {
        this();
        if (method <= 0) {
            this.method = MethodType.EULER;
            return;
        }
        this.method = method;
    }

    public static MethodType getInstance(int method) {
        MethodType m;
        if (method <= 0) {
            m = new MethodType(MethodType.EULER);
            return m;
        }
        m = new MethodType(method);
        return m;
    }

    public static MethodType getInstanceEULER() {
        MethodType m = new MethodType(MethodType.EULER);
        return m;
    }

    public static MethodType getInstanceRK2() {
        MethodType m = new MethodType(MethodType.RK2);
        return m;
    }

    public static MethodType getInstanceRK4() {
        MethodType m = new MethodType(MethodType.RK4);
        return m;
    }

    public int getMethod() {
        return this.method;
    }

    public String getMethodName() {
        switch (this.method) {
        case EULER:
            return "EULER";
        case RK2:
            return "Runge-Kutta 2";
        case RK4:
            return "Runge-Kutta 4";
        }
        return "ERROR";
    }

    public String toString() {
        return super.toString() + ": " + this.getMethodName();
    }

    // constants:
    /**
     * EULER
     */
    public final static int EULER = 1;
    /**
     * Runge-Kutta 2nd Order
     */
    public final static int RK2 = 2;
    /**
     * Runge-Kutta 4th Order
     */
    public final static int RK4 = 3;

}
