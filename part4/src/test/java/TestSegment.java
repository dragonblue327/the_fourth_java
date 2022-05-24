import Geometry.Point2D;
import Geometry.Segment;
import org.junit.Assert;
import org.junit.Test;

public class TestSegment
{
    private double eps = 0.00001;
    @Test
    public  void squareTest()
    {
        Segment s = new Segment(new Point2D(0,0), new Point2D(2,2));
        Assert.assertEquals(s.square(),0,eps);
    }

    @Test
    public  void lengthTest()
    {
        Segment s = new Segment(new Point2D(0,0), new Point2D(2,0));
        Assert.assertEquals(s.length(),2,eps);
    }

    @Test
    public void intersectionTest()
    {
        Segment s1 = new Segment(new Point2D(0,0), new Point2D(2,2));
        Segment s2 = new Segment(new Point2D(0,1), new Point2D(2,1));
        Assert.assertTrue(s1.cross(s2));
    }

}
