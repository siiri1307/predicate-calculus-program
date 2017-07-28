package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by siiri on 27/07/17.
 */
public class Yl1A {

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

    //x jagub 3-ga, aga mitte 9-ga

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "Ey(x=(1+1+1)*y) & -(y=0) & -Ez(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "P(x,y) := Ez(x = y * z) & -(y=0) P(x, 1+1+1) & -P(x, 1+1+1+1+1+1+1+1+1)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "Ez(x = (1+1+1)*z) & Az-(x=(1+1+1+1+1+1+1+1+1)*z)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "Ey(x = y + y + y) & -Ez(x = z + z + z + z + z + z + z + z + z)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertFalse(kontrollimine.eiOleSamavaarneIlmaErindita());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "EmEn(((1+1+1)*m=x)&-((1+1+1)*(1+1+1)*n=x))";
        String sisendB = "Ey(x = y + y + y) & -Ez(x = z + z + z + z + z + z + z + z + z)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarneIlmaErindita());
        //Sinu vastus on väärtustusel {x=0} true, aga peaks olema false
    }


    @Test(expected = ErinevIndiviidideArv.class)
    public void eiOleSamavaarneValjaArvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind, ErinevIndiviidideArv {

        String sisendA = "AxEz(x = z * (1+1+1) & -(x = z * (1+1+1)*(1+1+1)))";
        String sisendB = "Ey(x = y + y + y) & -Ez(x = z + z + z + z + z + z + z + z + z)";
        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(sisendA), LoppValem.tagastaValem(sisendB));
        assertTrue(kontrollimine.eiOleSamavaarne());

    }
}
