package predmoodul;

import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by siiri on 20/03/17.
 */
public class TaielikuLabivaatuseMeetod {

    @Test
    public void testVaartustamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        String pakkumine = "1 = 0 -> x = x + 1";
        String vastus = "1 + 1 = x";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertTrue(kontrollimine.eiOleSamavaarne());
    }

    @Test
    public void testVaartustamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        String pakkumine = "1 = 0";
        String vastus = "y + 1 = y";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

    @Test
    public void testVaartustamineAbiPredikaat() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        String pakkumine = "" +
                "P := 1 = 0" +
                "P";
        String vastus = "y + 1 = y";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

    @Test
    public void testVaartustamineAbiPredikaadid() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        String pakkumine = "" +
                "P := 1 = 0" +
                "Q(x) := x = 0" +
                "Q(0) & P";
        String vastus = "y + 1 = y";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }


    @Test
    public void testVaartustamineAbiPredikaadidKvantoritega() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        String pakkumine = "" +
                "P := Ex(x=1)" +
                "Q(x) := x = 0" +
                "Q(0) v P";
        String vastus = "y + 1 = y";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertTrue(kontrollimine.eiOleSamavaarne());
    }
}
