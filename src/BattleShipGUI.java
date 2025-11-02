import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;


public class BattleShipGUI extends JFrame {
    private JButton startButton;
    private JButton reglas = new JButton();
    private JButton cambiarPosicion1 = new JButton();
    private JButton cambiarPosicion2 = new JButton();
    private JButton confirmar = new JButton();
    private JButton continuar = new JButton();
    private JButton inicio = new JButton();
    private JButton[][] tableroj1= new JButton[10][10];
    private JButton[][] tableroj2= new JButton[10][10];
    private JButton[] j1PowerUps= new JButton[3];
    private JButton[] j2PowerUps= new JButton[3];
    private JButton reiniciar = new JButton();

    public BattleShipGUI() {};

    public JPanel getPanelInicio() {
        setTitle("BattleShip");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Ventana principal ---
        JPanel panelPrincipal = new JPanel(new GridBagLayout()); // Usamos GridBagLayout para centrar
        panelPrincipal.setOpaque(false);


        JPanel panelComponentes = new JPanel(new GridLayout(1, 2, 30, 30));
        panelComponentes.setOpaque(false);
        panelComponentes.setPreferredSize(new Dimension(700, 700));

        BackgroundPanel bgPanel = new BackgroundPanel("resources/pruebaFBGc1.png", BackgroundPanel.ScaleStrategy.SCALE_STRETCH);
        setContentPane(bgPanel);

        //Adapto imagenes

            //Icono jugar
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonJugar1.png")));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

            //icono Reglas
        ImageIcon originalIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/pruebaBotonReglas-Photoroom.png")));
        Image originalImage2 = originalIcon2.getImage();
        Image scaledImage2 = originalImage2.getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);

        JButton battleShip = new JButton();
        battleShip.setIcon(scaledIcon);
        battleShip.setBorderPainted(false);
        battleShip.setContentAreaFilled(false);
        battleShip.setBorderPainted(false);
        this.startButton = battleShip;

        JButton reglas = new JButton();
        reglas.setIcon(scaledIcon2);
        reglas.setBorderPainted(false);
        reglas.setContentAreaFilled(false);
        reglas.setBorderPainted(false);
        this.reglas = reglas;

