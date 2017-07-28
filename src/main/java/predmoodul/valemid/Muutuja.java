package predmoodul.valemid;

import java.util.Arrays;
import java.util.List;

/**
 * Created by siiri on 10/03/17.
 */
public class Muutuja extends AstNode {

    private Character tahis;
    private int jarjeNumber;

    public Muutuja(Character tahis){
        this.tahis = tahis;
    }

    public Muutuja(Character tahis, int nr){
        this.tahis = tahis;
        this.jarjeNumber = nr;
    }

    public Character getTahis() {

        return tahis;
    }

    public int getJarjeNumber() {

        return jarjeNumber;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList((Object) tahis);
    }
}
