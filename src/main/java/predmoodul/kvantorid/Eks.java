package predmoodul.kvantorid;

import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.Term;
import predmoodul.valemid.Muutuja;
import predmoodul.valemid.Termikuulaja;
import predmoodul.valemid.TõesuspuuTipp;
import predmoodul.valemid.Valem;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by siiri on 09/03/17.
 */
public class Eks extends Valem implements Kvantor {

    private Valem valem;
    private Muutuja indiviidmuutuja;

    public Eks(Valem valem, Muutuja indiviidmuutuja){
        this.valem = valem;
        this.indiviidmuutuja = indiviidmuutuja;
    }

    public Eks(Eks leidubKvantor){
        this.valem = leidubKvantor.valem.koopia();
        this.indiviidmuutuja = leidubKvantor.indiviidmuutuja;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) valem, indiviidmuutuja);
    }

    @Override
    public String toString() {
        return "Eks{" +
                "indiviidmuutuja=" + indiviidmuutuja.toString() + ", valem=" + valem +
                '}';
    }

    @Override
    public Muutuja getIndiviidMuutuja() {
        return this.indiviidmuutuja;
    }


    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus) {

        for(double i = 0; i < maxVaartus; i++){
            vaartustus.put(indiviidmuutuja, i);
            boolean valemiVäärtus = valem.vaartusta(vaartustus, maxVaartus);
            if(valemiVäärtus){
                return true;
            }
        }

        return false;

    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();
        indiviidTermid.add(new IndiviidTerm(indiviidmuutuja));
        return indiviidTermid;
    }

    @Override
    public Set<Muutuja> getVabadMuutujad() {

        Set<Muutuja> valemiVabadMuutujad = valem.getVabadMuutujad();
        valemiVabadMuutujad.remove(indiviidmuutuja);

        return valemiVabadMuutujad;
    }

    @Override
    public Set<Muutuja> getSeotudMuutujad() {

        Set<Muutuja> valemiSeotudMuutujad = valem.getSeotudMuutujad();
        valemiSeotudMuutujad.add(indiviidmuutuja);

        return valemiSeotudMuutujad;
    }


    @Override
    public int getKvantoriteArv() {
        return 1 + valem.getKvantoriteArv();
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid) {

        //return reegelKvantorile(tõeväärtus, puusEsinenudTermid, kuulajad);

    //}

    //private List<TõesuspuuTipp> reegelKvantorile(boolean tõeväärtus, Set<Character> harusEsinenudTermid, Set<Termikuulaja> kuulajad) {

         List<TõesuspuuTipp> tõesuspuuTipud = new ArrayList<>();

        if(tõeväärtus){

            //too sisse uus konstantsümbol, mida harus ei esine
            Muutuja suvalineSumbol = tagastaSuvalineKasutamataSumbol(puusEsinenudTermid, harusEsinenudTermid);
            //Muutuja suvalineSumbol = tagastaSuvalineKasutamataSumbol(harusEsinenudTermid);
            puusEsinenudTermid.add(suvalineSumbol);

            Valem valemiKoopia = this.valem.koopia();
            valemiKoopia.uusKonstantSumbol(suvalineSumbol, indiviidmuutuja);//, false
            TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, true);

            //kuulajate loogika
            /*TõesuspuuTipp leht = laps;
            for (Termikuulaja kuulaja : kuulajad) {
                TõesuspuuTipp kvantoriTipp = kuulaja.kuulaKonstantSumbolit(suvalineSumbol);
                leht.setVasakLaps(kvantoriTipp);
                leht = kvantoriTipp;
            }*/

            tõesuspuuTipud.add(laps);
        }

        else{

            //kasuta olemasolevat termi (indiviidmuutuja)
            if(harusEsinenudTermid.isEmpty()){ //ühtegi termi harus ei esine; eeldame, et mingi element on alati olemas, sest põhihulk ei saa olla tühi
                Muutuja term = new Muutuja('m', Muutuja.uusMNumber());
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja); //, false
                TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);
                tõesuspuuTipud.add(laps);
                puusEsinenudTermid.add(term);
            }
            else{
                //vt õpikus näide 22, lk 62; kui harus on kasutatud mitut konstantsümbolit, siis tuleb valemis kõiki neid kasutada
/*                Set<Character> harusEsinenudTermidKoopia = new HashSet<>(harusEsinenudTermid);
                Iterator<Character> it = harusEsinenudTermidKoopia.iterator();
                Character term = it.next();
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja);
                TõesuspuuTipp juur = new TõesuspuuTipp(valemiKoopia, false);
                TõesuspuuTipp leht = juur;*/

      /*          while (it.hasNext()) {
                    term = it.next();
                    valemiKoopia = this.valem.koopia();
                    valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja);
                    TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);
                    leht.setVasakLaps(laps);
                    leht = laps;
                }*/
                //tõesuspuuTipud.add(juur);
            }
        }

        return tõesuspuuTipud;

    }

    private Muutuja tagastaSuvalineKasutamataSumbol(Set<Muutuja> puusEsinenudTermid, Set<Muutuja> harusEsinenudTermid) {

//        int seniSuurim = 0;
//
//        for(Muutuja m : harusEsinenudTermid){
//            if(m.getTahis().equals('m') && m.getJarjeNumber() > seniSuurim){
//                seniSuurim = m.getJarjeNumber();
//            }
//        }

        //harusEsinenudTermid.add(uusMuutuja);
        return new Muutuja('m', Muutuja.uusMNumber());


        /*Set<Character> voimalikudTermid = new HashSet<>();

        for(int i = 0; i < 256; i++){
            voimalikudTermid.add((char)i);
        }*/

        //Set<Character> voimalikudTermid = new HashSet<>(Arrays.asList('b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 't'));

        //voimalikudTermid.removeAll(puusEsinenudTermid); //kahe set'i vahe, et saada teada sümbolid, mida pole veel harus kasutatud

        //return new Muutuja(voimalikudTermid.iterator().next(),0);

        //throw new NotImplementedException();
    }


    @Override
    public boolean equals(Object valem) {

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
                public TõesuspuuTipp kuulaKonstantSumbolit(Muutuja c) {
                    Valem koopia = valem.koopia();
                    koopia.uusKonstantSumbol(c, indiviidmuutuja);//, false
                    return new TõesuspuuTipp(koopia, false);
                }
            });
        }
        return Optional.empty();
    }

    @Override
    public String dot() {
        return "∃" + indiviidmuutuja + "(" + valem.dot() + ")";
    }

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) { //, boolean vahetaKvantoriSees
        if(vanaSumbol.equals(getIndiviidMuutuja()) ) {
            return;
        }
            /*if(!vahetaKvantoriSees) {
                return;
            }
            else {
                vahetaKvantoriSees = false;
            }
        }*/
        valem.uusKonstantSumbol(uusSumbol, vanaSumbol); //, vahetaKvantoriSees
    }

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {
        valem.asendaTerm(uus, tingimus);
    }

    @Override
    public Valem koopia() {

        return new Eks(this);
    }
}
