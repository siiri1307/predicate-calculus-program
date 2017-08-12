package predmoodul.valemid;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import predmoodul.ParsePuu;
import predmoodul.PredBaseListener;
import predmoodul.PredLexer;
import predmoodul.PredParser;
import predmoodul.erindid.LekseriErind;
import predmoodul.erindid.SyntaksiViga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siiri on 01/08/17.
 */
public class Sisend {

    private String oigeVastus;
    private String pakkumine;

    public Sisend(String pakkumine, String oige) {
        this.oigeVastus = oige;
        this.pakkumine = pakkumine;
    }

    public String getOigeVastus() {
        return oigeVastus;
    }

    public String getPakkumine() {
        return pakkumine;
    }


    public ParseTree antlriParser(String tekst){

        ANTLRInputStream input = new ANTLRInputStream(tekst);

        PredLexer lexer = new PredLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PredParser parser = new PredParser(tokens);

        ParseTree sisendiParsePuu = parser.koguvalem();

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

    public void liigutaOigeAbidefinitsioonidVasakule() throws IOException {

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

    public void asendaOigesVastusesPredtahised() throws IOException {

        long aeg = System.currentTimeMillis() % 1000;

        List<String> abidefinitsioonid = getAbidefinitsioonid(antlriParser(oigeVastus));

        List<String> atomaarsed = getAtomaarseteValemiteTahised(antlriParser(oigeVastus));

        for(String abidef : abidefinitsioonid){
            String oigeVastusTuhikuteta = oigeVastus.replaceAll(" ", "");
            oigeVastus = oigeVastusTuhikuteta.replace(abidef, "V" + Long.toString(aeg) + abidef);
        }

        for(String atomValem : atomaarsed){
            oigeVastus = oigeVastus.replaceAll(" ", "");
            oigeVastus = oigeVastus.replace(atomValem, "V" + Long.toString(aeg) + atomValem);
        }

    }

    public String getEkvivalentsSonena() throws IOException, LekseriErind, SyntaksiViga {

        ParsePuu parsePuuPakkumine = new ParsePuu(pakkumine);

        parsePuuPakkumine.looParsePuu();

        ParsePuu parsePuuOige = new ParsePuu(oigeVastus);

        parsePuuOige.looParsePuu();

        asendaOigesVastusesPredtahised();

        liigutaOigeAbidefinitsioonidVasakule();

        return pakkumine + "~" + oigeVastus;

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