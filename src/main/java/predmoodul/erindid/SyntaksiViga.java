package predmoodul.erindid;

/**
 * Created by siiri on 30/06/17.
 */
public class SyntaksiViga extends Throwable {

    private SyntaksiVigadeKuulaja kuulaja;

    public SyntaksiViga(SyntaksiVigadeKuulaja pvk){
        this.kuulaja = pvk;
    }

    public SyntaksiVigadeKuulaja getParseriVigadeKuulaja(){

        return kuulaja;
    }
}
