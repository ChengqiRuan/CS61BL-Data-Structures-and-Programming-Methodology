package main;

import ngrams.NGramMap;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.TimeSeries;


public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        int startYear = q.startYear();
        int endYear = q.endYear();
        var words = q.words();

        StringBuilder output = new StringBuilder();

        for (String word : words) {
            TimeSeries ts = map.weightHistory(word, startYear, endYear);
            output.append(word).append(": ").append(ts).append("\n");
        }

        return output.toString();
    }
}
