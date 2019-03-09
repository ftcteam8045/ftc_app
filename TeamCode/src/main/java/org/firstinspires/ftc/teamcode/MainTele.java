package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


@TeleOp(name = "MainTele", group = "8045")  // @Autonomous(...) is the other common choice
//@Disabled
public class MainTele extends OpMode {

    Hardware8045 Cosmo;

    // variables used during the configuration process

    private ElapsedTime runtime = new ElapsedTime();
    double timeLeft;

    public boolean teamIsRed = true;
    public RevBlinkinLedDriver.BlinkinPattern teamColor = RevBlinkinLedDriver.BlinkinPattern.RED;
//    public RevBlinkinLedDriver.BlinkinPattern teamColor = RevBlinkinLedDriver.BlinkinPattern.BLUE;

//    double turnDirection;
    public double topSpeed = 0.5 ;
    public boolean reverseDrive = false;
    //Booleans

    public boolean aIsReleased = true;

    //Back mode
    public boolean frontIsForward = false;
    public boolean rightbtnIsReleased = true;

    //Drive type
    public double driveType = 0;
    public String driveMode = "Normal";
    public boolean run = false;

    public double armUp1 = 1150;
    public double armUp2 = 750;
    public double armUp3 = 880;


    public double dump = 0.7;
    public double transport = 0.4;

    public double grayHueValue = 120.0;
    public double redHueValue  =  5;
    public double blueHueValue = 189;
    public double grayRedBorder  = (grayHueValue + redHueValue  ) / 2;
    public double grayBlueBorder = (grayHueValue + blueHueValue ) / 2;
    // hsvValues is an array that will hold the hue, saturation, and value information.
    public float hsvValues[] = {0F, 0F, 0F};
    // values is a reference to the hsvValues array.
    public float values[] = hsvValues;

    public boolean liftMovingUp = false;
    public boolean extendArmOutToScore = false;
    public boolean extendArmOutToScore2 = false;
    public boolean armMovingDown = false;
    public boolean armMovingIn = false;
    public boolean retractNow = false;
    public boolean oneHit = false;
    public boolean armMiddle = false;
    public boolean clearWall = false;
    public boolean finishRetracting = false;
    public boolean moveArmUpToScore1 = false;
    public boolean moveArmUpToScore2 = false;
    public boolean moveArmUpToScore3 = false;
    public boolean moveBox = false;




    public int dumpLength = 3154;
    public int moveLength1 = -1700;
    public int moveLength2 = -380;
    public int moveLength3 = -2200;
    public int moveLength4 = -6100;
    public int justAboveWallHeight = 2600;


