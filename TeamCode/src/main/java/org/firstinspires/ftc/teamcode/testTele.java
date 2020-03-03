package org.firstinspires.ftc.teamcode;




        import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.AnalogInput;
        import com.qualcomm.robotcore.hardware.DistanceSensor;
        import com.qualcomm.robotcore.util.ElapsedTime;

        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name = "testTeleRick", group = "8045")  // @Autonomous(...) is the other common choice
@Disabled
public class testTele extends OpMode {

   testHardware Cosmo;



    public double irvoltagetest;
    public double irdistancetest;

    private ElapsedTime runtime = new ElapsedTime();
    double timeLeft;
    // variables used during the configuration process


    //    double turnDirection;
    public double topSpeed = 0.5 ;


    //Back mode
    public boolean frontIsForward = false;
    public boolean rightbtnIsReleased = true;

    //Drive type

    public boolean run = false;

    //    Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorDistance;
    // hsvValues is an array that will hold the hue, saturation, and value information.
    public float hsvValues[] = {0F, 0F, 0F};
    // values is a reference to the hsvValues array.
    public float values[] = hsvValues;






    public double multiplier = 0.1818;



    @Override
    public void init() {
        Cosmo = new testHardware();
        Cosmo.init(hardwareMap);



        telemetry.addData("Status", "Initializing");
        telemetry.update();




        telemetry.addData("Finished", "Initializing");
        telemetry.update();


    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        // y = -15.261x3 + 53.826x2 - 64.398x + 30.353   051 2-15cm sensor on 3V
        // y = -10.538x3 + 50.132x2 - 79.223x + 48.602   041 4-30cm sensor on 3V
        irvoltagetest = Cosmo.sharp.getVoltage();
        irdistancetest = -15.261 * Math.pow(irvoltagetest, 3) + 53.826 * Math.pow(irvoltagetest, 2) - 64.398 * irvoltagetest + 30.353;
        telemetry.addData("", "Sharp volts, Distance(cm): %4.2f  %4.1f", irvoltagetest, irdistancetest);
        double irvoltagetest2 = Cosmo.sharp2.getVoltage();
        double irdistancetest2 = -10.538 * Math.pow(irvoltagetest2, 3) + 50.132  * Math.pow(irvoltagetest2, 2) - 79.223 * irvoltagetest2 + 48.602;
        telemetry.addData("", "Sharp2 volts, Distance(cm): %4.2f  %4.1f", irvoltagetest2, irdistancetest2);
        double irvoltagetest3 = Cosmo.sharp3.getVoltage();
        double irdistancetest3 = -7.411 * Math.pow(irvoltagetest3, 3) + 52.356 * Math.pow(irvoltagetest3, 2) - 125.222 * irvoltagetest3 + 111.659;
        telemetry.addData("", "Sharp3 volts, Distance(cm): %4.2f  %4.1f", irvoltagetest3, irdistancetest3);
        double irvoltagetest4 = Cosmo.sharp4.getVoltage();
        double irdistancetest4 = -7.411 * Math.pow(irvoltagetest4, 3) + 52.356 * Math.pow(irvoltagetest4, 2) - 125.222 * irvoltagetest4 + 111.659;
        telemetry.addData("", "Sharp4 volts, Distance(cm): %4.2f  %4.1f", irvoltagetest4, irdistancetest4);

        double irvoltage = Cosmo.sharp.getVoltage();
        double irdistance = 13*  Math.pow(irvoltage, -1) ;
        telemetry.addData("", "Sharp inverse volts, Distance(cm): %4.2f  %4.1f", irvoltage, irdistance);

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


//    public void drivesmart(double x, double y, double turn) {
//
//        if (frontIsForward) {             // driving with the front facing forward
//
//        } else {                            // driving with the rear facing forward
//            y = -y;
//            x = -x;
//        }
//
//        double lfpower;
//        double lrpower;
//        double rfpower;
//        double rrpower;
//
//        double rotation = turn;  // knock down the turn power -- NOT ANYMORE
//
//        //Determine largest power being applied in either direction
//
//        lfpower = ( y - x + rotation);
//        lrpower = ( y + x + rotation);
//        rfpower = ( y + x - rotation);
//        rrpower = ( y - x - rotation);
//
//        lfpower = lfpower * topSpeed;
//        lrpower = lrpower * topSpeed;
//        rfpower = rfpower * topSpeed;
//        rrpower = rrpower * topSpeed;
//
//       // Cosmo.leftFront.setPower(lfpower);
//        Cosmo.leftRear.setPower(lrpower);
//       // Cosmo.rightFront.setPower(rfpower);
//        Cosmo.rightRear.setPower(rrpower);
//
//    }
////
//
//
//    public void stop() {
//        //Cosmo.leftFront.setPower(0.0);
//        Cosmo.leftRear.setPower(0.0);
//       // Cosmo.rightFront.setPower(0.0);
//        Cosmo.rightRear.setPower(0.0);
//    }




}
