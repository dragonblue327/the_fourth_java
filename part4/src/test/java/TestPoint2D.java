import Geometry.Point2D;
import org.junit.Assert;
import org.junit.Test;

public class TestPoint2D {
    private double eps = 0.00001;

    @Test
    public  void testRot()
    {
        Point2D p = new Point2D(1,1);
        p.rot(Math.PI);
        for (int i = 0;i  < 2; ++i)
            Assert.assertEquals(p.getX(i),-1,eps);
    }
}
