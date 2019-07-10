package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.math.Matrix4f;
import com.base.engine.math.Transform;

public class ForwardSpotShader extends Shader {

	private static final ForwardSpotShader instance = new ForwardSpotShader();

	public static ForwardSpotShader getInstance() {
		return instance;
	}

	public ForwardSpotShader() {
		super();

		super.addVertexShaderFromFile("forward-spot.vs");
		super.addFragmentShaderFromFile("forward-spot.fs");

		super.setAttribLocation("position", 0);
		super.setAttribLocation("texCoord", 1);
		super.setAttribLocation("normal", 2);

		// After Setting Attributes And Loading Shaders
		super.compileShader();

		super.addUniform("model");
		super.addUniform("MVP");

		super.addUniform("specularIntensity");
		super.addUniform("specularPower");
		super.addUniform("eyePos");

		super.addUniform("spotLight.pointLight.base.color");
		super.addUniform("spotLight.pointLight.base.intensity");
		super.addUniform("spotLight.pointLight.atten.constant");
		super.addUniform("spotLight.pointLight.atten.linear");
		super.addUniform("spotLight.pointLight.atten.exponent");
		super.addUniform("spotLight.pointLight.position");
		super.addUniform("spotLight.pointLight.range");
		super.addUniform("spotLight.cutoff");
		super.addUniform("spotLight.direction");

	}

	@Override
	public void updateUniform(Transform transform, Material mat, RenderingEngine engine) {

		Matrix4f worldMatrix = transform.getTransformation(),
				projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);
		mat.getTexture("diffuse").bind();

		super.setUniformMatrix4f("model", worldMatrix);
		super.setUniformMatrix4f("MVP", projectedMatrix);
		super.setUniformf("specularIntensity", mat.getFloat("specularIntensity"));
		super.setUniformf("specularPower", mat.getFloat("specularPower"));

		super.setUniform3f("eyePos", engine.getMainCamera().getTransform().getTransformedPosition());

		setUniformSpotLight("spotLight", (SpotLight) engine.getActiveLight());

	}

	public void setUniformPointLight(String uniformName, PointLight pointLight) {

		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform3f(uniformName + ".position", pointLight.getTransform().getPosition());
		setUniformf(uniformName + ".range", pointLight.getRange());

	}

	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {

		setUniform3f(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());

	}

	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniformPointLight(uniformName + ".pointLight", (PointLight) spotLight);
		setUniform3f(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());

	}

}
