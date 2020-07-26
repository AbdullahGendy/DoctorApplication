package com.company.doctorapplication.Models.ChangeDoctorPassword;

public class ChangeDoctorPasswordRequest {
    private String DoctorId;

    private String DoctorOldPassword;

    private String DoctorNewPassword;

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String DoctorId) {
        this.DoctorId = DoctorId;
    }

    public String getDoctorOldPassword() {
        return DoctorOldPassword;
    }

    public void setDoctorOldPassword(String DoctorOldPassword) {
        this.DoctorOldPassword = DoctorOldPassword;
    }

    public String getDoctorNewPassword() {
        return DoctorNewPassword;
    }

    public void setDoctorNewPassword(String DoctorNewPassword) {
        this.DoctorNewPassword = DoctorNewPassword;
    }

}
