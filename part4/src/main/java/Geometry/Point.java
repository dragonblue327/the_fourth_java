package Geometry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Point
{
	@Getter
		protected int dim;
	@Getter
	@Setter
		protected double[] x;

		public Point(int dim)
		{
				this.dim = dim;
				x = new double[dim];

				for( int i = 0; i < dim; ++i)
						x[i] = 0; 
		}

		public Point(int dim, double[] x) throws Exception
		{
				if(x.length < dim)
						throw new Exception("Geometry.Point requires dim <= x.length");

				this.dim = dim;
				this.x = new double[dim];

				for( int i = 0; i < dim; ++i)
				{
						this.x[i] = x[i];
				}
		}

		public double getX(int i)
		{
				return x[i];
		}

		public void setX(double x, int i)
		{
				this.x[i] = x;
		}

		public double abs()
		{
				double dotProduct = 0;
				for (int i = 0; i < dim; ++i)
						dotProduct += x[i] * x[i];

				return Math.sqrt(dotProduct);
		}

		public static Point add(Point a, Point b) throws Exception
		{
				if(a.dim != b.dim)
						throw new Exception("Geometry.Point.add requires a.dim == b.dim");

				Point c = new Point(a.getDim());

				for( int i = 0; i < a.getDim(); ++i)
						c.x[i] = a.x[i] + b.x[i];

				return c;
		}


		public Point add(Point b) throws Exception
		{
				if(this.dim != b.dim)
						throw new Exception("Geometry.Point.add requires this.dim == b.dim");

				for (int i = 0; i< dim; ++i)
						x[i] += b.x[i];
				return this;
		}


		public static Point sub(Point a, Point b) throws Exception
		{
				if(a.dim != b.dim)
						throw new Exception("Geometry.Point.sub requires a.dim == b.dim");

				Point c = new Point(a.getDim());

				for( int i = 0; i < a.getDim(); ++i)
						c.x[i] = a.x[i] - b.x[i];

				return c;
		}


		public Point sub(Point b) throws Exception
		{
				if(this.dim != b.dim)
						throw new Exception("Geometry.Point.sub requires this.dim == b.dim");
				
				for (int i = 0; i< dim; ++i)
						x[i] -= b.x[i];
				return this;
		}

		public static Point mult(Point a, double r)
		{
				Point c = new Point(a.getDim());

				for( int i = 0; i < a.getDim(); ++i)
						c.x[i] = a.x[i] * r;

				return c;
		}


		public Point mult(double r)
		{
				for (int i = 0; i< dim; ++i)
						x[i] *= r;
				return this;
		}


		public static double mult(Point a, Point b) throws Exception
		{
				if(a.dim != b.dim)
						throw new Exception("Geometry.Point.mult requires a.dim == b.dim");

				double dotProduct = 0;
				for( int i = 0; i < a.getDim(); ++i)
						dotProduct += a.x[i] * b.x[i];

				return dotProduct;
		}


		public double mult(Point b) throws Exception
		{
				if(this.dim != b.dim)
						throw new Exception("Geometry.Point.mult requires this.dim == b.dim");

				double dotProduct = 0;
				for( int i = 0; i < dim; ++i)
						dotProduct += x[i] * b.x[i];

				return dotProduct;
		}

		public static Point symAxis(Point a, int i)
		{
				Point c = new Point(a.dim);

				for (int j = 0; j < a.dim; ++j)
						c.x[j] = -a.x[j];
				c.x[i] = a.x[i];

				return c;
		}

		public Point symAxis(int i)	
		{
				for (int j = 0; j < dim; ++j)
						x[j] = -x[j];
				x[i] = -x[i];

				return this;
		}
}


