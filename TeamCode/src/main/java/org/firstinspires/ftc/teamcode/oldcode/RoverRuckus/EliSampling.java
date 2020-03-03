
package org.firstinspires.ftc.teamcode.oldcode.RoverRuckus;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_COEF;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_THRESHOLD;
//Lara + Liesel positioning code


@Autonomous(name = "AutoL&L", group = "Cosmo")
@Disabled
public class EliSampling extends LinearOpMode {

    /* Declare OpMode members. */
//    Hardware8045testbot Cosmo = new Hardware8045testbot();   // Use a Pushbot's hardware
    Hardware8045 Cosmo = new Hardware8045();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime LEDcycletime = new ElapsedTime();
    final int blinktime = 200;  // milliseconds for the lights to be on/off

    /**
     * Menu Parameter Initialization
     **/
    public boolean teamIsRed = true;
    public boolean craterPosition = false;
    public boolean testBot = true;
    public int waitTime1 = 0;
    public int driveDis1 = 15;
    public int driveDis2 = 15;
    public int driveDis3 = 7; //forward+backward
    public int driveDis4 = 50; //drive to wall
    public int driveDis5 = 55; //drive to base
    public int driveDis6 = 60; //drive to crater
    public int driveDis7 = 0;
    public int driveDis8 = 0;
    public int driveDis9 = 0;
    public int driveDis10 = 0;


    // State used for updating telemetry
    public Orientation angles;
    public Acceleration gravity;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AWfr4/T/////AAAAGRMg80Ehu059mDMJI2h/y+4aBmz86AidOcs89UScq+n+QQyGFT4cZP+rzg1M9B/CW5bgDoVf16x6x3WlD5wYKZddt0UWQS65VIFPjZlM9ADBWvWJss9L1dj4X2LZydWltdeaBhkXTXFnKBkKLDcdTyC2ozJlcAUP0VnLMeI1n+f5jGx25+NdFTs0GPJYVrPQRjODb6hYdoHsffiOCsOKgDnzFsalKuff1u4Z8oihSY9pvv3me2gJjzrQKqp2gCRIZAXDdYzln28Z/8vNSU+aXr6eoRrNXPpYdAwyYI+fX2V9H04806eSUKsNYcPBSbVlhe2KoUsSD7qbOsBMagcEIdMZxo010kVCHHhnhV3IFIs8";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        final double FORWARD_SPEED = 0.3;
        final double TURN_SPEED = 0.3;
        final int cycletime = 500;
        int goldPosition = 0;   // 0 is on left, 1 in center, 2 on right


        /*
         * Initialize the drive system variables.the Robot
         * The init() method of the hardware class does all the work here
         */
        Cosmo.init(hardwareMap);

