package predmoodul.valemid;

import predmoodul.termid.IndiviidTerm;

import java.util.*;

/**
 * Created by siiri on 05/03/17.
 */
public class Disjunktsioon extends Valem{

    private List<Valem> konjuktsiooniValemid;

    public Disjunktsioon(List<Valem> konj){
        this.konjuktsiooniValemid = konj;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) konjuktsiooniValemid);
    }

    @Override
    public boolean vaartusta(Map<Character, Double> vaartustus) {

        return  konjuktsiooniValemid.stream()
                .map(valem -> valem.vaartusta(vaartustus))
                .reduce((valem1,valem2) -> valem1 && valem2).get();
    }

    @Override
    public Set<IndiviidTerm> getIndiviidTermid() {
        Set<IndiviidTerm> indiviidTermid = new HashSet<>();

        for(Valem valem : konjuktsiooniValemid){
            indiviidTermid.addAll(valem.getIndiviidTermid());
        }
        return indiviidTermid;
    }

    @Override
    public Set<Character> getVabadMuutujad() {
        Set<Character> vabad = new HashSet<>();
        for(Valem valem : konjuktsiooniValemid){
            vabad.addAll(valem.getVabadMuutujad());
        }
        return vabad;
    }
}
