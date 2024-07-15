package ae.dubai.buid.cs.cs314.bt;
import ae.dubai.buid.cs.cs314.util.Utilities;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinearProbingHashGraph {

    public static void main(String[] args) {
        XYChart chart = createChart();

        new SwingWrapper<>(chart).displayChart();
    }

    private static XYChart createChart() {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .theme(Styler.ChartTheme.GGPlot2)
                .title("Linear Probing Hashing Insertion and Search Time")
                .xAxisTitle("Number of Elements")
                .yAxisTitle("Time (ms)")
                .build();

        List<Integer> xData = new ArrayList<>();
        List<Double> insertionYData = new ArrayList<>();
        List<Double> searchYData = new ArrayList<>();

        Random random = new Random();

        // Add data points (replace with your actual data)
        for (int i = 10; i <= 500; i += 20) {
            LinearProbingHashST<String, Integer> linearProbingHashST = new LinearProbingHashST<>();
            List<String> randomStrings = Utilities.generateRandomStrings(i);
            long startTime = System.nanoTime();

            // Insertion logic
            for (String randomString : randomStrings) {
                linearProbingHashST.put(randomString, 1); // Insert a dummy value
            }

            long endTime = System.nanoTime();
            double insertionExecutionTime = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds using double

            // Search logic
            startTime = System.nanoTime();
            for (int j = 0; j < randomStrings.size(); j++) {
                linearProbingHashST.get(randomStrings.get(j)); // Search for the inserted values
            }
            endTime = System.nanoTime();
            double searchExecutionTime = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds using double

            xData.add(i);
            insertionYData.add(insertionExecutionTime);
            searchYData.add(searchExecutionTime);
        }

        chart.addSeries("Linear Probing Hashing Insertion", xData, insertionYData);
        chart.addSeries("Linear Probing Hashing Search", xData, searchYData);

        return chart;
    }
}
