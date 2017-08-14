package predmoodul;

import org.junit.Test;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.SyntaksiViga;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.Sisend;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by siiri on 02/08/17.
 */
public class SisendiTootlemine {


    @Test
    public void testPredtahiseAsendamineAbidefis() throws IOException, SyntaksiViga, AbiValemEiOleDefineeritud, VaarVabadeMuutujateEsinemine {

        Sisend sisend = new Sisend("B(x) := ∀y∀z(x = y * z -> y = 1 ∨ z = 1) & ¬(x = 1) 1=1",
                " ");
        sisend.asendaOigesVastusesPredtahised();
        String v = sisend.getOigeVastus();
        assertTrue(v.charAt(0) == 'V' && v.substring(1,4).matches("\\d{3}") && v.charAt(4) == 'B' &&
                v.substring(5, v.length()).equals("(x):=∀y∀z(x=y*z->y=1∨z=1)&¬(x=1)1=1"));

    }

    @Test
    public void testPredtahiseAsendamineAtomaarsesValemis() throws IOException, SyntaksiViga, AbiValemEiOleDefineeritud, VaarVabadeMuutujateEsinemine {

        Sisend sisend = new Sisend("∀z(B(z) & P(z,y))", "");
        sisend.asendaOigesVastusesPredtahised();
        String v = sisend.getOigeVastus();
        System.out.println(v);
        assertTrue(v.substring(0,4).equals("∀z(V") && v.substring(4, 7).matches("\\d{3}") && v.substring(7, 13).equals("B(z)&V") &&
        v.substring(13,16).matches("\\d{3}") && v.substring(16, v.length()).equals("P(z,y))")) ;
    }

    @Test
    public void testVastuseAbipredikaatideViimineVasakule() throws IOException, SyntaksiViga, AbiValemEiOleDefineeritud, VaarVabadeMuutujateEsinemine {

        Sisend sisend = new Sisend("∃a(x=a*b & ∃n(b=n*n))",
                "J(x,y) := ∃k(x = k * y) ∃z(J(x, z) & ∃y(z = y * y))");
        sisend.liigutaOigeAbidefinitsioonidVasakule();
        assertEquals("∃z(J(x,z)&∃y(z=y*y))", sisend.getOigeVastus());
        assertEquals("J(x,y):=∃k(x=k*y)∃a(x=a*b&∃n(b=n*n))", sisend.getPakkumine());

    }


}
