package predmoodul;

import predmoodul.kvantorid.Iga;
import predmoodul.kvantorid.Kvantor;
import predmoodul.valemid.Ekvivalents;
import predmoodul.valemid.Konjuktsioon;
import predmoodul.valemid.Valem;

import java.util.*;

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
        List<Kvantor> kvantorid = new ArrayList<>();
        for(Character muutuja : vabadMuutujad){
            kvantorid.add(new Iga(muutuja));
        }
        return new Konjuktsioon(kvantorid, ekvivalentsiValem, false);
    }

    private Valem moodustaEkvivalentsiValem(Valem pakkumine, Valem vastus) {
        return new Ekvivalents(Arrays.asList(pakkumine, vastus));
    }
}
