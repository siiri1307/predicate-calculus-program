package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 27/07/17.
 */
public class Yl1A {

    //x jagub 3-ga, aga mitte 9-ga

    String oige = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
    String pakkumine = "∃y((1 + 1 + 1) * y = x) & ∃z¬((1 + 1 + 1) * (1 + 1 + 1) * z = x)";
    String pakkumine1 = "Jagub(x,y) := ∃z((x=z*y) & ¬(z=0)) Jagub(x,3) & ¬Jagub(x,9)";

    public static Tõesuspuu konstrueeriPuu(String sisend1, String sisend2, boolean eeldatavToevaartus) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisend = sisend1 + "~" + sisend2;
        ParsePuu answer = new ParsePuu(sisend);
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        Kontroll kntr = new Kontroll(LoppValem.tagastaValem(sisend1), LoppValem.tagastaValem(sisend2));
        kntr.kontrolliIndiviidideArvuValemites(kntr.getPakkumine().getVabadMuutujad(), kntr.getVastus().getVabadMuutujad());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)ast.getChildren().get(ast.getChildren().size()-1), eeldatavToevaartus);
        tõesuspuu.looPuu();

        return tõesuspuu;
    }

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x=(1+1+1)*y) & ¬(y=0) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "P(x,y) := ∃z(x = y * z) & ¬(y=0) P(x, 1+1+1) & ¬P(x, 1+1+1+1+1+1+1+1+1)";
        //6 kv
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test()
    public void onSamavaarneToesuspuuMeetod() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "∃z(x = (1+1+1)*z) & ∀z¬(x=(1+1+1+1+1+1+1+1+1)*z)";
        Tõesuspuu tp = konstrueeriPuu(sisendA, sisendB, false);
        //System.out.println("Väärtustus, mil valem on väär: " + tp.vaartustusedVastavaltEeldusele());
        assertEquals(false,tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "∃z(x = (1+1+1)*z) & ∀z¬(x=(1+1+1+1+1+1+1+1+1)*z)";
        //5 kvantorit - 407 ms
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)";
        //5 kvantorit - 551 ms
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃m∃n(((1+1+1)*m=x)&¬((1+1+1)*(1+1+1)*n=x))";
        String sisendB = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)";
        //5 kvantorit 531 ms
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        kontrollimine.eiOleSamavaarne();
        System.out.println(kontrollimine.kontraNaideStringina());

        //assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
        //Sinu vastus on väärtustusel {x=0} true, aga peaks olema false
    }

    @Test(expected = ErinevIndiviidideArv.class)
    public void eiOleSamavaarneToesuspuuMeetod2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)"; //õige
        String sisendB = "∀x∃z(x = z * (1+1+1) & ¬(x = z * (1+1+1)*(1+1+1)))"; //pakkumine
        konstrueeriPuu(sisendB, sisendA, false);
    }

    @Test(expected = ErinevIndiviidideArv.class)
    public void eiOleSamavaarneValjaArvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∀x∃z(x = z * (1+1+1) & ¬(x = z * (1+1+1)*(1+1+1)))";
        String sisendB = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarne());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendB = "∃z(x=(1+1+1)*z & ¬(x=(1+1+1+1+1+1+1+1+1)*z))"; //pakkumine
        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)"; //õige
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendB), LoppValem.tagastaValem(sisendA));
        Map<Character, Integer> km = new HashMap<>();
        km.put('x', 9);
        kontrollimine.eiOleSamavaarne();
        System.out.println(kontrollimine.kontraNaideStringina());
        assertTrue(kontrollimine.eiOleSamavaarne());
        assertEquals(km, kontrollimine.tagastaKontramudel());
    }

    @Test()
    public void eiOleSamavaarneToesuspuuMeetod3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)"; //õige
        String sisendB = "∃z(x=(1+1+1)*z & ¬(x=(1+1+1+1+1+1+1+1+1)*z))"; //pakkumine
        Tõesuspuu tp = konstrueeriPuu(sisendB, sisendA, false);
        assertEquals(false,tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test(expected = AbiValemEiOleDefineeritud.class)
    public void eiOleSamavaarneValjaArvutamine4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendB = "P := jagub 3ga Q := jagub 9ga" +
                "∀x(P(x)& ¬Q(x))";
        String sisendA = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        kontrollimine.eiOleSamavaarne();
    }


    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendB = "∃y((1 + 1 + 1) * y = x) & ∃z¬((1 + 1 + 1) * (1 + 1 + 1) * z = x)"; //pakkumine
        String sisendA = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"; //õige
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendB), LoppValem.tagastaValem(sisendA));
        kontrollimine.eiOleSamavaarne();
        System.out.println(kontrollimine.kontraNaideStringina());
        Map<Character, Integer> km = new HashMap<>();
        km.put('x', 0);
        assertEquals(km, kontrollimine.tagastaKontramudel());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine6() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"; //õige

        String sisendB = "J(x, y) := ∃z(x = y * z) & ¬(y = 0) ∃z∃r(z = 1+1+1 & r = (1+1+1) * (1+1+1) -> J(x, z) & ¬J(x, r)) "; //pakkumine

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendB), LoppValem.tagastaValem(sisendA));
        kontrollimine.eiOleSamavaarne();
        Map<Character, Integer> km = new HashMap<>();
        km.put('x', 0);
        assertEquals(km, kontrollimine.tagastaKontramudel());
    }

    @Rule
    public ExpectedException erind = ExpectedException.none();

    @Test()
    public void eiOleSamavaarneValjaArvutamine7() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        erind.expect(ErinevIndiviidideArv.class);
        erind.expectMessage("x peab esinema valemis vabalt.");

        String sisendA = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"; //õige
        String sisendB = "∃x((y=(1+1+1)*x))&¬∃x(y=(1+1+1+1+1+1+1+1+1)*x)"; //pakkumine: väidetakse y'i, mitte x'i kohta

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendB), LoppValem.tagastaValem(sisendA));
        kontrollimine.eiOleSamavaarne();
    }

    @Test()
    public void eiOleSamavaarneValjaArvutamine8() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "Jagub(x,y) := ∃z(x = z*y) ∀x(Jagub(x,1+1+1) & ¬(Jagub(x,(1+1+1)*(1+1+1))))"; //pakkumine
        String sisendB = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"; //õige
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarne());
    }


    @Test()
    public void onSamavaarneValjaArvutamine8() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"; //õige
        String sisendB = "∃y(x=y*(1+1+1) & ¬∃z(y=z*(1+1+1)))"; //pakkumine Mark P.

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendB), LoppValem.tagastaValem(sisendA));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

    @Test(timeout=60000) //Karl Riis 90 p vs Laura K 100 p
    public void onSamavaarneValjaArvutamine4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"; //õige

        String sisendB = "J(x, y) := ∃z(x = y * z) J(x, 1+1+1) & ¬J(x,(1+1+1)*(1+1+1))"; //pakkumine

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendB), LoppValem.tagastaValem(sisendA));
        kontrollimine.eiOleSamavaarne();
        System.out.println(kontrollimine.tagastaKontramudel());
    }

    @Test()
    public void eiOleSamavaarneToesuspuuMeetod() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Tõesuspuu tp = konstrueeriPuu(pakkumine, oige, false);
        assertEquals(false,tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine), LoppValem.tagastaValem(oige));
        kontrollimine.eiOleSamavaarne();
        System.out.println(kontrollimine.kontraNaideStringina());
    }

    @Test(expected = SyntaksiViga.class)
    public void eiOleSamavaarneToesuspuuMeetod4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Tõesuspuu tp = konstrueeriPuu(pakkumine1, oige, false);
        assertEquals(false,tp.vaartustusedVastavaltEeldusele().isEmpty());
    }





}
