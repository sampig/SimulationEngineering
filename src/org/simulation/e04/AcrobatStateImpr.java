package org.simulation.e04;

public class AcrobatStateImpr implements Cloneable {

    private double values; // current value
    private double der; // current velocity
    private double time;

    private MethodType method;

    private AcrobatStateImpr next_state;
    private AcrobatStateImpr prev_state;

    private double[] temp_prev_der = new double[10];

    public AcrobatStateImpr() {
        der = 0;
        values = 0;
    }

    /**
     * 
     * @param sl
     * @param method
     */
    public void init(StateList sl, MethodType method) {
        sl.addState(this);
        this.method = method;
        der = 0;
    }

    public void update(StateList sl) {
        switch (this.getMethod().getMethod()) {
        case MethodType.EULER:
            this.values += sl.getStepTime() * der;
            // sl.getList().add(this.clone());
            break;
        case MethodType.RK2:
            if ((sl.getFrameCount() & 1) == 0) {
                this.prev_state = this.clone();
                this.values += 2 * sl.getStepTime() * der;
            } else {
                // System.out.println(der +","+ this.prev_state.getDer());
                this.values = this.prev_state.getValues() + sl.getStepTime()
                        * (this.prev_state.getDer() + der);
                // sl.getList().add(this.clone());
            }
            break;
        case MethodType.RK4:
            switch (sl.getFrameCount() & 3) {
            case 0:
                this.prev_state = this.clone();
                this.values += 2 * sl.getStepTime() * der;
                this.temp_prev_der[0] = der;
                break;
            case 1:
                this.values = this.prev_state.getValues() + 2 * sl.getStepTime() * der;
                this.temp_prev_der[1] = der;
                break;
            case 2:
                this.values = this.prev_state.getValues() + 4 * sl.getStepTime() * der;
                this.temp_prev_der[2] = der;
                break;
            case 3:
                // System.out.println(temp_prev_der[0] + "," + temp_prev_der[1]
                // + "," + temp_prev_der[2] + "," + der);
                this.values = this.prev_state.getValues()
                        + (4 * sl.getStepTime() / 6)
                        * (temp_prev_der[0] + 2 * temp_prev_der[1] + 2 * temp_prev_der[2] + der);
                // sl.getList().add(this.clone());
                break;
            }
            break;
        default:
            break;
        }
    }

    public AcrobatStateImpr clone() {
        AcrobatStateImpr clone = null;
        try {
            clone = (AcrobatStateImpr) super.clone();
            clone.setMethod(this.getMethod());
            if (clone.getNext() != null) {
                clone.setNext(clone.getNext().clone());
            }
            // if (clone.getPrev() != null) {
            // clone.setPrev(clone.getPrev().clone());
            // }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public void setPrev(AcrobatStateImpr prev) {
        this.prev_state = prev;
    }

    public AcrobatStateImpr getPrev() {
        return this.prev_state;
    }

    public void setNext(AcrobatStateImpr next) {
        this.next_state = next;
    }

    public AcrobatStateImpr getNext() {
        return this.next_state;
    }

    public double getDer() {
        return der;
    }

    public void setDer(double der) {
        this.der = der;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }

    public MethodType getMethod() {
        return method;
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String toString() {
        String str = super.toString();
        str += ": " + time + ", " + values + ", " + der;
        return str;
    }

}