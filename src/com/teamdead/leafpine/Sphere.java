/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ 
/*   6:    */ public class Sphere
/*   7:    */ {
/*   8:  6 */   private static Vector3D tmp1 = new Vector3D();
/*   9:    */   float radius;
/*  10:    */   float radiusSq;
/*  11:    */   public Vector3D location;
/*  12:    */   
/*  13:    */   public Sphere(float paramFloat, Vector3D paramVector3D)
/*  14:    */   {
/*  15: 11 */     this.radius = paramFloat;
/*  16: 12 */     this.location = paramVector3D;
/*  17: 13 */     this.radiusSq = (paramFloat * paramFloat);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Sphere()
/*  21:    */   {
/*  22: 17 */     this.location = new Vector3D();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setRadius(float paramFloat)
/*  26:    */   {
/*  27: 21 */     this.radius = paramFloat;
/*  28: 22 */     this.radiusSq = (paramFloat * paramFloat);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void apply(float[] paramArrayOfFloat)
/*  32:    */   {
/*  33: 26 */     this.location.setTo(paramArrayOfFloat[12], paramArrayOfFloat[13], paramArrayOfFloat[14]);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setTo(Sphere paramSphere)
/*  37:    */   {
/*  38: 30 */     this.radius = paramSphere.radius;
/*  39: 31 */     this.radiusSq = paramSphere.radiusSq;
/*  40: 32 */     this.location.setTo(paramSphere.location);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private static final void printMatrix(float[] paramArrayOfFloat)
/*  44:    */   {
/*  45: 36 */     System.out.println(paramArrayOfFloat[0] + "\t\t" + paramArrayOfFloat[4] + "\t\t" + paramArrayOfFloat[8] + "\t\t" + paramArrayOfFloat[12]);
/*  46: 37 */     System.out.println(paramArrayOfFloat[1] + "\t\t" + paramArrayOfFloat[5] + "\t\t" + paramArrayOfFloat[9] + "\t\t" + paramArrayOfFloat[13]);
/*  47: 38 */     System.out.println(paramArrayOfFloat[2] + "\t\t" + paramArrayOfFloat[6] + "\t\t" + paramArrayOfFloat[10] + "\t\t" + paramArrayOfFloat[14]);
/*  48: 39 */     System.out.println(paramArrayOfFloat[3] + "\t\t" + paramArrayOfFloat[7] + "\t\t" + paramArrayOfFloat[11] + "\t\t" + paramArrayOfFloat[15]);
/*  49: 40 */     System.out.println();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setRadiusSq(float paramFloat)
/*  53:    */   {
/*  54: 44 */     this.radiusSq = paramFloat;
/*  55: 45 */     this.radius = ((float)Math.sqrt(paramFloat));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void translate(Vector3D paramVector3D)
/*  59:    */   {
/*  60: 49 */     this.location.add(paramVector3D);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static Vector3D[] getPoints(PolygonMesh paramPolygonMesh)
/*  64:    */   {
/*  65: 53 */     ArrayList localArrayList = new ArrayList();
/*  66: 54 */     for (int i = 0; i < paramPolygonMesh.polygons.size(); i++)
/*  67:    */     {
/*  68: 56 */       Polygon3D localPolygon3D = (Polygon3D)paramPolygonMesh.polygons.get(i);
/*  69: 57 */       Vector3D[] arrayOfVector3D2 = localPolygon3D.getCoords();
/*  70: 58 */       for (int j = 0; j < arrayOfVector3D2.length; j++) {
/*  71: 60 */         localArrayList.add(arrayOfVector3D2[j]);
/*  72:    */       }
/*  73:    */     }
/*  74: 63 */     Vector3D[] arrayOfVector3D1 = new Vector3D[localArrayList.size()];
/*  75: 64 */     localArrayList.toArray(arrayOfVector3D1);
/*  76: 65 */     return arrayOfVector3D1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void calcBounds(PolygonMesh paramPolygonMesh)
/*  80:    */   {
/*  81: 69 */     Vector3D[] arrayOfVector3D = getPoints(paramPolygonMesh);
/*  82: 70 */     float f = 0.0F;
/*  83: 71 */     for (int i = 0; i < arrayOfVector3D.length; i++) {
/*  84: 73 */       f = Math.max(f, arrayOfVector3D[i].lengthSquared());
/*  85:    */     }
/*  86: 75 */     Sphere localSphere = new Sphere();
/*  87: 76 */     localSphere.setRadiusSq(f);
/*  88: 77 */     localSphere.location = paramPolygonMesh.getTransform3D().position;
/*  89: 78 */     paramPolygonMesh.setBounds(localSphere);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String toString()
/*  93:    */   {
/*  94: 82 */     return "radius: " + this.radius + "\n location :" + this.location.toString();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean intersects(Sphere paramSphere)
/*  98:    */   {
/*  99: 86 */     float f1 = this.radius + paramSphere.radius;
/* 100: 87 */     float f2 = f1 * f1;
/* 101: 88 */     tmp1.setTo(paramSphere.location);
/* 102: 89 */     tmp1.subtract(this.location);
/* 103: 90 */     float f3 = tmp1.lengthSquared();
/* 104: 91 */     return f3 <= f2;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void expand(Vector3D paramVector3D)
/* 108:    */   {
/* 109: 95 */     float f = paramVector3D.lengthSquared();
/* 110: 96 */     if (this.radiusSq < f)
/* 111:    */     {
/* 112: 98 */       this.radiusSq = f;
/* 113: 99 */       this.radius = ((float)Math.sqrt(this.radiusSq));
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isVisible(Transform3D paramTransform3D, Plane[] paramArrayOfPlane)
/* 118:    */   {
/* 119:104 */     if (paramArrayOfPlane == null) {
/* 120:104 */       return true;
/* 121:    */     }
/* 122:105 */     for (int i = 0; i < paramArrayOfPlane.length; i++)
/* 123:    */     {
/* 124:107 */       float f = paramArrayOfPlane[i].dotProduct(this.location, 0);
/* 125:108 */       if (f <= -this.radius) {
/* 126:108 */         return false;
/* 127:    */       }
/* 128:    */     }
/* 129:110 */     return true;
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Sphere
 * JD-Core Version:    0.7.0.1
 */