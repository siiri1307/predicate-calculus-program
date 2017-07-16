package predmoodul.termid;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 18/03/17.
 */
public class NullTerm extends Term {

    private Character c;

    public NullTerm() {
        this.c = '0';
    }

    public NullTerm(NullTerm nullTerm){
        this.c = nullTerm.c;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) c);
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {
        return 0.0;
    }


    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return new HashSet<>();
    }

    @Override
    public boolean equals(Object term) {

        return this.getClass() == term.getClass();
    }

    @Override
    public String dot() {
        return c.toString() ;
    }

    @Override
    public Character getTahis() {
        return c;
    }

    @Override
    public void uusKonstantSumbol(Character uusSumbol, Character vanaSumbol) {}

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {}

    @Override
    public Term koopia() {
        return new NullTerm(this);
    }
}
