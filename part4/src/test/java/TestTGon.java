import Geometry.Point2D;
import Geometry.TGon;
import org.junit.Assert;
import org.junit.Test;

public class TestTGon {
    private double eps = 0.00001;
    @Test
    public  void squareTest() {
        Point2D[] p = new Point2D[4];
        p[0] = new Point2D(0, 0);
        p[1] = new Point2D(4, 0);
        p[2] = new Point2D(4, 4);
        try {
            TGon r = new TGon(p);
            Assert.assertEquals(r.square(), 8, eps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
