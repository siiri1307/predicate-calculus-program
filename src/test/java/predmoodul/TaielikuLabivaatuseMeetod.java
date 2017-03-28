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
        String vastus = "x + 1 = x";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

}
