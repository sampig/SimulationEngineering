package org.simulation.e03;

import java.util.List;
import java.util.Random;

public class EventServing extends Event {

    // constant: time to make each ice cream
    public static final double TIME_ICECREAM = 0.5;

    public EventServing(double time) {
        this.time = time;
    }

    public void execute(Simulator sim) {

        // if it is a single queue.
        List<Customer> list = sim.getCustomer_queue().get(0);
        // the first customer get a service.
        Customer c = list.get(0);
        c.time_serviced = time;

        int num_scoops = 0;
        // numbers of scoops which customers want are random
        switch (sim.getType_random()) {
        case Simulator.RANDOM_RANDOM:
            num_scoops = this.generateRandom(c.time_generated);
            break;
        case Simulator.RANDOM_NORMAL_DISTRIBUTION_1:
        case Simulator.RANDOM_NORMAL_DISTRIBUTION_2:
            num_scoops = this.generateND(c.time_generated);
            break;
        default:
            num_scoops = this.generateRandom(c.time_generated);
            break;
        }
        c.setNum_scoops(num_scoops);

        System.out.println("Customer_" + c.getId() + "(" + c.getNum_scoops()
                + ") is being served at time " + Event.printTime(c.time_serviced));
        time += num_scoops * TIME_ICECREAM;

        // if the customer get a service,
        // he will leave the queue and get into the service queue.
        c.time_done = time;
        sim.getCustomer_queue().get(0).remove(c);
        sim.getServer_queue().add(c);

        EventDone done = new EventDone(time);
        sim.insert(done);

    }

    /**
     * Generate a number randomly.
     * 
     * @param time
     * @return a random number
     */
    public int generateRandom(double time) {
        Random r = new Random();
        int num_scoops = 0;
        // numbers of scoops which customers want are random
        switch (Event.getHour(time)) {
        case Event.TIME_OFF:
            break;
        case Event.TIME_11:
            num_scoops = r.nextInt(5) + 1; // 1-5 at 11-12
            break;
        case Event.TIME_12:
        case Event.TIME_13:
        case Event.TIME_14:
            num_scoops = r.nextInt(4) + 3; // 3-6 at 12-15
            break;
        case Event.TIME_15:
        case Event.TIME_16:
        case Event.TIME_17:
        case Event.TIME_18:
        case Event.TIME_19:
            num_scoops = r.nextInt(4) + 1; // 1-4 at 15-20
            break;
        }
        return num_scoops;
    }

    /**
     * Generate a number randomly based on Normal Distribution.
     * 
     * @param time
     * @return a random number
     */
    public int generateND(double time) {
        Random r = new Random();
        int num_scoops = 0;
        // numbers of scoops which customers want are random
        switch (Event.getHour(time)) {
        case Event.TIME_OFF:
            break;
        case Event.TIME_11:
            // 1-5 at 11-12
            num_scoops = (int) Math.round(r.nextGaussian() * 1.0 + 3);
            break;
        case Event.TIME_12:
        case Event.TIME_13:
        case Event.TIME_14:
            // 3-6 at 12-15
            num_scoops = (int) Math.round(r.nextGaussian() * 0.75 + 4.5);
            break;
        case Event.TIME_15:
        case Event.TIME_16:
        case Event.TIME_17:
        case Event.TIME_18:
        case Event.TIME_19:
            // 1-4 at 15-20
            num_scoops = (int) Math.round(r.nextGaussian() * 0.75 + 2.5);
            break;
        }
        return num_scoops;
    }

}
