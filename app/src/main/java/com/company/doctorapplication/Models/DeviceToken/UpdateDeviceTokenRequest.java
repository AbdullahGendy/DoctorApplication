package com.company.doctorapplication.Models.DeviceToken;

public class UpdateDeviceTokenRequest {
    private String DonatorId;

    private String DeviceToken;
    public String getDonatorId() {
        return DonatorId;
    }

    public void setDonatorId(String donatorId) {
        DonatorId = donatorId;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

}
