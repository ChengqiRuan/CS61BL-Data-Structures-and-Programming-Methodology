package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {
        NGramMap ng = new NGramMap(wordHistoryFile, yearHistoryFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        return new HyponymsHandler(wn, ng);
    }
}
