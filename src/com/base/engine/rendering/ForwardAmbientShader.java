package com.base.engine.rendering;

import com.base.engine.core.Transform;

public class ForwardAmbientShader extends Shader {

	private static final ForwardAmbientShader instance = new ForwardAmbientShader();

	public static ForwardAmbientShader getInstance() {
		return instance;
	}

	public ForwardAmbientShader() {
		super();

		super.addVertexShaderFromFile("forward-ambient.vs");
		super.addFragmentShaderFromFile("forward-ambient.fs");

		super.setAttribLocation("position", 0);
		super.setAttribLocation("texCoord", 1);

		super.compileShader();

		super.addUniform("MVP");
		super.addUniform("ambientIntensity");

	}

	@Override
	public void updateUniform(Transform transform, Material mat) {

		Matrix4f worldMatrix = transform.getTransformation(),
				projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		mat.getTexture().bind();

		super.setUniformMatrix4f("MVP", projectedMatrix);
		super.setUniform3f("ambientIntensity", getRenderingEngine().getAmbientLight());

	}

}
