package jp.mincra.mincramagics.container;

import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.List;

public class MagicCircle {

    /**
     * @param type 魔法陣の形状の番号
     * @param radius 魔法陣の半径
     * @param particleList 使用するparticleのリスト(typeによって使用するものが変化)
     */
    private int type = 1;
    private double radius = 1;
    private List<Particle> particleList = new ArrayList<>();

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Particle> getParticleList() {
        return particleList;
    }

    public void setParticle(int index, Particle particle) {
        particleList.add(index,particle);
    }
}
