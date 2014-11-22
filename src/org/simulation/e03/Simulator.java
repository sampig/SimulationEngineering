package org.simulation.e03;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    protected int mode; // queue mode
    protected int num_servers = 1; // number of servers
    protected int num_queues = 1; // number of queues

    private double time; // minutes of time: 0h-24h (0m-1440m)
    private double start_time = 0;
    private double end_time = 1440;
    // save all customers' information.
    private List<Customer> customer_list = new ArrayList<>(0);
    // there could be multiple queues.
    private List<List<Customer>> customer_queue = new ArrayList<>(0);
    // there could be multiple servers.
    private List<Customer> server_queue = new ArrayList<>(0);

    // store all events.
    protected EventList events = new EventList();

    // random type
    protected int type_random;

    public Simulator() {
        this.time = start_time;
    }

    public Simulator(double time) {
        this.time = time;
    }

    public double now() {
        return time;
    }

    public void insert(Event e) {
        events.insert(e);
    }

    public Event cancel(Event e) {
        return e;
    }

    /**
     * deal with all the events.
     */
    public void doAllEvents() {
        Event e = new Event();
        for (; events.getSize() > 0 || this.time < end_time;) {
            e = events.remove();
            time = e.getTime();
            e.execute(this);
            // if it is the end of the simulation,
            // there won't be a new generation.
            if (this.time > end_time) {
                for (int i = 0; i < events.getSize();) {
                    Event ev = events.getList().get(i);
                    if (EventGenerating.class.isInstance(ev)) {
                        events.getList().remove(i);
                    } else {
                        i++;
                    }
                }
            }
            // the part-time employee would work during 12-13
            if (this.mode == MODE_MIKE_TEST) {
                if (Event.getHour(this.time) == Event.TIME_12) {
                    this.setNum_servers(2);
                } else {
                    this.setNum_servers(1);
                }
            }
        }
    }

    // constants: Mode of the simulation
    /**
     * single server, single queue
     */
    public final static int MODE_SINGLESERVER_SINGLEQUEUE = 1;
    /**
    *
    */
    public final static int MODE_SINGLESERVER_MULTIQUEUE = 2;
    /**
     * multiple servers, single queue
     */
    public final static int MODE_MULTISERVER_SINGLEQUEUE = 3;
    /**
    *
    */
    public final static int MODE_MULTISERVER_MULTIQUEUE = 4;
    /**
     * experiment of Mike
     */
    public final static int MODE_MIKE_TEST = 5;
    
    // constants: type of the random
    /**
     * random
     */
    public final static int RANDOM_RANDOM = 0;
    /**
     * random based on normal distribution with 1 standard deviations
     */
    public final static int RANDOM_NORMAL_DISTRIBUTION_1 = 1;
    /**
     * random based on normal distribution with 2 standard deviations
     */
    public final static int RANDOM_NORMAL_DISTRIBUTION_2 = 2;

    public List<Customer> getCustomer_list() {
        return customer_list;
    }

    public List<List<Customer>> getCustomer_queue() {
        return customer_queue;
    }

    public List<Customer> getServer_queue() {
        return server_queue;
    }

    public EventList getEvents() {
        return events;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getStart_time() {
        return start_time;
    }

    public void setStart_time(double start_time) {
        this.start_time = start_time;
    }

    public double getEnd_time() {
        return end_time;
    }

    public void setEnd_time(double end_time) {
        this.end_time = end_time;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getNum_servers() {
        return num_servers;
    }

    public void setNum_servers(int num_servers) {
        if (num_servers < 1) {
            this.num_servers = 1;
            return;
        }
        this.num_servers = num_servers;
    }

    public void setNum_queues(int num_queues) {
        this.num_queues = num_queues;
    }

    public int getType_random() {
        return type_random;
    }

    public void setType_random(int type_random) {
        if (type_random < 0) {
            this.type_random = 0;
            return;
        }
        this.type_random = type_random;
    }

}
