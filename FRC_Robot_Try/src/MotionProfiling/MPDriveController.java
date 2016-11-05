package MotionProfiling;

import org.usfirst.frc.team3211.robot.Robot;

public abstract class MPDriveController {

	public void setOutput(double left, double right){
		Robot.drivetrain.tank(right, left);
	}
	
	public abstract MPDoubleSidePos getPosition();
}
