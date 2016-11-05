package MotionProfiling;

import PID_Calsses.Setpoint;

public class MPMonoPath {
	
	public static final double MAX_SPEED = 2;
	public static final double MAX_ACCELERATION = 1;
	
	private double distance_, Vmax_, V0_, Vend_, acc_;
	private double speedingTime_, slowingTime_, cruisingTime_;
	private double speedingDistance_, cruisingDistance_, slowingDistance_;
	
	public MPMonoPath(double distance){
		if (distance > 0){
			calculateTimes(distance, MAX_SPEED, 0, 0, MAX_ACCELERATION);
		}else {
			calculateTimes(distance, -MAX_SPEED, 0, 0, -MAX_ACCELERATION);
		}
	}
	
	public MPMonoPath(double distance, double V0, double Vend) {
		if (distance > 0){
			calculateTimes(distance, MAX_SPEED, V0, Vend, MAX_ACCELERATION);
		}else {
			calculateTimes(distance, -MAX_SPEED, V0, Vend, -MAX_ACCELERATION);
		}
	}
	
	public MPMonoPath(double distance, double Vmax, double V0, double Vend, double acc) {
		calculateTimes(distance, Vmax, V0, Vend, acc);
	}
	
	private void calculateTimes(double distance, double Vmax, double V0, double Vend, double acc){
		double speeding2VmaxDistance = (Vmax * Vmax - V0 * V0) / (2 * acc);
		double slowing2VendDistance = (Vmax * Vmax - Vend * Vend) / (2 * acc);
		
		double cruisingDistance = distance - (speeding2VmaxDistance + slowing2VendDistance);
		
		/*
		 * this condition is if just speeding to maximum velocity will take the
		 * mechanism too far you need to slow down before reaching maximum velocity
		 * 
		 * the condition is calculated by this table :
		 * 
		 *  _________________________________________________________________________
		 * |              | distance > dis2VmaxAndBack | distance <= dis2VmaxAndBack |
		 * |______________|____________________________|_____________________________|
		 * |distance > 0  |             ok             |     need to recalculate     |
		 * |______________|____________________________|_____________________________|
		 * |distance <= 0 |     need to recalculate    |              ok             |
		 * |______________|____________________________|_____________________________|
		*/
		if (distance > 0 ^ distance > (speeding2VmaxDistance + slowing2VendDistance)){
			Vmax = Math.sqrt((2 * distance * acc + V0 * V0 + Vend * Vend) / 2);
			cruisingDistance = 0;
		}
		
		speedingTime_ = (Vmax - V0) / acc;
		slowingTime_ = (Vmax - Vend) / acc;
		cruisingTime_ = cruisingDistance / Vmax;
		
		speedingDistance_ = (Vmax * Vmax - V0 * V0) / (2 * acc);
		cruisingDistance_ = cruisingDistance;
		slowingDistance_ = (Vmax * Vmax - Vend * Vend) / (2 * acc);
		
		Vmax_ = Vmax;
		V0_ = V0;
		Vend_ = Vend;
		distance_ = distance;
		acc_ = acc;
		
		System.out.println("Times :");
		System.out.println("Speeding : " + speedingTime_);
		System.out.println("Cruising : " + cruisingTime_);
		System.out.println("Slowing  : " + slowingTime_);
		
		System.out.println("Distances : ");
		System.out.println("Speeding : " + speedingDistance_);
		System.out.println("Cruising : " + cruisingDistance_);
		System.out.println("Slowing  : " + slowingDistance_);
	}
	
	public Setpoint getCurrentState(double currTime){
		Setpoint setpoint = new Setpoint();
		
		if (currTime < speedingTime_) {
			setpoint.pos = V0_ * currTime + 0.5 * acc_ * currTime * currTime;
			setpoint.vel = V0_ + acc_ * currTime;
			setpoint.acc = acc_;
			
			return setpoint;
		}
		
		currTime -= speedingTime_;
		
		if (currTime < cruisingTime_){
			setpoint.pos = speedingDistance_ + currTime * Vmax_;
			setpoint.vel = Vmax_;
			setpoint.acc = 0;
			
			return setpoint;
		}
		
		currTime -= cruisingTime_;
		
		if (currTime < slowingTime_){
			setpoint.pos = speedingDistance_ + cruisingDistance_ +
					Vmax_ * currTime - 0.5 * acc_ * currTime * currTime;
			setpoint.vel = Vmax_ - acc_ * currTime;
			setpoint.acc = -acc_;
			
			return setpoint;
		}
		
		setpoint.pos = distance_;
		setpoint.vel = Vend_;
		setpoint.acc = 0;
		
		return setpoint;
	}
	
	public double getTotalTime(){
		return speedingTime_ + cruisingTime_ + slowingTime_;
	}
	
	public void scale(double scaleRate){
		distance_ *= scaleRate;
		Vmax_ *= scaleRate;
		acc_ *= scaleRate;
		V0_ *= scaleRate;
		Vend_ *= scaleRate;
	}
}
