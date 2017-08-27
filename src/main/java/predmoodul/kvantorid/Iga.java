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
public class Iga extends Valem implements Kvantor {

    private Valem valem;
    private Muutuja indiviidmuutuja;

    public Iga(Valem valem, Muutuja indiviidmuutuja){
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
    public Muutuja getIndiviidMuutuja() {
        return this.indiviidmuutuja;
    }


    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus) {

        for(double i = 0; i < maxVaartus; i++){
            vaartustus.put(indiviidmuutuja, i);
            boolean valemiVaartus = valem.vaartusta(vaartustus, maxVaartus);
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
    public int getKvantoriteSygavus() {

        return valem.getKvantoriteSygavus() + 1;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad,  Set<Muutuja> harusEsinenudTermid) {

        List<TõesuspuuTipp> tõesuspuuTipud = new ArrayList<>();

        if(tõeväärtus){

            //kasuta olemasolevat termi (indiviidmuutuja)

            if(harusEsinenudTermid.isEmpty()){ //ühtegi termi harus ei esine; eeldame, et mingi element on alati olemas, sest põhihulk ei saa olla tühi
                Muutuja term = new Muutuja('m', Muutuja.uusMNumber());
                Valem valemiKoopia = this.valem.koopia();
                valemiKoopia.uusKonstantSumbol(term, indiviidmuutuja); //, false
                TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, true);
                tõesuspuuTipud.add(laps);
                puusEsinenudTermid.add(term);
            }
        }
        else{
            //too sisse uus konstantsümbol, mida harus ei esine
            Muutuja suvalineSumbol = tagastaSuvalineKasutamataSumbol(puusEsinenudTermid, harusEsinenudTermid);
            puusEsinenudTermid.add(suvalineSumbol);

            Valem valemiKoopia = this.valem.koopia();
            valemiKoopia.uusKonstantSumbol(suvalineSumbol, indiviidmuutuja);//, false
            TõesuspuuTipp laps = new TõesuspuuTipp(valemiKoopia, false);

            tõesuspuuTipud.add(laps);
        }

        return tõesuspuuTipud;
    }

    @Override
    public boolean onSamavaarne(Valem valem) {
        if(super.onSamavaarne(valem)){
            return true;
        }

        if(valem instanceof Iga){
            Iga igaValem = (Iga) valem;
            Muutuja uusMuutuja = new Muutuja(
                    indiviidmuutuja.getTahis(),
                    indiviidmuutuja.getPredTahis() + "*" + igaValem.getIndiviidMuutuja().getPredTahis());
            Valem koopiaValemist = this.valem.koopia();
            koopiaValemist.uusKonstantSumbol(uusMuutuja, indiviidmuutuja);
            Valem koopiaArgumentValemist = igaValem.koopia();
            koopiaArgumentValemist.uusKonstantSumbol(uusMuutuja, igaValem.indiviidmuutuja);
            return koopiaValemist.onSamavaarne(koopiaArgumentValemist);
        }

        return false;
    }


    private Muutuja tagastaSuvalineKasutamataSumbol(Set<Muutuja> puusEsinenudTermid, Set<Muutuja> harusEsinenudTermid)  {

        Muutuja uusMuutuja = new Muutuja('m', Muutuja.uusMNumber());

        return uusMuutuja;
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
        return "∀" + indiviidmuutuja +"(" + valem.dot() + ")";
    }

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) {
        if (vanaSumbol.equals(getIndiviidMuutuja()) ) {
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
                public TõesuspuuTipp kuulaKonstantSumbolit(Muutuja c) {
                    Valem koopia = valem.koopia();
                    koopia.uusKonstantSumbol(c, indiviidmuutuja);//, false
                    return new TõesuspuuTipp(koopia, true);
                }
            });
        }
        return Optional.empty();
    }
}
