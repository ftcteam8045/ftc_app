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

            //----------------//

            forward = -gamepad1.right_stick_y;
            strafe = -gamepad1.right_stick_x;
            rotate = gamepad1.left_stick_x;

            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftRear.setPower(0);
            robot.rightRear.setPower(0);
//            robot.lift.setPower(0);

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
            //Lift
//            if(gamepad1.left_trigger <= 0.5) {
//                robot.lift.setPower(gamepad1.left_trigger);
//            }
//            if(gamepad1.right_trigger <= 0.5) {
//                robot.lift.setPower(gamepad1.right_trigger);
//            }

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