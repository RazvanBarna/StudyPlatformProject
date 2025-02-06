package ro.platformaStudiu.serviceClass;

public class Roluri {
    private final int idRol;
    private final String numeRol;

    public Roluri(int idRol, String numeRol) {
        this.idRol = idRol;
        this.numeRol = numeRol;
        }
        public int getIdRol() {
            return idRol;
        }
        public String getNumeRol() {
            return numeRol;
        }

        @Override
        public String toString() {
            return numeRol+" ID_ROL: "+idRol;
        }
}
