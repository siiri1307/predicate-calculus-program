package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 18/03/17.
 */
public class KorrutisTerm extends TehteTerm {

    public KorrutisTerm(List<Term> termid) {
       super(termid);
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return teeTehe(vaartustus, alamTermid, (korrutis,korrutatav) -> korrutis * korrutatav);
        /*int loppVaartus = 1;

        for(Term term : alamTermid){
            loppVaartus *= term.vaartusta(vaartustus);
        }
        return loppVaartus;*/
    }

    @Override
    public String dot() {

        String korrutisterm = "*Term(";

        for(int i = 0; i < alamTermid.size()-1; i++){
            korrutisterm += alamTermid.get(i).dot() + ",";
        }

        korrutisterm += alamTermid.get(alamTermid.size()-1) + ")";

        return korrutisterm;
    }

}
