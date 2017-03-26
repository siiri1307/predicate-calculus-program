package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class Ekvivalents extends Valem {

    private List<Valem> implikatsiooniValemid;

    public Ekvivalents(List<Valem> impl){
        this.implikatsiooniValemid = impl;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object)implikatsiooniValemid);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return  implikatsiooniValemid.stream()
                .map(valem -> valem.vaartusta(vaartustus))
                .reduce((valem1,valem2) -> implikatsioon(valem1, valem2)).get();
    }

    private boolean implikatsioon(boolean valem1, boolean valem2) {
        if(valem1 && !valem2){
            return false;
        }
        return true;
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        for(Valem valem : implikatsiooniValemid){
            indiviidTermid.addAll(valem.getIndiviidTermid());
        }
        return indiviidTermid;
    }

    public Set<Character> getVabadMuutujad() {
        Set<Character> vabad = new HashSet<>();
        for(Valem valem : implikatsiooniValemid){
            vabad.addAll(valem.getVabadMuutujad());
        }
        return vabad;
    }
}
