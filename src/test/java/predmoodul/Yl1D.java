package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by siiri on 26/07/17.
 */
public class Yl1D {

    //x < y ning arvude x ja y vahel leidub täpselt üks algarv

    String oige = "A(x) := ∀y∀z(x=y*z -> y=1 ∨ z=1) & ¬(x=1)" +
            "V(x,y) := ∃z(x+z+1=y)" +
            "∃a(V(x,a)&V(a,y)&A(a)& ∀b(V(x,b) & V(b,y) & A(b) -> b=a))";

    String pakkumine1 = "V(x,y) := ∃z((x + z = y) & ¬(z = 0))" +
            "A(x) := ∀y∀z((x = y * z) -> (y = 1) ∨ (z = 1)) & ¬(x = 1)" +
            "V(x,y) & ∃s(V(x,s) & V(s,y) & A(s) & ∀t(A(t) & V(x,t) & V(t,y) -> t = s))";

    String pakkumine2 = "A(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
            "V(x,y) := ∃n(x+n+1=y)" +
            "∃a(V(x,a) & V(a, y) & A(a) & ∀b(x, b) & V(b, y) & A(b) -> b = a))";

    String pakkumine4 = "A(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
            "V(x, y) := ∃z(x + z = y & ¬(z = 0))" +
            "∃z(V(x, z) & V(z, y) & A(z)) & ¬∃q(V(x, q) & V(q, y) & A(q) & ¬(q = z))";

    String pakkumine5 = "W(x,y) := ∃z((x + z = y) & ¬(z=0))" +
            "P(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
            "W(x,y) & ∃a(W(x, a) & W(a, y) & P(a) -> ∀b(W(x, b) & W(b, y) -> ¬P(a)))";

    //Elise P - väidetavalt õige
    String pakkumine3 = "Algarv(x) := ∀y∀z(x = y*z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
            "Algarvud(x,y) := ∃m(∃k(m = x + k + 1) & ∃k(y = m+ k + 1) & Algarv(m))" +
            "∃m(y=x+m+1) & ∃z(∃m(z = x+m+1) & ∃m(y=z+m+1) & Algarv(z) & ¬Algarvud(x,z) & ¬Algarvud(z,y))";

    public Tõesuspuu konstrueeriPuu(String sisend, boolean eeldatavToevaartus) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        ParsePuu answer = new ParsePuu(sisend);
        Map m = new HashMap<>();
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)ast.getChildren().get(ast.getChildren().size()-1), eeldatavToevaartus);
        tõesuspuu.looPuu();

        return tõesuspuu;
    }

    @Test(timeout = 5000)
    public void onSamavaarneToesuspuuMeetod() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Sisend sisend = new Sisend(pakkumine1, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        //System.out.println(toodeldudSisend);
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(true, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test()
    public void onSamavaarneVaartusteValjaarvutamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind, ErinevIndiviidideArv, IOException {
        Valem pakkumisValem = LoppValem.tagastaValem(pakkumine1);
        Valem oigeValem = LoppValem.tagastaValem(oige);
        //System.out.println("Õige valemi kuju parsimisjärgselt: " + oigeValem);
        Kontroll kontrollimine = new Kontroll(pakkumisValem, oigeValem);
        assertFalse(kontrollimine.eiOleSamavaarne());
    }


    @Test(expected = SyntaksiViga.class)
    public void eiOleSamavaarneToesuspuuMeetod() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Sisend sisend = new Sisend(pakkumine2, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(true, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test(expected = ErinevIndiviidideArv.class)
    public void eiOleSamavaarneToesuspuu() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind, ErinevIndiviidideArv, IOException {
        Sisend sisend = new Sisend(pakkumine4, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        Kontroll k = new Kontroll(LoppValem.tagastaValem(pakkumine4), LoppValem.tagastaValem(oige));
        k.kontrolliIndiviidideArvuValemites(k.getPakkumine().getVabadMuutujad(), k.getVastus().getVabadMuutujad());
        konstrueeriPuu(toodeldudSisend, false);
    }

    @Test(timeout = 5000)
    public void eiOleSamavaarneToesuspuuMeetod2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Sisend sisend = new Sisend(pakkumine5, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        Kontroll k = new Kontroll(LoppValem.tagastaValem(pakkumine5), LoppValem.tagastaValem(oige));
        k.kontrolliIndiviidideArvuValemites(k.getPakkumine().getVabadMuutujad(), k.getVastus().getVabadMuutujad());
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(false, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test()
    public void eiOleSamavaarneVaartusteValjaarvutamine() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind, ErinevIndiviidideArv, IOException {
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine5), LoppValem.tagastaValem(oige));
        System.out.println(kontrollimine.eiOleSamavaarne());
        System.out.println(kontrollimine.kontraNaideStringina());
        assertTrue(kontrollimine.eiOleSamavaarne());
    }

    @Test
    public void onSamavaarneBruteForce() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        String sisendA = "P(x,y) := ∃z(x + z = y & ¬(z = 0))" + //1
                "Algarv(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" + //2
                "∃z(Algarv(z) & P(x,y) & ∀a(¬(a = z) -> ¬Algarv(z)))"; //2
        String sisendB = "M(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" + //2
                "V(x,y) := ∃z(x+z+1 = y)" + //1
                "∃a(V(x,a) & V(a,y) & M(a) & ∀b(V(x,b) & V(b,y) & M(b) -> b=a))"; //2
        //12 vs 19
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void onSamavaarneToesuspuu() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv {

        /*long aeg = System.currentTimeMillis();
        System.out.println(aeg);
        System.out.println(aeg % 1000);*/

        String sisendA = "P(x,y) := ∃z(x + z = y & ¬(z = 0))" +
                "B(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "S(x) := ∀y∀z(x=y*z -> y=1 ∨ z=1) & ¬(x=1)" +
                "V(x,y) := ∃z(x+z+1 = y)" +
                "∃z(B(z) & P(x,y) & ∀a(¬(a = z) -> ¬B(z)))";
        String sisendB =
                "∃a(V(x,a) & V(a,y) & S(a) & ∀b(V(x,b) & V(b,y) & S(b) -> b=a))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }


    @Test(timeout = 5000)
    public void onSamavaarneToesuspuuMeetod1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Sisend sisend = new Sisend(pakkumine3, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(true, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test()
    public void onSamavaarneVaartusteValjaarvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind, ErinevIndiviidideArv, IOException {
        Valem pakkumisValem = LoppValem.tagastaValem(pakkumine3);
        Valem oigeValem = LoppValem.tagastaValem(oige);
        //System.out.println("Pakkumise kuju parsimisjärgselt: " + pakkumisValem);
        Kontroll kontrollimine = new Kontroll(pakkumisValem, oigeValem);
        assertFalse(kontrollimine.eiOleSamavaarne());
        System.out.println(kontrollimine.kontraNaideStringina());
    }


}
