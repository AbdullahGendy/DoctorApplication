package com.company.doctorapplication.Models.GetEstimatedReservationTime;

public class GetEstimatedReservationTimeRequest {
    private String UserId;

    private String ClinicId;

    private String BookingType;

    private String Date;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getClinicId() {
        return ClinicId;
    }

    public void setClinicId(String ClinicId) {
        this.ClinicId = ClinicId;
    }

    public String getBookingType() {
        return BookingType;
    }

    public void setBookingType(String BookingType) {
        this.BookingType = BookingType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

}
