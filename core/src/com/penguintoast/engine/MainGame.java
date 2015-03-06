package com.penguintoast.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MainGame extends ApplicationAdapter {
    ShaderProgram shader;
    SpriteBatch batch;
    Texture texture;
    int width, height;
    
    @Override
    public void create () {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        ShaderProgram.pedantic = false;
    	shader = new ShaderProgram(Gdx.files.internal("shader.vert"),
                                   Gdx.files.internal("shader.frag"));
        if (!shader.isCompiled()) {
            System.err.println(shader.getLog());
            System.exit(0);
        }
		
        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());
        
    	Pixmap pix = new Pixmap(width, height, Format.RGBA8888);
    	pix.setColor(Color.BLACK);
    	pix.fill();
    	texture = new Texture(pix);
    	pix.dispose();
    	batch = new SpriteBatch(1000, shader);
        batch.setShader(shader);

        OrthographicCamera cam;
        cam = new OrthographicCamera(width, height);
        cam.setToOrtho(false);
        batch.setProjectionMatrix(cam.combined);
    }

    float time = 0;

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += Gdx.graphics.getDeltaTime();
        batch.begin();
        shader.setUniformf("iGlobalTime", time);
        shader.setUniformf("iResolution", width, height);
        batch.draw(texture, 0, 0);
        batch.end();
    }
}
