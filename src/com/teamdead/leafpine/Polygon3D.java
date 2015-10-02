/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import com.sun.opengl.util.texture.Texture;
/*   4:    */ import com.sun.opengl.util.texture.TextureIO;
/*   5:    */ import java.awt.Point;
/*   6:    */ import java.awt.image.BufferedImage;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import javax.media.opengl.GL;
/*  10:    */ 
/*  11:    */ public class Polygon3D
/*  12:    */   extends Shape3D
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   protected Vector3D[] vertices;
/*  16:    */   protected TextureCoord[] texCoords;
/*  17:    */   protected int numVertices;
/*  18:    */   protected Vector3D normal;
/*  19: 18 */   protected transient Texture texture = null;
/*  20:    */   private boolean mipmapped;
/*  21:    */   private BufferedImage texImage;
/*  22: 24 */   private static Vector3D temp1 = new Vector3D();
/*  23: 25 */   private static Vector3D temp2 = new Vector3D();
/*  24:    */   
/*  25:    */   public Polygon3D()
/*  26:    */   {
/*  27: 30 */     this.numVertices = 0;
/*  28: 31 */     this.vertices = new Vector3D[0];
/*  29: 32 */     this.texCoords = new TextureCoord[0];
/*  30: 33 */     this.normal = new Vector3D();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Polygon3D(TextureCoord[] paramArrayOfTextureCoord, Texture paramTexture, Vector3D[] paramArrayOfVector3D)
/*  34:    */   {
/*  35: 38 */     this.vertices = paramArrayOfVector3D;
/*  36: 39 */     this.numVertices = paramArrayOfVector3D.length;
/*  37: 40 */     if (paramArrayOfTextureCoord.length != paramArrayOfVector3D.length) {
/*  38: 40 */       throw new IllegalArgumentException("texCoords.length must equal vertices.length");
/*  39:    */     }
/*  40: 41 */     this.texCoords = paramArrayOfTextureCoord;
/*  41: 42 */     this.texture = paramTexture;
/*  42: 43 */     calcNormal();
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void initTexture()
/*  46:    */   {
/*  47: 47 */     if (this.texImage == null) {
/*  48: 47 */       System.out.println("texImage==null!");
/*  49:    */     } else {
/*  50: 48 */       System.out.println("");
/*  51:    */     }
/*  52: 49 */     if (this.texture == null) {
/*  53: 49 */       this.texture = TextureIO.newTexture(this.texImage, this.mipmapped);
/*  54:    */     }
/*  55: 50 */     if (this.texture == null) {
/*  56: 50 */       System.out.println("texture==null");
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Polygon3D(TextureCoord[] paramArrayOfTextureCoord, BufferedImage paramBufferedImage, boolean paramBoolean, Vector3D[] paramArrayOfVector3D)
/*  61:    */   {
/*  62: 54 */     this.vertices = paramArrayOfVector3D;
/*  63: 55 */     this.numVertices = paramArrayOfVector3D.length;
/*  64: 56 */     if (paramArrayOfTextureCoord.length != paramArrayOfVector3D.length) {
/*  65: 56 */       throw new IllegalArgumentException("texCoords.length must equal vertices.length");
/*  66:    */     }
/*  67: 57 */     this.texCoords = paramArrayOfTextureCoord;
/*  68: 58 */     this.mipmapped = paramBoolean;
/*  69: 59 */     this.texImage = paramBufferedImage;
/*  70: 60 */     if (this.texImage == null) {
/*  71: 60 */       System.out.println("Polygon3D():texImage==null");
/*  72:    */     } else {
/*  73: 61 */       System.out.println("Polygon3D():texImage!=null");
/*  74:    */     }
/*  75: 62 */     calcNormal();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Polygon3D(Polygon3D paramPolygon3D)
/*  79:    */   {
/*  80: 66 */     this(paramPolygon3D.texCoords, paramPolygon3D.texture, paramPolygon3D.vertices);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setTo(Polygon3D paramPolygon3D)
/*  84:    */   {
/*  85: 71 */     this.numVertices = paramPolygon3D.numVertices;
/*  86: 72 */     this.vertices = new Vector3D[this.numVertices];
/*  87: 73 */     for (int i = 0; i < this.numVertices; i++) {
/*  88: 75 */       this.vertices[i] = new Vector3D(paramPolygon3D.vertices[i]);
/*  89:    */     }
/*  90: 77 */     this.texCoords = new TextureCoord[paramPolygon3D.texCoords.length];
/*  91: 78 */     for (i = 0; i < this.texCoords.length; i++) {
/*  92: 80 */       this.texCoords[i] = ((TextureCoord)paramPolygon3D.texCoords[i].clone());
/*  93:    */     }
/*  94: 82 */     this.normal.setTo(paramPolygon3D.normal);
/*  95: 83 */     this.texture = paramPolygon3D.texture;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object clone()
/*  99:    */   {
/* 100: 88 */     return new Polygon3D(this);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getNumVertices()
/* 104:    */   {
/* 105: 93 */     return this.numVertices;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getType()
/* 109:    */   {
/* 110: 98 */     switch (this.numVertices)
/* 111:    */     {
/* 112:    */     case 1: 
/* 113:100 */       return 0;
/* 114:    */     case 2: 
/* 115:101 */       return 1;
/* 116:    */     case 3: 
/* 117:102 */       return 4;
/* 118:    */     case 4: 
/* 119:103 */       return 7;
/* 120:    */     }
/* 121:104 */     return 9;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public TextureCoord[] getTexCoords()
/* 125:    */   {
/* 126:110 */     return this.texCoords;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void render(GL paramGL, Transform3D paramTransform3D, Plane[] paramArrayOfPlane, float[] paramArrayOfFloat)
/* 130:    */   {
/* 131:114 */     this.texture.bind();
/* 132:115 */     paramGL.glBegin(getType());
/* 133:116 */     paramGL.glNormal3f(this.normal.x, this.normal.y, this.normal.z);
/* 134:117 */     for (int i = 0; i < this.vertices.length; i++)
/* 135:    */     {
/* 136:119 */       paramGL.glTexCoord2f(this.texCoords[i].s, this.texCoords[i].t);
/* 137:120 */       paramGL.glVertex3f(this.vertices[i].x, this.vertices[i].y, this.vertices[i].z);
/* 138:    */     }
/* 139:122 */     paramGL.glEnd();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Vector3D[] getCoords()
/* 143:    */   {
/* 144:127 */     return this.vertices;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Vector3D getCoord(int paramInt)
/* 148:    */   {
/* 149:134 */     if (paramInt >= this.numVertices) {
/* 150:134 */       throw new IllegalArgumentException("index must be smaller than numVertices: index==" + paramInt + " !");
/* 151:    */     }
/* 152:135 */     return this.vertices[paramInt];
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Texture getTexture()
/* 156:    */   {
/* 157:140 */     return this.texture;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void calcNormal()
/* 161:    */   {
/* 162:145 */     if (this.normal == null) {
/* 163:145 */       this.normal = new Vector3D();
/* 164:    */     }
/* 165:146 */     temp1.setTo(this.vertices[2]);
/* 166:147 */     temp1.subtract(this.vertices[1]);
/* 167:148 */     temp2.setTo(this.vertices[0]);
/* 168:149 */     temp2.subtract(this.vertices[1]);
/* 169:150 */     this.normal.crossProduct(temp1, temp2);
/* 170:151 */     this.normal.normalize();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Vector3D getNormal()
/* 174:    */   {
/* 175:156 */     return this.normal;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isFacing(Vector3D paramVector3D)
/* 179:    */   {
/* 180:161 */     temp1.setTo(paramVector3D);
/* 181:162 */     temp1.subtract(this.vertices[0]);
/* 182:163 */     return this.normal.dotProduct(temp1) >= 0.0F;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Point[] project2D(Vector3D paramVector3D)
/* 186:    */   {
/* 187:167 */     Point[] arrayOfPoint = new Point[this.numVertices];
/* 188:168 */     temp1.setTo(this.normal);
/* 189:169 */     temp1.x = Math.abs(temp1.x);
/* 190:170 */     temp1.y = Math.abs(temp1.y);
/* 191:171 */     temp1.z = Math.abs(temp1.z);
/* 192:172 */     float f = Math.max(temp1.x, Math.max(temp1.y, temp1.z));
/* 193:    */     int i;
/* 194:173 */     if (f == temp1.x)
/* 195:    */     {
/* 196:175 */       paramVector3D.x = paramVector3D.y;
/* 197:176 */       paramVector3D.y = paramVector3D.z;
/* 198:177 */       for (i = 0; i < arrayOfPoint.length; i++) {
/* 199:179 */         arrayOfPoint[i] = new Point(Math.round(this.vertices[i].y), Math.round(this.vertices[i].z));
/* 200:    */       }
/* 201:    */     }
/* 202:182 */     else if (f == temp1.y)
/* 203:    */     {
/* 204:184 */       paramVector3D.y = paramVector3D.z;
/* 205:185 */       for (i = 0; i < arrayOfPoint.length; i++) {
/* 206:187 */         arrayOfPoint[i] = new Point(Math.round(this.vertices[i].x), Math.round(this.vertices[i].z));
/* 207:    */       }
/* 208:    */     }
/* 209:190 */     else if (f == temp1.z)
/* 210:    */     {
/* 211:192 */       for (i = 0; i < arrayOfPoint.length; i++) {
/* 212:194 */         arrayOfPoint[i] = new Point(Math.round(this.vertices[i].x), Math.round(this.vertices[i].y));
/* 213:    */       }
/* 214:    */     }
/* 215:197 */     return arrayOfPoint;
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Polygon3D
 * JD-Core Version:    0.7.0.1
 */