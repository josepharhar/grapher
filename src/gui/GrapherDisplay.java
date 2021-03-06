package gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

//This import allows calls without the GL11.
import static org.lwjgl.opengl.GL11.*;

public class GrapherDisplay {
    
    private int width = 800;
    private int height = 800;
    
    private float x = 0;
    private float y = 0;
    private float z = 0;

    double downx = 0.0;
    double downy = 0.0;
    boolean pressed = false;
    float diffx = 0.0f;
    float diffy = 0.0f;
    float rotation = 0.0f;
    
    Surface surface;
    Axis axis;

    public void run() throws Exception {
        init();
        loop();
    }
    
    private void init() throws Exception {
        //Vars setup
        surface = new Surface();
        axis = new Axis();
        
        //LWJGL 2 setup
        DisplayMode displayMode = new DisplayMode(width, height);
        Display.setDisplayMode(displayMode);
        Display.setResizable(true);
        Display.create();
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-10, 10, -10, 10, -50, 50);
        glMatrixMode(GL_MODELVIEW);
        
//        BezierCurve.init();
        surface.init();
        
//        Lights.init();
    }
    
    private void loop() {
        while (!Display.isCloseRequested()) {
            glLoadIdentity();
            
            //Keyboard
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                y += .1f;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                y -= .1f;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                x += .1f;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_D)){
                x -= .1f;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
                z += .1f;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_E)){
                z -= .1f;
            }
            
            //Mouse
            if (Mouse.isButtonDown(0)) {
                if (!pressed) {
                    // Just clicked down for the first time, need to store this cursor location
                    pressed = true;
                    downx = Mouse.getX();
                    downy = Mouse.getY();
                } else {
                    // Being held down, need to update rotation relative to init location
                    diffy = (float) -(Mouse.getX() - downx);
                    diffx = (float) -(Mouse.getY() - downy);
                    
                    //total distance cursor has traveled to be used for the rotation
                    double distance = Math.sqrt(diffx * diffx + diffy * diffy);
                    //20 pixels will be 180 degrees of rotation
                    //glRotatef uses DEGREES, NOT RADIANS
                    rotation = (float) (distance / 3.0);
                }
            } else {
                pressed = false;
            }
            
            //Draw
            draw();
        }
    }
    
    private void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glTranslatef(x, y, z);
        
//        rotation += 0.01f;
//        System.out.println("Rotation: " + rotation);
//        System.out.println("diffx: " + diffx);
//        System.out.println("diffy: " + diffy);
        glRotatef(rotation, diffx, -diffy, 0f);
        
        axis.draw();
        
//        BezierCurve.draw();
        surface.draw();
        
        glFlush();
        Display.update();
        Display.sync(60);
    }
    
    private void drawCube() {
        rotation += 0.01f;
        glRotatef(rotation, 2, 1, 0);
        
        glBegin(GL_QUADS);
            glColor3f(0.0f,1.0f,0.0f); // Set The Color To Green
            glVertex3f( 1.0f, 1.0f,-1.0f); // Top Right Of The Quad (Top)
            glVertex3f(-1.0f, 1.0f,-1.0f); // Top Left Of The Quad (Top)
            glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
            glVertex3f( 1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)
            
            glColor3f(1.0f,0.5f,0.0f); // Set The Color To Orange
            glVertex3f( 1.0f,-1.0f, 1.0f); // Top Right Of The Quad (Bottom)
            glVertex3f(-1.0f,-1.0f, 1.0f); // Top Left Of The Quad (Bottom)
            glVertex3f(-1.0f,-1.0f,-1.0f); // Bottom Left Of The Quad (Bottom)
            glVertex3f( 1.0f,-1.0f,-1.0f); // Bottom Right Of The Quad (Bottom)
            
            glColor3f(1.0f,0.0f,0.0f); // Set The Color To Red
            glVertex3f( 1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
            glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
            glVertex3f(-1.0f,-1.0f, 1.0f); // Bottom Left Of The Quad (Front)
            glVertex3f( 1.0f,-1.0f, 1.0f); // Bottom Right Of The Quad (Front)
            
            glColor3f(1.0f,1.0f,0.0f); // Set The Color To Yellow
            glVertex3f( 1.0f,-1.0f,-1.0f); // Bottom Left Of The Quad (Back)
            glVertex3f(-1.0f,-1.0f,-1.0f); // Bottom Right Of The Quad (Back)
            glVertex3f(-1.0f, 1.0f,-1.0f); // Top Right Of The Quad (Back)
            glVertex3f( 1.0f, 1.0f,-1.0f); // Top Left Of The Quad (Back)
            
            glColor3f(0.0f,0.0f,1.0f); // Set The Color To Blue
            glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
            glVertex3f(-1.0f, 1.0f,-1.0f); // Top Left Of The Quad (Left)
            glVertex3f(-1.0f,-1.0f,-1.0f); // Bottom Left Of The Quad (Left)
            glVertex3f(-1.0f,-1.0f, 1.0f); // Bottom Right Of The Quad (Left)
            
            glColor3f(1.0f,0.0f,1.0f); // Set The Color To Violet
            glVertex3f( 1.0f, 1.0f,-1.0f); // Top Right Of The Quad (Right)
            glVertex3f( 1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
            glVertex3f( 1.0f,-1.0f, 1.0f); // Bottom Left Of The Quad (Right)
            glVertex3f( 1.0f,-1.0f,-1.0f); // Bottom Right Of The Quad (Right)
        glEnd();
    }
    
}