package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 30/07/17.
 */
public class Yl2A {

    //Arvu x tegurite hulgas leidub täisruut

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

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "T(x) := ∃n(x = n * n)" + //x on täisruut
                "∃z∃y((x = z * y) & (T(z) ∨ T(y)))"; //4 kvantorit
        String sisendB = "J(x,y) := ∃k(x = k * y)" + //x jagub y'ga ehk y on x'i tegur
                "∃z(J(x, z) & ∃y(z = y * y))"; //3 kvantorit
        //8 kvantorit
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void onSamavaarneToesuspuu() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA =  "T(x) := ∃n(x = n * n)" + //x on täisruut
                "J(x,y) := ∃k(x = k * y)" +
                "∃z∃y((x = z * y) & (T(z) ∨ T(y)))";
        String sisendB =
                "∃z(J(x, z) & ∃y(z = y * y))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        assertEquals(true, tp.vaartustusedVastavaltEeldusele().isEmpty()); //hulk, mis sisaldab väärtustust, mil valem on väär.
        // Kontrollime, et see hulk on tühi, ehk ei leidu väärtustus, mil valem on väär
    }

    @Test()
    //hea näide - ei ole samaväärsed aga viga ei tule välja
    public void eiOleSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "T(x) := ∃n(x = n * n)" + //x on täisruut
                "∃a∃b(x = a * b) -> T(a) ∨ T(b)"; //6 kvantorit
        String sisendB = "J(x,y) := ∃k(x = k * y)" + //x jagub y'ga ehk y on x'i tegur
                "∃z(J(x, z) & ∃y(z = y * y))"; //3 kvantorit
        //10 kvantorit
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneToesuspuu1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "J(x,y) := ∃k(x = k * y) T(x) := ∃n(x = n * n) ∃z(J(x, z) & ∃y(z = y * y))"; //tõene
        String sisendB =
                "∃a∃b(x = a * b) -> T(a) ∨ T(b)"; //väär
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        assertEquals(false, tp.vaartustusedVastavaltEeldusele().isEmpty()); //hulk, mis sisaldab väärtustust, mil valem on väär.
        // Kontrollime, et see hulk ei ole tühi, ehk leidub väärtustus, mil valem on väär
    }


    @Test()
    public void eiOleSamavaarneValjaArvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "∃y∃z((x = y*z) -> ∃w(y = w* w))"; //3
        String sisendB = "J(x,y) := ∃k(x = k * y)" + //x jagub y'ga ehk y on x'i tegur
                "∃z(J(x, z) & ∃y(z = y * y))"; //3 kvantorit
        //7 kvantorit
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarne());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneToesuspuu2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "J(x,y) := ∃k(x = k * y) ∃z(J(x, z) & ∃y(z = y * y))";
        String sisendB = "∃y∃z((x = y*z) -> ∃w(y = w* w))";
        Tõesuspuu tp = konstrueeriPuu(sisendA + "~" + sisendB, false);
        assertEquals(false, tp.vaartustusedVastavaltEeldusele().isEmpty()); //hulk, mis sisaldab väärtustust, mil valem on väär.
        // Kontrollime, et see hulk ei ole tühi, ehk leidub väärtustus, mil valem on väär
    }






}
