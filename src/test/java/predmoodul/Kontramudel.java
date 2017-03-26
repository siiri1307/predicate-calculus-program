package predmoodul;

import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;

import java.util.HashMap;
import java.util.Map;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by siiri on 25/03/17.
 */
public class Kontramudel {

    @Test
    public void kontraMudel() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Map<Character, Double> algMudel = new HashMap<>();
        Map<Character, Double> eeldatavLoppMudel = new HashMap<>();
        eeldatavLoppMudel.put('x', 0.0);
        eeldatavLoppMudel.put('y', 1.0);
        LoppValem.tagastaValem("AxAy(x=y)").vaartusta(algMudel);
        assertEquals(eeldatavLoppMudel, algMudel);
    }

}
