package predmoodul;

import junit.framework.TestCase;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.Muutuja;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 28/03/17.
 */
public class Vaartustamine {

    @Test
    public void test2Iga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Map<Muutuja, Double> mudel = new HashMap<>();
        assertFalse(LoppValem.tagastaValem("∀x∀y(x=y)").vaartusta(mudel, 100.0));
    }

    @Test
    public void testSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Map<Muutuja, Double> mudel = new HashMap<>();
        LoppValem.tagastaValem("∃x∃y∃z∃w(x+y+z+w=x+y+z+w+1)").vaartusta(mudel, 5.0);

    }

    @Test
    public void testLeidubIga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem("∀x∃y(x=y)").vaartusta(new HashMap<>(), 100.0));

    }

    @Test
    public void testVaartustamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem( "∀x((1 = 0 -> x = x + 1) ~ (x = x))").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void test2IgaAbivalemiga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        assertFalse(LoppValem.tagastaValem("" +
                "M := ∀x∃y(x + 1 = y) " +
                "M" +
                "").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void testNullJaUksTerm() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y) := ∃a(x * a = y)\n" +
                "T(1,0)").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void testTeheValemiArgumendina1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y,z) := ∃a(x + y + z = a)\n" +
                "T(1,(1+1+1)*(1+1),1)").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void testTeheValemiArgumendina2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y,z) := ∃a(x + y + z = a)\n" +
                "T(1,1+1+1+1+1,1)").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void testYksTermLiitmine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        assertFalse(LoppValem.tagastaValem("S(y) := ∃x(x+x=y)\n" + "S(1)").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void testYksTermJagamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem("S(y) := ∃x(x/x=y)\n" + "S(1)").vaartusta(new HashMap<>(), 100.0));
    }

    @Test
    public void testVaartustamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        TestCase.assertTrue(LoppValem.tagastaValem("L(m) := ¬(m=1)\n" + "L(0)").vaartusta(new HashMap<>(), 100.0));
    }

}
