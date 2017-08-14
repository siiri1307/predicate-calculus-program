package predmoodul;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.ErinevIndiviidideArv;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.Sisend;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Integratsioon {

    String vastus1A ="∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
    String vastus1B = "∃q∃p(∃z(y+z+1=q) & ∃w(y+w+1=p) & (x=q*p))";
    String vastus1C = "A(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
            "J(x,y) := ∃z(x=y*z) & ¬(y=0)" +
            "V(x,y) := ∃z(x + z = y & ¬(z = 0))" +
            "∀z(A(z) & V(z,y) -> ¬J(x,z))";
    String vastus1D = "A(x) := ∀y∀z(x=y*z -> y=1 ∨ z=1) & ¬(x=1)" +
            "V(x,y) := ∃z(x+z+1=y)" +
            "∃a(V(x,a)&V(a,y)&A(a)& ∀b(V(x,b) & V(b,y) & A(b) -> b=a))";

    Sisend sisend;
    Kontroll knt;
    String pakkumine;

    @Rule
    public ExpectedException erand = ExpectedException.none();

    @Test
    public void testYl1A_0() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∃y∀z(x = (1-1+1)*y & ¬(x = (1+1+1)*(1+1+1)*z))";

        sisend = new Sisend(pakkumine, vastus1A);
        knt = new Kontroll(pakkumine, vastus1A);
        assertEquals(1, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1A_0_0() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∃a(x = (1+1+1)*a) & ∀b¬(x=(1+1+1)*(1+1+1)*b)";

        sisend = new Sisend(pakkumine, vastus1A);
        knt = new Kontroll(pakkumine, vastus1A);
        assertEquals(1, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1A_1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∃z(x=(1+1+1)*z) & ∀z¬(x=(1+1+1+1+1+1+1+1+1)*z)";

        sisend = new Sisend(pakkumine, vastus1A);
        knt = new Kontroll(pakkumine, vastus1A);
        assertEquals(3, knt.getKontrolliTulemus());
    }

    @Test(expected = ErinevIndiviidideArv.class)
    public void testYl1A_2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∀x∃z(x = z * (1+1+1) & ¬(x = z * (1+1+1)*(1+1+1)))";

        sisend = new Sisend(pakkumine, vastus1A);
        new Kontroll(pakkumine, vastus1A).getKontrolliTulemus();
    }

    @Test
    public void testYl1A_3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∃z(x=(1+1+1)*z & ¬(x=(1+1+1+1+1+1+1+1+1)*z))";

        sisend = new Sisend(pakkumine, vastus1A);
        knt = new Kontroll(pakkumine, vastus1A);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(0, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1A_4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∃y((1 + 1 + 1) * y = x) & ∃z¬((1 + 1 + 1) * (1 + 1 + 1) * z = x)";

        sisend = new Sisend(pakkumine, vastus1A);
        knt = new Kontroll(pakkumine, vastus1A);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(0, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1A_5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(SyntaksiViga.class);
        erand.expectMessage("Positsioonil 43 on lubamatu sümbol '3'.");

        pakkumine = "Jagub(x,y) := ∃z((x=z*y) & ¬(z=0)) Jagub(x,3) & ¬Jagub(x,9)";
        knt = new Kontroll(pakkumine, vastus1A);
    }

    @Test
    public void testYl1B_1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "Q(x, y) := ∃d(y + d = x & ¬(d = 0))" +
                "∃z∃w(Q(w, y) & Q(z, y) & x = w * z)";

        sisend = new Sisend(pakkumine, vastus1B);
        knt = new Kontroll(pakkumine, vastus1B);
        assertEquals(3, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1B_2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(VaarVabadeMuutujateEsinemine.class);
        erand.expectMessage("Valemis esineb vabana muutuja m, mida ei ole abipredikaadi tähises kirjas.");

        pakkumine = "Suurem(x,y)  := ∃m(y+m=x)& ¬(m=0)" +
                "∃a∃b(x=a*b & Suurem(a,y) & Suurem(b,y))";
        sisend = new Sisend(pakkumine, vastus1B);
        knt = new Kontroll(pakkumine, vastus1B);
    }

    @Test
    public void testYl1B_3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∃a∃b(∃n(y+n=a) & ∃n(y+n=b) -> x=a*b)";

        sisend = new Sisend(pakkumine, vastus1B);
        knt = new Kontroll(pakkumine, vastus1B);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(0, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1B_4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(SyntaksiViga.class);
        erand.expectMessage("Positsioonil 63 on lubamatu sümbol '¬'. Ootasin üht järgnevast:  {'->', '~', '&', ')', '∨'}.");

        pakkumine = "∃z∃w((x = z * w) & ∃a((y + a = z) & ¬(a = 0)) & " +
                "∃b((y + b = w) ¬(b = 0)))";
        sisend = new Sisend(pakkumine, vastus1B);
        knt = new Kontroll(pakkumine, vastus1B);
    }

    @Test
    public void testYl1B_5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(ErinevIndiviidideArv.class);
        erand.expectMessage("Esitasid 4-kohalise predikaadi, ootasin aga 2-kohalist predikaati, kus indiviidid y, x esinevad vabalt.");

        pakkumine = "(x = z * w) & ∃k∃l((y + k + 1 = z) & (y + l + 1 = w))";
        sisend = new Sisend(pakkumine, vastus1B);
        new Kontroll(pakkumine, vastus1B);
    }

    @Test
    public void testYl1C_1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "∀w(∀o∀p(w = o * p -> o = 1 ∨ p= 1) & ¬(w = 1) & ∃q(w+q+1=y ) -> ¬∃z(x=w*z) & ¬(w=0))";

        sisend = new Sisend(pakkumine, vastus1C);
        knt = new Kontroll(pakkumine, vastus1C);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(3, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1C_2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "¬∃z∃w((x = z * w) & ∃k((z + k = y) & ¬(k = 0)) & ∀a∀b(z = a * b -> a = 1 ∨ b = 1) & ¬(z = 1))";

        sisend = new Sisend(pakkumine, vastus1C);
        knt = new Kontroll(pakkumine, vastus1C);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(3, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1C_3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "R(x) := ∀y∀z(x = y*z -> y=1 ∨ z=1) & ¬(x=1)" +
                "V(x,y) := ∃z(x+z=y & ¬(z=0))" +
                "∀r∀z(¬(x=r*z)-> ((R(z)&V(z,y)∨(R(r)&V(r,y)))))";

        sisend = new Sisend(pakkumine, vastus1C);
        knt = new Kontroll(pakkumine, vastus1C);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(0, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1C_4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(SyntaksiViga.class);
        erand.expectMessage("Positsioonil 92 puudub ')'.");

        pakkumine = "¬∃z∃w((x = z * w) & ∃v((z + v = y) & ¬(v = 0)) & ∀a∀b(z = a * b -> a = 1 ∨ b = 1) & ¬(z = 1)";
        sisend = new Sisend(pakkumine, vastus1C);
        new Kontroll(pakkumine, vastus1C);
    }

    @Test
    public void testYl1C_5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(SyntaksiViga.class);
        erand.expectMessage("Positsioonil 65 on lubamatu sümbol '<'.");

        pakkumine = "P(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1) ¬∃z(P(z) & z < y & ∃w(x = w * z))";
        sisend = new Sisend(pakkumine, vastus1C);
        new Kontroll(pakkumine, vastus1C);
    }

    @Test
    public void testYl1D_1() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "V(x,y) := ∃z((x + z = y) & ¬(z = 0))" +
                "A(x) := ∀y∀z((x = y * z) -> (y = 1) ∨ (z = 1)) & ¬(x = 1)" +
                "V(x,y) & ∃s(V(x,s) & V(s,y) & A(s) & ∀t(A(t) & V(x,t) & V(t,y) -> t = s))";
        sisend = new Sisend(pakkumine, vastus1D);
        knt = new Kontroll(pakkumine, vastus1D);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(3,knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1D_2() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(SyntaksiViga.class);
        erand.expectMessage("Positsioonil 105 on lubamatu sümbol ','.");
        pakkumine = "A(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "V(x,y) := ∃n(x+n+1=y)" +
                "∃a(V(x,a) & V(a, y) & A(a) & ∀b(x, b) & V(b, y) & A(b) -> b = a))";
        sisend = new Sisend(pakkumine, vastus1D);
        new Kontroll(pakkumine, vastus1D);
    }

    @Test
    public void testYl1D_3() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        erand.expect(ErinevIndiviidideArv.class);
        erand.expectMessage("Esitasid 3-kohalise predikaadi, ootasin aga 2-kohalist predikaati, kus indiviidid y, x esinevad vabalt.");
        pakkumine = "A(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "V(x, y) := ∃z(x + z = y & ¬(z = 0))" +
                "∃z(V(x, z) & V(z, y) & A(z)) & ¬∃q(V(x, q) & V(q, y) & A(q) & ¬(q = z))";
        sisend = new Sisend(pakkumine, vastus1D);
        new Kontroll(pakkumine, vastus1D);
    }

    @Test
    public void testYl1D_4() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "W(x,y) := ∃z((x + z = y) & ¬(z=0))" +
                "P(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "W(x,y) & ∃a(W(x, a) & W(a, y) & P(a) -> ∀b(W(x, b) & W(b, y) -> ¬P(a)))";
        sisend = new Sisend(pakkumine, vastus1D);
        knt = new Kontroll(pakkumine, vastus1D);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(0, knt.getKontrolliTulemus());
    }

    @Test
    public void testYl1D_5() throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, SyntaksiViga, IOException, ErinevIndiviidideArv {

        pakkumine = "Algarv(x) := ∀y∀z(x = y*z -> y = 1 ∨ z = 1) & ¬(x = 1)" +
                "Algarvud(x,y) := ∃m(∃k(m = x + k + 1) & ∃k(y = m+ k + 1) & Algarv(m))" +
                "∃m(y=x+m+1) & ∃z(∃m(z = x+m+1) & ∃m(y=z+m+1) & Algarv(z) & ¬Algarvud(x,z) & ¬Algarvud(z,y))";

        sisend = new Sisend(pakkumine, vastus1D);
        knt = new Kontroll(pakkumine, vastus1D);
        System.out.println(knt.kontraNaideStringina());
        assertEquals(3, knt.getKontrolliTulemus());
    }
}
