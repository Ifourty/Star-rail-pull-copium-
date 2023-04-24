package Class.Gacha;

import java.util.ArrayList;
import java.util.Random;

public class Gacha {
    private static final String[] standard_5 = { "Bronya", "Himeko", "Welt", "Gepard", "Clara", "Yanqing",
            "Bailu" };
    private static final String[] standard_4 = { "Sampo", "Herta", "Serval", "Natasha", "Hook", "Arlan",
            "Asta", "Dan_Heng", "March_7th", "Tingyun", "Sushang", "Qingque" };
    private static final String[] standard_3 = { "1", "2", "3", "4",
            "5" };

    private static int pity = 0; // pity
    private static boolean safeFive = false; // sécure limited 5*
    private static boolean safeFour = false; // sécure limited 4*

    private static final double perFive = 0.6;
    private static final double perFour = 5.1;

    private static String gachaGen(String[] tab) {
        String result;
        Random random = new Random();
        result = tab[random.nextInt(tab.length)];
        return result;
    }

    public static String pull(Banner banner) {
        pity++;
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
        if (rand < perFive) {
            if (isRatedUp || safeFive) {
                result = gachaGen(banner.getLimited_5());
                safeFive = false;
            } else {
                result = gachaGen(standard_5);
                safeFive = true;
            }
        } else if (rand < perFour) {
            if (isRatedUp || safeFour) {
                result = gachaGen(banner.getLimited_4());
                safeFour = false;
            } else {
                result = gachaGen(standard_4);
                safeFour = true;
            }
        } else {
            result = gachaGen(standard_3);
        }
        return result;
    }

    public static ArrayList<String> multiPull(Banner banner) {
        ArrayList<String> result = new ArrayList<>();
        Boolean isNon3star = false;
        for (int i = 0; i < 10; i++) {
            pity++;
            double rand = Math.random() * 100;
            double isRatedUpint = Math.random() * 2;
            boolean isRatedUp = false;
            if (isRatedUpint > 1) {
                isRatedUp = true;
            }
            if (banner == null) {
                banner = new Banner(standard_5, standard_4);
            }
            /* Soft pity */
            if (pity == 75) {
                double soft = Math.random() * 5;
                if (soft == 1) {
                    if (isRatedUp || safeFive) {
                        result.add(gachaGen(banner.getLimited_5()));
                        safeFive = false;
                    } else {
                        result.add(gachaGen(standard_5));
                        safeFive = true;
                    }
                    pity = 0;
                } else {
                    if (rand < perFive) {
                        if (isRatedUp || safeFive) {
                            result.add(gachaGen(banner.getLimited_5()));
                            safeFive = false;
                        } else {
                            result.add(gachaGen(standard_5));
                            safeFive = true;
                        }
                        pity = 0;
                    } else if (rand < perFour) {
                        if (isRatedUp || safeFour) {
                            result.add(gachaGen(banner.getLimited_4()));
                            safeFour = false;
                        } else {
                            result.add(gachaGen(standard_4));
                            safeFour = true;
                        }
                    } else {
                        result.add(gachaGen(standard_3));
                    }
                }
                /* Hard pity */
            } else if (pity == 90) {
                if (isRatedUp || safeFive) {
                    result.add(gachaGen(banner.getLimited_5()));
                    safeFive = false;
                } else {
                    result.add(gachaGen(standard_5));
                    safeFive = true;
                }
                pity = 0;
                /* Aucune pity */
            } else {
                if (rand < perFive) {
                    if (isRatedUp || safeFive) {
                        result.add(gachaGen(banner.getLimited_5()));
                        safeFive = false;
                    } else {
                        result.add(gachaGen(standard_5));
                        safeFive = true;
                    }
                    pity = 0;
                } else if (rand < perFour) {
                    if (isRatedUp || safeFour) {
                        result.add(gachaGen(banner.getLimited_4()));
                        safeFour = false;
                    } else {
                        result.add(gachaGen(standard_4));
                        safeFour = true;
                    }
                } else {
                    result.add(gachaGen(standard_3));
                }
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
