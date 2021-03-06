package predmoodul.termid;

import predmoodul.valemid.Muutuja;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 18/03/17.
 */
public class ÜksTerm extends Term {

    private Character c;

    public ÜksTerm() {
        this.c = '1';
    }

    //copy constructor
    public ÜksTerm(ÜksTerm t){
        this.c = t.c;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object)c);
    }

    @Override
    public double vaartusta(Map<Muutuja, Double> vaartustus) {
        return 1.0;
    }


    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return new HashSet<>();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }

    @Override
    public String dot() {
        return c.toString();
    }

    @Override
    public Muutuja getTahis() {
        return null;
    }

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {}

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {}

    @Override
    public Term koopia() {
        return new ÜksTerm(this);
    }
}
