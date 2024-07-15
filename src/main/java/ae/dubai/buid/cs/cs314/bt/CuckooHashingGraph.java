package ae.dubai.buid.cs.cs314.bt;
import ae.dubai.buid.cs.cs314.util.Utilities;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;

public class CuckooHashingGraph {

    public static void main(String[] args) {
        new SwingWrapper<>(createChart()).displayChart();
    }

    private static XYChart createChart() {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .theme(Styler.ChartTheme.GGPlot2)
                .title("Cuckoo Hashing Execution Time")
                .xAxisTitle("Number of Elements")
                .yAxisTitle("Time (ms)")
                .build();

        List<Integer> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>(); // Use Double for the y-axis

        // Add data points (replace with your actual data)
        for (int i = 10; i <= 1000; i += 10) {
            long startTime = System.nanoTime();

            // Replace the code below with your Cuckoo hashing insertion logic
            CuckooNode<String> cuckooNode = new CuckooNode<>();
            List<String> randomStrings = Utilities.generateRandomStrings(i);
            for (String randomString : randomStrings) {
                cuckooNode.insertCuckoo(randomString);
            }

            long endTime = System.nanoTime();
            double executionTime = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds using double

            xData.add(i);
            yData.add(executionTime);
        }

        chart.addSeries("Cuckoo Hashing", xData, yData);

        return chart;
    }
}