package misc;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * TODO: Document
 */
public class RuntimeAnalysis {
    // If a single test takes more then 5 seconds, give up -- it's too expensive.
    private static final int TIMEOUT = 5 * 1000;

    // Collect 10,000 data points per each trial
    private static final int CAP = 10000;

    // Do 5 trials and average the results
    private static final int TRIALS = 5;

    public static void main(String[] args) {

    }

    private static List<Long> conductExperiment(Function<Integer, List<Long>> experiment) {
        // Discard first result
        experiment.apply(CAP);

        List<List<Long>> trials= new ArrayList<>();
        int maxLength = 0;
        for (int i = 0; i < TRIALS; i++) {
            List<Long> times = experiment.apply(CAP);
            maxLength = Math.max(maxLength, times.size());
            trials.add(times);
        }

        List<Long> total = new ArrayList<>();
        for (int i = 0; i < maxLength; i++) {
            total.add(0L);
        }
        for (List<Long> trial : trials) {
            for (int i = 0; i < maxLength; i++) {
                total.set(i, total.get(i) + trial.get(i));
            }
        }
        for (int i = 0; i < maxLength; i++) {
            total.set(i, total.get(i) / TRIALS);
        }
        return total;
    }

    private static List<Long> testAddToEnd(int cap) {
        List<Long> out = new ArrayList<>();
        IList<String> list = new DoubleLinkedList<>();
        for (int i = 0; i < cap; i++) {
            long start = System.currentTimeMillis();
            list.add("a");
            long delta = System.currentTimeMillis() - start;

            out.add(delta);
            if (delta > TIMEOUT) {
                break;
            }
        }
        return out;
    }
}
