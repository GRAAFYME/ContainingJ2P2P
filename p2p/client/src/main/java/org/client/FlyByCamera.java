package org.client;

import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
//import com.jme3.input.binding.BindingListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;


public class FlyByCamera implements AnalogListener, ActionListener {

    private Camera cam;
    private Vector3f initialUpVec;
    private float rotationSpeed = 1f;
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
    public void setRotationSpeed(float rotationSpeed){
        this.rotationSpeed = rotationSpeed;
    }

    public void setEnabled(boolean enable){
        enabled = enable;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void registerWithInput(){
       

        String[] mappings = new String[]{
            "FLYCAM_Left",
            "FLYCAM_Right",
            "FLYCAM_Up",
            "FLYCAM_Down",

            "FLYCAM_StrafeLeft",
            "FLYCAM_StrafeRight",
            "FLYCAM_Forward",
            "FLYCAM_Backward",

            "FLYCAM_ZoomIn",
            "FLYCAM_ZoomOut",
            "FLYCAM_RotateDrag",

            "FLYCAM_RiseZ",
            "FLYCAM_LowerX",
            
            "FLYCAM_RiseSpace",
            "FLYCAM_LowerShift"
         
        };
        inputManager.addMapping("FLYCAM_Left", new MouseAxisTrigger(0, true),
                                               new KeyTrigger(KeyInput.KEY_LEFT));

        inputManager.addMapping("FLYCAM_Right", new MouseAxisTrigger(0, false),
                                                new KeyTrigger(KeyInput.KEY_RIGHT));

        inputManager.addMapping("FLYCAM_Up", new MouseAxisTrigger(1, false),
                                             new KeyTrigger(KeyInput.KEY_UP));

        inputManager.addMapping("FLYCAM_Down", new MouseAxisTrigger(1, true),
                                               new KeyTrigger(KeyInput.KEY_DOWN));

        inputManager.addMapping("FLYCAM_ZoomIn", new MouseAxisTrigger(2, false));
        inputManager.addMapping("FLYCAM_ZoomOut", new MouseAxisTrigger(2, true));
        inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(0));

        inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_S));
        
        inputManager.addMapping("FLYCAM_RiseZ", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("FLYCAM_RiseSpace", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addMapping("FLYCAM_LowerX", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("FLYCAM_LowerShift", new KeyTrigger(KeyInput.KEY_LSHIFT));

        inputManager.addListener(this, mappings);
    }

    private void rotateCamera(float value, Vector3f axis){


        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(rotationSpeed * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        cam.setAxes(q);
    }

    private void zoomCamera(float value){
        // derive fovY value
        float h = cam.getFrustumTop();
        float w = cam.getFrustumRight();
        float aspect = w / h;

        float near = cam.getFrustumNear();

        float fovY = FastMath.atan(h / near)
                  / (FastMath.DEG_TO_RAD * .5f);
        fovY += value * 0.1f;

        h = FastMath.tan( fovY * FastMath.DEG_TO_RAD * .5f) * near;
        w = h * aspect;

        cam.setFrustumTop(h);
        cam.setFrustumBottom(-h);
        cam.setFrustumLeft(-w);
        cam.setFrustumRight(w);
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

    private void moveCamera(float value, boolean sideways){
        Vector3f vel = new Vector3f();
        Vector3f pos = cam.getLocation().clone();

        if (sideways){
            cam.getLeft(vel);
        }else{
            cam.getDirection(vel);
        }
        vel.multLocal(value * moveSpeed);

        if (motionAllowed != null)
            motionAllowed.checkMotionAllowed(pos, vel);
        else
            pos.addLocal(vel);

        cam.setLocation(pos);
    }

    public void onAnalog(String name, float value, float tpf) {
        if (!enabled)
            return;

        if (name.equals("FLYCAM_Left")){
            rotateCamera(value, initialUpVec);
        }else if (name.equals("FLYCAM_Right")){
            rotateCamera(-value, initialUpVec);
        }else if (name.equals("FLYCAM_Up")){
            rotateCamera(-value, cam.getLeft());
        }else if (name.equals("FLYCAM_Down")){
            rotateCamera(value, cam.getLeft());
        }else if (name.equals("FLYCAM_Forward")){
            moveCamera(value, false);
        }else if (name.equals("FLYCAM_Backward")){
            moveCamera(-value, false);
        }else if (name.equals("FLYCAM_StrafeLeft")){
            moveCamera(value, true);
        }else if (name.equals("FLYCAM_StrafeRight")){
            moveCamera(-value, true);
        }else if (name.equals("FLYCAM_RiseZ")){
            riseCamera(value);
        }else if (name.equals("FLYCAM_LowerX")){
        	riseCamera(-value);
        }else if (name.equals("FLYCAM_RiseSpace")){
            riseCamera(value);
        }else if (name.equals("FLYCAM_LowerShift")){
        	riseCamera(-value);
        }
        else if (name.equals("FLYCAM_ZoomIn")){
            zoomCamera(value);
        }else if (name.equals("FLYCAM_ZoomOut")){
            zoomCamera(-value);
        }
    }

    public void onAction(String name, boolean value, float tpf) {
        if (!enabled)
            return;
    }
}
