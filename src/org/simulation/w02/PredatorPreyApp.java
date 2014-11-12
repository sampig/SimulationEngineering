package org.simulation.w02;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import org.simulation.w02.PredatorPrey.State_PredatorPrey;

public class PredatorPreyApp extends Application {

    public static void main(String... strings) {
        launch(strings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Predator-Prey Model");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,
                yAxis);

        lineChart.setTitle("Predator-Prey Model: ");

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        PredatorPrey pp = new PredatorPrey(2014.0, 2018.0);
        pp.start();
        List<State_PredatorPrey> list = pp.getState_list();
        
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Rabbits");
        for (State_PredatorPrey s : list) {
            series1.getData().add(
                    new XYChart.Data<String, Number>(
                            String.valueOf(s.getTime()), s.getRabits_num()));
        }
        
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Lynxes");
        for (State_PredatorPrey s : list) {
            series2.getData().add(
                    new XYChart.Data<String, Number>(
                            String.valueOf(s.getTime()), s.getLynxes_num()));
        }

        Scene scene = new Scene(lineChart);
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        //oList.addAll(series1,series2);
        // scene.getStylesheets().add("linechartsample/Chart.css");
        stage.setScene(scene);
        stage.show();
    }

}
