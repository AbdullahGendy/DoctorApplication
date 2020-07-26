package com.company.doctorapplication.Models.Appointments;

public class GetAppointmentsResponseDataModel {

    private String ExpectedTime;

    private String AppointmentId;

    private String UserId;

    private String City;

    private String BookingType;

    private String CheckupNumber;

    private String Date;

    private String Name;

    public String getExpectedTime() {
        return ExpectedTime;
    }

    public void setExpectedTime(String ExpectedTime) {
        this.ExpectedTime = ExpectedTime;
    }

    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String AppointmentId) {
        this.AppointmentId = AppointmentId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getBookingType() {
        return BookingType;
    }

    public void setBookingType(String BookingType) {
        this.BookingType = BookingType;
    }

    public String getCheckupNumber() {
        return CheckupNumber;
    }

    public void setCheckupNumber(String CheckupNumber) {
        this.CheckupNumber = CheckupNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
