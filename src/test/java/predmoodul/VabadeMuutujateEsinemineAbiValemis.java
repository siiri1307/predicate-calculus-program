package predmoodul;

import predmoodul.erindid.AbiValemEiOleDefineeritud;
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
    public void testKoikTahiseVabadMuutujadEsinevadValemis1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := AmAn(m=n)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testKoikTahiseVabadMuutujadEsinevadValemis2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := EmEn(m+n=x)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test
    public void testKoikTahiseVabadMuutujadEsinevadValemis3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := Ek(k+y=x)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test
    public void testKoikValemiVabadMuutujadEsinevadTahises1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := Ea(x*a=y)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testKoikValemiVabadMuutujadEsinevadTahises2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x,y) := Ea(x*a=y+z)\n M(x,y)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }
    // M(x,y,z) ;= x*x + y*y = z*z
    // M(a,b,c)

    @Test
    public void testVabadeMuutujateErind1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x) := AmAnEy(x = m * n -> m = 1 v n = 1 v y=1) & -(x = 1)\n M(x)");
        assertTrue(abivalem.vabadeMuutujateEsinemineKorrektne());
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testVabadeMuutujateErind2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M(x) := Am(m+1=y) M(x)");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    @Test
    public void testVabadeMuutujateErind3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M := AxEy(x + 1 = y) M");
        assertTrue(abivalem.vabadeMuutujateEsinemineKorrektne());
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void testVabadeMuutujateErind4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        AbiValem abivalem = tagastaAbiValem("M := Ax(x + 1 = z) M");
        abivalem.vabadeMuutujateEsinemineKorrektne();
    }

    private AbiValem tagastaAbiValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Vastus answer = new Vastus(sisend);
        ParseTree pt = answer.createParseTree(sisend);
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid, new HashMap<>());
        return (AbiValem) (ast.getChildren().get(0));
    }

}
