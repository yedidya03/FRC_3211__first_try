package MotionProfiling;

import PID_Calsses.Setpoint;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class MPSubsystem extends Subsystem{
	
	protected Setpoint currState;
	protected double defultVmax = 0, defultAcc = 0;
	
	
	public abstract double getPosition();
	public abstract void setOutput(double output);
	
	/*
	 * sets the current state userlly in a finish of a privies motion
	 * 
	 * if the system is continues (like moving wheels that need to rotate 1 rotation every time)
	 * the pos value should always return to 0 
	 * (so in the next motion the movment will be from this position)
	 */
	public abstract void setCurrState(Setpoint state);
	public Setpoint getCurrState(){
		return currState;
	}
	
	public abstract MPGains getDefultGains();
	
	public double getDefultVmax(){
		return defultVmax;
	}
	
	public double getDefultAcc(){
		return defultAcc;
	}
}
