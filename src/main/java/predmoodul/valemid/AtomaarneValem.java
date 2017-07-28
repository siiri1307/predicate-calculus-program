package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 05/03/17.
 */
public class AtomaarneValem extends Valem {

    private Term vasakTerm; //vasakpool võrdusmärki
    private Term paremTerm;

    public AtomaarneValem(Term vasak, Term parem) {
        this.vasakTerm = vasak;
        this.paremTerm = parem;
    }

    public AtomaarneValem(AtomaarneValem av){
        this.vasakTerm = av.vasakTerm.koopia();
        this.paremTerm = av.paremTerm.koopia();
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) vasakTerm, paremTerm);
    }

    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus) {

        return vasakTerm.vaartusta(vaartustus) == paremTerm.vaartusta(vaartustus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidMuutujadVasakul = vasakTerm.getIndiviidTermid();
        Set<IndiviidTerm> indiviidMuutujadParemal = paremTerm.getIndiviidTermid();
        indiviidMuutujadVasakul.addAll(indiviidMuutujadParemal);
        return indiviidMuutujadVasakul;
    }

    public Term getVasakTerm() {
        return vasakTerm;
    }

    public Term getParemTerm() {
        return paremTerm;
    }


    @Override
    public Set<Muutuja> getVabadMuutujad() {
        Set<Muutuja> s = new HashSet<>();
        for (IndiviidTerm id : getIndiviidTermid()) {

            s.add(id.getTahis());

        }
        return s;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid) {

        return new ArrayList<>();

    }

    @Override
    public boolean equals(Object valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        AtomaarneValem atomaarneValem = (AtomaarneValem) valem;

        return this.vasakTerm.equals(atomaarneValem.vasakTerm) && this.paremTerm.equals(atomaarneValem.paremTerm);
    }

    @Override
    public String dot() {
        return   vasakTerm.dot() + " = " + paremTerm.dot();
    }

    /*@Override
    public String dot() {
        return "";
    }*/

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {

        vasakTerm.uusKonstantSumbol(uusSumbol, vanaSumbol);
        paremTerm.uusKonstantSumbol(uusSumbol, vanaSumbol);
    }

    @Override
    public void asendaTerm(Term uusTerm, Predicate<Term> tingimus) {
        if (tingimus.test(vasakTerm)) {
            vasakTerm = uusTerm.koopia();

        } else {
            vasakTerm.asendaTerm(uusTerm, tingimus);
        }
        if (tingimus.test(paremTerm)) {
            paremTerm = uusTerm.koopia();

        } else {
            paremTerm.asendaTerm(uusTerm, tingimus);
        }
    }

    @Override
    public Valem koopia() {
        return new AtomaarneValem(this);
    }
}
