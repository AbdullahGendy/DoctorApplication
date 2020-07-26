package com.company.doctorapplication.Models.GetEstimatedReservationTime;


public class GetEstimatedReservationTimeResponse {
    private String ResultMessageEn;

    private Data Data;

    private String ResultCode;

    public String getResultMessageEn ()
    {
        return ResultMessageEn;
    }

    public void setResultMessageEn (String ResultMessageEn)
    {
        this.ResultMessageEn = ResultMessageEn;
    }

    public Data getData ()
    {
        return Data;
    }

    public void setData (Data Data)
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
