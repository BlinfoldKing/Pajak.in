package pajakin.model;

public class Property {
    /* Please note the area in this section using m2
    and the sale value in this section is sale value per area */

    private String address;
    private double landArea;
    private double landSaleValue;
    private double buildingArea = 0; // By default the value set to 0 because its possible only has land
    private double buildingSaleValue = 0; // By default the value set to 0 because its possible only has land


    public Property(String address, double landArea, double landSaleValue, double buildingArea, double buildingSaleValue) {
        this.address = address;
        this.landArea = landArea;
        this.landSaleValue = landSaleValue;
        this.buildingArea = buildingArea;
        this.buildingSaleValue = buildingSaleValue;
    }

    // if it is only land without building
    public Property(String address, double landArea, double landSaleValue) {
        this.address = address;
        this.landArea = landArea;
        this.landSaleValue = landSaleValue;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public void setBuildingArea(double buildingArea) {
        this.buildingArea = buildingArea;
    }

    public void setLandSaleValue(double landSaleValue) {
        this.landSaleValue = landSaleValue;
    }

    public void setBuildingSaleValue(double buildingSaleValue) {
        this.buildingSaleValue = buildingSaleValue;
    }

    public String getAddress() {
        return address;
    }

    public double getLandArea() {
        return landArea;
    }

    public double getBuildingArea() {
        return buildingArea;
    }

    public double getLandSaleValue() {
        return landSaleValue;
    }

    public double getBuildingSaleValue() {
        return buildingSaleValue;
    }
}