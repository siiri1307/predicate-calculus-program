package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by siiri on 17/04/17.
 */
public class SamaseltToeneVaar {

    public Tõesuspuu konstrueeriPuu(String sisend, boolean eeldatavToevaartus) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        Vastus answer = new Vastus(sisend);
        Map m = new HashMap<>();
        ParseTree pt = answer.createParseTree(sisend);
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid, m);
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)ast.getChildren().get(ast.getChildren().size()-1), eeldatavToevaartus);
        tõesuspuu.looPuu();

        return tõesuspuu;
    }

    @Test
    public void eiOleSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 -(M->B)v-(B->M)", false);
        Map<String, Boolean> v1 = new HashMap<>();
        v1.put("M", false);
        v1.put("B", false);
        Map<String, Boolean> v2 = new HashMap<>();
        v2.put("M", true);
        v2.put("B", true);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        vaartustused.add(v1);
        vaartustused.add(v2);
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 C:= 1=1 -B&-(-C-> -BvM)", true);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void eiOleSamaseltVaar2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    //Testid kvantoritega
    @Test
    //õpikust lk 65: iga kvantori distributiivsus
    public void onSamavaarne1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Tõesuspuu tp = konstrueeriPuu("F(x) := x=1 G(x) := x=0 Ax(F(x)&G(x)) ~ AxF(x)&AxG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //Bugi
    //õpikust lk 65: leidub kvantori distributiivsus. Eeldus, et leidub väärtustus, mil valem on väär, on vale (tagastatav väärtustuste hulk on tühi), seega valem on samaselt tõene.
    public void onSamavaarne2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Tõesuspuu tp = konstrueeriPuu("F(x) := x=1 G(x) := x=0 Ex(F(x)vG(x)) ~ ExF(x)vExG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele()); //ei arvesta harus esinevaid konstantsümboleid: see tuleb SIIN ESILE!
    }
}
