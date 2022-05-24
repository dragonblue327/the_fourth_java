package Geometry;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Polyline extends OpenFigure implements IPolyPoint
{
	@Getter
		private int n;
	@Getter
	@Setter
		private Point2D[] p;

		public Polyline(Point2D[] p) throws Exception
		{
			n = p.length;
			setP(p);
		}

		public Point2D getP(int i)
		{
				return p[i];
		}
		public void setP(Point2D p, int i)
		{
				this.p[i] = p;
		}

		public double length()
		{
				double len = 0;

				for(int i = 1; i< n; ++i)
				{
						len += (Point2D.sub(p[i],p[i-1])).abs();
				}

				return len;
		}

		public Polyline shift(Point2D m)
		{
				for(int i = 0; i < n; ++i)
						p[i].add(m);

				return this;
		}

		public Polyline rot(double phi)
		{
				for(int i = 0; i < n; ++i)
						p[i].rot(phi);

				return this;
		}

		public Polyline symAxis(int s)
		{
				for(int i = 0; i < n; ++i)
						p[i].symAxis(s);

				return this;
		}
		public  Polyline scale(double f)
		{
			for(int i =0; i < n; ++i)
			{
				p[i].mult(f);
			}
			return this;
		}

		public static boolean cross(Polyline a, Polyline b)
		{
				for( int i = 1; i< a.n; ++i)
				{
						for (int j = 1; j < b.n; ++j)
						{
								if(Segment.cross(a.getP(i-1),a.getP(i),b.getP(j-1),b.getP(j)))
										return true;
						}
				}
				return false;
		}

		public static boolean cross(Polyline pl, NGon ng)
		{
				for( int i = 1; i< pl.n; ++i)
				{
						int edges = ng.getN();
						for (int j = 0; j < edges; ++j)
						{
								if(Segment.cross(pl.getP(i-1),pl.getP(i),ng.getP((edges + j-1)%edges),ng.getP(j)))
										return true;
						}
				}
				return false;
		}
		
		public static boolean cross(NGon a, NGon b)
		{
				int aEdges = a.getN();
				for( int i = 0; i< aEdges; ++i)
				{
						int bEdges = b.getN();
						for (int j = 0; j < bEdges; ++j)
						{
								if(Segment.cross(a.getP((aEdges + i-1)%aEdges),a.getP(i),
										b.getP((bEdges + j-1)%bEdges),b.getP(j)))
								{
									//	System.out.println(String.valueOf(i) +", " + String.valueOf(j));
										return true;
								}
						}	
				}
				return false;
		}

		public boolean cross(IShape i)
		{
						if (i instanceof Circle)
						{
								return Segment.cross(this, (Circle)i);
						}

						else if (i instanceof Segment)
						{
								return Segment.cross(this,  (Segment)i);
						}

						else if (i instanceof Polyline)
						{
								return cross(this, (Polyline)i);	
						}

						
						else if (i instanceof NGon)
						{
								return cross(this, (NGon)i);
						}

						else
								return false;
				}


	public void draw(Canvas canvas, Paint paint)
	{
		Path path = new Path();
		path.moveTo((float) p[0].getX(0), (float) p[0].getX(1));
		for(int i =1; i < n; ++i)
		{
			path.lineTo((float) p[i].getX(0), (float) p[i].getX(1));
		}

		canvas.drawPath(path,paint);
	}

		public String curtForm()
		{
				String res = new String("#pp#;3;#vertices#;") + String.valueOf(n) + ";";

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
