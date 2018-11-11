
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sat Sep 08 12:05:31 CST 2018
 */



/**
 * @author pmz
 */
public class MainMedicalWaveFrame extends JFrame implements Runnable{

    public Thread m_thread;
    public boolean go_thread = false;

    public int pre,sbp,map,dbp,spo2,pr,rr,dl,pluse,ii,spi,pi,t1_stamsg,t2_stamsg;
    public double bt1,bt2;
    public int hr;





    public MainMedicalWaveFrame() {
        initComponents();
    }

    private void e2MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1InfoDisplay = new JPanel();
        ECGwavePanel1 = new MedicalWaveDisplayPanel();
        ECGwavePanel2 = new MedicalWaveDisplayPanel();
        Spo2WavePanel = new MedicalWaveDisplayPanel();
        RESPwavePanel = new MedicalWaveDisplayPanel();
        ECGdataPanel = new JPanel();
        e1 = new JLabel();
        e2 = new JLabel();
        e3 = new JLabel();
        e4 = new JLabel();
        e5 = new JLabel();
        e6 = new JLabel();
        e7 = new JLabel();
        e8 = new JLabel();
        e9 = new JLabel();
        e10 = new JLabel();
        e11 = new JLabel();
        e0 = new JLabel();
        NIBPdataPanel = new JPanel();
        n1 = new JLabel();
        n2 = new JLabel();
        n3 = new JLabel();
        n4 = new JLabel();
        n5 = new JLabel();
        n6 = new JLabel();
        n7 = new JLabel();
        n8 = new JLabel();
        n9 = new JLabel();
        n10 = new JLabel();
        panel1 = new JPanel();
        panel1.setVisible(false);
        l1 = new JLabel();
        l2 = new JLabel();
        SPO2dataPanel = new JPanel();
        s1 = new JLabel();
        s2 = new JLabel();
        s3 = new JLabel();
        s4 = new JLabel();
        s5 = new JLabel();
        s6 = new JLabel();
        s7 = new JLabel();
        s8 = new JLabel();
        RESPdataPanel = new JPanel();
        r1 = new JLabel();
        r2 = new JLabel();
        r3 = new JLabel();
        panel3 = new JPanel();
        t1 = new JLabel();
        t2 = new JLabel();
        t3 = new JLabel();
        t4 = new JLabel();
        t5 = new JLabel();
        t6 = new JLabel();
        t7 = new JLabel();
        t8 = new JLabel();
        t9 = new JLabel();
        t10 = new JLabel();
        t11 = new JLabel();
        t12 = new JLabel();
        panel2 = new JPanel();
        warningText = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1InfoDisplay ========
        {
            panel1InfoDisplay.setBackground(Color.black);
            panel1InfoDisplay.setLayout(null);

            //---- ECGwavePanel1 ----
            ECGwavePanel1.setBackground(Color.black);
            ECGwavePanel1.setBorder(new TitledBorder(new EtchedBorder(), "   II", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12), Color.green));
            panel1InfoDisplay.add(ECGwavePanel1);
            ECGwavePanel1.setBounds(0, 45, 760, 185);

