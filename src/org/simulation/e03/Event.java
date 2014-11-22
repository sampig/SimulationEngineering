package org.simulation.e03;

public class Event {

    protected double time = 0.0; // time when event happens.

    public void execute(Simulator sim) {
    }

    /**
     * compare the event
     * 
     * @param e
     * @return <code>true</code> if this earlier than e; <code>false</code>
     *         otherwise.
     */
    public boolean earlierThan(Event e) {
        return this.time < e.time;
    }

    /**
     * Get corresponding hour according to the minutes of current time. For
     * instance, 0 represents Day_0 00:00, and 60 represents Day_0 01:00.
     * 
     * @param time
     *            the minutes of current time
     * @return <tt>Event.TIME_OFF</tt> if it is not working time (00:00-11:00
     *         and 20:00-24:00);<br>
     *         <tt>Event.TIME_11</tt> if it is 11:00-12:00;<br>
     *         <tt>Event.TIME_12</tt> if it is 12:00-13:00;<br>
     *         <tt>Event.TIME_13</tt> if it is 13:00-14:00;<br>
     *         <tt>Event.TIME_14</tt> if it is 14:00-15:00;<br>
     *         <tt>Event.TIME_15</tt> if it is 15:00-16:00;<br>
     *         <tt>Event.TIME_16</tt> if it is 16:00-17:00;<br>
     *         <tt>Event.TIME_17</tt> if it is 17:00-18:00;<br>
     *         <tt>Event.TIME_18</tt> if it is 18:00-19:00;<br>
     *         <tt>Event.TIME_19</tt> if it is 19:00-20:00;<br>
     */
    public static int getHour(double time) {
        if (time % 1440 <= 660 || time % 1440 > 1200) { // 0-11, 20-24
            return TIME_OFF;
        } else if (time % 1440 > 660 && time % 1440 <= 720) { // 11-12
            return TIME_11;
        } else if (time % 1440 > 720 && time % 1440 <= 13 * 60) { // 12-13
            return TIME_12;
        } else if (time % 1440 > 13 * 60 && time % 1440 <= 14 * 60) { // 13-14
            return TIME_13;
        } else if (time % 1440 > 14 * 60 && time % 1440 <= 15 * 60) { // 14-15
            return TIME_14;
        } else if (time % 1440 > 15 * 60 && time % 1440 <= 16 * 60) { // 15-16
            return TIME_15;
        } else if (time % 1440 > 16 * 60 && time % 1440 <= 17 * 60) { // 16-17
            return TIME_16;
        } else if (time % 1440 > 17 * 60 && time % 1440 <= 18 * 60) { // 17-18
            return TIME_17;
        } else if (time % 1440 > 18 * 60 && time % 1440 <= 19 * 60) { // 18-19
            return TIME_18;
        } else if (time % 1440 > 19 * 60 && time % 1440 <= 20 * 60) { // 19-20
            return TIME_19;
        }
        return TIME_OFF;
    }

    /**
     * in order to be friendly when printing
     * 
     * @param time
     *            the minutes of current time
     * @return a string format 'time (Day_d hh:mm)'
     */
    public static String printTime(double time) {
        int day = (int) (time / 1440);
        int hour = (int) (time % 1440 / 60);
        double minute = Math.round(((time % 1440) - 60 * hour) * 10) / 10.0;
        return time + "(" + "Day_" + day + " " + hour + "h:" + minute + "m)";
    }

    public double getTime() {
        return this.time;
    }

    // constants for the presentation of each hour.
    /**
     * rest time
     */
    public final static int TIME_OFF = 0; // rest time
    /**
     * 11:00-12:00
     */
    public final static int TIME_11 = 1; // 11:00-12:00
    /**
     * 12:00-13:00
     */
    public final static int TIME_12 = 2; // 12:00-13:00
    /**
     * 13:00-14:00
     */
    public final static int TIME_13 = 3; // 13:00-14:00
    /**
     * 14:00-15:00
     */
    public final static int TIME_14 = 4; // 14:00-15:00
    /**
     * 15:00-16:00
     */
    public final static int TIME_15 = 5; // 15:00-16:00
    /**
     * 16:00-17:00
     */
    public final static int TIME_16 = 6; // 16:00-17:00
    /**
     * 17:00-18:00
     */
    public final static int TIME_17 = 7; // 17:00-18:00
    /**
     * 18:00-19:00
     */
    public final static int TIME_18 = 8; // 18:00-19:00
    /**
     * 19:00-20:00
     */
    public final static int TIME_19 = 9; // 19:00-20:00
    public final static int TIME_20 = 10; //

}
