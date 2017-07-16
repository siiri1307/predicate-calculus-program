package predmoodul.kvantorid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;
import predmoodul.valemid.Termikuulaja;
import predmoodul.valemid.TõesuspuuTipp;
import predmoodul.valemid.Valem;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 09/03/17.
 */
public class Iga extends Valem implements Kvantor {

    private Valem valem;
    private Character indiviidmuutuja;

    public Iga(Valem valem, Character indiviidmuutuja){
        this.valem = valem;
        this.indiviidmuutuja = indiviidmuutuja;
    }

    public Iga(Iga yldisusKvantor){
        this.valem = yldisusKvantor.valem.koopia();
        this.indiviidmuutuja = yldisusKvantor.indiviidmuutuja;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem, indiviidmuutuja);
    }

    @Override
    public String toString() {
        return "Iga{" +
                "indiviidmuutuja=" + indiviidmuutuja + ", valem=" + valem.toString() +
                '}';
    }

    @Override
    public Character getIndiviidMuutuja() {
        return this.indiviidmuutuja;
    }


    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        for(double i = 0; i < 100; i++){
            vaartustus.put(indiviidmuutuja, i);
            boolean valemiVaartus = valem.vaartusta(vaartustus);
            if(!valemiVaartus){
                return false;
            }
        }

        return true;
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();
        indiviidTermid.add(new IndiviidTerm(indiviidmuutuja));
        return indiviidTermid;
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        Set<Character> valemiVabadMuutujad = valem.getVabadMuutujad();
        valemiVabadMuutujad.remove(indiviidmuutuja);

        return valemiVabadMuutujad;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad,  Set<Character> harusEsinenudTermid) {

        //Set<Character> harusEsinenudTermid = new HashSet<>();

        //return reegelKvantorile(tõeväärtus, harusEsinenudTermid, kuulajad);
    //}

    //private List<TõesuspuuTipp> reegelKvantorile(boolean tõeväärtus, Set<Character> harusEsinenudTermid, Set<Termikuulaja> kuulajad) {

        // Map<Character, Optional<Double>> vaartustus

        List<TõesuspuuTipp> tõesuspuuTipud = new ArrayList<>();

        if(tõeväärtus){

            //kasuta olemasolevat termi (indiviidmuutuja)

            if(harusEsinenudTermid.isEmpty()){ //ühtegi termi harus ei esine; eeldame, et mingi element on alati olemas, sest põhihulk ei saa olla tühi
                Character term = 'a';
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja);
                TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, true);
                tõesuspuuTipud.add(laps);
                puusEsinenudTermid.add(term);
            }
            else {

                //vt õpikus näide 22, lk 62; kui harus on kasutatud mitut konstantsümbolit, siis tuleb valemis kõiki neid kasutada

                /*see loogika on nüüd Tõesuspuu klassis Set<Character> harusEsinenudTermidKoopia = new HashSet<>(harusEsinenudTermid);
                Iterator<Character> it = harusEsinenudTermidKoopia.iterator();
                Character term = it.next();
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja);
                TõesuspuuTipp juur = new TõesuspuuTipp(valemiKoopia, true);
                TõesuspuuTipp leht = juur;*/

 /*               while (it.hasNext()) {
                    term = it.next();
                    valemiKoopia = this.valem.koopia();
                    valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja);
                    TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, true);
                    leht.setVasakLaps(laps);
                    leht = laps;
                }*/

                //tõesuspuuTipud.add(juur);
            }
        }
        else{

            //too sisse uus konstantsümbol, mida harus ei esine
            Character suvalineSumbol = tagastaSuvalineKasutamataSumbol(puusEsinenudTermid);
            puusEsinenudTermid.add(suvalineSumbol);

            //Character uus = 'z';

            Valem valemiKoopia = this.valem.koopia();
            valemiKoopia.uusKonstantSumbol(suvalineSumbol, indiviidmuutuja);
            TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);

            //Kuulajate loogika
            /*TõesuspuuTipp leht = laps;
            for (Termikuulaja kuulaja : kuulajad) {
                TõesuspuuTipp kvantoriTipp = kuulaja.kuulaKonstantSumbolit(suvalineSumbol);
                leht.setVasakLaps(kvantoriTipp);
                leht = kvantoriTipp;
            }*/

            tõesuspuuTipud.add(laps);
        }

        return tõesuspuuTipud;
    }

    private Character tagastaSuvalineKasutamataSumbol(Set<Character> puusEsinenudTermid) {

        /*Set<Character> voimalikudTermid = new HashSet<>();

        for(int i = 0; i < 256; i++){
            voimalikudTermid.add((char) i);
        }*/

        Set<Character> voimalikudTermid = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 't'));

        voimalikudTermid.removeAll(puusEsinenudTermid); //kahe set'i vahe, et saada teada sümbolid, mida pole veel harus kasutatud

        return voimalikudTermid.iterator().next();
    }

    @Override
    public boolean equals(Object valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        Iga universaalKvantorValem = (Iga) valem;

        return this.valem.equals(universaalKvantorValem.valem);
    }

    @Override
    public String dot() {
        return "A" + indiviidmuutuja +"(" + valem.dot() + ")";
    }

    @Override
    public void uusKonstantSumbol(Character uusSumbol, Character vanaSumbol) {
        if(vanaSumbol.equals(indiviidmuutuja)) {
            return;
        }
        valem.uusKonstantSumbol(uusSumbol, vanaSumbol);
    }

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {
        valem.asendaTerm(uus, tingimus);
    }

    @Override
    public Valem koopia() {

        return new Iga(this);
    }

    @Override
    public Optional<Termikuulaja> getKuulaja(boolean tõeväärtus) {
        if (tõeväärtus) {
            return Optional.of(new Termikuulaja() {

                @Override
                public TõesuspuuTipp kuulaKonstantSumbolit(Character c) {
                    Valem koopia = valem.koopia();
                    koopia.uusKonstantSumbol(c, indiviidmuutuja);
                    return new TõesuspuuTipp(koopia, true);
                }
            });
        }
        return Optional.empty();
    }
}
