package org.simulation.charts;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import org.simulation.w2.CircusTrapeze;
import org.simulation.w2.CircusTrapeze.AcrobatState;

public class CircusTrapezeApp extends Application {

    public static void main(String... strings) {
        launch(strings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Simple Pendulum Model");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,
                yAxis);

        lineChart.setTitle("Circus Trapeze Model: ");

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        CircusTrapeze ct = new CircusTrapeze();
        ct.start(CircusTrapeze.STATUS_STATIC);
        List<AcrobatState> list = ct.getState_list();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Angle");
        for (AcrobatState s : list) {
            series1.getData().add(
                    new XYChart.Data<String, Number>(
                            String.valueOf(s.getTime()), (s.getAngle())));
        }

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Velocity");
        for (AcrobatState s : list) {
            series2.getData()
                    .add(new XYChart.Data<String, Number>(String.valueOf(s
                            .getTime()), s.getAngle_velocity()));
        }

        Scene scene = new Scene(lineChart);
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        // oList.addAll(series1,series2);
        // scene.getStylesheets().add("linechartsample/Chart.css");
        stage.setScene(scene);
        stage.show();
    }

}
