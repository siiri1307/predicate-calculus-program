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

    @Test(timeout=60000)
    public void onSamavaarneToesuspuuMeetod() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        String sisendA = "B(x) := AyAz(x = y * z -> y = 1 v z = 1) & -(x = 1)" +
                "J(x,y) := Ez(x=y*z) & -(y=0)" +
                "P(x,y) := Ez(x + z = y & -(z = 0))" +
                "Az(B(z) & P(z,y) -> -J(x,z))";
        String sisendB = "Aw(AoAp(w = o * p -> o = 1 v p= 1) & -(w = 1) & Eq(w+q+1=y) -> -Ez(x=w*z) & -(w=0))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarneBruteForce1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "B(x) := AyAz(x = y * z -> y = 1 v z = 1) & -(x = 1) " +
                "J(x,y) := Ez(x=y*z) & -(y=0)" +
                "P(x,y) := Ez(x + z = y & -(z = 0))" +
                "Az(B(z) & P(z,y) -> -J(x,z))";
        String sisendB = "Aw(AoAp(w = o * p -> o = 1 v p= 1) & -(w = 1) & Eq(w+q+1=y) -> -Ez(x=w*z) & -(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test
    public void onSamavaarneBruteForce2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "-EzEw((x = z * w) & Ek((z + k = y) & -(k = 0)) & AaAb(z = a * b -> a = 1 v b = 1) & -(z = 1))";
        String sisendB = "Aw(AoAp(w = o * p -> o = 1 v p= 1) & -(w = 1) & Eq(w+q+1=y) -> -Ez(x=w*z) & -(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }


    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void eiOleSamavaarneBruteForce1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "P(w) := (w = t*z -> t=1 v z = 1) & -(w=1) -Eu((x=y*u) & -(y=0) & Em((w+m+1=y) & AtAzP(w)))";
        String sisendB = "Aw(AoAp(w = o * p -> o = 1 v p= 1) & -(w = 1) & Eq(w+q+1=y) -> -Ez(x=w*z) & -(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test
    public void eiOleSamavaarneBruteForce2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "R(x) := AyAz(x = y*z -> y=1 v z=1) & -(x=1) V(x,y) := Ez(x+z=y & -(z=0)) ArAz(-(x=r*z) -> ((R(z)&V(z,y)v(R(r)&V(r,y)))))";
        String sisendB = "Aw(AoAp(w = o * p -> o = 1 v p= 1) & -(w = 1) & Eq(w+q+1=y) -> -Ez(x=w*z) & -(w=0))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
        //Sinu vastus on väärtustusel {x=1, y=0} false, aga peaks olema true
    }


}
