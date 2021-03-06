/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.realtest;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IO;
import frc.robot.subsystems.Lift;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.vision.Block;
import frc.robot.vision.Pixy2SpiJNI;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static boolean debug = true;
  public static RobotMap robotMap = new RobotMap();
  public static Lift lift = new Lift();
  public static OI m_oi;
  public static Pixy2SpiJNI pixy2SpiJNI = new Pixy2SpiJNI();
  public static Drivetrain drivetrain = new Drivetrain();
  public static IO io = new IO();
  public static Compressor compressor = new Compressor(0);

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

   

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    //compressor.setClosedLoopControl(true);
    compressor.start();
    // lift.resetEncoders();
    //m_chooser.setDefaultOption("Default Auto", new DriveCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    //SmartDashboard.putData("Auto mode", m_chooser);
    SmartDashboard.putData("TestRun", new realtest(5));
    SmartDashboard.putNumber("MyValue", 5);

    new Thread(() -> {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
      camera.setResolution(320, 240);
      
      CvSink cvSink = CameraServer.getInstance().getVideo();
      CvSource outputStream = CameraServer.getInstance().putVideo("Video", 320, 240);
      
      Mat source = new Mat(320,240, CvType.CV_8UC3);

      while(!Thread.interrupted()) {
          cvSink.grabFrame(source);
          Imgproc.line(source, new Point(280, 0), new Point(280, 240), new Scalar(0,255,0), 2);
          outputStream.putFrame(source);
      }
  }).start();

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    double[] currents = lift.getAmps();
    SmartDashboard.putNumber("Elevator Current", currents[0]);
    SmartDashboard.putNumber("Lift Current", currents[1]);
    SmartDashboard.putNumber("Elevator Position", Robot.lift.getElevatorPosition());
    SmartDashboard.putNumber("Lift Position", Robot.lift.getLiftPosition());
    double[] temps = lift.getTemps();
    SmartDashboard.putNumber("Lift Temperature", temps[0]);
    SmartDashboard.putNumber("Elevator Temperature", temps[1]);
    SmartDashboard.putBoolean("Piston Pos", !io.getPistonPos());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    drivetrain.set(ControlMode.PercentOutput, 0, 0);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    lift.zero();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    lift.setElevator(1);
    lift.setLift(1);
    lift.setDrive(0);

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      System.out.println("Running Autonomous");
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {

    lift.setElevator(0);
    lift.setLift(1);
    lift.setDrive(0);
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
     int[] velocities = Robot.drivetrain.getVelocity();
     int leftside = velocities[0];
     int rightside = velocities[1];

     int[] positions = Robot.drivetrain.getPosition();
     int pLeftside = positions[0];
     int pRightside = positions[1];

     double[] currents = Robot.drivetrain.getCurrent();
     double cLeftside = currents[0];
     double cRightside = currents[1];
    
     SmartDashboard.putNumber("Leftside Position ", pLeftside);
     SmartDashboard.putNumber("Rightside Position ", pRightside);
     SmartDashboard.putNumber("Leftside Velocity ", leftside);
     SmartDashboard.putNumber("Rightside Velocity ", rightside);
     SmartDashboard.putNumber("Leftside Current ", cLeftside);
     SmartDashboard.putNumber("Righside Current ", cRightside);

    //Scheduler.getInstance().run();
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }


}
