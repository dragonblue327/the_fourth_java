import Geometry.Polyline;
import  Geometry.Point2D;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestPolyline {
    private double eps = 0.00001;

    @Test
    public  void squareTest()
    {
        Point2D[] p = new Point2D[5];
        p[0] = new Point2D(0,0);
        p[1] = new Point2D(2,2);
        p[2] = new Point2D(3,2);
        p[3] = new Point2D(3,4);
        p[4] = new Point2D(0,4);
        try {
            Polyline pl = new Polyline(p);
        Assert.assertEquals(pl.square(),0,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void lengthTest()
    {
        Point2D[] p = new Point2D[5];
        p[0] = new Point2D(0,0);
        p[1] = new Point2D(2,0);
        p[2] = new Point2D(3,0);
        p[3] = new Point2D(4,0);
        p[4] = new Point2D(4,4);
        try {
            Polyline pl = new Polyline(p);
            Assert.assertEquals(pl.length(),8,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void intersectionTest()
    {
        Point2D[] p1 = new Point2D[5];
        p1[0] = new Point2D(-1,0);
        p1[1] = new Point2D(2,2);
        p1[2] = new Point2D(3,2);
        p1[3] = new Point2D(3,4);
        p1[4] = new Point2D(0,4);
        Point2D[] p2 = new Point2D[5];
        p2[0] = new Point2D(0,0);
        p2[1] = new Point2D(0,2);
        p2[2] = new Point2D(3,0);
        p2[3] = new Point2D(4,0);
        p2[4] = new Point2D(4,4);
        try {
            Polyline pl1 = new Polyline(p1);
            Polyline pl2 = new Polyline(p2);
            Assert.assertTrue(pl1.cross(pl2));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
