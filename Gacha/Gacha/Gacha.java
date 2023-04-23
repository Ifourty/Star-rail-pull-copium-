package Gacha;

import java.util.ArrayList;
import java.util.Random;

public class Gacha {
    private static final String[] standard_5 = { "Bronya", "Himeko", "Welt", "Gepard", "Clara", "Yanqing",
            "Bailu" };
    private static final String[] standard_4 = { "Sampo", "Herta", "Serval", "Natasha", "Hook", "Arlan",
            "Asta", "Dan_Heng", "March_7th", "Tingyun", "Sushang", "Qingque" };
    private static final String[] standard_3 = { "1", "2", "3", "4",
            "5" };

    private static String gachaGen(String[] tab) {
        String result;
        Random random = new Random();
        result = tab[random.nextInt(tab.length)];
        return result;
    }

    public static String pull(Banner banner) {
        String result;
        double rand = Math.random() * 100;
        double isRatedUpint = Math.random() * 2;
        boolean isRatedUp = false;
        if (isRatedUpint > 1) {
            isRatedUp = true;
        }
        if (banner == null) {
            banner = new Banner(standard_5, standard_4);
        }
        if (rand < 0.6) {
            result = isRatedUp ? gachaGen(banner.getLimited_5()) : gachaGen(standard_5);
        } else if (rand < 4.6) {
            result = isRatedUp ? gachaGen(banner.getLimited_4()) : gachaGen(standard_4);
        } else {
            result = gachaGen(standard_3);
        }
        return result;
    }

    public static ArrayList<String> multiPull(Banner banner) {
        ArrayList<String> result = new ArrayList<>();
        Boolean isNon3star = false;
        for (int i = 0; i < 10; i++) {
            double rand = Math.random() * 100;
            double isRatedUpint = Math.random() * 2;
            boolean isRatedUp = false;
            if (isRatedUpint > 1) {
                isRatedUp = true;
            }
            if (banner == null) {
                banner = new Banner(standard_5, standard_4);
            }
            if (rand < 0.6) {
                result.add((isRatedUp ? gachaGen(banner.getLimited_5()) : gachaGen(standard_5)));
            } else if (rand < 5.1) {
                result.add((isRatedUp ? gachaGen(banner.getLimited_4()) : gachaGen(standard_4)));
            } else {
                result.add(gachaGen(standard_3));
            }
        }
        boolean present;
        String compa;
        for (String n : result) {
            present = false;
            for (int i = 0; i < standard_3.length; i++) {
                compa = standard_3[i];
                if (compa.equals(n)) {
                    present = true;
                }
            }
            if (!present) {
                isNon3star = true;
            }
        }

        if (!isNon3star) {
            double isRatedUpint = Math.random() * 2;
            boolean isRatedUp = false;
            if (isRatedUpint > 1) {
                isRatedUp = true;
            }
            result.remove(9);
            result.add((isRatedUp ? gachaGen(banner.getLimited_4()) : gachaGen(standard_4)));
        }
        return (result);
    }
}
