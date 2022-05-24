package Geometry;

import lombok.ToString;
import org.bson.Document;
import org.bson.types.ObjectId;

@ToString
public class Rectangle extends QCon
{
		private static double EPS = 0.0001;

		public Rectangle(Point2D[] p) throws Exception
		{
				super(p);

				Point2D s1 = Point2D.sub(p[1],p[0]);
				Point2D s2 = Point2D.sub(p[3],p[0]);
				Point2D s3 = Point2D.sub(p[2],p[1]);
				Point2D s4 = Point2D.sub(p[2],p[3]);

				Point2D d1 = Point2D.sub(p[2],p[0]);
				Point2D d2 = Point2D.sub(p[3],p[1]);

				double c1 = Point.mult(s1,s1) - Point.mult(s3,s3);
				double c2 = Point.mult(s2,s2) - Point.mult(s4,s4);
				double c3 = Point.mult(d1,d1) - Point.mult(d2,d2);

				if (c1 + c2 + c3 > EPS)
						throw new Exception("Geometry.QCon is not a square");
		}

		public double square()
		{
				return Point2D.sub(p[1],p[0]).abs() * Point2D.sub(p[2],p[1]).abs();
		}

		@Override
		public String curtForm()
		{
				String res = new String("#rc#;7;");

				for (int i = 0; i < 4; ++i)
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
