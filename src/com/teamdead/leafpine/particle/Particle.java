/*   1:    */ package com.teamdead.leafpine.particle;
/*   2:    */ 
/*   3:    */ import com.sun.opengl.util.texture.Texture;
/*   4:    */ import com.sun.opengl.util.texture.TextureIO;
/*   5:    */ import com.teamdead.leafpine.Plane;
/*   6:    */ import com.teamdead.leafpine.Renderer3D;
/*   7:    */ import com.teamdead.leafpine.Shape3D;
/*   8:    */ import com.teamdead.leafpine.Transform3D;
/*   9:    */ import com.teamdead.leafpine.Vector3D;
/*  10:    */ import java.awt.Color;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.image.BufferedImage;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import javax.media.opengl.GL;
/*  15:    */ 
/*  16:    */ public class Particle
/*  17:    */   extends Shape3D
/*  18:    */ {
/*  19: 12 */   private static HashMap<Color, Texture> map = new HashMap();
/*  20:    */   private Texture texture;
/*  21:    */   private static final long DIE_TIME = 5000L;
/*  22:    */   private long totalTime;
/*  23: 16 */   private static Vector3D tmp1 = new Vector3D();
/*  24:    */   Vector3D position1;
/*  25:    */   Vector3D position2;
/*  26:    */   Vector3D velocity;
/*  27:    */   Vector3D startPoint;
/*  28:    */   Vector3D startVelocity;
/*  29:    */   
/*  30:    */   static
/*  31:    */   {
/*  32: 20 */     initMap();
/*  33:    */   }
/*  34:    */   
/*  35:    */   private static void initMap()
/*  36:    */   {
/*  37: 24 */     Runnable local1 = new Runnable()
/*  38:    */     {
/*  39:    */       public void run()
/*  40:    */       {
/*  41: 28 */         Particle.createTexture(Color.black);
/*  42: 29 */         Particle.createTexture(Color.blue);
/*  43: 30 */         Particle.createTexture(Color.cyan);
/*  44: 31 */         Particle.createTexture(Color.red);
/*  45: 32 */         Particle.createTexture(Color.orange);
/*  46: 33 */         Particle.createTexture(Color.yellow);
/*  47: 34 */         Particle.createTexture(Color.darkGray);
/*  48: 35 */         Particle.createTexture(Color.gray);
/*  49: 36 */         Particle.createTexture(Color.green);
/*  50: 37 */         Particle.createTexture(Color.lightGray);
/*  51: 38 */         Particle.createTexture(Color.magenta);
/*  52: 39 */         Particle.createTexture(Color.pink);
/*  53: 40 */         Particle.createTexture(Color.white);
/*  54:    */       }
/*  55: 42 */     };
/*  56: 43 */     Renderer3D.executeOnInit(local1);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Particle(Vector3D paramVector3D1, Vector3D paramVector3D2, Color paramColor)
/*  60:    */   {
/*  61: 47 */     this.position1 = ((Vector3D)paramVector3D1.clone());
/*  62: 48 */     this.position2 = ((Vector3D)paramVector3D1.clone());
/*  63: 49 */     this.velocity = ((Vector3D)paramVector3D2.clone());
/*  64: 50 */     if (paramVector3D2.y < 0.0F) {
/*  65: 52 */       paramVector3D2.y = (-paramVector3D2.y);
/*  66:    */     }
/*  67: 55 */     this.startPoint = ((Vector3D)paramVector3D1.clone());
/*  68: 56 */     this.startVelocity = ((Vector3D)paramVector3D2.clone());
/*  69: 57 */     Texture localTexture = (Texture)map.get(paramColor);
/*  70: 58 */     if (localTexture != null) {
/*  71: 58 */       this.texture = localTexture;
/*  72:    */     } else {
/*  73: 59 */       createTexture(paramColor);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void reset(Vector3D paramVector3D1, Vector3D paramVector3D2, Color paramColor)
/*  78:    */   {
/*  79: 64 */     this.position1 = ((Vector3D)paramVector3D1.clone());
/*  80: 65 */     this.position2 = ((Vector3D)paramVector3D1.clone());
/*  81: 66 */     this.velocity = ((Vector3D)paramVector3D2.clone());
/*  82: 67 */     if (paramVector3D2.y < 0.0F) {
/*  83: 69 */       paramVector3D2.y = (-paramVector3D2.y);
/*  84:    */     }
/*  85: 72 */     this.startPoint = ((Vector3D)paramVector3D1.clone());
/*  86: 73 */     this.startVelocity = ((Vector3D)paramVector3D2.clone());
/*  87: 74 */     Texture localTexture = (Texture)map.get(paramColor);
/*  88: 75 */     if (localTexture != null) {
/*  89: 75 */       this.texture = localTexture;
/*  90:    */     } else {
/*  91: 76 */       createTexture(paramColor);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean shouldRemove()
/*  96:    */   {
/*  97: 80 */     return this.position2.y <= 0.0F;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void regenerate()
/* 101:    */   {
/* 102: 84 */     this.totalTime = 0L;
/* 103: 85 */     this.position1.setTo(this.startPoint);
/* 104: 86 */     this.position2.setTo(this.startPoint);
/* 105: 87 */     this.velocity.setTo(this.startVelocity);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void render(GL paramGL, Transform3D paramTransform3D, Plane[] paramArrayOfPlane, float[] paramArrayOfFloat)
/* 109:    */   {
/* 110: 91 */     if (this.texture == null) {
/* 111: 91 */       return;
/* 112:    */     }
/* 113: 93 */     this.texture.bind();
/* 114: 94 */     paramGL.glBegin(1);
/* 115: 95 */     paramGL.glTexCoord2f(0.0F, 0.0F);
/* 116: 96 */     paramGL.glVertex3f(this.position1.x, this.position1.y, this.position1.z);
/* 117: 97 */     paramGL.glTexCoord2f(0.0F, 0.0F);
/* 118: 98 */     paramGL.glVertex3f(this.position2.x, this.position2.y, this.position2.z);
/* 119: 99 */     paramGL.glEnd();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void update(long paramLong)
/* 123:    */   {
/* 124:103 */     this.position1.setTo(this.position2);
/* 125:104 */     tmp1.setTo(this.velocity);
/* 126:105 */     tmp1.multiply((float)paramLong);
/* 127:106 */     this.position2.add(tmp1);
/* 128:107 */     this.totalTime += paramLong;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private Vector3D randomVector()
/* 132:    */   {
/* 133:111 */     float f1 = (float)Math.random();
/* 134:112 */     float f2 = (float)Math.random();
/* 135:113 */     float f3 = (float)Math.random();
/* 136:114 */     float f4 = f1 * 2.0F - 1.0F;
/* 137:115 */     float f5 = f2;
/* 138:116 */     float f6 = f3 * 2.0F - 1.0F;
/* 139:117 */     Vector3D localVector3D = new Vector3D(f4, f5, f6);
/* 140:118 */     localVector3D.normalize();
/* 141:119 */     return localVector3D;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private static void createTexture(Color paramColor)
/* 145:    */   {
/* 146:123 */     if (map.containsKey(paramColor)) {
/* 147:123 */       return;
/* 148:    */     }
/* 149:124 */     BufferedImage localBufferedImage = new BufferedImage(2, 2, 1);
/* 150:125 */     Graphics localGraphics = localBufferedImage.getGraphics();
/* 151:126 */     localGraphics.setColor(paramColor);
/* 152:127 */     localGraphics.fillRect(0, 0, 2, 2);
/* 153:128 */     localGraphics.dispose();
/* 154:129 */     Texture localTexture = TextureIO.newTexture(localBufferedImage, false);
/* 155:130 */     map.put(paramColor, localTexture);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Particle() {}
/* 159:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.particle.Particle
 * JD-Core Version:    0.7.0.1
 */