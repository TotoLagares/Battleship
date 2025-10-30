import java.util.*;

public class PowerUp {

    private Tablero tablero;

    public PowerUp(){}

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    //Metodos activar

    public int[] pw1(){
        Random rand = new Random();
        int[] coordenadas = new int[2];
        int fila, col;
        do {
            fila = rand.nextInt(10);
            col = rand.nextInt(10);
        } while (tablero.getCasilla(fila, col).getFueDisparada());
        coordenadas[0] = fila;
        coordenadas[1] = col;
        tablero.getCasilla(fila, col).setFueDisparada(true);
        return coordenadas;
    }

    public int[] pw2(){
        List<int[]> barcoSinHundir = new ArrayList<>();
        Casilla[][] botones = tablero.getTablero();
        Random rand = new Random();

        for(int i = 0; i <= 9; i++){
            for(int j = 0; j <= 9; j++){
                int[] coordenadas = new int[2];
                int fila = i;
                int col = j;
                if(!botones[i][j].getFueDisparada() && botones[i][j].getTieneBarco()){
                    coordenadas[0] = i;
                    coordenadas[1] = j;
                    barcoSinHundir.add(coordenadas);
                }
            }
        }
        int[] elegido = barcoSinHundir.get(rand.nextInt(barcoSinHundir.size()));
        tablero.getCasilla(elegido[0], elegido[1]).setFueDisparada(true);
        return elegido;

    }

    public List<int[]> pw3(){
        Random rand = new Random();
        int filaRandom =  rand.nextInt(10);
        List<int[]> filaBoton = new ArrayList<>();
        for(int i = 0; i <= 9; i++){
            int[] coordenadas = new int[2];
            coordenadas[0] = filaRandom;
            coordenadas[1] = i;
            filaBoton.add(coordenadas);
        }
        return filaBoton;
    }

}
