/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import Jama.EigenvalueDecomposition;
/*   4:    */ import Jama.Matrix;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ 
/*   7:    */ public final class Math3D
/*   8:    */ {
/*   9:    */   public static final float EPSILON = 1.0E-010F;
/*  10:    */   public static final int MAX_SWEEPS = 2147483647;
/*  11:    */   
/*  12:    */   public static BoundingBox calculateBoundingBox(Vector3D[] paramArrayOfVector3D)
/*  13:    */   {
/*  14:  9 */     Vector3D localVector3D1 = new Vector3D();
/*  15: 10 */     for (int i = 0; i < paramArrayOfVector3D.length; i++)
/*  16:    */     {
/*  17: 12 */       localVector3D1.x += paramArrayOfVector3D[i].x;
/*  18: 13 */       localVector3D1.y += paramArrayOfVector3D[i].y;
/*  19: 14 */       localVector3D1.z += paramArrayOfVector3D[i].z;
/*  20:    */     }
/*  21: 16 */     localVector3D1.x /= paramArrayOfVector3D.length;
/*  22: 17 */     localVector3D1.y /= paramArrayOfVector3D.length;
/*  23: 18 */     localVector3D1.z /= paramArrayOfVector3D.length;
/*  24: 19 */     float[][] arrayOfFloat = new float[3][3];
/*  25: 20 */     for (int j = 0; j < paramArrayOfVector3D.length; j++)
/*  26:    */     {
/*  27: 22 */       float f1 = paramArrayOfVector3D[j].x - localVector3D1.x;
/*  28: 23 */       float f2 = paramArrayOfVector3D[j].y - localVector3D1.y;
/*  29: 24 */       float f3 = paramArrayOfVector3D[j].z - localVector3D1.z;
/*  30: 25 */       arrayOfFloat[0][0] += f1 * f1;
/*  31: 26 */       arrayOfFloat[1][1] += f2 * f2;
/*  32: 27 */       arrayOfFloat[2][2] += f3 * f3;
/*  33: 28 */       arrayOfFloat[0][1] += f1 * f2;
/*  34: 29 */       arrayOfFloat[0][2] += f1 * f3;
/*  35: 30 */       arrayOfFloat[1][2] += f2 * f3;
/*  36:    */     }
/*  37: 32 */     arrayOfFloat[1][2] /= paramArrayOfVector3D.length;
/*  38: 33 */     arrayOfFloat[2][1] = arrayOfFloat[1][2];
/*  39: 34 */     arrayOfFloat[0][2] /= paramArrayOfVector3D.length;
/*  40: 35 */     arrayOfFloat[2][0] = arrayOfFloat[0][2];
/*  41: 36 */     arrayOfFloat[0][1] /= paramArrayOfVector3D.length;
/*  42: 37 */     arrayOfFloat[1][0] = arrayOfFloat[0][1];
/*  43: 38 */     arrayOfFloat[1][1] /= paramArrayOfVector3D.length;
/*  44: 39 */     arrayOfFloat[2][2] /= paramArrayOfVector3D.length;
/*  45: 40 */     arrayOfFloat[0][0] /= paramArrayOfVector3D.length;
/*  46: 41 */     System.out.println(arrayOfFloat[0][0] + "\t" + arrayOfFloat[0][1] + "\t" + arrayOfFloat[0][2] + "\t");
/*  47: 42 */     System.out.println(arrayOfFloat[1][0] + "\t" + arrayOfFloat[1][1] + "\t" + arrayOfFloat[1][2] + "\t");
/*  48: 43 */     System.out.println(arrayOfFloat[2][0] + "\t" + arrayOfFloat[2][1] + "\t" + arrayOfFloat[2][2] + "\t");
/*  49: 44 */     double[][] arrayOfDouble1 = new double[3][3];
/*  50: 45 */     for (int k = 0; k < 3; k++) {
/*  51: 47 */       for (int m = 0; m < 3; m++) {
/*  52: 49 */         arrayOfDouble1[k][m] = arrayOfFloat[k][m];
/*  53:    */       }
/*  54:    */     }
/*  55: 52 */     Matrix localMatrix1 = new Matrix(arrayOfDouble1);
/*  56: 53 */     EigenvalueDecomposition localEigenvalueDecomposition = localMatrix1.eig();
/*  57: 54 */     Matrix localMatrix2 = localEigenvalueDecomposition.getV();
/*  58: 55 */     double[][] arrayOfDouble2 = localMatrix2.getArray();
/*  59: 56 */     Vector3D localVector3D2 = new Vector3D((float)arrayOfDouble2[0][0], (float)arrayOfDouble2[1][0], (float)arrayOfDouble2[2][0]);
/*  60: 57 */     Vector3D localVector3D3 = new Vector3D(-(float)arrayOfDouble2[0][1], -(float)arrayOfDouble2[1][1], -(float)arrayOfDouble2[2][1]);
/*  61: 58 */     Vector3D localVector3D4 = new Vector3D(-(float)arrayOfDouble2[0][2], -(float)arrayOfDouble2[1][2], -(float)arrayOfDouble2[2][2]);
/*  62: 59 */     System.out.println("r=" + localVector3D4);
/*  63: 60 */     System.out.println("s=" + localVector3D3);
/*  64: 61 */     System.out.println("t=" + localVector3D2);
/*  65: 62 */     float f4 = 0.0F;
/*  66: 63 */     float f5 = 0.0F;
/*  67: 64 */     float f6 = 0.0F;
/*  68: 65 */     float f7 = 0.0F;
/*  69: 66 */     float f8 = 0.0F;
/*  70: 67 */     float f9 = 0.0F;
/*  71: 68 */     for (int n = 0; n < paramArrayOfVector3D.length; n++)
/*  72:    */     {
/*  73: 70 */       float f10 = paramArrayOfVector3D[n].dotProduct(localVector3D4);
/*  74: 71 */       float f11 = paramArrayOfVector3D[n].dotProduct(localVector3D4);
/*  75: 72 */       float f12 = paramArrayOfVector3D[n].dotProduct(localVector3D4);
/*  76: 73 */       f4 = Math.min(f4, f10);
/*  77: 74 */       f5 = Math.min(f5, f11);
/*  78: 75 */       f6 = Math.min(f6, f12);
/*  79: 76 */       f7 = Math.max(f7, f10);
/*  80: 77 */       f8 = Math.max(f8, f11);
/*  81: 78 */       f9 = Math.max(f9, f12);
/*  82:    */     }
/*  83: 80 */     float[] arrayOfFloat1 = { localVector3D4.x, localVector3D4.y, localVector3D4.z, -f4 };
/*  84: 81 */     float[] arrayOfFloat2 = { -localVector3D4.x, -localVector3D4.y, -localVector3D4.z, f7 };
/*  85: 82 */     float[] arrayOfFloat3 = { localVector3D2.x, localVector3D2.y, localVector3D2.z, -f6 };
/*  86: 83 */     float[] arrayOfFloat4 = { -localVector3D2.x, -localVector3D2.y, -localVector3D2.z, f9 };
/*  87: 84 */     float[] arrayOfFloat5 = { localVector3D3.x, localVector3D3.y, localVector3D3.z, -f5 };
/*  88: 85 */     float[] arrayOfFloat6 = { -localVector3D3.x, -localVector3D3.y, -localVector3D3.z, f8 };
/*  89: 86 */     return new BoundingBox(arrayOfFloat1, arrayOfFloat2, arrayOfFloat3, arrayOfFloat4, arrayOfFloat5, arrayOfFloat6);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static boolean isVisible(BoundingBox paramBoundingBox, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*  93:    */   {
/*  94: 90 */     float f1 = (float)Math.sqrt(paramFloat1 * paramFloat1 + 1.0F);
/*  95: 91 */     float f2 = (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*  96: 92 */     float[] arrayOfFloat1 = { 0.0F, 0.0F, -1.0F, -paramFloat3 };
/*  97: 93 */     float[] arrayOfFloat2 = { 0.0F, 0.0F, 1.0F, paramFloat4 };
/*  98: 94 */     float[] arrayOfFloat3 = { paramFloat1 / f1, 0.0F, -1.0F / f1, 0.0F };
/*  99: 95 */     float[] arrayOfFloat4 = { -paramFloat1 / f1, 0.0F, -1.0F / f1, 0.0F };
/* 100: 96 */     float[] arrayOfFloat5 = { 0.0F, paramFloat1 / f2, -paramFloat2 / f2, 0.0F };
/* 101: 97 */     float[] arrayOfFloat6 = { 0.0F, -paramFloat1 / f2, -paramFloat2 / f2, 0.0F };
/* 102: 98 */     float f3 = paramBoundingBox.getEffectiveRadius();
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:105 */     Vector3D localVector3D = paramBoundingBox.getCenterPoint();
/* 110:106 */     float[] arrayOfFloat7 = { localVector3D.x, localVector3D.y, localVector3D.z, 1.0F };
/* 111:107 */     if (dotProduct(arrayOfFloat1, arrayOfFloat7) <= -f3) {
/* 112:107 */       return false;
/* 113:    */     }
/* 114:108 */     if (dotProduct(arrayOfFloat2, arrayOfFloat7) <= -f3) {
/* 115:108 */       return false;
/* 116:    */     }
/* 117:109 */     if (dotProduct(arrayOfFloat3, arrayOfFloat7) <= -f3) {
/* 118:109 */       return false;
/* 119:    */     }
/* 120:110 */     if (dotProduct(arrayOfFloat4, arrayOfFloat7) <= -f3) {
/* 121:110 */       return false;
/* 122:    */     }
/* 123:111 */     if (dotProduct(arrayOfFloat6, arrayOfFloat7) <= -f3) {
/* 124:111 */       return false;
/* 125:    */     }
/* 126:112 */     if (dotProduct(arrayOfFloat5, arrayOfFloat7) <= -f3) {
/* 127:112 */       return false;
/* 128:    */     }
/* 129:113 */     return true;
/* 130:    */   }
/* 131:    */   
/* 132:    */   private static float dotProduct(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/* 133:    */   {
/* 134:117 */     return paramArrayOfFloat1[0] * paramArrayOfFloat2[0] + paramArrayOfFloat1[1] * paramArrayOfFloat2[1] + paramArrayOfFloat1[2] * paramArrayOfFloat2[2] + paramArrayOfFloat1[3] * paramArrayOfFloat2[3];
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Math3D
 * JD-Core Version:    0.7.0.1
 */