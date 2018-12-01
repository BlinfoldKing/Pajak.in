package pajakin.model;

import java.util.*;

import pajakin.model.Tax;
import pajakin.model.taxWrapper;


public class Person {
    /* Term used in this section */
    // NIK  : Nomor Induk Kendaraan
    // NPWP : Nomor Pokok Wajib Pajak

    /* Please note the salary, allowance, pensionFee is per month */

    private String fullName;
    private String NIK;
    private String NPWP;
    private double salary;
    private double allowance;
    private boolean married;
    private int children = 0;
    private double positionRate;
    private double pensionFee;


    // List of taxes that the person has
    private List<Tax> taxList;
    // List of the item that the person has
    private List<Taxable> ownership;

    public Person(String fullName,
                    String NIK,
                    String NPWP,
                    double salary,
                    double allowance,
                    boolean married,
                    int children) {
        this.fullName = fullName;
        this.NIK = NIK;
        this.NPWP = NPWP;
        this.salary = salary;
        this.allowance = allowance;
        this.married = married;
        this.children = children;
        taxList = new LinkedList<Tax>();
        ownership = new LinkedList<Taxable>();
    }

    public List<Taxable> getOwnership() {
        return this.ownership;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public void setNPWP(String NPWP) {
        this.NPWP = NPWP;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public void setChildren(int children) {
        this.children = children;
    }


    public void setPositionRate(double positionRate) {
        this.positionRate = positionRate;
    }

    public void setPensionFee(double pensionFee) {
        this.pensionFee = pensionFee;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNIK() {
        return NIK;
    }

    public String getNPWP() {
        return NPWP;
    }

    public double getSalary() {
        return salary;
    }

    public double getAllowance() {
        return allowance;
    }

    public boolean getMarried() {
        return married;
    }

    public int getChildren() {
        return children;
    }

    public double getPositionRate() {
        return positionRate;
    }

    public double getPensionFee() {
        return pensionFee;
    }

    public void addOwnership(Taxable item) {
        ownership.add(item);
    }

    public void processSalary() {
        taxList.add(new PPh(this));
    }

    public List<taxWrapper> getTax() {
        List<taxWrapper> tax = new ArrayList<taxWrapper>();
        for (Tax t: taxList) {
            tax.add(new taxWrapper(t.countTax(), t.getClass().toString()));
        }
        return tax;
    }

    public void processOwnership() {
        int vehicleCount = 0;
        for (Taxable t: ownership) {
            if (t instanceof Vehicle) {
                Vehicle v = (Vehicle) t;
                vehicleCount++;
                taxList.add(new PKB(v, vehicleCount));
            } else {
                Property p = (Property) t;
                taxList.add(new PBB(p));
            }
        }
    }
}