/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  public static Joystick stick = new Joystick(0);
  public static Joystick gamepad = new Joystick(1);
  public static JoystickButton pad1 = new JoystickButton(gamepad, 1);
  public static JoystickButton pad2 = new JoystickButton(gamepad, 2);
  public static JoystickButton pad3 = new JoystickButton(gamepad, 3);
  public static JoystickButton pad4 = new JoystickButton(gamepad, 4);
  public static JoystickButton pad5 = new JoystickButton(gamepad, 5);
  public static JoystickButton pad6 = new JoystickButton(gamepad, 6);
  public static JoystickButton pad7 = new JoystickButton(gamepad, 7);

  public static JoystickButton trigger = new JoystickButton(stick, 1);
  public static JoystickButton button3 = new JoystickButton(stick, 3);
  public static JoystickButton button2 = new JoystickButton(stick, 2);
  public static JoystickButton button5 = new JoystickButton(stick, 5);
  public static JoystickButton button4 = new JoystickButton(stick, 4);
  public static JoystickButton button6 = new JoystickButton(stick, 6);
  public static JoystickButton button7 = new JoystickButton(stick, 7);
  public static JoystickButton button8 = new JoystickButton(stick, 8);
  public static JoystickButton button10 = new JoystickButton(stick, 10);
  public static JoystickButton button11 = new JoystickButton(stick, 11);

  
  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());
  
  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());



  public OI(){
    
    button7.whenPressed(new MoveElevator(0));
    
    // button11.whenPressed(new Place(2));
    trigger.whenPressed(new Intake());
    trigger.whenReleased(new NeutralIntake() );
    pad1.whenPressed(new SetObjectMode("hatch"));
    pad1.whenReleased(new SetObjectMode("ball"));
    pad6.whenPressed(new MoveElevator(0));
    pad7.whenPressed(new MoveLift(2.5));

    button8.whenPressed(new InstantCommand(){
        @Override
        protected void initialize() {
          Robot.lift.resetEncoders();
        }
    });

    // pad5.whenPressed(new ClimbG());
    button11.whenPressed(new ClimbG());

    pad4.whenPressed(new LowConditional());
    pad3.whenPressed(new MiddleConditional());
    pad2.whenPressed(new TopConditional());

    // button4.whenPressed(new MoveLift(19.5));
    // button8.whenPressed(new MoveLift(0));
    // button4.whenPressed(new TestPID());


    
    // button8.whenPressed(new Climb(-15, power, distanceOne, distanceTwo));
  }
}
