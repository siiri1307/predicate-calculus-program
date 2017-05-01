package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.NullTerm;
import predmoodul.termid.ÜksTerm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by siiri on 19/03/17.
 */
public class AtomaarneValemPredSümboliga extends Valem {

    private ValemiID id;
    private List<TermiPaar> termArgumendid;
    private Valem valem;
    //private String predSümbol;
    //private boolean valemiTõeväärtus;

    /*public AtomaarneValemPredSümboliga(String predSümbol, List<Term> termArgumendid, boolean tõeVäärtus) {
        this.predSümbol = predSümbol;
        this.termArgumendid = termArgumendid;
        this.valemiTõeväärtus = tõeVäärtus;
    }*/

    public AtomaarneValemPredSümboliga(ValemiID id, Valem valem){
        this.id = id;
        this.valem = valem;
        this.termArgumendid = new ArrayList<>();
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
        if(termArgumendid != null){
            for(TermiPaar paar : termArgumendid){
                if(paar.getTerm() instanceof NullTerm){
                    vaartustus.put(paar.getTahis(), 0.0);
                }
                else if(paar.getTerm() instanceof ÜksTerm){
                    vaartustus.put(paar.getTahis(), 1.0);
                }
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

    public ValemiID getId() {
        return id;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus) {
        return valem.reegel(tõeväärtus);
    }

    @Override
    public boolean equals(Valem valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        AtomaarneValemPredSümboliga atomaarnePredValem = (AtomaarneValemPredSümboliga) valem;

        if(!this.id.equals(atomaarnePredValem.id)){
            return false;
        }

        return this.valem.equals(atomaarnePredValem.valem);
    }

    @Override
    public String dot() {
        return "[" + id.dot() + "(" + String.join(",", termArgumendid.stream().map(x -> x.getTahis().toString()).collect(Collectors.toList())) +  ") := " + valem.dot() + "]";
    }
}
