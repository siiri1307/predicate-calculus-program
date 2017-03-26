package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;

import java.util.*;

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

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) vasakTerm, paremTerm);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {
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
    public Set<Character> getVabadMuutujad() {
        return null;
    }
}
