package utilities;

import java.io.IOException;
import java.util.Map;

/**
 * Created by satheesh on 25/8/14.
 */
public class SentimentScoreOfWord {

    private Map<String, Double> dictionary;

    SentimentScoreOfWord() throws IOException {
        dictionary = SentiwordnetDictionary.getDictionary("/home/shubham/project/twitter_analysis/app/utilities/SentiWordNet.txt");
    }

    public double extract(String word, String pos) {
        if (dictionary.get(word + "#" + pos)!=null){
            return dictionary.get(word + "#" + pos);
        }
        return 0.0;
    }


    public double getSentimentScoreOfWord(String word,String pos){

        return extract(word,pos);
     }

}
