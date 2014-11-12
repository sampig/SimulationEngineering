package org.simulation.e03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IceCreamShopSimulator extends Simulator {

    public final static int MODE_SINGLESERVER_SINGLEQUEUE = 1;
    public final static int MODE_SINGLESERVER_MULTIQUEUE = 2;
    public final static int MODE_MULTISERVER_SINGLEQUEUE = 3;
    public final static int MODE_MULTISERVER_MULTIQUEUE = 4;
    private int mode;
    private int num_servers;
    private int num_queues;
    private boolean flag = false;

    // public IceCreamShopSimulator() {
    // super(0);
    // this.setEnd_time(1440);
    // this.mode = 1;
    // }

    public IceCreamShopSimulator(double start_time, double end_time, int mode) {
        super(start_time);
        this.setEnd_time(end_time);
        this.mode = mode;
    }

    public void start() {
        List<Customer> list;
        System.out.println("Simulation is ready to start. RUN_MODE is " + mode);
        System.out.println("Start from " + this.getStart_time() + "("
                + Event.printTime(this.getStart_time()) + ") to " + this.getEnd_time() + "("
                + Event.printTime(this.getEnd_time()) + ").");
        flag = true;
        switch (mode) {
        case MODE_SINGLESERVER_SINGLEQUEUE: // single server, single queue.
            list = new ArrayList<>(0);
            this.getCustomer_queue().add(list);
            break;
        case MODE_SINGLESERVER_MULTIQUEUE: // single server, multiple queues.
            for (int i = 0; i < num_queues; i++) {
                list = new ArrayList<>(0);
                this.getCustomer_queue().add(list);
            }
            break;
        case MODE_MULTISERVER_SINGLEQUEUE: // multiple servers, single queue.
            list = new ArrayList<>(0);
            this.getCustomer_queue().add(list);
            for (int i = 0; i < num_servers; i++) {
                ;
            }
            break;
        default:
            System.out.println("Run Mode: error.");
            return;
        }
    }

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

    private void printResult() {
        System.out.println("*****Simualtion result:");
        System.out.println("Time: " + this.getStart_time() + "-" + this.getEnd_time());
        System.out.println("Customer Quantity: " + this.getCustomer_list().size());
        int sum_num_scoops = 0;
        double avg_num_scoops = 0;
        double avg_waiting_time = 0;
        Customer customer = new Customer();
        Map<Integer, Double> map_waiting_time = new HashMap<>();
        Map<Integer, Integer> map_customer = new HashMap<>();
        for (Customer c : this.getCustomer_list()) {
            sum_num_scoops += c.getNum_scoops();
            double waiting_time = (c.time_done - c.time_generated);
            avg_waiting_time += waiting_time;
            if (c.max_num_customer > customer.max_num_customer) {
                customer = c;
            }
            int hour = Event.getHour(c.time_generated);
            Integer num = map_customer.get(hour);
            map_customer.put(hour, (num == null ? 1 : num + 1));
            Double time = map_waiting_time.get(hour);
            map_waiting_time.put(hour, (time == null ? waiting_time : time + waiting_time));
        }
        System.out.println("Total scoops made: " + sum_num_scoops);
        avg_num_scoops = sum_num_scoops * 1.0 / this.getCustomer_list().size();
        System.out.println("Average scoops made: " + avg_num_scoops);
        System.out.println("The customer waiting longest: " + customer.toString());
        System.out.println("Total waiting time: " + avg_waiting_time);
        avg_waiting_time /= this.getCustomer_list().size();
        System.out.println("Average waiting time: " + avg_waiting_time);
        System.out.println("Time-customers: " + map_customer);
        System.out.println("Time-waiting time: " + map_waiting_time);
    }

}
