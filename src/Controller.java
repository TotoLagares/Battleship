import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego = new Juego();
    private PowerUp powerUp = new PowerUp();

    public Controller() {
        vista = new BattleShipGUI();
        JPanel panelInicio = vista.getPanelInicio();
        vista.add(panelInicio);
        comenzarJuego();
        Elecciones();
        powerUp.setTablero(juego.getTableroJugador1());
        powerUp.setTablero(juego.getTableroJugador2());
    }

    private void comenzarJuego() {
        vista.getStartButton().addActionListener(e -> {
            vista.remove(vista.getPanelInicio());
            vista.construtorPanelPrincipal();
            vista.add(vista.getPanelElection("Jugador1"));
            vista.revalidate();
            vista.repaint();
            juego.getTableroJugador1().crearBarcos();
            juego.getTableroJugador2().crearBarcos();

            mostrar("Tablero1");
        });
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
            usoPowerUp(vista.getJ1PowerUps(), "tablero1");
            usoPowerUp(vista.getJ2PowerUps(), "tablero2");


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
    }
    public void usoPowerUp(JButton[] powers, String tablero) {
        powers[0].addActionListener(e -> {
            for (int i = 0; i < 3; i++) {
                int[] coordenadas = powerUp.pw1();
                int fila = coordenadas[0];
                int col = coordenadas[1];
                if (tablero.equals("tablero1")) {
                   dispararAgua(fila,col,"jugador2");
                }
                else if (tablero.equals("tablero2")) {
                    dispararAgua(fila,col,"jugador1");
                }
            }
        });
        powers[1].addActionListener(e -> {
            int[] coordenadas = powerUp.pw2();
            int fila = coordenadas[0];
            int col = coordenadas[1];
            if (tablero.equals("tablero1")) {
                vista.getTableroj1()[fila][col].setContentAreaFilled(true);
                vista.getTableroj1()[fila][col].setBackground(Color.green);
            }
            else if (tablero.equals("tablero2")) {
                vista.getTableroj2()[fila][col].setContentAreaFilled(true);
                vista.getTableroj2()[fila][col].setBackground(Color.green);
            }
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



            } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj2()[fila][col].setContentAreaFilled(true);
                vista.getTableroj2()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
            }
            juego.cambiarTurno();

            //---Cambio de Tablero---
            vista.getContentPane().removeAll();
            vista.add(vista.switchJugador1());
            vista.repaint();
            vista.revalidate();



        } else {
            if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                dispararAgua(fila, col, "jugador2");


            } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj1()[fila][col].setContentAreaFilled(true);
                vista.getTableroj1()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
            }
            juego.cambiarTurno();

            //---Cambio de Tablero---
            vista.getContentPane().removeAll();
            vista.add(vista.switchJugador2());
            vista.repaint();
            vista.revalidate();

        }
    }
    private void dispararAgua(int fila, int col, String tirador) {
        if ("jugador1".equals(tirador)) {
            juego.getTableroJugador2().getCasilla(fila, col).disparar();
            String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg","src/resources/anim3.jpg" };
            vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 600);
        }else{
            juego.getTableroJugador1().getCasilla(fila, col).disparar();
            String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg","src/resources/anim3.jpg" };
            vista.mostrarSecuenciaImagenes(vista.getTableroj1()[fila][col], imagenes, 600);
        }

    }
}
