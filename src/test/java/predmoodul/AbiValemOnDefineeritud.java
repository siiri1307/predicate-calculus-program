package predmoodul;

import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by siiri on 25/03/17.
 */
public class AbiValemOnDefineeritud {

    @Test(expected=AbiValemEiOleDefineeritud.class)
    public void testPuuduvAbivalemiDefinitsioon() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        LoppValem.tagastaValem("T(x,y) := ∃a(x * a = y)\n M(x,y)");
    }

    @Test
    public void testAbiValemDefineeritud() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        assertTrue(LoppValem.tagastaValem("T(x,y) := ∃a(x * a = y)\n T(1,0)").vaartusta(new HashMap<>(), 100.0));
    }

}
