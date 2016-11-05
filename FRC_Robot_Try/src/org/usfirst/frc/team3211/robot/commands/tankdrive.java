package org.usfirst.frc.team3211.robot.commands;
import org.usfirst.frc.team3211.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class tankdrive extends Command{

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
		Robot.drivetrain.tank(Robot.oi.stick.getRawAxis(1),Robot.oi.stick.getRawAxis(5));
			
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
