package printlayers3D;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;

public class MovingPt {

	layerPrint3D p5;
	PWindow p52;
	Vec2D pos;
	float radious = 15;
	int indexVertex = 0;

	MovingPt(layerPrint3D _p5, PWindow _p52, Vec2D _pos) {
		p5 = _p5;
		p52 = _p52;
		pos = _pos;
	}

	void run() {
		render();
		updatePos();
		if (!p5.pause)
			pourePts();
	}

	void render() {
		p5.pushMatrix();
		p5.translate(pos.x, pos.y);
		p5.pushStyle();
		// p5.stroke(255, 0, 255);
		p5.strokeWeight(3);
		p5.noFill();
		if (p5.pause)
			p5.stroke(255, 255);
		else
			p5.stroke(255, 0, 255, 255);
		p5.ellipseMode(p5.CENTER);
		p5.ellipse(0, 0, radious, radious);
		p5.popStyle();
		p5.popMatrix();
	}

	void updatePos() {

		Vec2D vertexPos = p5.bezier2D.divPts.get(indexVertex);
		this.pos = vertexPos;

		if (!p5.pause) {
			if (indexVertex < p5.bezier2D.divPts.size() - 1) {
				indexVertex++;
				p5.newLap = false;
			} else {
				indexVertex = 0;
				p5.newLap = true;
//				p5.newLapOffsetting();
				p5.layerCount++;
			}
		}
	}

	void pourePts() {
		if (!p5.pause) {

			float newZ = 0;

			Vec3D newPos = new Vec3D(p5.movingPt.pos.x, p5.movingPt.pos.y, 10000);
			ExtrudedPt ePt = new ExtrudedPt(p5, p52, newPos, p5.layerCount, p5.extrudedPts.size());
			if (!p5.extrudedPts.contains(ePt))
				p5.extrudedPts.add(ePt);

			if (ePt.closest2D == null) {
				ePt.pos.z = 0;
			} else {
				ePt.pos.z = ePt.closest2D.pos.z + p5.heightOffset;
			}

		}


	}

}
