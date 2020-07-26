package com.company.doctorapplication.Models.Appointments;

import java.util.ArrayList;
import java.util.List;

public class GetAppointmentsResponseModel {
    private String ResultMessageEn;

    private ArrayList<GetAppointmentsResponseDataModel> Data;

    private String ResultCode;

    public String getResultMessageEn() {
        return ResultMessageEn;
    }

    public void setResultMessageEn(String ResultMessageEn) {
        this.ResultMessageEn = ResultMessageEn;
    }

    public List<GetAppointmentsResponseDataModel> getData() {
        return Data;
    }

    public void setData(ArrayList<GetAppointmentsResponseDataModel> Data) {
        this.Data = Data;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this.ResultCode = ResultCode;
    }

}
