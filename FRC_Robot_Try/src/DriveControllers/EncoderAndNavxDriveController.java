package DriveControllers;

import org.usfirst.frc.team3211.robot.Robot;

import MotionProfiling.MPDoubleSidePos;
import MotionProfiling.MPDriveController;

public class EncoderAndNavxDriveController extends MPDriveController{

	double width_;
	
	@Override
	public MPDoubleSidePos getPosition() {
		// TODO Auto-generated method stub
		double distance = (Robot.drivetrain.getLeftEncoder() + Robot.drivetrain.getRightEncoder()) / 2;
		double angle =  Robot.drivetrain.getNavxAngle();
		double radius = distance / angle;
		
		double fasterRadius = (radius + (width_ / 2));
		double slowerRadius = (radius - (width_ / 2));
		
		MPDoubleSidePos pos = new MPDoubleSidePos();
		pos.left = angle * fasterRadius;
		pos.right = angle * slowerRadius;
		
		return pos;
	}

}
