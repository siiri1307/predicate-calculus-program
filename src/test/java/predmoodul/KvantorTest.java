package predmoodul;

import junit.framework.TestCase;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 20/03/17.
 */
public class KvantorTest {

    @Test
    public void test2Iga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Map<Character, Double> mudel = new HashMap<>();
        assertFalse(LoppValem.tagastaValem("AxAy(x=y)").vaartusta(mudel));
        System.out.println(mudel);
    }

    @Test
    public void test2IgaOige() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("AxAy(x=x)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testLeidubIga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("AxEy(x=y)").vaartusta(new HashMap<>()));
    }

    @Test
    public void test2LeidubAbi() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("P(x):=x\nAxEy(P(x) & y)").vaartusta(new HashMap<>()));
    }

    @Test
    public void test2IgaAbivalemiga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        assertFalse(LoppValem.tagastaValem("M(x) := AmAn(m=n)\n"+ //x ja y pole vabad muutujad. Ei peaks acceptima
                "M(x)").vaartusta(new HashMap<>()));
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
    public void testIgaEksAbivalemiga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        TestCase.assertTrue(LoppValem.tagastaValem("K(x,y) := AxEy(x=y)\n"+
                "K(x,y)").vaartusta(new HashMap<>()));
    }

}
