package com.base.engine.rendering.shaders;

import com.base.engine.math.Matrix4f;
import com.base.engine.math.Transform;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class ForwardAmbientShader extends Shader {

	private static final ForwardAmbientShader instance = new ForwardAmbientShader();

	public static ForwardAmbientShader getInstance() {
		return instance;
	}

	public ForwardAmbientShader() {
		super("forward-ambient");

	}

	@Override
	public void updateUniform(Transform transform, Material mat, RenderingEngine engine) {

		Matrix4f worldMatrix = transform.getTransformation(),
				projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);
		mat.getTexture("diffuse").bind();

		super.setUniformMatrix4f("MVP", projectedMatrix);
		super.setUniform3f("ambientIntensity", engine.getAmbientLight());

	}

}