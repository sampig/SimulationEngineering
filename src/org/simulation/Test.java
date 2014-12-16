package org.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.simulation.e04.AcrobatStateImpr;

public class Test {

    public static void main(String... strings) {
        Test t = new Test();
        //t.normalDistribution();
        t.testRandom();
        //t.testBitOperator();
    }

    public void print() {
        for (int i = 0; i < 10; i++) {
            double n = Math.random() * 10;
            System.out.println(n);
        }
    }

    public void testRandom() {
        Random r = new Random();
        r.setSeed(1);
        Map<Integer, Integer> map = new HashMap<>(0);
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int n = r.nextInt(3) + 1;
            Integer count = map.get(n);
            map.put(n, (count == null ? 1 : count + 1));
        }
        System.out.println(map);
        map.clear();
        for (int i = 0; i < times; i++) {
            int n = r.nextInt(9) + 2;
            Integer count = map.get(n);
            map.put(n, (count == null ? 1 : count + 1));
        }
        System.out.println(map);
        map.clear();
        for (int i = 0; i < times; i++) {
            int n = r.nextInt(2) + 1;
            Integer count = map.get(n);
            map.put(n, (count == null ? 1 : count + 1));
        }
        System.out.println(map);
        map.clear();
        for (int i = 0; i < times; i++) {
            int n = r.nextInt(4) + 2;
            Integer count = map.get(n);
            map.put(n, (count == null ? 1 : count + 1));
        }
        System.out.println(map);
    }

    public void normalDistribution() {
        Random r = new Random();
        Map<String, Integer> map = new HashMap<>(0);
        // standard
        map.put("1", 0);
        map.put("2", 0);
        map.put("3", 0);
        map.put("4", 0);
        for (int i = 0; i < 1000; i++) {
            double d = r.nextGaussian();
            // System.out.println(d);
            if (Math.abs(d) <= 1) {
                int n = map.get("1");
                map.put("1", n + 1);
            } else if (Math.abs(d) <= 2) {
                int n = map.get("2");
                map.put("2", n + 1);
            } else if (Math.abs(d) <= 3) {
                int n = map.get("3");
                map.put("3", n + 1);
            } else {
                int n = map.get("4");
                map.put("4", n + 1);
            }
        }
        System.out.println(map);
        //
        map.clear();
        int start = 1;
        int end = 3;
        String str = "[" + start + "," + end + "]";
        map.put(str, 0);
        map.put("other", 0);
        for (int i = 0; i < 1000; i++) {
            double d = r.nextGaussian() * ((end - start) / 4.0) + (end + start) / 2.0;
            // System.out.println(d);
            if (d >= start && d <= end) {
                int n = map.get(str);
                map.put(str, n + 1);
            } else {
                int n = map.get("other");
                map.put("other", n + 1);
            }
        }
        System.out.println(map);
        //
        map.clear();
        start = 1;
        end = 5;
        str = "[" + start + "," + end + "]";
        map.put(str, 0);
        map.put("other", 0);
        for (int i = 0; i < 1000; i++) {
            int d = (int) Math.round(r.nextGaussian() * ((end - start) / 4.0) + (end + start)
                    / 2.0);
            // System.out.println(d);
            if (d >= start && d <= end) {
                int n = map.get(str);
                map.put(str, n + 1);
            } else {
                int n = map.get("other");
                map.put("other", n + 1);
            }
        }
        System.out.println(map);
        System.out.println(5.1 + ": " + Math.round(5.1));
        System.out.println(5.9 + ": " + Math.round(5.9));
    }

    public void testClone() {
        AcrobatStateImpr asi = new AcrobatStateImpr();
        AcrobatStateImpr asi2 = asi.clone();
        AcrobatStateImpr asi3 = asi;
        System.out.println(asi.getValues());
        System.out.println(asi2.getValues());
        System.out.println(asi3.getValues());
        asi.setValues(100);
        System.out.println(asi.getValues());
        System.out.println(asi2.getValues());
        System.out.println(asi3.getValues());
        AcrobatStateImpr a = new AcrobatStateImpr();
        AcrobatStateImpr b = new AcrobatStateImpr();
        a.setValues(11);
        a.setNext(b);
        System.out.println(a.getValues() + "," + a.getNext().getValues());
        AcrobatStateImpr c = a.clone();
        System.out.println(c.getValues() + "," + c.getNext().getValues());
        b.setValues(22);
        System.out.println(a.getValues() + "," + a.getNext().getValues());
        System.out.println(c.getValues() + "," + c.getNext().getValues());
    }

    public void testBitOperator() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + (i & 0x2) + ", " + (i & 2));
        }
    }

}
