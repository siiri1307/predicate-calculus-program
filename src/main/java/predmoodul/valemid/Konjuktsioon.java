package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class Konjuktsioon extends Valem {

    //private List<Kvantor> kvantorid;
    //private Valem valem;
    //private boolean eitus;

    private Valem vasakLaps;
    private Valem paremLaps;

    public Konjuktsioon(Valem valem1, Valem valem2){
        this.vasakLaps = valem1;
        this.paremLaps = valem2;
        //this.kvantorid = kvantorid;
        //this.valem = valem;
        //this.eitus = eitus;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) vasakLaps, paremLaps);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        indiviidTermid.addAll(vasakLaps.getIndiviidTermid());
        indiviidTermid.addAll(paremLaps.getIndiviidTermid());

        return indiviidTermid;
        //return valem.getIndiviidTermid();
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        Set<Character> vabad = new HashSet<>();

        vabad.addAll(vasakLaps.getVabadMuutujad());
        vabad.addAll(paremLaps.getVabadMuutujad());

        return vabad;

        /*Set<Character> kvantoritegaSeotud = new HashSet<>();
        Set<Character> koikMuutujad = new HashSet<>();

        for(Kvantor kv : kvantorid){
            kvantoritegaSeotud.add(kv.getIndiviidMuutuja());
        }

        Set<IndiviidTerm> koikIndiviidMuutujad = getIndiviidTermid();

        for(IndiviidTerm indterm : koikIndiviidMuutujad){
            koikMuutujad.add(indterm.getTahis());
        }

        koikMuutujad.removeAll(kvantoritegaSeotud);
        System.out.println("Kvantoriga seotud muutujad on: " + kvantoritegaSeotud);

        System.out.println("Vabad muutujad on: " + koikMuutujad);

        return koikMuutujad;*/
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return vasakLaps.vaartusta(vaartustus) && paremLaps.vaartusta(vaartustus);

        /*if(eitus){
            return !vaartustaAbi(vaartustus, new ArrayList<>(kvantorid));
        }

        return vaartustaAbi(vaartustus, new ArrayList<>(kvantorid));*/
    }

    /*boolean vaartustaAbi(Map<Character, Double> vaartustus, List<Kvantor> kvantorid){

        if (kvantorid.isEmpty()) {
            return valem.vaartusta(vaartustus);
        }

        List<Kvantor> koopiakvantoritest = new ArrayList<>(kvantorid);
        Kvantor kvantor = koopiakvantoritest.remove(0);
        for(double j= 0; j < 10; j++){
            vaartustus.put(kvantor.getIndiviidMuutuja(), j);
            boolean toeVaartus = vaartustaAbi(vaartustus, koopiakvantoritest);
            if(kvantor.lõpetamiseTingimus() == toeVaartus){
                return kvantor.lõpetamiseTingimus();
            }
        }

        return !kvantor.lõpetamiseTingimus();

    }*/

    public static Valem looKonjuktsioonid(List<Valem> alamValemid) {

        Valem vasak = alamValemid.get(0);

        for(int i = 1; i < alamValemid.size(); i++){
            vasak = new Konjuktsioon(vasak, alamValemid.get(i));

        }

        return vasak;
    }

    public List<TõesuspuuTipp> reegel(boolean tõeväärtus) {

        if(tõeväärtus){

            TõesuspuuTipp laps = new TõesuspuuTipp(this.vasakLaps, true);
            TõesuspuuTipp lapseLaps = new TõesuspuuTipp(this.paremLaps, true);

            laps.setVasakLaps(lapseLaps);

            return Arrays.asList(laps);
        }
        else{

            TõesuspuuTipp vasakLaps = new TõesuspuuTipp(this.vasakLaps, false);
            TõesuspuuTipp paremLaps = new TõesuspuuTipp(this.paremLaps, false);

            return Arrays.asList(vasakLaps, paremLaps);
        }
    }
}
