package main.java.elsim.models;

/**
 * A simple rectangle class that just defines the shape of a rectangle, without a position in a space or any rotation.
 */
public class Rectangle {
	private int width;
	private int height;

	/**
	 * Creates a rectangle with the given width and height.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(int width, int height) {
		if (width < 0) {
			throw new IllegalArgumentException("width must be non-negative.");
		}

		this.width = width;

		if (height < 0) {
			throw new IllegalArgumentException("height must be non-negative.");
		}

		this.height = height;
	}

	/**
	 * Calculates the area of the rectangle.
	 * @return The area of the rectangle.
	 */
	public int getArea() {
		return width * height;
	}

	/**
	 * Returns the width of the rectangle.
	 * @return The width of the rectangle.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the rectangle.
	 * @return The height of the rectangle.
	 */
	public int getHeight() {
		return height;
	}
}
