package org.simulation.e04;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CircusTrapezeImprApp extends Application {

    public static void main(String... strings) {
        launch(strings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pendulum Model");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        double start = 0;
        double end = 10;
        double step = 0.01;
        int status = CircusTrapezeImpr.STATUS_STATIC;

        CircusTrapezeImpr ctiEULER = new CircusTrapezeImpr(start, end, step, status,
                MethodType.getInstanceEULER());
        ctiEULER.start();
        ctiEULER.outputResult();
        List<AcrobatStateImpr> listEULER = ctiEULER.getList();

        CircusTrapezeImpr ctiRK2 = new CircusTrapezeImpr(start, end, step, status,
                MethodType.getInstanceRK2());
        ctiRK2.start();
        //ctiRK2.outputResult();
        List<AcrobatStateImpr> listRK2 = ctiRK2.getList();

        CircusTrapezeImpr ctiRK4 = new CircusTrapezeImpr(start, end, step, status,
                MethodType.getInstanceRK4());
        ctiRK4.start();
        ctiRK4.outputResult();
        List<AcrobatStateImpr> listRK4 = ctiRK4.getList();

        lineChart.setTitle("Improvement of Circus Trapeze Model: ");

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Angle: " + ctiEULER.method.getMethodName());
        for (AcrobatStateImpr s : listEULER) {
            series1.getData().add(
                    new XYChart.Data<>(String.valueOf(s.getTime()), (s.getValues())));
        }

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(ctiRK2.method.getMethodName());
        for (AcrobatStateImpr s : listRK2) {
            series2.getData().add(
                    new XYChart.Data<>(String.valueOf(s.getTime()), s.getValues()));
        }

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName(ctiRK4.method.getMethodName());
        for (AcrobatStateImpr s : listRK4) {
            series3.getData().add(
                    new XYChart.Data<>(String.valueOf(s.getTime()), s.getValues()));
        }

        Scene scene = new Scene(lineChart);
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        lineChart.getData().add(series3);
        stage.setScene(scene);
        stage.show();
    }

}
