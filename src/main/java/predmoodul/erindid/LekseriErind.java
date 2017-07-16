package predmoodul.erindid;

/**
 * Created by siiri on 30/06/17.
 */
public class LekseriErind extends Throwable {

    private LekseriVigadeKuulaja kuulaja;

    public LekseriErind(LekseriVigadeKuulaja lvk){
        this.kuulaja = lvk;
    }

    public LekseriVigadeKuulaja getLekseriViagdeKuulaja(){

        return kuulaja;
    }


}