        panelComponentes.add(battleShip);
        panelComponentes.add(reglas);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelComponentes, gbc);

        setVisible(true);
        return panelPrincipal;
    }
    public JPanel getPanelReglas() {
        BackgroundPanel bgPanel = new BackgroundPanel("resources/pruebaFBGc2.png", BackgroundPanel.ScaleStrategy.SCALE_STRETCH);
        setContentPane(bgPanel);

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setOpaque(false);
        panelPrincipal.setPreferredSize(new Dimension(1000, 700));

        // Configurar restricciones para manejar posiciones y tamaños
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Crear el área de texto (TextArea) y configurarla
        JTextArea textAreaReglas = new JTextArea();
        textAreaReglas.setEditable(false);
        textAreaReglas.setLineWrap(true);
        textAreaReglas.setWrapStyleWord(true);
        textAreaReglas.setFont(new Font("Arial", Font.PLAIN, 18));
        textAreaReglas.setBackground(new Color(0, 0, 0, 100));
        textAreaReglas.setForeground(Color.WHITE);
        textAreaReglas.setCaretPosition(0);


        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/resources/reglas.txt"))))) {
            StringBuilder reglasTexto = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                reglasTexto.append(linea).append("\n");
            }
            textAreaReglas.setText(reglasTexto.toString());
            textAreaReglas.setCaretPosition(0);
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }


        JScrollPane scrollPane = new JScrollPane(textAreaReglas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setMinimumSize(new Dimension(600, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(0, 0, 0, 0));

        panelPrincipal.add(scrollPane, gbc);

        //
        ImageIcon originalIconInicio = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonInicio.png")));
        Image originalImageInicio = originalIconInicio.getImage();
        Image scaledImageInicio = originalImageInicio.getScaledInstance(450, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIconInicio  = new ImageIcon(scaledImageInicio);
        // Crear el botón "Regresar"
        JButton botonRegresar = new JButton();
        botonRegresar.setIcon(scaledIconInicio);
        botonRegresar.setBorderPainted(false);
        botonRegresar.setContentAreaFilled(false);
        botonRegresar.setPreferredSize(new Dimension(350, 70));
        this.inicio = botonRegresar;

        // Configurar restricciones para el botón
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(botonRegresar, gbc);

        return panelPrincipal;
    }
    public JPanel getPanelFinal(String ganador){
        BackgroundPanel bgPanel = ganador.equals("jugador1") ? new BackgroundPanel("resources/ganador1.1.png", BackgroundPanel.ScaleStrategy.SCALE_STRETCH) : new BackgroundPanel("resources/ganador2.2.png", BackgroundPanel.ScaleStrategy.SCALE_STRETCH);
        setContentPane(bgPanel);
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);

        JPanel panelComponentes = new JPanel(new GridLayout(1, 1, 70, 70));
        panelComponentes.setOpaque(false);

        ImageIcon originalIconInicio = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonCerrar.png")));
        Image originalImageInicio = originalIconInicio.getImage();
        Image scaledImageInicio = originalImageInicio.getScaledInstance(550, 160, Image.SCALE_SMOOTH);
        ImageIcon scaledIconInicio  = new ImageIcon(scaledImageInicio);
        JButton botonRegresar = new JButton();
        botonRegresar.setIcon(scaledIconInicio);
        this.reiniciar = botonRegresar;
        botonRegresar.setBorderPainted(false);
        botonRegresar.setContentAreaFilled(false);
        botonRegresar.setPreferredSize(new Dimension(450, 150));
        panelComponentes.add(botonRegresar);
        panelPrincipal.add(panelComponentes, BorderLayout.SOUTH);

        return  panelPrincipal;
    }

    // ---Creo Tablero---
    private void crearTablero(JButton[][] a) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (a[i][j] == null) { // Creamos el botón solo si no existe
                    JButton botonCelda = new JButton("");
                    botonCelda.setPreferredSize(new Dimension(50, 50));
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
            a[i]=boton;
        }

        //icono Sniper
        ImageIcon originalIconSniper = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonSniper2-Photoroom.png")));
        Image originalImageSniper = originalIconSniper.getImage();
        Image scaledImageSniper = originalImageSniper.getScaledInstance(250, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIconSniper = new ImageIcon(scaledImageSniper);
        a[1].setContentAreaFilled(false);
        a[1].setBorderPainted(false);
        a[1].setIcon(scaledIconSniper);
        a[1].setPreferredSize(new Dimension(200, 70));

        //Icono Explotar
        ImageIcon originalIconExplotar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonExplotar-Photoroom.png")));
        Image originalImageExplotar = originalIconExplotar.getImage();
        Image scaledImageExplotar = originalImageExplotar.getScaledInstance(250, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIconExplotar  = new ImageIcon(scaledImageExplotar);
        a[0].setContentAreaFilled(false);
        a[0].setBorderPainted(false);
        a[0].setIcon(scaledIconExplotar );
        a[0].setPreferredSize(new Dimension(200, 70));

        //Icono Barrer
        ImageIcon originalIconBarrer = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonBarrer.png")));
        Image originalImageBarrer = originalIconBarrer.getImage();
        Image scaledImageBarrer = originalImageBarrer.getScaledInstance(250, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBarrer  = new ImageIcon(scaledImageBarrer);
        a[2].setContentAreaFilled(false);
        a[2].setBorderPainted(false);
        a[2].setIcon(scaledIconBarrer);
        a[2].setPreferredSize(new Dimension(200, 70));

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
        panelJuego1.setPreferredSize(new Dimension(600, 600));

        JPanel panelTablero1 = getTablero(tableroj1);
        panelTablero1.setOpaque(false);
        JPanel panelPowerUps1 = getPowerUps(j1PowerUps);
        panelPowerUps1.setOpaque(false);


        panelJuego1.add(panelTablero1, BorderLayout.CENTER);
        panelJuego1.add(panelPowerUps1, BorderLayout.SOUTH);

        panelJuego1.setOpaque(false);
        panelTotal.setOpaque(false);

        BackgroundPanel bgPanel = new BackgroundPanel("resources/BGj2.3.png", BackgroundPanel.ScaleStrategy.SCALE_STRETCH);
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
        panelJuego2.setPreferredSize(new Dimension(600, 600));

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

        BackgroundPanel bgPanel = new BackgroundPanel("resources/BGj1.png", BackgroundPanel.ScaleStrategy.SCALE_STRETCH);
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
        panelJuego.setPreferredSize(new Dimension(600, 600));

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

        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/cambiarPosicion-Photoroom.png")));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(400,130, Image.SCALE_SMOOTH); // Ajusta las dimensiones deseadas
        ImageIcon scaledIcon = new ImageIcon(scaledImage);


        ImageIcon originalIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/botonContinuar.png")));
        Image originalImage2 = originalIcon2.getImage();
        Image scaledImage2 = originalImage2.getScaledInstance(300,130, Image.SCALE_SMOOTH); // Ajusta las dimensiones deseadas
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);

        if (Objects.equals(jugador, "Jugador1")) {
            continuar.setPreferredSize(new Dimension(230, 60));
            continuar.setIcon(scaledIcon2);
            continuar.setContentAreaFilled(false);
            continuar.setBorderPainted(false);
            panelOpciones.add(continuar);

            cambiarPosicion1.setIcon(scaledIcon);
            cambiarPosicion1.setPreferredSize(new Dimension(300,60));
            cambiarPosicion1.setContentAreaFilled(false);
            cambiarPosicion1.setBorderPainted(false);
            panelOpciones.add(cambiarPosicion1);
        } else {
            confirmar.setPreferredSize(new Dimension(230, 60));
            confirmar.setIcon(scaledIcon2);
            confirmar.setContentAreaFilled(false);
            confirmar.setBorderPainted(false);
            panelOpciones.add(confirmar);

            cambiarPosicion2.setIcon(scaledIcon);
            cambiarPosicion2.setPreferredSize(new Dimension(300,60));
            cambiarPosicion2.setContentAreaFilled(false);
            cambiarPosicion2.setBorderPainted(false);
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
    public JButton getInicio() {return inicio;}
    public JButton getReglas(){return reglas;}
    public JButton getReiniciar(){return reiniciar;}


    //---Clase Auxiliar BG ---
    public class BackgroundPanel extends JPanel {
        private Image background; // Imagen de fondo
        private ScaleStrategy scaleStrategy; // Estrategia para el escalado

        // Enum para definir las estrategias de escalado
        public enum ScaleStrategy {
            SCALE_PROPORTIONAL, // Escala manteniendo proporción (puede dejar bordes)
            SCALE_FILL,         // Llena el panel (puede recortar la imagen)
            SCALE_STRETCH       // Ajusta estirando (sin mantener proporción)
        }

        public BackgroundPanel(String imagePath) {
            this(imagePath, ScaleStrategy.SCALE_PROPORTIONAL); // Estrategia predeterminada: proporcional
        }

        public BackgroundPanel(String imagePath, ScaleStrategy scaleStrategy) {
            // Cargar la imagen
            this.background = loadImage(imagePath);
            this.scaleStrategy = scaleStrategy;
            setLayout(new BorderLayout());
        }

        // Permitir cambiar la imagen de fondo dinámicamente
        public void setBackgroundImage(String imagePath) {
            this.background = loadImage(imagePath);
            repaint(); // Redibujar el panel
        }

        // Cambiar la estrategia de escalado
        public void setScaleStrategy(ScaleStrategy strategy) {
            this.scaleStrategy = strategy;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            if (background != null) {
                // Configurar renderizado de alta calidad
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Obtener dimensiones del panel y de la imagen
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = background.getWidth(this);
                int imageHeight = background.getHeight(this);

                int newWidth, newHeight;
                int x = 0, y = 0;

                switch (scaleStrategy) {
                    case SCALE_PROPORTIONAL:
                        // Escalar proporcionalmente
                        double scaleX = (double) panelWidth / imageWidth;
                        double scaleY = (double) panelHeight / imageHeight;
                        double scale = Math.min(scaleX, scaleY); // Escalar a la menor proporción para evitar cortes
                        newWidth = (int) (imageWidth * scale);
                        newHeight = (int) (imageHeight * scale);
                        x = (panelWidth - newWidth) / 2;
                        y = (panelHeight - newHeight) / 2;
                        break;

                    case SCALE_FILL:
                        // Llenar completamente, recortando la imagen si es necesario
                        scaleX = (double) panelWidth / imageWidth;
                        scaleY = (double) panelHeight / imageHeight;
                        scale = Math.max(scaleX, scaleY); // Escalar a la mayor proporción
                        newWidth = (int) (imageWidth * scale);
                        newHeight = (int) (imageHeight * scale);
                        x = (panelWidth - newWidth) / 2;
                        y = (panelHeight - newHeight) / 2;
                        break;

                    case SCALE_STRETCH:
                    default:
                        // Estirar sin mantener proporciones
                        newWidth = panelWidth;
                        newHeight = panelHeight;
                        break;
                }

                // Dibujar la imagen escalada
                g2d.drawImage(background, x, y, newWidth, newHeight, this);
            }
        }

        private Image loadImage(String imagePath) {
            try {
                return new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))).getImage();
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + imagePath);
                return null;
            }
        }
    }


    public void mostrarSecuenciaImagenes(JComponent componente, String[] imagenes, int delay) {
        final int[] index = {0}; // contador interno

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (index[0] < imagenes.length) {
                    ImageIcon originalIconBarrer = new ImageIcon(imagenes[index[0]]);
                    Image originalImageBarrer = originalIconBarrer.getImage();
                    Image scaledImageBarrer = originalImageBarrer.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    ImageIcon scaledIconBarrer  = new ImageIcon(scaledImageBarrer);

                    if (componente instanceof JButton)
                        ((JButton) componente).setIcon(scaledIconBarrer);
                    else if (componente instanceof JLabel)
                        ((JLabel) componente).setIcon(scaledIconBarrer);
                    index[0]++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}