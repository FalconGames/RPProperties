package net.swordsofvalor.rpproperties;

public class SelectionArea {

	int x, y, z, x2, y2, z2;
	
	public SelectionArea(){
		x = y = z = x2 = y2 = z2 = 0;
	}

	public int getX() {
		return x;
	}

	public SelectionArea setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public SelectionArea setY(int y) {
		this.y = y;
		return this;
	}
	
	public int getZ() {
		return z;
	}

	public SelectionArea setZ(int z) {
		this.z = z;
		return this;
	}

	public int getX2() {
		return x2;
	}

	public SelectionArea setX2(int x2) {
		this.x2 = x2;
		return this;
	}

	public int getY2() {
		return y2;
	}

	public SelectionArea setY2(int y2) {
		this.y2 = y2;
		return this;
	}
	
	public int getZ2() {
		return z2;
	}

	public SelectionArea setZ2(int z2) {
		this.z2 = z2;
		return this;
	}
	
}
