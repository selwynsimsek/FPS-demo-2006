/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class TextureCoord
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   public float s;
/*  9:   */   public float t;
/* 10:   */   
/* 11:   */   public TextureCoord()
/* 12:   */   {
/* 13: 8 */     this.s = 0.0F;
/* 14: 9 */     this.t = 0.0F;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public TextureCoord(float paramFloat1, float paramFloat2)
/* 18:   */   {
/* 19:13 */     this.s = paramFloat1;
/* 20:14 */     this.t = paramFloat2;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object clone()
/* 24:   */   {
/* 25:18 */     return new TextureCoord(this.s, this.t);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.TextureCoord
 * JD-Core Version:    0.7.0.1
 */