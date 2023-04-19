import java.nio.file.Files;
import java.nio.file.Path;

public class Partido {
    String equipo1;
    String equipo2;
    int golesEquipo1;
    int golesEquipo2;
    int prediccion;

    String fase;
    String nomRonda;

    public Partido(String equipo1, String equipo2, int golesEquipo1, int golesEquipo2, int prediccion, Ronda ronda, String fase) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.golesEquipo1 = golesEquipo1;
        this.golesEquipo2 = golesEquipo2;
        this.prediccion = prediccion;
        this.nomRonda = nomRonda;
        this.fase = fase;
    }

    public Partido(){


    }
    public int resultadoPartido(){
       if(golesEquipo2 > golesEquipo1){
           return 2;
       } if(golesEquipo1 > golesEquipo2){
           return 1;
        } else {
           return 0;
        }
    }
}
