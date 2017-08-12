package predmoodul;

import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.kvantorid.Eitus;
import predmoodul.kvantorid.Eks;
import predmoodul.termid.*;
import predmoodul.valemid.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
        koopia.uusKonstantSumbol(new Muutuja('a'), new Muutuja('z'));//, false
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
    public void testVabuMuutujaid2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        assertEquals(2, LoppValem.tagastaValem("∃u∃z((x=z*u) & ∃w(y+w+1=z) & ∃t(y+t+1=u))").getVabadMuutujad().size());
    }

    @Test
    public void testVabuMuutujaid0() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        assertEquals(0, LoppValem.tagastaValem("S(a,b) := ∃c(a=b+c & ¬(c=0)) ∀x∀y∃m∃n(S(m,y) & S(n,y) -> x=m*n)").getVabadMuutujad().size());
        assertEquals(0, LoppValem.tagastaValem("J(a,b) := ∃z(a=b*z & ¬(b=0)) ∀x(J(x, 1+1+1) & ¬J(x, (1+1+1)*(1+1+1)))").getVabadMuutujad().size());
    }

    @Test
    public void testVabuMuutujaid1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        assertEquals(1, LoppValem.tagastaValem("∃y(x=(1+1+1)*y)&¬∃z(x=(1+1+1)*(1+1+1)*z)").getVabadMuutujad().size());
    }

    @Test
    public void vaartustaImpl1() {

        //1=1 -> 0=1
        Implikatsioon impl = new Implikatsioon(new AtomaarneValem(new ÜksTerm(), new ÜksTerm()), new AtomaarneValem(new NullTerm(), new ÜksTerm()));
        boolean tul = impl.vaartusta(new HashMap<>(), 0);
        assertEquals(false, tul);
    }

    @Test
    public void vaartustaImpl2() {

        //1=1 -> 1=1
        Implikatsioon impl = new Implikatsioon(new AtomaarneValem(new ÜksTerm(), new ÜksTerm()), new AtomaarneValem(new ÜksTerm(), new ÜksTerm()));
        boolean tul = impl.vaartusta(new HashMap<>(), 0);
        assertEquals(true, tul);
    }

    @Test
    public void vaartustaImpl3() {

        //0=1 -> 1=1
        Implikatsioon impl = new Implikatsioon(new AtomaarneValem(new NullTerm(), new ÜksTerm()), new AtomaarneValem(new ÜksTerm(), new ÜksTerm()));
        boolean tul = impl.vaartusta(new HashMap<>(), 0);
        assertEquals(true, tul);
    }

    @Test
    public void vaartustaImpl4() {

        //0=1 -> 0=1
        Implikatsioon impl = new Implikatsioon(new AtomaarneValem(new NullTerm(), new ÜksTerm()), new AtomaarneValem(new NullTerm(), new ÜksTerm()));
        boolean tul = impl.vaartusta(new HashMap<>(), 0);
        assertEquals(true, tul);
    }

    @Test
    public void testiSeotudMuutujaAsendust() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind {

        String sisend = "P(x) := ∃n(n=1 & x=0)";
        Valem valem = LoppValem.tagastaValem(sisend);
        Set<Muutuja> seotud = valem.getSeotudMuutujad();
        Muutuja parastAsendust = new Muutuja('n', "P");
        assertTrue(seotud.contains(parastAsendust));
    }

    @Test
    public void testiMitmeJarjestikuseSeotudMuutujaAsendust() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind {

        String sisend = "P(x) := ∃n∃h(n=1 & ∃t(t=0 & h=1 & x=0))";
        Valem valem = LoppValem.tagastaValem(sisend);
        Set<Muutuja> seotudParastAsendus = new HashSet<>();
        seotudParastAsendus.add(new Muutuja('n', "P"));
        seotudParastAsendus.add(new Muutuja('t', "P"));
        seotudParastAsendus.add(new Muutuja('h', "P"));
        assertEquals(seotudParastAsendus, valem.getSeotudMuutujad());
    }

    @Test
    public void testiMitmeSamaTahisegaSeotudMuutujaAsendust() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind {

        String sisend = "P(x) := ∃n(n=1) & ∃n(n=0 & x=0)";
        Valem valem = LoppValem.tagastaValem(sisend);
        Set<Muutuja> seotudParastAsendus = new HashSet<>();
        System.out.println(valem);
        seotudParastAsendus.add(new Muutuja('n', "P"));
        //seotudParastAsendus.add(new Muutuja('t', "P"));
        assertEquals(seotudParastAsendus, valem.getSeotudMuutujad());
    }





}
