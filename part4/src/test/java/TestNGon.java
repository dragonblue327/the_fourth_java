import Geometry.NGon;
import Geometry.Point2D;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TestNGon {
    private double eps = 0.00001;

@Test
    public  void squareTest()
{
    Point2D[] p = new Point2D[4];
    p[0] = new Point2D(1,1);
    p[1] = new Point2D(9,1);
    p[2] = new Point2D(9,9);
    p[3] = new Point2D(1,9);
    try
    {
        NGon ng = new NGon(p);
        Assert.assertEquals(ng.square(),64,eps);
    }
    catch (Exception e)
    {
        System.out.println(e.getMessage());
    }
}
    @Test
    public  void lengthTest() {
        Point2D[] p = new Point2D[4];
        p[0] = new Point2D(1, 1);
        p[1] = new Point2D(9, 1);
        p[2] = new Point2D(9, 9);
        p[3] = new Point2D(1, 9);
        try {
            NGon ng = new NGon(p);
            Assert.assertEquals(ng.length(), 32, eps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public  void intersectionTestTrue() {
        Point2D[] p1 = new Point2D[4];
        p1[0] = new Point2D(1, 1);
        p1[1] = new Point2D(9, 1);
        p1[2] = new Point2D(9, 9);
        p1[3] = new Point2D(1, 9);

        Point2D[] p2 = new Point2D[4];
        p2[0] = new Point2D(0, 0);
        p2[1] = new Point2D(9, 1);
        p2[2] = new Point2D(9, 9);

        try {
            NGon ng1 = new NGon(p1);
            NGon ng2 = new NGon(p2);
            Assert.assertTrue(ng1.cross(ng2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public  void intersectionTestFalse() {
        Point2D[] p1 = new Point2D[4];
        p1[0] = new Point2D(1, 1);
        p1[1] = new Point2D(9, 1);
        p1[2] = new Point2D(9, 9);
        p1[3] = new Point2D(1, 9);

        Point2D[] p2 = new Point2D[4];
        p2[0] = new Point2D(2, 2);
        p2[1] = new Point2D(8, 2);
        p2[2] = new Point2D(8, 8);

        try {
            NGon ng1 = new NGon(p1);
            NGon ng2 = new NGon(p2);
            Assert.assertFalse(ng1.cross(ng2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
