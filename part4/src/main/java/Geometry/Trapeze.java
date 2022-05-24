package Geometry;

import lombok.ToString;
import org.bson.Document;
import org.bson.types.ObjectId;

@ToString
public class Trapeze extends QCon
{
		private static double EPS = 0.0001;

		private boolean parallPair_f_13__t_24;

		public Trapeze(Point2D[] p) throws Exception
		{
				super(p);

				Point2D s1 = Point2D.sub(p[1],p[0]);
				Point2D s2 = Point2D.sub(p[3],p[0]);
				Point2D s3 = Point2D.sub(p[2],p[1]);
				Point2D s4 = Point2D.sub(p[2],p[3]);

				double c1 = Point.mult(s1,s3) * Point.mult(s1,s3) - Point.mult(s1,s1) * Point.mult(s3,s3);
				double c2 = Point.mult(s2,s4) * Point.mult(s2,s4) - Point.mult(s2,s2) * Point.mult(s4,s4);
				
				parallPair_f_13__t_24 = c2 < EPS;

				if( c1 > EPS && !parallPair_f_13__t_24)
						throw new Exception("Geometry.QCon is not a Geometry.Trapeze");
		}

		@Override
		public String curtForm()
		{
				String res = new String("#tr#;8;");

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
