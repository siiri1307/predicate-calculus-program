
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

    public TõesuspuuTipp(AtomaarneValem atomaarneValem, boolean tõeväärtus) {
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

    public Set<TõesuspuuTipp> getLehed() {

        if (vasakLaps == null && paremLaps == null) {
            return new HashSet<>(Arrays.asList(this));
        }

        Set<TõesuspuuTipp> lehed = new HashSet<>();

        if (paremLaps != null) {
            lehed.addAll(paremLaps.getLehed());
        }
        if (vasakLaps != null) {
            lehed.addAll(vasakLaps.getLehed());
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
        else if(this.valem.equals(vanemTipp.valem) && !samadTõeväärtused(this, vanemTipp)){
            return true;
            //this.annabVastuolu = true;
            //System.out.println("----------------------");
            //System.out.println("Laps: " + this.toString());
            //System.out.println("Tipp, millega on vastuolus: " + vanemTipp.toString());
            //System.out.println("----------------------");
        }
        else{
            return vanemTipp.sisaldabVastuolu() || leidubVastuolu(vanemTipp.vanem);
        }
        //return annabVastuolu;
    }

    private boolean samadTõeväärtused(TõesuspuuTipp tõesuspuuTipp, TõesuspuuTipp vanemTipp) {

        return tõesuspuuTipp.tõeväärtus == vanemTipp.tõeväärtus;
    }

    public Map<String, Boolean> tagastaVaartustused(){

        Map<String, Boolean> vaartustused = new HashMap<>();

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
                vaartustused.put(valem.getId().getPredSümbol(), tõeväärtus);
            }
            vaartustused.putAll(this.vanem.tagastaVaartustused());
        }

        //if(this.vanem == null){
          //  return vaartustused;
        //}




        return vaartustused;
    }

    /*public Map<String, Boolean> tagastaVaartustused(TõesuspuuTipp vanemTipp){

        Map<String, Boolean> vaartustused = new HashMap<>();

        if(vanemTipp == null){
            if(this.getValem() instanceof AtomaarneValemPredSümboliga){
                AtomaarneValemPredSümboliga valem = (AtomaarneValemPredSümboliga) this.getValem();
                vaartustused.put(valem.getId().getPredSümbol(), tõeväärtus);
            }
            return vaartustused;
        }
        else{
            if(this.getValem() instanceof AtomaarneValemPredSümboliga){
                AtomaarneValemPredSümboliga valem = (AtomaarneValemPredSümboliga) this.getValem();
                vaartustused.put(valem.getId().getPredSümbol(), tõeväärtus);
                tagastaVaartustused(vanemTipp.vanem);
            }
            if(vanem.getValem() instanceof AtomaarneValemPredSümboliga){
                AtomaarneValemPredSümboliga valem = (AtomaarneValemPredSümboliga) vanem.getValem();
                vaartustused.put(valem.getId().getPredSümbol(), tõeväärtus);
                tagastaVaartustused(vanemTipp.vanem);
            }
        }

        return vaartustused;

    }*/

    public String dotFormaat(){

        String id = this.hashCode() + " [label=" + "\"" + this.getValem().dot() + " : " + Boolean.toString(this.tõeväärtus) + "\"" + "]; \n";

        if(this.getVanem() != null){
            return id + "\n" + this.getVanem().hashCode() + " -- " + this.hashCode() + "\n";
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

}
