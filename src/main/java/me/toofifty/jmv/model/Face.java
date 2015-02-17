package me.toofifty.jmv.model;

import me.toofifty.jmv.model.Element.Dir;

import org.lwjgl.util.vector.Vector2f;

import com.fasterxml.jackson.databind.JsonNode;

public class Face {

	// Main
	private Vector2f uv_from;
	private Vector2f uv_to;
	private String texture;
	private Dir cullface;
	private int rotation;

	private Model model;

	/**
	 * Create a new face with float values.
	 * 
	 * @param fu
	 * @param fv
	 * @param tu
	 * @param tv
	 * @param tex
	 */
	public Face(float fu, float fv, float tu, float tv, String tex) {
		this.uv_from = new Vector2f(fu, fv);
		this.uv_to = new Vector2f(tu, tv);
		this.texture = tex;
	}

	/**
	 * Create a new face with Vectors.
	 * 
	 * @param uv_from
	 * @param uv_to
	 * @param tex
	 */
	public Face(Vector2f uv_from, Vector2f uv_to, String tex) {
		this.uv_from = uv_from;
		this.uv_to = uv_to;
		this.texture = tex;
	}

	/**
	 * Create a face from a JSON node.
	 * 
	 * @param rootNode
	 */
	public Face(Model model, JsonNode rootNode) {
		final JsonNode uvNode = rootNode.path("uv");
		this.model = model;
		if (uvNode != null && uvNode.size() == 4) {
			this.uv_from = new Vector2f(uvNode.get(0).floatValue(), uvNode.get(1)
					.floatValue());
			this.uv_to = new Vector2f(uvNode.get(2).floatValue(), uvNode.get(3)
					.floatValue());
		} else {
			this.uv_from = new Vector2f(0, 0);
			this.uv_to = new Vector2f(16F, 16F);
		}

		final JsonNode textureNode = rootNode.path("texture");
		if (textureNode != null) {
			texture = textureNode.textValue();
			texture = texture.substring(1, texture.length());
		}

		final JsonNode cullfaceNode = rootNode.path("cullface");
		if (cullfaceNode != null && cullfaceNode.toString() != "") {
			cullface = Element.getDir(cullfaceNode.toString());
		}

		final JsonNode rotationNode = rootNode.path("rotation");
		if (rotationNode != null) {
			rotation = rotationNode.asInt();
		}
	}

	/**
	 * UV-From getter
	 * 
	 * @return uv_from + textureCoords
	 */
	public Vector2f getUVFrom(Vector2f textureCoords) {
		return new Vector2f(textureCoords.x + uv_from.x / model.getAtlas().width,
				textureCoords.y + uv_from.y / model.getAtlas().height);
	}

	/**
	 * UV-To getter
	 * 
	 * @return uv_to + textureCoords
	 */
	public Vector2f getUVTo(Vector2f textureCoords) {
		return new Vector2f(textureCoords.x + uv_to.x / model.getAtlas().width,
				textureCoords.y + uv_to.y / model.getAtlas().height);
	}

	/**
	 * Texture getter
	 * 
	 * @return texture name as string
	 */
	public String getTexture() {
		return texture;
	}

}
