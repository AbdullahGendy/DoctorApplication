package com.company.doctorapplication.Models.GetEstimatedReservationTime;

public class Data {
    private String ExpectedTime;

    private String CheckupNumber;

    public String getExpectedTime ()
    {
        return ExpectedTime;
    }

    public void setExpectedTime (String ExpectedTime)
    {
        this.ExpectedTime = ExpectedTime;
    }

    public String getCheckupNumber ()
    {
        return CheckupNumber;
    }

    public void setCheckupNumber (String CheckupNumber)
    {
        this.CheckupNumber = CheckupNumber;
    }

}
