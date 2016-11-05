package MotionProfiling;

public class MPArcedPath {
	
	private MPMonoPath leftPath_, rightPath_;
	private double width_;
	
	public MPArcedPath(double radius, double angle, double Vmax, double V0, double Vend, double acc) {
		double distance = radius * angle;
		
		leftPath_ = new MPMonoPath(distance, Vmax, V0, Vend, acc);
		rightPath_ = new MPMonoPath(distance, Vmax, V0, Vend, acc);
		
		double faster = (radius + (width_ / 2)) / radius;
		double slower = (radius - (width_ / 2)) / radius;
		
		if (angle > 0) {
			leftPath_.scale(faster);
			rightPath_.scale(slower);
		} else {
			leftPath_.scale(slower);
			rightPath_.scale(faster);
		}
	}
	
	public MPMonoPath getLeftPath(){
		return leftPath_;
	}
	
	public MPMonoPath getRightPath(){
		return rightPath_;
	}
	
}
