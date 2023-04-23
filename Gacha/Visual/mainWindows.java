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

    private String[] kafkaLimited_5 = { "Kafka" };
    private String[] kafkaLimited_4 = { "Herta", "Qingque", "Tingyun" };
    private Banner bannerKafkaPull = new Banner(kafkaLimited_5, kafkaLimited_4);

    private String[] fuXuanLimited_5 = { "Fu_Xuan" };
    private String[] fuXuanLimited_4 = { "Sushang", "Qingque", "Arlan" };
    private Banner bannerFuXuanPull = new Banner(fuXuanLimited_5, fuXuanLimited_4);

    private int actualConfig = 0;

    private JPanel mainPanel;
    private JPanel panelPull;
    private JPanel panelButton;

    private JButton one;
    private JButton ten;
    private JButton back;
    private JButton next;
    private JButton previous;

    private ImageIcon bannerSeele;
    private Image tempo;
    private JLabel banner;

    private ArrayList<String> result;

    private int index = 0;

    private boolean ini = true;
    private boolean wait = false;

    private ArrayList<String> soundList = new ArrayList<>();

    private String resultat;

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
        soundList.add("Kafka");
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

        previous = new JButton("Previous");
        previous.addActionListener(this);
        one = new JButton("Pull x1");
        one.addActionListener(this);
        ten = new JButton("Pull x10");
        ten.addActionListener(this);
        next = new JButton("Next");
        next.addActionListener(this);

        panelButton.add(one);
        panelButton.add(ten);
        panelButton.add(next);
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
                switch (actualConfig) {
                    case 0:
                        resultat = Gacha.pull(bannerSeelePull);
                        break;
                    case 1:
                        resultat = Gacha.pull(bannerKafkaPull);
                        break;
                    case 2:
                        resultat = Gacha.pull(bannerFuXuanPull);
                        break;
                }
                panelButton.removeAll();
                panelButton.add(one);
                panelButton.add(ten);
                back = new JButton("Back");
                back.addActionListener(this);
                panelButton.add(back);
                panelPull.removeAll();
                if (ini) {
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
                switch (actualConfig) {
                    case 0:
                        result = Gacha.multiPull(bannerSeelePull);
                        break;
                    case 1:
                        result = Gacha.multiPull(bannerKafkaPull);
                        break;
                    case 2:
                        result = Gacha.multiPull(bannerFuXuanPull);
                        break;
                }
                panelButton.removeAll();
                panelButton.add(one);
                panelButton.add(ten);
                back = new JButton("Back");
                back.addActionListener(this);
                panelButton.add(back);
                panelPull.removeAll();
                panelPull.setLayout(new GridLayout(2, 5));
                panelPull.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
                if (ini) {
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
                reload();
            }
            if (e.getSource() == next) {
                actualConfig++;
                reload();
            }
            if (e.getSource() == previous) {
                actualConfig--;
                reload();
            }
        }

    }

    private void reload() {
        ini = true;
        mainPanel.removeAll();
        ;
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        /* Zone de pull */
        panelPull = new JPanel();
        panelButton = new JPanel();
        switch (actualConfig) {
            case 0: // Fenetre Seele
                bannerSeele = new ImageIcon("BannerPromo/Seele.jpg");
                panelButton.add(one);
                panelButton.add(ten);
                panelButton.add(next);
                break;
            case 1: // Fenetre Kafka
                bannerSeele = new ImageIcon("BannerPromo/Kafka.png");
                panelButton.add(previous);
                panelButton.add(one);
                panelButton.add(ten);
                panelButton.add(next);
                break;
            case 2: // Fenetre Fu Xuan
                bannerSeele = new ImageIcon("BannerPromo/Fu_Xuan.png");
                previous = new JButton("Previous");
                previous.addActionListener(this);
                panelButton.add(previous);
                panelButton.add(one);
                panelButton.add(ten);
                break;

        }
        tempo = bannerSeele.getImage().getScaledInstance(1280, 600, Image.SCALE_SMOOTH);
        bannerSeele = new ImageIcon(tempo);
        banner = new JLabel(bannerSeele);
        panelPull.add(banner);

        /* Zone de bouton */

        panelButton.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelButton.setBorder(BorderFactory.createLineBorder(Color.gray, 5));

        /* Add final */
        mainPanel.add(panelPull);
        mainPanel.add(panelButton);

        add(mainPanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        mainWindows fenetre = new mainWindows();
        fenetre.setVisible(true);
    }

}
