package jp.mincra.mincramagics.container;

public class MagicPoint {

    private float value = 20;

    private float max = 20;
    private float max_sum = 0;
    private float max_scale = 1;

    //MP回復
    private float recover_sum = 1;
    private float recover_scale = 1;

    //カプセル化部分
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public float getMax() {
        return max;
    }


    public void setMax() {
        max = 20;
    }

    public void addMax_sum(float amount) {
        max_sum = max_sum + amount;
    }

    public void addMax_scale(float amount) {
        max_scale = max_scale + amount;
    }

    public void addRecover_sum(float amount) {
        recover_sum = recover_sum + amount;
    }

    public void addRecover_scale(float amount) {
        recover_scale = recover_scale + amount;
    }

    //メインのメソッド
    public void recover() {
        value = value + recover_sum * recover_scale;
    }

    public boolean has(float amount) {
        return value >= amount;
    }
}
