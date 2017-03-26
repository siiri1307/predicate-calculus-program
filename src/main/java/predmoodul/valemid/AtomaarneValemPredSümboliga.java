package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.NullTerm;
import predmoodul.termid.ÜksTerm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by siiri on 19/03/17.
 */
public class AtomaarneValemPredSümboliga extends Valem {

    private ValemiID id;
    private Valem valem;
    //private String predSümbol;
    private List<TermiPaar> termArgumendid;
    //private boolean valemiTõeväärtus;

    /*public AtomaarneValemPredSümboliga(String predSümbol, List<Term> termArgumendid, boolean tõeVäärtus) {
        this.predSümbol = predSümbol;
        this.termArgumendid = termArgumendid;
        this.valemiTõeväärtus = tõeVäärtus;
    }*/

    public AtomaarneValemPredSümboliga(ValemiID id, Valem valem){
        this.id = id;
        this.valem = valem;
    }

    public AtomaarneValemPredSümboliga(ValemiID id, List<TermiPaar> termArgumendid, Valem valem) {
        this.id = id;
        this.termArgumendid = termArgumendid;
        this.valem = valem;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(id, termArgumendid, valem);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {
        for(TermiPaar paar : termArgumendid){
            if(paar.getTerm() instanceof NullTerm){
                vaartustus.put(paar.getTahis(), 0.0);
            }
            else if(paar.getTerm() instanceof ÜksTerm){
                vaartustus.put(paar.getTahis(), 1.0);
            }
        }
        return valem.vaartusta(vaartustus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return valem.getIndiviidTermid();
    }

    @Override
    public Set<Character> getVabadMuutujad() {
        return valem.getVabadMuutujad();
    }
}
