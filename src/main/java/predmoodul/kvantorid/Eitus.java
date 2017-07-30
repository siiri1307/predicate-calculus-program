package predmoodul.kvantorid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;
import predmoodul.valemid.Muutuja;
import predmoodul.valemid.Termikuulaja;
import predmoodul.valemid.TõesuspuuTipp;
import predmoodul.valemid.Valem;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 13/04/17.
 */
public class Eitus extends Valem implements Kvantor {

    private Valem valem;

    public Eitus(Valem valem){
        this.valem = valem;
    }

    public Eitus(Eitus eitus){
        this.valem = eitus.valem.koopia();
    }

    @Override
    public Muutuja getIndiviidMuutuja() {
        return null;
    }


    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus) {

        return !valem.vaartusta(vaartustus, maxVaartus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return new HashSet<>();
    }

    @Override
    public Set<Muutuja> getVabadMuutujad() {

        return valem.getVabadMuutujad();
    }

    @Override
    public int getKvantoriteArv() {
        return valem.getKvantoriteArv();
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem);
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid) {

        if(tõeväärtus){

            return Arrays.asList(new TõesuspuuTipp(this.valem, false));
        }
        else{

            return Arrays.asList(new TõesuspuuTipp(this.valem, true));
        }
    }

    @Override
    public boolean equals(Object valem) {

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
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {
        valem.uusKonstantSumbol(uusSumbol, vanaSumbol);
    }

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {
        valem.asendaTerm(uus, tingimus);
    }

    @Override
    public Valem koopia() {
        return new Eitus(this);
    }
}
