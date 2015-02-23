package edu.wpi.first.wpilibj.ui;

import edu.wpi.first.wpilibj.DriverStation;

public class DriverStationGUI extends javax.swing.JFrame {

    DriverStation m_ds;

    /**
     * Creates new form CelsiusConverterGUI
     */
    public DriverStationGUI(DriverStation ds) {
        m_ds = ds;
        initComponents();
    }

    public String modeText() {
        System.out.println("thing: " + m_ds);
        return m_ds == null ? "Not connected" :
                m_ds.isDisabled() ? "Disabled" :
                        m_ds.isAutonomous() ? "Autonomous" :
                                m_ds.isTest() ? "Test" : "Teleop";
    }

    private void renderMode() {
        modeLabel.setText(modeText());
    }

    private void initComponents() {
        disableButton = new javax.swing.JButton();
        teleopButton = new javax.swing.JButton();
        autoButton = new javax.swing.JButton();
        testButton = new javax.swing.JButton();

        modeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Driver Station");


        disableButton.setText("Disable");
        teleopButton.setText("Tele-Op");
        autoButton.setText("Autonomous");
        testButton.setText("Test");

        disableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_ds.setDSMode(DriverStation.DSMode.Disabled);
                renderMode();
            }
        });

        teleopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_ds.setDSMode(DriverStation.DSMode.Teleop);
                renderMode();
            }
        });

        autoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_ds.setDSMode(DriverStation.DSMode.Autonomous);
                renderMode();
            }
        });

        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_ds.setDSMode(DriverStation.DSMode.Test);
                renderMode();
            }
        });

        renderMode();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
                getContentPane());

        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(modeLabel)
                .addComponent(disableButton)
                .addComponent(teleopButton)
                .addComponent(autoButton)
                .addComponent(testButton));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(modeLabel)
                .addComponent(disableButton)
                .addComponent(teleopButton)
                .addComponent(autoButton)
                .addComponent(testButton));
        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{autoButton});
        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{autoButton});
        pack();
    }

    public void start() {
        final DriverStationGUI _this = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                _this.setVisible(true);
            }
        });
    }

    private javax.swing.JLabel modeLabel;
    private javax.swing.JButton disableButton;
    private javax.swing.JButton teleopButton;
    private javax.swing.JButton autoButton;
    private javax.swing.JButton testButton;

}
