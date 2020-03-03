package org.firstinspires.ftc.teamcode.Skystone;


import android.graphics.Color;

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
import org.firstinspires.ftc.teamcode.oldcode.AutoTransitioner;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_COEF;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_THRESHOLD;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.turn_MIN_SPEED;
//Lara + Liesel positioning code


@Autonomous(name = "MainAuto2019", group = "Cosmo")
@Disabled
public class AutoLL2019 extends LinearOpMode {

    /* Declare OpMode members. */
//    Hardware8045testbot Cosmo = new Hardware8045testbot();   // Use a Pushbot's hardware
    Hardware2019 Cosmo = new Hardware2019();   // Use a Pushbot's hardware

    /**
     * Menu Parameter Initialization
     **/
    public int waitTime1 = 0;
    public int driveDis1 = 16;
    public int driveDis2 = 22;
    public int driveDis3 = 10; //forward+backward
    public int driveDis4 = 30;

    public boolean loadingPosition = false;
    public boolean buildingPosition = false;

    // State used for updating telemetry
    public Orientation angles;
    public Acceleration gravity;


    @Override
    public void runOpMode() {

        final double FORWARD_SPEED = 0.3;
        final double TURN_SPEED = 0.3;
        final int cycletime = 500;
        int goldPosition = 2;   // 0 is on left, 1 in center, 2 on right


        /*
         * Initialize the drive system variables.the Robot
         * The init() method of the hardware class does all the work here
         */
        Cosmo.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        /**************************************************************
         // Actual Init loop
         *************************************************************/
        while (!opModeIsActive() && !isStopRequested()) {

            /**   End of  LED Light signalling  **/

            /** End of Signal the position of the gold mineral  here **/


            telemetry.update();
        }
        /**************************************************************
         // End Init loop
         *************************************************************/
        // Wait for the game to start (driver presses PLAY) replaced by init loop
        //       waitForStart();



        /**************************************************************
         // Actual RUN instructions
         *************************************************************/



        if (loadingPosition){            /** loading zone drive  **/

        /** scans for skystone first **/
            mecanumDrive(0.5, driveDis3, 0, 0); //drive forward
            sleep(200);
            mecanumTurn(0.5, -90); // turn left
            sleep(200);
            mecanumDrive(2.5, driveDis4, 0, 0); // drive forward
            sleep(200);
            /** place down block? **/
            mecanumDrive(0.5, -driveDis3, 0, 0); //drive backward



            mecanumDrive(0.5, driveDis1, 0, 0);     // drive forward
            sleep(200);
            mecanumDrive(0.5, driveDis2, 0, 90);    // drive left
            mecanumDrive(0.3, driveDis3, 0, 0);     // drive forward
            sleep(200);
            mecanumDrive(0.3, -driveDis3, 0, 0);     // drive backwards

        }


        if (buildingPosition){             /** building zone drive  **/

            mecanumDrive(0.5, driveDis1,0, 0);       // drive forward
            sleep(200);
            mecanumDrive(.5, -driveDis1,0,0);        // drive backwards
            sleep(200);





            mecanumDrive(0.5, driveDis1, 0, 0);     // drive forward
            sleep(200);
            mecanumDrive(0.5, driveDis2, 0, 90);    // drive left
            mecanumDrive(0.3, driveDis3, 0, 0);     // drive forward
            sleep(200);
            mecanumDrive(0.3, -driveDis3, 0, 0);     // drive backwards

        }

        Cosmo.leftFront.setPower(0);
        Cosmo.rightFront.setPower(0);
        Cosmo.leftRear.setPower(0);
        Cosmo.rightRear.setPower(0);

    }
    /**************************************************************
     // End Actual Program Run
     *************************************************************/

