package Geometry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.Document;
import org.bson.types.ObjectId;

@ToString
public class Circle implements IShape
{
	@Getter
	@Setter
		private Point2D p;
	@Getter
	@Setter
		private double r;

		public Circle(Point2D p, double r)
		{
				this.p = p;
				this.r = r;
		}
		public double square()
		{
				return Math.PI * r * r;
		}

		public double length()
		{
				return 2 * Math.PI * r;
		}

		public Circle shift(Point2D p)
		{
				this.p.add(p); 

				return this;
		}

		public Circle rot(double phi)
		{
				p.rot(phi);
				return this;
		}

		public Circle symAxis(int i)
		{
				p.symAxis(i);
				return this;
		}

		public  Circle scale(double f)
		{
			p.mult(f);
			r *=f;
			return this;
		}
		public boolean cross(IShape i)
		{
				if (i instanceof Circle)
				{
						return Circle.cross(this, (Circle)i);
				}

				else if (i instanceof Segment)
				{
						return Segment.cross((Segment)i, this);
				}

				else if (i instanceof Polyline)
				{
						return Segment.cross((Polyline)i, this); 
				}

				
				else if (i instanceof NGon)
				{
						return Segment.cross((NGon)i, this);
				}

				else
						return false;
		}

		public static boolean cross(Circle a, Circle b)
		{
				Point2D diff = b.p.sub(a.p);

				return (diff.getX(0) * diff.getX(0)  +diff.getX(1) *diff.getX(1) <= a.r * a.r + b.r * b.r);
		}

		public void draw(Canvas canvas, Paint paint)
		{
			canvas.drawCircle((float) p.getX(0), (float) p.getX(1), (float) r, paint);
		}

		public String curtForm()
		{
				return "#ci#;2;{center:};" + String.valueOf(p.getX(0)) +";" + String.valueOf(p.getX(1)) 
						+";{radius:};" + String.valueOf(r);
		}

	@Override
	public Document toBson() {
		Document d = new Document("_id",new ObjectId())
				.append("data",curtForm());
		return d;
	}
}
