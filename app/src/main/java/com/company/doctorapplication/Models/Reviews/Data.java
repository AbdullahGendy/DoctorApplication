package com.company.doctorapplication.Models.Reviews;

public class Data {
    private String RateAndReviewId;

    private String Comment;

    private String Rate;

    private String Name;

    public Data(String comment, String rate, String name) {
        Comment = comment;
        Rate = rate;
        Name = name;
    }

    public String getRateAndReviewId ()
    {
        return RateAndReviewId;
    }

    public void setRateAndReviewId (String RateAndReviewId)
    {
        this.RateAndReviewId = RateAndReviewId;
    }

    public String getComment ()
    {
        return Comment;
    }

    public void setComment (String Comment)
    {
        this.Comment = Comment;
    }

    public String getRate ()
    {
        return Rate;
    }

    public void setRate (String Rate)
    {
        this.Rate = Rate;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }
}
