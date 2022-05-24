import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import Geometry.Point;

public class TestPoint {
    private double eps = 0.00001;

    @Test
    public  void TestAbs()
    {
        double[] cords = new double[4];
        cords[0] = 4;
        cords[1] = 4;
        cords[2] = 4;
        cords[3] = 4;
        try
        {
            Point p = new Point(4);
            p.setX(cords);
            Assert.assertEquals(p.abs(),8,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public  void TestAdd()
    {
        double[] cords1 = new double[4];
        cords1[0] = 4;
        cords1[1] = 4;
        cords1[2] = 4;
        cords1[3] = 4;
        double[] cords2 = new double[4];
        cords2[0] = 2;
        cords2[1] = 2;
        cords2[2] = 2;
        cords2[3] = 2;
        try
        {
            Point p1 = new Point(4);
            p1.setX(cords1);
            Point p2 = new Point(4);
            p2.setX(cords2);
            Point p3 = Point.add(p1,p2);
            for(int i = 0; i < 4; ++i)
                Assert.assertEquals(p3.getX(i),6,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public  void TestSub()
    {
        double[] cords1 = new double[4];
        cords1[0] = 4;
        cords1[1] = 4;
        cords1[2] = 4;
        cords1[3] = 4;
        double[] cords2 = new double[4];
        cords2[0] = 2;
        cords2[1] = 2;
        cords2[2] = 2;
        cords2[3] = 2;
        try
        {
            Point p1 = new Point(4);
            p1.setX(cords1);
            Point p2 = new Point(4);
            p2.setX(cords2);
            Point p3 = Point.sub(p1,p2);
            for(int i = 0; i < 4; ++i)
                Assert.assertEquals(p3.getX(i),2,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void TestMult()
    {
        double[] cords1 = new double[4];
        cords1[0] = 4;
        cords1[1] = 4;
        cords1[2] = 4;
        cords1[3] = 4;
        double[] cords2 = new double[4];
        cords2[0] = 4;
        cords2[1] = 4;
        cords2[2] = 4;
        cords2[3] = 4;
        try
        {
            Point p1 = new Point(4);
            p1.setX(cords1);
            Point p2 = new Point(4);
            p2.setX(cords2);
            Assert.assertEquals(p1.mult(p2),64,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void TestCoefMult()
    {
        double[] cords = new double[4];
        cords[0] = 4;
        cords[1] = 4;
        cords[2] = 4;
        cords[3] = 4;
        try
        {
            Point p = new Point(4);
            p.setX(cords);
            p.mult(2);
            for( int i = 0; i < 4; ++i)
                Assert.assertEquals(p.getX(i),8,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void TestSymAxis()
    {
        double[] cords = new double[4];
        cords[0] = 4;
        cords[1] = 4;
        cords[2] = 4;
        cords[3] = 4;
        try
        {
            Point p = new Point(4);
            p.setX(cords);
            p.symAxis(0);
            Assert.assertEquals(p.getX(0),4,eps);
            for (int i = 1; i < 4; ++i)
                Assert.assertEquals(p.getX(i),-4,eps);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
