package com.company.doctorapplication.Models.Login;

public class LoginResponseModel {
    private String ResultMessageEn;

    private String Data;

    private String ResultCode;

    public String getResultMessageEn() {
        return ResultMessageEn;
    }

    public void setResultMessageEn(String ResultMessageEn) {
        this.ResultMessageEn = ResultMessageEn;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this.ResultCode = ResultCode;
    }
}
