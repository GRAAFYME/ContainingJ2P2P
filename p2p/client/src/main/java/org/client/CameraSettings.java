
package org.client;

import com.jme3.app.Application;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;


public class CameraSettings {

    private static Boolean left = false, right = false, up = false, down = false, sprint = false;

    public static CharacterControl MyCharacter(final Application app, final Node rootNode, final PhysicsSpace space) {
        final CharacterControl player;

        Cylinder d = new Cylinder(150, 150, 3.012f, 0, true);
        Geometry myCharacter = new Geometry("Cylinder", d);
        Material mat2 = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat2.setColor("Diffuse", ColorRGBA.Red);
        myCharacter.setLocalTranslation(new Vector3f(-240, 5, 200));
        myCharacter.setMaterial(mat2);
        rootNode.attachChild(myCharacter);

        ConfigFile c = new ConfigFile();
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.5f);
        player.setJumpSpeed(c.PlayerJumpSpeed());
      //  player.setFallSpeed(c.PlayerFallSpeed());
       // player.setGravity(c.PlayersetGravity());
        player.setPhysicsLocation(new Vector3f(-240, 50, 200));
        myCharacter.addControl(player);
        space.add(player);

        ActionListener actionListener = new ActionListener() {
            public void onAction(String name, boolean value, float tpf) {
                if (name.equals("Lefts")) {
                    if (value) {
                        left = true;
                    } else {
                        left = false;
                    }
                } else if (name.equals("Rights")) {
                    if (value) {
                        right = true;
                    } else {
                        right = false;
                    }
                } else if (name.equals("Ups")) {
                    if (value) {
                        up = true;
                    } else {
                        up = false;
                    }
                } else if (name.equals("Downs")) {
                    if (value) {
                        down = true;
                    } else {
                        down = false;
                    }
                } else if (name.equals("Space")) {
                    player.jump();
                } else if (name.equals("lShift")) {
                    if (value) {
                        sprint = true;
                    } else {
                        sprint = false;
                    }
                }
            }
        };
        app.getInputManager().addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        app.getInputManager().addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        app.getInputManager().addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        app.getInputManager().addMapping("Downs", new KeyTrigger(KeyInput.KEY_S));
        app.getInputManager().addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        app.getInputManager().addMapping("lShift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        app.getInputManager().addListener(actionListener, "Lefts");
        app.getInputManager().addListener(actionListener, "Rights");
        app.getInputManager().addListener(actionListener, "Ups");
        app.getInputManager().addListener(actionListener, "Downs");
        app.getInputManager().addListener(actionListener, "Space");
        app.getInputManager().addListener(actionListener, "lShift");
        return player;
    }

    public boolean left() {
        return left;
    }

    public boolean right() {
        return right;
    }

    public boolean up() {
        return up;
    }

    public boolean down() {
        return down;
    }

    public boolean sprint() {
        return sprint;
    }
}
