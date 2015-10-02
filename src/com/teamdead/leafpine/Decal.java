/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import com.sun.opengl.util.texture.Texture;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import javax.media.opengl.GL;
/*  6:   */ 
/*  7:   */ public class Decal
/*  8:   */   extends Polygon3D
/*  9:   */ {
/* 10: 8 */   private static Vector3D tmp1 = new Vector3D();
/* 11: 9 */   private static ArrayList<Decal> decals = new ArrayList();
/* 12:   */   private static final float EPSILON = 0.001F;
/* 13:   */   private static final float MIN_DIST_SQ = 2.0F;
/* 14:11 */   private static final TextureCoord[] CORNERS = { new TextureCoord(0.0F, 0.0F), new TextureCoord(0.0F, 1.0F), new TextureCoord(1.0F, 1.0F), new TextureCoord(1.0F, 0.0F) };
/* 15:12 */   private static final Vector3D[] POINTS = { new Vector3D(-0.5F, 0.001F, -0.5F), new Vector3D(0.5F, 0.001F, -0.5F), new Vector3D(0.5F, 0.001F, 0.5F), new Vector3D(-0.5F, 0.001F, 0.5F) };
/* 16:   */   
/* 17:   */   public Decal(float paramFloat1, float paramFloat2, Texture paramTexture)
/* 18:   */   {
/* 19:15 */     this.texture = paramTexture;
/* 20:16 */     this.texCoords = CORNERS;
/* 21:17 */     this.numVertices = 4;
/* 22:18 */     this.vertices = new Vector3D[] { (Vector3D)POINTS[0].clone(), (Vector3D)POINTS[1].clone(), (Vector3D)POINTS[2].clone(), (Vector3D)POINTS[3].clone() };
/* 23:19 */     this.vertices[0].add(paramFloat1, 0.0F, paramFloat2);
/* 24:20 */     this.vertices[1].add(paramFloat1, 0.0F, paramFloat2);
/* 25:21 */     this.vertices[2].add(paramFloat1, 0.0F, paramFloat2);
/* 26:22 */     this.vertices[3].add(paramFloat1, 0.0F, paramFloat2);
/* 27:23 */     for (int i = 0; i < decals.size(); i++)
/* 28:   */     {
/* 29:25 */       Decal localDecal = (Decal)decals.get(i);
/* 30:26 */       if (intersects(this, localDecal)) {
/* 31:28 */         for (int j = 0; j < this.vertices.length; j++) {
/* 32:30 */           this.vertices[j].y += 0.001F;
/* 33:   */         }
/* 34:   */       }
/* 35:   */     }
/* 36:34 */     decals.add(this);
/* 37:   */   }
/* 38:   */   
/* 39:   */   private static boolean intersects(Decal paramDecal1, Decal paramDecal2)
/* 40:   */   {
/* 41:38 */     tmp1.setTo(paramDecal1.vertices[0]);
/* 42:39 */     tmp1.subtract(paramDecal2.vertices[0]);
/* 43:40 */     float f = tmp1.lengthSquared();
/* 44:41 */     return f < 2.0F;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void render(GL paramGL, Transform3D paramTransform3D, Plane[] paramArrayOfPlane, float[] paramArrayOfFloat)
/* 48:   */   {
/* 49:45 */     paramGL.glEnable(3042);
/* 50:46 */     paramGL.glBlendFunc(770, 771);
/* 51:47 */     super.render(paramGL, paramTransform3D, paramArrayOfPlane, paramArrayOfFloat);
/* 52:48 */     paramGL.glDisable(3042);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Decal
 * JD-Core Version:    0.7.0.1
 */