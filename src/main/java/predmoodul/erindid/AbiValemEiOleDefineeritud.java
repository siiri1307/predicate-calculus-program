package predmoodul.erindid;

/**
 * Created by siiri on 25/03/17.
 */
public class AbiValemEiOleDefineeritud extends Throwable {

    private String sonum;


    public AbiValemEiOleDefineeritud(String symbol, int argumentideArv) {
       this.sonum = argumentideArv + "-kohaline abipredikaat predikaatsümboliga " + symbol + " ei ole defineeritud.";
    }

    @Override
    public String getMessage(){
        return sonum;
    }
}
