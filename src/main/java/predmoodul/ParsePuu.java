package predmoodul;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.erindid.AbiValemEiOleDefineeritud;
import predmoodul.erindid.LekseriVigadeKuulaja;
import predmoodul.erindid.VaarVabadeMuutujateEsinemine;
import predmoodul.erindid.ViganeSisend;
import predmoodul.kvantorid.*;
import predmoodul.termid.*;
import predmoodul.valemid.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by siiri on 02/02/17.
 */

public class ParsePuu {

    String sisend;


    public ParsePuu(String input) {
        this.sisend = input;
    }


    public ParseTree createParseTree(String program){

        ParseTree parseTree;

        //lexer splits input into tokens
        ANTLRInputStream input = new ANTLRInputStream(program); //like a char[] buffer
        PredLexer lexer = new PredLexer(input);
        LekseriVigadeKuulaja lvk = new LekseriVigadeKuulaja();
        lexer.addErrorListener(lvk);


        CommonTokenStream tokens = new CommonTokenStream(lexer); //provides access to all tokens by index

        PredParser parser = new PredParser(tokens);
        ViganeSisend vigadeKuulaja = new ViganeSisend();
        parser.addErrorListener(vigadeKuulaja);


        parseTree = parser.koguvalem();

        System.out.println(parseTree.toStringTree(parser));

        if(lvk.onLekseriVigu()){

            List<String> vead = lvk.getVeaSonumid();

            for(String s: vead){
                System.out.println(s);
            }

            throw new RuntimeException("Leksimine ebaõnnestus");
        }

        if(vigadeKuulaja.sisendisOnVigu()){

            List<String> veaSonumid = vigadeKuulaja.getVeaSonumid();

            for(String s: veaSonumid){
                System.out.println(s);
            }

            throw new RuntimeException("Parsimine ebaõnnestus");
        }

        if (parseTree == null
                || parseTree.getChildCount() == 0
                || parser.getNumberOfSyntaxErrors() != 0
                ) {
            System.out.println(parser.getNumberOfSyntaxErrors());
            throw new RuntimeException("Problem with parsing");
        }

        if (tokens.LA(1) != -1) {
            throw new RuntimeException("Some tokens left after parsing");
        }

        return parseTree;
    }

    public AstNode createAST(ParseTree parseTreeNode, Map<ValemiID, Vaartus> abivalemid, Map<String, Double> väärtustus) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        if (parseTreeNode instanceof PredParser.KoguvalemContext) {

            return createValem(parseTreeNode, abivalemid, väärtustus);

        }

