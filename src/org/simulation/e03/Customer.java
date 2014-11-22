package org.simulation.e03;

public class Customer {

    public double time_generated; // time when customer arrives.
    public double time_serviced; // time when customer get a service.
    public double time_done; // time when customer leaves.
    private int id; // identification
    private int num_scoops; // number of scoops
    public int max_num_customer; // the number of customer ahead when arriving

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

    public boolean equals(Customer c) {
        return (this.id == c.id);
    }

    /**
     * override the method toString() in order to be friendly when printing
     * 
     * @return a string representation of the customer's state.
     */
    public String toString() {
        String str = "";
        str += "Customer_" + this.id + " with " + this.num_scoops + " scoops ";
        str += "(arrived at " + Event.printTime(this.time_generated) + " with "
                + this.max_num_customer + " customers ahead, ";
        str += "was serviced at " + Event.printTime(this.time_serviced) + ", ";
        str += "left at " + Event.printTime(this.time_done) + ")";
        return str;
    }

}
