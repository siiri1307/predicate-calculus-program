package predmoodul.valemid;

import predmoodul.termid.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by siiri on 19/03/17.
 */
public class AtomaarneValemPredSümboliga extends Valem {

    private ValemiID id;
    private List<TermiPaar> termArgumendid;
    private Valem valem;
    //private String predSümbol;
    //private boolean valemiTõeväärtus;

    /*public AtomaarneValemPredSümboliga(String predSümbol, List<Term> termArgumendid, boolean tõeVäärtus) {
        this.predSümbol = predSümbol;
        this.termArgumendid = termArgumendid;
        this.valemiTõeväärtus = tõeVäärtus;
    }*/

    public AtomaarneValemPredSümboliga(ValemiID id, Valem valem){
        this.id = id;
        this.valem = valem;
        this.termArgumendid = new ArrayList<>();
    }

    public AtomaarneValemPredSümboliga(ValemiID id, List<TermiPaar> termArgumendid, Valem valem) {
        this.id = id;
        this.termArgumendid = termArgumendid;
        this.valem = valem;
    }

    public AtomaarneValemPredSümboliga(AtomaarneValemPredSümboliga avps){

        List<TermiPaar> argumendid = new ArrayList<>();

        this.id = avps.id;
        for(TermiPaar tp : avps.termArgumendid){
            argumendid.add(new TermiPaar(tp));
        }
        this.termArgumendid = argumendid;
        this.valem = avps.valem.koopia();
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(id, termArgumendid, valem);
    }

    @Override
    public boolean vaartusta(Map<Muutuja, Double> vaartustus, double maxVaartus) {
        if(termArgumendid != null){
            //System.out.println("Predsümboliga valemi termargumendid on: ");
            for(TermiPaar paar : termArgumendid){
                //System.out.println(paar.getTerm());
                if(paar.getTerm() instanceof NullTerm){
                    vaartustus.put(paar.getTahis(), 0.0);
                }
                else if(paar.getTerm() instanceof ÜksTerm){
                    vaartustus.put(paar.getTahis(), 1.0);
                }
                else if(paar.getTerm() instanceof LiitTerm){
                    LiitTerm lt = (LiitTerm) paar.getTerm();
                    vaartustus.put(paar.getTahis(), lt.vaartusta(vaartustus));
                }
                else if(paar.getTerm() instanceof LahutusTerm){
                    LahutusTerm laht = (LahutusTerm) paar.getTerm();
                    vaartustus.put(paar.getTahis(), laht.vaartusta(vaartustus));
                }
                else if(paar.getTerm() instanceof KorrutisTerm){
                    KorrutisTerm kt = (KorrutisTerm) paar.getTerm();
                    vaartustus.put(paar.getTahis(), kt.vaartusta(vaartustus));
                }
                else if(paar.getTerm() instanceof JagamisTerm){
                    JagamisTerm jt = (JagamisTerm) paar.getTerm();
                    vaartustus.put(paar.getTahis(), jt.vaartusta(vaartustus));
                }
            }
        }
        //System.out.println("Atom valem predsümboliga valem on: " + valem.toString());
        return valem.vaartusta(vaartustus, maxVaartus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        return valem.getIndiviidTermid();
    }

    @Override
    public Set<Muutuja> getVabadMuutujad() {
        return valem.getVabadMuutujad();
    }

    @Override
    public Set<Muutuja> getSeotudMuutujad() {

        return valem.getSeotudMuutujad();
    }


    @Override
    public int getKvantoriteArv() {
        return valem.getKvantoriteArv();
    }

    @Override
    public int getKvantoriteSygavus() {

        return valem.getKvantoriteSygavus();
    }

    public ValemiID getId() {
        return id;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Muutuja> puusEsinenudTermid, Set<Termikuulaja> kuulajad, Set<Muutuja> harusEsinenudTermid) {
        return valem.reegel(tõeväärtus, puusEsinenudTermid, kuulajad, harusEsinenudTermid);
    }

    @Override
    public boolean equals(Object valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        AtomaarneValemPredSümboliga atomaarnePredValem = (AtomaarneValemPredSümboliga) valem;

        if(!this.id.equals(atomaarnePredValem.id)){
            return false;
        }

        return this.valem.equals(atomaarnePredValem.valem);
    }

    @Override
    public String dot() {
        return "[" + id.dot() + "(" + String.join(",", termArgumendid.stream().map(x -> x.getTahis().toString()).collect(Collectors.toList())) +  ") := " + valem.dot() + "]";
    }

    /*@Override
    public String dot() {
        return id.dot();
    }*/

    @Override
    public void uusKonstantSumbol(Muutuja uusSumbol, Muutuja vanaSumbol) { //, boolean vahetaKvantoriSees

        for(TermiPaar tp : termArgumendid){
            //if(tp.getTahis().getTahis() == 'x'){
              //  tp.getTahis().setTahis(uusSumbol);;
            //}
        }

        valem.uusKonstantSumbol(uusSumbol, vanaSumbol); //, vahetaKvantoriSees
    }

    @Override
    public void asendaTerm(Term uus, Predicate<Term> tingimus) {

        valem.asendaTerm(uus, tingimus);
    }

    @Override
    public Valem koopia() {
        return new AtomaarneValemPredSümboliga(this);
    }
}
