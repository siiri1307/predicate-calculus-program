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
import predmoodul.valemid.AbiValem;
import predmoodul.valemid.AtomaarneValem;
import predmoodul.valemid.Konjuktsioon;
import predmoodul.valemid.ValemiID;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by siiri on 24/06/17.
 */
public class SisendOnKorrektne {

    @Test
    public void testSignatuuriMittekuuluvSymbol1() {
        ParsePuu sisend = new ParsePuu("ExEk(x=3*k & -Eb(x=3*k))");
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch (ParseriErind parseriErind) {
            assertEquals(Arrays.asList(7,19), parseriErind.getParseriVigadeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testSignatuuriMittekuuluvSymbol2() {

        ParsePuu sisend = new ParsePuu("ExEk(x=3?k & -Eb(x=3%k))");
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | ParseriErind e){
            e.printStackTrace();
        }
        catch(LekseriErind lekseriErind) {
            assertEquals(Arrays.asList(8,20), lekseriErind.getLekseriViagdeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testYleliigneSulg(){
        ParsePuu sisend = new ParsePuu("EqEp(Ez(y+z+1=q))&Ew(y+w+1=p))&(x=q*p))"); //Alo
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch(ParseriErind parseriErind) {
            assertEquals(Arrays.asList(29,38), parseriErind.getParseriVigadeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testPuuduvKorrutisMark(){
        ParsePuu sisend = new ParsePuu("EqEp(Ez(y+z+1=q)&Ew(y+w+1=p)&(x=qp))");
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch(ParseriErind parseriErind) {
            assertEquals(Arrays.asList(33), parseriErind.getParseriVigadeKuulaja().getVeaPosNumbrid());
        }
    }

    @Test
    public void testPuuduvSulg(){
        ParsePuu sisend = new ParsePuu(("EaEb(Ec(y+c=a)&Ec(y+c=b)&x=a*b"));
        try{
            sisend.looParsePuu();
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
        catch(ParseriErind parseriErind) {
            assertEquals(Arrays.asList(30), parseriErind.getParseriVigadeKuulaja().getVeaPosNumbrid());
            assertEquals("Valemi lõpust puudub sulg.", parseriErind.getParseriVigadeKuulaja().getVeaSonumid().get(0));
        }
    }


    @Rule
    public ExpectedException erind = ExpectedException.none();

    @Test
    public void testPuuduvSulg2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        try{
            new Kontroll(LoppValem.tagastaValem("EaEb(Ec(y + c = a) & Ec(y + c = b) & x = a*b"), LoppValem.tagastaValem("EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u))"));
        }
        catch(ParseriErind parseriErind) {
            assertEquals("Valemi lõpust puudub sulg.", parseriErind.getParseriVigadeKuulaja().getVeaSonumid().get(0));
        }
        catch(RuntimeException | LekseriErind e){
            e.printStackTrace();
        }
    }

    @Test
    public void testErinevIndiviidideArvPredikaatides1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, ParseriErind {
        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("Esitasid 0-kohalise predikaadi, ootasin aga 2-kohalist predikaati.");
        String vastus = "EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u))";
        String pakkumine = "Suurem(a,b) := Ec(a=b+c & -(c=0)) AxAyEmEn(Suurem(m,y) & Suurem(n,y) -> x=m*n)";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
    }

    @Test
    public void testErinevIndiviidideArvPredikaatides2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, ParseriErind {
        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("Esitasid 0-kohalise predikaadi, ootasin aga 1-kohalist predikaati.");
        String vastus = "Ey(x=(1+1+1)*y)&-Ez(x=(1+1+1)*(1+1+1)*z)";
        String pakkumine = "J(a,b) := Ez(a=b*z & -(b=0)) Ax(J(x, 1+1+1) & -J(x, (1+1+1)*(1+1+1)))";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
    }

    @Test
    public void testErinevIndiviidideArvPredikaatides3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, ParseriErind {

        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("Esitasid 4-kohalise predikaadi, ootasin aga 2-kohalist predikaati.");
        String vastus = "EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u))";
        String pakkumine = "EzEw(y + w = z & -(w=0)) & EaEb(y + b = a & -(a=0)) & x = z * a";
        Kontroll kontroll = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(vastus));
        kontroll.eiOleSamavaarne();
    }

    @Test
    public void testAsendaTermAbiDefiValjakutsel() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, ParseriErind {

        Konjuktsioon konjuktsioon = new Konjuktsioon(new AtomaarneValem(new LiitTerm(new IndiviidTerm('y'), new IndiviidTerm('d')), new IndiviidTerm('x')), new Eitus(new AtomaarneValem(new IndiviidTerm('d'), new NullTerm())));
        Eks eks = new Eks(konjuktsioon, 'd');
        AbiValem abivalem = new AbiValem(new ValemiID("Suurem", 2), Arrays.asList('x', 'y'), eks);

        Konjuktsioon konjuktsioonAsendatud = new Konjuktsioon(new AtomaarneValem(new LiitTerm(new IndiviidTerm('z'), new IndiviidTerm('d')), new IndiviidTerm('y')), new Eitus(new AtomaarneValem(new IndiviidTerm('d'), new NullTerm())));
        Eks eksAsendatud = new Eks(konjuktsioonAsendatud, 'd');
        AbiValem asendatudValem = new AbiValem(new ValemiID("Suurem", 2), Arrays.asList('y', 'z'), eksAsendatud);

        abivalem.asendaTerm(new IndiviidTerm('y'), x -> x instanceof IndiviidTerm && 'x' == x.getTahis());
        abivalem.asendaTerm(new IndiviidTerm('z'), x -> x instanceof IndiviidTerm && 'y' == x.getTahis());
        assertEquals(abivalem, asendatudValem);
    }

}
