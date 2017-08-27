package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 05/03/17.
 */
public class Disjunktsioon extends Valem {

    private final Valem vasakLaps;
    private final Valem paremLaps;

    public Disjunktsioon(Valem valem1, Valem valem2){
        this.vasakLaps = valem1;
        this.paremLaps = valem2;
    }

    //copy constructor
    public Disjunktsioon(Disjunktsioon d){
        this.vasakLaps = d.vasakLaps.koopia();
        this.paremLaps = d.paremLaps.koopia();
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) vasakLaps, paremLaps);
    }

    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus) {

        return vasakLaps.vaartusta(vaartustus, maxVaartus) || paremLaps.vaartusta(vaartustus, maxVaartus);

    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        indiviidTermid.addAll(vasakLaps.getIndiviidTermid());
        indiviidTermid.addAll(paremLaps.getIndiviidTermid());

        return indiviidTermid;
    }

    @Override
    public Set<Muutuja> getVabadMuutujad() {

        Set<Muutuja> vabad = new HashSet<>();

        vabad.addAll(vasakLaps.getVabadMuutujad());
        vabad.addAll(paremLaps.getVabadMuutujad());

        return vabad;
    }

    @Override
    public Set<Muutuja> getSeotudMuutujad() {

        Set<Muutuja> seotud = new HashSet<>();

        seotud.addAll(vasakLaps.getSeotudMuutujad());
        seotud.addAll(paremLaps.getSeotudMuutujad());

        return seotud;
    }


    @Override
    public int getKvantoriteArv() {
        return vasakLaps.getKvantoriteArv() + paremLaps.getKvantoriteArv();
    }

    public static Valem looDisjunktsioonid(List<Valem> alamValemid) {

        Valem vasak = alamValemid.get(0);

        for(int i = 1; i < alamValemid.size(); i++){
            vasak = new Disjunktsioon(vasak, alamValemid.get(i));

        }

        return vasak;
    }

    @Override
    public int getKvantoriteSygavus() {

        return Integer.max(vasakLaps.getKvantoriteSygavus(), paremLaps.getKvantoriteSygavus());
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid) {

        if(tõeväärtus){
            return Arrays.asList(new TõesuspuuTipp(this.vasakLaps, true), new TõesuspuuTipp(this.paremLaps, true));
        }
        else{

            TõesuspuuTipp laps = new TõesuspuuTipp(vasakLaps, false);
            TõesuspuuTipp lapseLaps = new TõesuspuuTipp(paremLaps, false);

            laps.setVasakLaps(lapseLaps);

            return Arrays.asList(laps);
        }
    }

    @Override
    public boolean equals(Object valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        Disjunktsioon disjunktsiooniValem = (Disjunktsioon) valem;

        return vasakLaps.equals(disjunktsiooniValem.vasakLaps) && paremLaps.equals(disjunktsiooniValem.paremLaps);
    }

    @Override
    public String dot() {
        return  vasakLaps.dot() + " v " + paremLaps.dot();
    }

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {

        vasakLaps.uusKonstantSumbol(uusSumbol, vanaSumbol);
        paremLaps.uusKonstantSumbol(uusSumbol, vanaSumbol);
    }

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {

        vasakLaps.asendaTerm(uus, tingimus);
        paremLaps.asendaTerm(uus, tingimus);
    }

    @Override
    public Valem koopia() {
        return new Disjunktsioon(this);
    }
}
