package printlayers3D;

import java.util.ArrayList;

import processing.core.PApplet;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;

public class ExtrudedPt {

	layerPrint3D p5;
	PWindow p52;
	Vec3D pos;
	float radious = 20;
	boolean isSelected = false;
	int index;
	int index2D;
	ExtrudedPt closest2D;
	int layerId;
	int life=0;

	public ExtrudedPt(layerPrint3D _p5, PWindow _p52, Vec3D _pos, int _layerId, int _index) {
		p5 = _p5;
		p52 = _p52;
		pos = _pos;
		layerId = _layerId;
		index = _index;
		// closest2D = null;
		// if (p5.layerCount > 0)
		closest2D = findClosest();
	}

	void run() {
//		render();
//		renderLineToPrevious();
//		renderLineToClosest();
		life++;
	}

	void render() {
		p52.pushStyle();
		p52.stroke(255, 0, 255);
		p52.strokeWeight(4);
		// p5.fill(255, 255);
		p52.point(pos.x, pos.y, pos.z);
		p52.popStyle();
	}

	void renderLineToPrevious() {
		if (index > 0) {
			p52.pushStyle();
			p52.stroke(255, 255);
			p52.strokeWeight(1);
			ExtrudedPt previous = p5.extrudedPts.get(index - 1);
			p52.line(this.pos.x, this.pos.y, this.pos.z, previous.pos.x, previous.pos.y, previous.pos.z);
			p52.popStyle();
		}
	}

	void renderLineToClosest() {
		if (index > 0) {
			if (this.closest2D!=null) {
				p52.pushStyle();
				p52.stroke(255, 255, 0, 255);
				p52.strokeWeight(1);
				p52.line(this.pos.x, this.pos.y, this.pos.z, closest2D.pos.x, closest2D.pos.y, closest2D.pos.z);
				p52.popStyle();
			}
		}
	}

	ExtrudedPt findClosest() {

		float searchRadioius = p5.maxOffset;

		ArrayList<ExtrudedPt> extrudedPtsInRange = new ArrayList<ExtrudedPt>();
		ExtrudedPt closest = null;
		float maxZ = -1;

		if (p5.extrudedPts.size() > 0) {
			for (int i = 0; i < p5.extrudedPts.size()-(p5.divResolution / 10); i++) {
				ExtrudedPt other = p5.extrudedPts.get(i);
				Vec2D this2D = new Vec2D(this.pos.x, this.pos.y);
				Vec2D other2D = new Vec2D(other.pos.x, other.pos.y);
				if (this != other) {
					if ((this.layerId > other.layerId) || (other.life>p5.divResolution / 10)) {
//						if ((this.layerId > other.layerId) || ((PApplet.abs(index - other.index) > p5.divResolution / 10))) {
						float dist = this2D.distanceTo(other2D);

						if (dist < searchRadioius) {
							if (!extrudedPtsInRange.contains(other)) {
								extrudedPtsInRange.add(other);
								// PApplet.println("extrudedPtsInRange.size() =
								// " +
								// extrudedPtsInRange.size());
							}

						}
					}
				}
			}

			if (extrudedPtsInRange.size() > 0) {

				// ExtrudedPt higestPt = extrudedPtsInRange.get(0);
				for (int i = 0; i < extrudedPtsInRange.size(); i++) {
					ExtrudedPt pt = extrudedPtsInRange.get(i);
					if ((this.layerId == 0) || (pt.pos.z > maxZ)) {
						maxZ = pt.pos.z;
						closest = pt;
						PApplet.println("maxZ = " + maxZ);

					}
				}
			}
		}
		return closest;
	}


}
