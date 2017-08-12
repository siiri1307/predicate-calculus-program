package predmoodul.erindid;

/**
 * Created by siiri on 30/06/17.
 */
public class ParseriErind extends Throwable {

    private ParseriVigadeKuulaja kuulaja;

    public ParseriErind(ParseriVigadeKuulaja pvk){
        this.kuulaja = pvk;
    }

    public ParseriVigadeKuulaja getParseriVigadeKuulaja(){

        return kuulaja;
    }
}
