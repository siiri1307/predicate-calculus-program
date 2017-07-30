package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 25/07/17.
 */
public class Yl1C {

    public Tõesuspuu konstrueeriPuu(String sisend, boolean eeldatavToevaartus) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        ParsePuu answer = new ParsePuu(sisend);
        Map m = new HashMap<>();
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)ast.getChildren().get(ast.getChildren().size()-1), eeldatavToevaartus);
        tõesuspuu.looPuu();

        return tõesuspuu;
    }

    //x ei jagu ühegi y-st väiksema algarvuga

    @Test()
    public void onSamavaarneToesuspuuMeetod() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        String sisendA = "B(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "J(x,y) := ∃z(x=y*z) & ¬(y=0)" +
                "P(x,y) := ∃z(x + z = y & ¬(z = 0))" +
                "∀z(B(z) & P(z,y) -> ¬J(x,z))";
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarneBruteForce1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "B(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1) " +
                "J(x,y) := ∃z(x=y*z) & ¬(y=0)" +
                "P(x,y) := ∃z(x + z = y & ¬(z = 0))" +
                "∀z(B(z) & P(z,y) -> ¬J(x,z))"; //5 kv
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";//5kv
        //12 kv
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test
    public void onSamavaarneBruteForce2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "¬∃z∃w((x = z * w) & ∃k((z + k = y) & ¬(k = 0)) & ∀a∀b(z = a * b -> a = 1 ∨ b = 1) & ¬(z = 1))"; //5kv
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))"; //5kv
        //12 kv
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }


    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void eiOleSamavaarneBruteForce1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "P(w) := (w = t*z -> t=1 ∨ z = 1) & ¬(w=1) ¬∃u((x=y*u) & ¬(y=0) & ∃m((w+m+1=y) & ∀t∀zP(w)))";
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test
    public void eiOleSamavaarneBruteForce2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "R(x) := ∀y∀z(x = y*z -> y=1 ∨ z=1) & ¬(x=1) "+
                "V(x,y) := ∃z(x+z=y & ¬(z=0)) " +
                "∀r∀z(¬(x=r*z) -> ((R(z)&V(z,y)∨(R(r)&V(r,y)))))";
        String sisendB = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y) -> ¬∃z(x=w*z) & ¬(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
        //Sinu vastus on väärtustusel {x=1, y=0} false, aga peaks olema true
    }


}
