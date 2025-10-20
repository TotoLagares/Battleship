import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private BattleShipGUI vista;
    private Juego juego = new Juego();
    private PowerUp powerUp;

    public Controller() {
        vista = new BattleShipGUI();
        JPanel panelInicio = vista.getPanelInicio();
        vista.add(panelInicio);
        powerUp = new PowerUp();
        powerUp.setTablero(juego.getTableroJugador1());

        comenzarJuego();

    }

    private void comenzarJuego() {
        vista.getStartButton().addActionListener(e -> {
            vista.remove(vista.getPanelInicio());
            vista.construtorPanelPrincipal();
            vista.add(vista.switchJugador2());
            vista.revalidate();
            vista.repaint();
            vista.addTableroListener(this);
            usoPowerUp(vista.getJ1PowerUps(), "jugador1");
            usoPowerUp(vista.getJ2PowerUps(), "jugador2");
            juego.getTableroJugador1().crearBarcos();
            juego.getTableroJugador2().crearBarcos();
        });
    }

    public void usoPowerUp(JButton[] powers, String jugador) {
    powers[0].addActionListener(e -> {
        for (int i = 0; i < 3; i++) {
            int[] coordenadas = powerUp.pw1();
            int fila = coordenadas[0];
            int col = coordenadas[1];
            if (jugador.equals("jugador1")) {
                vista.getTableroj1()[fila][col].setContentAreaFilled(true);
                vista.getTableroj1()[fila][col].setBackground(Color.BLUE);
            }
            else if (jugador.equals("jugador2")) {
                vista.getTableroj2()[fila][col].setContentAreaFilled(true);
                vista.getTableroj2()[fila][col].setBackground(Color.BLUE);
            }
        }
    });
    powers[1].addActionListener(e -> {
        int[] coordenadas = powerUp.pw2();
        int fila = coordenadas[0];
        int col = coordenadas[1];
        if (jugador.equals("jugador1")) {
            vista.getTableroj1()[fila][col].setContentAreaFilled(true);
            vista.getTableroj1()[fila][col].setBackground(Color.green);
        }
        else if (jugador.equals("jugador2")) {
            vista.getTableroj2()[fila][col].setContentAreaFilled(true);
            vista.getTableroj2()[fila][col].setBackground(Color.green);
        }
    });
    }

    public void actionPerformed(ActionEvent e) {
        String[] partes = e.getActionCommand().split(",");
        int fila = Integer.parseInt(partes[0]);
        int col = Integer.parseInt(partes[1]);

        if (juego.getTurnoActual()) {
            if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
                String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg","src/resources/anim3.jpg" };
                vista.mostrarSecuenciaImagenes(vista.getTableroj2()[fila][col], imagenes, 600);


            } else if (!juego.getTableroJugador1().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador1().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj2()[fila][col].setContentAreaFilled(true);
                vista.getTableroj2()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador1().getCasilla(fila, col).disparar();
            }
            juego.cambiarTurno();

            //---Cambio de Tablero---
            vista.remove(vista.switchJugador2());
            vista.add(vista.switchJugador1());
            vista.repaint();
            vista.revalidate();



        } else {
            if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && !juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
                String[] imagenes = { "src/resources/anim1.jpg", "src/resources/anim2.jpg","src/resources/anim3.jpg" };
                vista.mostrarSecuenciaImagenes(vista.getTableroj1()[fila][col], imagenes, 600);


            } else if (!juego.getTableroJugador2().getCasilla(fila, col).getFueDisparada()
                    && juego.getTableroJugador2().getCasilla(fila, col).getTieneBarco()) {
                vista.getTableroj1()[fila][col].setBackground(Color.RED);
                juego.getTableroJugador2().getCasilla(fila, col).disparar();
                vista.getTableroj1()[fila][col].setEnabled(false);
            }
            juego.cambiarTurno();

            //---Cambio de Tablero---
            vista.remove(vista.switchJugador1());
            vista.add(vista.switchJugador2());
            vista.repaint();
            vista.revalidate();

        }
    }
}
