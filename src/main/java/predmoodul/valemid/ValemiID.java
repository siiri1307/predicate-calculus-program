package predmoodul.valemid;

import java.util.Arrays;
import java.util.List;

/**
 * Created by siiri on 21/03/17.
 */
public class ValemiID extends AstNode {

    private String predSümbol;
    private int argumentideArv;

    public ValemiID(String predSümbol, int argumentideArv) {
        this.predSümbol = predSümbol;
        this.argumentideArv = argumentideArv;
    }

    public String getPredSümbol() {
        return predSümbol;
    }

    public int getArgumentideArv() {
        return argumentideArv;
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj){
            return true;
        }
        else if(!(obj instanceof ValemiID)){
            return false;
        }

        ValemiID id = (ValemiID) obj;

        return this.predSümbol.equals(id.getPredSümbol()) && this.argumentideArv == id.getArgumentideArv();
    }

    @Override
    public int hashCode() {
        int rasi = 17;
        rasi = 31 * rasi + predSümbol.hashCode();
        rasi = 31 * rasi + argumentideArv;
        return rasi;
    }

    @Override
    public List<Object> getChildren() {
        return Arrays.asList(predSümbol, argumentideArv);
    }

    public String dot() {

        return predSümbol;
    }
}
