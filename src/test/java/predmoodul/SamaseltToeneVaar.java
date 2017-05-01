package predmoodul;

import predmoodul.termid.NullTerm;
import predmoodul.valemid.*;

/**
 * Created by siiri on 17/04/17.
 */
public class SamaseltToeneVaar {

    public void testVastuoluVanemaga(){
        Valem valem = new AtomaarneValemPredSümboliga(new ValemiID("P", 0), new AtomaarneValem(new NullTerm(), new NullTerm()));
        TõesuspuuTipp eitusVanem = new TõesuspuuTipp(valem, true);
        TõesuspuuTipp eitusLaps = new TõesuspuuTipp(valem, false);

        //Tõesuspuu puu = new Tõesuspuu(eitusVanem);
    }
}
