package predmoodul.kvantorid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.valemid.Termikuulaja;
import predmoodul.valemid.TõesuspuuTipp;
import predmoodul.valemid.Valem;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by siiri on 13/04/17.
 */
public class Eitus extends Valem implements Kvantor {

    private Valem valem;

    public Eitus(Valem valem){
        this.valem = valem;
    }

    @Override
    public Character getIndiviidMuutuja() {
        return null;
    }


    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return !valem.vaartusta(vaartustus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return null;
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        return valem.getVabadMuutujad();
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem);
    }

    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad) {

        if(tõeväärtus){

            return Arrays.asList(new TõesuspuuTipp(this.valem, false));
        }
        else{

            return Arrays.asList(new TõesuspuuTipp(this.valem, true));
        }
    }

    @Override
    public boolean equals(Valem valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        Eitus eitus = (Eitus) valem;

        return this.valem.equals(eitus.valem);
    }

    @Override
    public String dot() {
        return "!(" + valem.dot() + ")";
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {
        valem.uusKonstantSumbol(sumbol);
    }

    @Override
    public Valem koopia() {
        throw new NotImplementedException();
    }
}
