package org.usfirst.frc.team3211.robot.subsystems;
import org.usfirst.frc.team3211.robot.commands.shoterCommand;

import edu.wpi.first.wpilibj.command.Subsystem;


public class shoter extends Subsystem{

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new shoterCommand());
	}
	public void time_shoter(){
	
	}
 
}
