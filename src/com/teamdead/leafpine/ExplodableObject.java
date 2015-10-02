/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import com.teamdead.leafpine.particle.ParticleSystem;
/*  4:   */ import java.awt.Color;
/*  5:   */ 
/*  6:   */ public class ExplodableObject
/*  7:   */ {
/*  8:   */   private PolygonMesh mesh;
/*  9:   */   private Color[] particleColours;
/* 10:   */   
/* 11:   */   public ExplodableObject(PolygonMesh paramPolygonMesh, Color[] paramArrayOfColor)
/* 12:   */   {
/* 13:12 */     this.mesh = paramPolygonMesh;
/* 14:13 */     this.particleColours = paramArrayOfColor;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ParticleSystem explode(Renderer3D paramRenderer3D)
/* 18:   */   {
/* 19:17 */     paramRenderer3D.removePolygon(this.mesh);
/* 20:18 */     this.mesh.getTransform3D().position.y = 0.0F;
/* 21:19 */     ParticleSystem localParticleSystem = new ParticleSystem(this.mesh.getTransform3D().position, this.particleColours);
/* 22:20 */     paramRenderer3D.addPolygon(localParticleSystem);
/* 23:21 */     return localParticleSystem;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object clone()
/* 27:   */   {
/* 28:25 */     ExplodableObject localExplodableObject = new ExplodableObject((PolygonMesh)this.mesh.clone(), this.particleColours);
/* 29:26 */     return localExplodableObject;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public PolygonMesh getMesh()
/* 33:   */   {
/* 34:30 */     return this.mesh;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.ExplodableObject
 * JD-Core Version:    0.7.0.1
 */