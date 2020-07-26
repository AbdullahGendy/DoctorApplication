package com.company.doctorapplication.Models.GetDoctorInfo;

public class WorkingDays {
    private String Clinic;
    private String IsDeleted;
    private String IsActive;
    private String ClinicId;
    private String DayId;
    private String From;
    private String To;
    private String DayName;

    public String getClinic() {
        return Clinic;
    }

    public void setClinic(String Clinic) {
        this.Clinic = Clinic;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String IsActive) {
        this.IsActive = IsActive;
    }

    public String getClinicId() {
        return ClinicId;
    }

    public void setClinicId(String ClinicId) {
        this.ClinicId = ClinicId;
    }

    public String getDayId() {
        return DayId;
    }

    public void setDayId(String DayId) {
        this.DayId = DayId;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String DayName) {
        this.DayName = DayName;
    }

}
