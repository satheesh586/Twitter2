package models.ResultJsonObjects;

/**
 * Created by satheesh on 11/9/14.
 */
public class JsonTrendingHashTags {

    private String hashTag;
    private  double score;

    public JsonTrendingHashTags(String hashTag, double score) {
        this.hashTag = hashTag;
        this.score = score;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
