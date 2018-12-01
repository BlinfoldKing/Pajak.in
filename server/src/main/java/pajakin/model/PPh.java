public class PPh extends Tax implements Taxable {
    /* Term used in this section */
    // PPh  : Pajak Penghasilan
    // PTKP : Penghasilan Tidak Kena Pajak
    // PKP  : Penghasilan Kena Pajak
    
    private Person person;
    private double PTKP = 54000000; // Based on PMK No. 101/PMK.010/2016

    public PPh (Person person){
        this.person = person;
        if (person.getMarried())
            PTKP += 4500000;
        PTKP += person.getChildren() * 4500000;
    }


    public double countTax() {
        double salaryPerYear = 12*person.getSalary();
        double allowancePerYear = 12*person.getAllowance();
        double pensionFeePerYear = 12*person.getPensionFee();

        // Calculate the bruto salary value
        double brutoSalary = salaryPerYear + allowancePerYear;

        // Calculate the reduction value
        double reduction = PTKP + (person.getPositionRate() * brutoSalary) + pensionFeePerYear;

        // Calculate PKP
        double PKP = brutoSalary - reduction;

        // calculate the tax value per month by using formula (5% * PKP) / 12
        double tax = (0.05 * PKP) / 12;

        return tax;
    }

    public String toString() {
        // to be implemented
        return "";
    }
}