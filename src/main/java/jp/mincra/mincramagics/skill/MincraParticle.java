package jp.mincra.mincramagics.skill;

import jp.mincra.mincramagics.util.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MincraParticle {

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

    public void drawLine(Particle particle, Player player, Location loc1, Location loc2, int count, double speed, double distanceBetween, double offsetX, double offsetY, double offsetZ) {
        //https://www.spigotmc.org/threads/drawing-particle-line.446101/
        Vector vector = getDirectionBetweenLocations(loc1,loc2).normalize(); //make sure it has length one at start
        for (double i = 0, dis = loc1.distance(loc2); i < dis; i += distanceBetween) {
            Vector addition = new Vector().copy(vector).multiply(i);
            Location newLoc = loc1.clone().add(addition);

            player.spawnParticle(particle, newLoc, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    public void drawLine(Particle particle, Player player, Location loc1, Location loc2, int count, double speed, double distanceBetween) {
        drawLine(particle, player, loc1, loc2, count, speed, distanceBetween, 0, 0, 0);
    }

    public void drawCircle(Particle particle, Player player, Location location, int count, double offsetX, double offsetY, double offsetZ, double speed, double radius, int amount) {
        double increment = (2 * Math.PI) / amount;
        for(int i = 0;i < amount; i++)
        {
            double angle = i * increment;
            double x = location.getX() + (radius * Math.cos(angle));
            double z = location.getZ() + (radius * Math.sin(angle));

            player.spawnParticle(particle, x, location.getY(), z, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    public void drawCircle(Particle particle, Player player, Location location, int count, double speed, double radius, int amount) {
        drawCircle(particle, player, location, count, 0, 0, 0, speed, radius, amount);
    }

    public void drawMagicCircle(Player player, Location location) {
        switch (type) {
            //普通の五芒星
            case 1:
                //円
                Particle particle = particleList.get(0);
                drawCircle(particle, player, location, 1, 0.0, 1.0 * radius, (int) (80 * radius));
                drawCircle(particle, player, location, 1, 0.0, 1.3 * radius, (int) (104 * radius));

                //五角形の頂点の座標を取得
                List<Vector> vectorList = getVertexCoordinate(5, radius);
                List<Location> locationList = new ArrayList<>();
                ChatUtil.sendConsoleMessage(vectorList.get(0).getX()+" "+vectorList.get(2).getX());

                for (int i=0; i<5; i++) {
                    Location tempLocation = player.getLocation();
                    tempLocation.setX(location.getX());
                    tempLocation.setY(location.getY());
                    tempLocation.setZ(location.getZ());

                    tempLocation.add(vectorList.get(i));

                    locationList.add(tempLocation);
                }

                for (int i=0; i<5; i++) {
                    ChatUtil.sendConsoleMessage(i+" x:"+locationList.get(i).getX()+" z:"+locationList.get(i).getZ()+" y:"+locationList.get(i).getY());
                }

                //直線
                drawLine(particle,player,locationList.get(0),locationList.get(2),1,0.0,0.2);
                drawLine(particle,player,locationList.get(0),locationList.get(3),1,0.0,0.2);
                drawLine(particle,player,locationList.get(1),locationList.get(3),1,0.0,0.2);
                drawLine(particle,player,locationList.get(1),locationList.get(4),1,0.0,0.2);
                drawLine(particle,player,locationList.get(2),locationList.get(4),1,0.0,0.2);
        }
    }


    /**
     * 半径radiusの多角形の頂点の座標を取得します。
     * @param vertex 頂点の数
     * @param radius 半径
     * @return VectorのList
     */
    List<Vector> getVertexCoordinate(int vertex, double radius) {
        List<Vector> vectorList = new ArrayList<>();
        double angle = Math.PI * 2 / vertex;

        for (int i = 0; i < vertex; i++) {
            double sin = Math.sin(angle*i) * radius;
            double cos = Math.cos(angle*i) * radius;

            Vector vector = new Vector(sin,0,cos);

            vectorList.add(vector);
        }

        return vectorList;
    }

    Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }
}
