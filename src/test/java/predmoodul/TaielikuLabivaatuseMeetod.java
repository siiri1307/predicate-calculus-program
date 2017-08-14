package predmoodul;

import org.junit.Test;
import predmoodul.erindid.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by siiri on 20/03/17.
 */
public class TaielikuLabivaatuseMeetod {

    @Test
    public void testVaartustamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga, IOException {
        String pakkumine = "1 = 0 -> x = x + 1";
        String vastus = "1 + 1 = x";
        Map vaartustused = new HashMap<>();
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertTrue(kontrollimine.eiOleSamavaarne());
    }

    @Test
    public void testVaartustamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga, IOException {
        String pakkumine = "1 = 0";
        String vastus = "y + 1 = y";
        Map vaartustused = new HashMap<>();
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test
    public void testVaartustamineAbiPredikaat() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga, IOException {
        String pakkumine = "" +
                "P := 1 = 0" +
                "P";
        String vastus = "y + 1 = y";
        Map vaartustused = new HashMap<>();
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test
    public void testVaartustamineAbiPredikaadid() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga, IOException {
        String pakkumine = "" +
                "P := 1 = 0" +
                "Q(x) := x = 0" +
                "Q(0) & P";
        String vastus = "y + 1 = y";
        Map vaartustused = new HashMap<>();
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }


    @Test
    public void testVaartustamineAbiPredikaadidKvantoritega() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga, IOException {
        String pakkumine = "" +
                "P := ∃x(x=1)" +
                "Q(x) := x = 0" +
                "Q(0) ∨ P";
        String vastus = "y + 1 = y";
        Map vaartustused = new HashMap<>();
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
    }
}
