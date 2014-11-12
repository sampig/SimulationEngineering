package org.simulation.w02;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import org.simulation.w02.MassSpringDashpot.MassState;

public class MassSpringDashpotApp extends Application {

    public static void main(String... strings) {
        launch(strings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mass Spring Dashpot Model");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,
                yAxis);

        lineChart.setTitle("Mass Spring Dashpot Model: ");

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        MassSpringDashpot msd = new MassSpringDashpot();
        msd.start();
        List<MassState> list = msd.getStateList();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Position");
        for (MassState s : list) {
            series1.getData().add(
                    new XYChart.Data<String, Number>(
                            String.valueOf(s.getTime()), (s.getPosition())));
        }

        Scene scene = new Scene(lineChart);
        lineChart.getData().add(series1);
        stage.setScene(scene);
        stage.show();
    }

}
