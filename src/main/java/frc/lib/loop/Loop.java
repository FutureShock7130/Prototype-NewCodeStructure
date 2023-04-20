package frc.lib.loop;

// By Team 254
public interface Loop {
    
    void onStart(double timeStamp);

    void onLoop(double timeStamp);

    void onStop(double timeStamp);
        
}
