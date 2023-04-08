import java.nio.file.Files;
import java.nio.file.Path;

public class Partido {
    String equipo1;
    String equipo2;
    int golesEquipo1;
    int golesEquipo2;
    int prediccion;


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
