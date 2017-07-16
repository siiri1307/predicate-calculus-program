package predmoodul.erindid;

/**
 * Created by siiri on 25/03/17.
 */
public class AbiValemEiOleDefineeritud extends Throwable {

    private String predSymbol;

    public AbiValemEiOleDefineeritud(String symbol) {
        this.predSymbol = symbol;
    }


    @Override
    public String getMessage(){
        return "Abivalem predikaatsümboliga " + predSymbol + " ei ole defineeritud.";
    }
}
