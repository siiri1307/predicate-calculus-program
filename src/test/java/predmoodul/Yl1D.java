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

        String sisendA = "P(x,y) := Ez(x + z = y & -(z = 0))" +
                "B(x) := AyAz(x = y * z -> y = 1 v z = 1) & -(x = 1)" +
                "Ez(B(z) & P(x,y) & Aa(-(a = z) -> -B(z)))";
        String sisendB = "B(x) := AyAz(x=y*z -> y=1 v z=1) & -(x=1)" +
                "V(x,y) := Ez(x+z+1 = y)" +
                "Ea(V(x,a) & V(a,y) & B(a) & Ab(V(x,b) & V(b,y) & B(b) -> b=a))";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void onSamavaarneToesuspuu() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "P(x,y) := Ez(x + z = y & -(z = 0))" +
                "B(x) := AyAz(x = y * z -> y = 1 v z = 1) & -(x = 1)" +
                "S(x) := AyAz(x=y*z -> y=1 v z=1) & -(x=1)" +
                "V(x,y) := Ez(x+z+1 = y)" +
                "Ez(B(z) & P(x,y) & Aa(-(a = z) -> -B(z)))";
        String sisendB =
                "Ea(V(x,a) & V(a,y) & S(a) & Ab(V(x,b) & V(b,y) & S(b) -> b=a))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }


}
