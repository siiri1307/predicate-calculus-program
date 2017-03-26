package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class Implikatsioon extends Valem {

    private List<Valem> disjunktsiooniValemid;

    public Implikatsioon(List<Valem> disj){
        this.disjunktsiooniValemid = disj;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) disjunktsiooniValemid);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return  disjunktsiooniValemid.stream()
                .map(valem -> valem.vaartusta(vaartustus))
                .reduce((valem1,valem2) -> valem1 || valem2).get();
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        for(Valem valem : disjunktsiooniValemid){
            indiviidTermid.addAll(valem.getIndiviidTermid());
        }
        return indiviidTermid;
    }

    public Set<Character> getVabadMuutujad() {
        Set<Character> vabad = new HashSet<>();
        for(Valem valem : disjunktsiooniValemid){
            vabad.addAll(valem.getVabadMuutujad());
        }
        return vabad;
    }
}
