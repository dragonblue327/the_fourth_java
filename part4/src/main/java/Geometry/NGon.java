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
public class NGon implements IShape, IPolyPoint {
	@Getter
	protected int n;
	@Getter
	@Setter
	protected Point2D[] p;

	public Point2D getP(int i) {
		return p[i];
	}

	public void setP(Point2D a, int i)
	{
		p[i] = a;
	}

		public NGon(Point2D[] p) throws Exception
		{
			n = p.length;
			setP(p);
		}
				
		private boolean convexityTest()
		{
				return convexityTest(this.p);
		}
		
		public static boolean convexityTest(Point2D[] p)
		{
				if(p.length < 3)
						return false;

				int cn = p.length;
				Point2D vv1 = Point2D.sub(p[0],p[cn-1]);
				Point2D vv2 = Point2D.sub(p[1],p[0]);
				
				double prevSign = vv1.getX(0) * vv2.getX(1) - vv1.getX(1) * vv2.getX(0);
				for (int i =1; i < cn -1; ++i)
				{
						
						Point2D v1 = Point2D.sub(p[i],p[i-1]);
						Point2D v2 = Point2D.sub(p[i+1],p[i]);
						double sign = v1.getX(0) * v2.getX(1) - v1.getX(1) * v2.getX(0);
						if (prevSign * sign < 0)
								return false;

						prevSign = sign;
				}
				return true;
		}

		public double square()
		{
				double res = (p[n-1].getX(0) + p[0].getX(0)) * (p[n-1].getX(1) - p[0].getX(1));
				for (int i = 1; i < n; ++i)
				{
						res += (p[i-1].getX(0) + p[i].getX(0)) * (p[i-1].getX(1) - p[i].getX(1));			
				}
				return Math.abs(res/2);
		}

		public double length()
		{
				double len = 0;

				for(int i =0; i < n; ++i)
						len += (Point2D.sub(p[i],p[(n+ i-1)%n])).abs();

				return len;
		}

		public NGon shift(Point2D m)
		{
				for(int i =0; i < n; ++i)
						p[i].add(m);

				return this;
		}

		public NGon rot(double phi)
		{
				for(int i =0; i < n; ++i)
						p[i].rot(phi);

				return this;
		}

		public NGon symAxis(int s)
		{
				for(int i =0; i < n; ++i)
						p[i].symAxis(s);

				return this;
		}

		public  NGon scale(double f)
		{
			for(int i =0; i < n; ++i)
			{
				p[i].mult(f);
			}
			return this;
		}
		public boolean cross(IShape i)
		{
				if (i instanceof Circle)
				{
						return Segment.cross(this,(Circle)i);
				}

				else if (i instanceof Segment)
				{
						return Segment.cross(this,(Segment)i);
				}

				else if (i instanceof Polyline)
				{
						return Polyline.cross((Polyline)i,this);
				}

				
				else if (i instanceof NGon)
				{
						return Polyline.cross((NGon)i,this);
				}

				else
						return false;

		}

		public static String NGonToString(NGon ng,String titleLine)
		{
				String res = "{"+ titleLine +":\n";

				for(int i =1; i < ng.n; ++i)
				{
						res += String.valueOf(i - 1) +": " + ng.p[i-1].toString() +
							" ; "	+ ng.p[i].toString() + ",\n";
				}

				res += String.valueOf(ng.n-1) +": " + ng.p[ng.n-1].toString() +
							" ; "	+ ng.p[0].toString() + "\n}";

				return res;
		}

    public void draw(Canvas canvas, Paint paint)
    {
		Path path = new Path();
		path.moveTo((float) p[0].getX(0), (float) p[0].getX(1));
		for(int i =1; i < n; ++i)
		{
			path.lineTo((float) p[i].getX(0), (float) p[i].getX(1));
		}
		path.lineTo((float) p[0].getX(0), (float) p[0].getX(1));

		canvas.drawPath(path,paint);
    }

		public String curtForm()
		{
				String res = new String("#ng#;4;#vertices#;") + String.valueOf(n) + ";";

				for (int i = 0; i < n; ++i)
				{
						res +="{" + String.valueOf(i) +":};"
								+ String.valueOf(p[i].getX(0)) +";" + String.valueOf(p[i].getX(1)) +";";
				}
				return res;
		}
        @Override
        public Document toBson() {
            Document d = new Document("_id",new ObjectId())
                    .append("data",curtForm());
            return d;
        }
}
