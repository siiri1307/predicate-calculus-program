package predmoodul.termid;

import java.util.*;

/**
 * Created by siiri on 10/03/17.
 */
public class LiitTerm extends TehteTerm {

    /*public LiitTerm(List<Term> termid) {
        super(termid);
    }*/

    //private Term vasakTerm;
    //private Term paremTerm;

    public LiitTerm(Term vasakTerm, Term paremTerm){
        this.vasakTerm = vasakTerm;
        this.paremTerm = paremTerm;
    }

    //copy constructor
    public LiitTerm(LiitTerm lt){

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

        return vasakTerm.vaartusta(vaartustus) + paremTerm.vaartusta(vaartustus);
        //return teeTehe(vaartustus, alamTermid, (summa,liidetav) -> summa + liidetav);

    }

    public static Term binaarneLiitmine(List<Term> termid) {

        Term vasakTerm = termid.get(0);

        for(int i = 1; i < termid.size(); i++){
            vasakTerm = new LiitTerm(vasakTerm, termid.get(i));
        }

        return vasakTerm;
    }

    @Override
    public String dot() {

        String liitterm = "+Term(";

        liitterm += vasakTerm.dot() + ", ";

        liitterm += paremTerm.dot();

        liitterm += ")";

        /*for(int i = 0; i < alamTermid.size()-1; i++){
            liitterm += alamTermid.get(i).dot() + ",";
        }

        liitterm += alamTermid.get(alamTermid.size()-1) + ")";*/

        return liitterm;
    }

    @Override
    public Character getTahis() {
        return null;
    }

    @Override
    public Term koopia() {
        return new LiitTerm(this);
    }
}
