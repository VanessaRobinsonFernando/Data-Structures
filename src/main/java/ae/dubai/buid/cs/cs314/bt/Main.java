package ae.dubai.buid.cs.cs314.bt;

import ae.dubai.buid.cs.cs314.util.Utilities;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import java.text.DecimalFormat;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        plotInsertionAndSearchTime();
    }

    private static void plotInsertionAndSearchTime() {
        List<Integer> sizes = new ArrayList<>();
        List<Double> bloomFilterInsertionTimes = new ArrayList<>();
        List<Double> hashSetInsertionTimes = new ArrayList<>();
        List<Double> bloomFilterSearchTimes = new ArrayList<>();
        List<Double> hashSetSearchTimes = new ArrayList<>();

        for (int size : new int[]{1000, 5000, 10000,50000, 100000, 1000000}) {
            sizes.add(size);

            List<Integer> randomIntegers = Utilities.generateRandomIntegers(size);
            List<Integer> elementsToInsert = randomIntegers.subList(0, size / 2);
            List<Integer> mixedElements = mixElements(elementsToInsert);

            // Record insertion time for Bloom Filter
            long bloomFilterInsertionStartTime = System.currentTimeMillis();
            testInsertionTimeBloomFilter(elementsToInsert);
            long bloomFilterInsertionEndTime = System.currentTimeMillis();
            bloomFilterInsertionTimes.add((double) (bloomFilterInsertionEndTime - bloomFilterInsertionStartTime));

            // Record insertion time for HashSet
            long hashSetInsertionStartTime = System.currentTimeMillis();
            testInsertionTimeHashSet(elementsToInsert);
            long hashSetInsertionEndTime = System.currentTimeMillis();
            hashSetInsertionTimes.add((double) (hashSetInsertionEndTime - hashSetInsertionStartTime));

            // Record search time for Bloom Filter
            BloomFilter<Integer> bloomFilter = new BloomFilter<>(3, 1000);
            long bloomFilterSearchStartTime = System.currentTimeMillis();
            testContainmentBloomFilter(bloomFilter, mixedElements);
            long bloomFilterSearchEndTime = System.currentTimeMillis();
            bloomFilterSearchTimes.add((double) (bloomFilterSearchEndTime - bloomFilterSearchStartTime));

            // Record search time for HashSet
            Set<Integer> hashSet = new HashSet<>();
            long hashSetSearchStartTime = System.currentTimeMillis();
            testContainmentHashSet(hashSet, mixedElements);
            long hashSetSearchEndTime = System.currentTimeMillis();
            hashSetSearchTimes.add((double) (hashSetSearchEndTime - hashSetSearchStartTime));
        }

        // Plotting
        plotLineChart("Insertion Time", "Number of Elements", "Time (ms)", sizes,
                bloomFilterInsertionTimes, hashSetInsertionTimes);

        plotLineChart("Search Time", "Number of Elements", "Time (ms)", sizes,
                bloomFilterSearchTimes, hashSetSearchTimes);
    }

    private static void plotLineChart(String chartTitle, String xAxisTitle, String yAxisTitle,
                                      List<Integer> xData, List<Double> yData1, List<Double> yData2) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(chartTitle)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        chart.addSeries("Bloom Filter", xData, yData1);
        chart.addSeries("HashSet", xData, yData2);

        // Set a custom formatter for the X-axis labels to avoid scientific notation
        DecimalFormat xFormat = new DecimalFormat("0");
        chart.getStyler().setXAxisDecimalPattern("0");

        new SwingWrapper<>(chart).displayChart();
    }


    private static List<Integer> mixElements(List<Integer> elements) {
        List<Integer> mixedElements = new ArrayList<>(elements);

        // Assume half of the elements are mixed
        int size = elements.size();
        for (int i = 0; i < size / 2; i++) {
            int index = Math.floorMod(Utilities.generateRandomIntegers(1).get(0), size);
            mixedElements.set(index, Utilities.generateRandomIntegers(1).get(0));
        }

        return mixedElements;
    }


    private static void testInsertionTimeBloomFilter(List<Integer> elementsToInsert) {
        int n = elementsToInsert.size();
        int m = calculateOptimalM(n, 0.001);
        int k = calculateOptimalK(m, n);
        BloomFilter<Integer> bloomFilter = new BloomFilter<>(k, m);

        for (Integer element : elementsToInsert) {
            bloomFilter.add(element);
        }
    }

    private static void testInsertionTimeHashSet(List<Integer> elementsToInsert) {
        Set<Integer> hashSet = new HashSet<>();

        for (Integer element : elementsToInsert) {
            hashSet.add(element);
        }
    }

    private static void testContainmentBloomFilter(BloomFilter<Integer> bloomFilter, List<Integer> mixedElements) {
        for (Integer element : mixedElements) {
            bloomFilter.contains(element);
        }
    }

    private static void testContainmentHashSet(Set<Integer> hashSet, List<Integer> mixedElements) {
        for (Integer element : mixedElements) {
            hashSet.contains(element);
        }
    }

    private static int calculateOptimalM(int n, double desiredFalsePositiveRate) {
        return (int) Math.ceil(-(n * Math.log(desiredFalsePositiveRate)) / Math.pow(Math.log(2), 2));
    }

    private static int calculateOptimalK(int m, int n) {
        return (int) Math.round((m / (double) n) * Math.log(2));
    }
}
