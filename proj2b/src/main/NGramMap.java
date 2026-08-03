package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;




/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private Map<String, TimeSeries> wordCountData;
    private Map<String, TimeSeries> wordWeightData;
    private TimeSeries totalWordsPerYear;


    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordCountData = new HashMap<>();
        wordWeightData = new HashMap<>();
        totalWordsPerYear = new TimeSeries();

        /*
        File wordFile = new File(wordsFilename);

        try (Scanner wordScan = new Scanner(wordFile)) {
            while (wordScan.hasNext()) {
                String word = wordScan.next();
                int year = wordScan.nextInt();
                double times = wordScan.nextDouble();
                wordScan.next();

                TimeSeries t = new TimeSeries();
                t.put(year, times);
                if (wordCountData.containsKey(word)) {
                    TimeSeries oringin = wordCountData.get(word);
                    oringin.put(year, times);
                } else {
                    wordCountData.put(word, t);
                }
            }
        } catch (FileNotFoundException e) {
            return;
        }
         */
        In in = new In(wordsFilename);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] parts = line.split("\\s+");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double times = Double.parseDouble(parts[2]);

            TimeSeries t = new TimeSeries();
            t.put(year, times);
            if (wordCountData.containsKey(word)) {
                TimeSeries oringin = wordCountData.get(word);
                oringin.put(year, times);
            } else {
                wordCountData.put(word, t);
            }
        }
        /*
            File countFile = new File(countsFilename);
        try (Scanner countScan = new Scanner(countFile)) {
            while (countScan.hasNextLine()) {
                String line = countScan.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",");
                int year = Integer.parseInt(parts[0]);
                double nums = Double.parseDouble(parts[1]);
                totalWordsPerYear.put(year, nums);
            }
        } catch (FileNotFoundException e) {
            return;
        }

         */
        In in2 = new In(countsFilename);
        while (!in2.isEmpty()) {
            String line2 = in2.readLine();
            String[] parts2 = line2.split(",");
            int year2 = Integer.parseInt(parts2[0]);
            double times2 = Double.parseDouble(parts2[1]);
            totalWordsPerYear.put(year2, times2);
        }

        /*
        for (String w : wordCountData.keySet()) {
            TimeSeries ts = wordCountData.get(w);
            wordWeightData.put(w, ts.dividedBy(totalWordsPerYear));
        }
         */
    }


    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordCountData.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries fullData = wordCountData.get(word);
        TimeSeries res = new TimeSeries(fullData, startYear, endYear);
        return res;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordCountData.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = wordCountData.get(word);
        return original;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries res = new TimeSeries();
        for (int year : totalWordsPerYear.keySet()) {
            res.put(year, totalWordsPerYear.get(year));
        }
        return res;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordWeightData.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = wordWeightData.get(word);
        return new TimeSeries(original, startYear, endYear);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordWeightData.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = wordWeightData.get(word);
        return new TimeSeries(original, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries total = new TimeSeries();
        for (String w : words) {
            TimeSeries single = weightHistory(w, startYear, endYear);
            total = total.plus(single);
        }
        return total;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries total = new TimeSeries();
        for (String w : words) {
            TimeSeries single = weightHistory(w);
            total = total.plus(single);
        }
        return total;
    }
}