        /** TURN ON LIGHTS */
        if (teamIsRed) {
            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_RED);
        } else {
            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_BLUE);
        }
        //Create variable thing for light colors  based on team color
        RevBlinkinLedDriver.BlinkinPattern teamColor;
        if (teamIsRed) {
            teamColor = RevBlinkinLedDriver.BlinkinPattern.RED;
        } else {
            teamColor = RevBlinkinLedDriver.BlinkinPattern.BLUE;
        }

        /** TURN ON LIGHTS */
        //Cosmo.LEDDriver.setPattern(teamColor);

        // get a reference to the RelativeLayout so we can change the background  for Edit mode
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        if (teamIsRed) {
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.RED);
                }
            });
        } else {
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.BLUE);
                }
            });
        }

        /***********************************************************************************************
         * The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that first.
         *  Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.  */

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.

        /** Initialize the Tensor Flow Object Detection engine. */
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset("RoverRuckus.tflite", "Gold", "Silver");

        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        /** Activate Tensor Flow Object Detection. */
        if (tfod != null) {
            tfod.activate();
        }


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        //Clamp Team Marker
        Cosmo.flagServo.setPosition(0.315);

        /**************************************************************
         // Actual Init loop
         *************************************************************/
        while (!opModeIsActive() && !isStopRequested()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    if (teamIsRed) {
                        telemetry.addData("", "RED");
                    } else {
                        telemetry.addData("", "BLUE");
                    }
                    if (craterPosition) {
                        telemetry.addData("", "Crater");
                    } else {
                        telemetry.addData("", "Base");
                    }
                    telemetry.addLine(" Press Left Joystick for Edit");
                    telemetry.addData("# Objects Detected", updatedRecognitions.size());
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addLine().addData("", "%.2f %s   X %.0f Y %.0f", recognition.getConfidence(), recognition.getLabel(), recognition.getLeft(), recognition.getBottom());
                    }


                    if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals("Gold")) {
                                goldMineralX = (int) recognition.getLeft();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getLeft();
                            } else {
                                silverMineral2X = (int) recognition.getLeft();
                            }
                        }
                        //Eli Edit V
                        if (goldMineralX <= .3) {
                            goldPosition = 0;
                        } else if (goldMineralX > .3 && goldMineralX < .7) {
                            goldPosition = 1;
                        } else {
                            goldPosition = 2;
                        }
                        //Eli Edit ^^
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                goldPosition = 0;
                                telemetry.addData("Gold Mineral Position", "Left").addData(" ", goldPosition);
                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                goldPosition = 2;
                                telemetry.addData("Gold Mineral Position", "Right").addData(" ", goldPosition);
                            } else {
                                goldPosition = 1;
                                telemetry.addData("Gold Mineral Position", "Center").addData(" ", goldPosition);
                            }
                        }
                    } else {
                        goldPosition = 99;
                        telemetry.addData("Gold Mineral NOT FOUND ", goldPosition);
                    }
                    //telemetry.update();
                }
            }
            /** Eli's edit Menu params  **/
            if (gamepad1.back || gamepad1.left_stick_button) {             // edit parameters  & write the new file
                // change the background color to yellow
//                relativeLayout.post(new Runnable() {
//                    public void run() { relativeLayout.setBackgroundColor(Color.YELLOW); }
//                });

                editParameters();

//                if (teamIsRed) {
//                    relativeLayout.post(new Runnable() {
//                        public void run() { relativeLayout.setBackgroundColor(Color.RED); }
//                    });
//                } else {
//                    relativeLayout.post(new Runnable() {
//                        public void run() { relativeLayout.setBackgroundColor(Color.BLUE); }
//                    });
//                }
            }


            /** Signal the position of the gold mineral  here. From POV of driver**/

            if (goldPosition == 0) {
                if ((LEDcycletime.milliseconds() < blinktime)) {                 // blink pattern white white gold
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                } else if ((LEDcycletime.milliseconds() < 2 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else if ((LEDcycletime.milliseconds() < 3 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                } else if ((LEDcycletime.milliseconds() < 4 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else if ((LEDcycletime.milliseconds() < 5 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
                } else if ((LEDcycletime.milliseconds() < 12 * blinktime)) {                                       // back to team color
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else {                                      // reset timer, repeat cycle
                    LEDcycletime.reset();
                }

            } else if (goldPosition == 1) {     // insert blink pattern for white gold white  here
                if ((LEDcycletime.milliseconds() < blinktime)) {                 // blink pattern white white gold
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                } else if ((LEDcycletime.milliseconds() < 2 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else if ((LEDcycletime.milliseconds() < 3 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
                } else if ((LEDcycletime.milliseconds() < 4 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else if ((LEDcycletime.milliseconds() < 5 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                } else if ((LEDcycletime.milliseconds() < 12 * blinktime)) {                                       // back to team color
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else {                                      // reset timer, repeat cycle
                    LEDcycletime.reset();
                }
            } else if (goldPosition == 2) {     // insert blink pattern for gold white white  here
                if ((LEDcycletime.milliseconds() < blinktime)) {                 // blink pattern white white gold
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
                } else if ((LEDcycletime.milliseconds() < 2 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else if ((LEDcycletime.milliseconds() < 3 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                } else if ((LEDcycletime.milliseconds() < 4 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else if ((LEDcycletime.milliseconds() < 5 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
                } else if ((LEDcycletime.milliseconds() < 12 * blinktime)) {                                       // back to team color
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else {                                      // reset timer, repeat cycle
                    LEDcycletime.reset();
                }
            } else {          // gold not found
                if ((LEDcycletime.milliseconds() < 5 * blinktime)) {
                    Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
                } else if ((LEDcycletime.milliseconds() < 12 * blinktime)) {                                       // back to team color
                    Cosmo.LEDDriver.setPattern(teamColor);
                } else {                                      // reset timer, repeat cycle
                    LEDcycletime.reset();
                }
            }
            /**   End of  LED Light signalling  **/

            /** End of Signal the position of the gold mineral  here **/


            telemetry.update();
        }

        // Wait for the game to start (driver presses PLAY) replaced by init loop
        //       waitForStart();

        /**************************************************************
         // Actual RUN instructions
         *************************************************************/
//        while (opModeIsActive() && !isStopRequested() {
        telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.milliseconds());
        telemetry.update();
//
        // First task would be to deploy  here//
//        mecanumDrive(0.5, 100, 0, 0);     // drive forward
//       sleep(200000);

//          goldposition 0 = left,1 = center, 2 = right
        if (goldPosition == 99) {                         // default to the left.
            goldPosition = 0;
        }

        if (goldPosition == 0) {        // left position

            mecanumDrive(0.5, driveDis1, 0, 0);     // drive forward
            mecanumDrive(0.5, driveDis2, 0, 90);    // drive left
            mecanumDrive(0.5, driveDis3, 0, 0);     // drive forward

            mecanumDrive(0.5, -driveDis3, 0, 0);     // drive backwards

        }

        if (goldPosition == 1) {

            mecanumDrive(0.5, driveDis1, 0, 0);     // drive forward
            mecanumDrive(0.5, driveDis3, 0, 0);     // drive forward

            mecanumDrive(0.5, -driveDis3, 0, 0);     // drive backwards
            mecanumDrive(0.5, driveDis2, 0, 90);      // drive left 1x

        }

        if (goldPosition == 2) {

            mecanumDrive(0.5, driveDis1, 0, 0);     // drive forward
            mecanumDrive(0.5, driveDis2, 0, -90);    // drive right
            mecanumDrive(0.5, driveDis3, 0, 0);     // drive forward

            mecanumDrive(0.5, -driveDis3, 0, 0);     // drive backwards
            mecanumDrive(0.5, 2*driveDis2, 0, 90);      // drive left 2x
        }

        // drive towards the wall (all modes)
        mecanumDrive(0.8,driveDis4,0,90);      // drive towards wall
        mecanumTurn(0.8, -43);
        mecanumDrive(0.5,4,-45,90);
        mecanumDrive(0.6, driveDis5, -45, 0);  //drive towards base
        //Unclamp Team Marker
        Cosmo.flagServo.setPosition(0.9);
        sleep(2000);
        mecanumDrive(0.6, -driveDis6, -45, 0); //drive back to crater
        //Reclamp Team Marker
        Cosmo.flagServo.setPosition(0.315);


        // drive forward or backward based on crater starting position

        if (craterPosition){            // crater side drive

        }else {                         // base side drive

        }
        Cosmo.leftFront.setPower(0);
        Cosmo.rightFront.setPower(0);
        Cosmo.leftRear.setPower(0);
        Cosmo.rightRear.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(20000);
        //       }
    }

    //  Drive routine using the IMU and Mecanum wheels
    //  Robot Orientation is to the field
    //  Drive direction is from the robot

    public void mecanumDrive(double speed, double distance, double robot_orientation, double drive_direction) {
        double max;
        double multiplier;
        int right_start;
        int left_start;
        int moveCounts;
        //int drive_direction = -90;
        moveCounts = (int) (distance * Cosmo.COUNTS_PER_INCH);
        right_start = Cosmo.rightRear.getCurrentPosition();
        left_start = Cosmo.leftRear.getCurrentPosition();
        double lfpower;
        double lrpower;
        double rfpower;
        double rrpower;

        double lfbase;
        double lrbase;
        double rfbase;
        double rrbase;
        lfbase = signum(distance) * Math.cos(Math.toRadians(drive_direction + 45));
        lrbase = signum(distance) * Math.sin(Math.toRadians(drive_direction + 45));
        rfbase = signum(distance) * Math.sin(Math.toRadians(drive_direction + 45));
        rrbase = signum(distance) * Math.cos(Math.toRadians(drive_direction + 45));
        while (((abs(Cosmo.rightRear.getCurrentPosition() - right_start) + abs(Cosmo.leftRear.getCurrentPosition() - left_start)) / 2 < abs(moveCounts)) && opModeIsActive() /* ENCODERS*/) {//Should we average all four motors?
            //Determine correction
            double correction = robot_orientation - getheading();
            if (correction <= -180) {
                correction += 360;
            } else if (correction >= 180) {                      // correction should be +/- 180 (to the left negative, right positive)
                correction -= 360;
            }
            lrpower = lrbase; //MIGHT BE MORE EFFECIENT TO COMBINE THESE WITHT HE ADJUSTMENT PART AND SET ADJUSTMENT TO ZERO IF NOT NEEDED
            lfpower = lfbase;
            rrpower = rrbase;
            rfpower = rfbase;
            if (abs(correction) > drive_THRESHOLD) {//If you are off
                //Apply power to one side of the robot to turn the robot back to the right heading
                double right_adjustment = Range.clip((drive_COEF * correction / 45), -1, 1);
                lrpower -= right_adjustment;
                lfpower -= right_adjustment;
                rrpower = rrbase + right_adjustment;
                rfpower = rfbase + right_adjustment;

            }//Otherwise you Are at the right orientation

            //Determine largest power being applied in either direction
            max = abs(lfpower);
            if (abs(lrpower) > max) max = abs(lrpower);
            if (abs(rfpower) > max) max = abs(rfpower);
            if (abs(rrpower) > max) max = abs(rrpower);

            multiplier = speed / max; //multiplier to adjust speeds of each wheel so you can have a max power of 1 on atleast 1 wheel

            lfpower *= multiplier;
            lrpower *= multiplier;
            rfpower *= multiplier;
            rrpower *= multiplier;

            Cosmo.leftFront.setPower(lfpower);
            Cosmo.leftRear.setPower(lrpower);
            Cosmo.rightFront.setPower(rfpower);
            Cosmo.rightRear.setPower(rrpower);

//            RobotLog.ii("[GromitIR] ", Double.toString(18.7754*Math.pow(sharpIRSensor.getVoltage(),-1.51)), Integer.toString(left_front.getCurrentPosition()));

        }
        //gromit.driveTrain.stopMotors();
        Cosmo.leftFront.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
    }


    // Turn using the IMU and meccanum drive
    public void mecanumTurn(double speed, double target_heading) {
        if (speed > 1) speed = 1.0;
        //else if(speed <= 0) speed = 0.1;

        double correction = target_heading - getheading();
        if (correction <= -180) {
            correction += 360;   // correction should be +/- 180 (to the left negative, right positive)
        } else if (correction >= 180) {
            correction -= 360;
        }

        while (abs(correction) >= Cosmo.turn_THRESHOLD && opModeIsActive()) { //opmode active?{
            correction = target_heading - getheading();
            if (abs(correction) <= Cosmo.turn_THRESHOLD) break;

            if (correction <= -180)
                correction += 360;   // correction should be +/- 180 (to the left negative, right positive)
            if (correction >= 180) correction -= 360;
            /**^^^^^^^^^^^MAYBE WE ONLY NEED TO DO THIS ONCE?????*/

            double adjustment = Range.clip((Math.signum(correction) * Cosmo.turn_MIN_SPEED + Cosmo.turn_COEF * correction / 100), -1, 1);  // adjustment is motor power: sign of correction *0.07 (base power)  + a proportional bit

            Cosmo.leftFront.setPower(-adjustment * speed);
            Cosmo.leftRear.setPower(-adjustment * speed);
            Cosmo.rightFront.setPower((adjustment * speed));
            Cosmo.rightRear.setPower((adjustment * speed));
        }
//        gromit.driveTrain.stopMotors();
        Cosmo.leftFront.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
    }


    public float getheading() {
        // Acquiring the angles is relatively expensive; we don't want
        // to do that in each of the three items that need that info, as that's
        // three times the necessary expense.
        angles = Cosmo.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle; //For a -180 to 180 range
        //return (angles.firstAngle + 180 + 180)%360; // for a zero to 360 range
    }

    /*************************************************************************************************\
     |--------------------------------- Eli Edit Method ----------------------------------------------|
     \************************************************************************************************/

    public void editParameters() {

        String arrow0 = " ";
        String arrow1 = " ";
        String arrow2 = " ";
        String arrow3 = " ";
        String arrow4 = " ";
        String arrow5 = " ";
        String arrow6 = " ";
        String arrow7 = " ";
        String arrow8 = " ";
        String arrow9 = " ";
        String arrow10 = " ";
        String arrow11 = " ";
        String arrow12 = " ";
        String arrow13 = " ";
        boolean dpadPressedUp = false;
        boolean dpadPressedDown = false;
        boolean dpadPressedLeft = false;
        boolean dpadPressedRight = false;
        String[] position = {"base", "crater"};
        int positionIndex = 0;
        if (craterPosition) positionIndex = 1;

        String[] color = {"Blue", "Red"};
        int colorIndex = 0;
        if (teamIsRed) colorIndex = 1;

        String[] botName = {"Real Bot", "TestBot"};
        int botIndex = 0;
        if (testBot) botIndex = 1;

        int currentEdit = 1;

        while (!gamepad1.right_stick_button && !opModeIsActive() && !isStopRequested()) {   // while haven't presse exit button, not in play mode, and not in stop
            telemetry.addLine("===> Press Right Joystick to exit EDIT mode <===");
            // Send telemetry message to signify robot waiting;


            telemetry.addLine("Use Dpad to Navigate & change");
            telemetry.addLine().addData("", currentEdit).addData("current edit number", ' ');
            telemetry.addLine().addData(arrow0, waitTime1).addData("Wait Time", arrow0);
            telemetry.addLine().addData(arrow1, colorIndex).addData(color[colorIndex], arrow1);
            telemetry.addLine().addData(arrow2, positionIndex).addData(position[positionIndex], arrow2);
            telemetry.addLine().addData(arrow3, botIndex).addData(botName[botIndex], arrow3);
            telemetry.addLine().addData(arrow4, driveDis1).addData("Distance 1", arrow4);
            telemetry.addLine().addData(arrow5, driveDis2).addData("Distance 2", arrow5);
            telemetry.addLine().addData(arrow6, driveDis3).addData("Distance 3", arrow6);
            telemetry.addLine().addData(arrow7, driveDis4).addData("Distance 4", arrow7);
            telemetry.addLine().addData(arrow8, driveDis5).addData("Distance 5", arrow8);
            telemetry.addLine().addData(arrow9, driveDis6).addData("Distance 6", arrow9);
            telemetry.addLine().addData(arrow10, driveDis7).addData("Distance 7", arrow10);
            telemetry.addLine().addData(arrow11, driveDis8).addData("Distance 8", arrow11);
            telemetry.addLine().addData(arrow12, driveDis9).addData("Distance 9", arrow12);
            telemetry.addLine().addData(arrow13, driveDis10).addData("Distance 10", arrow13);
//            telemetry.addLine().addData(arrow4, "Color       ");
            telemetry.update();

            if (gamepad1.dpad_down) {
                dpadPressedDown = true;
            } else if (gamepad1.dpad_down == false && dpadPressedDown) {
                dpadPressedDown = false;
                currentEdit += 1;
                if (currentEdit > 13) {
                    currentEdit = 0;
                }
            }

            if (gamepad1.dpad_up) {
                dpadPressedUp = true;
            } else if (gamepad1.dpad_up == false && dpadPressedUp) {
                dpadPressedUp = false;
                currentEdit -= 1;
                if (currentEdit < 0) {
                    currentEdit = 13;
                }
            }


            if (currentEdit == 0) {
                arrow0 = "<>";
            } else {
                arrow0 = "    ";
            }
            if (currentEdit == 1) {
                arrow1 = "<>";
            } else {
                arrow1 = "    ";
            }
            if (currentEdit == 2) {
                arrow2 = "<>";
            } else {
                arrow2 = "    ";
            }
            if (currentEdit == 3) {
                arrow3 = "<>";
            } else {
                arrow3 = "    ";
            }
            if (currentEdit == 4) {
                arrow4 = "<>";
            } else {
                arrow4 = "    ";
            }
            if (currentEdit == 5) {
                arrow5 = "<>";
            } else {
                arrow5 = "    ";
            }
            if (currentEdit == 6) {
                arrow6 = "<>";
            } else {
                arrow6 = "    ";
            }
            if (currentEdit == 7) {
                arrow7 = "<>";
            } else {
                arrow7 = "    ";
            }
            if (currentEdit == 8) {
                arrow8 = "<>";
            } else {
                arrow8 = "    ";
            }
            if (currentEdit == 9) {
                arrow9 = "<>";
            } else {
                arrow9 = "    ";
            }
            if (currentEdit == 10) {
                arrow10 = "<>";
            } else {
                arrow10 = "    ";
            }
            if (currentEdit == 11) {
                arrow11 = "<>";
            } else {
                arrow11 = "    ";
            }
            if (currentEdit == 12) {
                arrow12 = "<>";
            } else {
                arrow12 = "    ";
            }
            if (currentEdit == 13) {
                arrow13 = "<>";
            } else {
                arrow13 = "    ";
            }


            if (gamepad1.dpad_left) {
                dpadPressedLeft = true;
            } else if (gamepad1.dpad_left == false && dpadPressedLeft) {
                dpadPressedLeft = false;
                if (currentEdit == 0) {
                    waitTime1 -= 1;
                }
                if (currentEdit == 1) {
                    if (colorIndex == 1) {
                        colorIndex = 0;
                        teamIsRed = false;
                        Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_BLUE);
                    } else {
                        colorIndex = 1;
                        teamIsRed = true;
                        Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_RED);
                    }
                }
                if (currentEdit == 2) {
                    if (positionIndex == 1) {
                        positionIndex = 0;
                        craterPosition = false;
                    } else {
                        positionIndex = 1;
                        craterPosition = true;
                    }
                }
                if (currentEdit == 3) {
                    if (botIndex == 1) {
                        botIndex = 0;
                        testBot = false;
                    } else {
                        botIndex = 1;
                        testBot = true;
                    }
                }
                if (currentEdit == 4) {
                    driveDis1 -= 1;
                }
                if (currentEdit == 5) {
                    driveDis2 -= 1;
                }
                if (currentEdit == 6) {
                    driveDis3 -= 1;
                }
                if (currentEdit == 7) {
                    driveDis4 -= 1;
                }
                if (currentEdit == 8) {
                    driveDis5 -= 1;
                }
                if (currentEdit == 9) {
                    driveDis6 -= 1;
                }
                if (currentEdit == 10) {
                    driveDis7 -= 1;
                }
                if (currentEdit == 11) {
                    driveDis8 -= 1;
                }
                if (currentEdit == 12) {
                    driveDis9 -= 1;
                }
                if (currentEdit == 13) {
                    driveDis10 -= 1;
                }

            }

            if (gamepad1.dpad_right) {
                dpadPressedRight = true;
            } else if (gamepad1.dpad_right == false && dpadPressedRight) {
                dpadPressedRight = false;
                if (currentEdit == 0) {
                    waitTime1 += 1;
                }
                if (currentEdit == 1) {
                    if (colorIndex == 1) {
                        colorIndex = 0;
                        teamIsRed = false;
                        Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_BLUE);
                    } else {
                        colorIndex = 1;
                        teamIsRed = true;
                        Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_RED);
                    }
                }
                if (currentEdit == 2) {
                    if (positionIndex == 1) {
                        positionIndex = 0;
                        craterPosition = false;
                    } else {
                        positionIndex = 1;
                        craterPosition = true;
                    }
                }
                if (currentEdit == 3) {
                    if (botIndex == 1) {
                        botIndex = 0;
                        testBot = false;
                    } else {
                        botIndex = 1;
                        testBot = true;
                    }
                }
                if (currentEdit == 4) {
                    driveDis1 += 1;
                }
                if (currentEdit == 5) {
                    driveDis2 += 1;
                }
                if (currentEdit == 6) {
                    driveDis3 += 1;
                }
                if (currentEdit == 7) {
                    driveDis4 += 1;
                }
                if (currentEdit == 8) {
                    driveDis5 += 1;
                }
                if (currentEdit == 9) {
                    driveDis6 += 1;
                }
                if (currentEdit == 10) {
                    driveDis7 += 1;
                }
                if (currentEdit == 11) {
                    driveDis8 += 1;
                }
                if (currentEdit == 12) {
                    driveDis9 += 1;
                }
                if (currentEdit == 13) {
                    driveDis10 += 1;
                }

            }
//                if (gamepad1.y) {
//                    break;
//                }
        }
        telemetry.update();
    }


}





