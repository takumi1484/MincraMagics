package jp.mincra.mincramagics.util;

import jp.mincra.mincramagics.container.MagicCircle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleUtil {

    public static void drawLine(Particle particle, Player player, Location loc1, Location loc2, int count, double speed, double distanceBetween, double offsetX, double offsetY, double offsetZ) {
        //https://www.spigotmc.org/threads/drawing-particle-line.446101/
        Vector vector = getDirectionBetweenLocations(loc1,loc2).normalize(); //make sure it has length one at start
        for (double i = 0, dis = loc1.distance(loc2); i < dis; i += distanceBetween) {
            Vector addition = new Vector().copy(vector).multiply(i);
            Location newLoc = loc1.clone().add(addition);

            player.spawnParticle(particle, newLoc, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    public static void drawLine(Particle particle, Player player, Location loc1, Location loc2, int count, double speed, double distanceBetween) {
        drawLine(particle, player, loc1, loc2, count, speed, distanceBetween, 0, 0, 0);
    }

    public static void drawCircle(Particle particle, Player player, Location location, int count, double offsetX, double offsetY, double offsetZ, double speed, double radius, int amount) {
        double increment = (2 * Math.PI) / amount;
        for(int i = 0;i < amount; i++)
        {
            double angle = i * increment;
            double x = location.getX() + (radius * Math.cos(angle));
            double z = location.getZ() + (radius * Math.sin(angle));

            player.spawnParticle(particle, x, location.getY(), z, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    public static void drawCircle(Particle particle, Player player, Location location, int count, double speed, double radius, int amount) {
        drawCircle(particle, player, location, count, 0, 0, 0, speed, radius, amount);
    }

    public static void drawMagicCircle(MagicCircle magicCircle, Player player, Location location) {
        switch (magicCircle.getType()) {
            //普通の五芒星
            case 1:
                //円
                Particle particle = magicCircle.getParticleList().get(0);
                drawCircle(particle, player, location, 1, 0.01, 0.01, 0.01, 0.02, 1.0 * magicCircle.getRadius(), (int) (100 * magicCircle.getRadius()));
                drawCircle(particle, player, location, 1, 0.01, 0.01, 0.01, 0.02, 1.3 * magicCircle.getRadius(), (int) (120 * magicCircle.getRadius()));

                //五角形の頂点のリスト
                List<Location> locationList = new ArrayList<>();
                Location tempLocation;
                Vector tempVector;
                location = player.getLocation();

                for (int i = 0; i < 5; i++) {
                    ChatUtil.sendConsoleMessage("cos:"+Math.cos(72*i)+" sin:"+Math.sin(72*i));
                    tempLocation = location;
                    tempVector = new Vector();
                    tempVector.setX(Math.cos(72*i));
                    tempVector.setZ(Math.sin(72*i));
                    tempVector.multiply(magicCircle.getRadius());

                    tempLocation.add(tempVector);

                    ChatUtil.sendConsoleMessage(i+"素X:"+location.getX()+" X:"+tempLocation.getX()+" Z:"+tempLocation.getZ());

                    locationList.add(tempLocation);
                }

                tempLocation = locationList.get(2);

                ChatUtil.sendConsoleMessage("素X:"+location.getX()+" X:"+tempLocation.getX()+" Z:"+tempLocation.getZ());

                //
                /*
                Location location1 = location;
                tempVector.setX(Math.cos(72*0));
                tempVector.setZ(Math.sin(72*0));
                tempVector.multiply(magicCircle.getRadius());
                location1.add(tempVector);

                Location location2 = location;
                tempVector.setX(Math.cos(72*1));
                tempVector.setZ(Math.sin(72*1));
                tempVector.multiply(magicCircle.getRadius());
                location2.add(tempVector);

                Location location3 = location;
                tempVector.setX(Math.cos(72*2));
                tempVector.setZ(Math.sin(72*2));
                tempVector.multiply(magicCircle.getRadius());
                location3.add(tempVector);

                Location location4 = location;
                tempVector.setX(Math.cos(72*3));
                tempVector.setZ(Math.sin(72*3));
                tempVector.multiply(magicCircle.getRadius());
                location4.add(tempVector);

                Location location5 = location;
                tempVector.setX(Math.cos(72*4));
                tempVector.setZ(Math.sin(72*4));
                tempVector.multiply(magicCircle.getRadius());
                location5.add(tempVector);

                drawLine(particle,player,location1,location3,1,0.02,0.1);
                drawLine(particle,player,location1,location4,1,0.02,0.1);
                drawLine(particle,player,location2,location4,1,0.02,0.1);
                drawLine(particle,player,location2,location5,1,0.02,0.1);
                drawLine(particle,player,location3,location5,1,0.02,0.1);

                */
                //直線
                drawLine(particle,player,locationList.get(0),locationList.get(2),1,0.02,0.1);
                drawLine(particle,player,locationList.get(0),locationList.get(3),1,0.02,0.1);
                drawLine(particle,player,locationList.get(1),locationList.get(3),1,0.02,0.1);
                drawLine(particle,player,locationList.get(1),locationList.get(4),1,0.02,0.1);
                drawLine(particle,player,locationList.get(2),locationList.get(4),1,0.02,0.1);
//                for (int i = 0; i < 2; i++) {
//                    ChatUtil.sendConsoleMessage("chokusen");
//                    drawLine(particle,player,locationList.get(i),locationList.get(i+2),1,0.02,0.1);
//                    drawLine(particle,player,locationList.get(i),locationList.get(i+3),1,0.02,0.1);
//                }
//                drawLine(particle,player,locationList.get(2),locationList.get(4),1,0.02,0.1);
        }

    }

    static Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }
}
