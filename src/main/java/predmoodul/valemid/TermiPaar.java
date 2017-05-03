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

    public TermiPaar(TermiPaar tp){
        this.term = tp.term.koopia();
        this.tahis = tp.tahis;
    }

    public void setTahis(Character tahis) {
        this.tahis = tahis;
    }

    @Override
    public String toString() {
        return "TermiPaar{" +
                "tahis=" + tahis +
                '}';
    }

    public Term getTerm() {
        return term;
    }

    public Character getTahis() {
        return tahis;
    }
}
