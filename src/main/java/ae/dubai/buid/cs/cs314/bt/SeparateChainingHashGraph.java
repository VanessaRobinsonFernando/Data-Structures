package ae.dubai.buid.cs.cs314.bt;
import ae.dubai.buid.cs.cs314.util.Utilities;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeparateChainingHashGraph {

    public static void main(String[] args) {
        XYChart chart = createChart();

        new SwingWrapper<>(chart).displayChart();
    }

    private static XYChart createChart() {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .theme(Styler.ChartTheme.GGPlot2)
                .title("Separate Chaining Hashing Insertion and Search Time")
                .xAxisTitle("Number of Elements")
                .yAxisTitle("Time (ms)")
                .build();

        List<Integer> xData = new ArrayList<>();
        List<Double> insertionYData = new ArrayList<>();
        List<Double> searchYData = new ArrayList<>();

        Random random = new Random();

        // Add data points (replace with your actual data)
        for (int i = 10; i <= 500; i += 20) {
            SeparateChainingHashST<String, Integer> separateChainingHashST = new SeparateChainingHashST<>();
            List<String> randomStrings = Utilities.generateRandomStrings(i);
            long insertionStartTime = System.nanoTime();

            // Insertion logic
            for (int j = 0; j < randomStrings.size(); j++) {
                separateChainingHashST.put(randomStrings.get(j), 1); // Insert a dummy value
            }

            long insertionEndTime = System.nanoTime();
            double insertionExecutionTime = (insertionEndTime - insertionStartTime) / 1_000_000.0; // Convert to milliseconds using double

            // Search logic
            long searchStartTime = System.nanoTime();
            for (int j = 0; j < randomStrings.size(); j++) {
                separateChainingHashST.get(randomStrings.get(j)); // Search for the inserted values
            }
            long searchEndTime = System.nanoTime();
            double searchExecutionTime = (searchEndTime - searchStartTime) / 1_000_000.0; // Convert to milliseconds using double

            xData.add(i);
            insertionYData.add(insertionExecutionTime);
            searchYData.add(searchExecutionTime);
        }

        chart.addSeries("Separate Chaining Hashing Insertion", xData, insertionYData);
        chart.addSeries("Separate Chaining Hashing Search", xData, searchYData);

        return chart;
    }
}
