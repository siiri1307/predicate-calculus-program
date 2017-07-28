package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 05/03/17.
 */
public class Ekvivalents extends Valem {

    private Valem vasakLaps;
    private Valem paremLaps;

    public Ekvivalents(Valem valem1, Valem valem2){

        this.vasakLaps = valem1;
        this.paremLaps = valem2;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) vasakLaps, paremLaps);
    }

    public Valem getVasakLaps() {
        return vasakLaps;
    }

    public Valem getParemLaps() {
        return paremLaps;
    }

    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus) {

        return ekvivalents(vasakLaps.vaartusta(vaartustus), paremLaps.vaartusta(vaartustus));

    }

    private boolean ekvivalents(boolean valem1, boolean valem2) {
        if((valem1 && valem2) || (!valem1 && !valem2)){
            return true;
        }
        return false;
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        indiviidTermid.addAll(paremLaps.getIndiviidTermid());
        indiviidTermid.addAll(vasakLaps.getIndiviidTermid());

        return indiviidTermid;
    }

    public Set<Muutuja> getVabadMuutujad() {

        Set<Muutuja> vabad = new HashSet<>();

        Set<Muutuja> vabadMuutujadVasakul = vasakLaps.getVabadMuutujad();
        Set<Muutuja> vabadMuutujadParemal = paremLaps.getVabadMuutujad();

        if(vabadMuutujadVasakul != null){
            vabad.addAll(vabadMuutujadVasakul);
        }
        if(vabadMuutujadParemal != null){
            vabad.addAll(vabadMuutujadParemal);
        }

        return vabad;
    }

    public static Valem looEkvivalentsid(List<Valem> alamValemid) {

        //return alamValemid.stream().reduce((x,y) -> new Ekvivalents(x,y)).orElse(alamValemid.get(0));


        Valem vasak = alamValemid.get(0);

        for(int i = 1; i < alamValemid.size(); i++){
            vasak = new Ekvivalents(vasak, alamValemid.get(i));

        }

        return vasak;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid) {

        TõesuspuuTipp vasakLaps;
        TõesuspuuTipp lapseLapsVasakul;
        TõesuspuuTipp paremLaps;
        TõesuspuuTipp lapseLapsParemal;

        if(tõeväärtus){

            vasakLaps = new TõesuspuuTipp(this.vasakLaps, true);
            lapseLapsVasakul = new TõesuspuuTipp(this.paremLaps, true);

            vasakLaps.setVasakLaps(lapseLapsVasakul); //kui hargnemist ei toimu, siis laps vasakule

            paremLaps = new TõesuspuuTipp(this.vasakLaps, false);
            lapseLapsParemal = new TõesuspuuTipp(this.paremLaps, false);

            paremLaps.setVasakLaps(lapseLapsParemal);

            return Arrays.asList(vasakLaps, paremLaps);
        }
        else{

            vasakLaps = new TõesuspuuTipp(this.vasakLaps, true);
            lapseLapsVasakul = new TõesuspuuTipp(this.paremLaps, false);

            vasakLaps.setVasakLaps(lapseLapsVasakul);

            paremLaps = new TõesuspuuTipp(this.vasakLaps, false);
            lapseLapsParemal = new TõesuspuuTipp(this.paremLaps, true);

            paremLaps.setVasakLaps(lapseLapsParemal);

            return Arrays.asList(vasakLaps, paremLaps);
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

        Ekvivalents ekvivalentsiValem = (Ekvivalents) valem;

        return vasakLaps.equals(ekvivalentsiValem.vasakLaps) && paremLaps.equals(ekvivalentsiValem.paremLaps);
    }

    @Override
    public String dot() {
        return  vasakLaps.dot() + " ~ " + paremLaps.dot();
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
        throw new NotImplementedException();
    }
}
