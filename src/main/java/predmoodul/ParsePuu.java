package predmoodul;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import predmoodul.erindid.*;
import predmoodul.kvantorid.*;
import predmoodul.termid.*;
import predmoodul.valemid.*;

import java.util.*;


/**
 * Created by siiri on 02/02/17.
 */

public class ParsePuu {

    private String sisend;
    private LekseriVigadeKuulaja lekseriVead;
    private SyntaksiVigadeKuulaja parseriVead;

    public ParsePuu(){}

    public ParsePuu(String tekstilineSisend) {

        this.sisend = tekstilineSisend;
        lekseriVead = new LekseriVigadeKuulaja();
        parseriVead = new SyntaksiVigadeKuulaja();
    }

    public LekseriVigadeKuulaja getLekseriVead() {
        return lekseriVead;
    }

    public SyntaksiVigadeKuulaja getParseriVead() {
        return parseriVead;
    }

    public ParseTree looParsePuu() throws LekseriErind, SyntaksiViga {

        ParseTree parseTree;

        //lexer splits input into tokens
        ANTLRInputStream input = new ANTLRInputStream(sisend); //like a char[] buffer
        PredLexer lexer = new PredLexer(input);
        //LekseriVigadeKuulaja lvk = new LekseriVigadeKuulaja();
        //lexer.addErrorListener(lekseriVead);
        lexer.addErrorListener(parseriVead);

        CommonTokenStream tokens = new CommonTokenStream(lexer); //provides access to all tokens by index
        tokens.fill(); //initiate lexing manually
        List<Token> tokeniteList = tokens.getTokens();

        PredParser parser = new PredParser(tokens);
        //ParseriVigadeKuulaja vigadeKuulaja = new ParseriVigadeKuulaja();
        parser.removeErrorListeners(); //eemaldame konsooli error listener'i, et mitte saada topelt veateateid
        parser.addErrorListener(parseriVead);

        parseTree = parser.koguvalem();

        //System.out.println(parseTree.toStringTree(parser));
        //System.out.println(parser.getNumberOfSyntaxErrors());

        List<String> lekseriVeaSonumid = lekseriVead.getVeaSonumid();
        if(!lekseriVeaSonumid.isEmpty()){
            prindiVeasonumid(lekseriVeaSonumid);
            throw new LekseriErind(lekseriVead);}

        List<String> parseriVeaSonumid = parseriVead.getVeaSonumid();
        if(!parseriVeaSonumid.isEmpty()){
            prindiVeasonumid(parseriVeaSonumid);
            throw new SyntaksiViga(parseriVead);
        }

        if (parseTree == null || parseTree.getChildCount() == 0) {
            //System.out.println(parser.getNumberOfSyntaxErrors());
            throw new RuntimeException("Parsimine ebaõnnestus");
        }
        if (tokens.LA(1) != -1) {
            throw new RuntimeException("Kõiki märgendeid ei parsitud");
        }

        return parseTree;
    }

    public void prindiVeasonumid(List<String> vead){

        if(!vead.isEmpty()){
            System.out.println(vead.get(0));
        }

    }
    
    public AstNode createAST(ParseTree parseTreeNode, Map<ValemiID, Vaartus> abivalemid) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        if (parseTreeNode instanceof PredParser.KoguvalemContext) {

            return createValem(parseTreeNode, abivalemid, null);

        }

