package com.example.stazionidiricaricans.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;

public class Charger implements Serializable {

    public static Charger parseJSON(JSONObject object) throws JSONException {
        if (object == null) return null;

        Charger charger = new Charger();
        charger.setOperator(object.getJSONObject("OperatorInfo").optString("Title"));
        charger.setUsageType(object.getJSONObject("UsageType").optString("Title"));
        charger.setStatus(object.getJSONObject("StatusType").optString("Title"));
        charger.setUsageCost(object.optString("UsageCost"));
        JSONObject addressInfo = object.getJSONObject("AddressInfo");
        charger.setAddress(addressInfo.optString("AddressLine1"));
        charger.setTown(addressInfo.optString("Town"));
        charger.setStateOrProvince(addressInfo.optString("StateOrProvince"));
        charger.setCountry(addressInfo.getJSONObject("Country").optString("Title"));
        charger.setLatitude(addressInfo.optDouble("Latitude"));
        charger.setLongitude(addressInfo.optDouble("Longitude"));

        return charger;
    }
    private Integer id;
    private String operator;
    private String usageType;
    private String status; //operational or not
    private String usageCost;
    private String address;
    private String town;
    private String stateOrProvince;
    private String country;
    private double latitude;
    private double longitude;
    private URL image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsageCost() {
        return usageCost;
    }

    public void setUsageCost(String usageCost) {
        this.usageCost = usageCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }
}
