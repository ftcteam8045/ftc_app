package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.oldcode.DriveTrain;

import static java.lang.Math.abs;

@TeleOp(name="Eli Tele Bot", group="Pushbot")
//@Disabled


public class elijahTeleCode extends LinearOpMode {

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

    public float forward;
    public float strafe;
    public float rotate;

    public int currentEdit = 0;
    public int driveDis1 = 10;
    public int driveDis2 = 15;
    public int driveDis3 = 20;
    public boolean dpadPressedUp = false;
    public boolean dpadPressedDown = false;
    public boolean dpadPressedLeft = false;
    public boolean dpadPressedRight = false;
    public int colorRed = 1;
    public String[] teamColor = {"Blue", "Red"};
    public String arrow1 = " ";
    public String arrow2 = " ";
    public String arrow3 = " ";
    public String arrow4 = " ";


    Hardware8045         robot   = new Hardware8045();   // Use a Pushbot's hardware



    public boolean frontIsForward = true;
    private DriveTrain.SpeedSetting speedMode;

    @Override
    public void runOpMode() {



        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

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

            // --- Eli Test Code -- \\
            telemetry.addLine("VVV Eli's Test VVV");
            telemetry.addLine().addData(arrow1, driveDis1).addData("Drive Distance One", arrow1);
            telemetry.addLine().addData(arrow2, driveDis2).addData("Drive Distance One", arrow2);
            telemetry.addLine().addData(arrow3, driveDis3).addData("Drive Distance One", arrow3);
            telemetry.addLine().addData(arrow4, "Team Color:       ").addData(teamColor[colorRed], arrow4);
            telemetry.addLine().addData("", currentEdit).addData("current edit number test", ' ');
            // -------------------- \\


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
                if (currentEdit > 3) {
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
            if (currentEdit == 3) {
                arrow4 = "<>";
            }
            else {
                arrow4 = "    ";
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
                if (currentEdit == 3) {
                    if (colorRed == 1) {
                        colorRed = 0;
                    } else {
                        colorRed = 1;
                    }
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
                if (currentEdit == 3) {
                    if (colorRed == 1) {
                        colorRed = 0;
                    } else {
                        colorRed = 1;
                    }
                }
            }
            //----------------//

            forward = -gamepad1.right_stick_y;
            strafe = -gamepad1.right_stick_x;
            rotate = gamepad1.left_stick_x;

//            gamepad1.right_stick_x
//            gamepad1.right_stick_y
//            gamepad1.left_stick_x
//            gamepad1.left_stick_y

            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftRear.setPower(0);
            robot.rightRear.setPower(0);

            if(gamepad1.left_stick_y >= 0.5) {
                robot.leftFront.setPower(gamepad1.left_stick_y);
                robot.rightFront.setPower(gamepad1.left_stick_y);
                robot.leftRear.setPower(gamepad1.left_stick_y);
                robot.rightRear.setPower(gamepad1.left_stick_y);
            }
            if(gamepad1.left_stick_y <= -0.5) {
                robot.leftFront.setPower(gamepad1.left_stick_y);
                robot.rightFront.setPower(gamepad1.left_stick_y);
                robot.leftRear.setPower(gamepad1.left_stick_y);
                robot.rightRear.setPower(gamepad1.left_stick_y);
            }
            if(gamepad1.left_stick_x >= 0.5) {
                robot.leftFront.setPower(-gamepad1.left_stick_x);
                robot.rightFront.setPower(gamepad1.left_stick_x);
                robot.leftRear.setPower(gamepad1.left_stick_x);
                robot.rightRear.setPower(-gamepad1.left_stick_x);
            }
            if(gamepad1.left_stick_x <= -0.5) {
                robot.leftFront.setPower(-gamepad1.left_stick_x);
                robot.rightFront.setPower(gamepad1.left_stick_x);
                robot.leftRear.setPower(gamepad1.left_stick_x);
                robot.rightRear.setPower(-gamepad1.left_stick_x);
            }

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