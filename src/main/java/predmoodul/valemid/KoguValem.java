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
}
