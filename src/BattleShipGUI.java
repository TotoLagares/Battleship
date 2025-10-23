import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javax.imageio.ImageIO;


public class BattleShipGUI extends JFrame {
    private JButton startButton;
    private JButton cambiarPosicion1 = new JButton();
    private JButton cambiarPosicion2 = new JButton();
    private JButton confirmar = new JButton();
    private JButton continuar = new JButton();
    private JButton[][] tableroj1= new JButton[10][10];
    private JButton[][] tableroj2= new JButton[10][10];
    private JButton[] j1PowerUps= new JButton[3];
    private JButton[] j2PowerUps= new JButton[3];

    public BattleShipGUI() {};

    public JPanel getPanelInicio(){
        setTitle("BattleShip");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); //Gap entre paneles internos


        //---Ventana Main---
        JPanel panelPrincipal = new JPanel( new BorderLayout() );

        BackgroundPanel bgPanel = new BackgroundPanel("resources/bgPrin1.jpg");
        setContentPane(bgPanel);

        JButton battleShip = new JButton("Jugar");
        this.startButton = battleShip;
        battleShip.setSize(25,25);
        panelPrincipal.add(battleShip,BorderLayout.NORTH);
        panelPrincipal.setOpaque(false);


        setVisible(true);
        return panelPrincipal;
    };

    // ---Creo Tablero--- Cambiar para que sea void
    private void crearTablero(JButton[][] a) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (a[i][j] == null) { // Creamos el botón solo si no existe
                    JButton botonCelda = new JButton("");
                    botonCelda.setPreferredSize(new Dimension(30, 30));
                    botonCelda.setContentAreaFilled(false);
                    a[i][j] = botonCelda;
                }
            }
        }
    }
    public void addTableroListener(ActionListener listener) {
        for (int i = 0; i < tableroj1.length; i++) {
            for (int j = 0; j < tableroj1[i].length; j++) {
                JButton b = tableroj1[i][j];
                b.setActionCommand(i + "," + j);
                b.addActionListener(listener);
            }
        }
        for (int i = 0; i < tableroj2.length; i++) {
            for (int j = 0; j < tableroj2[i].length; j++) {
                JButton b = tableroj2[i][j];
                b.setActionCommand(i + "," + j);
                b.addActionListener(listener);
            }
        }
    }


    // ---Creo Panel PowerUps
    private void crearPanelPowerUps(JButton[] a) {
        for (int i=0;i<3; i++ ) {
            JButton boton = new JButton();
            boton.setPreferredSize(new Dimension(30, 30));
            a[i]=boton;
        }
    }

    //---getTablero---
    private JPanel getTablero(JButton[][] a) {
        JPanel panelTablero = new JPanel(new GridLayout(10, 10,4,4));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                panelTablero.add(a[i][j]);
            }
        }
        return panelTablero;
    }

    //---getPowerUps---
    private JPanel getPowerUps(JButton[] a) {
        JPanel panelPowerUps = new JPanel(new GridLayout(1, 3));
        for (int i = 0; i < 3; i++) {
            panelPowerUps.add(a[i]);
        }
        return panelPowerUps;
    }
    //--- Mostrar un tablero solo---
    public JPanel switchJugador1() {
        JPanel panelTotal = new JPanel(new GridBagLayout());
        panelTotal.setPreferredSize(new Dimension(700, 700));

        JPanel panelJuego1 = new JPanel(new BorderLayout(30, 30));
        panelJuego1.setPreferredSize(new Dimension(800, 800));

        JPanel panelTablero1 = getTablero(tableroj1);
        panelTablero1.setOpaque(false);
        JPanel panelPowerUps1 = getPowerUps(j1PowerUps);
        panelPowerUps1.setOpaque(false);


        panelJuego1.add(panelTablero1, BorderLayout.CENTER);
        panelJuego1.add(panelPowerUps1, BorderLayout.SOUTH);

        panelJuego1.setOpaque(false);
        panelTotal.setOpaque(false);

        BackgroundPanel bgPanel = new BackgroundPanel("resources/bgPrin1.jpg");
        setContentPane(bgPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Centrar horizontalmente
        gbc.gridy = 0; // Centrar verticalmente
        gbc.anchor = GridBagConstraints.CENTER; // Aseguramos que se centre
        panelTotal.add(panelJuego1, gbc);

        return panelTotal;
    }

    public JPanel switchJugador2() {
        JPanel panelTotal2 = new JPanel(new GridBagLayout());
        panelTotal2.setPreferredSize(new Dimension(700, 700));

        JPanel panelJuego2 = new JPanel(new BorderLayout(30, 30));
        panelJuego2.setPreferredSize(new Dimension(800, 800));

        JPanel panelTablero2 = getTablero(tableroj2);
        panelTablero2.setOpaque(false);
        JPanel panelPowerUps2 = getPowerUps(j2PowerUps);
        panelPowerUps2.setOpaque(false);


        panelJuego2.add(panelTablero2, BorderLayout.CENTER);
        panelJuego2.add(panelPowerUps2, BorderLayout.SOUTH);

        panelJuego2.setOpaque(false);
        panelTotal2.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Centrar horizontalmente
        gbc.gridy = 0; // Centrar verticalmente
        gbc.anchor = GridBagConstraints.CENTER; // Aseguramos que se centre
        panelTotal2.add(panelJuego2, gbc);

        BackgroundPanel bgPanel = new BackgroundPanel("resources/tablero1.png");
        setContentPane(bgPanel);

        return panelTotal2;
    };

    //---Cambio Pantalla Juego---

    public void construtorPanelPrincipal() {
        //----Jugador 1----
        crearTablero(tableroj1);
        crearPanelPowerUps(j1PowerUps);

        //----Jugador 2----
        crearTablero(tableroj2);
        crearPanelPowerUps(j2PowerUps);

    }

    //---Pantalla Eleccion---
    public JPanel getPanelElection(String jugador) {
        JPanel panelTotal = new JPanel(new GridBagLayout());
        panelTotal.setPreferredSize(new Dimension(700, 700));

        JPanel panelJuego = new JPanel(new BorderLayout(30, 30));
        panelJuego.setPreferredSize(new Dimension(800, 800));

        // Determina tablero y panel de opciones en base al jugador
        JPanel panelTablero = "Jugador1".equals(jugador) ? getTablero(tableroj1) : getTablero(tableroj2);
        JPanel panelOpciones = "Jugador1".equals(jugador) ? crearPanelOpciones("Jugador1") : crearPanelOpciones("Jugador2");

        panelTablero.setOpaque(false);
        panelJuego.add(panelTablero, BorderLayout.CENTER);
        panelJuego.add(panelOpciones, BorderLayout.SOUTH);
        panelJuego.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        panelTotal.setOpaque(false);
        panelTotal.add(panelJuego, gbc);

        return panelTotal;
    }

    //----Crear Panel Opciones---
    private JPanel crearPanelOpciones(String jugador) {
        JPanel panelOpciones = new JPanel();

        if (Objects.equals(jugador, "Jugador1")) {
            continuar.setText("Continuar");
            continuar.setPreferredSize(new Dimension(100, 30));
            continuar.setForeground(Color.BLACK); // Color del texto
            continuar.setBackground(Color.LIGHT_GRAY); // Fondo visible (opcional)
            continuar.setOpaque(true); // Necesario si se configura el fondo
            panelOpciones.add(continuar);

            cambiarPosicion1.setPreferredSize(new Dimension(100, 30));
            cambiarPosicion1.setForeground(Color.BLACK);
            cambiarPosicion1.setBackground(Color.LIGHT_GRAY);
            cambiarPosicion1.setOpaque(true);
            cambiarPosicion1.setText("Cambiar Posicion");
            panelOpciones.add(cambiarPosicion1);
        } else {
            confirmar.setText("Confirmar");
            confirmar.setPreferredSize(new Dimension(100, 30));
            confirmar.setForeground(Color.BLACK);
            confirmar.setBackground(Color.LIGHT_GRAY);
            confirmar.setOpaque(true);
            panelOpciones.add(confirmar);

            cambiarPosicion2.setPreferredSize(new Dimension(100, 30));
            cambiarPosicion2.setForeground(Color.BLACK);
            cambiarPosicion2.setBackground(Color.LIGHT_GRAY);
            cambiarPosicion2.setOpaque(true);
            cambiarPosicion2.setText("Cambiar Posicion");
            panelOpciones.add(cambiarPosicion2);
        }

        panelOpciones.setOpaque(false);
        return panelOpciones;
    }


    public JButton getStartButton() {
        return startButton;
    }
    public JButton[][] getTableroj1() {
        return tableroj1;
    }
    public JButton[][] getTableroj2() {
        return tableroj2;
    }
    public JButton getCambiarPosicion1() {return cambiarPosicion1;}
    public JButton getCambiarPosicion2() {return cambiarPosicion2;}
    public JButton getConfirmar() {return confirmar;}
    public JButton getContinuar() {return continuar;}
    public JButton[] getJ1PowerUps() {return j1PowerUps;}
    public JButton[] getJ2PowerUps() {return j2PowerUps;}







    //---Clase Auxiliar BG ---
    private static class BackgroundPanel extends JPanel {
        private BufferedImage background;

        public BackgroundPanel(String path) {
            // intentamos cargar desde disco
            try {
                File f = new File(path);
                if (f.exists()) {
                    background = ImageIO.read(f);
                } else {
                    // intentamos cargar como recurso en classpath
                    URL res = BackgroundPanel.class.getResource("/" + path);
                    if (res != null) background = ImageIO.read(res);
                }
            } catch (IOException e) {
                // si falla, dejamos background en null y seguimos
                background = null;
            }
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void cambiarBackground(String path) {

    }
    public void mostrarSecuenciaImagenes(JComponent componente, String[] imagenes, int delay) {
        final int[] index = {0}; // contador interno

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (index[0] < imagenes.length) {
                    ImageIcon icono = new ImageIcon(imagenes[index[0]]);
                    if (componente instanceof JButton)
                        ((JButton) componente).setIcon(icono);
                    else if (componente instanceof JLabel)
                        ((JLabel) componente).setIcon(icono);
                    index[0]++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}
