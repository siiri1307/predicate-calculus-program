package predmoodul.termid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by siiri on 18/03/17.
 */
public class JagamisTerm extends TehteTerm {

    public JagamisTerm(List<Term> termid) {
        super(termid);
    }

    public JagamisTerm(JagamisTerm jt){

        List<Term> termid = new ArrayList<>();

        for(Term t : jt.alamTermid){
            termid.add(t.koopia());
        }

        this.alamTermid = termid;
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return teeTehe(vaartustus, alamTermid, (jagatav,jagaja) -> jagatav/jagaja );

    }

    //return  alamTermid.stream().flatMap(x -> x.getIndiviidTermid().stream()).collect(Collectors.toSet());

    @Override
    public String dot() {

        String jagamisterm = "/Term(";

        for(int i = 0; i < alamTermid.size()-1; i++){
            jagamisterm += alamTermid.get(i).dot() + ",";
        }

        jagamisterm += alamTermid.get(alamTermid.size()-1) + ")";

        return jagamisterm;
    }

    @Override
    public Term koopia() {
        return new JagamisTerm(this);
    }


}
