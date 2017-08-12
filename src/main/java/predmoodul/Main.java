package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.erindid.*;
import predmoodul.valemid.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by siiri on 03/03/17.
 */


public class Main {

    public static void main(String[] args) throws Exception, VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, SyntaksiViga {

        Kontroll kontroll;

        //Süntaksiviga 1

        /*kontroll = new Kontroll(tagastaValem("En(x = 3*n) & -Em(x = 9*m)"), tagastaValem("Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
*/

        //Kui palju infot vigade kohta väljastada: kas positsiooni number, karakter, või lihtsalt vigane sisend? Kas kõik korraga, või ükshaaval?
        //detailne info, ükshaaval


        //Süntaksiviga 2
        /*
        kontroll = new Kontroll(tagastaValem("Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z))"), tagastaValem("Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        */

        //Ei parsi õigesti predtähiseid, mis sisaldavad sümboleid A, E, v? Kas keelata nende kasutamine või muuta grammatika reegleid?

        //uuri kiirklahvide defineerimist sümbolite sisestamiseks

        //Erinev indiviidide arv
        //x jagub 3-ga, aga mitte 9-ga

        /*kontroll = new Kontroll(tagastaValem("AxEz(x = z * (1+1+1) & -(x = z * (1+1+1)*(1+1+1)))"), tagastaValem("Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        */

        //selgita viga täpsemalt, väljasta vabad muutujad




        //Predikaatsümbol ei ole defineeritud
        /*
        kontroll = new Kontroll(tagastaValem("J(x,y) := ∃z(x = y * z) & ¬(y=0) P(x, 1+1+1) & ¬J(x, 1+1+1+1+1+1+1+1+1)"), tagastaValem("∃y(x=(1+1+1)*y) &  ¬∃z(x=(1+1+1)*(1+1+1)*z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        */

        //kasuta eristavaid sümboleid, nt kuupäev või mud karakterid, stringi töötlus

        //Kas predikaatsümbolite mitte.korduvust tuleks kontrollida? Või kokku leppida vähetõenäoliselt korduv süntaks õige valemi salvestamise jaoks?



        //Abipredikaadi tähises on kirjas vabu muutujaid, mis ei esine valemis vabadena
        //ei ole viga, nt H(x,y) := x^2 on aktsept. Viga on väljakutsel erinevate argumentide arvuga.

        /*
        kontroll = new Kontroll(tagastaValem("J(x,y,z) := ∃z(x = y * z) & ¬(y=0) J(x, 1+1+1) & ¬J(x, 1+1+1+1+1+1+1+1+1)"), tagastaValem("∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        */

        //Too välja vabad muutujad, täpsusta teadet



        //Valemis on vabu muutujaid, mis ei ole tähises kirjas
        /*
        kontroll = new Kontroll(tagastaValem("J(x,y) := ∃z(x = y * z) & ¬(k=0) J(x, 1+1+1) & ¬J(x, 1+1+1+1+1+1+1+1+1)"), tagastaValem("∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        */


        //Valemid ei ole samaväärsed
        //x jagub 3-ga, aga mitte 9-ga

        /*
        kontroll = new Kontroll(tagastaValem("EmEn(((1+1+1)*m=x)&-((1+1+1)*(1+1+1)*n=x))"), tagastaValem("Ey(x = y + y + y) & -Ez(x = z + z + z + z + z + z + z + z + z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        if(toevaartus){
            System.out.println(kontroll.kontraNaideStringina());
        }
        */

        //kontrollida vabade muutujate, kvantorite arvu, ja määrata vastavalt vahemik, nt 2 - 100, 5 - 10, 7 - 8. Nt min vahemik on 2 (0 ja 1), funktsioonina avalduv




        //Valemid on samaväärsed

        /*
        kontroll = new Kontroll(tagastaValem("Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)"), tagastaValem("Ez(x = (1+1+1)*z) & Az-(x=(1+1+1+1+1+1+1+1+1)*z)"));

        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        if(toevaartus){
            System.out.println(kontroll.kontraNaideStringina());
        }*/


        /*
        ParsePuu pak = new ParsePuu("M(x) := x=1 B(x) := x=0 C(x) := x=1 Ax(M(x)->B(x))~AxC(x)");
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());
        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        prindiPuu(tõesuspuu);
        */

        /*
        ParsePuu pak = new ParsePuu("J(x,y) := Ek(x = k * y) EyEz((x = y*z) -> Ew(y = w* w))~Ez(J(x, z) & Ey(z = y * y))");
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());
        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        prindiPuu(tõesuspuu);
        */


        //Tõesuspuu meetod: põhisamaväärsus
        /*
        ParsePuu pak = new ParsePuu("M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))");
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());
        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        prindiPuu(tõesuspuu);
        */


        //Tõesuspuu meetod: samaväärsed, kuid ei tuvasta vastuolu
        /*
        String sisendA = "Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "Ey(x=(1+1+1)*y) & -Ez(x=(1+1+1+1+1+1+1+1+1)*z)";
        ParsePuu pak = new ParsePuu(sisendA + "~" + sisendB);
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());

        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        //System.out.println("Väärtustused: " + tõesuspuu.vaartustusedVastavaltEeldusele());
        prindiPuu(tõesuspuu);
        */




        //Tõesuspuu meetod: samaväärsed, kui tippude arv järjekorras on väga suur
        //x on kahe y-st suurema arvu korrutis

        String sisendA = "∃y(x=(1+1+1)*y) & ¬∃z(x=(1+1+1)*(1+1+1)*z)";
        String sisendB = "∃z(x=(1+1+1)*z & ¬(x=(1+1+1+1+1+1+1+1+1)*z))";
        Kontroll kntr = new Kontroll(tagastaValem(sisendB), tagastaValem(sisendA));
        kntr.kontrolliIndiviidideArvuValemites(kntr.getPakkumine().getVabadMuutujad(), kntr.getVastus().getVabadMuutujad());
        ParsePuu pak = new ParsePuu(sisendB + "~" + sisendA);
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());

        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        //System.out.println("Väärtustused: " + tõesuspuu.vaartustusedVastavaltEeldusele());
        prindiPuu(tõesuspuu);
        //analüüsi esimesena valemit, mis toob sisse uue sümboli



