package pajakin.model;
import pajakin.model.Tax;
import pajakin.model.Taxable;
import pajakin.model.Property;

public class PBB extends Tax implements Taxable {
    /* Term used in this section */
    // PBB      : Pajak Bumi Bangunan
    // NJOP     : NIlai Jual Objek Pajak
    // NJOKP     : Nilai Jual Objek Kena Pajak
    // NJOTKP   : Nilai Jual Objek Tidak Kena Pajak

    /* Please note the PBB rate is 0.5%
    and the NJOP rate is 20% (Based on UU No. 12 Tahun 1994) */

    private Property property;
    private double NJOPTKP = 8000000; // Default value is Rp8.000.000

    public PBB (Property property) {
        this.property = property;
    }

    public double countTax() {
        // first we need to calculate the NJOP value
        double NJOP = (property.getLandArea() * property.getLandSaleValue()) +
                            (property.getBuildingArea() * property.getBuildingSaleValue());

        // calculate the NJOKP value by reducing the value with NJOPTKP
        double NJOKP = NJOP - NJOPTKP;

        // if the the result less equal than 0 return the value with 0
        if (NJOKP <= 0) return 0;

        // calculate the tax value by using the formula 0.5% * 20% * NJOP
        double tax = 0.005 * 0.2 * NJOP;

        return tax;
    }
}