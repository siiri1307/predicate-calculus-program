package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 10/03/17.
 */
public class LiitTerm extends TehteTerm {

    public LiitTerm(List<Term> termid) {
        super(termid);
    }

    public LiitTerm(LiitTerm lt){

        List<Term> termid = new ArrayList<>();

        for(Term t : lt.alamTermid){
            termid.add(t.koopia());
        }

        this.alamTermid = termid;
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return teeTehe(vaartustus, alamTermid, (summa,liidetav) -> summa + liidetav);

    }

    @Override
    public String dot() {

        String liitterm = "+Term(";

        for(int i = 0; i < alamTermid.size()-1; i++){
            liitterm += alamTermid.get(i).dot() + ",";
        }

        liitterm += alamTermid.get(alamTermid.size()-1) + ")";

        return liitterm;
    }

    @Override
    public Term koopia() {
        return new LiitTerm(this);
    }
}
