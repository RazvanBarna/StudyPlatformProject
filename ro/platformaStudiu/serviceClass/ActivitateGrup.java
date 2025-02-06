package ro.platformaStudiu.serviceClass;

import java.sql.Time;

public class ActivitateGrup extends Activitate{
    String descriere;
    int grup_id;
    int nr_curent_participanti;
    int nr_min_participanti;
    Time oraGrup;
    int durata;
    int timp_expirare;

    public ActivitateGrup (int activitate_id, int grup_id, int nr_curent_participanti, int nr_min_participanti, String ziua, Time ora, String descriere, int durata, int timp_expirare) {
        super(ziua,activitate_id);
        this.grup_id = grup_id;
        this.nr_curent_participanti = nr_curent_participanti;
        this.nr_min_participanti = nr_min_participanti;
        this.oraGrup = ora;
        this.descriere = descriere;
        this.durata = durata;
        this.timp_expirare = timp_expirare;
    }

    @Override
    public String toString() {
        return super.toString()+ descriere + " - "  + " la ora " + oraGrup.toString() + " (" + durata + " ore)" +
                " | Participanți: " + nr_curent_participanti;
    }
}
