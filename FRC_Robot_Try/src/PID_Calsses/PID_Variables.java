package PID_Calsses;

public class PID_Variables {
	public double error;
	public double lastError;
	public double errorSum;
	public Setpoint setpoint;
	
	public void resetVars(){
		error = 0;
		lastError = 0;
		errorSum = 0;
	}
}
