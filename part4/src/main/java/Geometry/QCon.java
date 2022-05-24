package Geometry;

import lombok.ToString;
import org.bson.Document;
import org.bson.types.ObjectId;

@ToString
public class QCon extends NGon
{
		public QCon(Point2D[] p) throws Exception
		{
				super(p);

				if(p.length != 4)
						throw new Exception("Geometry.QCon requires 4 points");

		}
		
		public double square()
		{
				double t1 =  Math.abs((p[1].getX(0) - p[0].getX(0))*(p[2].getX(1) - p[0].getX(1)) -
								(p[2].getX(0) - p[0].getX(0))*(p[1].getX(1) - p[0].getX(1))) / 2;

				double t2 =  Math.abs((p[3].getX(0) - p[2].getX(0))*(p[0].getX(1) - p[2].getX(1)) -
								(p[0].getX(0) - p[2].getX(0))*(p[3].getX(1) - p[2].getX(1))) / 2;
				return t1 + t2;
		}

		@Override
		public String curtForm()
		{
				String res = new String("#pp#;6;");

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

