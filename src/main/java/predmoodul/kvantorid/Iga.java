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
public class Iga extends Valem implements Kvantor {

    private Valem valem;
    private Character indiviidmuutuja;

    public Iga(Valem valem, Character indiviidmuutuja){
        this.valem = valem;
        this.indiviidmuutuja = indiviidmuutuja;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem, indiviidmuutuja);
    }

    @Override
    public String toString() {
        return "Iga{" +
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
            boolean valemiVaartus = valem.vaartusta(vaartustus);
            if(!valemiVaartus){
                return false;
            }
        }

        return true;
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
}
