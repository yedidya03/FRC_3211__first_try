package org.usfirst.frc.team3211.robot.subsystems;

import java.util.Set;

import org.usfirst.frc.team3211.robot.Robot;
import org.usfirst.frc.team3211.robot.commands.tankdrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem{
	
	Victor leftMotor_ = new Victor(0);
	Victor rightMotor_ = new Victor(1);
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new tankdrive());
	}
	public void tank(double right, double left){
		leftMotor_.set(right);
		rightMotor_.set(left);
	}
	
	public double getLeftEncoder(){
		return 0;
	}
	
	public double getRightEncoder(){
		return 0;
	}
	
	public double getNavxAngle(){
		return 0;
	}
	
	public double getNavxDistance(){
		return 0;
	}
}
