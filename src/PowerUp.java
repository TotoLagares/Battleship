import javax.swing.*;
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

        Casilla[][] botones = tablero.getTablero();
        int[] coordenadas = new int[2];

        for(int i = 0; i <= 9; i++){
            for(int j = 0; j <= 9; j++){
                int fila = i;
                int col = j;
                if(!botones[i][j].getFueDisparada() && botones[i][j].getTieneBarco()){
                    coordenadas[0] = i;
                    coordenadas[1] = j;
                    tablero.getCasilla(fila, col).setFueDisparada(true);
                    return coordenadas;
                }
            }
        }

        return null;
    }

    public void pw3(){}

//    private Casilla[][] getTableroNoDisparado() {
//        Casilla[][] botones = tablero.getTablero();
//        Casilla[][] tableroDisparado = new Casilla[10][10];
//        for(int i = 0; i < botones.length; i++){
//            for(int j = 0; j < botones[i].length; j++){
//                if(!botones[i][j].getFueDisparada()){
//                    tableroDisparado[i][j] = botones[i][j];
//                }
//            }
//        }
//        return tableroDisparado;
//    }
}
