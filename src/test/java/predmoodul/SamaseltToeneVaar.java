package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.ParseriErind;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by siiri on 17/04/17.
 */
public class SamaseltToeneVaar {

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

    @Test
    public void testEiSisaldaKorduvaidTippe() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        Tõesuspuu tp = konstrueeriPuu("AxAy(x+y=1)", true);
        Collection<TõesuspuuTipp> lehed = tp.getJuurtipp().getLehed();
        lehed.forEach(x -> assertEquals(false, x.getVanem().equals(x)));
    }

    @Test
    public void eiOleSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

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
    public void onSamaseltVaar() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 C:= 1=1 -B&-(-C-> -BvM)", true);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void eiOleSamaseltVaar2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        Tõesuspuu tp = konstrueeriPuu("M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void eiOleSamaseltTõene() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {

        Tõesuspuu tp = konstrueeriPuu("M(x) := x=1 B(x) := x=0 Ax(M(x)->B(x))->(ExM(x)->AxB(x))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    //Testid kvantoritega
    @Test
    //õpikust lk 65: iga kvantori distributiivsus
    public void onSamavaarne1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x) := x=1 G(x) := x=0 Ax(F(x)&G(x)) ~ AxF(x)&AxG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //Bugi
    //õpikust lk 65: leidub kvantori distributiivsus. Eeldus, et leidub väärtustus, mil valem on väär, on vale (tagastatav väärtustuste hulk on tühi), seega valem on samaselt tõene.
    public void onSamavaarne2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x) := x=1 G(x) := x=0 Ex(F(x)vG(x)) ~ ExF(x)vExG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele()); //ei arvesta harus esinevaid konstantsümboleid: see tuleb SIIN ESILE!
    }

    @Test
    public void onSamavaarne3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 Ax(F(x)&G) ~ AxF(x)&G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 Ex(F(x)&G) ~ ExF(x)&G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 Ax(F(x)vG) ~ AxF(x)vG", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne6() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 Ex(F(x)vG) ~ ExF(x)vG", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne7() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 Ax(F(x)->G) ~ ExF(x)->G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne8() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 G:=1=0 Ex(F(x)->G) ~ AxF(x)->G", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne9() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("G(x):=x=1 F:=1=0 Ax(F->G(x)) ~ F->AxG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne10() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("G(x):=x=1 F:=1=0 Ex(F->G(x)) ~ F->ExG(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne11() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 Ax(F(x)) ~ Ay(F(y))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne12() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 -AxF(x) ~ Ex-F(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void onSamavaarne13() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("F(x):=x=1 -ExF(x) ~ Ax-F(x)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //x jagub 3-ga, aga mitte 9-ga
    public void onSamavaarne14() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)~Ey(x=(1+1+1)*y)&Ay-(x=(1+1+1)*(1+1+1)*y)&-(y=0)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //x jagub 3-ga, aga mitte 9-ga
    public void eiOleSamavaarne15() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("Em((x=(1+1+1)*m))&-En(x=(1+1+1)*(1+1+1)*n)~EmEn(((1+1+1)*m=x)&((1+1+1)*(1+1+1)*n=x))", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    //x jagub 3-ga, aga mitte 9-ga
    public void eiOleSamavaarne16() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)~Ey(x=(1+1+1)*y)&Ay-(x=(1+1+1)*(1+1+1)*y)&-(y=0)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

    @Test
    public void muutujateAsenduseTest() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        Tõesuspuu tp = konstrueeriPuu("Ey(x + 1 = y)", false);
        Set<Map<String, Boolean>> vaartustused = new HashSet<>();
        assertEquals(vaartustused, tp.vaartustusedVastavaltEeldusele());
    }

}
