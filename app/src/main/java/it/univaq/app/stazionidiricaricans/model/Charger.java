package it.univaq.app.stazionidiricaricans.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;

@Entity(tableName="chargers")
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
        charger.setLocationInfo(charger.country, charger.stateOrProvince, charger.town);

        return charger;
    }
    @PrimaryKey
    private Integer id;
    private String operator;
    private String usageType;
    private String status; //operational or not
    private String usageCost;
    private String address;
    private String town;
    private String stateOrProvince;
    private String country;
    private String locationInfo;
    private double latitude;
    private double longitude;


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
        this.usageType = (usageType == null || usageType.equals("null")) ? "unknown" : usageType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = (status == null || status.equals("null")) ? "unknown" : status;
    }

    public String getUsageCost() {
        return usageCost;
    }


    public void setUsageCost(String usageCost) {
        this.usageCost = (usageCost == null || usageCost.equals("null")) ? "unknown" : usageCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (address == null || address.equals("null") ? "unknown" : address);
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = (town == null || town.equals("null")) ? "unknown" : town;
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
        this.country = (country == null || country.equals("null")) ? "unknown" : country;
    }

    public String getLocationInfo() {return locationInfo;}

    public void setLocationInfo(String country, String stateOrProvince, String town){
        locationInfo = country;
        if(stateOrProvince != null && !stateOrProvince.equals("null")) locationInfo = locationInfo + ", " + stateOrProvince;
        if(town != null && !town.equals("null")) locationInfo = locationInfo + ", " + town;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
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

}
