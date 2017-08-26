package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by siiri on 17/04/17.
 */
public class SamaseltToeneVaar {

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

    @Test
    public void testEiSisaldaKorduvaidTippe() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        Tõesuspuu tp = konstrueeriPuu("∀x∀y(x+y=1)", true);
        Collection<TõesuspuuTipp> lehed = tp.getJuurtipp().getLehed();
        lehed.forEach(x -> assertEquals(false, x.getVanem().equals(x)));
    }

    @Test
    public void eiOleSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 ¬(M->B)∨¬(B->M)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(false, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test
    public void onSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 C:= 1=1 ¬B&¬(¬C-> ¬B∨M)", true);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void eiOleSamaseltVaar2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void eiOleSamaseltTõene() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {

        Tõesuspuu tp = konstrueeriPuu("M(x) := x=1 B(x) := x=0 ∀x(M(x)->B(x))->(∃xM(x)->∀xB(x))", false);
        assertEquals(false, tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    //Testid kvantoritega
    @Test
    //õpikust lk 65: iga kvantori distributiivsus
    public void onSamavaarne1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x) := x=1 G(x) := x=0 ∀x(F(x)&G(x)) ~ ∀xF(x)&∀xG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //õpikust lk 65: leidub kvantori distributiivsus. Eeldus, et leidub väärtustus, mil valem on väär, on vale (tagastatav väärtustuste hulk on tühi), seega valem on samaselt tõene.
    public void onSamavaarne2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x) := x=1 G(x) := x=0 ∃x(F(x)∨G(x)) ~ ∃xF(x)∨∃xG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele()); //ei arvesta harus esinevaid konstantsümboleid: see tuleb SIIN ESILE!
    }

    @Test
    public void onSamavaarne3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 ∀x(F(x)&G) ~ ∀xF(x)&G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 ∃x(F(x)&G) ~ ∃xF(x)&G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 ∀x(F(x)∨G) ~ ∀xF(x)∨G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne6() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 ∃x(F(x)∨G) ~ ∃xF(x)∨G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne7() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 ∀x(F(x)->G) ~ ∃xF(x)->G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne8() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 ∃x(F(x)->G) ~ ∀xF(x)->G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne9() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("G(x):=x=1 F:=1=0 ∀x(F->G(x)) ~ F->∀xG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne10() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("G(x):=x=1 F:=1=0 ∃x(F->G(x)) ~ F->∃xG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne11() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 ∀x(F(x)) ~ ∀y(F(y))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne12() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 ¬∀xF(x) ~ ∃x¬F(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne13() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 ¬∃xF(x) ~ ∀x¬F(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //x jagub 3-ga, aga mitte 9-ga
    public void onSamavaarne14() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("∃y(x=(1+1+1)*y)&¬(y=0)&¬∃z(x=(1+1+1)*(1+1+1)*z)~∃y(x=(1+1+1)*y)&∀y¬(x=(1+1+1)*(1+1+1)*y)&¬(y=0)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //x jagub 3-ga, aga mitte 9-ga
    public void eiOleSamavaarne16() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        Tõesuspuu tp = konstrueeriPuu("∃y(x=(1+1+1)*y)&¬(y=0)&¬∃z(x=(1+1+1)*(1+1+1)*z)~∃y(x=(1+1+1)*y)&∀y¬(x=(1+1+1)*(1+1+1)*y)&¬(y=0)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

}
