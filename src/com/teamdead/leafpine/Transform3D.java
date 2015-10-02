/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javax.media.opengl.GL;
/*  5:   */ 
/*  6:   */ public class Transform3D
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   public Vector3D position;
/* 10:   */   public float rotX;
/* 11:   */   public float rotY;
/* 12:   */   public float rotZ;
/* 13:   */   
/* 14:   */   public Transform3D()
/* 15:   */   {
/* 16:22 */     this.position = new Vector3D();
/* 17:23 */     this.rotX = 0.0F;
/* 18:24 */     this.rotY = 0.0F;
/* 19:25 */     this.rotZ = 0.0F;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Transform3D(Vector3D paramVector3D, float paramFloat1, float paramFloat2, float paramFloat3)
/* 23:   */   {
/* 24:29 */     this.position = ((Vector3D)paramVector3D.clone());
/* 25:30 */     this.rotX = paramFloat1;
/* 26:31 */     this.rotY = paramFloat2;
/* 27:32 */     this.rotZ = paramFloat3;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void apply(GL paramGL)
/* 31:   */   {
/* 32:37 */     paramGL.glTranslatef(this.position.x, this.position.y, this.position.z);
/* 33:38 */     paramGL.glRotatef(this.rotX, 1.0F, 0.0F, 0.0F);
/* 34:39 */     paramGL.glRotatef(this.rotY, 0.0F, 1.0F, 0.0F);
/* 35:40 */     paramGL.glRotatef(this.rotZ, 0.0F, 0.0F, 1.0F);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void applySubtract(GL paramGL)
/* 39:   */   {
/* 40:44 */     paramGL.glRotatef(360.0F - this.rotX, 1.0F, 0.0F, 0.0F);
/* 41:45 */     paramGL.glRotatef(360.0F - this.rotY, 0.0F, 1.0F, 0.0F);
/* 42:46 */     paramGL.glRotatef(360.0F - this.rotZ, 0.0F, 0.0F, 1.0F);
/* 43:47 */     paramGL.glTranslatef(-this.position.x, -this.position.y, -this.position.z);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setTo(Transform3D paramTransform3D)
/* 47:   */   {
/* 48:51 */     this.position.setTo(paramTransform3D.position);
/* 49:52 */     this.rotX = paramTransform3D.rotX;
/* 50:53 */     this.rotY = paramTransform3D.rotY;
/* 51:54 */     this.rotZ = paramTransform3D.rotZ;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Transform3D
 * JD-Core Version:    0.7.0.1
 */