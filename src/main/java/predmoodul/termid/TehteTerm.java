package predmoodul.termid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by siiri on 25/03/17.
 */
public abstract class TehteTerm extends Term {

    protected List<Term> alamTermid;

    public TehteTerm(List<Term> termid) {
        this.alamTermid = termid;
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();
        for(Term term : alamTermid){
            indiviidTermid.addAll(term.getIndiviidTermid());
        }

        return indiviidTermid;

        }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(alamTermid);
    }
}
