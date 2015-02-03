package gui;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.BufferUtils.*;

public class Surface {
    
    private float[] pointsArray = new float[]{
        -1.5f, -1.5f, 4f, -0.5f, -1.5f, 2f,
        0.5f, -1.5f, -1f, 1.5f, -1.5f, 2f,
        -1.5f, -.5f, 1f, -.5f, -.5f, 3f,
        .5f, -.5f, 0f, 1.5f, -.5f, -1f,
        -1.5f, .5f, 4f, -.5f, .5f, 0f,
        0.5f, 0.5f, 3.0f, 1.5f, 0.5f, 4.0f,
        -1.5f, 1.5f, -2f, -0.5f, 1.5f, -2f,
        0.5f, 1.5f, 0f, 1.5f, 1.5f, -1f
    };
    
    private float f(float x, float y) {
        return (float)(Math.cos(x) + Math.sin(y));
    }
    
    public void init() {
        
        int numPoints = 200;
        float d = 0.1f;
        
        pointsArray = new float[numPoints * numPoints * 3];
        for (int x = 0; x < numPoints; x++) {
            for (int y = 0; y < numPoints; y++) {
                int index = (x * numPoints + y) * 3;
                pointsArray[index] = x * d;
                pointsArray[index + 1] = y * d;
                pointsArray[index + 2] = f(x*d, y*d);
            }
        }
        
        //debug
//        for (int i = 0; i < pointsArray.length; i+=3) {
//            System.out.println("x: " + pointsArray[i] + ", y: " + pointsArray[i+1] + ", z: " + pointsArray[i+2]);
//        }
        
        glClearColor(0f, 0f, 0f, 0f);
        
        FloatBuffer pointsBuffer = createFloatBuffer(pointsArray.length);
        pointsBuffer.put(pointsArray);
        pointsBuffer.flip();
        
        glMap2f(GL_MAP2_VERTEX_3, 0f, 1f, 3, 4, 0, 1f, 12, 4, pointsBuffer);
        glEnable(GL_MAP2_VERTEX_3);
        glMapGrid2f(20, 0, 1, 20, 0, 1);
        glEnable(GL_DEPTH_TEST);
        glShadeModel(GL_FLAT);
    }
    
    public void draw() {
        glColor3f(1f, 1f, 1f);
        glBegin(GL_POINTS);
            for (int i = 0; i < pointsArray.length; i += 3) {
                glVertex3f(pointsArray[i], pointsArray[i+1], pointsArray[i+2]);
            }
        glEnd();
//        glPushMatrix();
////            glRotatef(85f, 1f, 1f, 1f);
////            glEvalMesh2(GL_FILL, 0, 20, 0, 20);
//            glRotatef(85f, 1f, 1f, 1f);
//            for (int i = 0; i < 8; i++) {
//                glBegin(GL_LINE_STRIP);
//                    for (int j = 0; j <= 30; j++) {
//                        glEvalCoord2f((float)j/30.0f, (float)i/8.0f);
//                    }
//                glEnd();
//                glBegin(GL_LINE_STRIP);
//                    for (int j = 0; j <= 30; j++) {
//                        glEvalCoord2f((float)i/8.0f, (float)j/30.0f);
//                    }
//                glEnd();
//            }
//        glPopMatrix();
    }
}