            //---- ECGwavePanel2 ----
            ECGwavePanel2.setBackground(Color.black);
            ECGwavePanel2.setBorder(new TitledBorder(new EtchedBorder(), "   I", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12), Color.green));
            panel1InfoDisplay.add(ECGwavePanel2);
            ECGwavePanel2.setBounds(0, 230, 760, 185);

            //---- Spo2WavePanel ----
            Spo2WavePanel.setBackground(Color.black);
            Spo2WavePanel.setBorder(new TitledBorder(new EtchedBorder(), "  PLETH", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12), Color.cyan));
            panel1InfoDisplay.add(Spo2WavePanel);
            Spo2WavePanel.setBounds(0, 415, 760, 185);

            //---- RESPwavePanel ----
            RESPwavePanel.setBackground(Color.black);
            RESPwavePanel.setBorder(new TitledBorder(new EtchedBorder(), "  RESP", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12), Color.yellow));
            panel1InfoDisplay.add(RESPwavePanel);
            RESPwavePanel.setBounds(0, 600, 760, 165);

            //======== ECGdataPanel ========
            {
                ECGdataPanel.setBackground(Color.black);
                ECGdataPanel.setForeground(Color.green);
                ECGdataPanel.setBorder(new TitledBorder(null, "ECG", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                    new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16), Color.green));
                ECGdataPanel.setLayout(null);

                //---- e1 ----
                e1.setText(" 1");
                e1.setFont(e1.getFont().deriveFont(e1.getFont().getSize() + 30f));
                e1.setForeground(Color.green);
                e1.setHorizontalAlignment(SwingConstants.CENTER);
                ECGdataPanel.add(e1);
                e1.setBounds(5, 40, 150, 85);

                //---- e2 ----
                e2.setText("120");
                e2.setFont(e2.getFont().deriveFont(e2.getFont().getSize() + 4f));
                e2.setForeground(Color.green);
                e2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        e2MouseClicked(e);
                    }
                });
                ECGdataPanel.add(e2);
                e2.setBounds(160, 45, 40, e2.getPreferredSize().height);

                //---- e3 ----
                e3.setText("50");
                e3.setForeground(Color.green);
                e3.setFont(e3.getFont().deriveFont(e3.getFont().getSize() + 4f));
                ECGdataPanel.add(e3);
                e3.setBounds(160, 70, 40, e3.getPreferredSize().height);

                //---- e4 ----
                e4.setText("OFF");
                e4.setFont(e4.getFont().deriveFont(e4.getFont().getSize() + 5f));
                e4.setForeground(Color.green);
                ECGdataPanel.add(e4);
                e4.setBounds(280, 20, 55, e4.getPreferredSize().height);

                //---- e5 ----
                e5.setText("PACE");
                e5.setFont(e5.getFont().deriveFont(e5.getFont().getSize() + 5f));
                e5.setForeground(Color.green);
                e5.setBackground(Color.blue);
                ECGdataPanel.add(e5);
                e5.setBounds(220, 20, 55, e5.getPreferredSize().height);

                //---- e6 ----
                e6.setText("ST1");
                e6.setForeground(Color.green);
                e6.setFont(e6.getFont().deriveFont(e6.getFont().getSize() + 5f));
                ECGdataPanel.add(e6);
                e6.setBounds(220, 50, 55, e6.getPreferredSize().height);

                //---- e7 ----
                e7.setText("OFF");
                e7.setFont(e7.getFont().deriveFont(e7.getFont().getSize() + 5f));
                e7.setForeground(Color.green);
                ECGdataPanel.add(e7);
                e7.setBounds(280, 50, 55, 23);

                //---- e8 ----
                e8.setText("ST2");
                e8.setForeground(Color.green);
                e8.setFont(e8.getFont().deriveFont(e8.getFont().getSize() + 5f));
                ECGdataPanel.add(e8);
                e8.setBounds(220, 80, 55, 23);

                //---- e9 ----
                e9.setText("OFF");
                e9.setFont(e9.getFont().deriveFont(e9.getFont().getSize() + 5f));
                e9.setForeground(Color.green);
                ECGdataPanel.add(e9);
                e9.setBounds(280, 80, 55, 23);

                //---- e10 ----
                e10.setText("PVCs");
                e10.setFont(e10.getFont().deriveFont(e10.getFont().getSize() + 5f));
                e10.setForeground(Color.green);
                ECGdataPanel.add(e10);
                e10.setBounds(220, 115, 55, 23);

                //---- e11 ----
                e11.setText("OFF");
                e11.setFont(e11.getFont().deriveFont(e11.getFont().getSize() + 5f));
                e11.setForeground(Color.green);
                ECGdataPanel.add(e11);
                e11.setBounds(280, 115, 55, 23);

                //---- e0 ----
                e0.setText("HR");
                e0.setForeground(Color.green);
                e0.setFont(e0.getFont().deriveFont(e0.getFont().getSize() + 4f));
                ECGdataPanel.add(e0);
                e0.setBounds(new Rectangle(new Point(25, 25), e0.getPreferredSize()));

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < ECGdataPanel.getComponentCount(); i++) {
                        Rectangle bounds = ECGdataPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = ECGdataPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    ECGdataPanel.setMinimumSize(preferredSize);
                    ECGdataPanel.setPreferredSize(preferredSize);
                }
            }
            panel1InfoDisplay.add(ECGdataPanel);
            ECGdataPanel.setBounds(760, 45, 340, 160);

            //======== NIBPdataPanel ========
            {
                NIBPdataPanel.setBorder(new TitledBorder(null, "NIBP", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                    new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16), Color.white));
                NIBPdataPanel.setBackground(Color.black);
                NIBPdataPanel.setLayout(null);

                //---- n1 ----
                n1.setText("16:53");
                n1.setFont(n1.getFont().deriveFont(n1.getFont().getSize() + 5f));
                n1.setForeground(Color.white);
                NIBPdataPanel.add(n1);
                n1.setBounds(70, 15, 60, 23);

                //---- n2 ----
                n2.setText("2");
                n2.setFont(n2.getFont().deriveFont(n2.getFont().getSize() + 38f));
                n2.setForeground(Color.white);
                n2.setHorizontalTextPosition(SwingConstants.CENTER);
                n2.setHorizontalAlignment(SwingConstants.CENTER);
                NIBPdataPanel.add(n2);
                n2.setBounds(0, 43, 120, 67);

                //---- n3 ----
                n3.setText("/");
                n3.setForeground(Color.white);
                n3.setFont(n3.getFont().deriveFont(n3.getFont().getSize() + 38f));
                NIBPdataPanel.add(n3);
                n3.setBounds(120, 40, 21, 70);

                //---- n4 ----
                n4.setText("2");
                n4.setForeground(Color.white);
                n4.setFont(n4.getFont().deriveFont(n4.getFont().getSize() + 38f));
                n4.setHorizontalAlignment(SwingConstants.CENTER);
                NIBPdataPanel.add(n4);
                n4.setBounds(145, 40, 110, 70);

                //---- n5 ----
                n5.setText("2");
                n5.setFont(n5.getFont().deriveFont(n5.getFont().getSize() + 13f));
                n5.setForeground(Color.white);
                n5.setHorizontalAlignment(SwingConstants.CENTER);
                NIBPdataPanel.add(n5);
                n5.setBounds(150, 125, 95, 34);

                //---- n6 ----
                n6.setText("(       )");
                n6.setFont(n6.getFont().deriveFont(n6.getFont().getSize() + 13f));
                n6.setForeground(Color.white);
                NIBPdataPanel.add(n6);
                n6.setBounds(165, 125, 70, 34);

                //---- n7 ----
                n7.setText("NN");
                n7.setForeground(Color.white);
                n7.setFont(n7.getFont().deriveFont(n7.getFont().getSize() + 3f));
                NIBPdataPanel.add(n7);
                n7.setBounds(285, 30, 45, 20);

                //---- n8 ----
                n8.setText("188");
                n8.setForeground(Color.white);
                n8.setFont(n8.getFont().deriveFont(n8.getFont().getSize() + 3f));
                NIBPdataPanel.add(n8);
                n8.setBounds(285, 50, 45, 20);

                //---- n9 ----
                n9.setText("90");
                n9.setForeground(Color.white);
                n9.setFont(n9.getFont().deriveFont(n9.getFont().getSize() + 3f));
                NIBPdataPanel.add(n9);
                n9.setBounds(285, 70, 45, 20);

                //---- n10 ----
                n10.setText("\u624b\u52a8\u6d4b\u91cf...");
                n10.setForeground(Color.white);
                n10.setFont(n10.getFont().deriveFont(n10.getFont().getSize() + 4f));
                NIBPdataPanel.add(n10);
                n10.setBounds(5, 115, 90, 34);

                //======== panel1 ========
                {
                    panel1.setLayout(null);

                    //---- l1 ----
                    l1.setText("\u8896\u5e26\u538b");
                    l1.setFont(l1.getFont().deriveFont(l1.getFont().getSize() + 4f));
                    panel1.add(l1);
                    l1.setBounds(15, 60, 80, 20);

                    //---- l2 ----
                    l2.setText("000");
                    l2.setFont(l2.getFont().deriveFont(l2.getFont().getSize() + 13f));
                    panel1.add(l2);
                    l2.setBounds(90, 45, 85, 54);

                    { // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < panel1.getComponentCount(); i++) {
                            Rectangle bounds = panel1.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = panel1.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        panel1.setMinimumSize(preferredSize);
                        panel1.setPreferredSize(preferredSize);
                    }
                }
                NIBPdataPanel.add(panel1);
                panel1.setBounds(95, 45, 185, 130);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < NIBPdataPanel.getComponentCount(); i++) {
                        Rectangle bounds = NIBPdataPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = NIBPdataPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    NIBPdataPanel.setMinimumSize(preferredSize);
                    NIBPdataPanel.setPreferredSize(preferredSize);
                }
            }
            panel1InfoDisplay.add(NIBPdataPanel);
            NIBPdataPanel.setBounds(760, 205, 340, 180);

            //======== SPO2dataPanel ========
            {
                SPO2dataPanel.setBorder(new TitledBorder(null, "SPO2", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                    new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16), Color.cyan));
                SPO2dataPanel.setBackground(Color.black);
                SPO2dataPanel.setLayout(null);

                //---- s1 ----
                s1.setText("%");
                s1.setFont(s1.getFont().deriveFont(s1.getFont().getStyle() | Font.BOLD, s1.getFont().getSize() + 7f));
                s1.setForeground(Color.cyan);
                SPO2dataPanel.add(s1);
                s1.setBounds(125, 75, 30, 40);

                //---- s2 ----
                s2.setText("3");
                s2.setFont(s2.getFont().deriveFont(s2.getFont().getSize() + 45f));
                s2.setForeground(Color.cyan);
                s2.setHorizontalAlignment(SwingConstants.CENTER);
                SPO2dataPanel.add(s2);
                s2.setBounds(5, 25, 120, 91);

                //---- s3 ----
                s3.setText("2");
                s3.setFont(s3.getFont().deriveFont(s3.getFont().getSize() + 45f));
                s3.setForeground(Color.cyan);
                s3.setHorizontalAlignment(SwingConstants.LEFT);
                SPO2dataPanel.add(s3);
                s3.setBounds(180, 40, 110, 75);

                //---- s4 ----
                s4.setText("90");
                s4.setForeground(Color.cyan);
                s4.setFont(s4.getFont().deriveFont(s4.getFont().getSize() + 4f));
                SPO2dataPanel.add(s4);
                s4.setBounds(125, 45, 40, 22);

                //---- s5 ----
                s5.setText("100");
                s5.setForeground(Color.cyan);
                s5.setFont(s5.getFont().deriveFont(s5.getFont().getSize() + 4f));
                SPO2dataPanel.add(s5);
                s5.setBounds(125, 25, 40, 22);

                //---- s6 ----
                s6.setText("PR");
                s6.setFont(s6.getFont().deriveFont(s6.getFont().getSize() + 10f));
                s6.setForeground(Color.cyan);
                SPO2dataPanel.add(s6);
                s6.setBounds(175, 10, 45, 30);

                //---- s7 ----
                s7.setText("120");
                s7.setForeground(Color.cyan);
                s7.setFont(s7.getFont().deriveFont(s7.getFont().getSize() + 4f));
                SPO2dataPanel.add(s7);
                s7.setBounds(295, 20, 35, 22);

                //---- s8 ----
                s8.setText("50");
                s8.setForeground(Color.cyan);
                s8.setFont(s8.getFont().deriveFont(s8.getFont().getSize() + 4f));
                SPO2dataPanel.add(s8);
                s8.setBounds(295, 45, 35, 22);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < SPO2dataPanel.getComponentCount(); i++) {
                        Rectangle bounds = SPO2dataPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = SPO2dataPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    SPO2dataPanel.setMinimumSize(preferredSize);
                    SPO2dataPanel.setPreferredSize(preferredSize);
                }
            }
            panel1InfoDisplay.add(SPO2dataPanel);
            SPO2dataPanel.setBounds(760, 385, 340, 120);

            //======== RESPdataPanel ========
            {
                RESPdataPanel.setBorder(new TitledBorder(null, "RESP", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                    new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16), Color.yellow));
                RESPdataPanel.setBackground(Color.black);
                RESPdataPanel.setLayout(null);

                //---- r1 ----
                r1.setText("2");
                r1.setFont(r1.getFont().deriveFont(r1.getFont().getStyle() & ~Font.BOLD, r1.getFont().getSize() + 42f));
                r1.setForeground(Color.yellow);
                r1.setHorizontalAlignment(SwingConstants.CENTER);
                RESPdataPanel.add(r1);
                r1.setBounds(5, 20, 125, 78);

                //---- r2 ----
                r2.setText("30");
                r2.setForeground(Color.yellow);
                r2.setFont(r2.getFont().deriveFont(r2.getFont().getSize() + 4f));
                RESPdataPanel.add(r2);
                r2.setBounds(145, 15, 35, 22);

                //---- r3 ----
                r3.setText("8");
                r3.setForeground(Color.yellow);
                r3.setFont(r3.getFont().deriveFont(r3.getFont().getSize() + 4f));
                RESPdataPanel.add(r3);
                r3.setBounds(145, 40, 35, 22);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < RESPdataPanel.getComponentCount(); i++) {
                        Rectangle bounds = RESPdataPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = RESPdataPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    RESPdataPanel.setMinimumSize(preferredSize);
                    RESPdataPanel.setPreferredSize(preferredSize);
                }
            }
            panel1InfoDisplay.add(RESPdataPanel);
            RESPdataPanel.setBounds(760, 505, 340, 105);

            //======== panel3 ========
            {
                panel3.setBackground(Color.black);
                panel3.setBorder(new TitledBorder(null, "TEMP", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                    new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 16), Color.white));
                panel3.setLayout(null);

                //---- t1 ----
                t1.setText("T1");
                t1.setFont(t1.getFont().deriveFont(t1.getFont().getSize() + 6f));
                t1.setForeground(Color.white);
                panel3.add(t1);
                t1.setBounds(10, 35, 26, 35);

                //---- t2 ----
                t2.setText("2");
                t2.setFont(t2.getFont().deriveFont(t2.getFont().getSize() + 26f));
                t2.setForeground(Color.white);
                panel3.add(t2);
                t2.setBounds(40, 25, 110, 45);

                //---- t3 ----
                t3.setText("T2");
                t3.setFont(t3.getFont().deriveFont(t3.getFont().getSize() + 6f));
                t3.setForeground(Color.white);
                panel3.add(t3);
                t3.setBounds(10, 95, 26, 35);

                //---- t4 ----
                t4.setText("2");
                t4.setFont(t4.getFont().deriveFont(t4.getFont().getSize() + 26f));
                t4.setForeground(Color.white);
                panel3.add(t4);
                t4.setBounds(45, 90, 110, 50);

                //---- t5 ----
                t5.setText("39.0");
                t5.setForeground(Color.white);
                t5.setFont(t5.getFont().deriveFont(t5.getFont().getSize() + 6f));
                panel3.add(t5);
                t5.setBounds(155, 25, 40, 24);

                //---- t6 ----
                t6.setText("\u2103");
                t6.setForeground(Color.white);
                t6.setFont(t6.getFont().deriveFont(t6.getFont().getSize() + 8f));
                panel3.add(t6);
                t6.setBounds(200, 15, 35, 25);

                //---- t7 ----
                t7.setText("36.0");
                t7.setForeground(Color.white);
                t7.setFont(t7.getFont().deriveFont(t7.getFont().getSize() + 6f));
                panel3.add(t7);
                t7.setBounds(155, 50, 40, 24);

                //---- t8 ----
                t8.setText("39.0");
                t8.setForeground(Color.white);
                t8.setFont(t8.getFont().deriveFont(t8.getFont().getSize() + 6f));
                panel3.add(t8);
                t8.setBounds(155, 90, 40, 24);

                //---- t9 ----
                t9.setText("36.0");
                t9.setForeground(Color.white);
                t9.setFont(t9.getFont().deriveFont(t9.getFont().getSize() + 6f));
                panel3.add(t9);
                t9.setBounds(155, 115, 40, 24);

                //---- t10 ----
                t10.setText("TD");
                t10.setForeground(Color.white);
                t10.setFont(t10.getFont().deriveFont(t10.getFont().getSize() + 5f));
                panel3.add(t10);
                t10.setBounds(235, 65, 45, 23);

                //---- t11 ----
                t11.setText("2.0");
                t11.setForeground(Color.white);
                t11.setFont(t11.getFont().deriveFont(t11.getFont().getSize() + 5f));
                panel3.add(t11);
                t11.setBounds(285, 100, 45, 28);

                //---- t12 ----
                t12.setText("0.0");
                t12.setForeground(Color.white);
                t12.setFont(t12.getFont().deriveFont(t12.getFont().getSize() + 5f));
                panel3.add(t12);
                t12.setBounds(235, 100, 45, 28);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel3.getComponentCount(); i++) {
                        Rectangle bounds = panel3.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel3.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel3.setMinimumSize(preferredSize);
                    panel3.setPreferredSize(preferredSize);
                }
            }
            panel1InfoDisplay.add(panel3);
            panel3.setBounds(760, 610, 340, 145);

            //======== panel2 ========
            {
                panel2.setBackground(Color.black);
                panel2.setLayout(null);

                //---- warningText ----
                warningText.setBackground(Color.darkGray);
                panel2.add(warningText);
                warningText.setBounds(695, 5, 140, 35);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel2.getComponentCount(); i++) {
                        Rectangle bounds = panel2.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel2.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel2.setMinimumSize(preferredSize);
                    panel2.setPreferredSize(preferredSize);
                }
            }
            panel1InfoDisplay.add(panel2);
            panel2.setBounds(0, 0, 1115, 45);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1InfoDisplay.getComponentCount(); i++) {
                    Rectangle bounds = panel1InfoDisplay.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1InfoDisplay.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1InfoDisplay.setMinimumSize(preferredSize);
                panel1InfoDisplay.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1InfoDisplay, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1InfoDisplay;
    private MedicalWaveDisplayPanel ECGwavePanel1;
    private MedicalWaveDisplayPanel ECGwavePanel2;
    private MedicalWaveDisplayPanel Spo2WavePanel;
    private MedicalWaveDisplayPanel RESPwavePanel;
    private JPanel ECGdataPanel;
    private JLabel e1;
    private JLabel e2;
    private JLabel e3;
    private JLabel e4;
    private JLabel e5;
    private JLabel e6;
    private JLabel e7;
    private JLabel e8;
    private JLabel e9;
    private JLabel e10;
    private JLabel e11;
    private JLabel e0;
    private JPanel NIBPdataPanel;
    private JLabel n1;
    private JLabel n2;
    private JLabel n3;
    private JLabel n4;
    private JLabel n5;
    private JLabel n6;
    private JLabel n7;
    private JLabel n8;
    private JLabel n9;
    private JLabel n10;
    private JPanel panel1;
    private JLabel l1;
    private JLabel l2;
    private JPanel SPO2dataPanel;
    private JLabel s1;
    private JLabel s2;
    private JLabel s3;
    private JLabel s4;
    private JLabel s5;
    private JLabel s6;
    private JLabel s7;
    private JLabel s8;
    private JPanel RESPdataPanel;
    private JLabel r1;
    private JLabel r2;
    private JLabel r3;
    private JPanel panel3;
    private JLabel t1;
    private JLabel t2;
    private JLabel t3;
    private JLabel t4;
    private JLabel t5;
    private JLabel t6;
    private JLabel t7;
    private JLabel t8;
    private JLabel t9;
    private JLabel t10;
    private JLabel t11;
    private JLabel t12;
    private JPanel panel2;
    private JTextField warningText;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void DrawStart()//绘制方格
    {
        this.ECGwavePanel1.DispStart();
        this.ECGwavePanel2.DispStart();
        this.Spo2WavePanel.DispStart();
        this.RESPwavePanel.DispStart();
        return;
    }

    public void setSPO2wavedata(int data1)
    {
        this.Spo2WavePanel.putSPO2data(data1);
        this.SetAllText();
    }

    public void setRESPwavedata(int data2)
    {
        this.RESPwavePanel.putRESP(data2);
        this.SetAllText();
    }

    public void setECGwavedata(int data1,int data2)
    {
        this.ECGwavePanel1.putECGdata(data2);
        this.ECGwavePanel2.putECGdata(data1);
        this.SetAllText();
    }

 /*   //将数据传过来并设置比例、显示
    public void setSPO2_ECG_RESPwavedata(int data1,int[] data2)
    {
        int[] b1 = data1;
        int[] b2 = data2;
     //   int[] b3 = data3;
      this.Spo2WavePanel.SetSpo2WaveData(b1);
      this.RESPwavePanel.SetRESPWaveData(b2);
     // this.ECGwavePanel1.SetECG_WaveData(b2);

        this.SetAllText();
    }*/
 //显示数据
    public void SetAllText()
    {
        //NIBP模块
        n2.setText(String.valueOf(sbp));
        n4.setText(String.valueOf(dbp));
        n5.setText(String.valueOf(map));
        if(pre != 0)
        {
            panel1.setVisible(true);
            l2.setText(String.valueOf(pre));
        }

        //ECG模块
        e1.setText(String.valueOf(hr));
    //    System.out.println(" hr =  "+hr);

        //SPO2
        s2.setText(String.valueOf(spo2));
        s3.setText(String.valueOf(pr));
 //       System.out.println("spo2 = "+spo2 +"    pr = "+pr);



        //RESP
        r1.setText(String.valueOf(rr));
    //    System.out.println(" rr = "+rr);

        //TEMP
        t2.setText(String.valueOf(bt1));
        t4.setText(String.valueOf(bt2));
    //    System.out.println(" bt1 = "+bt1+"    bt2 = "+bt2);
    }

    public void run()
    {
        int _count = 0;
        while (go_thread)
        {
            try
            {
            //    setSPO2_ECG_RESPwavedata(para_spo2.getSPO2_WAVE(),para_resp.getRESP_WAVE());

                System.out.println("Sleep Count"+_count);
                Thread.sleep(0);      //every millisecond to check the windows is showing,

            }catch(InterruptedException e)
            {
                go_thread = false;
            }
            _count ++;
        }
        return;
    }
    public void DrawStop()
    {
        // this.m_MedInforPanel.DispStop();
        return;
    }
}
