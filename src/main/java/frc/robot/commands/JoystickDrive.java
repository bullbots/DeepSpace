/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.Constants;

public class JoystickDrive extends Command {
  public JoystickDrive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.drivetrain.setPIDSlot(1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double joyX = OI.stick.getX();
    double joyY = OI.stick.getY();

    Robot.drivetrain.arcadeDrive(joyX, joyY, true);
    Robot.drivetrain.print();

    /*if(Math.abs(joyX)> .1){
      //Robot.drivetrain.turn("pow", joyX);
      Robot.drivetrain.turn("vel", joyX*Constants.kMaxVelocity);
    }else if(Math.abs(joyY) > .1){
      //Robot.drivetrain.move("pow", joyY);
      Robot.drivetrain.move("vel", joyY*Constants.kMaxVelocity);
    }else{
      Robot.drivetrain.move("vel", 0);
    }
    Robot.drivetrain.print();*/


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