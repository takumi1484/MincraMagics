package jp.mincra.mincramagics.skill;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MincraParticle {

    private double radius = 1;
    private List<Particle> particleList = new ArrayList<>();

    private double roll = 0;
    private double yaw = 0;
    private double pitch = 0;

    private Vector rotationAxis = new Vector(0,0,0);
    private double angle = 0;

    private Location origin;

    private Particle.DustOptions dustOptions;

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

    public void setDustOptions(Particle.DustOptions dustOptions) {
        this.dustOptions = dustOptions;
    }


    /**
     * X軸方向の傾きをラジアンで指定する。
     * @param roll
     */
    public void setRoll(double roll) {
        this.roll = roll;
    }

    /**
     * Y軸方向の傾きをラジアンで指定する。
     * @param yaw
     */
    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    /**
     * Z軸方向の傾きをラジアンで指定する。
     * @param pitch
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * 傾きをラジアンで指定する。
     * @param roll X軸方向の傾き
     * @param pitch Y軸方向の傾き
     * @param yaw Z軸方向の傾き
     */
    public void setRolling(double roll, double pitch, double yaw) {
        setRoll(roll);
        setYaw(yaw);
        setPitch(pitch);
    }

    /**
     * 回転軸を指定する。
     * @param rotationAxis 回転軸
     */
    public void setRotationAxis(Vector rotationAxis) {
        this.rotationAxis = rotationAxis;
    }

    /**
     * rotationAxis回転軸に対する回転角度をラジアンで指定する。
     * @param angle 角度
     */
    public void setAngle(double angle) {
        this.angle = angle;
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
    public void drawLine(Particle particle, Location locationStart, Location locationEnd, int count, double speed, double distanceBetween, double offsetX, double offsetY, double offsetZ, Particle.DustOptions dustOptions) {
        //https://www.spigotmc.org/threads/drawing-particle-line.446101/
        Vector vector = getDirectionBetweenLocations(locationStart, locationEnd).normalize(); //make sure it has length one at start
        for (double i = 0, dis = locationStart.distance(locationEnd); i < dis; i += distanceBetween) {
            Vector addition = new Vector().copy(vector).multiply(i);
            Location newLoc = locationStart.clone().add(addition);

            if (particle == Particle.REDSTONE) {
                locationStart.getWorld().spawnParticle(particle, newLoc, count, offsetX, offsetY, offsetZ, speed, dustOptions);

            } else {
                locationStart.getWorld().spawnParticle(particle, newLoc, count, offsetX, offsetY, offsetZ, speed);

            }
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
        drawLine(particle, locationStart, locationEnd, count, speed, distanceBetween, offset, offset, offset, dustOptions);

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
        drawLine(particle, locationStart, locationEnd, count, speed, distanceBetween, 0, 0, 0, dustOptions);
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
    public void drawCircle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double speed, double radius, int amount, Particle.DustOptions dustOptions) {
        double increment = (2 * Math.PI) / amount;
        for(int i = 0;i < amount; i++) {
            double angle = i * increment;
//            Vector vector = new Vector(radius * Math.cos(angle), 0, radius * Math.sin(angle));
//            double x = location.getX() + rotateVector(vector).getX();
//            double y = location.getY() + rotateVector(vector).getY();
//            double z = location.getZ() + rotateVector(vector).getZ();
//
//            if (particle == Particle.REDSTONE) {
//                location.getWorld().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, speed, dustOptions);
//
//            } else {
//                location.getWorld().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, speed);
//
//            }

            double x = location.getX() + (radius * Math.cos(angle));
            double z = location.getZ() + (radius * Math.sin(angle));

            if (particle == Particle.REDSTONE) {
                location.getWorld().spawnParticle(particle, x, location.getY(), z, count, offsetX, offsetY, offsetZ, speed, dustOptions);

            } else {
                location.getWorld().spawnParticle(particle, x, location.getY(), z, count, offsetX, offsetY, offsetZ, speed);

            }
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
        drawCircle(particle, location, count, offset, offset, offset, speed, radius, amount, dustOptions);
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
        drawCircle(particle, location, count, 0, 0, 0, speed, radius, amount, dustOptions);
    }





    /**
     * パーティクルの星を描画します。
     * @param particle 描画するパーティクル
     * @param location 星の中心
     * @param radius 星の半径
     * @param vertex 星の頂点の数
     */
    public void drawStar(Particle particle, Location location, int count, double offset, double speed, double distanceBetween, double radius, int vertex) {

        origin = location;

        //多角形の頂点の座標を取得
        List<Vector> vectorList = getVertexCoordinate(vertex, radius);
        List<Location> locationList = new ArrayList<>();

        //頂点座標と実行座標をマージ
        for (Vector vector : vectorList) {
            Location tempLocation = location.clone();
            tempLocation.add(rotateVector(vector));

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

    /**
     * ベクトルをxyz軸について回転させます
     * @param vector ベクトル
     * @return 回転したベクトル
     */
    Vector rotateVectorFromRolling(Vector vector) {

        double sinX = Math.sin(roll);
        double cosX = Math.cos(roll);
        double sinY = Math.sin(yaw);
        double cosY = Math.cos(yaw);
        double sinZ = Math.sin(pitch);
        double cosZ = Math.cos(pitch);

        double xVector = 0;
        double yVector = 0;
        double zVector = 0;

        //roll(x軸)
        xVector = xVector + vector.getX();
        yVector = yVector + vector.getY() * cosX - vector.getZ() * sinX;
        zVector = zVector + vector.getY() * sinX + vector.getZ() * cosX;

        //pitch(z軸)
        xVector = xVector + vector.getX() * cosZ + vector.getY() * cosZ;
        yVector = yVector - vector.getX() * sinZ + vector.getY() * cosZ;
        zVector = zVector + vector.getZ();

        //yaw(y軸)
        xVector = xVector + vector.getX() * cosY - vector.getZ() * sinY;
        yVector = yVector + vector.getY();
        zVector = zVector + vector.getX() * sinY + vector.getZ() * cosY;

        Vector resultVector = new Vector(xVector, yVector, zVector);
        resultVector.normalize();
        resultVector.multiply(radius);

        return resultVector;
    }

    /**
     * rotationAxisで指定した軸に対してangleだけ回転させます。
     * @param vector ベクトル
     * @return 回転したベクトル
     */
    Vector rotateVector(Vector vector) {

        vector = rotateVectorFromRolling(vector);

        double axisX = rotationAxis.getX();
        double axisY = rotationAxis.getY();
        double axisZ = rotationAxis.getZ();

        double argX = vector.getX();
        double argY = vector.getY();
        double argZ = vector.getZ();

        double sinTheta = Math.sin(angle);
        double cosTheta = Math.cos(angle);

        //計算軽量化
        double oneSubCos = 1 - cosTheta;
        double axisXY = axisX * axisY;
        double axisYZ = axisY * axisZ;
        double axisZX = axisZ * axisX;
        double axisXsin = axisX * sinTheta;
        double axisYsin = axisY * sinTheta;
        double axisZsin = axisZ * sinTheta;

        double resultX =
                ( Math.pow(axisX,2) * oneSubCos + cosTheta ) * argX
                + ( axisXY * oneSubCos - axisZsin ) * argY
                + ( axisZX * oneSubCos + axisYsin ) * argZ;
        double resultY =
                ( axisXY * oneSubCos + axisZsin ) * argX
                + ( Math.pow(axisY,2) * oneSubCos + cosTheta ) * argY
                + ( axisYZ * oneSubCos - axisXsin ) * argZ;
        double resultZ =
                + ( axisZX * oneSubCos - axisYsin ) * argX
                + ( axisYZ * oneSubCos + axisXsin ) * argY
                + ( Math.pow(axisZ,2) * oneSubCos + cosTheta ) * argZ ;

        return new Vector(resultX,resultY,resultZ);

    }
}
