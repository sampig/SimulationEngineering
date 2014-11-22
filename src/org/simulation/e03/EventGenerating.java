package org.simulation.e03;

import java.util.Random;

public class EventGenerating extends Event {

    private int counter_customers; // counter for customers.

    public EventGenerating(double time) {
        this.counter_customers = 0;
        this.time = time;
    }

    public void execute(Simulator sim) {

        // if it is not working time, continue by 1 minute.
        if (Event.getHour(this.time) == Event.TIME_OFF) {
            this.time += 1;
            sim.insert(this);
            return;
        }

        // if it is a single queue.
        // a new customer arrives.
        this.counter_customers++;
        Customer c = new Customer(time, counter_customers);
        sim.getCustomer_list().add(c);
        // calculate the number of customers ahead.
        // number = number(waiting queue) + number(service queue)
        c.max_num_customer = sim.getCustomer_queue().get(0).size()
                + sim.getServer_queue().size();
        sim.getCustomer_queue().get(0).add(c);
        System.out.println("Customer_" + c.getId() + " arrived (with " + c.max_num_customer
                + " ahead) at time " + Event.printTime(c.time_generated));

        // if there is space in server queue,
        // a customer get a service.
        if (sim.getCustomer_queue().get(0).size() > 0
                && sim.getServer_queue().size() < sim.num_servers) {
            EventServing s = new EventServing(this.time);
            sim.insert(s);
        }

        // next new customers arrives randomly
        // according to the random type.
        double next_event_time = 0;
        switch (sim.getType_random()) {
        case Simulator.RANDOM_RANDOM:
            next_event_time = this.generateRandom(this.time);
            break;
        case Simulator.RANDOM_NORMAL_DISTRIBUTION_1:
        case Simulator.RANDOM_NORMAL_DISTRIBUTION_2:
            next_event_time = this.generateND(this.time);
            break;
        default:
            next_event_time = this.generateRandom(this.time);
            break;
        }

        this.time = next_event_time;
        sim.insert(this); // insert a new Generating event.
    }

    /**
     * Generate a number randomly.
     * 
     * @param time
     * @return a random number
     */
    public double generateRandom(double time) {
        // it is a real random in JAVA (Math.Random() is pseudo random)
        Random r = new Random();

        // next new customers arrives randomly
        double next_event_time = time;
        switch (Event.getHour(time)) {
        case Event.TIME_11:
        case Event.TIME_12:
            next_event_time += r.nextInt(3) + 1; // 1-3 at 11-13
            break;
        case Event.TIME_13:
        case Event.TIME_14:
        case Event.TIME_15:
        case Event.TIME_16:
            next_event_time += r.nextInt(9) + 2; // 2-10 at 13-17
            break;
        case Event.TIME_17:
        case Event.TIME_18:
            next_event_time += r.nextInt(2) + 1; // 1-2 at 17-19
            break;
        case Event.TIME_19:
            next_event_time += r.nextInt(4) + 2; // 2-5 at 19-20
            break;
        }
        return next_event_time;
    }

    /**
     * Generate a number randomly based on Normal Distribution.
     * 
     * @param time
     * @return a random number
     */
    public double generateND(double time) {
        // it is a real random in JAVA (Math.Random() is pseudo random)
        Random r = new Random();

        // next new customers arrives randomly
        double next_event_time = time;
        switch (Event.getHour(time)) {
        case Event.TIME_11:
        case Event.TIME_12:
            next_event_time += r.nextGaussian() * 0.5 + 2.0; // 1-3 at 11-13
            break;
        case Event.TIME_13:
        case Event.TIME_14:
        case Event.TIME_15:
        case Event.TIME_16:
            next_event_time += r.nextGaussian() * 2.0 + 6.0; // 2-10 at 13-17
            break;
        case Event.TIME_17:
        case Event.TIME_18:
            next_event_time += r.nextGaussian() * 0.25 + 1.5; // 1-2 at 17-19
            break;
        case Event.TIME_19:
            next_event_time += r.nextGaussian() * 0.75 + 3.5; // 2-5 at 19-20
            break;
        }
        return next_event_time;
    }

}