    @Override
    public void init() {
        Cosmo = new Hardware8045();
        Cosmo.init(hardwareMap);
        Cosmo.sensorColor.enableLed(true);

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        timeLeft = 120;

        Cosmo.LEDDriver.setPattern(teamColor);

        telemetry.addData("Finished", "Initializing");
        telemetry.update();


    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        timeLeft = 120 - runtime.seconds();

//        // BACK mode

        if (gamepad1.right_stick_button) {
            if (rightbtnIsReleased) {
                rightbtnIsReleased = false;
                frontIsForward = !frontIsForward;
            }
        } else {
            rightbtnIsReleased = true;
        }

        if (gamepad1.left_stick_button) {
            if (gamepad1.dpad_up) {
                driveType = 0;
            } else if (gamepad1.dpad_left) {
                driveType = 1;
            } else if (gamepad1.dpad_right) {
                driveType = 2;
            }
        }

        /**  set lights color  **/

        Color.RGBToHSV((int)(Cosmo.sensorColor.red() * 255), (int)(Cosmo.sensorColor.green() * 255), (int)(Cosmo.sensorColor.blue() * 255), hsvValues);
        if (hsvValues[0] > grayRedBorder && hsvValues[0] < grayBlueBorder ) {
            Cosmo.LEDDriver.setPattern(teamColor);
        } else {
            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
        }


        /**  set drive speed  **/

        if (gamepad1.left_bumper) {
            topSpeed = 1.0;
        } else if (gamepad1.left_trigger > 0.1) {
            topSpeed = 0.3;
        }
        else {
            topSpeed = 0.6;
        }
//        /** set driving colors **/
//        if (topSpeed == 0.4) {
//            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_BLUE);
//        } else {
//            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
//        }

        /** DRIVE  HERE   */
        if (driveType == 0) {
            drivesmart(-gamepad1.right_stick_x, -gamepad1.right_stick_y, gamepad1.left_stick_x);
            driveMode = "Normal";
        } else if(driveType == 1) {
            drivesmart(-gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            driveMode = "Gamer";
        } else {
            drivesmart(-gamepad1.left_stick_x, -gamepad1.right_stick_y, gamepad1.right_stick_x);
            driveMode = "South Paw";
        }



        /** Vertical Arm Controls for Controller 2 **/

        int armSlowSpeedPos = 1400;

        if(oneHit == false) {
            if (gamepad2.left_stick_y > 0.01 || gamepad2.left_stick_y < 0.01) {
                Cosmo.armmotor.setPower(gamepad2.left_stick_y * 0.28);
            } else {
                Cosmo.armmotor.setPower(0);
            }
        }
//
//        if (gamepad2.left_trigger > 0.1) {
//            if (Cosmo.armmotor.getCurrentPosition() < armSlowSpeedPos) {
//                Cosmo.armmotor.setPower(0.09);
////                Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
//            }
//
//            if (Cosmo.armmotor.getCurrentPosition() > armSlowSpeedPos) {
//                Cosmo.armmotor.setPower(-0.5);
////                Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
//            }
//        }

        /** Sweeper Motor Controls for Controller 2 **/

        if (gamepad2.right_trigger > 0.1){
            Cosmo.vexMotor.setPower(-0.88);
        }

        if (gamepad2.left_trigger > 0.1){

            Cosmo.vexMotor.setPower(0.88);
// njm
        }

        if (gamepad2.right_bumper || gamepad2.left_bumper){

            Cosmo.vexMotor.setPower(0);

        }


        /** Extension Motor Controls for Controller 2 **/

        int exMax = 4740;

        if(oneHit == false) {
            if (gamepad2.right_stick_y != 0) {
                Cosmo.exmotor.setPower(gamepad2.right_stick_y);
            } else {
                Cosmo.exmotor.setPower(0);
            }
        }


        //extension arm one hit align


        /** arm down */
        if (gamepad2.dpad_down){
            armMiddle = true;
            oneHit = true;
            Cosmo.dumpServo.setPosition(dump);

        }

        if (armMiddle){
            if (Cosmo.armmotor.getCurrentPosition() < armUp2){
                Cosmo.armmotor.setPower(0.8);
            }
            else {
                armMovingIn = true;
                armMiddle = false;
                armMovingDown = true;

            }

        }
        if (armMovingIn){
            if (Cosmo.exmotor.getCurrentPosition() < moveLength3) {
                Cosmo.exmotor.setPower(1);
            } else {
                Cosmo.exmotor.setPower(0);
                armMovingIn = false;
            }

        }

        if (armMovingDown) {
            if (Cosmo.armmotor.getCurrentPosition() < justAboveWallHeight) {
                Cosmo.armmotor.setPower(0.8);
            } else {
                armMovingDown = false;
                oneHit = false;
            }
        }


        /** GO UP TO SCORE  ONE HIT BUTTON **/


        // Press gamepad2.Y
        if (gamepad2.y) {
            retractNow = true;
            oneHit = true;
        }



        // Retract arm to first transport height
            if (retractNow) {
                if (Cosmo.exmotor.getCurrentPosition() < moveLength1) {
                    Cosmo.exmotor.setPower(1);
                } else {
                    Cosmo.exmotor.setPower(0);
                    clearWall = true;
                    retractNow = false;
                }
            }

            // Raise arm to just above wall height
            if (clearWall) {
                if (Cosmo.armmotor.getCurrentPosition() > justAboveWallHeight) {
                    Cosmo.armmotor.setPower(-0.9);
                } else {
                    Cosmo.armmotor.setPower(0);
                    clearWall = false;
                    finishRetracting = true;
                    moveArmUpToScore1 = true;
                }
            }

            // Retract arm to second transport height
            if (finishRetracting) {
                if (Cosmo.exmotor.getCurrentPosition() < moveLength2) {
                    Cosmo.exmotor.setPower(1);
                } else {
                    Cosmo.exmotor.setPower(0);

                    finishRetracting = false;

                }
            }
            if (moveArmUpToScore1) {

                if (Cosmo.armmotor.getCurrentPosition() > armUp1) {

                    Cosmo.armmotor.setPower(-0.5);

                } else {
                    Cosmo.armmotor.setPower(0);
                    moveBox = true;
                    moveArmUpToScore1 = false;

                }

            }
            //rotate collection box to transport orientation
            if (moveBox) {
                Cosmo.dumpServo.setPosition(transport);
                extendArmOutToScore = true;
                moveBox = false;
            }
            if (extendArmOutToScore) {

                if (Cosmo.exmotor.getCurrentPosition() > moveLength3) {
                    Cosmo.exmotor.setPower(-1);

                } else {
                    Cosmo.exmotor.setPower(0);
                    sleep(100);
                    moveArmUpToScore2 = true;
                    extendArmOutToScore = false;

                }
            }
            // Rotate arm to scoring position
            if (moveArmUpToScore2) {

                if (Cosmo.armmotor.getCurrentPosition() > armUp2) {

                    Cosmo.armmotor.setPower(-0.4);

                } else {
                    Cosmo.armmotor.setPower(0);

                    moveArmUpToScore2 = false;
                    extendArmOutToScore2 = true;

                }

            }
            if (extendArmOutToScore2) {

                if (Cosmo.exmotor.getCurrentPosition() > moveLength4) {
                    Cosmo.exmotor.setPower(-1);

                } else {
                    Cosmo.exmotor.setPower(0);
                    extendArmOutToScore2 = false;
                    moveArmUpToScore3 = true;
                    oneHit = false;

                }
            }

//        // Extend arm to scoring position
//        if (moveArmUpToScore3){
//
//            if (Cosmo.armmotor.getCurrentPosition() < armUp3){
//
//                Cosmo.armmotor.setPower(0.6);
//
//            }
//            else {
//                Cosmo.armmotor.setPower(0);
//
//                moveArmUpToScore3 = false;
//
//            }
//
//        }

        if (gamepad1.b){
              liftMovingUp = false;
              extendArmOutToScore = false;
              extendArmOutToScore2 = false;
              armMovingDown = false;
              armMovingIn = false;
              retractNow = false;
              armMiddle = false;
              clearWall = false;
              finishRetracting = false;
              moveArmUpToScore1 = false;
              moveArmUpToScore2 = false;
              moveArmUpToScore3 = false;
              moveBox = false;
        }

        /** Dump Servo Controls for Controller 2 **/


        if (gamepad2.a) {
            Cosmo.dumpServo.setPosition(dump);
        }
        if (gamepad2.x) {
            Cosmo.dumpServo.setPosition(transport);
        }

        /**ONE HIT LIFT HEIGHT**/

//      assume it's zeroed from Auto???  not the best solution
//        int liftStartPos = Cosmo.liftmotor.getCurrentPosition();
        int liftMax = 8250;

        if (gamepad1.right_bumper) {                  //set logical that the lift is moving up.
            liftMovingUp = true;
        }




        /** Lift Controls for Controller 1 **/

        if (gamepad1.dpad_down) {
            Cosmo.liftmotor.setPower(-1.0);
            liftMovingUp = false;

//            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GOLD);
        }else if (gamepad1.dpad_up){                    // don't turn off power if the lift is raising
            Cosmo.liftmotor.setPower(1.0);
            liftMovingUp = false;

        }else if(liftMovingUp && Cosmo.liftmotor.getCurrentPosition() < liftMax){
            Cosmo.liftmotor.setPower(1);
        } else {
            Cosmo.liftmotor.setPower(0);
        }

        if (Cosmo.liftmotor.getCurrentPosition() > liftMax){
            liftMovingUp = false;
        }
                // don't turn off power if the lift is raising


        /**ONE HIT LIFT HEIGHT**/



        telemetry.addLine().addData("Drive Mode", driveMode);
        telemetry.addData("TimeLeft: ",timeLeft);
//        telemetry.addData("Right -X: ", -gamepad1.right_stick_x);
//        telemetry.addData("Right -Y: ", -gamepad1.right_stick_y);
//        telemetry.addData(" Left -X: ", -gamepad1.left_stick_x);
//        telemetry.addData("Right Button Is Released", rightbtnIsReleased);
        telemetry.addData("Front Is Forward", frontIsForward);
        telemetry.addData("LiftCounts", Cosmo.liftmotor.getCurrentPosition());
        telemetry.addData("ArmMotorCounts", Cosmo.armmotor.getCurrentPosition());
//        telemetry.addData("IntakeMotorCounts", Cosmo.intakemotor.getCurrentPosition());
        telemetry.addData("ExMotorCounts", Cosmo.exmotor.getCurrentPosition());



        telemetry.update();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This method puts the current thread to sleep for the given time in msec.
     * It handles InterruptException where it recalculates the remaining time
     * and calls sleep again repeatedly until the specified sleep time has past.
     *
     * @param sleepTime specifies sleep time in msec.
     */
    public static void sleep(long sleepTime) {
        long wakeupTime = System.currentTimeMillis() + sleepTime;

        while (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
            sleepTime = wakeupTime - System.currentTimeMillis();
        }
    }   //sleep


    public void drivesmart(double x, double y, double turn) {

        if (frontIsForward) {             // driving with the front facing forward

        } else {                            // driving with the rear facing forward
            y = -y;
            x = -x;
        }

        double lfpower;
        double lrpower;
        double rfpower;
        double rrpower;

        double rotation = turn;  // knock down the turn power -- NOT ANYMORE

        //Determine largest power being applied in either direction

        lfpower = ( y - x + rotation);
        lrpower = ( y + x + rotation);
        rfpower = ( y + x - rotation);
        rrpower = ( y - x - rotation);

        lfpower = lfpower * topSpeed;
        lrpower = lrpower * topSpeed;
        rfpower = rfpower * topSpeed;
        rrpower = rrpower * topSpeed;

        Cosmo.leftFront.setPower(lfpower);
        Cosmo.leftRear.setPower(lrpower);
        Cosmo.rightFront.setPower(rfpower);
        Cosmo.rightRear.setPower(rrpower);

    }
//


    public void stop() {
        Cosmo.leftFront.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
    }




}
