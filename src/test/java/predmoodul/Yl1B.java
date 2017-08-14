package predmoodul;

import org.junit.Test;
import predmoodul.erindid.*;
import predmoodul.valemid.Tõesuspuu;
import predmoodul.valemid.Valem;

import java.io.IOException;

import static org.junit.Assert.*;

public class Yl1B {

    //x on kahe y-st suurema arvu korrutis

    String oige = "∃q∃p(∃z(y+z+1=q) & ∃w(y+w+1=p) & (x=q*p))";
    String pakkumine1 = "Q(x, y) := ∃d(y + d = x & ¬(d = 0)) " +
            "∃z∃w(Q(w, y) & Q(z, y) & x = w * z)";
    String pakkumine2 = "Suurem(x,y) := ∃m(y+m=x)& ¬(m=0) ∃a∃b(x=a*b & Suurem(a,y) & Suurem(b,y))";
    String pakkumine2_1 = "Suurem(x,y) := ∃m((y+m=x)& ¬(m=0)) ∃a∃b(x=a*b & Suurem(a,y) & Suurem(b,y))";
    String pakkumine3 = "∃z∃w((x = z * w) & ∃a((y + a = z) & ¬(a = 0)) & ∃b((y + b = w) ¬(b = 0)))";
    String pakkumine4 = "∃a∃b(∃n(y+n=a) & ∃n(y+n=b) -> x=a*b)";
    String pakkumine5 = "(x = z * w) & ∃k∃l((y + k + 1 = z) & (y + l + 1 = w))";

    @Test(timeout = 5000)
    public void onSamavaarneToesuspuuMeetod1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Tõesuspuu tp = Yl1A.konstrueeriPuu(pakkumine1, oige, false);
        assertEquals(true,tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test(timeout=60000)
    public void onSamavaarneValjaArvutamine1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine1), LoppValem.tagastaValem(oige));
        assertFalse(kontrollimine.eiOleSamavaarne());
    }

    @Test(expected = VaarVabadeMuutujateEsinemine.class)
    public void eiOleSamavaarneToesuspuuMeetod2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {
        Yl1A.konstrueeriPuu(pakkumine2, oige, false);
    }

    @Test(expected = SyntaksiViga.class)
    public void eiOleSamavaarneToesuspuuMeetod3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {
        Yl1A.konstrueeriPuu(pakkumine3, oige, false);
    }

    @Test(timeout = 5000)
    public void eiOleSamavaarneToesuspuuMeetod4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {
        Tõesuspuu tp = Yl1A.konstrueeriPuu(pakkumine4, oige, false);
        assertEquals(false,tp.vaartustusedVastavaltEeldusele().isEmpty());
    }

    @Test(timeout=60000)
    public void eiOleSamavaarneValjaArvutamine2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {

        Kontroll kontrollimine = new Kontroll(LoppValem.tagastaValem(pakkumine4), LoppValem.tagastaValem(oige));
        kontrollimine.eiOleSamavaarne();
        System.out.println(kontrollimine.kontraNaideStringina());
        assertTrue(kontrollimine.eiOleSamavaarne());
    }

    @Test(expected = ErinevIndiviidideArv.class)
    public void eiOleSamavaarneToesuspuuMeetod5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga, ErinevIndiviidideArv, IOException {
        Yl1A.konstrueeriPuu(pakkumine5, oige, false);
    }

    @Test
    public void test() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, LekseriErind {
        Valem pakkumine = LoppValem.tagastaValem(pakkumine1);
        Valem oige = LoppValem.tagastaValem(pakkumine2_1);
        //Valem ekv = Kontroll.moodustaEkvivalentsiValem(pakkumine, oige);
        //Tõesuspuu tp = Tõesuspuu.looTõesuspuu(ekv, false);
        //tp.looPuu();
    }

}
