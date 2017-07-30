package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 05/03/17.
 */
public class Implikatsioon extends Valem {

    private Valem vasakLaps;
    private Valem paremLaps;

    public Implikatsioon(Valem valem1, Valem valem2){

        this.vasakLaps = valem1;
        this.paremLaps = valem2;
    }

    //copy constructor
    public Implikatsioon(Implikatsioon impl){
        this.vasakLaps = impl.vasakLaps.koopia();
        this.paremLaps = impl.paremLaps.koopia();
    }

    public Valem getVasakLaps() {
        return vasakLaps;
    }

    public Valem getParemLaps() {
        return paremLaps;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) vasakLaps, paremLaps);
    }

    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus) {

        return implikatsioon(vasakLaps.vaartusta(vaartustus, maxVaartus), paremLaps.vaartusta(vaartustus, maxVaartus));

        /*return  disjunktsiooniValemid.stream()
                .map(valem -> valem.vaartusta(vaartustus))
                .reduce((valem1,valem2) -> valem1 || valem2).get();*/
    }

    private boolean implikatsioon(boolean valem1, boolean valem2) {
        if(valem1 && !valem2){
            return false;
        }
        return true;
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        indiviidTermid.addAll(paremLaps.getIndiviidTermid());
        indiviidTermid.addAll(vasakLaps.getIndiviidTermid());

        /*for(Valem valem : disjunktsiooniValemid){
            indiviidTermid.addAll(valem.getIndiviidTermid());
        }*/
        return indiviidTermid;
    }

    public Set<Muutuja> getVabadMuutujad() {

        Set<Muutuja> vabad = new HashSet<>();

        vabad.addAll(paremLaps.getVabadMuutujad());
        vabad.addAll(vasakLaps.getVabadMuutujad());

        /*for(Valem valem : disjunktsiooniValemid){
            vabad.addAll(valem.getVabadMuutujad());
        }*/

        return vabad;
    }

    @Override
    public int getKvantoriteArv() {
        return vasakLaps.getKvantoriteArv() + paremLaps.getKvantoriteArv();
    }


    public static Valem looImplikatsioonid(List<Valem> alamValemid) {

        Valem vasak = alamValemid.get(0);

        for(int i = 1; i < alamValemid.size(); i++){
            vasak = new Implikatsioon(vasak, alamValemid.get(i));

        }

        return vasak;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid){

        if(tõeväärtus){

            return Arrays.asList(new TõesuspuuTipp(this.vasakLaps, false), new TõesuspuuTipp(this.paremLaps, true));
        }
        else{
            //kui hargnemist ei ole, siis alluv seatakse vasakule
            TõesuspuuTipp laps = new TõesuspuuTipp(this.vasakLaps, true);
            TõesuspuuTipp lapseLaps = new TõesuspuuTipp(this.paremLaps, false);
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

        Implikatsioon implikatsiooniValem = (Implikatsioon) valem;

        return vasakLaps.equals(implikatsiooniValem.vasakLaps) && paremLaps.equals(implikatsiooniValem.paremLaps);
    }

    @Override
    public String dot() {
        return "(" + vasakLaps.dot() + " -> " + paremLaps.dot() +")";
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

        return new Implikatsioon(this);
    }
}
