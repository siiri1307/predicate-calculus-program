package predmoodul.erindid;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siiri on 26/06/17.
 */
public class LekseriVigadeKuulaja extends BaseErrorListener {

    private List<String> veaSonumid;
    private List<Integer> veaPosNumbrid;


    public LekseriVigadeKuulaja(){
        veaSonumid = new ArrayList<>();
        veaPosNumbrid = new ArrayList<>();
    }

    public List<String> getVeaSonumid() {
        return veaSonumid;
    }

    public List<Integer> getVeaPosNumbrid() {
        return veaPosNumbrid;
    }

    public boolean onLekseriVigu(){

        return veaSonumid.size() > 0;
    }

    @Override
    public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {

        StringBuilder viga = new StringBuilder();

        if(msg.contains("token recognition error")){
            viga.append("Positsioonil " + charPositionInLine + " on lubamatu sümbol " + msg.split(":")[1].trim() + ".");
        }

        veaSonumid.add(viga.toString());
        veaPosNumbrid.add(charPositionInLine);
    }
}
