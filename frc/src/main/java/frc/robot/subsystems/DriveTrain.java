/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;

/**
 * The motors that control the drive base of the robot.
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public TalonSRX frontLeft = new TalonSRX(RobotMap.FRONT_LEFT_ID);
  public TalonSRX backLeft = new TalonSRX(RobotMap.BACK_LEFT_ID);
  public TalonSRX frontRight = new TalonSRX(RobotMap.FRONT_RIGHT_ID);
  public TalonSRX backRight = new TalonSRX(RobotMap.BACK_RIGHT_ID);

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
  }

  public void controllerDrive() {
    /*if (Robot.oi.getLeftStick() > 0.2) {
      frontLeft.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getLeftStick(), 0.2, 1, 0, 1));
      backLeft.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getLeftStick(), 0.2, 1, 0, 1));
    } else if (Robot.oi.getLeftStick() < -0.2) {
      frontLeft.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getLeftStick(), -0.2, -1, 0, -1));
      backLeft.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getLeftStick(), -0.2, -1, 0, -1));
    } else {
      frontLeft.set(ControlMode.PercentOutput, 0);
      backLeft.set(ControlMode.PercentOutput, 0);
    }
    if (Robot.oi.getRightStick() > 0.2) {
      frontRight.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getRightStick(), 0.2, 1, 0, 1));
      backRight.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getRightStick(), 0.2, 1, 0, 1));
    } else if (Robot.oi.getRightStick() < -0.2) {
      frontRight.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getRightStick(), -0.2, -1, 0, -1));
      backRight.set(ControlMode.PercentOutput, Functions.map(Robot.oi.getRightStick(), -0.2, -1, 0, -1));
    } else {
      frontRight.set(ControlMode.PercentOutput, 0);
      backRight.set(ControlMode.PercentOutput, 0);
    }*/
  }

  private double deadband(double val) {
    return val = (val < -0.2 ? val : (val > 0.2 ? val : (val = 0)));
  }

  /**
   * Controls the robot based on identified vision targets, orienting
   * itself and allowing forward and backward movement when a target
   * is identified, otherwise allow normal movement.
   */
  public void trackVision() {
    double margin = 5;
    if (Robot.limelight.hasTarget()) {
      double target = Robot.limelight.targetX();
      if (target < -margin) {
        spinLeft(0.23);
        spinRight(-0.23);
      } else if (target > margin) {
        spinLeft(-0.23);
        spinRight(0.23);
      } else {
        if (Robot.oi.getY() > 0.2 || Robot.oi.getY() < 0.2) {
          spinLeft(Robot.oi.getY());
          spinRight(Robot.oi.getY());
        } else {
          stop();
        }
      }
    } else {
      arcadeDrive();
    }
  }

  /**
   * Drives the robot using the arcade drive style, where forward and
   * backward movement is controlled by the Y axis of the joystick
   * and rotation is controlled by the X axis.
   */
  public void arcadeDrive() {
    double throt = deadband(Robot.oi.getY());
    double turn = deadband(Robot.oi.getX() * 0.3);
    //if (throt > 0) {
      //if (throt < 0)
       // turn = -turn;
      spinLeft(turn + throt);
      spinRight(-turn + throt);
    /*} else if (throt < 0) {
      turn = -turn;
      frontLeft.set(ControlMode.PercentOutput, turn + throt);
      backLeft.set(ControlMode.PercentOutput, turn + throt);
      frontRight.set(ControlMode.PercentOutput, turn - throt);
      backRight.set(ControlMode.PercentOutput, turn - throt);
    } else {
      frontLeft.set(ControlMode.PercentOutput, 0);
      backLeft.set(ControlMode.PercentOutput, 0);
      frontRight.set(ControlMode.PercentOutput, 0);
      backRight.set(ControlMode.PercentOutput, 0);
    }*/


    /*
    if (Math.abs(Robot.oi.getX()) > 0.1 || Math.abs(Robot.oi.getY()) > 0.1) {
      frontLeft.set(ControlMode.PercentOutput, Robot.oi.getY() - Robot.oi.getX());
      backLeft.set(ControlMode.PercentOutput, Robot.oi.getY() - Robot.oi.getX());
      frontRight.set(ControlMode.PercentOutput, Robot.oi.getY() + Robot.oi.getX());
      backRight.set(ControlMode.PercentOutput, Robot.oi.getY() + Robot.oi.getX());
    } else {
      frontLeft.set(ControlMode.PercentOutput, 0);
      backLeft.set(ControlMode.PercentOutput, 0);
      frontRight.set(ControlMode.PercentOutput, 0);
      backRight.set(ControlMode.PercentOutput, 0);
    }*/
  }

  /**
   * Spins the two left side motors of the robot's drive base.
   * 
   * @param speed a double in the range of -1 to 1
   */
  public void spinLeft(double speed) {
    frontLeft.set(ControlMode.PercentOutput, speed);
    backLeft.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Stops the two left side motors of the robot's drive base.
   */
  public void stopLeft() {
    spinLeft(0);
  }

  /**
   * Spins the two right side motors of the robot's drive base.
   * 
   * @param speed a double in the range of -1 to 1
   */
  public void spinRight(double speed) {
    frontRight.set(ControlMode.PercentOutput, -speed);
    backRight.set(ControlMode.PercentOutput, -speed);
  }

  /**
   * Stops the two right side motors of the robot's drive base.
   */
  public void stopRight() {
    spinRight(0);
  }

  /**
   * Stops the four motors of the robot's drive base.
   */
  public void stop() {
    stopLeft();
    stopRight();
  }

}
