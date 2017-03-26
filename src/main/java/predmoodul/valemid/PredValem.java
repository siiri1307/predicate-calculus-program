package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class PredValem extends Valem {

    private List<Valem> ekvivalentsiValemid;

    public PredValem(List<Valem> ekvivalentsid){
        this.ekvivalentsiValemid = ekvivalentsid;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(ekvivalentsiValemid);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return  ekvivalentsiValemid.stream()
                .map(valem -> valem.vaartusta(vaartustus))
                .reduce((valem1,valem2) -> ekvivalents(valem1, valem2)).get();
    }

    private boolean ekvivalents(boolean valem1, boolean valem2) {
        if((valem1 && valem2) || (!valem1 && !valem2)){
            return true;
        }
        return false;
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        for(Valem valem : ekvivalentsiValemid){
            indiviidTermid.addAll(valem.getIndiviidTermid());
        }
        return indiviidTermid;
    }

    @Override
    public Set<Character> getVabadMuutujad() {
        Set<Character> vabad = new HashSet<>();
        for(Valem valem : ekvivalentsiValemid){
            vabad.addAll(valem.getVabadMuutujad());
        }
        return vabad;
    }
}