        /*
        //String sisendA = "∃u∃z((x = z*u) & ∃w(y + w + 1 = z) & ∃t(y + t +1 = u))" ;
        String sisendA = "∃u∃z(u = z)" ;
        //String sisendB = "S(x, y) := ∃z(x + z = y & ¬(z = 0 )) J(x, y , z ) := x = y * z & ¬(y = 0) ∀z∀w(S(z, y ) & S (w, y) -> ∃xJ(x, z , w))"; //Fredi A
        //String sisendB = "∃n∃m((x=(y+n)*(y+m))& ¬(m=0) & ¬(n=0))";
        String sisendB = "∃x∃y(1=0 & 0=0 & ∃w(y+x=w))";
        //String sisendB = "∃z(x = (y+z) * (y+z) & ¬(z=0))"; //sellega lõpetab töö. Geio I.
        ParsePuu pak = new ParsePuu(sisendB + "~" + sisendA);
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());

        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        //System.out.println("Väärtustused: " + tõesuspuu.vaartustusedVastavaltEeldusele());
        prindiPuu(tõesuspuu);
        */

        //Files.copy(new ByteArrayInputStream(
                //("graph graphname { " + tõesuspuu.dot() + "}").getBytes(StandardCharsets.UTF_8)), Paths.get("/tmp","toesuspuu2.dot"), StandardCopyOption.REPLACE_EXISTING);
        //System.out.println(((Valem)(ast.getChildren().get(ast.getChildren().size()-1))).getVabadMuutujad());
        //System.out.println(((AbiValem)(ast.getChildren().get(0))).vabadeMuutujateEsinemineKorrektne());

        /*
        Sisend sisend = new Sisend("R(x,y) := ∃d(y+d=x & ¬(d=0)) ∃z∃w(Q(w,y) & Q(z,y) & x= z*w)",
                "T(x,y):=∃k(x = y + k) ∃u∃z((x=z*u) & ∃w(y+w+1=z) & ∃t(y+t+1=u))");
        //sisend.liigutaPakkumiseAbidefintsioonidALgusesse();
        System.out.println(sisend.getOigeVastus());
        sisend.asendaOigesVastusesPredtahised();
        System.out.println(sisend.getOigeVastus());
        //System.out.println(sisend.getAbidefinitsioonid(sisend.antlriParser(sisend.getPakkumine())));
        //System.out.println(sisend);*/


        /*
        kontroll = new Kontroll(tagastaValem("P := jagub 3ga Q := jagub 9ga\n" +
                "∀x( P(x)& ¬Q(x) )"),
                tagastaValem("∃y(x = y + y + y) & ¬∃z(x = z + z + z + z + z + z + z + z + z)"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valemid ei ole samaväärsed: " + toevaartus);
        if(toevaartus){
            System.out.println(kontroll.kontraNaideStringina());
        }
        */

        /*
        String sisendA = "F(x):=x=1 G:=1=0 ∀x(F(x)->G)";
        String sisendB = "∃xF(x)->G";
        ParsePuu pak = new ParsePuu("P(x,y) := x+1=y ∀x∃yP(x, y)");
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());

        System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), true);
        tõesuspuu.looPuu();
        //System.out.println("Väärtustused: " + tõesuspuu.vaartustusedVastavaltEeldusele());
        prindiPuu(tõesuspuu);
        */

    }

    private static void prindiPuu(Tõesuspuu tõesuspuu) throws IOException {
        Files.copy(new ByteArrayInputStream(
                ("graph graphname { " + tõesuspuu.dot() + "}").getBytes(StandardCharsets.UTF_8)), Paths.get("/tmp","toesuspuu1.dot"), StandardCopyOption.REPLACE_EXISTING);
        ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "/tmp/toesuspuu1.dot");
        Path pic = Paths.get("/tmp", "toesuspuu1.png");
        pb.redirectOutput(ProcessBuilder.Redirect.to(pic.toFile()));
        Process process = pb.start();
    }

    public static Valem tagastaValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, SyntaksiViga {
        ParsePuu answer = new ParsePuu(sisend);
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        return (Valem) (ast.getChildren().get(ast.getChildren().size()-1));
    }
}
