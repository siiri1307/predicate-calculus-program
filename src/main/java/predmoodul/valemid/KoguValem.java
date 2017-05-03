package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class KoguValem extends Valem {

    List<Valem> children;

    public KoguValem(List<Valem> children){
        this.children = children;
    }

    public KoguValem(KoguValem kv){

        List<Valem> lapsed = new ArrayList<>();

        for(Valem valem : kv.children){
            lapsed.add(valem.koopia());
        }
        this.children = lapsed;
    }

    @Override
    public List<Object> getChildren() {
        return new ArrayList<Object>(children);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {
        return children.get(children.size()-1).vaartusta(vaartustus);
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {

        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        for(Valem valem : children){
            indiviidTermid.addAll(valem.getIndiviidTermid());
        }
        return indiviidTermid;

    }

    @Override
    public Set<Character> getVabadMuutujad() {
        Set<Character> vabad = new HashSet<>();
        for(Valem valem : children){
            vabad.addAll(valem.getVabadMuutujad());
        }
        return vabad;
    }

    @Override
    public List<TõesuspuuTipp> reegel(boolean tõeväärtus, Set<Character> puusEsinenudTermid, Set<Termikuulaja> kuulajad) {

        return children.get(children.size()-1).reegel(tõeväärtus, puusEsinenudTermid, kuulajad);
    }

    @Override
    public boolean equals(Valem valem) {

        if(this == valem){
            return true;
        }
        if(valem == null || this.getClass() != valem.getClass()){
            return false;
        }

        KoguValem koguValem = (KoguValem) valem;

        for(int i = 0; i < children.size(); i++){
            //this.children.get(i).equals(koguValem.children.get(i));
        }

        return false;
    }

    @Override
    public String dot() {
        return children.get(children.size()-1).dot();
    }

    @Override
    public void uusKonstantSumbol(Character sumbol) {
        children.get(children.size()-1).uusKonstantSumbol(sumbol);
    }

    @Override
    public Valem koopia() {
        return new KoguValem(this);
    }
}
