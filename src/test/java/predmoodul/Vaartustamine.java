package predmoodul;

import junit.framework.TestCase;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 28/03/17.
 */
public class Vaartustamine {

    @Test
    public void test2Iga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Map<Character, Double> mudel = new HashMap<>();
        assertFalse(LoppValem.tagastaValem("AxAy(x=y)").vaartusta(mudel));
    }

    @Test
    public void testLeidubIga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("AxEy(x=y)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testVaartustamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem( "Ax((1 = 0 -> x = x + 1) ~ (x = x))").vaartusta(new HashMap<>()));
    }

    @Test
    public void test2IgaAbivalemiga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        assertFalse(LoppValem.tagastaValem("M := AxEy(x + 1 = y) M").vaartusta(new HashMap<>()));
    }

    @Test
    public void testNullJaUksTerm() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y) := Ea(x * a = y)\n" +
                "T(1,0)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testYksTermLiitmine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        assertFalse(LoppValem.tagastaValem("S(y) := Ex(x+x=y)\n" + "S(1)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testYksTermJagamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("S(y) := Ex(x/x=y)\n" + "S(1)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testVaartustamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("L(m) := -(m=1)\n" + "L(0)").vaartusta(new HashMap<>()));
    }

}
