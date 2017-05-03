package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class Disjunktsioon extends Valem{

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
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return vasakLaps.vaartusta(vaartustus) || paremLaps.vaartusta(vaartustus);

    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        indiviidTermid.addAll(vasakLaps.getIndiviidTermid());
        indiviidTermid.addAll(paremLaps.getIndiviidTermid());

        return indiviidTermid;
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        Set<Character> vabad = new HashSet<>();

        vabad.addAll(vasakLaps.getVabadMuutujad());
        vabad.addAll(paremLaps.getVabadMuutujad());

        return vabad;
    }

    public static Valem looDisjunktsioonid(List<Valem> alamValemid) {

        Valem vasak = alamValemid.get(0);

        for(int i = 1; i < alamValemid.size(); i++){
            vasak = new Disjunktsioon(vasak, alamValemid.get(i));

        }

        return vasak;
    }

    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad) {

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
    public boolean equals(Valem valem) {

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
    public void uusKonstantSumbol(Character sumbol) {

        vasakLaps.uusKonstantSumbol(sumbol);
        paremLaps.uusKonstantSumbol(sumbol);
    }

    @Override
    public Valem koopia() {
        return new Disjunktsioon(this);
    }
}
