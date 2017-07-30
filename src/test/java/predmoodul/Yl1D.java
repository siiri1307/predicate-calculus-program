package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 26/07/17.
 */
public class Yl1D {

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

    //x < y ning arvude x ja y vahel leidub täpselt üks algarv

    @Test
    public void onSamavaarneBruteForce() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

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
    public void onSamavaarneToesuspuu() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

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


}
