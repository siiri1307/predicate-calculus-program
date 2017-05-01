package predmoodul.kvantorid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.valemid.TõesuspuuTipp;
import predmoodul.valemid.Valem;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by siiri on 09/03/17.
 */
public class Eks extends Valem implements Kvantor {

    private Valem valem;
    private Character indiviidmuutuja;

    public Eks(Valem valem, Character indiviidmuutuja){
        this.valem = valem;
        this.indiviidmuutuja = indiviidmuutuja;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem, indiviidmuutuja);
    }

    @Override
    public String toString() {
        return "Eks{" +
                "indiviidmuutuja=" + indiviidmuutuja +
                '}';
    }

    @Override
    public Character getIndiviidMuutuja() {
        return this.indiviidmuutuja;
    }


    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {


        for(double i = 0; i < 100; i++){
            vaartustus.put(indiviidmuutuja, i);
            boolean valemiVäärtus = valem.vaartusta(vaartustus);
            if(valemiVäärtus){
                return true;
            }
        }

        return false;

    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return null;
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        Set<Character> valemiVabadMuutujad = valem.getVabadMuutujad();
        valemiVabadMuutujad.remove(indiviidmuutuja);

        return valemiVabadMuutujad;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus) {

        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Valem valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        Eks leidubValem = (Eks) valem;

        return this.valem.equals(leidubValem.valem);
    }

    @Override
    public String dot() {
        return "E(" + valem.dot() + ")";
    }
}
