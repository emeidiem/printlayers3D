package printlayers3D;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;

import java.util.ArrayList;

import peasy.PeasyCam;

public class PWindow extends PApplet {

	PeasyCam cam;

	layerPrint3D p5;

	PWindow(layerPrint3D _p5) {
		super();
		PApplet.runSketch(new String[] { this.getClass().getSimpleName() }, this);
		p5 = _p5;
	}

	public void settings() {
		size(1200, 600, "processing.opengl.PGraphics3D");
	}

	public void setup() {
		background(0);
		cam = new PeasyCam(this, 850);// 1150
		cam.setResetOnDoubleClick(false);
		// cam.pan(+2500, -500);
		cam.lookAt(0, 0, 0);

	}

	public void draw() {
		background(0);
		renderPouredPts();
		renderCrvPouredPts();
		// PApplet.println("p5.extrudedPts.size() = " + p5.extrudedPts.size());
		// this.pushMatrix();
		// this.translate(0, 0);
		// this.pushStyle();
		// this.stroke(255,0,255);
		// this.box(100);
		// this.popStyle();
		// this.popMatrix();
		// ellipse(random(width), random(height), random(50), random(50));
	}

	void renderPouredPts() {

		for (int i = 0; i < p5.extrudedPts.size(); i++) {
			ExtrudedPt ept = p5.extrudedPts.get(i);
			ept.run();
		}

	}

	void renderCrvPouredPts() {
		this.pushStyle();
		this.stroke(255);
		;
		this.noFill();
		if (p5.extrudedPts.size() > 3) {
			this.beginShape();
			this.curveVertex(p5.extrudedPts.get(0).pos.x, p5.extrudedPts.get(0).pos.y, p5.extrudedPts.get(0).pos.z);
			this.curveVertex(p5.extrudedPts.get(1).pos.x, p5.extrudedPts.get(1).pos.y, p5.extrudedPts.get(1).pos.z);
			this.curveVertex(p5.extrudedPts.get(2).pos.x, p5.extrudedPts.get(2).pos.y, p5.extrudedPts.get(2).pos.z);

			// }
			for (int i = 0; i < p5.extrudedPts.size(); i++) {
				ExtrudedPt ept = p5.extrudedPts.get(i);
				// ept.run();
				this.curveVertex(ept.pos.x, ept.pos.y, ept.pos.z);
			}
			// if (p5.extrudedPts.size() > 3) {
			this.endShape();
		}
		this.popStyle();

	}

	public void keyPressed() {
		if (key == ' ' || key == ' ') {
			p5.pause = !p5.pause;
		}
		if (key == 's' || key == 'S') {
			p5.exportCrv();
		}
	}

	public void mousePressed() {
		println("mousePressed in secondary window");
	}
}
