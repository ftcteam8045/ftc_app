package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "MainTele", group = "8045")  // @Autonomous(...) is the other common choice
//@Disabled
public class MainTeleBlue extends OpMode {

    Hardware8045 Cosmo;

    // variables used during the configuration process

    private ElapsedTime runtime = new ElapsedTime();
    double timeLeft;

    public boolean teamIsRed = true;
//    public RevBlinkinLedDriver.BlinkinPattern teamColor = RevBlinkinLedDriver.BlinkinPattern.RED;
    public RevBlinkinLedDriver.BlinkinPattern teamColor = RevBlinkinLedDriver.BlinkinPattern.BLUE;

//    double turnDirection;
    public double topSpeed = 0.5 ;
    public boolean reverseDrive = false;
    //Booleans

    public boolean aIsReleased = true;

    //Back mode
    public boolean frontIsForward = true;
    public boolean rightbtnIsReleased = true;

    //Drive type
    public double driveType = 0;
    public String driveMode = "Normal";
    public boolean run = false;

    public double armUp = 2100;

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
    public boolean readyToDump = false;
    public boolean dumpNow = false;
    public boolean moveUp = false;
    public boolean readyToObtain = true;
    public boolean tweakingArm = true;


    public int dumpLength = 3154;
    public int moveLength = 1400;



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
            topSpeed = 0.4;
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

        if (gamepad2.left_stick_y > 0.01 || gamepad2.left_stick_y < 0.01) {
            Cosmo.armmotor.setPower(-gamepad2.left_stick_y * 0.5);
        }
        else {
            Cosmo.armmotor.setPower(0);
        }

        if (gamepad2.left_trigger > 0.1) {
            if (Cosmo.armmotor.getCurrentPosition() < armSlowSpeedPos) {
                Cosmo.armmotor.setPower(0.09);
//                Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            }

            if (Cosmo.armmotor.getCurrentPosition() > armSlowSpeedPos) {
                Cosmo.armmotor.setPower(-0.5);
//                Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
            }
        }

        /** Sweeper Motor Controls for Controller 2 **/

        if (gamepad2.right_trigger > 0.1){
//            Cosmo.sweepermotor.setPower(1);
        }

        if (gamepad2.left_bumper){

//            Cosmo.sweepermotor.setPower(-0.8);

        }

        if (gamepad2.right_bumper){

//            Cosmo.sweepermotor.setPower(0);

        }


        /** Extension Motor Controls for Controller 2 **/

        int exMax = 4740;

        if (gamepad2.right_stick_y != 0) {
            Cosmo.exmotor.setPower(-gamepad2.right_stick_y);
        }
        else {
            Cosmo.exmotor.setPower(0);
        }


        //extension arm one hit align


//        if(gamepad2.x && readyToObtain){
//
//            Cosmo.exmotor.setPower(0.18);
//            Cosmo.armmotor.setPower(0.5);
//            sleep(920);
//            Cosmo.armmotor.setPower(0.2);
//            Cosmo.exmotor.setPower(1);
//            sleep(1500);
//            Cosmo.armmotor.setPower(0);
//            Cosmo.exmotor.setPower(0);
//            Cosmo.dumpServo.setPosition(open);
//            readyToObtain = false;
//
//        }



//        if (gamepad2.y) {
//            dumpNow = true;
//
//        }
//
//        if (dumpNow) {
//
//            if (Cosmo.exmotor.getCurrentPosition() > moveLength) {
//                Cosmo.exmotor.setPower(-1);
//            }
//            else {
//                Cosmo.exmotor.setPower(0);
//                readyToDump = true;
//                dumpNow = false;
//            }
//
//
//        }
//
//
//        if (readyToDump){
//
//            if (Cosmo.armmotor.getCurrentPosition() < armUp){
//
//                Cosmo.armmotor.setPower(1);
//
//            }
//            else {
//                Cosmo.armmotor.setPower(0);
//                moveUp = true;
//                readyToDump = false;
//            }
//
//        }
//
//        if (moveUp) {
//
//            while (Cosmo.exmotor.getCurrentPosition() < dumpLength) {
//                Cosmo.exmotor.setPower(1);
//
//            }
//                Cosmo.exmotor.setPower(0);
//                moveUp = false;
//        }



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
        int liftMax = 7800;

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
