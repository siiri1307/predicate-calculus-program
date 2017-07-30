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
import predmoodul.valemid.Muutuja;
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

        IndiviidTerm term1 = new IndiviidTerm(new Muutuja('a'));
        IndiviidTerm term2 = new IndiviidTerm(new Muutuja('b'));
        assertNotEquals(term1, term2);
    }

    @Test
    public void LahutusTermOnVordne() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        LahutusTerm term1 = new LahutusTerm(new IndiviidTerm(new Muutuja('a')), new ÜksTerm());
        LahutusTerm term2 = new LahutusTerm(new IndiviidTerm(new Muutuja('a')), new ÜksTerm());
        assertEquals(term1, term2);
    }

    @Test
    public void LahutusTermEiOleVordne1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        LahutusTerm term1 = new LahutusTerm(new IndiviidTerm(new Muutuja('a')), new ÜksTerm());
        LahutusTerm term2 = new LahutusTerm(new IndiviidTerm(new Muutuja('b')), new ÜksTerm());
        assertNotEquals(term1, term2);
    }

    @Test
    public void LahutusTermEiOleVordne2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        LahutusTerm term1 = new LahutusTerm(new IndiviidTerm(new Muutuja('a')), new ÜksTerm());
        LahutusTerm term2 = new LahutusTerm(new IndiviidTerm(new Muutuja('a')), new NullTerm());
        assertNotEquals(term1, term2);
    }

    @Test
    public void konstantsSymboliAsendamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        Valem leidub = new Eks(vordub(new Muutuja('z'), new Muutuja('x')), new Muutuja('z')); //Ez(z=x)
        Valem koopia = leidub.koopia();
        koopia.uusKonstantSumbol(new Muutuja('a'), new Muutuja('x')); //Ez(z=a)
        assertEquals(new Eks(vordub(new Muutuja('z',0), new Muutuja('a',0)), new Muutuja('z',0)), koopia);

    }

    @Test
    public void konstantSymboliAsendamineSamaMuutuja() {
        //Ez(z=z)&z=z
        Valem konjuktsioon = new Konjuktsioon(new Eks(vordub(new Muutuja('z'), new Muutuja('z')), new Muutuja('z')), vordub(new Muutuja('z'), new Muutuja('z')));
        Valem koopia = konjuktsioon.koopia();
        koopia.uusKonstantSumbol(new Muutuja('a'), new Muutuja('z'));
        assertEquals(new Konjuktsioon(new Eks(vordub(new Muutuja('z'), new Muutuja('z')), new Muutuja('z')), vordub(new Muutuja('a'), new Muutuja('a'))), koopia);

    }

    private static AtomaarneValem vordub(Muutuja esimene, Muutuja teine) {
        return new AtomaarneValem(new IndiviidTerm(esimene), new IndiviidTerm(teine));
    }

    @Test
    public void testiVabadMuutujadOlemasoluValemis(){

        //Ey(x=(1+1+1)*y)
        AtomaarneValem atomaarneValem = new AtomaarneValem(new IndiviidTerm(new Muutuja('x')), new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm(new Muutuja('y'))));
        Eks leidub = new Eks(atomaarneValem, new Muutuja('y'));
        Set<Muutuja> vabad = leidub.getVabadMuutujad();
        Set<Muutuja> oodatav = new HashSet<>();
        oodatav.add(new Muutuja('x',0));
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testiVabadMuutujadEituseValemis(){

        // -(y=0)
        AtomaarneValem atomValem = new AtomaarneValem(new IndiviidTerm(new Muutuja('y')), new NullTerm());
        Eitus eitus = new Eitus(atomValem);
        Set<Muutuja> vabad = eitus.getVabadMuutujad();
        Set<Muutuja> oodatav = new HashSet<>();
        oodatav.add(new Muutuja('y',0));
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testiVabadMuutujadEitusLeidubValemis(){

        //-Ez(x=(1+1+1)*(1+1+1)*z)
        Term paremTermKorrutises = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm(new Muutuja('z')));
        Term korrutisTerm = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), paremTermKorrutises);
        AtomaarneValem atomValem = new AtomaarneValem(new IndiviidTerm(new Muutuja('x')), korrutisTerm);
        Eks leidubValem = new Eks(atomValem, new Muutuja('z'));
        Eitus eitus = new Eitus(leidubValem);

        Set<Muutuja> vabad = eitus.getVabadMuutujad();
        Set<Muutuja> oodatav = new HashSet<>();
        oodatav.add(new Muutuja('x',0));
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testiVabadMuutujadKonjuktsioon(){

        //Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)

        //Ey(x=(1+1+1)*y)
        AtomaarneValem atomaarneValem = new AtomaarneValem(new IndiviidTerm(new Muutuja('x')), new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm(new Muutuja('y'))));
        Eks leidub = new Eks(atomaarneValem, new Muutuja('y'));

        AtomaarneValem atomValem = new AtomaarneValem(new IndiviidTerm(new Muutuja('y')), new NullTerm());
        Eitus eitus = new Eitus(atomValem);

        Term paremTermKorrutises = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), new IndiviidTerm(new Muutuja('z')));
        Term korrutisTerm = new KorrutisTerm(new LiitTerm(new ÜksTerm(), new LiitTerm(new ÜksTerm(), new ÜksTerm())), paremTermKorrutises);
        AtomaarneValem atomValem2 = new AtomaarneValem(new IndiviidTerm(new Muutuja('x')), korrutisTerm);
        Eks leidubValem = new Eks(atomValem2, new Muutuja('z'));
        Eitus eitus2 = new Eitus(leidubValem);

        Konjuktsioon konjuParempool = new Konjuktsioon(eitus, eitus2);

        Konjuktsioon konj = new Konjuktsioon(leidub, konjuParempool);

        Set<Muutuja> vabad = konj.getVabadMuutujad();
        Set<Muutuja> oodatav = new HashSet<>();
        oodatav.add(new Muutuja('x'));
        oodatav.add(new Muutuja('y'));
        assertEquals(oodatav, vabad);
    }

    @Test
    public void testVabuMuutujaid2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        assertEquals(2, LoppValem.tagastaValem("∃u∃z((x=z*u) & ∃w(y+w+1=z) & ∃t(y+t+1=u))").getVabadMuutujad().size());
    }

    @Test
    public void testVabuMuutujaid0() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        assertEquals(0, LoppValem.tagastaValem("S(a,b) := ∃c(a=b+c & ¬(c=0)) ∀x∀y∃m∃n(S(m,y) & S(n,y) -> x=m*n)").getVabadMuutujad().size());
        assertEquals(0, LoppValem.tagastaValem("J(a,b) := ∃z(a=b*z & ¬(b=0)) ∀x(J(x, 1+1+1) & ¬J(x, (1+1+1)*(1+1+1)))").getVabadMuutujad().size());
    }

    @Test
    public void testVabuMuutujaid1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        assertEquals(1, LoppValem.tagastaValem("∃y(x=(1+1+1)*y)&¬∃z(x=(1+1+1)*(1+1+1)*z)").getVabadMuutujad().size());
    }





}
