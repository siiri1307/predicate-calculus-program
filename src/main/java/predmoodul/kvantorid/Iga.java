package predmoodul.kvantorid;

/**
 * Created by siiri on 09/03/17.
 */
public class Iga implements Kvantor {

    private Character indiviidmuutuja;

    public Iga(Character indiviidmuutuja){
        this.indiviidmuutuja = indiviidmuutuja;
    }

    @Override
    public String toString() {
        return "Iga{" +
                "indiviidmuutuja=" + indiviidmuutuja +
                '}';
    }

    @Override
    public Character getIndiviidMuutuja() {
        return this.indiviidmuutuja;
    }

    @Override
    public boolean lõpetamiseTingimus() {
        return false;
    }
}
