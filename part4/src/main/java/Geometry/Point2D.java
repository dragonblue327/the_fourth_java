package Geometry;

import lombok.SneakyThrows;

public class Point2D extends Point
{
		public Point2D()
		{
				super(2);
		}


		public Point2D(double[] x) throws Exception
		{
				super(2);
				if (x.length != 2)
						throw new Exception("Geometry.Point2D requires 2 cordinates");

				this.x[0] = x[0];
				this.x[1] = x[1];
		}

		public Point2D(double x, double y)
		{
				super(2);
				this.x[0] = x;
				this.x[1] = y;
		}


		public static Point2D rot(Point2D p, double phi)
		{
				Point2D n = new Point2D();

				n.x[0] = p.x[0] * Math.cos(phi) - p.x[1] * Math.sin(phi);
				n.x[1] = p.x[0] * Math.sin(phi) + p.x[1] * Math.cos(phi);
				return n;
		}

		public Point2D rot(double phi)
		{

				double tx0 = x[0] * Math.cos(phi) - x[1] * Math.sin(phi);
				double tx1 = x[0] * Math.sin(phi) + x[1] * Math.cos(phi);
				x[0] = tx0;
				x[1] = tx1;

				return this;
		}

		public static Point2D add(Point2D a, Point2D b)
		{
				Point2D c = new Point2D();

				c.x[0] = a.x[0] + b.x[0];
				c.x[1] = a.x[1] + b.x[1];

				return c;
		}

		public Point2D add(Point2D p)
		{
				this.x[0] += p.x[0];
				this.x[1] += p.x[1];

				return this;
		}

		public static Point2D sub(Point2D a, Point2D b)
		{
				Point2D c = new Point2D();

				c.x[0] = a.x[0] - b.x[0];
				c.x[1] = a.x[1] - b.x[1];

				return c;
		}

		public Point2D sub(Point2D p)
		{
				this.x[0] -= p.x[0];
				this.x[1] -= p.x[1];

				return this;
		}

		public static double mult(Point a, Point b)
		{
				return a.getX(0)*b.getX(0) + a.getX(1)*b.getX(1);
		}

		public double mult(Point b)
		{
				return this.getX(0)*b.getX(0) + this.getX(1)*b.getX(1);
		}

		public static Point2D mult(Point2D a, double coef)
		{
				return new Point2D(a.x[0]*coef,a.x[1]*coef);
		}

		public Point2D mult(double coef)
		{
				x[0]*=coef;
				x[1]*=coef;
				return this;
		}

		public static Point2D randomPoint(double minX, double maxX, double minY, double maxY)
		{
				return new Point2D(Math.random() * (maxX - minX) + minX, Math.random() * (maxY - minY) + minY);
		}

}
