package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.ForwardSpotShader;

public class SpotLight extends PointLight {

	private float cutoff;
	private Vector3f direction;

	// TODO: Find A better way of doing this or change back to PointLight in
	// parameter.
	public SpotLight(Vector3f color, float intensity, float constant, float linear, float exponent, Vector3f position,
			float range, Vector3f direction, float cutoff) {

		super(color, intensity, constant, linear, exponent, position, range);
		this.direction = direction.normalize();
		this.cutoff = cutoff;

		super.setShader(ForwardSpotShader.getInstance());

	}

	public float getCutoff() {
		return cutoff;

	}

	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction.normalize();
	}

}