package predmoodul.valemid;

import java.util.Arrays;
import java.util.List;

/**
 * Created by siiri on 10/03/17.
 */
public class Muutuja extends AstNode {

    private static int mNumber = 0;

    private Character tahis;
    private int jarjeNumber;
    private String predTahis;

    public String getPredTahis() {
        return predTahis;
    }

    public void setPredTahis(String predTahis) {
        this.predTahis = predTahis;
    }

    public Muutuja(Character tahis){
        this.tahis = tahis;
    }

    public Muutuja(Character tahis, int nr){
        this.tahis = tahis;
        this.jarjeNumber = nr;
    }

    public Muutuja(Character tahis, String predTahis) {
        this.tahis = tahis;
        this.predTahis = predTahis;
    }

    public Character getTahis() {

        return tahis;
    }

    public int getJarjeNumber() {

        return jarjeNumber;
    }

    public void setTahis(Character tahis) {
        this.tahis = tahis;
    }

    public void setJarjeNumber(int jarjeNumber) {
        this.jarjeNumber = jarjeNumber;
    }

    @Override
    public boolean equals(Object muutuja){

        if(this == muutuja){
            return true;
        }
        else if(muutuja == null || this.getClass() != muutuja.getClass()){
            return false;
        }

        Muutuja m = (Muutuja) muutuja;

        return this.tahis.equals(m.getTahis()) && this.jarjeNumber == m.getJarjeNumber() && samaAbiValem(m.getPredTahis());

    }

    private boolean samaAbiValem(String teiseMuutujaPredTahis) {
        if(this.predTahis == null){
            return teiseMuutujaPredTahis == null;
        }
        return this.predTahis.equals(teiseMuutujaPredTahis);
    }

    @Override
    public int hashCode() {
        int result = tahis.hashCode();
        result = 31 * result + jarjeNumber;
        return result;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) tahis);
    }

    @Override
    public String toString() {
        return tahis + Integer.toString(jarjeNumber);
    }

    public static int uusMNumber() {
        return mNumber++;
    }
}
