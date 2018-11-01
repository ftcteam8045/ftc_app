/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareK9bot
{
    /* Public OpMode members. */
    public DcMotor  leftFront   = null;
    public DcMotor  rightFront  = null;
    public DcMotor  leftRear    = null;
    public DcMotor  rightRear   = null;
    //    public Servo    arm         = null;
//    public Servo    claw        = null;

    public final static double ARM_HOME = 0.2;
    public final static double CLAW_HOME = 0.2;
    public final static double ARM_MIN_RANGE  = 0.20;
    public final static double ARM_MAX_RANGE  = 0.90;
    public final static double CLAW_MIN_RANGE  = 0.20;
    public final static double CLAW_MAX_RANGE  = 0.7;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareK9bot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFront  = hwMap.get(DcMotor.class, "left_front");
        rightFront = hwMap.get(DcMotor.class, "right_front");
        leftRear  = hwMap.get(DcMotor.class, "left_rear");
        rightRear = hwMap.get(DcMotor.class, "right_rear");
 /**       leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
*/
//this is for Neverest
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Define and initialize ALL installed servos.
//        arm  = hwMap.get(Servo.class, "arm");
//        claw = hwMap.get(Servo.class, "claw");
//        arm.setPosition(ARM_HOME);
//        claw.setPosition(CLAW_HOME);
    }

    @TeleOp(name="Eli Test Bot", group="Pushbot")
    //@Disabled


    public static class elijahTeleCode extends LinearOpMode {

        // Set all status flags to false
        public boolean dpadup = false;
        public boolean dpaddown = false;
        public boolean dpadleft = false;
        public boolean dpadright = false;
        public boolean button_a = false;
        public boolean button_b = false;
        public boolean button_x = false;
        public boolean button_y = false;
        public boolean start = false;
        public boolean backbutton = false;
        public boolean righttrigger = false;
        public boolean lefttrigger = false;
        public boolean rightbumper = false;
        public boolean leftbumper = false;
        public boolean rightstickx = false;
        public boolean rightsticky = false;
        public boolean leftstickx = false;
        public boolean leftsticky = false;
        public boolean rightstickbutton = false;
        public boolean leftstickbutton = false;

        public int currentEdit = 0;
        public int driveDis1 = 10;
        public int driveDis2 = 15;
        public int driveDis3 = 20;
        public boolean dpadPressedUp = false;
        public boolean dpadPressedDown = false;
        public boolean dpadPressedLeft = false;
        public boolean dpadPressedRight = false;
        public String arrow1 = " ";
        public String arrow2 = " ";
        public String arrow3 = " ";

        public DcMotor leftFront   = null;
        public DcMotor  rightFront  = null;
        public DcMotor  leftRear    = null;
        public DcMotor  rightRear   = null;


        @Override
        public void runOpMode() {



            /* Initialize the hardware variables.
             * The init() method of the hardware class does all the work here
             */
            //robot.init(hardwareMap);

            // Send telemetry message to signify robot waiting;
            telemetry.addData("To Start Test", "Press Play");    //
            telemetry.update();

            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                telemetry.addLine("*** Controller Test ****");

                // show status flag and current value
    //            telemetry.addLine().addData("", dpadup).addData("Dpad Up    ", gamepad1.dpad_up);
    //            telemetry.addLine().addData("", dpaddown).addData("Dpad Down  ", gamepad1.dpad_down);
    //            telemetry.addLine().addData("", dpadleft).addData("Dpad Left  ", gamepad1.dpad_left);
    //            telemetry.addLine().addData("", dpadright).addData("Dpad Right  ", gamepad1.dpad_right);
    //            telemetry.addLine().addData("", button_a).addData("Button A  ", gamepad1.a);
    //            telemetry.addLine().addData("", button_b).addData("Button B  ", gamepad1.b);
    //            telemetry.addLine().addData("", button_x).addData("Button X  ", gamepad1.x);
    //            telemetry.addLine().addData("", button_y).addData("Button Y  ", gamepad1.y);
    //            telemetry.addLine().addData("", start).addData("Start  ", gamepad1.start);
    //            telemetry.addLine().addData("", backbutton ).addData("Back  ", gamepad1.back);
    //            telemetry.addLine().addData("", righttrigger ).addData(" Right Trigger  ", gamepad1.right_trigger);
    //            telemetry.addLine().addData("", lefttrigger ).addData(" Left Trigger  ", gamepad1.left_trigger);
    //            telemetry.addLine().addData("", rightbumper ).addData(" Right Bumper  ", gamepad1.right_bumper);
    //            telemetry.addLine().addData("", leftbumper ).addData(" Left Bumper  ", gamepad1.left_bumper);
    //            telemetry.addLine().addData("", rightstickx ).addData(" Right Stick X  ", gamepad1.right_stick_x);
    //            telemetry.addLine().addData("", rightsticky ).addData(" Right Stick Y  ", gamepad1.right_stick_y);
    //            telemetry.addLine().addData("", leftstickx ).addData(" Left Stick X  ", gamepad1.left_stick_x);
    //            telemetry.addLine().addData("", leftsticky ).addData(" Left Stick Y  ", gamepad1.left_stick_y);

    //            telemetry.addLine().addData("", leftstickbutton ).addData(" Left Stick Button  ", gamepad1.left_stick_button);
    //            telemetry.addLine().addData("", rightstickbutton ).addData(" Right Stick Button  ", gamepad1.right_stick_button);

                telemetry.addLine("VVV Eli's Test VVV");
                telemetry.addLine().addData(arrow1, driveDis1).addData("Drive Distance One", arrow1);
                telemetry.addLine().addData(arrow2, driveDis2).addData("Drive Distance One", arrow2);
                telemetry.addLine().addData(arrow3, driveDis3).addData("Drive Distance One", arrow3);
                telemetry.addLine().addData("", currentEdit).addData("current edit number test", ' ');


                // set status flag true if pressed
                if (gamepad1.dpad_up) dpadup = true;
                if (gamepad1.dpad_down) dpaddown = true;
                if (gamepad1.dpad_left) dpadleft = true;
                if (gamepad1.dpad_right) dpadright = true;
                if (gamepad1.a) button_a = true;
                if (gamepad1.b) button_b = true;
                if (gamepad1.x) button_x = true;
                if (gamepad1.y) button_y = true;
                if (gamepad1.start) start = true;
                if (gamepad1.back) backbutton = true;
                if (gamepad1.right_trigger > 0.5 ) righttrigger = true;
                if (gamepad1.left_trigger > 0.5) lefttrigger = true;
                if (gamepad1.right_bumper) rightbumper = true;
                if (gamepad1.left_bumper) leftbumper = true;
                if (gamepad1.right_stick_x > 0.5) rightstickx = true;
                if (gamepad1.right_stick_y > 0.5) rightsticky = true;
                if (gamepad1.left_stick_x > 0.5) leftstickx = true;
                if (gamepad1.left_stick_y > 0.5) leftsticky = true;
                if (gamepad1.left_stick_button) leftstickbutton = true;
                if (gamepad1.right_stick_button) rightstickbutton = true;



                //Variable Change GUI

                if (gamepad1.dpad_down) {
                    dpadPressedDown = true;
                }
                else if (gamepad1.dpad_down == false && dpadPressedDown) {
                    dpadPressedDown = false;
                    currentEdit += 1;
                    if (currentEdit > 2) {
                        currentEdit = 0;
                    }
                }

                if (gamepad1.dpad_up) {
                    dpadPressedUp = true;
                }
                else if (gamepad1.dpad_up == false && dpadPressedUp) {
                    dpadPressedUp = false;
                    currentEdit -= 1;
                    if (currentEdit < 0) {
                        currentEdit = 2;
                    }
                }


                if (currentEdit == 0) {
                    arrow1 = "<>";
                }
                else {
                    arrow1 = "    ";
                }
                if (currentEdit == 1) {
                    arrow2 = "<>";
                }
                else {
                    arrow2 = "    ";
                }
                if (currentEdit == 2) {
                    arrow3 = "<>";
                }
                else {
                    arrow3 = "    ";
                }


                if (gamepad1.dpad_left) {
                    dpadPressedLeft = true;
                }
                else if (gamepad1.dpad_left == false && dpadPressedLeft) {
                    dpadPressedLeft = false;
                    if (currentEdit == 0) {
                        driveDis1 -= 1;
                    }
                    if (currentEdit == 1) {
                        driveDis2 -= 1;
                    }
                    if (currentEdit == 2) {
                    driveDis3 -= 1;
                    }
                }

                if (gamepad1.dpad_right) {
                    dpadPressedRight = true;
                }
                else if (gamepad1.dpad_right == false && dpadPressedRight) {
                    dpadPressedRight = false;
                    if (currentEdit == 0) {
                        driveDis1 += 1;
                    }
                    if (currentEdit == 1) {
                        driveDis2 += 1;
                    }
                    if (currentEdit == 2) {
                        driveDis3 += 1;
                    }
                }

                //----------------//


                //Drive Train



                //----------------//

    /**    controller buttons I can think of
     *      gamepad1.dpad_left
     gamepad1.dpad_right
     gamepad1.a
     gamepad1.b
     gamepad1.x
     gamepad1.y
     gamepad1.back
     gamepad1.start
     gamepad1.right_stick_button
     gamepad1.left_stick_button
     gamepad1.right_bumper
     gamepad1.left_bumper
     gamepad1.right_trigger
     gamepad1.left_trigger
     gamepad1.right_stick_x
     gamepad1.right_stick_y
     gamepad1.left_stick_x
     gamepad1.left_stick_y   */


                // test to see if all have been set
                if (    (dpadup) &&
                        (dpaddown) &&
                        (button_a) &&
                        (button_b) &&
                        (button_x) &&
                        (button_y) &&
                        (start) &&
                        (backbutton) &&
                        (righttrigger) &&
                        (lefttrigger) &&
                        (rightbumper) &&
                        (leftbumper) &&
                        (rightstickx) &&
                        (rightsticky) &&
                        (leftstickx) &&
                        (leftsticky) &&
                        (leftstickbutton) &&
                        (rightstickbutton)
                        )
                {

                    telemetry.addLine("****** All TESTS COMPLETE*******");
                }
                telemetry.update();



            }
        }
    }
}
