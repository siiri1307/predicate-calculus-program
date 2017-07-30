package predmoodul;

import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.ParseriErind;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.AbiValem;
import predmoodul.valemid.AstNode;
import predmoodul.valemid.Vaartus;
import predmoodul.valemid.ValemiID;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
/**
 * Created by siiri on 24/03/17.
 */
public class VabadeMuutujateEsinemineAbiValemis {

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testKoikTahiseVabadMuutujadEsinevadValemis1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := ∀m∀n(m=n)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testKoikTahiseVabadMuutujadEsinevadValemis2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := ∃m∃n(m+n=x)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test
    public void testKoikTahiseVabadMuutujadEsinevadValemis3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := ∃k(k+y=x)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test
    public void testKoikValemiVabadMuutujadEsinevadTahises1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := ∃a(x*a=y)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testKoikValemiVabadMuutujadEsinevadTahises2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := ∃a(x*a=y+z)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }
    // M(x,y,z) ;= x*x + y*y = z*z
    // M(a,b,c)

    @Test
    public void testVabadeMuutujateErind1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x) := ∀m∀n∃y(x = m * n -> m = 1 ∨ n = 1 ∨ y=1) & ¬(x = 1)\n M(x)");
        assertTrue(abivalem.vabadeMuutujateEsinemineKorrektne());
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testVabadeMuutujateErind2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M(x) := ∀m(m+1=y) M(x)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test
    public void testVabadeMuutujateErind3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M := ∀x∃y(x + 1 = y) M");
        assertTrue(abivalem.vabadeMuutujateEsinemineKorrektne());
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testVabadeMuutujateErind4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        AbiValem abivalem = tagastaAbiValem("M := ∀x(x + 1 = z) M");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    private AbiValem tagastaAbiValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        ParsePuu answer = new ParsePuu(sisend);
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        return (AbiValem) (ast.getChildren().get(0));
    }

}
