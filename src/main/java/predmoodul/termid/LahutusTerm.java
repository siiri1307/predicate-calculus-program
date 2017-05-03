package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 18/03/17.
 */
public class LahutusTerm extends TehteTerm {

    public LahutusTerm(List<Term> termid) {
        super(termid);
    }

    public LahutusTerm(LahutusTerm lt){

        List<Term> termid = new ArrayList<>();

        for(Term t : lt.alamTermid){
            termid.add(t.koopia());
        }

        this.alamTermid = termid;
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return teeTehe(vaartustus, alamTermid, (vahe,lahutatav) -> vahe-lahutatav);

    }

    @Override
    public String dot() {

        String lahutusterm = "-Term(";

        for(int i = 0; i < alamTermid.size()-1; i++){
            lahutusterm += alamTermid.get(i).dot() + ",";
        }

        lahutusterm += alamTermid.get(alamTermid.size()-1) + ")";

        return lahutusterm;
    }

    @Override
    public Term koopia() {
        return new LahutusTerm(this);
    }

}
