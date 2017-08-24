
package predmoodul.valemid;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by siiri on 13/04/17.
 */
public class TõesuspuuTipp {

    private final Valem valem;
    private final boolean tõeväärtus;
    private boolean analüüsitud;
    private Optional<Boolean> annabVastuolu; //true, false, empty

    private TõesuspuuTipp vasakLaps;
    private TõesuspuuTipp paremLaps;
    private TõesuspuuTipp vanem;


    public TõesuspuuTipp(Valem valem, boolean väärtus) {
        this.valem = valem;
        this.tõeväärtus = väärtus;
        this.annabVastuolu = Optional.empty(); //algväärtustatakse tühjaks, et tähistada, et tõeväärtust ei ole veel välja arvutatud
    }


    public TõesuspuuTipp(TõesuspuuTipp tipp){ //copy constructor
        this.valem = tipp.valem;
        this.tõeväärtus = tipp.tõeväärtus;
        this.annabVastuolu = Optional.empty();
        if(tipp.vasakLaps != null){
            this.setVasakLaps(new TõesuspuuTipp(tipp.vasakLaps));
        }
        if(tipp.paremLaps != null){
            this.setParemLaps(new TõesuspuuTipp(tipp.paremLaps));
        }

    }

    public TõesuspuuTipp(AtomaarneValem atomaarneValem, boolean tõeväärtus, Set<Termikuulaja> kuulajad) {
        this.valem = atomaarneValem;
        this.tõeväärtus = tõeväärtus;
        this.annabVastuolu = Optional.empty();
        this.analüüsitud = true;

    }

    public Valem getValem() {
        return valem;
    }

    public boolean getTõeväärtus() {
        return tõeväärtus;
    }

    public TõesuspuuTipp getVasakLaps() {
        return vasakLaps;
    }

    public TõesuspuuTipp getParemLaps() {
        return paremLaps;
    }

    public void setVasakLaps(TõesuspuuTipp vasakLaps) {
        this.vasakLaps = vasakLaps;
        vasakLaps.setVanem(this);
    }

    public void setParemLaps(TõesuspuuTipp paremLaps) {
        this.paremLaps = paremLaps;
        paremLaps.setVanem(this);
    }

    private void setVanem(TõesuspuuTipp vanem) {
        this.vanem = vanem;
    }

    public boolean sisaldabVastuolu() { //public meetod
        if (!annabVastuolu.isPresent()) { //kui see väli on tühi, siis arvuta see välja
            annabVastuolu = Optional.of(leidubVastuolu());
        }
        return annabVastuolu.get();
    }

    public TõesuspuuTipp getVanem(){

        return this.vanem;
    }

    public void lisaLapsed(List<TõesuspuuTipp> lapsed) {

        if (lapsed.size() > 1) {
            setVasakLaps(lapsed.get(0));
            setParemLaps(lapsed.get(1));
        }
        else if(lapsed.size() == 1){
            setVasakLaps(lapsed.get(0));
        }
    }

    public Collection<TõesuspuuTipp> getLehed() {

        if (vasakLaps == null && paremLaps == null) {
            return Arrays.asList(this);
        }

        List<TõesuspuuTipp> lehed = new ArrayList<>();

        if (vasakLaps != null) {
            lehed.addAll(vasakLaps.getLehed());
        }
        if (paremLaps != null) {
            lehed.addAll(paremLaps.getLehed());
        }
        return lehed;
    }

    public List<TõesuspuuTipp> getLapsed() {
        return Arrays.asList(vasakLaps,paremLaps).stream().filter(x -> x != null).collect(Collectors.toList());
    }

    public boolean onAnalüüsitud() {
        return analüüsitud;
    }

    public void setAnalüüsitud(boolean analüüsitud) {
        this.analüüsitud = analüüsitud;
    }

    private boolean leidubVastuolu(){
        return leidubVastuolu(getVanem());
    }

    private boolean leidubVastuolu(TõesuspuuTipp vanemTipp){ //kas vaadeldav tipp on vastuolus mõne oma ülemise vanemaga, või
        //mõni ülemine vanem on vastuolus oma mõne ülemise vanemaga

        if(vanemTipp == null) {
            return false;
        }
        /*else if(this.valem.equals(vanemTipp.valem) && !samadTõeväärtused(this, vanemTipp)){

            return true;
        }*/
        else if(this.valem.onSamavaarne(vanemTipp.valem) && !samadTõeväärtused(this, vanemTipp)){

            return true;
        }
        else{
            return vanemTipp.sisaldabVastuolu() || leidubVastuolu(vanemTipp.vanem);
        }

    }

