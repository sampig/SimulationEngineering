package org.simulation.e03;

public class Event {

    protected double time = 0.0;

    public void execute(Simulator sim) {
    }

    public boolean earlierThan(Event e) {
        return this.time < e.time;
    }

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

    public static String printTime(double time) {
        int day = (int) (time / 1440);
        int hour = (int) (time % 1440 / 60);
        double minute = Math.round(((time % 1440) - 60 * hour) * 10) / 10.0;
        return time + "(" + "Day_" + day + " " + hour + "h:" + minute + "m)";
    }

    public double getTime() {
        return this.time;
    }

    public final static int TIME_OFF = 0;
    public final static int TIME_11 = 1;
    public final static int TIME_12 = 2;
    public final static int TIME_13 = 3;
    public final static int TIME_14 = 4;
    public final static int TIME_15 = 5;
    public final static int TIME_16 = 6;
    public final static int TIME_17 = 7;
    public final static int TIME_18 = 8;
    public final static int TIME_19 = 9;
    public final static int TIME_20 = 10;

}
