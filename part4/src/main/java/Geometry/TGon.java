package Geometry;

import lombok.ToString;
import org.bson.Document;
import org.bson.types.ObjectId;

@ToString
public class TGon extends NGon
{
		static private Point2D[] defConstructor = {new Point2D(0,0),new Point2D(0,1), new Point2D(1,0)};

		public TGon(Point2D[] p) throws Exception
		{
				super(p);
				if(p.length != 3)
						throw new Exception("Geometry.TGon requiers 3 points");

		}

		public TGon(Point2D a, Point2D b, Point2D c) throws Exception
		{
				super(defConstructor);

				this.p[0] = a;
				this.p[1] = b;
				this.p[2] = c;
		}

		public double square()
		{
				return Math.abs((p[1].getX(0) - p[0].getX(0))*(p[2].getX(1) - p[0].getX(1)) -
								(p[2].getX(0) - p[0].getX(0))*(p[1].getX(1) - p[0].getX(1))) / 2;
		}

		@Override
		public String curtForm()
		{
				String res = new String("#tg#;5;");

				for (int i = 0; i < 3; ++i)
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


				
