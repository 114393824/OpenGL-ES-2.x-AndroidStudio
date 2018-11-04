package com.example.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    public static float[] mProjMatrix = new float[16];
    public static float[] mVMatrix = new float[16];
    public static float[] mMVPMatrix;

    int mProgram;
    int muMVPMatrixHandle;
    int maPositionHandle;
    int maColorHandle;
    String mVertexShader;
    String mFragmentShader;
    static float[] mMMatrix = new float[16];

    FloatBuffer mVertexBuffer;
    FloatBuffer mColorBuffer;
    int vCount = 0;
    float xAngle = 0;

    public Triangle(MyTDView mv) {

        initVertexData();

        initShader(mv);
    }

    public void initVertexData() {

        vCount = 3;
        final float UNIT_SIZE = 0.2f;
        float vertices[] = new float[]
                {
                        -4 * UNIT_SIZE, 0,
                        0, 0, -4 * UNIT_SIZE,
                        0, 4 * UNIT_SIZE, 0, 0
                };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);


        float colors[] = new float[]
                {
                        1, 1, 1, 0,
                        0, 0, 1, 0,
                        0, 1, 0, 0
                };

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }


    public void initShader(MyTDView mv) {

        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());

        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());


        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);

        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");

        maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");

        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {

        GLES20.glUseProgram(mProgram);

        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);

        Matrix.translateM(mMMatrix, 0, 0, 0, 1);

        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 0, 0);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, Triangle.getFianlMatrix(mMMatrix), 0);

        GLES20.glVertexAttribPointer(
                maPositionHandle,
                3,
                GLES20.GL_FLOAT,
                false,
                3 * 4,
                mVertexBuffer
        );
        GLES20.glVertexAttribPointer
                (
                        maColorHandle,
                        4,
                        GLES20.GL_FLOAT,
                        false,
                        4 * 4,
                        mColorBuffer
                );

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }

    public static float[] getFianlMatrix(float[] spec) {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }
}
