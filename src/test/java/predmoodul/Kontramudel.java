package predmoodul;

import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.Muutuja;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by siiri on 25/03/17.
 */
public class Kontramudel {

    @Test
    public void testKontraMudel() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Map<Muutuja, Double> algMudel = new HashMap<>();
        Map<Muutuja, Double> eeldatavLoppMudel = new HashMap<>();
        eeldatavLoppMudel.put(new Muutuja('x'), 0.0);
        eeldatavLoppMudel.put(new Muutuja('y'), 1.0);
        LoppValem.tagastaValem("AxAy(x=y)").vaartusta(algMudel, 100.0);
        assertEquals(eeldatavLoppMudel, algMudel);
    }

    @Test
    public void testKontraMudelYl2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, ParseriErind {

        String vastus = "EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u))";
        String pakkumine = "EaEb(x=a*b & EkEl(a=y+k & b=y+l))";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
        Map<Character, Integer> eeldatav = new HashMap<>();
        eeldatav.put('x', 0);
        eeldatav.put('y', 0);
        assertEquals(eeldatav, kontroll.tagastaKontramudel());
    }

}
