package predmoodul;

import junit.framework.TestCase;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.ParseriErind;
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
    public void test2Iga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Map<Muutuja, Double> mudel = new HashMap<>();
        assertFalse(LoppValem.tagastaValem("AxAy(x=y)").vaartusta(mudel));
    }

    @Test
    public void testLeidubIga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem("AxEy(x=y)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testVaartustamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem( "Ax((1 = 0 -> x = x + 1) ~ (x = x))").vaartusta(new HashMap<>()));
    }

    @Test
    public void test2IgaAbivalemiga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        assertFalse(LoppValem.tagastaValem("" +
                "M := AxEy(x + 1 = y) " +
                "M" +
                "").vaartusta(new HashMap<>()));
    }

    @Test
    public void testNullJaUksTerm() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y) := Ea(x * a = y)\n" +
                "T(1,0)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testTeheValemiArgumendina1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y,z) := Ea(x + y + z = a)\n" +
                "T(1,(1+1+1)*(1+1),1)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testTeheValemiArgumendina2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem("T(x,y,z) := Ea(x + y + z = a)\n" +
                "T(1,1+1+1+1+1,1)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testYksTermLiitmine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        assertFalse(LoppValem.tagastaValem("S(y) := Ex(x+x=y)\n" + "S(1)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testYksTermJagamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem("S(y) := Ex(x/x=y)\n" + "S(1)").vaartusta(new HashMap<>()));
    }

    @Test
    public void testVaartustamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        TestCase.assertTrue(LoppValem.tagastaValem("L(m) := -(m=1)\n" + "L(0)").vaartusta(new HashMap<>()));
    }

}
