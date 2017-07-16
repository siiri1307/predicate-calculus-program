package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 18/03/17.
 */
public class LahutusTerm extends TehteTerm {

    /*public LahutusTerm(List<Term> termid) {
        super(termid);
    }*/

    //private Term vasakTerm;
    //private Term paremTerm;

    public LahutusTerm(Term vasakTerm, Term paremTerm){
        this.vasakTerm = vasakTerm;
        this.paremTerm = paremTerm;
    }

    //copy constructor
    public LahutusTerm(LahutusTerm lt){

        this.vasakTerm = lt.vasakTerm.koopia();
        this.paremTerm = lt.paremTerm.koopia();
        /*List<Term> termid = new ArrayList<>();

        for(Term t : lt.alamTermid){
            termid.add(t.koopia());
        }

        this.alamTermid = termid;*/
    }

    @Override
    public double vaartusta(Map<Character, Double> vaartustus) {

        return vasakTerm.vaartusta(vaartustus) - paremTerm.vaartusta(vaartustus);
        //return teeTehe(vaartustus, alamTermid, (vahe,lahutatav) -> vahe-lahutatav);

    }

    public static Term binaarneLahutamine(List<Term> termid) {

        Term vasakTerm = termid.get(0);

        for(int i = 1; i < termid.size(); i++){
            vasakTerm = new LahutusTerm(vasakTerm, termid.get(i));
        }

        return vasakTerm;
    }

    @Override
    public String dot() {

        String lahutusterm = "-Term(";

        lahutusterm += vasakTerm.dot() + ", ";

        lahutusterm += paremTerm.dot();

        lahutusterm += ")";

        /*for(int i = 0; i < alamTermid.size()-1; i++){
            lahutusterm += alamTermid.get(i).dot() + ",";
        }

        lahutusterm += alamTermid.get(alamTermid.size()-1) + ")";
        */

        return lahutusterm;
    }

    @Override
    public Character getTahis() {
        return null;
    }

    @Override
    public Term koopia() {
        return new LahutusTerm(this);
    }

}
