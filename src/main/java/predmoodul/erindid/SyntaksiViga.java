package predmoodul.erindid;

import java.util.List;

/**
 * Created by siiri on 30/06/17.
 */
public class SyntaksiViga extends Throwable {

    private SyntaksiVigadeKuulaja kuulaja;
    private String sonum;

    public SyntaksiViga(SyntaksiVigadeKuulaja pvk){

        this.kuulaja = pvk;
        List<String> vead = kuulaja.getVeaSonumid();
        if(vead.size() != 0){
            sonum = vead.get(0);
        }
    }

    public SyntaksiVigadeKuulaja getParseriVigadeKuulaja(){

        return kuulaja;
    }

    @Override
    public String getMessage(){
        return sonum;
    }
}
