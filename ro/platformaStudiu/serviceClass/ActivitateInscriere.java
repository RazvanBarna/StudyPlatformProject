package ro.platformaStudiu.serviceClass;

public class ActivitateInscriere extends  Activitate{
    private String descriere;
    private String ora;
    //ora string
    public ActivitateInscriere(String ora,String ziua,int activitateID,String descriere){
        super(ziua,activitateID);
        this.descriere=descriere;
        this.ora=ora;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    @Override
    public String toString() {
        return " "+ super.toString()+
                ", descriere:'" + descriere + '\'' +
                ", ora:'" + ora + '\'' +
                '}';
    }
}
