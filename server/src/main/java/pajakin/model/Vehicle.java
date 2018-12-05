package pajakin.model;

public class Vehicle implements Taxable {

    private String plateNumber;
    private double taxValue;

    public Vehicle(String plateNumber, double taxValue) {
        this.plateNumber = plateNumber;
        this.taxValue = taxValue;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public double getTaxValue() {
        return taxValue;
    }
}