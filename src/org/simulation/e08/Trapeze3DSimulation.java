package org.simulation.e08;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.simulation.e07.DataPacketState;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Trapeze3DSimulation {

    DataPacketState receivedData;

    public void start() {
        // Create the universe
        SimpleUniverse universe = new SimpleUniverse();
        // Create a branch group for 3D objects
        BranchGroup group = new BranchGroup();

        // Create a sphere as the artist.
        Sphere sphere = new Sphere(0.5f);
        group.addChild(sphere);

        // Create a line as the rope.
        float vert[] = { 0.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f, 1.5f, 2.5f, 3.5f };
        float color[] = { 0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 1.0f };
        LineArray line = new LineArray(6, LineArray.COORDINATES | LineArray.COLOR_3);
        line.setCoordinate(0, vert);
        line.setColors(0, color);
        LineAttributes linea = new LineAttributes();
        linea.setLineWidth(2.0f);
        linea.setLineAntialiasingEnable(true);
        Appearance app = new Appearance();
        app.setLineAttributes(linea);
        Shape3D shape = new Shape3D();
        shape.setGeometry(line);
        shape.setAppearance(app);
        group.addChild(shape);
        
        // Create a red light that shines for 100 from the origin
        // the color of the light.
        Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
        // the direction of the light.
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        // the bound range of the light.
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);

        // set the view point. look towards the trapeze.
        universe.getViewingPlatform().setNominalViewingTransform();
        // add the group of objects to the Universe
        universe.addBranchGraph(group);
    }

    public void ball() {
        // Create the universe
        SimpleUniverse universe = new SimpleUniverse();
        // Create a structure to contain objects
        BranchGroup group = new BranchGroup();
        // Create a ball and add it to the group of objects
        Sphere sphere = new Sphere(0.5f);
        group.addChild(sphere);
        // Create a red light that shines for 100m from the origin
        // the color of the light.
        Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
        // the direction of the light.
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        // the bound range of the light.
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);
        // set the view point. look towards the ball
        universe.getViewingPlatform().setNominalViewingTransform();
        // add the group of objects to the Universe
        universe.addBranchGraph(group);
    }

    public void position() {
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup group = new BranchGroup();
        // X axis made of spheres
        for (float x = -1.0f; x <= 1.0f; x = x + 0.1f) {
            Sphere sphere = new Sphere(0.05f);
            TransformGroup tg = new TransformGroup();
            Transform3D transform = new Transform3D();
            Vector3f vector = new Vector3f(x, .0f, .0f);
            transform.setTranslation(vector);
            tg.setTransform(transform);
            tg.addChild(sphere);
            group.addChild(tg);
        }
        // Y axis made of cones
        for (float y = -1.0f; y <= 1.0f; y = y + 0.1f) {
            TransformGroup tg = new TransformGroup();
            Transform3D transform = new Transform3D();
            Cone cone = new Cone(0.05f, 0.1f);
            Vector3f vector = new Vector3f(.0f, y, .0f);
            transform.setTranslation(vector);
            tg.setTransform(transform);
            tg.addChild(cone);
            group.addChild(tg);
        }
        // Z axis made of cylinders
        for (float z = -1.0f; z <= 1.0f; z = z + 0.1f) {
            TransformGroup tg = new TransformGroup();
            Transform3D transform = new Transform3D();
            Cylinder cylinder = new Cylinder(0.05f, 0.1f);
            Vector3f vector = new Vector3f(.0f, .0f, z);
            transform.setTranslation(vector);
            tg.setTransform(transform);
            tg.addChild(cylinder);
            group.addChild(tg);
        }

        Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);
        universe.getViewingPlatform().setNominalViewingTransform();

        // add the group of objects to the Universe
        universe.addBranchGraph(group);
    }

    public void line() {
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup group = new BranchGroup();

        float vert[] = { 0.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f, 1.5f, 2.5f, 3.5f };
        float color[] = { 0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 1.0f, 0.0f, 0.5f, 1.0f };
        LineArray line = new LineArray(6, LineArray.COORDINATES | LineArray.COLOR_3);
        line.setCoordinate(0, vert);
        line.setColors(0, color);
        LineAttributes linea = new LineAttributes();
        linea.setLineWidth(2.0f);
        linea.setLineAntialiasingEnable(true);

        Appearance app = new Appearance();
        app.setLineAttributes(linea);
        Shape3D shape = new Shape3D();
        shape.setGeometry(line);
        shape.setAppearance(app);
        group.addChild(shape);

        Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }

    public static void main(String... strings) {
        Trapeze3DSimulation t = new Trapeze3DSimulation();
        t.line();
    }

}
