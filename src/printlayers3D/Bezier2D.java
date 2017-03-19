package printlayers3D;

import java.io.PrintWriter;
import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Vec2D;

public class Bezier2D {

	layerPrint3D p5;
	ArrayList<BzVertex> vertices;
	ArrayList<Vec2D> divPts;

	Bezier2D(layerPrint3D _p5) {
		p5 = _p5;
		vertices = new ArrayList<BzVertex>();
		createVertices();
	}

	void run() {
		render();
		runVertices();
		divideCurve();
		runDivPoints();
	}

	void render() {

		BzVertex v1 = vertices.get(0);
		BzVertex v2 = vertices.get(1);
		BzVertex v3 = vertices.get(2);
		BzVertex v4 = vertices.get(3);

		p5.pushStyle();
		p5.noFill();
		p5.stroke(0, 255, 255);
		// p5.line(v1.pos.x,v1.pos.y,v2.pos.x,v2.pos.y);
		// p5.line(v3.pos.x,v3.pos.y,v4.pos.x,v4.pos.y);
		p5.stroke(255, 255);
		// p5.bezier(v1.pos.x,v1.pos.y,v2.pos.x,v2.pos.y,v3.pos.x,v3.pos.y,v4.pos.x,v4.pos.y);

		p5.beginShape();
		// p5.curveVertex(v1.pos.x,v1.pos.y);
		p5.curveVertex(v1.pos.x, v1.pos.y);
		p5.curveVertex(v2.pos.x, v2.pos.y);
		p5.curveVertex(v3.pos.x, v3.pos.y);
		p5.curveVertex(v4.pos.x, v4.pos.y);
		// p5.curveVertex(v4.pos.x,v4.pos.y);
		p5.curveVertex(v1.pos.x, v1.pos.y);
		p5.curveVertex(v2.pos.x, v2.pos.y);
		p5.curveVertex(v3.pos.x, v3.pos.y);

		// p5.curveVertex(v1.pos.x,v1.pos.y);
		// p5.curveVertex(v2.pos.x,v2.pos.y);

		p5.endShape();

		// p5.beginShape();
		// p5.vertex(v1.pos.x,v1.pos.y);
		// p5.bezierVertex(v1.pos.x,v1.pos.y,v2.pos.x,v2.pos.y,v3.pos.x,v3.pos.y);
		//// p5.bezierVertex(v2.pos.x,v2.pos.y,v3.pos.x,v3.pos.y,v4.pos.x,v4.pos.y);
		// p5.endShape();
		p5.popStyle();

	}

	void createVertices() {

		int numbVertices = 4;

		for (int i = 0; i < 4; i++) {
			Vec2D v1 = new Vec2D((i + 1) * (p5.width / (numbVertices + 1)), p5.height / 2);
			BzVertex bv1 = new BzVertex(p5, v1, i);
			vertices.add(bv1);
		}

		divPts = new ArrayList<Vec2D>();

	}

	void divideCurve() {

		divPts = new ArrayList<Vec2D>();

		int numbSequence[] = new int[vertices.size()];
		// PApplet.println("this.vertices.size() = " + this.vertices.size());
		for (int j = 0; j < this.vertices.size(); j++) {
			numbSequence[j] = j;
		}

		for (int j = 0; j < this.vertices.size(); j++) {

			for (int i = 0; i <= p5.divResolution; i++) {

				float t = i / (float) (p5.divResolution);

				BzVertex v1 = vertices.get(numbSequence[0]);
				BzVertex v2 = vertices.get(numbSequence[1]);
				BzVertex v3 = vertices.get(numbSequence[2]);
				BzVertex v4 = vertices.get(numbSequence[3]);
				float x = p5.curvePoint(v1.pos.x, v2.pos.x, v3.pos.x, v4.pos.x, t);
				float y = p5.curvePoint(v1.pos.y, v2.pos.y, v3.pos.y, v4.pos.y, t);
				divPts.add(new Vec2D(x, y));

			}

			int[] numbSequenceTemp = PApplet.subset(numbSequence, 1);
			int[] numbSequenceTemp2 = PApplet.append(numbSequenceTemp, numbSequence[0]);
			numbSequence = numbSequenceTemp2;
			// PApplet.println("numbSequence[] = " + numbSequence[0] + "," +
			// numbSequence[1] + "," + numbSequence[2] + ","
			// + numbSequence[3]);
			// PApplet.println("j = " + j);

		}

	}

	void runVertices() {

		for (int i = 0; i < vertices.size(); i++) {
			BzVertex bv = vertices.get(i);
			bv.run();
		}

	}



	void runDivPoints() {

		float rad = 2.0f;

		for (int i = 0; i < divPts.size(); i++) {
			Vec2D dPt = divPts.get(i);
			p5.noStroke();
			p5.ellipse(dPt.x, dPt.y, rad, rad);
		}

	}

}
