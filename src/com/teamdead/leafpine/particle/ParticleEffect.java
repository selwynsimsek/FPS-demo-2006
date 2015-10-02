/*  1:   */ package com.teamdead.leafpine.particle;
/*  2:   */ 
/*  3:   */ import com.teamdead.leafpine.Vector3D;
/*  4:   */ 
/*  5:   */ public abstract class ParticleEffect
/*  6:   */ {
/*  7:   */   public abstract void apply(Particle paramParticle, long paramLong);
/*  8:   */   
/*  9:   */   public static class Gravity
/* 10:   */     extends ParticleEffect
/* 11:   */   {
/* 12:   */     private static final float GRAVITY = 1.6E-006F;
/* 13:   */     private float gravity;
/* 14:   */     
/* 15:   */     public Gravity()
/* 16:   */     {
/* 17:12 */       this.gravity = 1.6E-006F;
/* 18:   */     }
/* 19:   */     
/* 20:   */     public Gravity(float paramFloat)
/* 21:   */     {
/* 22:16 */       this.gravity = paramFloat;
/* 23:   */     }
/* 24:   */     
/* 25:   */     public void apply(Particle paramParticle, long paramLong)
/* 26:   */     {
/* 27:20 */       paramParticle.velocity.y -= this.gravity * (float)paramLong;
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.particle.ParticleEffect
 * JD-Core Version:    0.7.0.1
 */