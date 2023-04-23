package Visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Gacha.Banner;
import Gacha.Gacha;

public class mainWindows extends JFrame implements ActionListener {
    private String[] seeleLimited_5 = { "Seele" };
    private String[] seeleLimited_4 = { "Hook", "March_7th", "Herta" };
    private Banner bannerSeelePull = new Banner(seeleLimited_5, seeleLimited_4);

    private JPanel mainPanel;
    private JPanel panelPull;
    private JPanel panelButton;

    private JButton one;
    private JButton ten;
    private JButton back;

    private ImageIcon bannerSeele;
    private Image tempo;
    private JLabel banner;

    private ArrayList<String> result;

    private int index = 0;

    private boolean ini = true;
    private boolean wait = false;

    private ArrayList<String> soundList = new ArrayList<>();

    public mainWindows() {
        initComponents();
        initSound();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setTitle("Copium : Star Rail edition");
        Image bronya = Toolkit.getDefaultToolkit().getImage("Perso/Bronya.png");
        setIconImage(bronya);
    }

    private void initSound() {
        soundList.add("Seele");
        soundList.add("Herta");
        soundList.add("Bronya");
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.black);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        /* Zone de pull */
        panelPull = new JPanel();
        bannerSeele = new ImageIcon("BannerPromo/Seele.jpg");
        tempo = bannerSeele.getImage().getScaledInstance(1280, 600, Image.SCALE_SMOOTH);
        bannerSeele = new ImageIcon(tempo);
        banner = new JLabel(bannerSeele);
        panelPull.add(banner);

        /* Zone de bouton */
        panelButton = new JPanel();
        panelButton.setLayout(new FlowLayout(FlowLayout.CENTER));

        one = new JButton("Pull x1");
        one.addActionListener(this);
        ten = new JButton("Pull x10");
        ten.addActionListener(this);

        panelButton.add(one);
        panelButton.add(ten);
        panelButton.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        /* Add final */
        mainPanel.add(panelPull);
        mainPanel.add(panelButton);

        add(mainPanel);
        revalidate();
        repaint();

    }

    public void actionPerformed(ActionEvent e) {
        if (!wait) {
            if (e.getSource() == one) {
                String resultat = Gacha.pull(bannerSeelePull);
                System.out.println(resultat);
                panelPull.removeAll();
                if (ini) {
                    back = new JButton("Back");
                    back.addActionListener(this);
                    panelButton.add(back);
                    panelPull.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
                    ini = false;
                }
                ImageIcon resultPull = new ImageIcon("Perso/" + resultat + ".png");
                JLabel resultCase = new JLabel(resultPull);
                panelPull.add(resultCase);
                panelPull.revalidate();
                panelPull.repaint();
            }
            if (e.getSource() == ten) {
                wait = true;
                result = Gacha.multiPull(bannerSeelePull);
                panelPull.removeAll();
                panelPull.setLayout(new GridLayout(2, 5));
                panelPull.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
                if (ini) {
                    back = new JButton("Back");
                    back.addActionListener(this);
                    panelButton.add(back);
                    ini = false;
                }

                index = 0;

                Timer timer = new Timer(150, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ImageIcon resultPull = new ImageIcon("Perso/" + result.get(index) + ".png");
                        if (soundList.contains(result.get(index))) {
                            File sound = new File("Sound/" + result.get(index) + ".wav");
                            try {
                                Clip clip = AudioSystem.getClip();
                                clip.open(AudioSystem.getAudioInputStream(sound));
                                clip.start();
                            } catch (Exception y) {
                                y.printStackTrace();
                            }
                        }

                        JLabel resultCase = new JLabel(resultPull);
                        panelPull.add(resultCase);
                        index++;
                        if (index >= result.size()) {
                            ((Timer) e.getSource()).stop();
                            wait = false;
                        }
                        panelPull.revalidate();
                        panelPull.repaint();

                    }
                });
                timer.start();

            }
            if (e.getSource() == back) {
                ini = true;
                mainPanel.removeAll();
                ;
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

                /* Zone de pull */
                panelPull = new JPanel();
                bannerSeele = new ImageIcon("BannerPromo/Seele.jpg");
                tempo = bannerSeele.getImage().getScaledInstance(1280, 600, Image.SCALE_SMOOTH);
                bannerSeele = new ImageIcon(tempo);
                banner = new JLabel(bannerSeele);
                panelPull.add(banner);

                /* Zone de bouton */
                panelButton = new JPanel();
                panelButton.setLayout(new FlowLayout(FlowLayout.CENTER));

                one = new JButton("Pull x1");
                one.addActionListener(this);
                ten = new JButton("Pull x10");
                ten.addActionListener(this);

                panelButton.add(one);
                panelButton.add(ten);
                panelButton.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

                /* Add final */
                mainPanel.add(panelPull);
                mainPanel.add(panelButton);

                add(mainPanel);
                revalidate();
                repaint();
            }
        }

    }

    public static void main(String[] args) {
        mainWindows fenetre = new mainWindows();
        fenetre.setVisible(true);
    }

}
