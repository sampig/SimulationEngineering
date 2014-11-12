package org.simulation.e03;

import java.util.Random;

public class EventGenerating extends Event {

    private int num_generated_entities;

    public EventGenerating(double time) {
        this.num_generated_entities = 0;
        this.time = time;
    }

    public void execute(Simulator sim) {

        // it is a real random in JAVA (Math.Random() is pseudo random)
        Random r = new Random();

        // if it is not working time, continue by 1 minute.
        if (Event.getHour(this.time) == Event.TIME_OFF) {
            this.time += 1;
            sim.insert(this);
            return;
        }

        this.num_generated_entities++;
        Customer c = new Customer(time, num_generated_entities);
        sim.getCustomer_list().add(c);
        c.max_num_customer = sim.getCustomer_queue().get(0).size();
        sim.getCustomer_queue().get(0).add(c);
        System.out.println("Customer_" + c.getId() + " arrived (with " + c.max_num_customer
                + " ahead) at time " + Event.printTime(c.time_generated));

        // if there is no one else in queue, this new customer get a service.
        if (sim.getCustomer_queue().get(0).size() == 1) { // &&sim.getEvents().getSize()==0
            EventServing s = new EventServing(this.time);
            sim.insert(s);
        }

        // new customers arrives randomly
        double next_event_time = this.time;
        switch (Event.getHour(this.time)) {
        case Event.TIME_11:
        case Event.TIME_12:
            next_event_time += r.nextInt(2) + 1; // 1-3 at 11-13
            break;
        case Event.TIME_13:
        case Event.TIME_14:
        case Event.TIME_15:
        case Event.TIME_16:
            next_event_time += r.nextInt(8) + 2; // 2-10 at 13-17
            break;
        case Event.TIME_17:
        case Event.TIME_18:
            next_event_time += r.nextInt(1) + 1; // 1-2 at 17-19
            break;
        case Event.TIME_19:
            next_event_time += r.nextInt(3) + 2; // 2-5 at 19-20
            break;
        }

        this.time = next_event_time;
        sim.insert(this);
    }

}
