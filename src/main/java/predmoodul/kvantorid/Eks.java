package predmoodul.kvantorid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.valemid.Termikuulaja;
import predmoodul.valemid.TõesuspuuTipp;
import predmoodul.valemid.Valem;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by siiri on 09/03/17.
 */
public class Eks extends Valem implements Kvantor {

    private Valem valem;
    private Character indiviidmuutuja;

    public Eks(Valem valem, Character indiviidmuutuja){
        this.valem = valem;
        this.indiviidmuutuja = indiviidmuutuja;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem, indiviidmuutuja);
    }

    @Override
    public String toString() {
        return "Eks{" +
                "indiviidmuutuja=" + indiviidmuutuja +
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
            boolean valemiVäärtus = valem.vaartusta(vaartustus);
            if(valemiVäärtus){
                return true;
            }
        }

        return false;

    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return null;
    }

    @Override
    public Set<Character> getVabadMuutujad() {

        Set<Character> valemiVabadMuutujad = valem.getVabadMuutujad();
        valemiVabadMuutujad.remove(indiviidmuutuja);

        return valemiVabadMuutujad;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> esinenudTermid, Set<Termikuulaja> kuulajad) {

        return reegelKvantorile(tõeväärtus, esinenudTermid, kuulajad);

    }

    private List<TõesuspuuTipp> reegelKvantorile(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad) {

         List<TõesuspuuTipp> tõesuspuuTipud = new ArrayList<>();

        if(tõeväärtus){

            //too sisse uus konstantsümbol, mida harus ei esine
            Character suvalineSumbol = tagastaSuvalineKasutamataSumbol(puusEsinenudTermid);
            puusEsinenudTermid.add(suvalineSumbol);

            Valem valemiKoopia = this.valem.koopia();
            valemiKoopia.uusKonstantSumbol(suvalineSumbol);

            //Character uus = 'a';

            //valemiKoopia.uusKonstantSumbol(suvalineSumbol);

            TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, true);

            //kuulajate loogika
            TõesuspuuTipp leht = laps;
            for (Termikuulaja kuulaja : kuulajad) {
                TõesuspuuTipp kvantoriTipp = kuulaja.kuulaKonstantSumbolit(suvalineSumbol);
                leht.setVasakLaps(kvantoriTipp);
                leht = kvantoriTipp;
            }

            tõesuspuuTipud.add(laps);
        }

        else{

            //kasuta olemasolevat termi (indiviidmuutuja)
            /*if(puusEsinenudTermid.isEmpty()) { //ühtegi termi harus ei esine; eeldame, et mingi element on alati olemas, sest põhihulk ei saa olla tühi
                puusEsinenudTermid.add('t');
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol('t');
                TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);
                tõesuspuuTipud.add(laps);
            }*/
            { //vt õpikus näide 22, lk 62; kui harus on kasutatud mitut konstantsümbolit, siis tuleb valemis kõiki neid kasutada
                Set<Character> puusEsinenudTermidKoopia = new HashSet<>(puusEsinenudTermid);

                /*Character term = puusEsinenudTermidKoopia.iterator().next();
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol(term);
                TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);
                puusEsinenudTermidKoopia.remove(term);

                lisalapsed(puusEsinenudTermidKoopia, laps);
                tõesuspuuTipud.add(laps);*/

                for(Character term : puusEsinenudTermidKoopia){
                    Valem valemiKoopia = this.valem.koopia();
                    valemiKoopia.uusKonstantSumbol(term);
                    TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);
                    tõesuspuuTipud.add(laps);
                }
            }
        }

        return tõesuspuuTipud;

    }


    private void lisalapsed(Set<Character> puusEsinenudTermidKoopia, TõesuspuuTipp laps) {

        for(Character term : puusEsinenudTermidKoopia){
            Valem valemiKoopia = this.valem.koopia();
            valemiKoopia.uusKonstantSumbol(term);
            TõesuspuuTipp lapsLaps = new TõesuspuuTipp(valemiKoopia, false);
            laps.setVasakLaps(lapsLaps);
        }
    }

    private Character tagastaSuvalineKasutamataSumbol(Set<Character> puusEsinenudTermid) {

        Set<Character> voimalikudTermid = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q'));

        voimalikudTermid.removeAll(puusEsinenudTermid); //kahe set'i vahe, et saada teada sümbolid, mida pole veel puus kasutatud

        return voimalikudTermid.iterator().next();
    }


    @Override
    public boolean equals(Valem valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        Eks leidubValem = (Eks) valem;

        return this.valem.equals(leidubValem.valem);
    }

    @Override
    public Optional<Termikuulaja> getKuulaja(boolean tõeväärtus) {
        if (!tõeväärtus) {
            return Optional.of(new Termikuulaja() {

                @Override
                public TõesuspuuTipp kuulaKonstantSumbolit(Character c) {
                    Valem koopia = valem.koopia();
                    koopia.uusKonstantSumbol(c);
                    return new TõesuspuuTipp(koopia, false);
                }
            });
        }
        return Optional.empty();
    }

    @Override
    public String dot() {
        return "E(" + valem.dot() + ")";
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {
        valem.uusKonstantSumbol(sumbol);
    }

    @Override
    public Valem koopia() {
        throw new NotImplementedException();
    }
}
