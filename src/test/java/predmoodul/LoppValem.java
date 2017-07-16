package predmoodul;

import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.erindid.*;
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

    private LekseriVigadeKuulaja lekseriVigadeKuulaja;
    private ParseriVigadeKuulaja parseriVigadeKuulaja;

    public static Valem tagastaValem(String sisend) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud, LekseriErind, ParseriErind {
        ParsePuu answer = new ParsePuu(sisend);
        answer.getLekseriVead();
        ParseTree pt = answer.looParsePuu();
        Map<ValemiID, Vaartus> abivalemid = new HashMap<>();
        AstNode ast = answer.createAST(pt, abivalemid);
        return (Valem) (ast.getChildren().get(ast.getChildren().size()-1));
    }
}
