package com.mer.opengl.openglex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.mer.opengl.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureBuffer;
    private int mImages = 6;
    private int[] mImageResID = {R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6};
    private int[] mTextTureIDs = new int[mImages];
    private Bitmap[] mBitmaps = new Bitmap[mImages];
    private float mHalfSize = 1.2f;

    public Cube(Context context) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(12 * 4 * mImages);
        byteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuffer.asFloatBuffer();

        for (int img = 0; img < mImages; img++) {
            mBitmaps[img] = BitmapFactory.decodeStream(
                    context.getResources().openRawResource(mImageResID[img]));
            int imgWidth = mBitmaps[img].getWidth();
            int imgHeight = mBitmaps[img].getHeight();
            float width = 2.0f;
            float height = 2.0f;

            if (imgWidth > imgHeight) {
                height = height * imgHeight / imgWidth;
            } else {
                width = width * imgWidth / imgHeight;
            }
            float left = -width / 2;
            float right = -left;
            float top = height / 2;
            float bottom = -top;

            float[] vertices = {
                    left, bottom, 0.0f,
                    right, bottom, 0.0f,
                    left, top, 0.0f,
                    right, top, 0.0f,
            };
            mVertexBuffer.put(vertices);
        }
        mVertexBuffer.position(0);

        float[] texCoords = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };
        ByteBuffer texByteBuffer = ByteBuffer.allocateDirect(texCoords.length * 4 * mImages);
        texByteBuffer.order(ByteOrder.nativeOrder());
        mTextureBuffer = texByteBuffer.asFloatBuffer();
        for (int face = 0; face < mImages; face++) {
            mTextureBuffer.put(texCoords);
        }
        mTextureBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

        gl.glPushMatrix();
        gl.glTranslatef(0f, 0f, mHalfSize);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[0]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(270.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, mHalfSize);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[1]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, mHalfSize);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[2]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, mHalfSize);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(270.0f, 1f, 0f, 0f);
        gl.glTranslatef(0f, 0f, mHalfSize);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[4]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90.0f, 1f, 0f, 0f);
        gl.glTranslatef(0f, 0f, mHalfSize);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[5]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    public void loadSurface(GL10 gl) {
        gl.glGenTextures(6, mTextTureIDs, 0);

        for (int img = 0; img < mImages; img++) {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextTureIDs[img]);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmaps[img], 0);
            mBitmaps[img].recycle();
        }
    }
}
