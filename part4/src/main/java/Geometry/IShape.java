package Geometry;

public interface IShape extends IMoveable
{
		public double square();
		public double length();
		public boolean cross(IShape i);
}
