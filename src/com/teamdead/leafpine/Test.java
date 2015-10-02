/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class Test
/*  6:   */ {
/*  7:   */   public static void main(String[] paramArrayOfString)
/*  8:   */   {
/*  9:   */     BoundingBox localBoundingBox;
/* 10:   */     Vector3D[] arrayOfVector3D;
/* 11:   */     int i;
/* 12:16 */     if (paramArrayOfString[0].equals("boxcalc"))
/* 13:   */     {
/* 14:18 */       localBoundingBox = Math3D.calculateBoundingBox(new Vector3D[] { new Vector3D(-1.0F, -2.0F, 1.0F), new Vector3D(1.0F, 0.0F, 2.0F), new Vector3D(2.0F, -1.0F, 3.0F), new Vector3D(2.0F, -1.0F, 2.0F) });
/* 15:19 */       System.out.println(localBoundingBox);
/* 16:20 */       arrayOfVector3D = localBoundingBox.getPoints();
/* 17:21 */       for (i = 0; i < arrayOfVector3D.length; i++) {
/* 18:23 */         System.out.println(arrayOfVector3D[i]);
/* 19:   */       }
/* 20:25 */       return;
/* 21:   */     }
/* 22:27 */     if (paramArrayOfString[0].equals("boxview"))
/* 23:   */     {
/* 24:31 */       localBoundingBox = Math3D.calculateBoundingBox(new Vector3D[] { new Vector3D(-0.0F, -0.0F, -100.0F), new Vector3D(-0.0F, -0.0F, -0.0F), new Vector3D(-0.0F, -0.0F, -0.0F), new Vector3D(-0.0F, -0.0F, -0.0F) });
/* 25:32 */       System.out.println(localBoundingBox);
/* 26:33 */       arrayOfVector3D = localBoundingBox.getPoints();
/* 27:34 */       for (i = 0; i < arrayOfVector3D.length; i++) {
/* 28:36 */         System.out.println(arrayOfVector3D[i]);
/* 29:   */       }
/* 30:38 */       System.out.println("result of test:" + localBoundingBox.isVisible(45.0F, 1.0F, 1.0F, 2.0F));
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Test
 * JD-Core Version:    0.7.0.1
 */