        throw new IllegalStateException();
    }

    private Valem createValem(ParseTree valemContext, Map<ValemiID, Vaartus> abivalemid, Map<String, Double> väärtustus) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        if(valemContext instanceof PredParser.KoguvalemContext) {

            List<Valem> ast = new ArrayList<>();

            for (int i = 0; i < valemContext.getChildCount(); i++) {
                ast.add(createValem(valemContext.getChild(i), abivalemid, väärtustus));
            }

            return new KoguValem(ast);
        }

        else if(valemContext instanceof PredParser.AbidefContext){

            List<Character> argumendid = new ArrayList<>();

            if(valemContext.getChild(0).getChildCount() > 1){ //kui predtähisele järgnevad argumendid
                ParseTree argumentsNode = valemContext.getChild(0).getChild(1);
                for (int i = 1; i < argumentsNode.getChildCount() - 1; i += 2) {
                    argumendid.add(argumentsNode.getChild(i).getChild(0).getText().charAt(0));
                }
            }

            Valem valem = createValem(valemContext.getChild(2), abivalemid, väärtustus);

            ValemiID id = new ValemiID(valemContext.getChild(0).getChild(0).getChild(0).getText(), argumendid.size());

            Vaartus vaartus = new Vaartus(valem, argumendid);

            AbiValem abivalem = new AbiValem(id, argumendid, valem);

            if(!abivalem.vabadeMuutujateEsinemineKorrektne()){
                throw new VaarVabadeMuutujateEsinemine();
            }

            abivalemid.put(id, vaartus);

            return abivalem;
        }

        else if(valemContext instanceof PredParser.PredvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, väärtustus);

            }
            else{
                List<Valem> alamValemid = new ArrayList<>();
                for(int i = 0; i < valemContext.getChildCount(); i += 2){
                    Valem ekv = createValem(valemContext.getChild(i), abivalemid, väärtustus);
                    alamValemid.add(ekv);
                }

                return Ekvivalents.looEkvivalentsid(alamValemid);
                //return new PredValem(alamValemid);
            }
        }

        else if(valemContext instanceof PredParser.EkvvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, väärtustus);
            }
            else{
                List<Valem> alamValemid = new ArrayList<>();
                for(int i = 0; i < valemContext.getChildCount(); i += 2){
                    Valem ekv = createValem(valemContext.getChild(i), abivalemid, väärtustus);
                    alamValemid.add(ekv);
                }
                return Implikatsioon.looImplikatsioonid(alamValemid);
                //return Ekvivalents.looEkvivalentsid(alamValemid);
            }
        }

        else if(valemContext instanceof PredParser.ImplvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, väärtustus);
            }
            else{
                List<Valem> alamValemid = new ArrayList<>();
                for(int i = 0; i < valemContext.getChildCount(); i += 2){
                    Valem ekv = createValem(valemContext.getChild(i), abivalemid, väärtustus);
                    alamValemid.add(ekv);
                }
                return Disjunktsioon.looDisjunktsioonid(alamValemid);
                //return Implikatsioon.looImplikatsioonid(alamValemid);
            }
        }

        else if(valemContext instanceof PredParser.DisjvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, väärtustus);

            }

            List<Valem> alamValemid = new ArrayList<>();
            for(int i = 0; i < valemContext.getChildCount(); i += 2){
                Valem ekv = createValem(valemContext.getChild(i), abivalemid, väärtustus);
                alamValemid.add(ekv);
            }

            return Konjuktsioon.looKonjuktsioonid(alamValemid);
            //return Disjunktsioon.looDisjunktsioonid(alamValemid);
        }

        else if(valemContext instanceof PredParser.KonjvalemContext){

            if(valemContext.getChildCount() == 1){
                return createValem(valemContext.getChild(0), abivalemid, väärtustus);
            }

            List<Modifier> kvantorid = new ArrayList<>();
            boolean eitus = false;

            for(int i = 0; i < valemContext.getChildCount()-1; i++){
                ParseTree vaadeldavLaps = valemContext.getChild(i);
                if(vaadeldavLaps instanceof PredParser.IgaContext){
                    kvantorid.add(new ModifierIga(vaadeldavLaps.getChild(1).getChild(0).getText().charAt(0)));
                }
                else if(vaadeldavLaps instanceof PredParser.EksContext){
                    kvantorid.add(new ModifierEks(vaadeldavLaps.getChild(1).getChild(0).getText().charAt(0)));
                }
                else if(vaadeldavLaps.getText().charAt(0) == '-'){
                    kvantorid.add(new ModifierEitus());
                }
            }

            Valem valem;
            if(valemContext.getChild(valemContext.getChildCount()-1).getChildCount()==1){
                valem = createValem(valemContext.getChild(valemContext.getChildCount()-1).getChild(0), abivalemid, väärtustus);
            }
            else{
                valem = createValem(valemContext.getChild(valemContext.getChildCount()-1).getChild(1), abivalemid, väärtustus); //muuda Valemiks nt ja kaota Korgvalem ära, child 1 et sulud välja jätta
            }

            return looModifierid(kvantorid, valem);

        }

        else if(valemContext instanceof PredParser.KorgvalemContext){
            if(valemContext.getChildCount() == 1){
                return createValem(valemContext.getChild(0), abivalemid, väärtustus);
            }
            return createValem(valemContext.getChild(1), abivalemid, väärtustus); //juhul kui laps on (predvalem)
        }

        else if(valemContext instanceof PredParser.AtomaarnevalemContext){

            if(valemContext.getChild(0) instanceof PredParser.PredsymbolContext){ //predsümboliga variant. Grammatikas: atomaarnevalem1

                String predSümbol = valemContext.getChild(0).getChild(0).getChild(0).getText();

                if(valemContext.getChildCount() == 1){ //ilma vabade muutujateta predsümbol, nt P

                    ValemiID id = new ValemiID(predSümbol, 0);
                    if(abivalemid.containsKey(id)){
                        Valem valem = abivalemid.get(id).getValem();
                        return new AtomaarneValemPredSümboliga(id, valem);
                    }
                    else{
                        throw new AbiValemEiOleDefineeritud(valemContext.toString());
                    }
                }
                else{ //vabade muutujatega predsümbol, nt P(x,y)

                    List<Term> argumendid = new ArrayList<>();
                    ParseTree termargumendid = valemContext.getChild(1);
                    List<TermiPaar> termTahised = new ArrayList<>();

                    for(int i = 1; i < termargumendid.getChildCount()-1; i+=2){
                        argumendid.add(createTerm(termargumendid.getChild(i)));
                    }
                    System.out.println("Termargumendid on " + argumendid);

                    ValemiID id = new ValemiID(predSümbol, argumendid.size());

                    //T(x,y) := Ea(z * a = m); T(1,0)

                    if (abivalemid.containsKey(id)) { //kontrolli kas abivalem on eelnevalt defineeritud
                        Vaartus valemJaArgumendid = abivalemid.get(id);
                        List<Character> argumentideTahised = valemJaArgumendid.getArgumendid(); //abivalemisse salvestatud argumentide tähised
                        for(int i = 0; i < argumentideTahised.size(); i++){
                            termTahised.add(new TermiPaar(argumentideTahised.get(i), argumendid.get(i))); //nt (ÜksTerm, 'x'), millega tuleb vaba muutuja väljakutsel väärtustada
                        }
                        return new AtomaarneValemPredSümboliga(id, termTahised, valemJaArgumendid.getValem());
                    }
                    else{
                        throw new AbiValemEiOleDefineeritud(valemContext.toString());
                    }
                }

                //Valem valem = abivalemid.getOrDefault(id, createValem(valemContext, abivalemid, väärtustus));

            }

            else if(valemContext.getChild(1).getText().charAt(0) == '='){ //grammatikas: atomaarnevalem2

                Term vasakTerm = createTerm(valemContext.getChild(0));

                Term paremTerm = createTerm(valemContext.getChild(2));

                return new AtomaarneValem(vasakTerm, paremTerm);
            }
        }

        throw new UnsupportedOperationException();

    }

    private Valem looModifierid(List<Modifier> kvantorid, Valem valem) {

        if(kvantorid.isEmpty()){
            return valem;
        }
        Modifier mod = kvantorid.remove(0);
        if(mod instanceof ModifierEitus){
            return new Eitus(looModifierid(kvantorid, valem));
        }
        else if(mod instanceof ModifierEks){
            return new Eks(looModifierid(kvantorid,valem), ((ModifierEks) mod).indiviidmuutuja);
        }
        else if(mod instanceof ModifierIga){
            return new Iga(looModifierid(kvantorid, valem), ((ModifierIga) mod).indiviidmuutuja);
        }

        throw new IllegalStateException();
    }


    private Term createTerm(ParseTree termContext) {

        if(termContext instanceof PredParser.TermContext){
            if(termContext.getChildCount() == 1){
                return createTerm(termContext.getChild(0));
            }
            else{
                List<Term> liidetavad = new ArrayList<>();
                for(int i = 0; i < termContext.getChildCount(); i += 2){
                    liidetavad.add(createTerm(termContext.getChild(i)));
                }
                if(termContext.getChild(1).getText().charAt(0) == '+'){
                    return LiitTerm.binaarneLiitmine(liidetavad);
                }
                else{
                    return LahutusTerm.binaarneLahutamine(liidetavad);
                }
            }
        }
        else if(termContext instanceof PredParser.PmtermContext){
            if(termContext.getChildCount() == 1){
                return createTerm(termContext.getChild(0));
            }
            else{
                List<Term> korrutised = new ArrayList<>();
                for(int i = 0; i < termContext.getChildCount(); i += 2){
                    korrutised.add(createTerm(termContext.getChild(i)));
                }
                if(termContext.getChild(1).getText().charAt(0) == '*'){
                    return KorrutisTerm.binaarneKorrutis(korrutised);
                }
                else{
                    return JagamisTerm.binaarneJagatis(korrutised);
                }
            }
        }

        else if(termContext instanceof PredParser.KjtermContext){
            if(termContext.getChildCount() == 1){
                if(termContext.getChild(0).getText().charAt(0) == '0'){
                    return new NullTerm();
                }
                else if(termContext.getChild(0).getText().charAt(0) == '1'){
                    return new ÜksTerm();
                }
                else if(termContext.getChild(0) instanceof PredParser.IndiviidmuutujaContext){
                    return new IndiviidTerm(termContext.getChild(0).getChild(0).getText().charAt(0));
                }
            }
            else{
                return createTerm(termContext.getChild(1));
            }
        }

        throw new IllegalStateException();
    }


    /**
     * Created by siiri on 05/03/17.
     */

}
