package com.company.doctorapplication.Models.AddNewClinic;

public class WorkingDays {
    private String From;

    private String To;

    private String DayName;

    public WorkingDays(String from, String to, String dayName) {
        From = from;
        To = to;
        DayName = dayName;
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
