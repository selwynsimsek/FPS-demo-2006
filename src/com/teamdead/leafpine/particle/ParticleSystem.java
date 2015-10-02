/*   1:    */ package com.teamdead.leafpine.particle;
/*   2:    */ 
/*   3:    */ import com.teamdead.leafpine.Plane;
/*   4:    */ import com.teamdead.leafpine.Shape3D;
/*   5:    */ import com.teamdead.leafpine.Sphere;
/*   6:    */ import com.teamdead.leafpine.Transform3D;
/*   7:    */ import com.teamdead.leafpine.Vector3D;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.media.opengl.GL;
/*  12:    */ 
/*  13:    */ public class ParticleSystem
/*  14:    */   extends Shape3D
/*  15:    */ {
/*  16:    */   private static final int NUM_PARTICLES = 4000;
/*  17:    */   private static final long LIFETIME = 500L;
/*  18:    */   private long totalTime;
/*  19:    */   private Sphere sphere;
/*  20:    */   private Transform3D camera;
/*  21: 15 */   private static Sphere tmp1 = new Sphere();
/*  22:    */   private Vector3D epicentre;
/*  23: 17 */   private boolean ended = false;
/*  24: 18 */   private static final Transform3D BLANK_TRANSFORM = new Transform3D();
/*  25:    */   private Color[] particleColors;
/*  26: 20 */   private ArrayList<ParticleEffect> forces = new ArrayList();
/*  27:    */   private Particle[] particles;
/*  28:    */   
/*  29:    */   public ParticleSystem(Vector3D paramVector3D, Color[] paramArrayOfColor)
/*  30:    */   {
/*  31: 24 */     System.out.println("epicentre: " + paramVector3D);
/*  32: 25 */     addForce(new ParticleEffect.Gravity());
/*  33: 26 */     this.epicentre = paramVector3D;
/*  34: 27 */     this.particleColors = paramArrayOfColor;
/*  35: 28 */     spawnParticles();
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void spawnParticles()
/*  39:    */   {
/*  40: 32 */     this.particles = new Particle[4000];
/*  41: 34 */     for (int i = 0; i < this.particles.length; i++) {
/*  42: 36 */       this.particles[i] = new Particle(this.epicentre, randomVector(), randomColour());
/*  43:    */     }
/*  44: 38 */     this.sphere = new Sphere(25.0F, this.epicentre);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private Vector3D randomVector()
/*  48:    */   {
/*  49: 42 */     float f1 = (float)Math.random();
/*  50: 43 */     float f2 = (float)Math.random();
/*  51: 44 */     float f3 = (float)Math.random();
/*  52: 45 */     float f4 = f1 * 2.0F - 1.0F;
/*  53: 46 */     float f5 = f2;
/*  54: 47 */     float f6 = f3 * 2.0F - 1.0F;
/*  55: 48 */     Vector3D localVector3D = new Vector3D(f4, f5, f6);
/*  56: 49 */     localVector3D.normalize();
/*  57: 50 */     localVector3D.multiply(random(0.5F, 1.0F));
/*  58: 51 */     localVector3D.divide(250.0F);
/*  59: 52 */     return localVector3D;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private float random(float paramFloat1, float paramFloat2)
/*  63:    */   {
/*  64: 56 */     return (float)Math.random() * (paramFloat2 - paramFloat1) + paramFloat1;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private Color randomColour()
/*  68:    */   {
/*  69: 60 */     int i = (int)(this.particleColors.length * Math.random());
/*  70: 61 */     return this.particleColors[i];
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void update(long paramLong)
/*  74:    */   {
/*  75: 65 */     synchronized (this.particles)
/*  76:    */     {
/*  77: 67 */       boolean bool = true;
/*  78: 68 */       for (int i = 0; i < this.particles.length; i++) {
/*  79: 70 */         if (this.particles[i] != null)
/*  80:    */         {
/*  81: 72 */           bool = false;
/*  82:    */           
/*  83:    */ 
/*  84: 75 */           this.particles[i].update(paramLong);
/*  85: 76 */           for (int j = 0; j < this.forces.size(); j++) {
/*  86: 78 */             ((ParticleEffect)this.forces.get(j)).apply(this.particles[i], paramLong);
/*  87:    */           }
/*  88: 80 */           if (this.particles[i].shouldRemove()) {
/*  89: 82 */             this.particles[i] = null;
/*  90:    */           }
/*  91:    */         }
/*  92:    */       }
/*  93: 85 */       this.totalTime += paramLong;
/*  94: 86 */       this.ended = bool;
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean ended()
/*  99:    */   {
/* 100: 91 */     return this.ended;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static final void printMatrix(float[] paramArrayOfFloat)
/* 104:    */   {
/* 105: 95 */     System.out.println(paramArrayOfFloat[0] + "\t\t" + paramArrayOfFloat[4] + "\t\t" + paramArrayOfFloat[8] + "\t\t" + paramArrayOfFloat[12]);
/* 106: 96 */     System.out.println(paramArrayOfFloat[1] + "\t\t" + paramArrayOfFloat[5] + "\t\t" + paramArrayOfFloat[9] + "\t\t" + paramArrayOfFloat[13]);
/* 107: 97 */     System.out.println(paramArrayOfFloat[2] + "\t\t" + paramArrayOfFloat[6] + "\t\t" + paramArrayOfFloat[10] + "\t\t" + paramArrayOfFloat[14]);
/* 108: 98 */     System.out.println(paramArrayOfFloat[3] + "\t\t" + paramArrayOfFloat[7] + "\t\t" + paramArrayOfFloat[11] + "\t\t" + paramArrayOfFloat[15]);
/* 109: 99 */     System.out.println();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void render(GL paramGL, Transform3D paramTransform3D, Plane[] paramArrayOfPlane, float[] paramArrayOfFloat)
/* 113:    */   {
/* 114:103 */     paramGL.glLoadIdentity();
/* 115:104 */     paramTransform3D.applySubtract(paramGL);
/* 116:105 */     BLANK_TRANSFORM.apply(paramGL);
/* 117:106 */     float[] arrayOfFloat = new float[16];
/* 118:107 */     paramGL.glGetFloatv(2982, arrayOfFloat, 0);
/* 119:108 */     if (this.camera == null) {
/* 120:108 */       this.camera = paramTransform3D;
/* 121:    */     }
/* 122:109 */     tmp1.setTo(this.sphere);
/* 123:110 */     tmp1.apply(arrayOfFloat);
/* 124:    */     
/* 125:112 */     tmp1.location.add(paramTransform3D.position);
/* 126:116 */     synchronized (this.particles)
/* 127:    */     {
/* 128:118 */       if (this.particles != null) {
/* 129:120 */         for (int i = 0; i < this.particles.length; i++) {
/* 130:122 */           if (this.particles[i] != null) {
/* 131:122 */             this.particles[i].render(paramGL, paramTransform3D, paramArrayOfPlane, arrayOfFloat);
/* 132:    */           }
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void addForce(ParticleEffect paramParticleEffect)
/* 139:    */   {
/* 140:130 */     this.forces.add(paramParticleEffect);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void removeForce(ParticleEffect paramParticleEffect)
/* 144:    */   {
/* 145:134 */     this.forces.remove(paramParticleEffect);
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.particle.ParticleSystem
 * JD-Core Version:    0.7.0.1
 */