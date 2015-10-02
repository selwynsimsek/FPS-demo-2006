/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.nio.FloatBuffer;
/*  5:   */ import javax.media.opengl.GL;
/*  6:   */ 
/*  7:   */ public class Light3D
/*  8:   */   extends Vector3D
/*  9:   */ {
/* 10:   */   public Color lightColour;
/* 11:   */   
/* 12:   */   public Light3D() {}
/* 13:   */   
/* 14:   */   public Light3D(Color paramColor)
/* 15:   */   {
/* 16:15 */     this.lightColour = paramColor;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected void setup(GL paramGL, int paramInt)
/* 20:   */   {
/* 21:19 */     paramGL.glEnable(paramInt);
/* 22:20 */     paramGL.glLightfv(paramInt, 4611, FloatBuffer.wrap(new float[] { this.x, this.y, this.z, 1.0F }, 0, 4));
/* 23:21 */     paramGL.glLighti(paramInt, 4609, this.lightColour.getRGB());
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Light3D
 * JD-Core Version:    0.7.0.1
 */