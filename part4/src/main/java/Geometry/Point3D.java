package Geometry;

import lombok.SneakyThrows;

public class Point3D extends Point
{
		public Point3D()
		{
				super(3);
		}

		public Point3D(double[] x) throws Exception
		{
				super(3);
				if (x.length != 3)
						throw new Exception("Geometry.Point3D requires exact 3 cordinates");

				this.x[0] = x[0];			
				this.x[1] = x[1];
				this.x[2] = x[2];
		}

		public Point3D(double x, double y, double z)
		{
				super(3);
				this.x[0] = x;
				this.x[1] = y;
				this.x[2] = z;
		}

		public static Point3D cross_prod(Point3D p1, Point3D p2)
		{
				Point3D p3 = new Point3D();

				p3.x[0] = p1.x[1] * p2.x[2] - p1.x[2] * p2.x[1];
				p3.x[1] = p1.x[2] * p2.x[0] - p1.x[0] * p2.x[2];
				p3.x[2] = p1.x[0] * p2.x[1] - p1.x[1] * p2.x[0];

				return p3;
		}
	
		public Point3D cross_prod(Point3D p)
		{

				Point3D res = new Point3D();

				res.x[0] = x[1] * p.x[2] - x[2] * p.x[1];
				res.x[1] = x[2] * p.x[0] - x[0] * p.x[2];
				res.x[2] = x[0] * p.x[1] - x[1] * p.x[0];

				return res;
		}

		public Point2D toP2D()
		{
				return new Point2D(x[0],x[1]);
		}

		public static Point2D[] toPoints2D(Point3D[] p)
		{
				Point2D[] res = new Point2D[p.length];
				for (int i =0; i < p.length; ++i)
				{
						res[i] = new Point2D();
						res[i].x[0] = p[i].getX(0);
						res[i].x[1] = p[i].getX(1);
				}
				return res;
		}

		public static double mix_prod(Point3D p1, Point3D p2, Point3D p3)
		{
				Point3D crossProduct = new Point3D();

				crossProduct = cross_prod(p1,p2);
				
				double dotProduct = 0;
				for(int i = 0; i < 3; ++i)
						dotProduct += crossProduct.x[i] * p3.x[i];

				return dotProduct;
		}

		public double mix_prod(Point3D p1, Point3D p2)
		{
				Point3D crossProduct = new Point3D();

				crossProduct = cross_prod(p1,p2);
				
				double dotProduct = 0;
				for(int i = 0; i < dim; ++i)
						dotProduct += crossProduct.x[i] * x[i];

				return dotProduct;
		}

		public static Point3D add(Point3D a, Point3D b)
		{
				Point3D c = new Point3D();

				c.x[0] = a.x[0] + b.x[0];
				c.x[1] = a.x[1] + b.x[1];
				c.x[2] = a.x[2] + b.x[2];

				return c;
		}

		public Point3D add(Point3D p)
		{
				this.x[0] += p.x[0];
				this.x[1] += p.x[1];
				this.x[2] += p.x[2];

				return this;
		}

		public static Point3D sub(Point3D a, Point3D b)
		{
				Point3D c = new Point3D();

				c.x[0] = a.x[0] - b.x[0];
				c.x[1] = a.x[1] - b.x[1];
				c.x[2] = a.x[2] - b.x[2];

				return c;
		}

		public Point3D sub(Point3D p)
		{
				this.x[0] -= p.x[0];
				this.x[1] -= p.x[1];
				this.x[2] -= p.x[2];

				return this;
		}

		public static double mult(Point3D a, Point3D b)
		{
				return a.x[0]*b.x[0] + a.x[1]*b.x[1] + a.x[2] *b.x[2];
		}

		public double mult(Point3D b)
		{
				return this.x[0]*b.x[0] + this.x[1]*b.x[1] + this.x[2]*b.x[2];
		}

		public static Point3D mult(Point3D a, double coef)
		{
				return new Point3D(a.x[0]*coef,a.x[1]*coef,a.x[2]*coef);
		}

		public Point3D mult(double coef)
		{
				x[0]*=coef;
				x[1]*=coef;
				x[2]*=coef;
				return this;
		}

		private static double EPS = 0.0001;
		public static boolean isInOnePlane(Point3D[] p)
		{

				if(p.length < 4)
						return true;

				Point3D v1 = Point3D.sub(p[1],p[0]);
				Point3D v2 = Point3D.sub(p[2],p[0]);

				Point3D n = cross_prod(v1,v2);

				double A = n.x[0];
				double B = n.x[1];
				double C = n.x[2];

				for(int i = 3; i < p.length; ++i)
						if (Math.abs(A*(p[i].getX(0) - p[0].getX(0)) 
								+ (B*(p[i].getX(1) - p[0].getX(1))) 
								+ (C*(p[i].getX(2) - p[0].getX(2))) ) > EPS)
								return false;

				return true;
		}

		public static boolean convexityTest(Point3D[] p)
		{
				if(p.length < 3)
						return false;

				int cn = p.length;
				Point3D vv1 = Point3D.sub(p[0],p[cn-1]);
				Point3D vv2 = Point3D.sub(p[1],p[0]);
				
				double prevSign = vv1.getX(0) * vv2.getX(1) - vv1.getX(1) * vv2.getX(0);
				for (int i =1; i < cn -1; ++i)
				{
						
						Point3D v1 = Point3D.sub(p[i],p[i-1]);
						Point3D v2 = Point3D.sub(p[i+1],p[i]);
						double sign = v1.getX(0) * v2.getX(1) - v1.getX(1) * v2.getX(0);
						if (prevSign * sign < 0)
								return false;

						prevSign = sign;
				}
				return true;
		}

		public Point3D normalize()
		{
				double len = Math.sqrt(mult(this));
				this.x[0] /= len;
				this.x[1] /= len;
				this.x[2] /= len;

				return this;
		}

		public static Point2D[] moveFromPlane(Point3D[] p) throws Exception
		{
//				for(int i = 0; i < p.length; ++i)
//						System.out.println(p[i]);

				Point3D v1 = Point3D.sub(p[1],p[0]);
				Point3D v2 = Point3D.sub(p[2],p[0]);

//				System.out.println(v1);
//				System.out.println(v2);
//				
//				System.out.println("\n\n");

				v1.normalize();
				v2.normalize();
//				System.out.println(v1);
//				System.out.println(v2);
//				System.out.println("\n\n");
				
				Point3D v3 = Point3D.cross_prod(v1,v2).normalize();

				v1 = Point3D.cross_prod(v2,v3);

//				System.out.println(v1);
//				System.out.println(v2);
//				System.out.println(v3);
//				System.out.println("\n\n");
//
				Point2D[] res = new Point2D[p.length];
				for(int i =0; i < p.length; ++i)
				{
						Point3D t = Point3D.sub(p[i],p[0]);

						res[i] = new Point2D();
						res[i].x[0] = Point3D.mult(v1,t);
						res[i].x[1] = Point3D.mult(v2,t);
				}	

//				for(int i =0; i < p.length; ++i)
//				{
//						System.out.println(res[i]);
//				}	

			return res;	
		}


		public static Point3D randomPoint(double minX, double maxX, double minY, double maxY, double minZ, double maxZ)
		{
				return new Point3D(Math.random() * (maxX - minX) + minX,
						Math.random() * (maxY - minY) + minY,
						Math.random() * (maxZ - minZ) + minZ);
		}


		}
