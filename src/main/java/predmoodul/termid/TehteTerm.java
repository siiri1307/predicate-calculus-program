package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 25/03/17.
 */
public abstract class TehteTerm extends Term {

    protected List<Term> alamTermid;

    public TehteTerm(List<Term> termid) {
        this.alamTermid = termid;
    }

    public TehteTerm(){}

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();
        for(Term term : alamTermid){
            indiviidTermid.addAll(term.getIndiviidTermid());
        }

        return indiviidTermid;

        }

    @Override
    public boolean equals(Term term) {

        if(this == term){
            return true;
        }
        if(term == null || this.getClass() != term.getClass()){
            return false;
        }

        TehteTerm tehteTerm = (TehteTerm) term;

        return this.alamTermid == tehteTerm.alamTermid;
    }
    @Override
    public List<Object> getChildren() {
        return Arrays.asList(alamTermid);
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {

        for(Term alamterm : alamTermid){
            alamterm.uusKonstantSumbol(sumbol);
        }
    }
}