    //  Drive routine using the IMU and Mecanum wheels
    //  Robot Orientation is to the field
    //  Drive direction is from the robot
    //

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
        while (((abs(Cosmo.rightRear.getCurrentPosition() - right_start) + abs(Cosmo.leftRear.getCurrentPosition() - left_start)) / 2 < abs(moveCounts)) && opModeIsActive()  /* ENCODERS*/) {//Should we average all four motors?
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
            /*^^^^^^^^^^^MAYBE WE ONLY NEED TO DO THIS ONCE?????*/

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
//
//    public void editParameters() {
//
//        String arrow01 = " ";
//        String arrow0 = " ";
//        String arrow1 = " ";
//        String arrow2 = " ";
//        String arrow3 = " ";
//        String arrow4 = " ";
//        String arrow5 = " ";
//        String arrow6 = " ";
//        String arrow7 = " ";
//        String arrow8 = " ";
//        String arrow9 = " ";
//        String arrow10 = " ";
//        String arrow11 = " ";
//        String arrow12 = " ";
//        String arrow13 = " ";
//        boolean dpadPressedUp = false;
//        boolean dpadPressedDown = false;
//        boolean dpadPressedLeft = false;
//        boolean dpadPressedRight = false;
//        String[] position = {"base", "crater"};
//        int positionIndex = 0;
//        if (craterPosition) positionIndex = 1;
//
//        String[] color = {"Blue", "Red"};
//        int colorIndex = 0;
//        if (teamIsRed) colorIndex = 1;
//
//        String[] botName = {"Real Bot", "TestBot"};
//        int botIndex = 0;
//        if (testBot) botIndex = 1;
//
//        int currentEdit = 1;
//
//        while (!gamepad1.right_stick_button && !opModeIsActive() && !isStopRequested()) {   // while haven't presse exit button, not in play mode, and not in stop
//            telemetry.addLine("===> Press Right Joystick to exit EDIT mode <===");
//            // Send telemetry message to signify robot waiting;
//
//
//            telemetry.addLine("Use Dpad to Navigate & change");
//            telemetry.addLine().addData("", currentEdit).addData("current edit number", ' ');
//            telemetry.addLine().addData(arrow2, positionIndex).addData(position[positionIndex], arrow2);
//            telemetry.addLine().addData(arrow3, botIndex).addData(botName[botIndex], arrow3);
//            telemetry.addLine().addData(arrow4, driveDis1).addData("First drive foreward", arrow4);
//            telemetry.addLine().addData(arrow5, driveDis2).addData("Distance 2", arrow5);
//            telemetry.addLine().addData(arrow6, driveDis3).addData("Distance 3", arrow6);
//
////            telemetry.addLine().addData(arrow4, "Color       ");
//            telemetry.update();
//
//            if (gamepad1.dpad_down) {
//                dpadPressedDown = true;
//            } else if (gamepad1.dpad_down == false && dpadPressedDown) {
//                dpadPressedDown = false;
//                currentEdit += 1;
//                if (currentEdit > 13) {
//                    currentEdit = -1;
//                }
//            }
//
//            if (gamepad1.dpad_up) {
//                dpadPressedUp = true;
//            } else if (gamepad1.dpad_up == false && dpadPressedUp) {
//                dpadPressedUp = false;
//                currentEdit -= 1;
//                if (currentEdit < -1) {
//                    currentEdit = 13;
//                }
//            }
//
//            if (currentEdit == -1) {
//                arrow01 = "<>";
//            } else {
//                arrow01 = "    ";
//            }
//            if (currentEdit == 0) {
//                arrow0 = "<>";
//            } else {
//                arrow0 = "    ";
//            }
//            if (currentEdit == 1) {
//                arrow1 = "<>";
//            } else {
//                arrow1 = "    ";
//            }
//
//
//
//            if (gamepad1.dpad_left) {
//                dpadPressedLeft = true;
//            } else if (gamepad1.dpad_left == false && dpadPressedLeft) {
//                dpadPressedLeft = false;
//
//
//                if (currentEdit == 0) {
//                    if (positionIndex == 1) {
//                        positionIndex = 0;
//                        craterPosition = false;
//                    } else {
//                        positionIndex = 1;
//                        craterPosition = true;
//                    }
//                }
//                if (currentEdit == 1) {
//                    if (botIndex == 1) {
//                        botIndex = 0;
//                        otherPosition = false;
//                    } else {
//                        botIndex = 1;
//                        otherPosition = true;
//                    }
//                }
//                if (currentEdit == 4) {
//                    driveDis1 -= 1;
//                }
//                if (currentEdit == 5) {
//                    driveDis2 -= 1;
//                }
//                if (currentEdit == 6) {
//                    driveDis3 -= 1;
//                }
//
//            }
//
//            if (gamepad1.dpad_right) {
//                dpadPressedRight = true;
//            } else if (gamepad1.dpad_right == false && dpadPressedRight) {
//                dpadPressedRight = false;
//
//
//                if (currentEdit == 0) {
//                    if (positionIndex == 1) {
//                        positionIndex = 0;
//                        craterPosition = false;
//                    } else {
//                        positionIndex = 1;
//                        craterPosition = true;
//                    }
//                }
//                if (currentEdit == 1) {
//                    if (botIndex == 1) {
//                        botIndex = 0;
//                        otherPosition = false;
//                    } else {
//                        botIndex = 1;
//                        otherPosition = true;
//                    }
//                }
//                if (currentEdit == 4) {
//                    driveDis1 += 1;
//                }
//                if (currentEdit == 5) {
//                    driveDis2 += 1;
//                }
//                if (currentEdit == 6) {
//                    driveDis3 += 1;
//                }
//
//            }
////                if (gamepad1.y) {
////                    break;
////                }
//        }
//        telemetry.update();
//    }

}






