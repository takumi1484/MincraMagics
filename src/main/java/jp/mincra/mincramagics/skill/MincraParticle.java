package jp.mincra.mincramagics.skill;

import jp.mincra.mincramagics.util.Matrix2D;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MincraParticle {

    private double radius = 1;
    private List<Particle> particleList = new ArrayList<>();

    private double angleX = 0;
    private double angleY = 0;
    private double angleZ = 0;

    private Location center;

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public List<Particle> getParticleList() {
        return particleList;
    }
    public void setParticle(int index, Particle particle) {
        particleList.add(index,particle);
    }
    public void setParticle(Particle particle1) {
        particleList.add(0,particle1);
    }
    public void setParticle(Particle particle1, Particle particle2) {
        particleList.add(0,particle1);
        particleList.add(1,particle2);
    }
    public void setParticle(Particle particle1, Particle particle2, Particle particle3) {
        particleList.add(0,particle1);
        particleList.add(1,particle2);
        particleList.add(2,particle3);
    }
    public void setParticle(Particle particle1, Particle particle2, Particle particle3, Particle particle4) {
        particleList.add(0,particle1);
        particleList.add(1,particle2);
        particleList.add(2,particle3);
        particleList.add(3,particle4);
    }


    /**
     * X軸方向の傾きをラジアンで指定する。
     * @param angleX
     */
    public void setAngleX(double angleX) {
        this.angleX = angleX;
    }

    /**
     * Y軸方向の傾きをラジアンで指定する。
     * @param angleY
     */
    public void setAngleY(double angleY) {
        this.angleY = angleY;
    }

    /**
     * Z軸方向の傾きをラジアンで指定する。
     * @param angleZ
     */
    public void setAngleZ(double angleZ) {
        this.angleZ = angleZ;
    }

    /**
     * 傾きをラジアンで指定する。
     * @param angleX X軸方向の傾き
     * @param angleY Y軸方向の傾き
     * @param angleZ Z軸方向の傾き
     */
    public void setAngle(double angleX, double angleY, double angleZ) {
        setAngleX(angleX);
        setAngleY(angleY);
        setAngleZ(angleZ);
    }

    /**
     * 始点から終点にパーティクルの線分を描画します。
     * @param particle 描画するパーティクル
     * @param locationStart 始点
     * @param locationEnd 終点
     * @param count 1点あたりのパーティクルの量
     * @param speed パーティクルの速度
     * @param distanceBetween パーティクル同士の距離
     * @param offsetX X軸方向の最大のバラつき
     * @param offsetY Y軸方向の最大のバラつき
     * @param offsetZ Z軸方向の最大のバラつき
     */
    public void drawLine(Particle particle, Location locationStart, Location locationEnd, int count, double speed, double distanceBetween, double offsetX, double offsetY, double offsetZ) {
        //https://www.spigotmc.org/threads/drawing-particle-line.446101/
//        Vector startFromCenter = getDirectionBetweenLocations(center,locationStart);
//        Vector endFromCenter = getDirectionBetweenLocations(center,locationEnd);
//        Location rotatedStart = locationStart.add(setRotation(startFromCenter));
//        Location rotatedEnd = locationEnd.add(setRotation(endFromCenter);

        Vector vector = getDirectionBetweenLocations(locationStart, locationEnd).normalize(); //make sure it has length one at start
        for (double i = 0, dis = locationStart.distance(locationEnd); i < dis; i += distanceBetween) {
            Vector addition = new Vector().copy(vector).multiply(i);
            Location newLoc = locationStart.clone().add(addition);

            locationStart.getWorld().spawnParticle(particle, newLoc, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    /**
     * 始点から終点にパーティクルの線分を描画します。
     * @param particle 描画するパーティクル
     * @param locationStart 始点
     * @param locationEnd 終点
     * @param count 1点あたりのパーティクルの量
     * @param speed パーティクルの速度
     * @param distanceBetween パーティクル同士の距離
     * @param offset 全ての軸方向の最大のバラつき
     */
    public void drawLine(Particle particle, Location locationStart, Location locationEnd, int count, double offset, double speed, double distanceBetween) {
        drawLine(particle, locationStart, locationEnd, count, speed, distanceBetween, offset, offset, offset);
    }

    /**
     * 始点から終点にパーティクルの線分を描画します。
     * @param particle 描画するパーティクル
     * @param locationStart 始点
     * @param locationEnd 終点
     * @param count 1点あたりのパーティクルの量
     * @param speed パーティクルの速度
     * @param distanceBetween パーティクル同士の距離
     */
    public void drawLine(Particle particle, Location locationStart, Location locationEnd, int count, double speed, double distanceBetween) {
        drawLine(particle, locationStart, locationEnd, count, speed, distanceBetween, 0, 0, 0);
    }


    /**
     * パーティクルの円を描画します。
     * @param particle 描画するパーティクル
     * @param location 円の中心
     * @param count 1点あたりのパーティクルの量
     * @param offsetX X軸方向の最大のバラつき
     * @param offsetY Y軸方向の最大のバラつき
     * @param offsetZ Z軸方向の最大のバラつき
     * @param speed　パーティクルの速度
     * @param radius　円の半径
     * @param amount 描画するパーティクルの点数
     */
    public void drawCircle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double speed, double radius, int amount) {
        double increment = (2 * Math.PI) / amount;
        for(int i = 0;i < amount; i++)
        {
            double angle = i * increment;
            double x = location.getX() + (radius * Math.cos(angle));
            double z = location.getZ() + (radius * Math.sin(angle));

            location.getWorld().spawnParticle(particle, x, location.getY(), z, count, offsetX, offsetY, offsetZ, speed);
        }
    }

    /**
     * パーティクルの円を描画します。
     * @param particle 描画するパーティクル
     * @param location 円の中心
     * @param count 1点あたりのパーティクルの量
     * @param offset 全ての軸方向の最大のバラつき
     * @param speed　パーティクルの速度
     * @param radius　円の半径
     * @param amount 描画するパーティクルの点数
     */
    public void drawCircle(Particle particle, Location location, int count, double offset, double speed, double radius, int amount) {
        drawCircle(particle, location, count, offset, offset, offset, speed, radius, amount);
    }
    
    /**
     * パーティクルの円を描画します。
     * @param particle 描画するパーティクル
     * @param location 円の中心
     * @param count 1点あたりのパーティクルの量
     * @param speed　パーティクルの速度
     * @param radius　円の半径
     * @param amount 描画するパーティクルの点数
     */
    public void drawCircle(Particle particle, Location location, int count, double speed, double radius, int amount) {
        drawCircle(particle, location, count, 0, 0, 0, speed, radius, amount);
    }


    /**
     * パーティクルの星を描画します。
     * @param particle 描画するパーティクル
     * @param location 星の中心
     * @param radius 星の半径
     * @param vertex 星の頂点の数
     */
    public void drawStar(Particle particle, Location location, int count, double offset, double speed, double distanceBetween, double radius, int vertex) {

        center = location;

        //多角形の頂点の座標を取得
        List<Vector> vectorList = getVertexCoordinate(vertex, radius);
        List<Location> locationList = new ArrayList<>();

        //頂点座標と実行座標をマージ
        for (Vector vector : vectorList) {
//            Location tempLocation = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            Location tempLocation = location.clone();
            tempLocation.add(vector);

            locationList.add(tempLocation);
        }

        //直線
        for (int i = 0, len = locationList.size(); i < len; i++) {
            if (len > 4) {
                if (i + 2 < len) {
                    drawLine(particle, locationList.get(i), locationList.get(i + 2), count, offset, speed, distanceBetween);
                } else {
                    drawLine(particle, locationList.get(i), locationList.get(i + 2 - vertex), count, offset, speed, distanceBetween);
                }
            } else {
                if (i + 1 < len) {
                    drawLine(particle, locationList.get(i), locationList.get(i + 1), count, offset, speed, distanceBetween);
                } else {
                    drawLine(particle, locationList.get(i), locationList.get(i + 1 - vertex), count, offset, speed, distanceBetween);
                }
            }
        }
    }


    /**
     * パーティクルの魔法陣を描画します
     * @param location 魔法陣の中心
     * @param vertex 星の頂点の数
     * @param count 1点あたりのパーティクルの量
     * @param offset 全ての軸方向の最大のバラつき
     * @param speed パーティクルの速度
     */
    public void drawMagicCircle(Location location, int vertex, int type, int count, double offset, double speed) {
        switch (type) {
            //普通の五芒星
            case 1:
                //描画するパーティクルを登録
                Particle[] particle = new Particle[3];

                for (int i=0, len=particle.length; i<len; i++) {
                    if (particleList.size()>i) {
                        particle[i] = particleList.get(i);
                    } else {
                        particle[i] = particle[i-1];
                    }
                }

                //円
                drawCircle(particle[0], location, count, offset, speed, 1.0 * radius, (int) (80 * radius));
                drawCircle(particle[1], location, count, offset, speed, 1.3 * radius, (int) (104 * radius));

                //星
                drawStar(particle[2],location,count,offset,speed,0.17, radius,vertex);

                break;

        }
    }

    /**
     * パーティクルの魔法陣を描画します
     * @param location 魔法陣の中心
     * @param vertex 星の頂点の数
     */
    public void drawMagicCircle(Location location, int vertex, int type) {
        drawMagicCircle(location,vertex, type,1,0.01,0);
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

    /**
     * 始点から終点までの距離を返します。
     * @param Start 始点
     * @param End 終点
     * @return 距離ベクトル
     */
    Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }

    Vector setRotation(Vector vector) {

        /*
        double theta = 1;

        double x, y, z;
        double u, v, w;
        x=vector.getX();y=vector.getY();z=vector.getZ();
        u=angleX;v=angleY;w=angleZ;
        double v1 = u * x + v * y + w * z;
        double xPrime = u* v1 *(1d - Math.cos(theta))
                + x*Math.cos(theta)
                + (-w*y + v*z)*Math.sin(theta);
        double yPrime = v* v1 *(1d - Math.cos(theta))
                + y*Math.cos(theta)
                + (w*x - u*z)*Math.sin(theta);
        double zPrime = w* v1 *(1d - Math.cos(theta))
                + z*Math.cos(theta)
                + (-v*x + u*y)*Math.sin(theta);
        return new Vector(xPrime, yPrime, zPrime);
        */

        /*
        double sinX = Math.sin(angleX);
        double cosX = Math.cos(angleX);
        double sinY = Math.sin(angleY);
        double cosY = Math.cos(angleY);
        double sinZ = Math.sin(angleZ);
        double cosZ = Math.cos(angleZ);

        double[][] xMatrix = {
                {1,0,0},
                {0,cosX,sinX},
                {0,-sinX,cosX}
        };
        double[][] yMatrix = {
                {cosY,0,-sinY},
                {0,1,0},
                {sinY,0,cosX}
        };
        double[][] zMatrix = {
                {cosZ,sinZ,0},
                {-sinZ,cosZ,0},
                {0,0,1}
        };

        double[] origin = {vector.getX(),vector.getY(),vector.getZ()};

        double[][] xRotation = Matrix2D.dot(new Matrix2D(xMatrix), new Matrix2D(origin)).getArrays();
        double[][] yRotation = Matrix2D.dot(new Matrix2D(yMatrix), new Matrix2D(origin)).getArrays();
        double[][] zRotation = Matrix2D.dot(new Matrix2D(zMatrix), new Matrix2D(origin)).getArrays();

        double xVector = xRotation[0][0] + yRotation[0][0] + zRotation[0][0];
        double yVector = xRotation[1][0] + yRotation[1][0] + zRotation[1][0];
        double zVector = xRotation[2][0] + yRotation[2][0] + zRotation[2][0];

        return new Vector(xVector,yVector,zVector).normalize();
        */
        return null;
    }
}
