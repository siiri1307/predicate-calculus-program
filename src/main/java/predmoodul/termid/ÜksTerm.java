package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 18/03/17.
 */
public class ÜksTerm extends Term {

    private Character c;


    public ÜksTerm() {
        this.c = '1';
    }

    public ÜksTerm(ÜksTerm t){
        this.c = t.c;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object)c);
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {
        return 1.0;
    }


    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return new HashSet<>();
    }

    @Override
    public boolean equals(Term term) {

        if(this == term){
            return true;
        }
        if(term == null || this.getClass() != term.getClass()){
            return false;
        }

        ÜksTerm üksTerm = (ÜksTerm) term;

        return this.c == üksTerm.c;
    }

    @Override
    public String dot() {
        return c.toString();
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {}

    @Override
    public Term koopia() {
        return new ÜksTerm(this);
    }
}