        throw new IllegalStateException();
    }

    private Valem createValem(ParseTree valemContext, Map<ValemiID, Vaartus> abivalemid, String abiPredikaadiNimi) throws VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        //String abiPredikaadiNimi = "";

        if(valemContext instanceof PredParser.KoguvalemContext) {

            List<Valem> ast = new ArrayList<>();

            for (int i = 0; i < valemContext.getChildCount(); i++) {
                ast.add(createValem(valemContext.getChild(i), abivalemid, abiPredikaadiNimi));
            }

            return new KoguValem(ast);
        }

        else if(valemContext instanceof PredParser.AbidefContext){


            abiPredikaadiNimi = parsiAbiPredikaadiNimi(valemContext.getChild(0));

            Valem valem = createValem(valemContext.getChild(2), abivalemid, abiPredikaadiNimi);

            List<Muutuja> argumendid = new ArrayList<>(); //enne Character

            if(valemContext.getChild(0).getChildCount() > 1){ //kui predtähisele järgnevad argumendid
                ParseTree argumentsNode = valemContext.getChild(0).getChild(1);
                for (int i = 1; i < argumentsNode.getChildCount() - 1; i += 2) {
                    argumendid.add(new Muutuja(argumentsNode.getChild(i).getChild(0).getText().charAt(0), abiPredikaadiNimi));
                }
            }

            for(Muutuja muutuja : argumendid){
                valem.uusKonstantSumbol(new Muutuja(muutuja.getTahis(), abiPredikaadiNimi), muutuja); //vahetaKvantoriSees: false
                muutuja.setPredTahis(abiPredikaadiNimi);
            }

            System.out.println("Abidefinitsiooni valemis on seotud muutujad: " + valem.getSeotudMuutujad());

            Set<Muutuja> abiDefValemiSeotudMuutujad = valem.getSeotudMuutujad();

            /*
            for(Muutuja seotudMuutuja : abiDefValemiSeotudMuutujad){
                valem.uusKonstantSumbol(new Muutuja(seotudMuutuja.getTahis(), abiPredikaadiNimi), seotudMuutuja, true);
                seotudMuutuja.setPredTahis(abiPredikaadiNimi);
            }
            */

            System.out.println("Abidef valem: " + valem);
            System.out.println("Seotud muutujad pärast predtähisega sidumist: " + valem.getSeotudMuutujad());

            //ValemiID id = new ValemiID(valemContext.getChild(0).getChild(0).getChild(0).getText(), argumendid.size());
            ValemiID id = new ValemiID(abiPredikaadiNimi.toString(), argumendid.size());
            Vaartus vaartus = new Vaartus(valem, argumendid);

            AbiValem abivalem = new AbiValem(id, argumendid, valem);

            if(!abivalem.vabadeMuutujateEsinemineKorrektne()){
                throw new VaarVabadeMuutujateEsinemine(abivalem.getTahisestPuuduvadValemiVabadMuutujad(), abivalem.getValemistPuuduvadTahiseMuutujad());
            }

            abivalemid.put(id, vaartus);

            return abivalem;
        }

        else if(valemContext instanceof PredParser.PredvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, abiPredikaadiNimi);

            }
            else{
                List<Valem> alamValemid = new ArrayList<>();
                for(int i = 0; i < valemContext.getChildCount(); i += 2){
                    Valem ekv = createValem(valemContext.getChild(i), abivalemid, abiPredikaadiNimi);
                    alamValemid.add(ekv);
                }

                return Ekvivalents.looEkvivalentsid(alamValemid);
            }
        }

        else if(valemContext instanceof PredParser.EkvvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, abiPredikaadiNimi);
            }
            else{
                List<Valem> alamValemid = new ArrayList<>();
                for(int i = 0; i < valemContext.getChildCount(); i += 2){
                    Valem ekv = createValem(valemContext.getChild(i), abivalemid, abiPredikaadiNimi);
                    alamValemid.add(ekv);
                }
                return Implikatsioon.looImplikatsioonid(alamValemid);
            }
        }

        else if(valemContext instanceof PredParser.ImplvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, abiPredikaadiNimi);
            }
            else{
                List<Valem> alamValemid = new ArrayList<>();
                for(int i = 0; i < valemContext.getChildCount(); i += 2){
                    Valem ekv = createValem(valemContext.getChild(i), abivalemid, abiPredikaadiNimi);
                    alamValemid.add(ekv);
                }
                return Disjunktsioon.looDisjunktsioonid(alamValemid);
            }
        }

        else if(valemContext instanceof PredParser.DisjvalemContext){

            if(valemContext.getChildCount() == 1){

                return createValem(valemContext.getChild(0), abivalemid, abiPredikaadiNimi);

            }

            List<Valem> alamValemid = new ArrayList<>();
            for(int i = 0; i < valemContext.getChildCount(); i += 2){
                Valem ekv = createValem(valemContext.getChild(i), abivalemid, abiPredikaadiNimi);
                alamValemid.add(ekv);
            }

            return Konjuktsioon.looKonjuktsioonid(alamValemid);
        }

        else if(valemContext instanceof PredParser.KonjvalemContext){

            if(valemContext.getChildCount() == 1){
                return createValem(valemContext.getChild(0), abivalemid, abiPredikaadiNimi);
            }

            List<Modifier> kvantorid = new ArrayList<>();
            boolean eitus = false;

            for(int i = 0; i < valemContext.getChildCount()-1; i++){
                ParseTree vaadeldavLaps = valemContext.getChild(i);
                if(vaadeldavLaps instanceof PredParser.IgaContext){
                    kvantorid.add(new ModifierIga(new Muutuja(vaadeldavLaps.getChild(1).getChild(0).getText().charAt(0), abiPredikaadiNimi)));
                }
                else if(vaadeldavLaps instanceof PredParser.EksContext){
                    kvantorid.add(new ModifierEks(new Muutuja(vaadeldavLaps.getChild(1).getChild(0).getText().charAt(0), abiPredikaadiNimi)));
                }
                else if(vaadeldavLaps.getText().charAt(0) == '¬'){
                    kvantorid.add(new ModifierEitus());
                }
            }

            Valem valem;
            if(valemContext.getChild(valemContext.getChildCount()-1).getChildCount()==1){
                valem = createValem(valemContext.getChild(valemContext.getChildCount()-1).getChild(0), abivalemid, abiPredikaadiNimi);
            }
            else{
                valem = createValem(valemContext.getChild(valemContext.getChildCount()-1).getChild(1), abivalemid, abiPredikaadiNimi); //muuda Valemiks nt ja kaota Korgvalem ära, child 1 et sulud välja jätta
            }

            return looModifierid(kvantorid, valem);

        }

        else if(valemContext instanceof PredParser.KorgvalemContext){
            if(valemContext.getChildCount() == 1){
                return createValem(valemContext.getChild(0), abivalemid, abiPredikaadiNimi);
            }
            return createValem(valemContext.getChild(1), abivalemid, abiPredikaadiNimi); //juhul kui laps on (predvalem)
        }

        else if(valemContext instanceof PredParser.AtomaarnevalemContext){

            if(valemContext.getChild(0) instanceof PredParser.PredsymbolContext){ //predsümboliga variant. Grammatikas: atomaarnevalem1

                String predSümbol = parsiAbiPredikaadiNimi(valemContext);
                //valemContext.getChild(0).getChild(0).getChild(0).getText();

                if(valemContext.getChildCount() == 1){ //ilma vabade muutujateta predsümbol, nt P

                    ValemiID id = new ValemiID(predSümbol.toString(), 0);
                    if(abivalemid.containsKey(id)){
                        Valem valem = abivalemid.get(id).getValem();
                        return new AtomaarneValemPredSümboliga(id, valem);
                    }
                    else{
                        throw new AbiValemEiOleDefineeritud(id.getPredSümbol(), id.getArgumentideArv());
                    }
                }
                else{ //vabade muutujatega predsümbol, nt P(x,y)

                    List<Term> argumendid = new ArrayList<>();
                    ParseTree termargumendid = valemContext.getChild(1);
                    List<TermiPaar> termTahised = new ArrayList<>();

                    for(int i = 1; i < termargumendid.getChildCount()-1; i+=2){
                        argumendid.add(createTerm(termargumendid.getChild(i), abiPredikaadiNimi));
                    }
                    System.out.println("Termargumendid on " + argumendid);

                    ValemiID id = new ValemiID(predSümbol.toString(), argumendid.size());

                    //T(x,y) := Ea(z * a = m); T(1,0)
                    if (abivalemid.containsKey(id)) { //kontrolli kas sama predikaatsümboliga ja argumentide arvuga abivalem on eelnevalt defineeritud
                        Vaartus valemJaArgumendid = abivalemid.get(id); //Tüüpi Valem ja List<Character>
                        Valem valemiKoopia = valemJaArgumendid.getValem().koopia();
                        List<Muutuja> abiValemiArgumendid = valemJaArgumendid.getArgumendid(); //abivalemisse salvestatud argumentide tähised
                        for(int i = 0; i < abiValemiArgumendid.size(); i++){
                            //termTahised.add(new TermiPaar(argumentideTahised.get(i), argumendid.get(i)));
                            //nt ('x', ÜksTerm), millega tuleb vaba muutuja väljakutsel väärtustada
                            Muutuja argumendiTahis = abiValemiArgumendid.get(i);
                            valemiKoopia.asendaTerm(argumendid.get(i),
                                    x -> x instanceof IndiviidTerm && argumendiTahis.equals(x.getTahis()));

                            //termTahised.add(new TermiPaar(argumendid.get(i).getTahis(), argumendid.get(i)));
                            //valemJaArgumendid.getValem().uusKonstantSumbol(argumendid.get(i).getTahis(), argumentideTahised.get(i));

                        }
                        return new AtomaarneValemPredSümboliga(id, termTahised, valemiKoopia);
                    }
                    else{
                        throw new AbiValemEiOleDefineeritud(id.getPredSümbol(), id.getArgumentideArv());
                    }
                }

                //Valem valem = abivalemid.getOrDefault(id, createValem(valemContext, abivalemid, väärtustus));

            }

            else if(valemContext.getChild(1).getText().charAt(0) == '='){ //grammatikas: atomaarnevalem2

                Term vasakTerm = createTerm(valemContext.getChild(0), abiPredikaadiNimi);

                Term paremTerm = createTerm(valemContext.getChild(2), abiPredikaadiNimi);

                return new AtomaarneValem(vasakTerm, paremTerm);
            }
        }

        throw new UnsupportedOperationException();

    }

    private String parsiAbiPredikaadiNimi(ParseTree valemContext) {

        ParseTree nimeID = valemContext.getChild(0).getChild(0);
        StringBuilder abiPredikaadiNimi = new StringBuilder();

        if(nimeID.getChildCount() > 1){
            for(int i = 0; i < nimeID.getChildCount(); i++){
                abiPredikaadiNimi.append(nimeID.getChild(i).getText());
            }
        }
        else{
            abiPredikaadiNimi.append(nimeID.getText());
        }

        return abiPredikaadiNimi.toString();
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


    private Term createTerm(ParseTree termContext, String abiPredikaadiNimi) {

        if(termContext instanceof PredParser.TermContext){
            if(termContext.getChildCount() == 1){
                return createTerm(termContext.getChild(0), abiPredikaadiNimi);
            }
            else{
                List<Term> liidetavad = new ArrayList<>();
                for(int i = 0; i < termContext.getChildCount(); i += 2){
                    liidetavad.add(createTerm(termContext.getChild(i), abiPredikaadiNimi));
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
                return createTerm(termContext.getChild(0), abiPredikaadiNimi);
            }
            else{
                List<Term> korrutised = new ArrayList<>();
                for(int i = 0; i < termContext.getChildCount(); i += 2){
                    korrutised.add(createTerm(termContext.getChild(i), abiPredikaadiNimi));
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
                    return new IndiviidTerm(new Muutuja(termContext.getChild(0).getChild(0).getText().charAt(0), abiPredikaadiNimi));
                }
            }
            else{
                return createTerm(termContext.getChild(1), abiPredikaadiNimi);
            }
        }

        throw new IllegalStateException();
    }


    /**
     * Created by siiri on 05/03/17.
     */

}
