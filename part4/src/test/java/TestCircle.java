
import Geometry.Circle;
import Geometry.Point2D;
import junit.framework.TestCase;
        import org.junit.Assert;
        import org.junit.Test;

public class TestCircle {
    private double eps = 0.00001;
    @Test
    public void squareTest()
    {
        Circle c = new Circle(new Point2D(0,0),1);
        Assert.assertEquals(c.square(),Math.PI,eps);
    }
    @Test
    public void lengthTest()
    {
        Circle c = new Circle(new Point2D(0,0),1);
        Assert.assertEquals(c.length(),2 * Math.PI,eps);
    }
    @Test
    public void intersectTestTrue()
    {
        Circle c1 = new Circle(new Point2D(0,0),1);
        Circle c2 = new Circle(new Point2D(1.0,1.0),1);
        Assert.assertTrue(c1.cross(c2));
    }
    @Test
    public void intersectTestFalse()
    {
        Circle c1 = new Circle(new Point2D(0,0),1);
        Circle c2 = new Circle(new Point2D(2.5,2.5),1);
        Assert.assertFalse(c1.cross(c2));
    }
}