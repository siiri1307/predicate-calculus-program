package predmoodul.valemid;

/**
 * Created by siiri on 04/07/17.
 */
public class Argument {

    private char argumendiTahis;
    private String predikaatSymbol;

    public Argument(char argumendiTahis, String predSymbol) {

        this.argumendiTahis = argumendiTahis;
        this.predikaatSymbol = predSymbol;
    }

    public char getArgumendiTahis() {
        return argumendiTahis;
    }

    public String getPredikaatSymbol() {
        return predikaatSymbol;
    }

    public void setArgumendiTahis(char argumendiTahis) {
        this.argumendiTahis = argumendiTahis;
    }

    @Override
    public boolean equals(Object arg) {

        if(this == arg){
            return true;
        }
        if(arg == null || this.getClass() != arg.getClass()){
            return false;
        }

        Argument argument = (Argument) arg;

        return this.argumendiTahis == argument.getArgumendiTahis() && this.predikaatSymbol.equals(argument.getPredikaatSymbol());
    }
}
