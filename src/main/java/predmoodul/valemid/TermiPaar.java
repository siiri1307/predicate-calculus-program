package predmoodul.valemid;

import predmoodul.termid.Term;

/**
 * Created by siiri on 21/03/17.
 */
public class TermiPaar {

    private Term term;
    private Character tahis;

    public TermiPaar(Character tahis, Term term) {
        this.tahis = tahis;
        this.term = term;
    }

    public Term getTerm() {
        return term;
    }

    public Character getTahis() {
        return tahis;
    }
}
