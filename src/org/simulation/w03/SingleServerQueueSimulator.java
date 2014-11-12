package org.simulation.w03;

import java.util.ArrayList;
import java.util.List;

public class SingleServerQueueSimulator extends Simulator {
    
    public SingleServerQueueSimulator() {
        this.time = 0;
        this.end_time = 500;
    }
    
    public void start() {
        List<Entity> q = new ArrayList<>(0);
        this.queue_list.addAll(q);
    }
    
    public void run() {
        events = new EventList();
        this.insert(new Generating(0.0));
        this.doAllEvents();
    }

}