    private boolean samadTõeväärtused(TõesuspuuTipp tõesuspuuTipp, TõesuspuuTipp vanemTipp) {

        return tõesuspuuTipp.tõeväärtus == vanemTipp.tõeväärtus;
    }

    public Map<String, Boolean> tagastaVaartustused(){

        Map<String, Boolean> vaartustused = new HashMap<>();

        System.out.println("Leht mis tagastab tühja mapi: " + this.toString());

        /*if(this.vanem == null) {
            if (this.getValem() instanceof AtomaarneValemPredSümboliga) {
                AtomaarneValemPredSümboliga valem = (AtomaarneValemPredSümboliga) this.getValem();
                vaartustused.put(valem.getId().getPredSümbol(), tõeväärtus);
            }
            return vaartustused;
        }*/

        if(!(this.vanem == null || this == null)){
            if (this.getValem() instanceof AtomaarneValemPredSümboliga) {
                AtomaarneValemPredSümboliga valem = (AtomaarneValemPredSümboliga) this.getValem();
                vaartustused.put(valem.getId().getPredSümbol() + "(" + valem.getVabadMuutujad() + ")", tõeväärtus);
            }
            /*else{
                vaartustused.put(this.getValem().dot(), tõeväärtus);
            }*/
            vaartustused.putAll(this.vanem.tagastaVaartustused());
        }

        //if(this.vanem == null){
          //  return vaartustused;
        //}


        return vaartustused;
    }

    public String dotFormaat(String number){

        String id = System.identityHashCode(this) + " [label=" + "\"" + this.getValem().dot() + " : " +
                Boolean.toString(this.tõeväärtus) +
                "\" color=\"" + (this.leidubVastuolu() ? "red" : "black") + "\""  + "]; \n";

        /*String id = System.identityHashCode(this) + " [label=" + "\"" + this.getValem().dot() + " : " +
                Boolean.toString(this.tõeväärtus) +
                " NR: " + number + "" +
                "\" color=\"" + (this.leidubVastuolu() ? "red" : "black") + "\""  + "]; \n";*/

        if(this.getVanem() != null){
            return id + "\n" + System.identityHashCode(this.getVanem()) + " -- " + System.identityHashCode(this) + "\n";
        }

        return id + "\n";

    }

    @Override
    public String toString() {
        return "TõesuspuuTipp{" +
                "valem=" + valem +
                ", tõeväärtus=" + tõeväärtus +
                ", annabVastuolu=" + annabVastuolu +
                '}';
    }

    public Set<Termikuulaja> getKuulajad(){
        if(vanem == null){
            Set<Termikuulaja> set = new HashSet<>();
            Optional<Termikuulaja> k = valem.getKuulaja(tõeväärtus); //igal valemil on see meetod, mis üldjuhul tagastab Optional.empty. See override'itakse Iga ja Eks puhul
            if(k.isPresent()){
                set.add(k.get());
            }
            return set;
        }

        Set<Termikuulaja> kuulajad = vanem.getKuulajad();

        Optional<Termikuulaja> kuulaja = valem.getKuulaja(tõeväärtus);

        if(kuulaja.isPresent()){
            kuulajad.add(kuulaja.get());
        }

        return kuulajad;
    }

    public Set<Muutuja> getTermid() {
        if(vanem == null){
            return valem.getVabadMuutujad();
            //return valem.getIndiviidTermid().stream().map(x -> x.getTahis()).collect(Collectors.toSet());
        }

        Set<Muutuja> termid = vanem.getTermid();
        //termid.addAll(valem.getIndiviidTermid().stream().map(x -> x.getTahis()).collect(Collectors.toSet()));
        termid.addAll(valem.getVabadMuutujad());

        return termid;
    }

    public Set<Valem> lisaVaaraksMuutvadVaartustused() {

        if(vanem == null){
            return new HashSet<>();
        }

        Set<Valem> vaaraksMuutvadVaartustused = vanem.lisaVaaraksMuutvadVaartustused();


        if(valem instanceof AtomaarneValemPredSümboliga){
            vaaraksMuutvadVaartustused.add(this.valem);
        }
        else if(valem instanceof AtomaarneValem){
            vaaraksMuutvadVaartustused.add(this.valem);
        }

        return vaaraksMuutvadVaartustused;

    }

    public boolean sama(TõesuspuuTipp uusLeht) {
        return uusLeht.valem.equals(valem) && uusLeht.getTõeväärtus() == tõeväärtus;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TõesuspuuTipp){
            return sama((TõesuspuuTipp) obj);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return valem.hashCode();
    }



}
