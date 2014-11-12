package org.simulation.e03;

import java.util.ArrayList;
import java.util.List;

public class EventList {

    private List<Event> list = new ArrayList<>(0);

    public void insert(Event e) {
        int i = 0;
        for (; i < list.size() && list.get(i).earlierThan(e); i++) {
            ;
        }
        list.add(i, e);
    }

    public Event remove() {
        Event e = list.get(0);
        list.remove(0);
        return e;
    }

    public int getSize() {
        return list.size();
    }

    public List<Event> getList() {
        return list;
    }

}
