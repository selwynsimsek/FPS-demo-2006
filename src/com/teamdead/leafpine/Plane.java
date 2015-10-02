/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ public class Plane
/*  4:   */ {
/*  5:   */   public static final int POINT = 0;
/*  6:   */   public static final int DIRECTION = 1;
/*  7:   */   public float x;
/*  8:   */   public float y;
/*  9:   */   public float z;
/* 10:   */   public float w;
/* 11:   */   
/* 12:   */   public Plane(Plane paramPlane)
/* 13:   */   {
/* 14: 9 */     setTo(paramPlane);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Plane(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/* 18:   */   {
/* 19:13 */     setTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Plane() {}
/* 23:   */   
/* 24:   */   public float dotProduct(Vector3D paramVector3D, int paramInt)
/* 25:   */   {
/* 26:18 */     if (paramInt == 0) {
/* 27:20 */       return this.x * paramVector3D.x + this.y * paramVector3D.y + this.z * paramVector3D.z + this.w;
/* 28:   */     }
/* 29:22 */     if (paramInt == 1) {
/* 30:24 */       return this.x * paramVector3D.x + paramVector3D.y * this.y + paramVector3D.z * this.z;
/* 31:   */     }
/* 32:26 */     return 3.4028235E+38F;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setTo(Plane paramPlane)
/* 36:   */   {
/* 37:30 */     setTo(paramPlane.x, paramPlane.y, paramPlane.z, paramPlane.w);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/* 41:   */   {
/* 42:34 */     this.x = paramFloat1;
/* 43:35 */     this.y = paramFloat2;
/* 44:36 */     this.z = paramFloat3;
/* 45:37 */     this.w = paramFloat4;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static Plane createPlane(Polygon3D paramPolygon3D)
/* 49:   */   {
/* 50:41 */     Plane localPlane = new Plane();
/* 51:42 */     Vector3D localVector3D1 = paramPolygon3D.getNormal();
/* 52:43 */     Vector3D localVector3D2 = paramPolygon3D.getCoord(0);
/* 53:44 */     localPlane.setTo(localVector3D1.x, localVector3D1.y, localVector3D1.z, -localVector3D1.dotProduct(localVector3D2));
/* 54:45 */     return localPlane;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void normalize()
/* 58:   */   {
/* 59:49 */     float f = (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 60:50 */     this.x /= f;
/* 61:51 */     this.y /= f;
/* 62:52 */     this.z /= f;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static Plane[] createViewFrustum(float[] paramArrayOfFloat)
/* 66:   */   {
/* 67:56 */     Plane[] arrayOfPlane = new Plane[6];
/* 68:57 */     arrayOfPlane[0] = new Plane(paramArrayOfFloat[3] + paramArrayOfFloat[0], paramArrayOfFloat[7] + paramArrayOfFloat[4], paramArrayOfFloat[11] + paramArrayOfFloat[8], paramArrayOfFloat[15] + paramArrayOfFloat[12]);
/* 69:58 */     arrayOfPlane[1] = new Plane(paramArrayOfFloat[3] - paramArrayOfFloat[0], paramArrayOfFloat[7] - paramArrayOfFloat[4], paramArrayOfFloat[11] - paramArrayOfFloat[8], paramArrayOfFloat[15] - paramArrayOfFloat[12]);
/* 70:59 */     arrayOfPlane[2] = new Plane(paramArrayOfFloat[3] + paramArrayOfFloat[1], paramArrayOfFloat[7] + paramArrayOfFloat[5], paramArrayOfFloat[11] + paramArrayOfFloat[9], paramArrayOfFloat[15] + paramArrayOfFloat[13]);
/* 71:60 */     arrayOfPlane[3] = new Plane(paramArrayOfFloat[3] - paramArrayOfFloat[1], paramArrayOfFloat[7] - paramArrayOfFloat[5], paramArrayOfFloat[11] - paramArrayOfFloat[9], paramArrayOfFloat[15] - paramArrayOfFloat[13]);
/* 72:61 */     arrayOfPlane[4] = new Plane(paramArrayOfFloat[3] + paramArrayOfFloat[2], paramArrayOfFloat[7] + paramArrayOfFloat[6], paramArrayOfFloat[11] + paramArrayOfFloat[10], paramArrayOfFloat[15] + paramArrayOfFloat[14]);
/* 73:62 */     arrayOfPlane[5] = new Plane(paramArrayOfFloat[3] - paramArrayOfFloat[2], paramArrayOfFloat[7] - paramArrayOfFloat[6], paramArrayOfFloat[11] - paramArrayOfFloat[10], paramArrayOfFloat[15] - paramArrayOfFloat[14]);
/* 74:63 */     arrayOfPlane[0].normalize();
/* 75:64 */     arrayOfPlane[1].normalize();
/* 76:65 */     arrayOfPlane[2].normalize();
/* 77:66 */     arrayOfPlane[3].normalize();
/* 78:67 */     arrayOfPlane[4].normalize();
/* 79:68 */     arrayOfPlane[5].normalize();
/* 80:69 */     return arrayOfPlane;
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Plane
 * JD-Core Version:    0.7.0.1
 */