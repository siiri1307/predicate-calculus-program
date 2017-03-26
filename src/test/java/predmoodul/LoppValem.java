package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.valemid.AstNode;
import predmoodul.valemid.Vaartus;
import predmoodul.valemid.Valem;
import predmoodul.valemid.ValemiID;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by siiri on 25/03/17.
 */
public class LoppValem {

    public static Valem tagastaValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {
        Vastus answer = new Vastus(sisend);
        ParseTree pt = answer.createParseTree(sisend);
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid, new HashMap<>());
        return (Valem) (ast.getChildren().get(ast.getChildren().size()-1));
    }
}
