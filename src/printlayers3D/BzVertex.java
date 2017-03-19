package printlayers3D;

import processing.core.PApplet;
import processing.core.PConstants;
import toxi.geom.Vec2D;
import toxi.geom.Vec3D;

public class BzVertex {

	layerPrint3D p5;
	Vec2D pos;
	float radious;
	boolean isSelected = false;
	int index;
	Vec2D offsetDirection;
	float ScaleVectorDir = 20;
	float offsetDimension = 1;
	boolean isSelectedOptions = false;
	float angleRot = 0;

	public BzVertex(layerPrint3D _p5, Vec2D _pos, int _index) {
		p5 = _p5;
		pos = _pos;
		index = _index;
		radious = 20;
		offsetDirection = new Vec2D((offsetDimension + 0) * ScaleVectorDir, (offsetDimension + 0) * ScaleVectorDir);
	}

	void run() {
		render();
		dragVertex();
		updatePos();
		renderoffSetDirection();
		this.offsetDirection.rotate(PApplet.radians(this.angleRot));
		offsetDirection.normalizeTo(offsetDimension);
		// optionsVertex();
		scaleLineWith_OffsetDirection();
	}

	void render() {
		p5.pushMatrix();
		p5.translate(pos.x, pos.y);
		p5.pushStyle();
		p5.stroke(255, 0, 255);
		p5.strokeWeight(1);
		if (isSelected)
			p5.fill(255, 100);
		else
			p5.fill(255, 0, 255, 50);

		p5.ellipseMode(p5.CENTER);
		p5.ellipse(0, 0, radious, radious);
		p5.fill(255, 255);
		p5.text(index, 0, 0);
		if (isSelectedOptions) {
			p5.fill(255, 0, 0, 170);
			p5.ellipse(0, 0, radious * 1.2f, radious * 1.2f);
		}
		p5.popStyle();
		p5.popMatrix();
	}

	void renderoffSetDirection() {
		p5.pushMatrix();
		p5.translate(pos.x, pos.y);
		p5.pushStyle();
		p5.stroke(0, 255, 255);
		p5.strokeWeight(1);
		Vec2D offsetScaled = offsetDirection.copy().normalizeTo(offsetDimension + radious * 1.2f);
		p5.line(0, 0, offsetScaled.x, offsetScaled.y);
		p5.ellipseMode(p5.CENTER);
		p5.fill(0, 255, 255, 100);
		p5.ellipse(offsetScaled.x, offsetScaled.y, 5, 5);
		if (offsetDimension != 1) {
			p5.noFill();
			p5.ellipse(offsetScaled.x, offsetScaled.y, 6, 6);
			p5.ellipse(offsetScaled.x, offsetScaled.y, 7, 7);
			p5.ellipse(offsetScaled.x, offsetScaled.y, 9, 9);
			String sc = p5.nf(offsetDimension, 2, 1);
			p5.text(sc, offsetScaled.x+5, offsetScaled.y+5);
		}
		p5.popStyle();
		p5.popMatrix();
	}

	void updatePos() {
		if (isSelected) {
			this.pos.x = p5.mouseX;
			this.pos.y = p5.mouseY;
		}
	}

	void rotateOffsetDirectionClock() {
		if (this.isSelectedOptions) {
			this.angleRot++;
		}
	}

	void rotateOffsetDirectionAntiClock() {
		if (this.isSelectedOptions) {
			this.angleRot--;
		}
	}

	void scaleLineWith_OffsetDirection() {
		if (p5.newLap) {
			if (this.offsetDimension > 1) {
				this.pos.addSelf(this.offsetDirection);
			}
		}

	}

	// void optionsVertex() {
	//
	// Vec2D mousePos = new Vec2D(p5.mouseX, p5.mouseY);
	// float dist = mousePos.distanceTo(pos);
	//
	// // if ((!p5.dragging) || (p5.indexDragged == this.index)) {
	// if ((p5.mouseSelectActive) && (dist < radious / 2)) {
	// if (p5.mouseButton == PConstants.RIGHT) {
	// isSelectedOptions = !isSelectedOptions;
	// }
	// }
	//
	// // }
	// }

	void dragVertex() {

		Vec2D mousePos = new Vec2D(p5.mouseX, p5.mouseY);
		float dist = mousePos.distanceTo(pos);

		if ((!p5.dragging) || (p5.indexDragged == this.index)) {
			if ((p5.mousePressed) && (dist < radious / 2)) {
				isSelected = true;
				p5.dragging = true;
				p5.indexDragged = this.index;
			}
			if (p5.mouseReleasedBool) {
				isSelected = false;
				p5.dragging = false;
			}
		}
	}

}
