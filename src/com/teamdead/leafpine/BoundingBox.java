/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import Jama.Matrix;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ public class BoundingBox
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   float[] plane1;
/*  10:    */   float[] plane2;
/*  11:    */   float[] plane3;
/*  12:    */   float[] plane4;
/*  13:    */   float[] plane5;
/*  14:    */   float[] plane6;
/*  15:    */   Vector3D centerPoint;
/*  16:    */   Vector3D point1;
/*  17:    */   Vector3D point2;
/*  18:    */   Vector3D point3;
/*  19:    */   Vector3D point4;
/*  20:    */   Vector3D point5;
/*  21:    */   Vector3D point6;
/*  22:    */   Vector3D point7;
/*  23:    */   Vector3D point8;
/*  24:    */   
/*  25:    */   public BoundingBox(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4, float[] paramArrayOfFloat5, float[] paramArrayOfFloat6)
/*  26:    */   {
/*  27: 10 */     this.plane1 = paramArrayOfFloat1;
/*  28: 11 */     this.plane2 = paramArrayOfFloat2;
/*  29: 12 */     this.plane3 = paramArrayOfFloat3;
/*  30: 13 */     this.plane4 = paramArrayOfFloat4;
/*  31: 14 */     this.plane5 = paramArrayOfFloat5;
/*  32: 15 */     this.plane6 = paramArrayOfFloat6;
/*  33: 16 */     this.point1 = planeIntersection(paramArrayOfFloat1, paramArrayOfFloat3, paramArrayOfFloat5);
/*  34: 17 */     this.point2 = planeIntersection(paramArrayOfFloat2, paramArrayOfFloat4, paramArrayOfFloat6);
/*  35: 18 */     this.point3 = planeIntersection(paramArrayOfFloat1, paramArrayOfFloat4, paramArrayOfFloat5);
/*  36: 19 */     this.point4 = planeIntersection(paramArrayOfFloat2, paramArrayOfFloat3, paramArrayOfFloat6);
/*  37: 20 */     this.point5 = planeIntersection(paramArrayOfFloat1, paramArrayOfFloat3, paramArrayOfFloat6);
/*  38: 21 */     this.point6 = planeIntersection(paramArrayOfFloat2, paramArrayOfFloat4, paramArrayOfFloat5);
/*  39: 22 */     this.point7 = planeIntersection(paramArrayOfFloat1, paramArrayOfFloat4, paramArrayOfFloat6);
/*  40: 23 */     this.point8 = planeIntersection(paramArrayOfFloat2, paramArrayOfFloat3, paramArrayOfFloat5);
/*  41: 24 */     this.centerPoint = new Vector3D();
/*  42: 25 */     this.centerPoint.x += this.point1.x + this.point2.x + this.point3.x + this.point4.x + this.point5.x + this.point6.x + this.point7.x + this.point8.x;
/*  43: 26 */     this.centerPoint.y += this.point1.y + this.point2.y + this.point3.y + this.point4.y + this.point5.y + this.point6.y + this.point7.y + this.point8.y;
/*  44: 27 */     this.centerPoint.z += this.point1.y + this.point2.z + this.point3.z + this.point4.z + this.point5.z + this.point6.z + this.point7.z + this.point8.z;
/*  45: 28 */     this.centerPoint.x /= 8.0F;
/*  46: 29 */     this.centerPoint.y /= 8.0F;
/*  47: 30 */     this.centerPoint.z /= 8.0F;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Vector3D getCenterPoint()
/*  51:    */   {
/*  52: 34 */     return this.centerPoint;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isVisible(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*  56:    */   {
/*  57: 38 */     return Math3D.isVisible(this, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public float getEffectiveRadius()
/*  61:    */   {
/*  62: 42 */     float f = 0.0F;
/*  63: 43 */     Vector3D localVector3D = new Vector3D();
/*  64: 44 */     localVector3D.setTo(this.point1);
/*  65: 45 */     localVector3D.subtract(this.centerPoint);
/*  66: 46 */     f = Math.max(f, localVector3D.length());
/*  67: 47 */     localVector3D.setTo(this.point2);
/*  68: 48 */     localVector3D.subtract(this.centerPoint);
/*  69: 49 */     f = Math.max(f, localVector3D.length());
/*  70: 50 */     localVector3D.setTo(this.point3);
/*  71: 51 */     localVector3D.subtract(this.centerPoint);
/*  72: 52 */     f = Math.max(f, localVector3D.length());
/*  73: 53 */     localVector3D.setTo(this.point4);
/*  74: 54 */     localVector3D.subtract(this.centerPoint);
/*  75: 55 */     f = Math.max(f, localVector3D.length());
/*  76: 56 */     localVector3D.setTo(this.point5);
/*  77: 57 */     localVector3D.subtract(this.centerPoint);
/*  78: 58 */     f = Math.max(f, localVector3D.length());
/*  79: 59 */     localVector3D.setTo(this.point6);
/*  80: 60 */     localVector3D.subtract(this.centerPoint);
/*  81: 61 */     f = Math.max(f, localVector3D.length());
/*  82: 62 */     localVector3D.setTo(this.point7);
/*  83: 63 */     localVector3D.subtract(this.centerPoint);
/*  84: 64 */     f = Math.max(f, localVector3D.length());
/*  85: 65 */     localVector3D.setTo(this.point8);
/*  86: 66 */     localVector3D.subtract(this.centerPoint);
/*  87: 67 */     return Math.max(f, localVector3D.length());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Vector3D planeIntersection(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3)
/*  91:    */   {
/*  92: 71 */     double[][] arrayOfDouble1 = new double[3][3];
/*  93: 72 */     arrayOfDouble1[0][0] = paramArrayOfFloat1[0];
/*  94: 73 */     arrayOfDouble1[0][1] = paramArrayOfFloat1[1];
/*  95: 74 */     arrayOfDouble1[0][2] = paramArrayOfFloat1[2];
/*  96: 75 */     arrayOfDouble1[1][0] = paramArrayOfFloat2[0];
/*  97: 76 */     arrayOfDouble1[1][1] = paramArrayOfFloat2[1];
/*  98: 77 */     arrayOfDouble1[1][2] = paramArrayOfFloat2[2];
/*  99: 78 */     arrayOfDouble1[2][0] = paramArrayOfFloat3[0];
/* 100: 79 */     arrayOfDouble1[2][1] = paramArrayOfFloat3[1];
/* 101: 80 */     arrayOfDouble1[2][2] = paramArrayOfFloat3[2];
/* 102: 81 */     Matrix localMatrix1 = new Matrix(arrayOfDouble1);
/* 103: 82 */     double[][] arrayOfDouble2 = new double[3][1];
/* 104: 83 */     arrayOfDouble2[0][0] = (-paramArrayOfFloat1[3]);
/* 105: 84 */     arrayOfDouble2[1][0] = (-paramArrayOfFloat2[3]);
/* 106: 85 */     arrayOfDouble2[2][0] = (-paramArrayOfFloat3[3]);
/* 107: 86 */     Matrix localMatrix2 = new Matrix(arrayOfDouble2);
/* 108: 87 */     Matrix localMatrix3 = localMatrix1.solve(localMatrix2);
/* 109: 88 */     double[][] arrayOfDouble3 = localMatrix3.getArray();
/* 110: 89 */     return new Vector3D((float)arrayOfDouble3[0][0], (float)arrayOfDouble3[1][0], (float)arrayOfDouble3[2][0]);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String toString()
/* 114:    */   {
/* 115: 93 */     String str1 = "plane1[0]=" + this.plane1[0] + "\nplane1[1]=" + this.plane1[1] + "\nplane1[2]=" + this.plane1[2] + "\nplane1[3]=" + this.plane1[3] + "\n";
/* 116: 94 */     String str2 = "plane2[0]=" + this.plane2[0] + "\nplane2[1]=" + this.plane2[1] + "\nplane2[2]=" + this.plane2[2] + "\nplane2[3]=" + this.plane2[3] + "\n";
/* 117: 95 */     String str3 = "plane3[0]=" + this.plane3[0] + "\nplane3[1]=" + this.plane3[1] + "\nplane3[2]=" + this.plane3[2] + "\nplane3[3]=" + this.plane3[3] + "\n";
/* 118: 96 */     String str4 = "plane4[0]=" + this.plane4[0] + "\nplane4[1]=" + this.plane4[1] + "\nplane4[2]=" + this.plane4[2] + "\nplane4[3]=" + this.plane4[3] + "\n";
/* 119: 97 */     String str5 = "plane5[0]=" + this.plane5[0] + "\nplane5[1]=" + this.plane5[1] + "\nplane5[2]=" + this.plane5[2] + "\nplane5[3]=" + this.plane5[3] + "\n";
/* 120: 98 */     String str6 = "plane6[0]=" + this.plane6[0] + "\nplane6[1]=" + this.plane6[1] + "\nplane6[2]=" + this.plane6[2] + "\nplane6[3]=" + this.plane6[3] + "\n";
/* 121: 99 */     return str1 + str2 + str3 + str4 + str5 + str6;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Vector3D[] getPoints()
/* 125:    */   {
/* 126:103 */     return new Vector3D[] { this.point1, this.point2, this.point3, this.point4, this.point5, this.point6, this.point7, this.point8 };
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.BoundingBox
 * JD-Core Version:    0.7.0.1
 */