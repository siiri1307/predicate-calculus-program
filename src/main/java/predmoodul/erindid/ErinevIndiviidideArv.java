package predmoodul.erindid;

import predmoodul.valemid.Muutuja;

import java.util.Set;

/**
 * Created by siiri on 28/06/17.
 */
public class ErinevIndiviidideArv extends Throwable {

    private String sonum;

    public ErinevIndiviidideArv(int indiviidideArvPakkumine, int indiviidideArvLahendus, Set<Muutuja> vabad){

        String indiviididSonum;
        if(vabad.size() == 1){
            indiviididSonum = "kus indiviid " + vabad.iterator().next().getTahis() + " esineb vabalt.";
        }
        else{
            StringBuilder indiviidideSB = new StringBuilder();
            vabad.forEach(x -> indiviidideSB.append(x.getTahis() + ", "));
            String indiviidideString = indiviidideSB.toString();
            int viimaneKoma = indiviidideString.lastIndexOf(", ");
            indiviididSonum = "kus indiviidid " + indiviidideString.substring(0, viimaneKoma) + " esinevad vabalt.";
        }

        this.sonum = "Esitasid " + indiviidideArvPakkumine + "-kohalise predikaadi, ootasin aga " + indiviidideArvLahendus + "-kohalist predikaati, " + indiviididSonum;
    }

    public ErinevIndiviidideArv(Muutuja m){
        this.sonum = m.getTahis() + " peab esinema valemis vabalt.";

    }

    @Override
    public String getMessage(){
        return sonum;
    }
}
