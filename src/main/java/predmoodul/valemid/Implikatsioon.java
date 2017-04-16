package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

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
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return implikatsioon(vasakLaps.vaartusta(vaartustus), paremLaps.vaartusta(vaartustus));

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

    public Set<Character> getVabadMuutujad() {

        Set<Character> vabad = new HashSet<>();

        vabad.addAll(paremLaps.getVabadMuutujad());
        vabad.addAll(vasakLaps.getVabadMuutujad());

        /*for(Valem valem : disjunktsiooniValemid){
            vabad.addAll(valem.getVabadMuutujad());
        }*/

        return vabad;
    }


    public static Valem looImplikatsioonid(List<Valem> alamValemid) {

        Valem vasak = alamValemid.get(0);

        for(int i = 1; i < alamValemid.size(); i++){
            vasak = new Implikatsioon(vasak, alamValemid.get(i));

        }

        return vasak;
    }

    public List<TõesuspuuTipp> reegel(boolean tõeväärtus){

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
}
