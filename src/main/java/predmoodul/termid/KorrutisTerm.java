package predmoodul.termid;

import java.util.List;
import java.util.Map;

/**
 * Created by siiri on 18/03/17.
 */
public class KorrutisTerm extends TehteTerm {

    //private Term vasakTerm;
    //private Term paremTerm;

    /*public KorrutisTerm(List<Term> termid) {
       super(termid);
    }*/

    public KorrutisTerm(Term vasakTerm, Term paremTerm){
        this.vasakTerm = vasakTerm;
        this.paremTerm = paremTerm;
    }

    //copy constructor
    public KorrutisTerm(KorrutisTerm kt){

        this.vasakTerm = kt.vasakTerm.koopia();
        this.paremTerm = kt.paremTerm.koopia();

        /*List<Term> termid = new ArrayList<>();

        for(Term t : kt.alamTermid){
            termid.add(t.koopia());
        }

        this.alamTermid = termid;*/
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return vasakTerm.vaartusta(vaartustus) * paremTerm.vaartusta(vaartustus);

        //return teeTehe(vaartustus, alamTermid, (korrutis,korrutatav) -> korrutis * korrutatav);
        /*int loppVaartus = 1;

        for(Term term : alamTermid){
            loppVaartus *= term.vaartusta(vaartustus);
        }
        return loppVaartus;*/
    }

    @Override
    public String dot() {

        String korrutisterm = "*Term(";

        korrutisterm += vasakTerm.dot() + ", ";

        korrutisterm += paremTerm.dot();

        korrutisterm += ")";

        /*for(int i = 0; i < alamTermid.size()-1; i++){
            korrutisterm += alamTermid.get(i).dot() + ",";
        }

        korrutisterm += alamTermid.get(alamTermid.size()-1) + ")";*/

        return korrutisterm;
    }

    @Override
    public Character getTahis() {
        return null;
    }

    public static Term binaarneKorrutis(List<Term> termid) {

        Term vasakTerm = termid.get(0);

        for(int i = 1; i < termid.size(); i++){
            vasakTerm = new KorrutisTerm(vasakTerm, termid.get(i));
        }

        return vasakTerm;
    }

    @Override
    public Term koopia() {
        return new KorrutisTerm(this);
    }

}
