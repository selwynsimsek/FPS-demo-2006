/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import javax.media.opengl.GL;
/*  6:   */ 
/*  7:   */ public class PolygonMesh
/*  8:   */   extends Shape3D
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   ArrayList<Polygon3D> polygons;
/* 12:   */   public Transform3D objectTransform;
/* 13:   */   private Sphere bounds;
/* 14:13 */   private static Sphere tmp1 = new Sphere();
/* 15:   */   
/* 16:   */   public PolygonMesh(Polygon3D[] paramArrayOfPolygon3D)
/* 17:   */   {
/* 18:16 */     this.polygons = new ArrayList();
/* 19:17 */     this.objectTransform = new Transform3D();
/* 20:18 */     for (int i = 0; i < paramArrayOfPolygon3D.length; i++) {
/* 21:20 */       this.polygons.add(paramArrayOfPolygon3D[i]);
/* 22:   */     }
/* 23:22 */     Sphere.calcBounds(this);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setBounds(Sphere paramSphere)
/* 27:   */   {
/* 28:26 */     this.bounds = paramSphere;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Transform3D getTransform3D()
/* 32:   */   {
/* 33:30 */     return this.objectTransform;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setTransform3D(Transform3D paramTransform3D)
/* 37:   */   {
/* 38:34 */     this.objectTransform = paramTransform3D;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void render(GL paramGL, Transform3D paramTransform3D, Plane[] paramArrayOfPlane, float[] paramArrayOfFloat)
/* 42:   */   {
/* 43:38 */     this.objectTransform.apply(paramGL);
/* 44:39 */     tmp1.setTo(this.bounds);
/* 45:40 */     float[] arrayOfFloat = new float[16];
/* 46:41 */     paramGL.glGetFloatv(2982, arrayOfFloat, 0);
/* 47:42 */     tmp1.apply(arrayOfFloat);
/* 48:43 */     if (tmp1.isVisible(paramTransform3D, paramArrayOfPlane))
/* 49:   */     {
/* 50:45 */       this.bounds.location.setTo(this.objectTransform.position);
/* 51:46 */       for (int i = 0; i < this.polygons.size(); i++) {
/* 52:48 */         ((Polygon3D)this.polygons.get(i)).render(paramGL, paramTransform3D, paramArrayOfPlane, arrayOfFloat);
/* 53:   */       }
/* 54:   */     }
/* 55:51 */     paramGL.glLoadIdentity();
/* 56:52 */     paramTransform3D.applySubtract(paramGL);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Object clone()
/* 60:   */   {
/* 61:56 */     Polygon3D[] arrayOfPolygon3D1 = new Polygon3D[this.polygons.size()];
/* 62:57 */     this.polygons.toArray(arrayOfPolygon3D1);
/* 63:58 */     Polygon3D[] arrayOfPolygon3D2 = new Polygon3D[arrayOfPolygon3D1.length];
/* 64:59 */     for (int i = 0; i < arrayOfPolygon3D2.length; i++) {
/* 65:61 */       arrayOfPolygon3D2[i] = ((Polygon3D)arrayOfPolygon3D1[i].clone());
/* 66:   */     }
/* 67:63 */     return new PolygonMesh(arrayOfPolygon3D2);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public Sphere getBounds()
/* 71:   */   {
/* 72:67 */     return this.bounds;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.PolygonMesh
 * JD-Core Version:    0.7.0.1
 */