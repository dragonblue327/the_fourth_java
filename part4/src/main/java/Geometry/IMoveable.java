package Geometry;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.bson.Document;

import java.awt.*;

public interface IMoveable
{
		public IMoveable shift(Point2D a);
		public IMoveable rot(double phi);
		public IMoveable symAxis(int i );
		public IMoveable scale(double f);
		public void draw(Canvas c, Paint p);
		public String toString();
		public String curtForm();
		public Document toBson();
}
