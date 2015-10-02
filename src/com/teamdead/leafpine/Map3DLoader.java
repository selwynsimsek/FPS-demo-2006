/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import com.sun.opengl.util.texture.Texture;
/*   4:    */ import com.sun.opengl.util.texture.TextureIO;
/*   5:    */ import com.teamdead.leafpine.path.Map3D;
/*   6:    */ import com.teamdead.leafpine.path.Portal;
/*   7:    */ import com.teamdead.leafpine.path.PortalGroup;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.io.BufferedReader;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStreamReader;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import javax.imageio.ImageIO;
/*  18:    */ 
/*  19:    */ public class Map3DLoader
/*  20:    */ {
/*  21:    */   private Vector3D player;
/*  22:    */   private HashMap<String, Texture> textures;
/*  23:    */   private ArrayList<Vector3D> points;
/*  24:    */   private ArrayList<Portal> portals;
/*  25:    */   private ArrayList<PortalGroup> rooms;
/*  26:    */   private Texture currentTexture;
/*  27:    */   private Texture floorTexture;
/*  28:    */   private Texture ceilingTexture;
/*  29:    */   private ArrayList<Polygon3D> polygons;
/*  30:    */   private Polygon3D floor;
/*  31:    */   private Polygon3D ceiling;
/*  32:    */   private ArrayList<PolygonMesh> staticObjects;
/*  33:    */   private HashMap<String, ExplodableObject> dexObjs;
/*  34:    */   private ArrayList<ExplodableObject> exObjs;
/*  35:    */   private PolygonMeshLoader objectLoader;
/*  36:    */   private float ceilingHeight;
/*  37:    */   private float boundsx1;
/*  38:    */   private float boundsz1;
/*  39:    */   private float boundsx2;
/*  40:    */   private float boundsz2;
/*  41:    */   private File parentDirectory;
/*  42:    */   private Renderer3D renderer;
/*  43: 30 */   private static HashMap<String, Color> colorMap = new HashMap();
/*  44:    */   private static final float EPSILON = 0.1F;
/*  45:    */   
/*  46:    */   static
/*  47:    */   {
/*  48: 31 */     colorMap.put("black", Color.black);
/*  49: 32 */     colorMap.put("blue", Color.blue);
/*  50: 33 */     colorMap.put("cyan", Color.cyan);
/*  51: 34 */     colorMap.put("red", Color.red);
/*  52: 35 */     colorMap.put("orange", Color.orange);
/*  53: 36 */     colorMap.put("yellow", Color.yellow);
/*  54: 37 */     colorMap.put("darkGray", Color.darkGray);
/*  55: 38 */     colorMap.put("gray", Color.gray);
/*  56: 39 */     colorMap.put("green", Color.green);
/*  57: 40 */     colorMap.put("lightGray", Color.lightGray);
/*  58: 41 */     colorMap.put("magenta", Color.magenta);
/*  59: 42 */     colorMap.put("pink", Color.pink);
/*  60: 43 */     colorMap.put("white", Color.white);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Map3DLoader()
/*  64:    */   {
/*  65: 48 */     this.textures = new HashMap();
/*  66: 49 */     this.points = new ArrayList();
/*  67: 50 */     this.portals = new ArrayList();
/*  68: 51 */     this.rooms = new ArrayList();
/*  69: 52 */     this.polygons = new ArrayList();
/*  70: 53 */     this.player = new Vector3D();
/*  71: 54 */     this.objectLoader = new PolygonMeshLoader();
/*  72: 55 */     this.staticObjects = new ArrayList();
/*  73: 56 */     this.dexObjs = new HashMap();
/*  74: 57 */     this.exObjs = new ArrayList();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Map3D parseMap(String paramString, Renderer3D paramRenderer3D)
/*  78:    */     throws ObjectParsingException, IOException
/*  79:    */   {
/*  80: 61 */     this.renderer = paramRenderer3D;
/*  81: 62 */     ArrayList localArrayList = new ArrayList();
/*  82: 63 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource(paramString).openStream()));
/*  83: 64 */     String str = null;
/*  84: 65 */     while ((str = localBufferedReader.readLine()) != null)
/*  85:    */     {
/*  86: 67 */       str = str.trim();
/*  87: 68 */       if ((!str.startsWith("#")) && (!str.equals("")))
/*  88:    */       {
/*  89: 69 */         System.out.println(str);
/*  90: 70 */         localArrayList.add(str);
/*  91:    */       }
/*  92:    */     }
/*  93: 72 */     localBufferedReader.close();
/*  94: 73 */     localBufferedReader = null;
/*  95: 74 */     for (int i = 0; i < localArrayList.size(); i++) {
/*  96: 76 */       parseLine((String)localArrayList.get(i));
/*  97:    */     }
/*  98: 78 */     addPortalNeighbours(localArrayList);
/*  99: 79 */     Polygon3D[] arrayOfPolygon3D = new Polygon3D[2 + this.polygons.size()];
/* 100: 80 */     for (int j = 0; j < this.polygons.size(); j++) {
/* 101: 82 */       arrayOfPolygon3D[j] = ((Polygon3D)this.polygons.get(j));
/* 102:    */     }
/* 103: 84 */     createFloorAndCeiling();
/* 104: 85 */     arrayOfPolygon3D[(arrayOfPolygon3D.length - 1)] = this.floor;
/* 105: 86 */     arrayOfPolygon3D[(arrayOfPolygon3D.length - 2)] = this.ceiling;
/* 106: 87 */     PortalGroup[] arrayOfPortalGroup = new PortalGroup[this.rooms.size()];
/* 107: 88 */     for (int k = 0; k < arrayOfPortalGroup.length; k++) {
/* 108: 90 */       arrayOfPortalGroup[k] = ((PortalGroup)this.rooms.get(k));
/* 109:    */     }
/* 110: 92 */     paramRenderer3D.getCamera().position.setTo(this.player);
/* 111: 93 */     PolygonMesh[] arrayOfPolygonMesh = new PolygonMesh[this.staticObjects.size()];
/* 112: 94 */     this.staticObjects.toArray(arrayOfPolygonMesh);
/* 113: 95 */     ExplodableObject[] arrayOfExplodableObject = new ExplodableObject[this.exObjs.size()];
/* 114: 96 */     this.exObjs.toArray(arrayOfExplodableObject);
/* 115: 97 */     Map3D localMap3D = new Map3D(arrayOfPolygon3D, arrayOfPortalGroup, paramRenderer3D, arrayOfPolygonMesh, arrayOfExplodableObject);
/* 116: 98 */     cleanUp();
/* 117: 99 */     return localMap3D;
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void cleanUp()
/* 121:    */   {
/* 122:103 */     this.player.setTo(0.0F, 0.0F, 0.0F);
/* 123:104 */     this.textures.clear();
/* 124:105 */     this.dexObjs.clear();
/* 125:106 */     this.exObjs.clear();
/* 126:107 */     this.points.clear();
/* 127:108 */     this.rooms.clear();
/* 128:109 */     this.portals.clear();
/* 129:110 */     this.polygons.clear();
/* 130:111 */     this.parentDirectory = null;
/* 131:112 */     this.ceilingHeight = 0.0F;
/* 132:113 */     this.boundsx1 = 0.0F;
/* 133:114 */     this.boundsx2 = 0.0F;
/* 134:115 */     this.boundsz1 = 0.0F;
/* 135:116 */     this.boundsz2 = 0.0F;
/* 136:117 */     this.floor = null;
/* 137:118 */     this.staticObjects.clear();
/* 138:119 */     this.ceiling = null;
/* 139:120 */     this.currentTexture = null;
/* 140:121 */     this.floorTexture = null;
/* 141:122 */     this.ceilingTexture = null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void addPortalNeighbours(ArrayList<String> paramArrayList)
/* 145:    */   {
/* 146:126 */     int i = 0;
/* 147:127 */     for (int j = 0; j < paramArrayList.size(); j++)
/* 148:    */     {
/* 149:129 */       String str = (String)paramArrayList.get(j);
/* 150:130 */       if ((str.startsWith("portal")) && (!str.startsWith("portalgrp")))
/* 151:    */       {
/* 152:132 */         Portal localPortal1 = (Portal)this.portals.get(i);
/* 153:133 */         i++;
/* 154:134 */         String[] arrayOfString = str.split(" ");
/* 155:135 */         for (int k = 2; k < arrayOfString.length; k++)
/* 156:    */         {
/* 157:137 */           Portal localPortal2 = (Portal)this.portals.get(Integer.parseInt(arrayOfString[k]) - 1);
/* 158:138 */           localPortal1.addNeighbour(localPortal2);
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private void parseLine(String paramString)
/* 165:    */     throws IOException, ObjectParsingException
/* 166:    */   {
/* 167:    */     try
/* 168:    */     {
/* 169:147 */       String[] arrayOfString = paramString.split(" ");
/* 170:148 */       if (arrayOfString[0].equals("mtllib"))
/* 171:    */       {
/* 172:148 */         loadMTLFile(arrayOfString[1]);
/* 173:    */       }
/* 174:149 */       else if (arrayOfString[0].equals("usemtl"))
/* 175:    */       {
/* 176:149 */         this.currentTexture = ((Texture)this.textures.get(arrayOfString[1]));
/* 177:    */       }
/* 178:150 */       else if (arrayOfString[0].equals("floortex"))
/* 179:    */       {
/* 180:150 */         this.floorTexture = this.currentTexture;
/* 181:    */       }
/* 182:151 */       else if (arrayOfString[0].equals("ceilingtex"))
/* 183:    */       {
/* 184:151 */         this.ceilingTexture = this.currentTexture;
/* 185:    */       }
/* 186:152 */       else if (arrayOfString[0].equals("ceiling"))
/* 187:    */       {
/* 188:152 */         this.ceilingHeight = Float.parseFloat(arrayOfString[1]);
/* 189:    */       }
/* 190:153 */       else if (arrayOfString[0].equals("bounds"))
/* 191:    */       {
/* 192:155 */         this.boundsx1 = (Float.parseFloat(arrayOfString[1]) - 0.1F);
/* 193:156 */         this.boundsz1 = (Float.parseFloat(arrayOfString[2]) - 0.1F);
/* 194:157 */         this.boundsx2 = (Float.parseFloat(arrayOfString[3]) + 0.1F);
/* 195:158 */         this.boundsz2 = (Float.parseFloat(arrayOfString[4]) + 0.1F);
/* 196:    */       }
/* 197:160 */       else if (arrayOfString[0].equals("corner"))
/* 198:    */       {
/* 199:162 */         this.points.add(new Vector3D(Float.parseFloat(arrayOfString[1]), 0.0F, Float.parseFloat(arrayOfString[2])));
/* 200:    */       }
/* 201:164 */       else if (arrayOfString[0].equals("wall"))
/* 202:    */       {
/* 203:166 */         this.polygons.add(createWallPolygon(getVector(arrayOfString[1]), getVector(arrayOfString[2])));
/* 204:    */       }
/* 205:168 */       else if (arrayOfString[0].equals("player"))
/* 206:    */       {
/* 207:170 */         this.player = getVector(arrayOfString[1]);
/* 208:    */       }
/* 209:172 */       else if (arrayOfString[0].equals("portal"))
/* 210:    */       {
/* 211:174 */         this.portals.add(new Portal(getVector(arrayOfString[1])));
/* 212:    */       }
/* 213:    */       else
/* 214:    */       {
/* 215:    */         Object localObject1;
/* 216:    */         Object localObject2;
/* 217:    */         Object localObject3;
/* 218:    */         int i;
/* 219:176 */         if (arrayOfString[0].equals("portalgrp"))
/* 220:    */         {
/* 221:178 */           localObject1 = getVector(arrayOfString[1]);
/* 222:179 */           localObject2 = (Vector3D)getVector(arrayOfString[2]).clone();
/* 223:180 */           ((Vector3D)localObject2).subtract((Vector3D)localObject1);
/* 224:181 */           localObject3 = new PortalGroup(((Vector3D)localObject1).x, ((Vector3D)localObject1).z, ((Vector3D)localObject2).x, ((Vector3D)localObject2).z);
/* 225:182 */           for (i = 3; i < arrayOfString.length; i++) {
/* 226:184 */             ((PortalGroup)localObject3).addNewPortal(getPortal(arrayOfString[i]));
/* 227:    */           }
/* 228:186 */           this.rooms.add(localObject3);
/* 229:    */         }
/* 230:188 */         else if (arrayOfString[0].equals("obj"))
/* 231:    */         {
/* 232:190 */           localObject1 = getVector(arrayOfString[1]);
/* 233:191 */           localObject2 = this.objectLoader.loadObject(getClass().getClassLoader().getResource(arrayOfString[2]));
/* 234:192 */           ((PolygonMesh)localObject2).getTransform3D().position.setTo((Vector3D)localObject1);
/* 235:193 */           if (arrayOfString.length >= 4) {
/* 236:193 */             ((PolygonMesh)localObject2).getTransform3D().rotY = Float.parseFloat(arrayOfString[3]);
/* 237:    */           }
/* 238:194 */           this.staticObjects.add(localObject2);
/* 239:    */         }
/* 240:196 */         else if (arrayOfString[0].equals("dexobj"))
/* 241:    */         {
/* 242:198 */           localObject1 = arrayOfString[1];
/* 243:199 */           localObject2 = this.objectLoader.loadObject(getClass().getClassLoader().getResource(arrayOfString[2]));
/* 244:200 */           localObject3 = new ArrayList();
/* 245:201 */           for (i = 3; i < arrayOfString.length; i++) {
/* 246:203 */             ((ArrayList)localObject3).add(colorMap.get(arrayOfString[i]));
/* 247:    */           }
/* 248:205 */           Color[] arrayOfColor = new Color[((ArrayList)localObject3).size()];
/* 249:206 */           ((ArrayList)localObject3).toArray(arrayOfColor);
/* 250:207 */           ExplodableObject localExplodableObject = new ExplodableObject((PolygonMesh)localObject2, arrayOfColor);
/* 251:208 */           this.dexObjs.put(localObject1, localExplodableObject);
/* 252:    */         }
/* 253:210 */         else if (arrayOfString[0].equals("exobj"))
/* 254:    */         {
/* 255:212 */           localObject1 = getVector(arrayOfString[1]);
/* 256:213 */           localObject2 = (ExplodableObject)((ExplodableObject)this.dexObjs.get(arrayOfString[2])).clone();
/* 257:214 */           ((ExplodableObject)localObject2).getMesh().getTransform3D().position.setTo((Vector3D)localObject1);
/* 258:215 */           this.renderer.addPolygon(((ExplodableObject)localObject2).getMesh());
/* 259:216 */           if (arrayOfString.length >= 4) {
/* 260:216 */             ((ExplodableObject)localObject2).getMesh().getTransform3D().rotY = Float.parseFloat(arrayOfString[3]);
/* 261:    */           }
/* 262:217 */           this.exObjs.add(localObject2);
/* 263:    */         }
/* 264:    */       }
/* 265:    */     }
/* 266:    */     catch (RuntimeException localRuntimeException)
/* 267:    */     {
/* 268:222 */       System.out.println("ERROR: " + paramString);
/* 269:223 */       throw new ObjectParsingException(localRuntimeException.getMessage());
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   private void loadMTLFile(String paramString)
/* 274:    */     throws IOException
/* 275:    */   {
/* 276:228 */     ArrayList localArrayList = new ArrayList();
/* 277:229 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(paramString)));
/* 278:230 */     System.out.println(getClass().getClassLoader().getResource(paramString));
/* 279:231 */     String str = null;
/* 280:232 */     while ((str = localBufferedReader.readLine()) != null)
/* 281:    */     {
/* 282:234 */       str = str.trim();
/* 283:235 */       if ((!str.startsWith("#")) && (!str.equals(""))) {
/* 284:236 */         localArrayList.add(str);
/* 285:    */       }
/* 286:    */     }
/* 287:238 */     localBufferedReader.close();
/* 288:239 */     localBufferedReader = null;
/* 289:240 */     Object localObject = null;
/* 290:241 */     Texture localTexture = null;
/* 291:242 */     for (int i = 0; i < localArrayList.size(); i++)
/* 292:    */     {
/* 293:244 */       String[] arrayOfString = ((String)localArrayList.get(i)).split(" ");
/* 294:245 */       if (arrayOfString[0].equals("newmtl"))
/* 295:    */       {
/* 296:245 */         localObject = arrayOfString[1];
/* 297:    */       }
/* 298:246 */       else if (arrayOfString[0].equals("map_Kd"))
/* 299:    */       {
/* 300:248 */         localTexture = loadTexture(arrayOfString[1]);
/* 301:249 */         this.textures.put(localObject, localTexture);
/* 302:    */       }
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   private Texture loadTexture(String paramString)
/* 307:    */     throws IOException
/* 308:    */   {
/* 309:255 */     Texture localTexture = TextureIO.newTexture(ImageIO.read(getClass().getClassLoader().getResource(paramString)), true);
/* 310:256 */     System.out.println(getClass().getClassLoader().getResource(paramString));
/* 311:257 */     localTexture.setTexParameteri(10242, 10497);
/* 312:258 */     localTexture.setTexParameteri(10243, 10497);
/* 313:259 */     return localTexture;
/* 314:    */   }
/* 315:    */   
/* 316:    */   private Vector3D getVector(String paramString)
/* 317:    */   {
/* 318:263 */     int i = Integer.parseInt(paramString);
/* 319:264 */     if (i < 0) {
/* 320:264 */       i = this.points.size() + i + 1;
/* 321:    */     }
/* 322:265 */     return (Vector3D)this.points.get(i - 1);
/* 323:    */   }
/* 324:    */   
/* 325:    */   private Portal getPortal(String paramString)
/* 326:    */   {
/* 327:269 */     int i = Integer.parseInt(paramString);
/* 328:270 */     if (i < 0) {
/* 329:270 */       i = this.portals.size() + i + 1;
/* 330:    */     }
/* 331:271 */     return (Portal)this.portals.get(i - 1);
/* 332:    */   }
/* 333:    */   
/* 334:    */   private void createFloorAndCeiling()
/* 335:    */   {
/* 336:275 */     Vector3D localVector3D1 = new Vector3D(this.boundsx1, 0.0F, this.boundsz1);
/* 337:276 */     Vector3D localVector3D2 = new Vector3D(this.boundsx2, 0.0F, this.boundsz1);
/* 338:277 */     Vector3D localVector3D3 = new Vector3D(this.boundsx2, 0.0F, this.boundsz2);
/* 339:278 */     Vector3D localVector3D4 = new Vector3D(this.boundsx1, 0.0F, this.boundsz2);
/* 340:279 */     TextureCoord localTextureCoord1 = new TextureCoord(this.boundsx1, this.boundsz1);
/* 341:280 */     TextureCoord localTextureCoord2 = new TextureCoord(this.boundsx2, this.boundsz1);
/* 342:281 */     TextureCoord localTextureCoord3 = new TextureCoord(this.boundsx2, this.boundsz2);
/* 343:282 */     TextureCoord localTextureCoord4 = new TextureCoord(this.boundsx1, this.boundsz2);
/* 344:283 */     TextureCoord[] arrayOfTextureCoord = { localTextureCoord1, localTextureCoord2, localTextureCoord3, localTextureCoord4 };
/* 345:284 */     this.floor = new Polygon3D(arrayOfTextureCoord, this.floorTexture, new Vector3D[] { localVector3D1, localVector3D4, localVector3D3, localVector3D2 });
/* 346:285 */     Vector3D localVector3D5 = new Vector3D(this.boundsx1, this.ceilingHeight, this.boundsz1);
/* 347:286 */     Vector3D localVector3D6 = new Vector3D(this.boundsx2, this.ceilingHeight, this.boundsz1);
/* 348:287 */     Vector3D localVector3D7 = new Vector3D(this.boundsx2, this.ceilingHeight, this.boundsz2);
/* 349:288 */     Vector3D localVector3D8 = new Vector3D(this.boundsx1, this.ceilingHeight, this.boundsz2);
/* 350:289 */     this.ceiling = new Polygon3D(arrayOfTextureCoord, this.ceilingTexture, new Vector3D[] { localVector3D5, localVector3D6, localVector3D7, localVector3D8 });
/* 351:    */   }
/* 352:    */   
/* 353:    */   private Polygon3D createWallPolygon(Vector3D paramVector3D1, Vector3D paramVector3D2)
/* 354:    */   {
/* 355:293 */     Vector3D[] arrayOfVector3D = new Vector3D[4];
/* 356:294 */     arrayOfVector3D[0] = paramVector3D1;
/* 357:295 */     arrayOfVector3D[1] = paramVector3D2;
/* 358:296 */     arrayOfVector3D[0].y = -0.1F;
/* 359:297 */     arrayOfVector3D[1].y = -0.1F;
/* 360:298 */     arrayOfVector3D[2] = ((Vector3D)paramVector3D2.clone());
/* 361:299 */     arrayOfVector3D[3] = ((Vector3D)paramVector3D1.clone());
/* 362:300 */     arrayOfVector3D[2].y = (this.ceilingHeight + 0.1F);
/* 363:301 */     arrayOfVector3D[3].y = (this.ceilingHeight + 0.1F);
/* 364:302 */     TextureCoord[] arrayOfTextureCoord = new TextureCoord[4];
/* 365:    */     int i;
/* 366:303 */     if (paramVector3D1.x == paramVector3D2.x) {
/* 367:305 */       for (i = 0; i < 4; i++) {
/* 368:307 */         arrayOfTextureCoord[i] = new TextureCoord(arrayOfVector3D[i].z, arrayOfVector3D[i].y);
/* 369:    */       }
/* 370:310 */     } else if (paramVector3D1.z == paramVector3D2.z) {
/* 371:312 */       for (i = 0; i < 4; i++) {
/* 372:314 */         arrayOfTextureCoord[i] = new TextureCoord(arrayOfVector3D[i].x, arrayOfVector3D[i].y);
/* 373:    */       }
/* 374:    */     }
/* 375:317 */     return new Polygon3D(arrayOfTextureCoord, this.currentTexture, arrayOfVector3D);
/* 376:    */   }
/* 377:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Map3DLoader
 * JD-Core Version:    0.7.0.1
 */