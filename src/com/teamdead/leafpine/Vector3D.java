/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class Vector3D
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   public float x;
/*   9:    */   public float y;
/*  10:    */   public float z;
/*  11:    */   
/*  12:    */   public Vector3D() {}
/*  13:    */   
/*  14:    */   public Vector3D(float paramFloat1, float paramFloat2, float paramFloat3)
/*  15:    */   {
/*  16: 20 */     this.x = paramFloat1;
/*  17: 21 */     this.y = paramFloat2;
/*  18: 22 */     this.z = paramFloat3;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Vector3D(Vector3D paramVector3D)
/*  22:    */   {
/*  23: 27 */     this(paramVector3D.x, paramVector3D.y, paramVector3D.z);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setTo(Vector3D paramVector3D)
/*  27:    */   {
/*  28: 32 */     this.x = paramVector3D.x;
/*  29: 33 */     this.y = paramVector3D.y;
/*  30: 34 */     this.z = paramVector3D.z;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setTo(float paramFloat1, float paramFloat2, float paramFloat3)
/*  34:    */   {
/*  35: 38 */     this.x = paramFloat1;
/*  36: 39 */     this.y = paramFloat2;
/*  37: 40 */     this.z = paramFloat3;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String toString()
/*  41:    */   {
/*  42: 44 */     return "x=" + this.x + " y=" + this.y + " z=" + this.z;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public float dotProduct(Vector3D paramVector3D)
/*  46:    */   {
/*  47: 49 */     return this.x * paramVector3D.x + this.y * paramVector3D.y + this.z * paramVector3D.z;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Vector3D crossProduct(Vector3D paramVector3D)
/*  51:    */   {
/*  52: 54 */     float f1 = this.x * paramVector3D.y - this.y * paramVector3D.x;
/*  53: 55 */     float f2 = this.y * paramVector3D.z - this.z * paramVector3D.y;
/*  54: 56 */     float f3 = this.z * paramVector3D.x - this.x * paramVector3D.z;
/*  55: 57 */     return new Vector3D(f2, f3, f1);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void crossProduct(Vector3D paramVector3D1, Vector3D paramVector3D2)
/*  59:    */   {
/*  60: 62 */     this.z = (paramVector3D1.x * paramVector3D2.y - paramVector3D1.y * paramVector3D2.x);
/*  61: 63 */     this.x = (paramVector3D1.y * paramVector3D2.z - paramVector3D1.z * paramVector3D2.y);
/*  62: 64 */     this.y = (paramVector3D1.z * paramVector3D2.x - paramVector3D1.x * paramVector3D2.z);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public float lengthSquared()
/*  66:    */   {
/*  67: 69 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public float length()
/*  71:    */   {
/*  72: 74 */     return (float)Math.sqrt(lengthSquared());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void normalize()
/*  76:    */   {
/*  77: 79 */     divide(length());
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void divide(float paramFloat)
/*  81:    */   {
/*  82: 84 */     this.x /= paramFloat;
/*  83: 85 */     this.y /= paramFloat;
/*  84: 86 */     this.z /= paramFloat;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void multiply(float paramFloat)
/*  88:    */   {
/*  89: 91 */     this.x *= paramFloat;
/*  90: 92 */     this.y *= paramFloat;
/*  91: 93 */     this.z *= paramFloat;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void add(Vector3D paramVector3D)
/*  95:    */   {
/*  96: 98 */     this.x += paramVector3D.x;
/*  97: 99 */     this.y += paramVector3D.y;
/*  98:100 */     this.z += paramVector3D.z;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void add(float paramFloat1, float paramFloat2, float paramFloat3)
/* 102:    */   {
/* 103:104 */     this.x += paramFloat1;
/* 104:105 */     this.y += paramFloat2;
/* 105:106 */     this.z += paramFloat3;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void subtract(Vector3D paramVector3D)
/* 109:    */   {
/* 110:111 */     this.x -= paramVector3D.x;
/* 111:112 */     this.y -= paramVector3D.y;
/* 112:113 */     this.z -= paramVector3D.z;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Object clone()
/* 116:    */   {
/* 117:118 */     return new Vector3D(this.x, this.y, this.z);
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Vector3D
 * JD-Core Version:    0.7.0.1
 */