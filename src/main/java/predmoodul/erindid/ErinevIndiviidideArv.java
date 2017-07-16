package predmoodul.erindid;

/**
 * Created by siiri on 28/06/17.
 */
public class ErinevIndiviidideArv extends Throwable {

    private String sonum;

    public ErinevIndiviidideArv(int indiviidideArvPakkumine, int indiviidideArvLahendus){
        this.sonum = "Esitasid " + indiviidideArvPakkumine + "-kohalise predikaadi, ootasin aga " + indiviidideArvLahendus + "-kohalist predikaati.";
    }

    @Override
    public String getMessage(){
        return sonum;
    }
}
