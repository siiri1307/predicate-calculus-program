package predmoodul.erindid;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siiri on 26/06/17.
 */
public class ParseriVigadeKuulaja extends BaseErrorListener {

    private List<String> veaSonumid;
    private List<Integer> veaPosNumbrid;


    public ParseriVigadeKuulaja(){
        veaSonumid = new ArrayList<>();
        veaPosNumbrid = new ArrayList<>();
    }

    public List<String> getVeaSonumid() {
        return veaSonumid;
    }

    public List<Integer> getVeaPosNumbrid() {
        return veaPosNumbrid;
    }

    public boolean sisendisOnVigu(){

        return veaSonumid.size() > 0;
    }

    @Override
    public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e){

        String[] tykeldatudSonum;
        StringBuilder viga = new StringBuilder();

        if(msg.contains("missing")){
            tykeldatudSonum = msg.split("missing");;
            String[] sonumiOsad = tykeldatudSonum[1].trim().split(" ");
            viga.append("Positsioonil " + charPositionInLine + " puudub " + sonumiOsad[0] + ".");
        }
        else if(msg.contains("extraneous input")){
            viga.append("Positsioonil " + charPositionInLine + " on üleliigne " + msg.split("extraneous input")[1].trim().split(" ")[0] + ".");
        }
        else if(msg.contains("token recognition error")){
            viga.append("Positsioonil " + charPositionInLine + " on lubamatu sümbol " + msg.split(":")[1].trim() + ".");
        }
        else{
            String[] tykeldatudVordusMargilt = offendingSymbol.toString().split("=");
            String[] vordusestParemal = tykeldatudVordusMargilt[1].split(",");
            if(vordusestParemal[0].equals("'<EOF>'")){
                viga.append("Valemi lõpust puudub sulg.");
                return;
            }
            viga.append("Positsioonil " + charPositionInLine + " on ");
            viga.append("lubamatu sümbol " + vordusestParemal[0] + ".");
            //viga.append("sonum " + msg);
            if(msg.contains("expecting")){
                tykeldatudSonum = msg.split("expecting");
                viga.append("Ootasin üht järgnevast: " + tykeldatudSonum[1]);
            }
        }

        veaSonumid.add(viga.toString());
        veaPosNumbrid.add(charPositionInLine);
    }

}
