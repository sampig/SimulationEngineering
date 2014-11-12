package org.simulation.w03;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    
    List<Event> list = new ArrayList<>(0);
    
    public void insert(Event e) {
        int i=0;
        for(;i<list.size()&&list.get(i).lessThan(e);) {
            i++;
        }
        list.add(i, e);;
    }
    
    public Event removeFirst() {
        Event e = list.get(0);
        list.remove(0);
        return e;
    }
    
    public int size() {
        return list.size();
    }

}
