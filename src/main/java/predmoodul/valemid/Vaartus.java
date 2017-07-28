package predmoodul.valemid;

import java.util.List;

/**
 * Created by siiri on 22/03/17.
 */
public class Vaartus {

    private Valem valem;
    private List<Muutuja> argumendid;

    public Vaartus(Valem valem, List<Muutuja> argumendid) {
        this.valem = valem;
        this.argumendid = argumendid;
    }

    public Valem getValem() {
        return valem;
    }

    public List<Muutuja> getArgumendid() {
        return argumendid;
    }
}
