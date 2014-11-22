package org.simulation.e03;

import java.util.ArrayList;
import java.util.List;

public class EventList {

    private List<Event> list = new ArrayList<>(0);

    /**
     * insert an event into list ordered by its time.
     * 
     * @param e
     *            the event to be inserted
     */
    public void insert(Event e) {
        int i = 0;
        for (; i < list.size() && list.get(i).earlierThan(e); i++) {
            ;
        }
        list.add(i, e);
    }

    /**
     * remove the first event.
     * 
     * @return the first event
     */
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
