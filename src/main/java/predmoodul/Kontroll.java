package predmoodul;

import predmoodul.kvantorid.Iga;
import predmoodul.valemid.Ekvivalents;
import predmoodul.valemid.Valem;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by siiri on 27/03/17.
 */
public class Kontroll {

    private Valem pakkumine;
    private Valem vastus;

    public Kontroll(Valem pakkumine, Valem vastus) {
        this.pakkumine = pakkumine;
        this.vastus = vastus;
    }

    public boolean eiOleSamavaarne(){
        Valem ekvivalentsiValem = moodustaEkvivalentsiValem(pakkumine, vastus);
        Valem valemVabadeMuutujateta = seoVabadMuutujad(ekvivalentsiValem);
        return !valemVabadeMuutujateta.vaartusta(new HashMap<>());
    }

    private Valem seoVabadMuutujad(Valem ekvivalentsiValem) {

        Set<Character> vabadMuutujad = ekvivalentsiValem.getVabadMuutujad();

        return helper(vabadMuutujad, ekvivalentsiValem);
    }

    private Valem helper(Set<Character> vabadMuutujad, Valem ekvivalentsiValem) {

        if(vabadMuutujad.isEmpty()){
            return ekvivalentsiValem;
        }
        else{
            Character vabaMuutuja = vabadMuutujad.iterator().next();
            vabadMuutujad.remove(vabaMuutuja);
            return new Iga(helper(vabadMuutujad, ekvivalentsiValem), vabaMuutuja);
        }

    }

    private Valem moodustaEkvivalentsiValem(Valem pakkumine, Valem vastus) {
        return new Ekvivalents(pakkumine, vastus);
    }
}
