/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import com.sun.opengl.util.texture.Texture;
/*   4:    */ import com.sun.opengl.util.texture.TextureIO;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.BufferedReader;
/*   7:    */ import java.io.File;
/*   8:    */ import java.io.FileInputStream;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.io.InputStreamReader;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.io.Reader;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import javax.imageio.ImageIO;
/*  19:    */ 
/*  20:    */ public class PolygonMeshLoader
/*  21:    */ {
/*  22: 13 */   File path = null;
/*  23:    */   
/*  24:    */   public PolygonMesh loadObject(File paramFile)
/*  25:    */     throws IOException, ObjectParsingException
/*  26:    */   {
/*  27: 20 */     this.path = paramFile.getParentFile();
/*  28: 21 */     return loadObject(new FileInputStream(paramFile));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PolygonMesh loadObject(URL paramURL)
/*  32:    */     throws IOException, ObjectParsingException
/*  33:    */   {
/*  34: 25 */     return loadObject(new BufferedReader(new InputStreamReader(paramURL.openStream())));
/*  35:    */   }
/*  36:    */   
/*  37:    */   private PolygonMesh loadObject(InputStream paramInputStream)
/*  38:    */     throws IOException, ObjectParsingException
/*  39:    */   {
/*  40: 29 */     return loadObject(new InputStreamReader(paramInputStream));
/*  41:    */   }
/*  42:    */   
/*  43:    */   private PolygonMesh loadObject(Reader paramReader)
/*  44:    */     throws IOException, ObjectParsingException
/*  45:    */   {
/*  46: 33 */     if ((paramReader instanceof BufferedReader)) {
/*  47: 33 */       return loadObject((BufferedReader)paramReader);
/*  48:    */     }
/*  49: 34 */     return loadObject(new BufferedReader(paramReader));
/*  50:    */   }
/*  51:    */   
/*  52:    */   private PolygonMesh loadObject(BufferedReader paramBufferedReader)
/*  53:    */     throws IOException, ObjectParsingException
/*  54:    */   {
/*  55:    */     try
/*  56:    */     {
/*  57: 40 */       String str1 = null;
/*  58: 41 */       ArrayList localArrayList1 = new ArrayList();
/*  59: 42 */       while ((str1 = paramBufferedReader.readLine()) != null) {
/*  60: 44 */         localArrayList1.add(str1);
/*  61:    */       }
/*  62: 46 */       paramBufferedReader.close();
/*  63: 47 */       paramBufferedReader = null;
/*  64: 48 */       ArrayList localArrayList2 = new ArrayList();
/*  65: 49 */       ArrayList localArrayList3 = new ArrayList();
/*  66: 50 */       Iterator localIterator = localArrayList1.iterator();
/*  67: 51 */       Texture localTexture = blankTexture();
/*  68: 52 */       HashMap localHashMap = new HashMap();
/*  69: 53 */       ArrayList localArrayList4 = new ArrayList();
/*  70: 54 */       while (localIterator.hasNext())
/*  71:    */       {
/*  72: 56 */         localObject1 = ((String)localIterator.next()).trim();
/*  73: 57 */         String[] arrayOfString1 = ((String)localObject1).split(" ");
/*  74: 58 */         if (arrayOfString1[0].equals("v"))
/*  75:    */         {
/*  76: 60 */           localArrayList2.add(new Vector3D(Float.parseFloat(arrayOfString1[1]), Float.parseFloat(arrayOfString1[2]), Float.parseFloat(arrayOfString1[3])));
/*  77:    */         }
/*  78: 62 */         else if (arrayOfString1[0].equals("usemtl"))
/*  79:    */         {
/*  80: 66 */           localTexture = (Texture)localHashMap.get(arrayOfString1[1]);
/*  81:    */         }
/*  82:    */         else
/*  83:    */         {
/*  84:    */           Object localObject2;
/*  85:    */           Object localObject3;
/*  86:    */           String[] arrayOfString2;
/*  87: 70 */           if (arrayOfString1[0].equals("mtllib"))
/*  88:    */           {
/*  89: 73 */             localObject2 = getClass().getClassLoader().getResourceAsStream(arrayOfString1[1]);
/*  90: 74 */             System.out.println(getClass().getClassLoader().getResource(arrayOfString1[1]));
/*  91: 75 */             localObject3 = new BufferedReader(new InputStreamReader((InputStream)localObject2));
/*  92: 76 */             String str2 = null;
/*  93: 77 */             while ((str1 = ((BufferedReader)localObject3).readLine()) != null)
/*  94:    */             {
/*  95: 79 */               arrayOfString2 = str1.split(" ");
/*  96: 80 */               if (arrayOfString2[0].equals("newmtl")) {
/*  97: 83 */                 str2 = new String(arrayOfString2[1]);
/*  98: 86 */               } else if (arrayOfString2[0].equals("map_Kd")) {
/*  99: 88 */                 localHashMap.put(str2, loadTexture(arrayOfString2[1]));
/* 100:    */               }
/* 101:    */             }
/* 102: 94 */             ((InputStream)localObject2).close();
/* 103: 95 */             ((BufferedReader)localObject3).close();
/* 104: 96 */             localObject2 = null;
/* 105: 97 */             localObject3 = null;
/* 106:    */           }
/* 107: 99 */           else if (arrayOfString1[0].equals("vt"))
/* 108:    */           {
/* 109:101 */             localArrayList3.add(new TextureCoord(Float.parseFloat(arrayOfString1[1]), Float.parseFloat(arrayOfString1[2])));
/* 110:    */           }
/* 111:103 */           else if (arrayOfString1[0].equals("f"))
/* 112:    */           {
/* 113:    */             int i;
/* 114:105 */             if (((String)localObject1).indexOf("/") == -1)
/* 115:    */             {
/* 116:107 */               localObject2 = new TextureCoord[arrayOfString1.length - 1];
/* 117:108 */               localObject3 = new Vector3D[arrayOfString1.length - 1];
/* 118:109 */               for (i = 1; i < arrayOfString1.length; i++)
/* 119:    */               {
/* 120:111 */                 localObject3[(i - 1)] = getVector(arrayOfString1[i], localArrayList2);
/* 121:112 */                 localObject2[(i - 1)] = new TextureCoord();
/* 122:    */               }
/* 123:114 */               localArrayList4.add(new Polygon3D((TextureCoord[])localObject2, localTexture, (Vector3D[])localObject3));
/* 124:    */             }
/* 125:    */             else
/* 126:    */             {
/* 127:118 */               localObject2 = new TextureCoord[arrayOfString1.length - 1];
/* 128:119 */               localObject3 = new Vector3D[arrayOfString1.length - 1];
/* 129:120 */               for (i = 1; i < arrayOfString1.length; i++)
/* 130:    */               {
/* 131:122 */                 arrayOfString2 = arrayOfString1[i].split("/");
/* 132:123 */                 localObject3[(i - 1)] = getVector(arrayOfString2[0], localArrayList2);
/* 133:124 */                 localObject2[(i - 1)] = getTexCoord(arrayOfString2[1], localArrayList3);
/* 134:    */               }
/* 135:127 */               localArrayList4.add(new Polygon3D((TextureCoord[])localObject2, localTexture, (Vector3D[])localObject3));
/* 136:    */             }
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:131 */       Object localObject1 = new Polygon3D[localArrayList4.size()];
/* 141:132 */       localArrayList4.toArray((Object[])localObject1);
/* 142:133 */       return new PolygonMesh((Polygon3D[])localObject1);
/* 143:    */     }
/* 144:    */     catch (RuntimeException localRuntimeException)
/* 145:    */     {
/* 146:135 */       throw new ObjectParsingException(localRuntimeException);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   private Texture loadTexture(String paramString)
/* 151:    */     throws IOException, ObjectParsingException
/* 152:    */   {
/* 153:139 */     System.out.println(paramString);
/* 154:140 */     System.out.println("objloader loading texture: " + getClass().getClassLoader().getResource(paramString));
/* 155:    */     
/* 156:142 */     BufferedImage localBufferedImage = ImageIO.read(getClass().getClassLoader().getResource(paramString));
/* 157:143 */     Texture localTexture = TextureIO.newTexture(localBufferedImage, true);
/* 158:144 */     localTexture.setTexParameteri(10242, 10497);
/* 159:145 */     localTexture.setTexParameteri(10243, 10497);
/* 160:    */     
/* 161:147 */     return localTexture;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private Texture blankTexture()
/* 165:    */   {
/* 166:151 */     BufferedImage localBufferedImage = new BufferedImage(4, 4, 1);
/* 167:152 */     return TextureIO.newTexture(localBufferedImage, false);
/* 168:    */   }
/* 169:    */   
/* 170:    */   private Vector3D getVector(String paramString, ArrayList<Vector3D> paramArrayList)
/* 171:    */     throws ObjectParsingException
/* 172:    */   {
/* 173:156 */     int i = Integer.parseInt(paramString);
/* 174:157 */     if (i == 0) {
/* 175:157 */       throw new ObjectParsingException("invalid vertex data");
/* 176:    */     }
/* 177:158 */     if (i > 0) {
/* 178:158 */       return (Vector3D)paramArrayList.get(i - 1);
/* 179:    */     }
/* 180:161 */     return (Vector3D)paramArrayList.get(paramArrayList.size() + i);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private TextureCoord getTexCoord(String paramString, ArrayList<TextureCoord> paramArrayList)
/* 184:    */     throws ObjectParsingException
/* 185:    */   {
/* 186:166 */     int i = Integer.parseInt(paramString);
/* 187:167 */     if (i == 0) {
/* 188:167 */       throw new ObjectParsingException("invalid vertex data");
/* 189:    */     }
/* 190:168 */     if (i > 0) {
/* 191:168 */       return (TextureCoord)paramArrayList.get(i - 1);
/* 192:    */     }
/* 193:171 */     return (TextureCoord)paramArrayList.get(paramArrayList.size() + i);
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.PolygonMeshLoader
 * JD-Core Version:    0.7.0.1
 */