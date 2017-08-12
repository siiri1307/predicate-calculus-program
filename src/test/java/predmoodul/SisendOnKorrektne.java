package predmoodul;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import predmoodul.erindid.*;
import predmoodul.kvantorid.Eitus;
import predmoodul.kvantorid.Eks;
import predmoodul.termid.IndiviidTerm;
import predmoodul.termid.LiitTerm;
import predmoodul.termid.NullTerm;
import predmoodul.valemid.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 24/06/17.
 */
public class SisendOnKorrektne {

    @Test
    public void testSignatuuriMittekuuluvSymbol1() {
        ParsePuu sisend = new ParsePuu("∃x∃k(x=3*k & ¬∃b(x=3*k))");
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch (SyntaksiViga syntaksiViga) {
            assertEquals(Arrays.asList(7,19), syntaksiViga.getParseriVigadeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testSignatuuriMittekuuluvSymbol2() {

        ParsePuu sisend = new ParsePuu("∃x∃k(x=3?k & ¬∃b(x=3%k))");
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | SyntaksiViga e){
            e.printStackTrace();
        }
        catch(LekseriErind lekseriErind) {
            assertEquals(Arrays.asList(8,20), lekseriErind.getLekseriViagdeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testYleliigneSulg() throws LekseriErind, SyntaksiViga {
        ParsePuu sisend = new ParsePuu("∃q∃p(∃z(y+z+1=q))&∃w(y+w+1=p))&(x=q*p))"); //Alo
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch(SyntaksiViga syntaksiViga) {
            assertEquals(Arrays.asList(29,38), syntaksiViga.getParseriVigadeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testiPuuduvSulg() throws LekseriErind, SyntaksiViga {
        ParsePuu sisend = new ParsePuu("∃z∃y((x = z * y) & (T(z) ∨ T(y))");
        sisend.looParsePuu();
    }

    @Test
    public void testPuuduvKorrutisMark(){
        ParsePuu sisend = new ParsePuu("∃q∃p(∃z(y+z+1=q)&∃w(y+w+1=p)&(x=qp))"); //no viable alternative at input qp
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch(SyntaksiViga syntaksiViga) {
            assertEquals(Arrays.asList(33), syntaksiViga.getParseriVigadeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testPuuduvSulg() throws LekseriErind, SyntaksiViga {
        ParsePuu sisend = new ParsePuu(("∃a∃b(∃c(y+c=a)&∃c(y+c=b)&x=a*b"));
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch(SyntaksiViga syntaksiViga) {
            assertEquals(Arrays.asList(30), syntaksiViga.getParseriVigadeKuulaja().getVeaPosNumbrid());
            assertEquals("Valemi lõpust puudub sulg.", syntaksiViga.getParseriVigadeKuulaja().getVeaSonumid().get(0));
        }
    }


    @Rule
    public ExpectedException erind = ExpectedException.none();

    @Test
    public void testPuuduvSulg2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        try{
            new Kontroll(LoppValem.tagastaValem("∃a∃b(∃c(y + c = a) & ∃c(y + c = b) & x = a*b"), LoppValem.tagastaValem("∃u∃z((x=z*u) & ∃w(y+w+1=z) & ∃t(y+t+1=u))"));
        }
        catch(SyntaksiViga syntaksiViga) {
            assertEquals("Valemi lõpust puudub sulg.", syntaksiViga.getParseriVigadeKuulaja().getVeaSonumid().get(0));
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
    }

    @Test
    public void testPuuduvSulg3() throws LekseriErind, SyntaksiViga {
        erind.expect(SyntaksiViga.class);
        ParsePuu sisend = new ParsePuu("∃y(x=y*(1+1+1) & ∃z(y=z*(1+1+1))");
        sisend.looParsePuu();
    }

    @Test
    public void testiootamatuSumbol () throws LekseriErind, SyntaksiViga {
        erind.expect(SyntaksiViga.class);
        ParsePuu sisend = new ParsePuu("∃y:x=y·(1+1+1) & ¬∃f:x=f·(1+1+1+1+1+1+1+1+1)");
        sisend.looParsePuu();
    }

    @Test
    public void testiootamatuSumbol2 () throws LekseriErind, SyntaksiViga {
        erind.expect(SyntaksiViga.class);
        ParsePuu sisend = new ParsePuu(" ∀y∀z(x=(1+1+1)*y->¬(x=(1+1+1)(1+1+1)*z))");
        sisend.looParsePuu();
    }

    @Test
    public void testiAbimuutujaDef() throws LekseriErind, SyntaksiViga {
        erind.expect(SyntaksiViga.class);
        ParsePuu sisend = new ParsePuu("P3 := 1+1+1 ∃a(x= P3 *a) & ¬∃a(P3 * P3 * a)");
        sisend.looParsePuu();
    }

    @Test
    public void testiNumbriEsitusKahendkujul() throws LekseriErind, SyntaksiViga {
        erind.expect(SyntaksiViga.class);
        ParsePuu sisend = new ParsePuu("Jagub(x,y) := ∃k(x= k*y) Jagub(x,1) & ¬Jagub(x,1001)");
        sisend.looParsePuu();
    }

    @Test
    public void testiKomaKasutamineKvantoriteVahel() throws LekseriErind, SyntaksiViga {

        ParsePuu sisend = new ParsePuu("P(x,y) := x > y ∀x,y(∃a,b( P(a,y)&P(b,y) -> Q(a,b,x)))");
        sisend.looParsePuu();
    }

    @Test
    public void testiPuuduvSulg2() throws LekseriErind, SyntaksiViga {

        ParsePuu sisend = new ParsePuu("  ∃a∃b(∃c(y + c = a)&∃c(y + c = b)&x = a * b");
        sisend.looParsePuu();
    }

    @Test
    public void test() throws LekseriErind, SyntaksiViga {

        ParsePuu sisend = new ParsePuu("∃(a, b, c, d): a = y+c & r = y+d & x = a·b &¬(c=0) & ¬(d=0)");
        sisend.looParsePuu();
    }



    @Test
    public void testErinevIndiviidideArvPredikaatides1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga {
        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("Esitasid 0-kohalise predikaadi, ootasin aga 2-kohalist predikaati.");
        String vastus = "∃u∃z((x=z*u) & ∃w(y+w+1=z) & ∃t(y+t+1=u))";
        String pakkumine = "Suurem(a,b) := ∃c(a=b+c & ¬(c=0)) ∀x∀y∃m∃n(Suurem(m,y) & Suurem(n,y) -> x=m*n)";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
    }

    @Test
    public void testErinevIndiviidideArvPredikaatides2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga {
        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("Esitasid 0-kohalise predikaadi, ootasin aga 1-kohalist predikaati.");
        String vastus = "∃y(x=(1+1+1)*y)&¬∃z(x=(1+1+1)*(1+1+1)*z)";
        String pakkumine = "J(a,b) := ∃z(a=b*z & ¬(b=0)) ∀x(J(x, 1+1+1) & ¬J(x, (1+1+1)*(1+1+1)))";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
    }

    @Test
    public void testErinevIndiviidideArvPredikaatides3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga {

        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("Esitasid 4-kohalise predikaadi, ootasin aga 2-kohalist predikaati.");
        String vastus = "∃u∃z((x=z*u) & ∃w(y+w+1=z) & ∃t(y+t+1=u))";
        String pakkumine = "∃z∃w(y + w = z & ¬(w=0)) & ∃a∃b(y + b = a & ¬(a=0)) & x = z * a";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
    }

    @Test
    public void testAsendaTermAbiDefiValjakutsel() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga {

        //S(x,y) := Ed(y + d = x & -(d=0)) (abidefinitsioon)
        //Väljakutsel S(y,z)
        Konjuktsioon konjuktsioon = new Konjuktsioon(new AtomaarneValem(new LiitTerm(new IndiviidTerm(new Muutuja('y')), new IndiviidTerm(new Muutuja('d'))), new IndiviidTerm(new Muutuja('x'))), new Eitus(new AtomaarneValem(new IndiviidTerm(new Muutuja('d')), new NullTerm())));
        Eks eks = new Eks(konjuktsioon, new Muutuja('d'));
        AbiValem abivalem = new AbiValem(new ValemiID("Suurem", 2), Arrays.asList(new Muutuja('x'), new Muutuja('y')), eks);

        Konjuktsioon konjuktsioonAsendatud = new Konjuktsioon(new AtomaarneValem(new LiitTerm(new IndiviidTerm(new Muutuja('z')), new IndiviidTerm(new Muutuja('d'))), new IndiviidTerm(new Muutuja('y'))), new Eitus(new AtomaarneValem(new IndiviidTerm(new Muutuja('d')), new NullTerm())));
        Eks eksAsendatud = new Eks(konjuktsioonAsendatud, new Muutuja('d'));
        AbiValem asendatudValem = new AbiValem(new ValemiID("Suurem", 2), Arrays.asList(new Muutuja('y'), new Muutuja('z')), eksAsendatud);

        abivalem.asendaTerm(new IndiviidTerm(new Muutuja('y')), a -> a instanceof IndiviidTerm && new Muutuja('x').equals(a.getTahis()));
        abivalem.asendaTerm(new IndiviidTerm(new Muutuja('z')), a -> a instanceof IndiviidTerm && new Muutuja('y').equals(a.getTahis()));
        assertEquals(asendatudValem, abivalem);
    }

    @Test
    public void testiTransitiivsetAsendust() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga {

        Valem valem = LoppValem.tagastaValem("S(x,y) := x = y S(y,z)");
        Map<Muutuja, Double> m = new HashMap<>();
        m.put(new Muutuja('y'), 1.0);
        m.put(new Muutuja('z'), 2.0);
        assertFalse(valem.vaartusta(m, 100.0));

    }

}
