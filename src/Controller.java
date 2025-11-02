import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego = new Juego();
    private PowerUp powerUp1 = new PowerUp();
    private PowerUp powerUp2 = new PowerUp();

    public Controller() {
        vista = new BattleShipGUI();
        vista.add(vista.getPanelInicio());
        powerUp1.setTablero(juego.getTableroJugador1());
        powerUp2.setTablero(juego.getTableroJugador2());
        juego.getTableroJugador1().crearBarcos();
        juego.getTableroJugador2().crearBarcos();
        vista.construtorPanelPrincipal();
        usoPowerUp(vista.getJ1PowerUps(), "tablero1");
        usoPowerUp(vista.getJ2PowerUps(), "tablero2");
        comenzarJuego();


    }

    private void comenzarJuego() {
        vista.getStartButton().addActionListener(e -> {
            vista.remove(vista.getPanelInicio());
            vista.add(vista.getPanelElection("Jugador1"));
            vista.revalidate();
            vista.repaint();


            mostrar("Tablero1");
        });
        Elecciones();
    }

    private void Elecciones (){
        vista.getContinuar().addActionListener(e -> {
            mostrar("Tablero2");
            vista.getContentPane().removeAll();
            vista.add(vista.getPanelElection("Jugador2"));
            vista.revalidate();
            vista.repaint();
        });
        vista.getConfirmar().addActionListener(e -> {
            limpiar("Tablero2");
            limpiar("Tablero1");
            vista.getContentPane().removeAll();



            vista.add(vista.switchJugador2());
            vista.revalidate();
            vista.repaint();
            vista.addTableroListener(this);
        });
        vista.getCambiarPosicion1().addActionListener(e -> {
            limpiar("Tablero1");
            juego.getTableroJugador1().crearBarcos();
            mostrar("Tablero1");
        });
        vista.getCambiarPosicion2().addActionListener(e -> {
            limpiar("Tablero2");
            juego.getTableroJugador2().crearBarcos();
            mostrar("Tablero2");
        });
        vista.getReglas().addActionListener(e -> {
            vista.add(vista.getPanelReglas());
            vista.revalidate();
            vista.repaint();
            listenerInicio();
        });
    }
    private void listenerInicio() {
        vista.getInicio().addActionListener(e -> {
            vista.getContentPane().removeAll();
            vista.add(vista.getPanelInicio());
            vista.revalidate();
            vista.repaint();
            comenzarJuego();
        });
    }
    public void usoPowerUp(JButton[] powers, String tablero) {
        powers[0].addActionListener(e -> {
            for (int i = 0; i < 5; i++) {

                int[] coordenadas = "tablero1".equals(tablero) ? powerUp1.pw1(): powerUp2.pw1();
                if (tablero.equals("tablero1")) {
                    if(juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()){
                        dispararNave(coordenadas[0],coordenadas[1],"jugador2");
                    }else if(!juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()) {
                        dispararAgua(coordenadas[0],coordenadas[1],"jugador2");
                    }
                }
                else{
                    if(juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()){
                        dispararNave(coordenadas[0],coordenadas[1],"jugador1");
                    }else if(!juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()) {
                        dispararAgua(coordenadas[0],coordenadas[1],"jugador1");
                    }
                }
            }
            powers[0].setEnabled(false);
        });
        powers[1].addActionListener(e -> {
            int[] coordenadas = "tablero1".equals(tablero) ? powerUp1.pw2(): powerUp2.pw2();

            int fila = coordenadas[0];
            int col = coordenadas[1];
            if (tablero.equals("tablero2")) {
              dispararNave(fila,col,"jugador1");
            }
            else if (tablero.equals("tablero1") && !juego.getTableroJugador1().getCasilla(fila,col).getFueDisparada()) {
                dispararNave(fila,col,"jugador2");
            }
            powers[1].setEnabled(false);
        });

        powers[2].addActionListener(e -> {
            List<int[]> filaBoton = new ArrayList<>();
            filaBoton= "tablero1".equals(tablero) ? powerUp1.pw3(): powerUp2.pw3();

            int[] coordenadas = new int[2];
            for (int i = 0; i <= 9; i++) {
                coordenadas[0] = filaBoton.get(i)[0];
                coordenadas[1] = filaBoton.get(i)[1];
                if (tablero.equals("tablero1")) {
                    if(juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()){
                        dispararNave(coordenadas[0],coordenadas[1],"jugador2");
                    }else if(!juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador1().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()) {
                        dispararAgua(coordenadas[0],coordenadas[1],"jugador2");
                    }
                } else{
                    if(juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()){
                        dispararNave(coordenadas[0],coordenadas[1],"jugador1");
                    }else if(!juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getTieneBarco() && !juego.getTableroJugador2().getCasilla(coordenadas[0],coordenadas[1]).getFueDisparada()) {
                        dispararAgua(coordenadas[0],coordenadas[1],"jugador1");
                    }
                }
            }
            powers[2].setEnabled(false);
        });
    }


    private void limpiar(String tablero) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.equals("Tablero1")) {
                    vista.getTableroj1()[i][j].setContentAreaFilled(false);
                }else {
                    vista.getTableroj2()[i][j].setContentAreaFilled(false);
                }
            }
        }
    }
    private void mostrar(String tablero) {
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j<10;j++){
                switch (tablero) {
                    case "Tablero1":
                        if (juego.getTableroJugador1().getCasilla(i, j).getTieneBarco()) {
                            vista.getTableroj1()[i][j].setContentAreaFilled(true);
                            vista.getTableroj1()[i][j].setBackground(Color.GREEN);
                        }
                        break;
                    case "Tablero2":
                        if (juego.getTableroJugador2().getCasilla(i, j).getTieneBarco()) {
                            vista.getTableroj2()[i][j].setContentAreaFilled(true);
                            vista.getTableroj2()[i][j].setBackground(Color.GREEN);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        String[] partes = e.getActionCommand().split(",");
        int fila = Integer.parseInt(partes[0]);
        int col = Integer.parseInt(partes[1]);


        if (juego.getTurnoActual()) {

            if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                dispararAgua(fila,col,"jugador1");
                camiarTablero("Jugador1");


            } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                dispararNave(fila,col,"jugador1");
                camiarTablero("Jugador1");
            }


        } else {

            if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                dispararAgua(fila, col, "jugador2");
                camiarTablero("Jugador2");


            } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                dispararNave(fila, col, "jugador2");
                camiarTablero("Jugador2");
            }

        }

    }

    private void camiarTablero(String jugador){
        Timer timer = new Timer(1800, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (jugador.equals("Jugador1")){
                    juego.cambiarTurno();

                    //---Cambio de Tablero---
                    vista.getContentPane().removeAll();
                    vista.add(vista.switchJugador1());
                    vista.repaint();
                    vista.revalidate();
                }else{
                    juego.cambiarTurno();

                    //---Cambio de Tablero---
                    vista.getContentPane().removeAll();
                    vista.add(vista.switchJugador2());
                    vista.repaint();
                    vista.revalidate();
                }
                timer.stop();
            }
        });
        timer.start();
    }

    private void dispararAgua(int fila, int col, String tirador) {
        if ("jugador1".equals(tirador)) {
            juego.getTableroJugador2().getCasilla(fila, col).disparar();
            String[] imagenes = { "src/resources/animacion1.1.png", "src/resources/animacion2.png","src/resources/animacion3.png" };
            vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 450);
        }else{
            juego.getTableroJugador1().getCasilla(fila, col).disparar();
            String[] imagenes = { "src/resources/animacion1.1.png", "src/resources/animacion2.png","src/resources/animacion3.png" };
            vista.mostrarSecuenciaImagenes(vista.getTableroj1()[fila][col], imagenes, 450);
        }

    }
    private void dispararNave(int fila, int col, String tirador) {
        if ("jugador1".equals(tirador)) {
            juego.getTableroJugador2().getCasilla(fila, col).disparar();
            String[] imagenes = { "src/resources/animacion1.1.png", "src/resources/animacion2Nav.png","src/resources/animacion3Nav.png" };
            vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 450);
            if (juego.verificarGanador(1)){
               vista.getContentPane().removeAll();
               vista.add(vista.getPanelFinal("jugador1"));
               vista.revalidate();
               vista.repaint();
               System.out.println("ganador 1");
               listReiniciar();

            }
        }else if ("jugador2".equals(tirador)){
            juego.getTableroJugador1().getCasilla(fila, col).disparar();
            String[] imagenes = { "src/resources/animacion1.1.png", "src/resources/animacion2Nav.png","src/resources/animacion3Nav.png" };
            vista.mostrarSecuenciaImagenes(vista.getTableroj1()[fila][col], imagenes, 450);
            if (juego.verificarGanador(2)){
                vista.getContentPane().removeAll();
                vista.add(vista.getPanelFinal("jugador2"));
                vista.revalidate();
                vista.repaint();
                System.out.println("ganador 2");
                listReiniciar();
            }
        }
    }
    private void listReiniciar(){
        vista.getReiniciar().addActionListener(e -> {
                vista.dispose();
        });
    }
}
