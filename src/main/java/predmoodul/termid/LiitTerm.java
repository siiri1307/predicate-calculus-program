package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 10/03/17.
 */
public class LiitTerm extends TehteTerm {

    public LiitTerm(List<Term> termid) {
        super(termid);
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return teeTehe(vaartustus, alamTermid, (summa,liidetav) -> summa + liidetav);

    }
}
