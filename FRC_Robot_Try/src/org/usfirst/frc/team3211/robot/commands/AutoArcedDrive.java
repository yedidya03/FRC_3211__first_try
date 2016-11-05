package org.usfirst.frc.team3211.robot.commands;

import org.usfirst.frc.team3211.robot.Robot;

import sun.dc.pr.PathFiller;
import MotionProfiling.MPArcedPath;
import MotionProfiling.MPDoubleSidePos;
import MotionProfiling.MPDriveController;
import MotionProfiling.MPGains;
import MotionProfiling.MPMonoPath;
import PID_Calsses.PID_Variables;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoArcedDrive extends Command{

	private MPMonoPath leftPath_, rightPath_;
	private MPGains gains_;
	
	private double 	startingTime_, time_;
	private PID_Variables leftPID_, rightPID_;
	
	private MPDriveController driveController_;
	
	public AutoArcedDrive(MPDriveController driveController,double radius, double angle, double Vmax, double V0,
			double Vend, double acc, MPGains gains) {
		requires(Robot.drivetrain);
		
		MPArcedPath arcadePath = new MPArcedPath(radius, angle, Vmax, V0, Vend, acc);
		leftPath_ = arcadePath.getLeftPath();
		rightPath_ = arcadePath.getRightPath();
		
		gains_ = gains;
		driveController_ = driveController;
	}
	
	@Override
	protected void initialize() {
		leftPID_.resetVars();
		rightPID_.resetVars();
		
		startingTime_ = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		time_ = Timer.getFPGATimestamp() - startingTime_;
		
		MPDoubleSidePos currPos = driveController_.getPosition();
		
		leftPID_.setpoint = leftPath_.getCurrentState(time_);
		rightPID_.setpoint = rightPath_.getCurrentState(time_);
		
		leftPID_.error = leftPID_.setpoint.pos - currPos.left;
		rightPID_.error = rightPID_.setpoint.pos - currPos.right;
		
		double leftOutout = gains_.kv * leftPID_.setpoint.vel + gains_.ka * leftPID_.setpoint.acc
				+ gains_.kp * leftPID_.error + gains_.ki * leftPID_.errorSum +
				gains_.kd * (leftPID_.error - leftPID_.lastError);
		
		double rightOutout = gains_.kv * rightPID_.setpoint.vel + gains_.ka * rightPID_.setpoint.acc
				+ gains_.kp * rightPID_.error + gains_.ki * rightPID_.errorSum +
				gains_.kd * (rightPID_.error - rightPID_.lastError);
		
		leftPID_.errorSum += leftPID_.error;
		rightPID_.errorSum += rightPID_.error;
		
		if (leftPID_.error > 0 ^ leftPID_.lastError > 0){
			leftPID_.errorSum = 0;
		}
		
		if (rightPID_.error > 0 ^ rightPID_.lastError > 0){
			rightPID_.errorSum = 0;
		}
		
		leftPID_.lastError = leftPID_.error;
		rightPID_.lastError = rightPID_.error;
				
		driveController_.setOutput(leftOutout, rightOutout);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return time_ >= leftPath_.getTotalTime();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
