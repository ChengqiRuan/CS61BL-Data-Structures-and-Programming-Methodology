package main;

import static utils.Utils.*;

import ngrams.NGramMap;
import org.slf4j.LoggerFactory;

import browser.NgordnetServer;

import java.io.FileNotFoundException;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    /* Do not delete or modify the code above! */

    public static void main(String[] args) throws FileNotFoundException {

        NgordnetServer hns = new NgordnetServer();
        hns.startUp();


        NGramMap ngm = new NGramMap("data/ngrams/word_history_size82191.csv",  "data/ngrams/year_history.csv");




        //hns.register("history", new DummyHistoryHandler());
        hns.register("history", new HistoryHandler(ngm));

        hns.register("historytext", new HistoryTextHandler(ngm));


        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet_2a.html");
    }
}
