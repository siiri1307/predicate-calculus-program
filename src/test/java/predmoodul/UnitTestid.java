package predmoodul;

import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.ParseriErind;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.kvantorid.Eitus;
import predmoodul.kvantorid.Eks;
import predmoodul.termid.*;
import predmoodul.valemid.AtomaarneValem;
import predmoodul.valemid.Konjuktsioon;
import predmoodul.valemid.Valem;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by siiri on 15/05/17.
 */
public class UnitTestid {

    @Test
    public void UksTermOnVordne() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        ÜksTerm term1 = new ÜksTerm();
        ÜksTerm term2 = new ÜksTerm();
        assertEquals(term1, term2);
    }

    @Test
    public void NullTermOnVordne() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        NullTerm term1 = new NullTerm();
        NullTerm term2 = new NullTerm();
        assertEquals(term1, term2);
    }

    @Test
    public void IndiviidTermEiOleVordne() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        IndiviidTerm term1 = new IndiviidTerm('a');
        IndiviidTerm term2 = new IndiviidTerm('b');
        assertNotEquals(term1, term2);
    }

    @Test
    public void LahutusTermOnVordne() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        LahutusTerm term1 = new LahutusTerm(new IndiviidTerm('a'), new ÜksTerm());
        LahutusTerm term2 = new LahutusTerm(new IndiviidTerm('a'), new ÜksTerm());
        assertEquals(term1, term2);
    }

    @Test
    public void LahutusTermEiOleVordne1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        LahutusTerm term1 = new LahutusTerm(new IndiviidTerm('a'), new ÜksTerm());
        LahutusTerm term2 = new LahutusTerm(new IndiviidTerm('b'), new ÜksTerm());
        assertNotEquals(term1, term2);
    }

    @Test
    public void LahutusTermEiOleVordne2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        LahutusTerm term1 = new LahutusTerm(new IndiviidTerm('a'), new ÜksTerm());
        LahutusTerm term2 = new LahutusTerm(new IndiviidTerm('a'), new NullTerm());
        assertNotEquals(term1, term2);
    }

    @Test
    public void konstantsSymboliAsendamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        Valem leidub = new Eks(vordub('z', 'x'), 'z');
        Valem koopia = leidub.koopia();
        koopia.uusKonstantSumbol('a', 'x');
        assertEquals(new Eks(vordub('z', 'a'),'z'), koopia);

    }

    @Test
    public void konstantSymboliAsendamineSamaMuutuja() {
        Valem konjuktsioon = new Konjuktsioon(new Eks(vordub('z', 'z'), 'z'), vordub('z', 'z'));
        Valem koopia = konjuktsioon.koopia();
        koopia.uusKonstantSumbol('a','z');
        assertEquals(new Konjuktsioon(new Eks(vordub('z','z'),'z'), vordub('a','a')), koopia);

    }

    private static AtomaarneValem vordub(char esimene, char teine) {
        return new AtomaarneValem(new IndiviidTerm(esimene), new IndiviidTerm(teine));
    }

    @Test
    public void testiVabadMuutujadOlemasoluValemis(){

        //Ey(x=(1+1+1)*y)
        AtomaarneValem atomaarneValem = new AtomaarneValem(new IndiviidTerm('x'), new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm('y')));
        Eks leidub = new Eks(atomaarneValem, 'y');
        Set<Character> vabad = leidub.getVabadMuutujad();
        Set<Character> oodatav = new HashSet<>();
        oodatav.add('x');
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testiVabadMuutujadEituseValemis(){

        // -(y=0)
        AtomaarneValem atomValem = new AtomaarneValem(new IndiviidTerm('y'), new NullTerm());
        Eitus eitus = new Eitus(atomValem);
        Set<Character> vabad = eitus.getVabadMuutujad();
        Set<Character> oodatav = new HashSet<>();
        oodatav.add('y');
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testiVabadMuutujadEitusLeidubValemis(){

        //-Ez(x=(1+1+1)*(1+1+1)*z)
        Term paremTermKorrutises = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm('z'));
        Term korrutisTerm = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), paremTermKorrutises);
        AtomaarneValem atomValem = new AtomaarneValem(new IndiviidTerm('x'), korrutisTerm);
        Eks leidubValem = new Eks(atomValem, 'z');
        Eitus eitus = new Eitus(leidubValem);

        Set<Character> vabad = eitus.getVabadMuutujad();
        Set<Character> oodatav = new HashSet<>();
        oodatav.add('x');
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testiVabadMuutujadKonjuktsioon(){

        //Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)

        //Ey(x=(1+1+1)*y)
        AtomaarneValem atomaarneValem = new AtomaarneValem(new IndiviidTerm('x'), new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm('y')));
        Eks leidub = new Eks(atomaarneValem, 'y');

        AtomaarneValem atomValem = new AtomaarneValem(new IndiviidTerm('y'), new NullTerm());
        Eitus eitus = new Eitus(atomValem);

        Term paremTermKorrutises = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm('z'));
        Term korrutisTerm = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), paremTermKorrutises);
        AtomaarneValem atomValem2 = new AtomaarneValem(new IndiviidTerm('x'), korrutisTerm);
        Eks leidubValem = new Eks(atomValem2, 'z');
        Eitus eitus2 = new Eitus(leidubValem);

        Konjuktsioon konjuParempool = new Konjuktsioon(eitus, eitus2);

        Konjuktsioon konj = new Konjuktsioon(leidub, konjuParempool);

        Set<Character> vabad = konj.getVabadMuutujad();
        Set<Character> oodatav = new HashSet<>();
        oodatav.add('x');
        oodatav.add('y');
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testVabuMuutujaid2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        assertEquals(2, LoppValem.tagastaValem("EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u))").getVabadMuutujad().size());
    }

    @Test
    public void testVabuMuutujaid0() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        assertEquals(0, LoppValem.tagastaValem("S(a,b) := Ec(a=b+c & -(c=0)) AxAyEmEn(S(m,y) & S(n,y) -> x=m*n)").getVabadMuutujad().size());
        assertEquals(0, LoppValem.tagastaValem("J(a,b) := Ez(a=b*z & -(b=0)) Ax(J(x, 1+1+1) & -J(x, (1+1+1)*(1+1+1)))").getVabadMuutujad().size());
    }

    @Test
    public void testVabuMuutujaid1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        assertEquals(1, LoppValem.tagastaValem("Ey(x=(1+1+1)*y)&-Ez(x=(1+1+1)*(1+1+1)*z)").getVabadMuutujad().size());
    }





}
