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

}
