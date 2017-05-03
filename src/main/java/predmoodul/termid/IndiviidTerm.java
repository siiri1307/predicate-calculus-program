package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 18/03/17.
 */
public class IndiviidTerm extends Term {

    private Character a;

    public IndiviidTerm(Character text) {
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
    public double vaartusta(Map<Character, Double> vaartustus) {
        return vaartustus.get(a);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTerm = new HashSet<>();
        indiviidTerm.add(this);
        return indiviidTerm;
    }

    @Override
    public boolean equals(Term term) {

        if(this == term){
            return true;
        }
        if(term == null || this.getClass() != term.getClass()){
            return false;
        }

        IndiviidTerm indiviidTerm = (IndiviidTerm) term;

        return this.a == indiviidTerm.a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndiviidTerm that = (IndiviidTerm) o;

        return this.a.equals(that.a);

    }

    @Override
    public int hashCode() {
        return a != null ? a.hashCode() : 0;
    }

    public Character getTahis() {
        return a;
    }

    @Override
    public String dot() {

        return a.toString();
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {

        this.a = sumbol;
    }

    @Override
    public Term koopia() {
        return new IndiviidTerm(this);
    }
}
