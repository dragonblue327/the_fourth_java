package Geometry;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.Document;
import org.bson.types.ObjectId;

@ToString
public class Segment extends OpenFigure
{
	@Getter
	@Setter
		private Point2D s, f;

		public Segment(Point2D s, Point2D f)
		{
				this.s = s;
				this.f = f;
		}

		public double length()
		{
				return (f.sub(s)).abs();
		}
		
		public Segment shift(Point2D m)
		{
			this.s.add(m);
			this.f.add(m);

			return this;
		}

		public Segment rot(double phi)
		{
				this.s.rot(phi);
				this.f.rot(phi);

				return this;
		}

		public Segment symAxis(int i)
		{
				this.s.symAxis(i);
				this.f.symAxis(i);

				return this;
		}

		public Segment scale(double f)
		{
			s.mult(f);
			this.f.mult(f);
			return this;
		}

		public boolean cross(IShape i)
		{
				if (i instanceof Circle)
				{
						return cross(this, (Circle)i);
				}

				else if (i instanceof Segment)
				{
						return cross(this,(Segment)i);
				}

				else if (i instanceof Polyline)
				{
						return cross((Polyline)i,this);
				}

				
				else if (i instanceof NGon)
				{
						return cross((NGon)i,this);
				}

				else
						return false;
		}

		public static boolean cross(Point2D s1start,Point2D s1finish, Point2D s2start, Point2D s2finish)
		{
				Point2D dir1 = Point2D.sub(s1finish,s1start);
				Point2D dir2 = Point2D.sub(s2finish,s2start);

				double line1x = - dir1.getX(1);
				double line1y = dir1.getX(0);
				double line1c = -(s1start.getX(0)*line1x + s1start.getX(1) * line1y);

				double line2x = - dir2.getX(1);
				double line2y = dir2.getX(0);
				double line2c = -(s2start.getX(0)*line2x + s2start.getX(1) * line2y);

				double s1startPosition = line2x * s1start.getX(0) + line2y * s1start.getX(1) + line2c;
				double s1finishPosition = line2x * s1finish.getX(0) + line2y * s1finish.getX(1) + line2c;
				double s2startPosition = line1x * s2start.getX(0) + line1y * s2start.getX(1) + line1c;
				double s2finishPosition = line1x * s2finish.getX(0) + line1y * s2finish.getX(1) + line1c;

				if(s1startPosition * s1finishPosition >= 0 || s2startPosition * s2finishPosition >= 0)
						return false;

				return true;
		}

		public static boolean cross(Segment s1, Segment s2)
		{
				return cross(s1.s,s1.f,s2.s,s2.f);
		}

		public static boolean cross(Polyline pl, Segment s)
		{
				for(int i = 1; i < pl.getN(); ++i)
				{
						if (cross(pl.getP(i-1),pl.getP(i),s.s,s.f))
										return true;
				}
				return false;
		}

		public static boolean cross(NGon ng, Segment s)
		{
				int edges = ng.getN();
				for(int i = 0; i < edges; ++i)
				{
						if(cross(ng.getP((edges + i-1)%edges),ng.getP(i),s.s,s.f))
								return true;
				}
				return false;
		}

		public static boolean cross(Point2D sStart, Point2D sFinish,Circle c)
		{
				if ( sStart.getX(0) < sFinish.getX(0) )
				{
						Point2D temp = sStart;
						sStart = sFinish;
						sFinish = temp;
				}

				Point2D dir = sFinish.sub(sStart);

				Point2D toCenter = sStart.sub(c.getP());

				double A = dir.getX(0) * dir.getX(0) + dir.getX(1) * dir.getX(1);
				double B = 2 * (dir.getX(0) * toCenter.getX(0) + dir.getX(1) * toCenter.getX(1));
				double C = toCenter.getX(0) * toCenter.getX(0) + toCenter.getX(1) * toCenter.getX(1) - c.getR() * c.getR();

				double D = B * B - 4 * A * C;

				if (D < 0)
						return false;
				double r1 = (-B - Math.sqrt(D))/(2*A);
				double r2 = (-B + Math.sqrt(D))/(2*A);

				return (r1 >= 0 && r1 < 1) || (r2 >= 0 && r2 < 1);
		}
		
		public static boolean cross(Segment s, Circle c)
		{
				return cross(s.s, s.f, c);
		}

		public static boolean cross(Polyline pl, Circle c)
		{
				for(int i = 1; i < pl.getN(); ++i)
				{
						if (cross(pl.getP(i-1),pl.getP(i),c))
										return true;
				}
				return false;
		}

		public static boolean cross(NGon ng, Circle c)
		{
				int edges = ng.getN();
				for(int i = 0; i < edges; ++i)
				{
						if(cross(ng.getP((edges + i-1)%edges),ng.getP(i),c))
								return true;
				}
				return false;
		}

	public void draw(Canvas canvas, Paint paint)
	{
		canvas.drawLine((float) s.getX(0), (float) s.getX(1), (float) f.getX(0), (float) f.getX(1),paint);
	}

		public String curtForm()
		{
				String res = new String("#se#;1;{start:};");

				res += String.valueOf(s.getX(0)) +";" + String.valueOf(s.getX(1)) +";{finish:};";
				res += String.valueOf(f.getX(0)) +";" + String.valueOf(f.getX(1)) +";";

				return res;
		}
		@Override
		public Document toBson() {
			Document d = new Document("_id",new ObjectId())
					.append("data",curtForm());
			return d;
		}
}
