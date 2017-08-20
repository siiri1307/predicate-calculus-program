package predmoodul.erindid;

import predmoodul.valemid.Muutuja;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by siiri on 24/03/17.
 */
public class VaarVabadeMuutujateEsinemine extends Throwable {

    private String sonum;

    public VaarVabadeMuutujateEsinemine(Set<Muutuja> tahisestPuuduvadValemiVabadMuutujad, Set<Muutuja> valemistPuuduvadTahiseMuutujad){

        Set<Character> tahisestPuuduvadMuutujad = new TreeSet<>();
        Set<Character> predikaadistPuuduvadMuutujad = new TreeSet<>();

        tahisestPuuduvadValemiVabadMuutujad.forEach(x -> tahisestPuuduvadMuutujad.add(x.getTahis()));
        valemistPuuduvadTahiseMuutujad.forEach(x -> predikaadistPuuduvadMuutujad.add(x.getTahis()));

        String sonum1 = "";
        String sonum2 = "";

        if(!valemistPuuduvadTahiseMuutujad.isEmpty()){
            if(valemistPuuduvadTahiseMuutujad.size() == 1){
                sonum1 =  "Abipredikaadi definitsiooni kohaselt on muutuja " + valemistPuuduvadTahiseMuutujad.iterator().next().getTahis() +
                        " vaba, kuid abipredikaadis esineb see seotuna või on puudu. ";
            }
            else if(valemistPuuduvadTahiseMuutujad.size() > 1){
                StringBuilder tahisesVabad = new StringBuilder();
                predikaadistPuuduvadMuutujad.forEach(x -> tahisesVabad.append(x + ", "));
                String tahisesVabadSt = tahisesVabad.toString();
                int viimaneKoma = tahisesVabadSt.lastIndexOf(", ");
                sonum1 = "Abipredikaadi definitsiooni kohaselt on muutujad " + tahisesVabadSt.substring(0, viimaneKoma) + " vabad, kuid abipredikaadis esinevad need seotuna või on puudu. ";
            }
        }
        if(!tahisestPuuduvadValemiVabadMuutujad.isEmpty()){
            if(tahisestPuuduvadValemiVabadMuutujad.size() == 1){
                sonum2 = "Abipredikaadis esineb vabana muutuja " + tahisestPuuduvadValemiVabadMuutujad.iterator().next().getTahis() +
                        ", mida ei ole abipredikaadi tähises kirjas.";
            }
            else if(tahisestPuuduvadValemiVabadMuutujad.size() > 1){
                StringBuilder vabad = new StringBuilder();
                tahisestPuuduvadMuutujad.forEach(x -> vabad.append(x + ", "));
                String vabadSt = vabad.toString();
                int viimaneKoma = vabadSt.lastIndexOf(", ");
                sonum2 = "Abipredikaadis esinevad vabana muutujad " + vabadSt.substring(0, viimaneKoma) +
                        ", mida ei ole abipredikaadi tähises kirjas.";;
            }
        }

        this.sonum = sonum1 + sonum2;
    }

    @Override
    public String getMessage(){
        return sonum;
    }

}
