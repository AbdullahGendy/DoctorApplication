package com.company.doctorapplication.Models.Reviews;

import java.util.ArrayList;

public class ReviewsResponse {
    private String ResultMessageEn;

    private ArrayList<Data> Data;

    private String ResultCode;

    public String getResultMessageEn ()
    {
        return ResultMessageEn;
    }

    public void setResultMessageEn (String ResultMessageEn)
    {
        this.ResultMessageEn = ResultMessageEn;
    }

    public ArrayList<Data>  getData ()
    {
        return Data;
    }

    public void setData (ArrayList<Data>  Data)
    {
        this.Data = Data;
    }

    public String getResultCode ()
    {
        return ResultCode;
    }

    public void setResultCode (String ResultCode)
    {
        this.ResultCode = ResultCode;
    }

}
