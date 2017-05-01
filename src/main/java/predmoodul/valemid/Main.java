package predmoodul.valemid;

import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.Kontroll;
import predmoodul.Vastus;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;

import java.io.ByteArrayInputStream;
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

    public static void main(String[] args) throws Exception, VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
//        if (true) {
//            throw new Exception();
//        }
        String sisend = "M := 0=1 B := 0=0 C:= 1=1 (M->(B->C))->((M->B)->(M->C))";

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

        Vastus answer = new Vastus(sisend);

        String test = "1 = 0 -> x = x + 1";
        String vastus = "1 + 1 = x";

        String brute = "Ax((1 = 0 -> x = x + 1) ~ (x = x))";


        Map m = new HashMap<>();
        m.put("a",2.0);
        m.put("z",2.0);
        m.put("m",3.0);
        m.put("y",4.0);
        m.put("x",2.0);
        Kontroll kontroll = new Kontroll(tagastaValem(test), tagastaValem(vastus));
        System.out.println("Vastus: " + kontroll.eiOleSamavaarne());
        //System.out.println(answer.accepts());
        ParseTree pt = answer.createParseTree(sisend);
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid, m); //AST abivalemitega
        //AstNode loppValemAst = answer.createAST(pt, 0, astAbivalemitest, m);
        System.out.println(ast.toString());
        Tõesuspuu tõesuspuu = Tõesuspuu.looTõesuspuu((Valem)ast.getChildren().get(ast.getChildren().size()-1), false);
        tõesuspuu.looPuu();
        System.out.println(tõesuspuu.vaartustusedVastavaltEeldusele());

        Files.copy(new ByteArrayInputStream(
                ("graph graphname { " + tõesuspuu.dot() + "}").getBytes(StandardCharsets.UTF_8)), Paths.get("/tmp","toesuspuu2.dot"), StandardCopyOption.REPLACE_EXISTING);
        ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "/tmp/toesuspuu2.dot");
        Path pic = Paths.get("/tmp", "toesuspuu4.png");
        pb.redirectOutput(ProcessBuilder.Redirect.to(pic.toFile()));
        Process process = pb.start();


        //Files.copy(new ByteArrayInputStream(
                //("graph graphname { " + tõesuspuu.dot() + "}").getBytes(StandardCharsets.UTF_8)), Paths.get("/tmp","toesuspuu2.dot"), StandardCopyOption.REPLACE_EXISTING);
        //System.out.println(((Valem)(ast.getChildren().get(ast.getChildren().size()-1))).getVabadMuutujad());
        //System.out.println(((AbiValem)(ast.getChildren().get(0))).vabadeMuutujateEsinemineKorrektne());




    }

    public static Valem tagastaValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Vastus answer = new Vastus(sisend);
        ParseTree pt = answer.createParseTree(sisend);
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid, new HashMap<>());
        return (Valem) (ast.getChildren().get(ast.getChildren().size()-1));
    }
}
