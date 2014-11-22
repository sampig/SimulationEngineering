package org.simulation.e03;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import org.simulation.e03.Customer;
import org.simulation.e03.IceCreamShopSimulator;

public class IceCreamShopCharts extends Application {

    public static void main(String... strings) {
        launch(strings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ice Cream Shop Charts");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,
                yAxis);

        lineChart.setTitle("Ice Cream Shop: ");

        lineChart.setCreateSymbols(false);
        lineChart.setAlternativeRowFillVisible(false);

        IceCreamShopSimulator icSim = new IceCreamShopSimulator(0, 1440,
                IceCreamShopSimulator.MODE_SINGLESERVER_SINGLEQUEUE,
                IceCreamShopSimulator.RANDOM_RANDOM);
        if (icSim.getMode() == IceCreamShopSimulator.MODE_MULTISERVER_SINGLEQUEUE) {
            icSim.setNum_servers(2);
        }
        icSim.start();
        icSim.run();
        List<Customer> list = icSim.getCustomer_list();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Waiting Time of Every Customer");
        for (Customer c : list) {
            double waiting_time = c.time_done - c.time_generated;
            series1.getData().add(
                    new XYChart.Data<String, Number>(
                            String.valueOf(c.time_generated), (waiting_time)));
        }

        Scene scene = new Scene(lineChart);
        lineChart.getData().add(series1);
        stage.setScene(scene);
        stage.show();
    }

}
