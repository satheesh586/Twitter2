package utilities;

import java.io.IOException;

/**
 * Created by satheesh on 25/8/14.
 */
public class SentiMentScoreOfTweet {

    /*private PartsOfSpeech partsOfSpeech;*/
    private SentimentScoreOfWord sentimentScoreOfWord;

    public SentiMentScoreOfTweet() throws IOException, ClassNotFoundException {
        sentimentScoreOfWord =new SentimentScoreOfWord();
        /*partsOfSpeech=new PartsOfSpeech();*/
    }


   /* public String getPartsOfSpeech(String test) throws IOException, ClassNotFoundException {
        return partsOfSpeech.getPartsOfSpeech(test);
    }*/

 /*   public String getPOS(char pos[]){
        if(pos[0]=='N')
            return "n";
        else if (pos.toString()=="VBD")
            return "a";
        else if (pos[0]=='V')
            return "v";
        else if (pos[0]=='J')
            return  "r";
        else
            return "a";

    }*/

    public double getScoreOfSentence(String test) throws IOException, ClassNotFoundException {
        double score=0.0;
        /*test=getPartsOfSpeech(test);*/
        for (String text:test.split(" "))
        {
            /*System.out.println(text);
            String testArray[]=text.split("/");
            String pos=getPOS(testArray[1].toCharArray());*/
            score+= sentimentScoreOfWord.getSentimentScoreOfWord(text,"a");

        }

        return score;
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        System.out.println(new SentiMentScoreOfTweet().getScoreOfSentence("good and bad are neutralized"));
    }

  }
