package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 25/07/17.
 */
public class Yl1C {

    //x ei jagu ühegi y-st väiksema algarvuga

    String oige = "A(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
            "J(x,y) := ∃z(x=y*z) & ¬(y=0)" +
            "V(x,y) := ∃z(x + z = y & ¬(z = 0))" +
            "∀z(A(z) & V(z,y) -> ¬J(x,z))";

    String pakkumine1 = "∀w (∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";
    String pakkumine2 = "¬∃z∃w((x = z * w) & ∃k((z + k = y) & ¬(k = 0)) & ∀a∀b(z = a * b -> a = 1 ∨ b = 1) & ¬(z = 1))";
    String pakkumine3 = "R(x) := ∀y∀z(x = y*z -> y=1 ∨ z=1) & ¬(x=1)" +
            "V(x,y) := ∃z(x+z=y & ¬(z=0))" +
            "∀r∀z(¬(x=r*z) -> ((R(z)&V(z,y)∨(R(r)&V(r,y)))))";
    String pakkumine4 = "¬∃z∃w((x = z * w) & ∃v((z + v = y) & ¬(v = 0)) & ∀a∀b(z = a * b -> a = 1 ∨ b = 1) & ¬(z = 1)";
    String pakkumine5 = "P(x) = ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)) ¬∃z(P(z) & z < y & ∃w(x = w * z))";

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
        /*String sisendA = "B(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "J(x,y) := ∃z(x=y*z) & ¬(y=0)" +
                "P(x,y) := ∃z(x + z = y & ¬(z = 0))" +
                "∀z(B(z) & P(z,y) -> ¬J(x,z))";
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());*/
    }

    @Test
    public void onSamavaarneBruteForce1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv {

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine1), LoppValem.tagastaValem(oige));
        System.out.println(kontrollimine.eiOleSamavaarne());
        System.out.println(kontrollimine.kontraNaideStringina());
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

    @Test(timeout = 5000)
    public void onSamavaarneToesuspuuMeetod2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Sisend sisend = new Sisend(pakkumine2, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(true, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test
    public void onSamavaarneBruteForce2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv {

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine2), LoppValem.tagastaValem(oige));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }


    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void eiOleSamavaarneBruteForce1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv {

        String sisendA = "P(w) := (w = t*z -> t=1 ∨ z = 1) & ¬(w=1) ¬∃u((x=y*u) & ¬(y=0) & ∃m((w+m+1=y) & ∀t∀zP(w)))";
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout = 5000)
    public void eiOleSamavaarneToesuspuuMeetod3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Sisend sisend = new Sisend(pakkumine3, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        System.out.println(toodeldudSisend);
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(false, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test
    public void eiOleSamavaarneBruteForce2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv {

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine3), LoppValem.tagastaValem(oige));
        System.out.println(kontrollimine.eiOleSamavaarne());
        System.out.println(kontrollimine.kontraNaideStringina());
        assertTrue(kontrollimine.eiOleSamavaarne());
        //Sinu vastus on väärtustusel {x=0, y=0} false, aga peaks olema true.
    }

    @Test(expected = SyntaksiViga.class)
    public void syntaksiViga() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind, IOException {

        Sisend sisend = new Sisend(pakkumine4, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        Tõesuspuu tp = konstrueeriPuu(toodeldudSisend, false);
        assertEquals(true, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test()
    public void syntaksiViga2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind, IOException {

        Sisend sisend = new Sisend(pakkumine5, oige);
        String toodeldudSisend = sisend.getEkvivalentsSonena();
        konstrueeriPuu(toodeldudSisend, false);
    }

}
