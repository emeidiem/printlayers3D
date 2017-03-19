package printlayers3D;

import java.io.PrintWriter;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec2D;

public class layerPrint3D extends PApplet {

	PWindow win;
	Bezier2D bezier2D;
	boolean mouseReleasedBool = false;
	MovingPt movingPt;
	boolean pause = true;
	boolean dragging = false;
	boolean mouseSelectActive;
	int indexDragged = 0;
	float maxOffset = 5;
	float heightOffset = 5;
	int layerCount = 0;
	int divResolution = 100;
	boolean newLap = false;
	ArrayList<ExtrudedPt> extrudedPts;

	public void settings() {
		size(600, 600, "processing.opengl.PGraphics3D");

	}

	public void setup() {
		win = new PWindow(this);
		bezier2D = new Bezier2D(this);
		movingPt = new MovingPt(this, win, bezier2D.vertices.get(0).pos);
		extrudedPts = new ArrayList<ExtrudedPt>();

	}

	public void draw() {

		// background(0);
		this.pushStyle();
		this.fill(0, 80);
		this.rectMode(PConstants.CORNER);
		this.rect(0, 0, this.width, this.height);
		this.popStyle();
		// background(255, 0, 0);
		// fill(255);
		// rect(10, 10, frameCount, 10);
		bezier2D.run();
		movingPt.run();

	}

	void newLapOffsetting() {
		for (int j = 0; j < this.bezier2D.vertices.size(); j++) {
			BzVertex v = this.bezier2D.vertices.get(j);
			// if (v.isSelectedOptions)
			v.scaleLineWith_OffsetDirection();
		}
	}

	public void keyPressed() {
		if (key == ' ' || key == ' ') {
			pause = !pause;
		}
		if (key == 'e' || key == 'E') {
			for (int j = 0; j < this.bezier2D.vertices.size(); j++) {
				BzVertex v = this.bezier2D.vertices.get(j);
				if (v.isSelectedOptions)
					v.rotateOffsetDirectionClock();
			}
		}
		if (key == 'd' || key == 'D') {
			for (int j = 0; j < this.bezier2D.vertices.size(); j++) {
				BzVertex v = this.bezier2D.vertices.get(j);
				if (v.isSelectedOptions)
					v.rotateOffsetDirectionAntiClock();
			}
		}
		if (key == 'r' || key == 'R') {
			for (int j = 0; j < this.bezier2D.vertices.size(); j++) {
				BzVertex v = this.bezier2D.vertices.get(j);
				if (v.isSelectedOptions) {
					v.offsetDimension += .2f;
					// v.offsetDirection.normalizeTo(v.offsetDimension+v.radious*2);
				}
			}
		}
		if (key == 'f' || key == 'F') {
			for (int j = 0; j < this.bezier2D.vertices.size(); j++) {
				BzVertex v = this.bezier2D.vertices.get(j);
				if (v.isSelectedOptions) {
					v.offsetDimension -= .2f;
					// v.offsetDirection.normalizeTo(v.offsetDimension+v.radious*2);
				}
			}
		}

		if (key == 's' || key == 'S') {
			this.exportCrv();
		}
	}
	
	void exportCrv() {

		int numbDecimals = 3;
		
		String folderExport = new String("data/exports/" + PApplet.year() + "-" + PApplet.month() + "-" + PApplet.day()
				+ "_" + PApplet.hour() + "-" + PApplet.minute() + "-" + PApplet.second() + "/");
		PrintWriter output = this.createWriter(folderExport + "curve" + ".txt");

		for (int j = 0; j < extrudedPts.size(); j++) {
			output.print(PApplet.nf(extrudedPts.get(j).pos.x, 0, numbDecimals) + ",");
			output.print(PApplet.nf(extrudedPts.get(j).pos.y, 0, numbDecimals) + ",");
			output.print(PApplet.nf(extrudedPts.get(j).pos.z, 0, numbDecimals));
			output.print("\n");
		}
		output.flush(); // Writes the remaining data to the file
		output.close(); // Finishes the file
	}

	public void mouseReleased() {
		mouseReleasedBool = true;
		// mouseSelectActive = false;
	}

	public void mousePressed() {
		if (mouseButton == LEFT) {
			mouseReleasedBool = false;
		}
		if (mouseButton == RIGHT) {
			// mouseSelectActive = false;
		}

		println("mousePressed in primary window");
	}

	public void mouseClicked() {
		if (mouseButton == LEFT) {
		}
		if (mouseButton == RIGHT) {
			for (int j = 0; j < this.bezier2D.vertices.size(); j++) {
				BzVertex v = this.bezier2D.vertices.get(j);
				Vec2D mousePos = new Vec2D(this.mouseX, this.mouseY);
				float dist = mousePos.distanceTo(v.pos);
				if ((dist < v.radious / 2)) {
					v.isSelectedOptions = !v.isSelectedOptions;
				}
			}
			mouseSelectActive = !mouseSelectActive;
		} else {
			// mouseSelectActive = false;
		}
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { layerPrint3D.class.getName() });
	}
}
