package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 18/03/17.
 */
public class ÜksTerm extends Term {

    private final Character c;


    public ÜksTerm() {
        this.c = '1';
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
}
