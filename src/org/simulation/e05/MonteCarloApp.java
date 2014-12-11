/**
 * MonteCarloApp.java
 * 
 * Author: Chenfeng Zhu
 * Date  : 2014-12-02
 */
package org.simulation.e05;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MonteCarloApp extends Application {

    private List<Double> xList = new ArrayList<>(0);
    private List<Double> yList = new ArrayList<>(0);

    public static void main(String... strings) {
        launch(strings);
    }

    public void setList(List<Double> xList, List<Double> yList) {
        this.xList.clear();
        this.yList.clear();
        this.xList = xList;
        this.yList = yList;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Monte Carlo Simulation");
        
        final NumberAxis xAxis = new NumberAxis(-0.5, 11.0, 0.1);
        final NumberAxis yAxis = new NumberAxis(6.5, 7.2, 0.02);
        xAxis.setLabel("Length, Velocity, Angle");
        yAxis.setLabel("Period(s)");
        final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

        scatterChart.setTitle("Circus Trapeze Model: ");

        MonteCarloSimulation mcs = new MonteCarloSimulation();
        int times = 1000;
        mcs.setSimulationTimes(times);
        mcs.start();
        mcs.output();

        // length and period
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Length&Period");
        for (int i = 0; i < times; i++) {
            double l = mcs.getLengthList().get(i);
            double p = mcs.getPeriodList().get(i);
            series1.getData().add(new XYChart.Data<Number, Number>(l, p));
        }

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Velocity&Period");
        for (int i = 0; i < times; i++) {
            double v = mcs.getVelocityList().get(i);
            double p = mcs.getPeriodList().get(i);
            series2.getData().add(new XYChart.Data<Number, Number>(v, p));
        }

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.setName("Angle&Period");
        for (int i = 0; i < times; i++) {
            double a = mcs.getThetaList().get(i);
            double p = mcs.getPeriodList().get(i);
            series3.getData().add(new XYChart.Data<Number, Number>(a, p));
        }

        scatterChart.getData().add(series1);
        scatterChart.getData().add(series2);
        scatterChart.getData().add(series3);

        Button button2 = new Button("Accept");
        button2.setOnAction((ActionEvent e) -> {
            ;
        });

        Scene scene = new Scene(new Group());

        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);
        HBox hbox1 = new HBox();
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(button2);
//        ((Group) scene.getRoot()).getChildren().add(vbox);
        ((Group) scene.getRoot()).getChildren().add(scatterChart);

        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();

        xAxis.setLowerBound(9.0);
        xAxis.setUpperBound(11.0);
    }

}
