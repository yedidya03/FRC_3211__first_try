package org.usfirst.frc.team3211.robot.commands;

import MotionProfiling.MPMonoPath;
import MotionProfiling.MPGains;
import MotionProfiling.MPSubsystem;
import PID_Calsses.Setpoint;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MPCommand extends Command{
	private MPSubsystem system_;
	
	private MPMonoPath motion_;
	private double 	startingTime_, time_;
	private MPGains gains_;
	private double errorSum_, lastError_;
	
	public MPCommand(MPSubsystem system, double movment, double Vmax, double Vend, double acc, MPGains gains) {
		requires(system);
		
		// TODO Auto-generated constructor stub
		motion_ = new MPMonoPath(movment - system.getCurrState().pos, Vmax, system.getCurrState().vel, Vend, acc);
		system_ = system;
		gains_ = gains;
	}
	
	public MPCommand(MPSubsystem system, MPMonoPath path, MPGains gains){
		requires(system);
		
		motion_ = path;
		system_ = system;
		gains_ = gains;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		startingTime_ = Timer.getFPGATimestamp();
		errorSum_ = 0;
		lastError_ = 0;
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		time_ = Timer.getFPGATimestamp() - startingTime_;
		
		Setpoint setpoint = motion_.getCurrentState(time_);
		
		double error = setpoint.pos - system_.getPosition();
		
		double output = gains_.kv * setpoint.vel + gains_.ka * setpoint.acc +
				error * gains_.kp + errorSum_ * gains_.ki + (error - lastError_) * gains_.kd;
		
		errorSum_ += error;
		
		if (error > 0 ^ lastError_ > 0){
			errorSum_ = 0;
		}
		
		lastError_ = error;
		
		system_.setOutput(output);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return time_ >= motion_.getTotalTime();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		time_ = Timer.getFPGATimestamp() - startingTime_;
		system_.setCurrState(motion_.getCurrentState(time_));
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
