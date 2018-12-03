package pajakin.model;

import pajakin.model.Tax;
import pajakin.model.Taxable;

public class PKB extends Tax implements Taxable {
    /* Term used in this section */
    // PKB      : Pajak Kendaraan Bermotor
    // SWDKLLJ  : Sumbangan Wajib Dana Kecelakaan Lalu Lintas
    // NJK      : Nilai Jual Kembali

    private Vehicle vehicle;
    private double progressiveRate;
    private int SWDKLLJ = 50000; // Default value is Rp50.000

    public PKB (Vehicle vehicle, int vehicleCount) {
        this.vehicle = vehicle;
        if (vehicleCount == 1) {
            progressiveRate = 0.015;
        } else if (vehicleCount == 2) {
            progressiveRate = 0.02;
        } else  if (vehicleCount == 3) {
            progressiveRate = 0.025;
        } else if (vehicleCount >= 4) {
            progressiveRate = 0.04;
        }
    }

    public double countTax() {
        // first we need to calculate the NJK value
        double NJK = vehicle.getTaxValue() * (2/3) * 100; // default formula to calculate it        

        // calculate the tax value
        double tax = NJK * progressiveRate;

        // add the SWDKLLJ to the current result
        tax += SWDKLLJ;

        return tax;
    }
}