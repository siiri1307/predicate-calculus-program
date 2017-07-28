package predmoodul.termid;

import predmoodul.valemid.Muutuja;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 18/03/17.
 */
public class IndiviidTerm extends Term {

    private Muutuja a;

    public IndiviidTerm(Muutuja text) {

        this.a = text;
    }

    public IndiviidTerm(IndiviidTerm indiviidTerm){
        this.a = indiviidTerm.a;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object)a);
    }

    @Override
    public double vaartusta(Map<Muutuja, Double> vaartustus) {
        return vaartustus.get(a);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTerm = new HashSet<>();
        indiviidTerm.add(this);
        return indiviidTerm;
    }

    @Override
    public boolean equals(Object term) {

        if(this == term){
            return true;
        }
        if(term == null || this.getClass() != term.getClass()){
            return false;
        }

        IndiviidTerm indiviidTerm = (IndiviidTerm) term;

        return this.a.equals(indiviidTerm.a);
    }

    @Override
    public int hashCode() {
        return a != null ? a.hashCode() : 0;
    }

    public Muutuja getTahis() {
        return a;
    }

    @Override
    public String dot() {

        return a.toString();
    }

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {

        if(a.equals(vanaSumbol)){
            this.a = uusSumbol;
        }
    }

    @Override
    public void asendaTerm(Term uusTerm, Predicate<Term> tingimus) {}

    @Override
    public Term koopia() {
        return new IndiviidTerm(this);
    }
}
