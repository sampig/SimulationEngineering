package org.simulation.e03;

import java.util.Random;

public class EventServing extends Event {

    public static final double TIME_ICECREAM = 0.5;

    public EventServing(double time) {
        this.time = time;
    }

    public void execute(Simulator sim) {
        Random r = new Random();

        Customer c = sim.getCustomer_queue().get(0).get(0);
        c.time_serviced = time;

        int num_scoops = 0;

        // numbers of scoops which customers ask are random
        switch (Event.getHour(c.time_generated)) {
        case Event.TIME_OFF:
            break;
        case Event.TIME_11:
            num_scoops = r.nextInt(4) + 1; // 1-5 at 11-12
            break;
        case Event.TIME_12:
        case Event.TIME_13:
        case Event.TIME_14:
            num_scoops = r.nextInt(3) + 3; // 3-6 at 12-15
            break;
        case Event.TIME_15:
        case Event.TIME_16:
        case Event.TIME_17:
        case Event.TIME_18:
        case Event.TIME_19:
            num_scoops = r.nextInt(3) + 1; // 1-4 at 15-20
            break;
        }
        c.setNum_scoops(num_scoops);

        System.out.println("Customer_" + c.getId() + "(" + c.getNum_scoops()
                + ") is being served at time " + Event.printTime(c.time_serviced));
        time += num_scoops * TIME_ICECREAM;
        EventDone done = new EventDone(time);
        sim.insert(done);

    }

}
