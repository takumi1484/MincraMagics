package jp.mincra.mincramagics.container;

public class MincraSkill {

    private String id = "Unknown";
    private String name = "";
    private float cooltime = 0f;
    private int exp_lv = 0;
    private int exp = 0;
    private int break_rate = 0;

    public int getBreak_rate() {
        return break_rate;
    }

    public void setBreak_rate(int break_rate) {
        this.break_rate = break_rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp_lv() {
        return exp_lv;
    }

    public void setExp_lv(int exp_lv) {
        this.exp_lv = exp_lv;
    }

    public float getCooltime() {
        return cooltime;
    }

    public void setCooltime(float cooltime) {
        this.cooltime = cooltime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
