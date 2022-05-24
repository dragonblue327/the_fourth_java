import Geometry.Point3D;
import org.junit.Assert;
import org.junit.Test;

public class TestPoint3D {
    private double eps = 0.00001;

    @Test
    public  void crossProdTest()
    {
        Point3D p1 = new Point3D(1,0,0);
        Point3D p2 = new Point3D(0,1,0);

        Point3D p3 = Point3D.cross_prod(p1,p2);
        Assert.assertEquals(p3.getX(2),1,eps);
    }

    @Test
    public  void mixProdTest()
    {
        Point3D p1 = new Point3D(1,0,0);
        Point3D p2 = new Point3D(0,1,0);
        Point3D p3 = new Point3D(2,2,2);

        Assert.assertEquals(Point3D.mix_prod(p1,p2,p3),2,eps);
    }
}
