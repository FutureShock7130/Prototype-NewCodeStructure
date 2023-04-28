package frc.lib.util;

import edu.wpi.first.wpilibj.Joystick;

/** An utility class for Logitech F310 controller.
 * 
 * @author Neil | 6th (hongnai4507@gmail.com)
 */
public class JoystickUtil {
    Joystick js;
    
    public JoystickUtil(Joystick js) {
        this.js = js;
    }

    public void getA() {
        js.getRawButton(1);
    }

    public void getAPressed() {
        js.getRawButtonPressed(1);
    }

    public void getAReleased() {
        js.getRawButtonReleased(1);
    }

    public void getB() {
        js.getRawButton(2);
    }

    public void getBPressed() {
        js.getRawButtonPressed(2);
    }

    public void getBReleased() {
        js.getRawButtonReleased(2);
    }

    public void getY() {
        js.getRawButton(4);
    }

    public void getYPressed() {
        js.getRawButtonPressed(4);
    }

    public void getYReleased() {
        js.getRawButtonReleased(4);
    }

    public void getX() {
        js.getRawButton(3);
    }

    public void getXPressed() {
        js.getRawButtonPressed(3);
    }

    public void getXReleased() {
        js.getRawButtonReleased(3);
    }

    public void getDpadUp() {
        js.getPOV(0);
    }

    public void getDpadDown() {
        js.getPOV(180);
    }

    public void getDpadLeft() {
        js.getPOV(270);
    }

    public void getDpadRight() {
        js.getPOV(90);
    }

    public void getLeftBumper() {
        js.getRawButton(5);
    }

    public void getLeftBumperPressed() {
        js.getRawButtonPressed(5);
    }

    public void getLeftBumperReleased() {
        js.getRawButtonReleased(5);
    }

    public void getRightBumper() {
        js.getRawButton(6);
    }

    public void getRightBumperPressed() {
        js.getRawButtonPressed(6);
    }

    public void getRightBumperReleased() {
        js.getRawButtonReleased(6);
    }

    public void getLeftTrigger() {
        js.getRawAxis(2);
    }

    public void getRightTrigger() {
        js.getRawAxis(3);
    }

    public void getStartButton() {
        js.getRawButton(8);
    }

    public void getStartButtonPressed() {
        js.getRawButtonPressed(8);
    }

    public void getStartButtonReleased() {
        js.getRawButtonReleased(8);
    }

    public void getBackButton() {
        js.getRawButton(7);
    }

    public void getBackButtonPressed() {
        js.getRawButtonPressed(7);
    }

    public void getBackButtonReleased() {
        js.getRawButtonReleased(7);
    }

    public void getLeftY() {
        js.getRawAxis(1);
    }

    public void getLeftX() {
        js.getRawAxis(0);
    }

    public void getRightY() {
        js.getRawAxis(5);
    }

    public void getRightX() {
        js.getRawAxis(4);
    }

    public void getLeftStick() {
        js.getRawButton(9);
    }

    public void getLeftStickPressed() {
        js.getRawButtonPressed(9);
    }

    public void getLeftStickReleased() {
        js.getRawButtonReleased(9);
    }

    public void getRightStick() {
        js.getRawButton(10);
    }

    public void getRightStickPressed() {
        js.getRawButtonPressed(10);
    }

    public void getRightStickReleased() {
        js.getRawButtonReleased(10);
    }
}
