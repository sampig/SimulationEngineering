package org.simulation.e04;

import java.util.ArrayList;
import java.util.List;

public class StateList {

    private int frameCount = 0;
    private double stepTime = 0;
    int stepCount = 0;

    private AcrobatStateImpr state;

    private List<AcrobatStateImpr> list = new ArrayList<AcrobatStateImpr>(0);

    public void init(double step_time) {
        if (step_time <= 0) {
            step_time = 0.1;
            return;
        }
        frameCount = 0;
        stepTime = step_time;
        for (AcrobatStateImpr s = state; s != null; s = s.getNext()) {
            ;
        }
    }

    public void addState(AcrobatStateImpr s) {
        s.setNext(this.state);
        this.state = s;
    }

    public void integrate() {
        for (AcrobatStateImpr s = state; s != null; s = s.getNext()) {
            s.update(this);
        }
        frameCount++;
    }

    public double getTime() {
        return (int) (frameCount * stepTime * 1000) / 1000.0;
    }

    public void doubleStepTime() {
        this.stepTime *= 2;
        System.out.println(this.stepTime);
    }

    public void halveStepTime() {
        this.stepTime /= 2;
        System.out.println(this.stepTime);
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public double getStepTime() {
        return stepTime;
    }

    public void setStepTime(double stepTime) {
        this.stepTime = stepTime;
    }

    public AcrobatStateImpr getState() {
        return this.state;
    }
    
    public void setState(AcrobatStateImpr state) {
        this.state = state;
    }

    public List<AcrobatStateImpr> getList() {
        return list;
    }

    public void setList(List<AcrobatStateImpr> list) {
        this.list = list;
    }

}
