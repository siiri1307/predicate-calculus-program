package predmoodul.termid;

import predmoodul.valemid.Muutuja;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 25/03/17.
 */
public abstract class TehteTerm extends Term {

    //protected List<Term> alamTermid;
    protected Term vasakTerm;
    protected Term paremTerm;

    /*public TehteTerm(List<Term> termid) {
        this.alamTermid = termid;
    }*/

    public TehteTerm(){}

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        /*for(Term term : alamTermid){
            indiviidTermid.addAll(term.getIndiviidTermid());
        }*/

        indiviidTermid.addAll(vasakTerm.getIndiviidTermid());

        indiviidTermid.addAll(paremTerm.getIndiviidTermid());

        return indiviidTermid;

        }

    @Override
    public boolean equals(Object term) {

        if(this == term){
            return true;
        }
        if(term == null || this.getClass() != term.getClass()){
            return false;
        }

        TehteTerm tehteTerm = (TehteTerm) term;

        return this.vasakTerm.equals(tehteTerm.vasakTerm) && this.paremTerm.equals(tehteTerm.paremTerm);
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(vasakTerm, paremTerm);
    }

    @Override
    public void asendaTerm(Term uusTerm, Predicate<Term> tingimus) {
        if(tingimus.test(vasakTerm)){
            vasakTerm = uusTerm.koopia();
        }
        else{
            vasakTerm.asendaTerm(uusTerm, tingimus);
        }
        if(tingimus.test(paremTerm)){
            paremTerm = uusTerm.koopia();
        }
        else{
            paremTerm.asendaTerm(uusTerm, tingimus);
        }
    }

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {

        vasakTerm.uusKonstantSumbol(uusSumbol, vanaSumbol);
        paremTerm.uusKonstantSumbol(uusSumbol, vanaSumbol);

        /*for(Term alamterm : alamTermid){
            alamterm.uusKonstantSumbol(uusSumbol, vanaSumbol);
        }*/
    }
}
