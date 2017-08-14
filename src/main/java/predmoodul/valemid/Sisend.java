package predmoodul.valemid;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import predmoodul.ParsePuu;
import predmoodul.PredBaseListener;
import predmoodul.PredLexer;
import predmoodul.PredParser;
import predmoodul.erindid.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by siiri on 01/08/17.
 */
public class Sisend {

    private String oigeVastus;
    private String pakkumine;
    private SyntaksiVigadeKuulaja parseriVead;

    private ParseTree pakkumiseParsePuu;
    private ParseTree oigeParsePuu;

    private Valem pakkumiseValem;
    private Valem oigeValem;

    public Sisend(String pakkumine, String oige) throws SyntaksiViga, IOException, VaarVabadeMuutujateEsinemine, AbiValemEiOleDefineeritud {

        this.oigeVastus = oige;
        this.pakkumine = pakkumine;
        parseriVead = new SyntaksiVigadeKuulaja();

        pakkumiseParsePuu = antlriParser(pakkumine);
        asendaOigesVastusesPredtahised();
        oigeParsePuu = antlriParser(oige);

        AstNode pakkumiseASP = new ParsePuu().createAST(pakkumiseParsePuu, new HashMap<>());
        pakkumiseValem = (Valem) (pakkumiseASP.getChildren().get(pakkumiseASP.getChildren().size()-1));

        AstNode oigeASP = new ParsePuu().createAST(oigeParsePuu, new HashMap<>());
        oigeValem = (Valem) (oigeASP.getChildren().get(oigeASP.getChildren().size()-1));
    }

    public SyntaksiVigadeKuulaja getParseriVead() {
        return parseriVead;
    }

    public ParseTree getPakkumiseParsePuu() {
        return pakkumiseParsePuu;
    }

    public ParseTree getOigeParsePuu() {
        return oigeParsePuu;
    }

    public Valem getPakkumiseValem() {
        return pakkumiseValem;
    }

    public Valem getOigeValem() {
        return oigeValem;
    }

    public String getOigeVastus() {
        return oigeVastus;
    }

    public String getPakkumine() {
        return pakkumine;
    }


    public ParseTree antlriParser(String tekst) throws SyntaksiViga {

        ANTLRInputStream input = new ANTLRInputStream(tekst);

        PredLexer lexer = new PredLexer(input);

        lexer.addErrorListener(parseriVead);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PredParser parser = new PredParser(tokens);

        parser.removeErrorListeners();

        parser.addErrorListener(parseriVead);

        ParseTree sisendiParsePuu = parser.koguvalem();

        List<String> parseriVeaSonumid = parseriVead.getVeaSonumid();

        if(!parseriVeaSonumid.isEmpty()){
            prindiVeasonumid(parseriVeaSonumid);
            throw new SyntaksiViga(parseriVead);
        }

        return sisendiParsePuu;
    }

    public List<String> getAbidefinitsioonid(ParseTree parsepuu) throws IOException {

        ParseTreeWalker walker = new ParseTreeWalker();

        AbidefinitsiooniKuulaja adk = new AbidefinitsiooniKuulaja();

        walker.walk(adk, parsepuu);

        return adk.getAbidefinitsioonid();
    }

    public List<String> getAbidefinitsiooniTahised(ParseTree parsepuu) throws IOException {

        ParseTreeWalker walker = new ParseTreeWalker();

        AbidefinitsiooniKuulaja adk = new AbidefinitsiooniKuulaja();

        walker.walk(adk, parsepuu);

        return adk.getAbidefinitsiooniTahised();
    }

    public List<String> getAtomaarseteValemiteTahised(ParseTree parsepuu) throws IOException {

        ParseTreeWalker walker = new ParseTreeWalker();

        AtomaarsevalemiKuulaja adk = new AtomaarsevalemiKuulaja();

        walker.walk(adk, parsepuu);

        return adk.getAtomaarseteValemitePredsymbolid();
    }

    public void liigutaOigeAbidefinitsioonidVasakule() throws IOException, SyntaksiViga {

        List<String> abidefinitsioonid = getAbidefinitsioonid(antlriParser(oigeVastus));

        for(String abidef : abidefinitsioonid){

            oigeVastus = oigeVastus.replaceAll(" ", "").replace(abidef, "");
            StringBuilder sb = new StringBuilder();
            sb.append(abidef);
            sb.append(pakkumine);
            sb.append(" ");
            pakkumine = sb.toString().replaceAll(" ", "");
        }
    }

    public void asendaOigesVastusesPredtahised() throws IOException, SyntaksiViga {

        long aeg = System.currentTimeMillis() % 1000;
        String aegSonena = Long.toString(aeg).replaceAll("0","2");
        aegSonena = aegSonena.replaceAll("1", "3");


        List<String> abidefinitsioonid = getAbidefinitsioonid(antlriParser(oigeVastus));

        List<String> atomaarsed = getAtomaarseteValemiteTahised(antlriParser(oigeVastus));

        for(String abidef : abidefinitsioonid){
            String oigeVastusTuhikuteta = oigeVastus.replaceAll(" ", "");
            oigeVastus = oigeVastusTuhikuteta.replace(abidef, "V" + aegSonena + abidef);
        }

        for(String atomValem : atomaarsed){
            oigeVastus = oigeVastus.replaceAll(" ", "");
            oigeVastus = oigeVastus.replace(atomValem, "V" + aegSonena + atomValem);
        }

    }

    public String getEkvivalentsSonena() throws IOException, LekseriErind, SyntaksiViga {

        ParsePuu parsePuuPakkumine = new ParsePuu(pakkumine);

        parsePuuPakkumine.looParsePuu(); //kontrollib String sisendit süntaktiliselt ja loob parsepuu

        ParsePuu parsePuuOige = new ParsePuu(oigeVastus);

        parsePuuOige.looParsePuu();

        asendaOigesVastusesPredtahised();

        liigutaOigeAbidefinitsioonidVasakule();

        return pakkumine + "~" + oigeVastus;

    }

    private void prindiVeasonumid(List<String> vead){

        if(!vead.isEmpty()){
            System.out.println(vead.get(0));
        }
    }

    private class AbidefinitsiooniKuulaja extends PredBaseListener {

        private List<String> abidefinitsioonid = new ArrayList<>();
        private List<String> abidefinitsiooniTahised = new ArrayList<>();


        @Override
        public void enterAbidef(PredParser.AbidefContext ctx){

            abidefinitsioonid.add(ctx.getText());
            abidefinitsiooniTahised.add(ctx.predtahis().getText());
        }

        public List<String> getAbidefinitsioonid() {
            return abidefinitsioonid;
        }

        public List<String> getAbidefinitsiooniTahised(){
            return abidefinitsiooniTahised;
        }

    }

    private class AtomaarsevalemiKuulaja extends PredBaseListener {

        private List<String> atomaarseteValemitePredsymbolid = new ArrayList<>();

        @Override
        public void enterAtomaarnevalem(PredParser.AtomaarnevalemContext ctx){
            if(ctx.predsymbol() != null){
                atomaarseteValemitePredsymbolid.add(ctx.getText());
            }
        }

        public List<String> getAtomaarseteValemitePredsymbolid() {
            return atomaarseteValemitePredsymbolid;
        }
    }

    @Override
    public String toString() {
        return "Õige vastus:" + oigeVastus + '\n' +
                "Pakkumine:" + pakkumine + '\n';
    }
}