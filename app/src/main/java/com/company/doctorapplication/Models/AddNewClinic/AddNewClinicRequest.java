package com.company.doctorapplication.Models.AddNewClinic;

import java.util.ArrayList;

public class AddNewClinicRequest {
    private String StreetName;

    private String Floor;

    private String MobileNumber;

    private String BuildingName;

    private String City;

    private ArrayList<WorkingDays> WorkingDays;

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String Floor) {
        this.Floor = Floor;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String BuildingName) {
        this.BuildingName = BuildingName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public ArrayList<WorkingDays> getWorkingDays() {
        return WorkingDays;
    }

    public void setWorkingDays(ArrayList<WorkingDays> WorkingDays) {
        this.WorkingDays = WorkingDays;
    }
}
