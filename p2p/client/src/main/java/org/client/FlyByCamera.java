package org.client;

import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
//import com.jme3.input.binding.BindingListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;


public class FlyByCamera implements AnalogListener, ActionListener {

    private Camera cam;
    private Vector3f initialUpVec;
    private float moveSpeed = 100f;
    private MotionAllowedListener motionAllowed = null;
    private boolean enabled = true;
    private InputManager inputManager;
   

    public FlyByCamera(Camera cam, InputManager inputManager){
        this.cam = cam;
        initialUpVec = cam.getUp().clone();
        this.inputManager = inputManager;
         registerWithInput();
    }

    public void setMotionAllowedListener(MotionAllowedListener listener){
        this.motionAllowed = listener;
    }

    public void setMoveSpeed(float moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    public void setEnabled(boolean enable){
        enabled = enable;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void registerWithInput(){
        String[] mappings = new String[]{"FLYCAM_RiseSpace","FLYCAM_LowerShift"};       
        inputManager.addMapping("FLYCAM_RiseSpace", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addMapping("FLYCAM_LowerShift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener(this, mappings);
    }


    private void riseCamera(float value){
        Vector3f vel = new Vector3f(0, value * moveSpeed, 0);
        Vector3f pos = cam.getLocation().clone();

        if (motionAllowed != null)
            motionAllowed.checkMotionAllowed(pos, vel);
        else
            pos.addLocal(vel);

        cam.setLocation(pos);
    }


    public void onAnalog(String name, float value, float tpf) {
        if (!enabled)
        return;

        if (name.equals("FLYCAM_RiseSpace")){
            riseCamera(value);
        }else if (name.equals("FLYCAM_LowerShift")){
        	riseCamera(-value);
        }
    }

    public void onAction(String name, boolean value, float tpf) {
        if (!enabled)
            return;
    }
}
