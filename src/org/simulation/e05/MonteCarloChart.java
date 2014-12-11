package org.simulation.e05;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MonteCarloChart extends Application {

    public static void main(String... strings) {
        launch(strings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Monte Carlo Simulation in Circus Trapeze Model");

        // Run the simulation.
        MonteCarloSimulation mcs = new MonteCarloSimulation();
        int times = 1000;
        mcs.setSimulationTimes(times);
        mcs.start();
        mcs.output();

        // length and period
        final NumberAxis xAxisLP = new NumberAxis(9.2, 10.7, 0.02);
        final NumberAxis yAxisLP = new NumberAxis(6.5, 7.2, 0.02);
        xAxisLP.setLabel("Length(m)");
        yAxisLP.setLabel("Period(s)");
        ScatterChart<Number, Number> scatterChartLP = new ScatterChart<>(xAxisLP, yAxisLP);
        scatterChartLP.setTitle("Length & Period: ");
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Length&Period");
        for (int i = 0; i < times; i++) {
            double l = mcs.getLengthList().get(i);
            double p = mcs.getPeriodList().get(i);
            series1.getData().add(new XYChart.Data<Number, Number>(l, p));
        }
        scatterChartLP.getData().add(series1);

        // velocity and period
        final NumberAxis xAxisVP = new NumberAxis(-0.37, -0.22, 0.005);
        final NumberAxis yAxisVP = new NumberAxis(6.5, 7.2, 0.02);
        xAxisVP.setLabel("Velocity(r/s)");
        yAxisVP.setLabel("Period(s)");
        ScatterChart<Number, Number> scatterChartVP = new ScatterChart<>(xAxisVP, yAxisVP);
        scatterChartVP.setTitle("Velocity & Period: ");
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Velocity&Period");
        for (int i = 0; i < times; i++) {
            double v = mcs.getVelocityList().get(i);
            double p = mcs.getPeriodList().get(i);
            series2.getData().add(new XYChart.Data<Number, Number>(v, p));
        }
        scatterChartVP.getData().add(series2);

        // angle and period
        final NumberAxis xAxisAP = new NumberAxis(52, 67, 0.1);
        final NumberAxis yAxisAP = new NumberAxis(6.5, 7.2, 0.02);
        xAxisAP.setLabel("Angle(degree)");
        yAxisAP.setLabel("Period(s)");
        ScatterChart<Number, Number> scatterChartAP = new ScatterChart<>(xAxisAP, yAxisAP);
        scatterChartAP.setTitle("Angle & Period: ");
        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.setName("Angle&Period");
        for (int i = 0; i < times; i++) {
            double a = Math.toDegrees(mcs.getThetaList().get(i));
            double p = mcs.getPeriodList().get(i);
            series3.getData().add(new XYChart.Data<Number, Number>(a, p));
        }
        scatterChartAP.getData().add(series3);

        Scene scene = new Scene(new Group());

        VBox vbox = new VBox();
        vbox.setLayoutX(10);
        vbox.setLayoutY(10);
        HBox hbox1 = new HBox();
        hbox1.getChildren().add(scatterChartLP);
        hbox1.getChildren().add(scatterChartVP);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(scatterChartAP);
        ((Group) scene.getRoot()).getChildren().add(vbox);
        // ((Group) scene.getRoot()).getChildren().add(scatterChartLP);
        // ((Group) scene.getRoot()).getChildren().add(scatterChartVP);

        stage.setScene(scene);
        // stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();
    }

}
