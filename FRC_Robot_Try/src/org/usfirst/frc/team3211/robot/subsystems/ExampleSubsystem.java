
package org.usfirst.frc.team3211.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ExampleSubsystem extends PIDSubsystem {
    
    public ExampleSubsystem(double p, double i, double d, double period,
			double f) {
		super(p, i, d, period, f);
		// TODO Auto-generated constructor stub
	}

	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void moveAngle(double angle){
    	resetAngle();
    	setSetpoint(angle);
    	enable();
    }
    
    public void resetAngle(){
    	
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
}

