/*  1:   */ import com.teamdead.leafpine.Polygon3D;
/*  2:   */ import com.teamdead.leafpine.Vector3D;
/*  3:   */ 
/*  4:   */ public class CollisionDetection
/*  5:   */ {
/*  6: 6 */   Vector3D temp1 = new Vector3D();
/*  7:   */   public static final float MIN_WALL_DIST = 1.5F;
/*  8:   */   public static final float MIN_WALL_DIST_SQ = 2.25F;
/*  9:   */   public static final float EPSILON = 0.001F;
/* 10:   */   
/* 11:   */   public Object[] intersects(Vector3D paramVector3D1, Vector3D paramVector3D2, Polygon3D[] paramArrayOfPolygon3D)
/* 12:   */   {
/* 13:10 */     for (int i = 0; i < paramArrayOfPolygon3D.length; i++)
/* 14:   */     {
/* 15:12 */       Vector3D localVector3D2 = paramArrayOfPolygon3D[i].getCoord(0);
/* 16:13 */       float f1 = paramVector3D2.x - localVector3D2.x;
/* 17:14 */       float f2 = paramVector3D2.z - localVector3D2.z;
/* 18:15 */       if (f1 * f1 + f2 * f2 < 2.25F) {
/* 19:15 */         return new Object[] { null, paramArrayOfPolygon3D[i] };
/* 20:   */       }
/* 21:   */     }
/* 22:17 */     Vector3D localVector3D1 = new Vector3D(paramVector3D2);
/* 23:18 */     localVector3D1.subtract(paramVector3D1);
/* 24:19 */     for (int j = 0; j < paramArrayOfPolygon3D.length; j++) {
/* 25:21 */       if (paramArrayOfPolygon3D[j] != null)
/* 26:   */       {
/* 27:22 */         Polygon3D localPolygon3D = paramArrayOfPolygon3D[j];
/* 28:23 */         float[] arrayOfFloat = createPlane(localPolygon3D);
/* 29:24 */         arrayOfFloat[3] -= 1.5F;
/* 30:25 */         float f3 = dotProduct(arrayOfFloat, paramVector3D1, 1.0F);
/* 31:26 */         float f4 = dotProduct(arrayOfFloat, localVector3D1, 0.0F);
/* 32:27 */         if (f4 != 0.0F)
/* 33:   */         {
/* 34:28 */           float f5 = -f3 / f4;
/* 35:29 */           if ((f5 >= 0.0F) && (f5 <= 1.0F))
/* 36:   */           {
/* 37:30 */             Vector3D localVector3D3 = new Vector3D(localVector3D1);
/* 38:31 */             localVector3D3.multiply(f5);
/* 39:32 */             localVector3D3.add(paramVector3D1);
/* 40:33 */             Vector3D localVector3D4 = new Vector3D(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
/* 41:34 */             localVector3D4.multiply(1.5F);
/* 42:35 */             localVector3D3.subtract(localVector3D4);
/* 43:36 */             if (pointInPoly(localVector3D3, localPolygon3D)) {
/* 44:36 */               return new Object[] { localVector3D3, localPolygon3D };
/* 45:   */             }
/* 46:   */           }
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:38 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   private float dotProduct(float[] paramArrayOfFloat, Vector3D paramVector3D, float paramFloat)
/* 54:   */   {
/* 55:42 */     return paramArrayOfFloat[0] * paramVector3D.x + paramArrayOfFloat[1] * paramVector3D.y + paramArrayOfFloat[2] * paramVector3D.z + paramArrayOfFloat[3] * paramFloat;
/* 56:   */   }
/* 57:   */   
/* 58:   */   private float[] createPlane(Polygon3D paramPolygon3D)
/* 59:   */   {
/* 60:46 */     Vector3D localVector3D = paramPolygon3D.getNormal();
/* 61:47 */     return new float[] { localVector3D.x, localVector3D.y, localVector3D.z, -dotProduct(dirToPlane(localVector3D), paramPolygon3D.getCoord(0), 1.0F) };
/* 62:   */   }
/* 63:   */   
/* 64:   */   private float[] dirToPlane(Vector3D paramVector3D)
/* 65:   */   {
/* 66:51 */     return new float[] { paramVector3D.x, paramVector3D.y, paramVector3D.z, 0.0F };
/* 67:   */   }
/* 68:   */   
/* 69:   */   private boolean pointInPoly(Vector3D paramVector3D, Polygon3D paramPolygon3D)
/* 70:   */   {
/* 71:55 */     Vector3D[] arrayOfVector3D = paramPolygon3D.getCoords();
/* 72:56 */     Vector3D localVector3D1 = new Vector3D();
/* 73:57 */     Vector3D localVector3D2 = new Vector3D();
/* 74:58 */     float f1 = 0.0F;
/* 75:59 */     for (int i = 0; i < arrayOfVector3D.length; i++)
/* 76:   */     {
/* 77:61 */       localVector3D1.setTo(arrayOfVector3D[i]);
/* 78:62 */       if (i != arrayOfVector3D.length - 1) {
/* 79:62 */         localVector3D2.setTo(arrayOfVector3D[(i + 1)]);
/* 80:   */       } else {
/* 81:63 */         localVector3D2.setTo(arrayOfVector3D[0]);
/* 82:   */       }
/* 83:64 */       localVector3D1.subtract(paramVector3D);
/* 84:65 */       localVector3D2.subtract(paramVector3D);
/* 85:66 */       localVector3D1.normalize();
/* 86:67 */       localVector3D2.normalize();
/* 87:68 */       float f2 = localVector3D1.dotProduct(localVector3D2);
/* 88:69 */       f1 += (float)Math.acos(f2);
/* 89:   */     }
/* 90:71 */     f1 -= 6.283186F;
/* 91:72 */     return (f1 < 0.001F) && (f1 > -0.001F);
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\code.jar
 * Qualified Name:     CollisionDetection
 * JD-Core Version:    0.7.0.1
 */