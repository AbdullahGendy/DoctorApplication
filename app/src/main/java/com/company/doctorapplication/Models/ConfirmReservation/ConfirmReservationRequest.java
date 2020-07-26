package com.company.doctorapplication.Models.ConfirmReservation;

public class ConfirmReservationRequest {
    private String ExpectedTime;

    // private String UserId;

    private String ClinicId;
    private String BookingType;

    public String getBookingType() {
        return BookingType;
    }

    public void setBookingType(String bookingType) {
        BookingType = bookingType;
    }

    private String CheckupNumber;

    private String Date;

    public String getExpectedTime() {
        return ExpectedTime;
    }

    public void setExpectedTime(String ExpectedTime) {
        this.ExpectedTime = ExpectedTime;
    }

   /* public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }*/

    public String getClinicId() {
        return ClinicId;
    }

    public void setClinicId(String ClinicId) {
        this.ClinicId = ClinicId;
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

}
