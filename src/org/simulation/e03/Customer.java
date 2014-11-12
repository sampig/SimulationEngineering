package org.simulation.e03;

public class Customer {

    public double time_generated;
    public double time_serviced;
    public double time_done;
    private int id;
    private int num_scoops;
    public int max_num_customer; // maximum

    public Customer() {
    }

    public Customer(double time, int id) {
        this.id = id;
        this.time_generated = time;
        this.num_scoops = 1; // default
    }

    public Customer(double time, int id, int num_scoops) {
        this.id = id;
        this.time_generated = time;
        this.num_scoops = num_scoops;
    }

    public int getId() {
        return id;
    }

    public int getNum_scoops() {
        return num_scoops;
    }

    public void setNum_scoops(int num_scoops) {
        this.num_scoops = num_scoops;
    }

    public String toString() {
        String str = "";
        str += "Customer_" + this.id + " with " + this.num_scoops + " scoops";
        str += "(arrived at " + Event.printTime(this.time_generated) + " with "
                + this.max_num_customer + " customers ahead, ";
        str += "was serviced at " + Event.printTime(this.time_serviced) + ", ";
        str += "left at " + Event.printTime(this.time_done) + ")";
        return str;
    }

}
