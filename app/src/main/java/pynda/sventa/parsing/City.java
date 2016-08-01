package pynda.sventa.parsing;

public class City {
    private int unemployment = -1;

    private int transport = -1;

    private int criminality = -1;

    private int pollution = -1;

    public int getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(int unemployment) {
        this.unemployment = unemployment;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public int getCriminality() {
        return criminality;
    }

    public void setCriminality(int criminality) {
        this.criminality = criminality;
    }

    public int getPollution() {
        return pollution;
    }

    public void setPollution(int pollution) {
        this.pollution = pollution;
    }

    @Override
    public String toString() {
        return "ClassPojo [unemployment = " + unemployment + ", transport = " + transport + ", criminality = " + criminality + ", pollution = " + pollution + "]";
    }
}