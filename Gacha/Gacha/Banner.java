package Gacha;

public class Banner {
    private String[] limited_5;
    private String[] limited_4;

    public Banner(String[] limited_5, String[] limited_4) {
        this.limited_5 = limited_5;
        this.limited_4 = limited_4;
    }

    public String[] getLimited_4() {
        return limited_4;
    }

    public String[] getLimited_5() {
        return limited_5;
    }

    public void setLimited_4(String[] limited_4) {
        this.limited_4 = limited_4;
    }

    public void setLimited_5(String[] limited_5) {
        this.limited_5 = limited_5;
    }
}
