/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class LiftJoyStick extends Command {
  public LiftJoyStick() {
    requires(Robot.liftSubsystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.robotMap.liftBrushLessMaster.set(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double liftJoystickValues = Robot.m_oi.left.getY();
    Robot.robotMap.liftBrushLessMaster.set(liftJoystickValues);

    SmartDashboard.putNumber("Lift Encoder Values", Robot.robotMap.liftEncoder1.getPosition());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}