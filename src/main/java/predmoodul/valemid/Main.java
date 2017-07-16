package predmoodul.valemid;

import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.Kontroll;
import predmoodul.ParsePuu;
import predmoodul.erindid.*;

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

    public static void main(String[] args) throws Exception, VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, ErinevIndiviidideArv, LekseriErind, ParseriErind {

        String sisend = "Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)~Ey(x=(1+1+1)*y)&Ay-(x=(1+1+1)*(1+1+1)*y)&-(y=0)";

        String sisend2 = "Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)~Et(x=(1+1+1)*t & -(x=(1+1+1+1+1+1+1+1+1)*t))";

        String sisend3 = "Et(t=1)~Ez(z=1)";

        String sisend4 = "Ey(x=(1+1+1)*y)&-Ez(x=(1+1+1)*(1+1+1)*z)~Ea(x=(1+1+1)*a)&-Eb(x=(1+1+1+1+1+1+1+1+1)*b)";

       //x on kahe y-st suurema arvu korrutis
        //EmS(m,y) & EnS(n,y) -> (m*n=x)

        //samaväärsed valemid
        String sisend56 = "EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u)) ~ EzEw((x=z*w) & Ea((y+a=z) & -(a=0)) & Eb((y+b=w) & -(b = 0)))";

        //samaväärsed valemid (lehel, jääb tsüklisse)
        String sisend5 = "EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u)) ~ EaEb((x=(a+y)*(b+y)) & -(b=0) & -(a=0))";

        //mitte-samaväärsed
        String sisend6 = "S(a,b) := Ec(a=b+c & -(c=0)) EzEw((x=z*w) & Ea((y+a=z) & -(a=0)) & Eb((y+b=w) & -(b = 0))) ~ AxAyEmEn(S(m,y) & S(n,y) -> x=m*n)";


        String sisend7 = "ExEk(x=3*k)&-Eb(x=3*k)";
        String sisend8 = "Ek,l((y+k+1=z)&(y+l+1=w))";

        //mittesamaväärsed
        String sisend9 = "EuEz(Ew(y+w+1=z) & Et(t+1=1)) ~ Ez(x=(y+z)*(y+z)&-(z=0))";
        //(x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u)
        //2 = (0 + z) * (0 + z) := 2 = z^2

        String sisend10 = "EaEb(Ec(y+c=a)&Ec(y+c=b)&x=a*b)"; //puu joonistamine tsüklis

        String sisend12 = "F(x) := x=1 G(x) := x=0 Ex(F(x)vG(x)) ~ ExF(x)vExG(x)";

        String sisend11 = "EuEz((x=z*u) & Ew(y+w+1=z) & Et(y+t+1=u)) ~ EzEw(x=(y+z)*(y+w))";  //puu joonistamine tsüklis
        //2 = z * z & -(z=0)

        //x ei jagu ühegi y-st väiksema algarvuga
        String sisend13 = "Jagub(x,y) := -(y=0) & Ez(z*y=x) " +
                          "Suurem(x,y) := Ed(y+d=x & -(d=0))" +
                          "Q(x) := Ay(Jagub(x,y) -> y=x v y=1)" +
                           "Az(Suurem(y,z) & Q(z) -> -Jagub(x,z)) ~ Aw(AoAp(w=o*p -> o=1 v p=1) & -(w=1) & Eq(w + q + 1 = y)-> -Ez(x=w*z) & -(w=0))";

        String sisend14 =
                "P(x) := AyAz(x=y*z -> y=1 v z=1) & -(x=1)" +
                "Pd(x,y) := Em(Ek(m=x+k+1) & Ek(y=m+k+1) & P(m))" +
                "Aw(AoAp(w=o*p -> o=1 v p=1) & -(w=1) & Eq(w + q + 1 = y)-> -Ez(x=w*z) & -(w=0)) ~ Em(y=x+m+1)&Ez(Em(z=x+m+1)&Em(y=z+m+1)&P(z)&-Pd(x,z)&-Pd(z,y))";

        String sisend15 = "Aw(AoAp(w=o*p -> o=1 v p=1) & -(w=1) & Eq(w + q + 1 = y)-> -Ez(x=w*z) & -(w=0)) ~ Aa(Eb(a + b = y) & AbAc(a=c*b -> a=1 v b=1) & -(a=1) -> -Ec(x=a*c))";

        //x on kahe y-st suurema arvu korrutis
        String sisend16 = "EmEn((y+m)*(y+n)=x & -(n=0vm=0)) ~ EbEz((x=b*z)&Ea((y+a=b) & -(a=0)) & Ec((y+c=z)&-(c=0)))";

        String sisend17 = "P(x) := x=1 B(y) := y=1 C(z) := z=1+1 (P(x) -> (B(y) -> C(z))) -> ((P(x) -> B(y)) -> (P(x) -> C(z)))";

        String vastus = "Aw(AoAp(w=o*p -> o=1 v p=1) & -(w=1) & Eq(w + q + 1 = y)-> -Ez(x=w*z) & -(w=0))";

        String pakkumine =  "Jagub(x,y) := -(y=0) & Ez(z*y=x) " +
                "Suurem(x,y) := Ed(y+d=x & -(d=0))" +
                "Q(x) := Ay(Jagub(x,y) -> y=x v y=1)" +
                "Az(Suurem(y,z) & Q(z) -> -Jagub(x,z))";


        //Valem valem1 = tagastaValem("Suurem(a,b) := Ec(a=b+c & -(c=0)) AxAyEmEn(Suurem(m,y) & Suurem(n,y) -> x=m*n)");
        //System.out.println(valem1.getVabadMuutujad());
        //Valem valem = tagastaValem("J(a,b) := Ez(a=b*z & -(b=0)) Ax(J(x, 1+1+1) & -J(x, (1+1+1)*(1+1+1)))");
        //System.out.println(valem.getVabadMuutujad());
        //vabad.forEach(x-> System.out.println());
        //"B(x):= x=0 Ax(Ez(z+z=x)->B(x))->(Ex(Ez(z+z=x))->AxB(x))";
        //((0+0=1)->B(1))->((0+0=1)->B(1)) = (f->f)->(f->f)=1
        //x = 0: t -> t
        //x = 1: t -> (t -> f) = f
        //Otsime väärtustust mil on tõene: kui kõik on 0'd
        //z = 1, x = 1; z = 0, x = 0, x = 0; (f->f)->(t->t)=t->t

        //"M(y) := Ez(z+z=y) B(x):= x=0 Ax(M(x)->B(x))->(ExM(x)->AxB(x))"

                //"M(x) := x+1=1+1 B(x):= x=0 Ax(M(x)->B(x))->(ExM(x)->AxB(x))";

                //"Em((x=(1+1+1)*m))&-En(x=(1+1+1)*(1+1+1)*n)~EmEn(((1+1+1)*m=x)&-((1+1+1)*(1+1+1)*n=x))";



                //"M(x) := x=1 B(x) := x=0 Ax(M(x)->B(x))->(ExM(x)->AxB(x))";

                //"M(x) := x+1=1+1 B(x):= x=0 Ax(M(x)->B(x))->(EyM(y)->AzB(z))";

        //(M(0)->B(0))->(M(0)->B(1))
        //(f -> t) -> (f->f)= t -> t -> t

        //a=0, b=0, c=1
        //a=0, b=0, c=0 (M(0)->B(0))->(M(0)->B(0)) = (f->t)->(f->t)=t

                //"Em((x=(1+1+1)*m))&-En(x=(1+1+1)*(1+1+1)*n)~EmEn(((1+1+1)*m=x)&-((1+1+1)*(1+1+1)*n=x))";


                /*"T(x,y,z) := Ea(x + y + z = a)" +

                        "T(1+1,1,1)";*/

                //"(x+1)+1=(x+1)+1 ~ (x+1)+1=x+(1+1)";

                //"Ey((x=(1+1+1)*y)&-(y=0))&-Ez(x=(1+1+1)*(1+1+1)*z)~" +
                //"Ez(x=(1+1+1)*z)&Az-(x=(1+1+1)*(1+1+1)*z)";

                //"x+1=x+1~x+1=1+x"; hea test




        //Ey(x=(1+1+1)*y)&Ay-(x=(1+1+1)*(1+1+1)*y)&-(y=0)
        //Ey(x=(1+1+1)*y)&-(y=0)&-Ez(x=(1+1+1)*(1+1+1)*z)
                //"Ey(x + 1 = y)";

                //

                //"Ey(x + 1 = y)";

                //"F(x) := x=1 G(x) := x=0 Ex(F(x)vG(x)) ~ ExF(x)vExG(x)";

                //"-AxF(x) & AxF(x)";
                //;
                //"Ax(M(x)->B(x))->(ExM(x)->AxB(x))";
                //;

                //"M(x):=x=1 B(x):= x=0 (ExM(x)->AxB(x))->Ax(M(x)->B(x))";

                //"Ax(M(x)->B(x))->(ExM(x)->AxB(x))";


                //;

                //"M(x) := x = 1 B(x) := x=0 Ax(M(x)->B(x))";

                //"M(x):=x=1 B(x):= x=0 (ExM(x)->AxB(x))->Ax(M(x)->B(x))";



                //"M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))";

                //"M := 0=1 B := 0=0 -(M->B)v-(B->M)";

                //"M := 0=1 B := 0=0 C:= 1=1 -B&-(-C-> -BvM)";


                //"V:=1=1 B := 0=1 C:=1=1 (V->(B->C))->((V->B)->(V->C))";


                //"(V->(B->C))->((V->B)->(V->C))";

                //"M := AxEy(x + 1 = y) M";

                //"M(x) := AmAn(x = m * n -> m = 1 v n = 1) & -(x = 1) & z\n" + "M(x)";
                //"T(x,y) := Ea(x * a = y)\n M(x,y)";

                //"M := Ax(x=1)\n M";
                //"M := AxAmAn(x = m * n -> m = 1 v n = 1 v y=1) & Ax-(x = 1)\n" + "M";


                //"Ex(M(x) -> -B(y)) & C(x)";

                //"T(x,y) := Ea(x * a = y)\n" +
                //"T(1,0)";

                //"(ExM(x)->AxB(x))->Ax(M(x)->B(x))";

                //"-(M(x)->B(x))";

                /*"T(x,y) := x * a = y\n" +
                "L(m) := -(m=1)\n" +
                "T(x,y) ~ L(m)";*/


        /*Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        pak.createAST(pp, abivalemid);*/
        /*String test = "1 = 0 -> x = x + 1";
        String vastus = "1 + 1 = x";
        String brute = "Ax((1 = 0 -> x = x + 1) ~ (x = x))";*/


        //Map m = new HashMap<>();
        /*m.put("a",2.0);
        m.put("z",2.0);
        m.put("m",3.0);
        m.put("y",4.0);
        m.put("x",2.0);*/

        /*Kontroll kontroll = new Kontroll(tagastaValem("EmEn((y+m)*(y+n)=x & -(n=0vm=0))"), tagastaValem("EbEz((x=b*z)&Ea((y+a=b) & -(a=0)) & Ec((y+c=z)&-(c=0)))"));
        boolean toevaartus = kontroll.eiOleSamavaarne();*/
        //System.out.println(answer.accepts());

        //ParseTree pt = answer.looParsePuu();
        //Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        //AstNode ast = answer.createAST(pt, abivalemid); //AST abivalemitega
        //Valem loppValem = ((Valem) (ast.getChildren().get(ast.getChildren().size()-1)));

        //Kontroll vaartustusBruteForce = new Kontroll(loppValem);
        //boolean toevaartus = vaartustusBruteForce.eiOleSamavaarne2();

        /*System.out.println("Valem ei ole samaväärne (brute force): " + toevaartus);
        if(toevaartus){
            System.out.println(kontroll.kontraNaideStringina());
        }*/

        String testiLopetatudHaru = "P := 0=0 B := 0=1 -(P->B)v-(B->P)";
        String testiLopetatudHaru2 = "M(x) := x=1 B(x) := x=0 Ax(M(x)->B(x))->(ExM(x)->AxB(x))";
        String testiKorduvust = "M(x) := x=1 B(x) := x=0 (ExM(x) -> AxB(x)) -> Ax(M(x) -> B(x))";
        String testiIga = "AxAy(x+y=1)";
        String tsykkel = "P(x,y) := x+1=y AxEyP(x,y)";
        String testiPlahvatust = "ExEyEz(a*x + b*y + c*z = 1+1+1+1)";
        String testiHargemisteJarjekorda = "M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))";
        String testiKommutatiivsust = "x+1=x+1~x+1=1+x";
        String testiKommutatiivsust2 = "Eu(u+1 = 1+1+1) ~ Eu(1+u = 1+1+1)";
        String testiKommutatiivsust3 = "Ey(x=(1+1+1)*y)~Ey(x=y*(1+1+1))"; //predikaat "x jagub kolmega"
        ParsePuu pak = new ParsePuu(testiKommutatiivsust3);
        ParseTree pp = pak.looParsePuu();
        AstNode asp = pak.createAST(pp, new HashMap<>());

        /*System.out.println(asp.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)asp.getChildren().get(asp.getChildren().size()-1), false);
        tõesuspuu.looPuu();

        //System.out.println("Väärtustused: " + tõesuspuu.vaartustusedVastavaltEeldusele());

        prindiPuu(tõesuspuu);*/

        Kontroll kontroll = new Kontroll(tagastaValem("Ey(x=(1+1+1)*y)"), tagastaValem("Ey(x=y*(1+1+1))"));
        boolean toevaartus = kontroll.eiOleSamavaarne();
        System.out.println("Valem ei ole samaväärne (brute force): " + toevaartus);
        if(toevaartus){
            System.out.println(kontroll.kontraNaideStringina());
        }

        //System.out.println(answer.accepts());

        //Files.copy(new ByteArrayInputStream(
                //("graph graphname { " + tõesuspuu.dot() + "}").getBytes(StandardCharsets.UTF_8)), Paths.get("/tmp","toesuspuu2.dot"), StandardCopyOption.REPLACE_EXISTING);
        //System.out.println(((Valem)(ast.getChildren().get(ast.getChildren().size()-1))).getVabadMuutujad());
        //System.out.println(((AbiValem)(ast.getChildren().get(0))).vabadeMuutujateEsinemineKorrektne());




    }

    private static void prindiPuu(Tõesuspuu tõesuspuu) throws IOException {
        Files.copy(new ByteArrayInputStream(
                ("graph graphname { " + tõesuspuu.dot() + "}").getBytes(StandardCharsets.UTF_8)), Paths.get("/tmp","toesuspuu1.dot"), StandardCopyOption.REPLACE_EXISTING);
        ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "/tmp/toesuspuu1.dot");
        Path pic = Paths.get("/tmp", "toesuspuu1.png");
        pb.redirectOutput(ProcessBuilder.Redirect.to(pic.toFile()));
        Process process = pb.start();
    }

    public static Valem tagastaValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        ParsePuu answer = new ParsePuu(sisend);
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        return (Valem) (ast.getChildren().get(ast.getChildren().size()-1));
    }
}
