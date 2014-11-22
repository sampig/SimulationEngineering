package org.simulation.e03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IceCreamShopSimulator extends Simulator {

    private boolean flag = false; // flag for the preparation of simulation

    public IceCreamShopSimulator(double start_time, double end_time) {
        super(start_time);
        this.setEnd_time(end_time);
        this.mode = MODE_SINGLESERVER_SINGLEQUEUE; // default
        this.type_random = RANDOM_RANDOM;
    }

    public IceCreamShopSimulator(double start_time, double end_time, int mode, int random) {
        super(start_time);
        this.setEnd_time(end_time);
        this.mode = mode;
        this.type_random = random;
    }

    /**
     * Prepare for the simulation. Must use this method before running the
     * simulation!
     */
    public void start() {
        List<Customer> list;
        System.out.println("Simulation is ready to start.");
        System.out.println("RUN_MODE is: " + printMode());
        System.out.println("Start from " + this.getStart_time() + "("
                + Event.printTime(this.getStart_time()) + ") to " + this.getEnd_time() + "("
                + Event.printTime(this.getEnd_time()) + ").");
        flag = true;
        switch (mode) {
        case MODE_SINGLESERVER_SINGLEQUEUE: // single server, single queue.
        case MODE_MULTISERVER_SINGLEQUEUE: // multiple servers, single queue.
        case MODE_MIKE_TEST:
            list = new ArrayList<>(0);
            this.getCustomer_queue().add(list);
            break;
        case MODE_MULTISERVER_MULTIQUEUE: // multiple servers, multiple queues.
            for (int i = 0; i < num_queues; i++) {
                list = new ArrayList<>(0);
                this.getCustomer_queue().add(list);
            }
            break;
        default:
            System.out.println("Run Mode: error.");
            return;
        }
    }

    /**
     * Run the simulation after <tt>start()</tt>.
     */
    public void run() {
        if (!flag) {
            System.out.println("Simulation is not ready to start.");
            return;
        }
        System.out.println("Start to run: ");
        super.events = new EventList();
        super.insert(new EventGenerating(super.getTime()));
        super.doAllEvents();
        System.out.println("Simulation ends.");
        printResult();
    }

    /**
     * Print the simulation result.
     */
    private void printResult() {
        System.out.println("*****Simualtion result:");
        System.out.println("Time: " + this.getStart_time() + "-" + this.getEnd_time());
        System.out.println("Customer Quantity: " + this.getCustomer_list().size());
        int sum_num_scoops = 0;
        double avg_num_scoops = 0;
        double avg_waiting_time = 0;
        Customer customer_m = new Customer();
        Customer customer_l = new Customer();
        Map<Integer, Double> map_waiting_time = new HashMap<>();
        Map<Integer, Integer> map_customer = new HashMap<>();
        Map<Integer, Double> map_scoops = new HashMap<>();
        for (Customer c : this.getCustomer_list()) {
            sum_num_scoops += c.getNum_scoops();
            double waiting_time = (c.time_done - c.time_generated);
            avg_waiting_time += waiting_time;
            if (c.max_num_customer > customer_m.max_num_customer) {
                customer_m = c;
            }
            if (waiting_time > (customer_l.time_done - customer_l.time_generated)) {
                customer_l = c;
            }
            int hour = Event.getHour(c.time_generated);
            Integer num = map_customer.get(hour);
            map_customer.put(hour, (num == null ? 1 : num + 1));
            Double time = map_waiting_time.get(hour);
            map_waiting_time.put(hour, (time == null ? waiting_time : time + waiting_time));
            int scoop = c.getNum_scoops();
            Double scoops = map_scoops.get(hour);
            map_scoops.put(hour, (scoops == null ? scoop : scoops + scoop));
        }
        System.out.println("Total scoops made: " + sum_num_scoops);
        avg_num_scoops = (sum_num_scoops * 1.0 / this.getCustomer_list().size());
        System.out.println("Average scoops per customer: " + avg_num_scoops);
        System.out.println("The customer with most people ahead: " + customer_m.toString());
        System.out.println("The customer waiting longest: " + customer_l.toString());
        System.out.println("Total waiting time: " + avg_waiting_time);
        avg_waiting_time /= this.getCustomer_list().size();
        System.out.println("Average waiting time per customer: " + avg_waiting_time);
        // by periods:
        System.out.println("Customers at different periods: " + map_customer);
        System.out.println("Total scoops at different periods: " + map_scoops);
        System.out.println("Total waiting time at different periods: " + map_waiting_time);
        for (Integer hour : map_waiting_time.keySet()) {
            Double time = map_waiting_time.get(hour);
            Integer num = map_customer.get(hour);
            Double scoops = map_scoops.get(hour);
            if (num == null) {
                continue;
            }
            map_waiting_time.put(hour, (int) (time * 1000) / num / 1000.0);
            map_scoops.put(hour, (int) (scoops * 1000) / num / 1000.0);
        }
        System.out.println("Average scoops at different periods: " + map_scoops);
        System.out.println("Average waiting time at different periods: " + map_waiting_time);
    }

    /**
     * Get the string of current mode.
     * 
     * @return a string
     */
    public String printMode() {
        String str = "";
        switch (mode) {
        case MODE_SINGLESERVER_SINGLEQUEUE: // single server, single queue.
            str = "Single server, Single queue";
            break;
        case MODE_MULTISERVER_MULTIQUEUE: // multiple servers, multiple queues.
            str = "Multiple servers, Multiple queues";
            break;
        case MODE_MULTISERVER_SINGLEQUEUE: // multiple servers, single queue.
            str = "Multiple servers(" + this.num_servers + "), Single queue";
            break;
        case MODE_MIKE_TEST: // Mike hires a part-time employee
            str = "Mike hires a part-time employee";
            break;
        default:
            break;
        }
        return str;
    }

}
