/*   1:    */ import com.sun.opengl.util.texture.Texture;
/*   2:    */ import com.sun.opengl.util.texture.TextureIO;
/*   3:    */ import com.teamdead.leafpine.CaptureScreenThread;
/*   4:    */ import com.teamdead.leafpine.Decal;
/*   5:    */ import com.teamdead.leafpine.Map3DLoader;
/*   6:    */ import com.teamdead.leafpine.ObjectParsingException;
/*   7:    */ import com.teamdead.leafpine.PlayBackgroundThread;
/*   8:    */ import com.teamdead.leafpine.Polygon3D;
/*   9:    */ import com.teamdead.leafpine.PolygonMesh;
/*  10:    */ import com.teamdead.leafpine.Renderer3D;
/*  11:    */ import com.teamdead.leafpine.Shape3D;
/*  12:    */ import com.teamdead.leafpine.Sphere;
/*  13:    */ import com.teamdead.leafpine.Transform3D;
/*  14:    */ import com.teamdead.leafpine.Vector3D;
/*  15:    */ import com.teamdead.leafpine.particle.ParticleSystem;
/*  16:    */ import com.teamdead.leafpine.path.Map3D;
/*  17:    */ import java.applet.Applet;
/*  18:    */ import java.applet.AudioClip;
/*  19:    */ import java.awt.AWTException;
/*  20:    */ import java.awt.BorderLayout;
/*  21:    */ import java.awt.Color;
/*  22:    */ import java.awt.Component;
/*  23:    */ import java.awt.Container;
/*  24:    */ import java.awt.Cursor;
/*  25:    */ import java.awt.Point;
/*  26:    */ import java.awt.Robot;
/*  27:    */ import java.awt.Toolkit;
/*  28:    */ import java.awt.event.KeyEvent;
/*  29:    */ import java.awt.event.KeyListener;
/*  30:    */ import java.awt.event.MouseAdapter;
/*  31:    */ import java.awt.event.MouseEvent;
/*  32:    */ import java.awt.event.MouseMotionListener;
/*  33:    */ import java.awt.event.WindowAdapter;
/*  34:    */ import java.awt.event.WindowEvent;
/*  35:    */ import java.io.IOException;
/*  36:    */ import java.util.ArrayList;
/*  37:    */ import javax.imageio.ImageIO;
/*  38:    */ import javax.media.opengl.GLCanvas;
/*  39:    */ import javax.swing.JFrame;
/*  40:    */ import javax.swing.SwingUtilities;
/*  41:    */ 
/*  42:    */ public class MapExplorer
/*  43:    */   implements KeyListener, MouseMotionListener, Runnable
/*  44:    */ {
/*  45: 19 */   Vector3D temp3 = new Vector3D();
/*  46: 19 */   Vector3D temp1 = new Vector3D();
/*  47: 19 */   Vector3D temp2 = new Vector3D();
/*  48: 20 */   ArrayList<ParticleSystem> systems = new ArrayList();
/*  49: 23 */   long timeSincePathRecalc = 0L;
/*  50: 36 */   private boolean soundOn = false;
/*  51: 38 */   public static final Cursor INVISIBLE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(0, 0), "invisible");
/*  52:    */   Texture scorch;
/*  53:    */   AudioClip throwSound;
/*  54:    */   AudioClip explodeSound;
/*  55:    */   Transform3D camera;
/*  56:    */   Transform3D tmp1;
/*  57:    */   Transform3D tmp2;
/*  58:    */   Transform3D tmp3;
/*  59:    */   private boolean isJumping;
/*  60:    */   Polygon3D[] walls;
/*  61:    */   private float velocityY;
/*  62:    */   JFrame window;
/*  63:    */   Map3D map;
/*  64:    */   boolean isForwardPressed;
/*  65:    */   boolean isBackwardPressed;
/*  66:    */   boolean isRunPressed;
/*  67:    */   boolean isJumpPressed;
/*  68:    */   ArrayList<Grenade> grenades;
/*  69:    */   PathFinder bot;
/*  70:    */   Robot robot;
/*  71:    */   private static final float GRAVITY = 4.0E-005F;
/*  72:    */   private static final float VELOCITY_UP = 0.015F;
/*  73:    */   private static final float PLAYER_HEIGHT = 1.5F;
/*  74:    */   private static final float RECALC_TIME = 10000.0F;
/*  75:    */   private static final float RUN_SPEED = 1.5F;
/*  76:    */   CollisionDetection collision;
/*  77:    */   Renderer3D renderer;
/*  78:    */   boolean isRecentering;
/*  79:    */   PolygonMesh mesh;
/*  80:    */   Point centerPoint;
/*  81:    */   
/*  82:    */   public static void main(String[] paramArrayOfString)
/*  83:    */   {
/*  84: 41 */     if (paramArrayOfString.length == 0) {
/*  85: 41 */       new MapExplorer(true);
/*  86:    */     } else {
/*  87: 42 */       new MapExplorer(Boolean.parseBoolean(paramArrayOfString[0]));
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public MapExplorer(boolean paramBoolean)
/*  92:    */   {
/*  93: 46 */     this.window = ScreenManager.createFullScreenWindow(paramBoolean);
/*  94: 47 */     WindowAdapter local1 = new WindowAdapter()
/*  95:    */     {
/*  96:    */       public void windowClosing(WindowEvent paramAnonymousWindowEvent)
/*  97:    */       {
/*  98: 51 */         MapExplorer.this.window.dispose();
/*  99: 52 */         MapExplorer.this.window = null;
/* 100: 53 */         System.exit(0);
/* 101:    */       }
/* 102: 55 */     };
/* 103: 56 */     this.window.addWindowListener(local1);
/* 104: 57 */     this.renderer = new Renderer3D();
/* 105: 58 */     this.camera = this.renderer.getCamera();
/* 106: 59 */     this.camera.position.y = 1.5F;
/* 107: 60 */     this.throwSound = Applet.newAudioClip(getClass().getClassLoader().getResource("throw.wav"));
/* 108: 61 */     this.explodeSound = Applet.newAudioClip(getClass().getClassLoader().getResource("explode.wav"));
/* 109: 62 */     this.collision = new CollisionDetection();
/* 110: 63 */     Runnable local2 = new Runnable()
/* 111:    */     {
/* 112:    */       public void run()
/* 113:    */       {
/* 114:    */         try
/* 115:    */         {
/* 116: 69 */           Map3DLoader localMap3DLoader = new Map3DLoader();
/* 117: 70 */           MapExplorer.this.map = localMap3DLoader.parseMap("room2.bld", MapExplorer.this.renderer);
/* 118: 71 */           MapExplorer.this.walls = MapExplorer.this.map.getWalls();
/* 119:    */           
/* 120:    */ 
/* 121: 74 */           Grenade.init();
/* 122: 75 */           MapExplorer.this.scorch = TextureIO.newTexture(ImageIO.read(getClass().getClassLoader().getResource("scorch.png")), true);
/* 123:    */           
/* 124:    */ 
/* 125:    */ 
/* 126: 79 */           MapExplorer.this.renderer.setAmbientLightIntensity(0.4F);
/* 127:    */         }
/* 128:    */         catch (ObjectParsingException localObjectParsingException)
/* 129:    */         {
/* 130: 83 */           localObjectParsingException.printStackTrace();
/* 131:    */         }
/* 132:    */         catch (IOException localIOException)
/* 133:    */         {
/* 134: 87 */           localIOException.printStackTrace();
/* 135:    */         }
/* 136:    */       }
/* 137: 90 */     };
/* 138: 91 */     Renderer3D.executeOnInit(local2);
/* 139: 92 */     Container localContainer = this.window.getContentPane();
/* 140: 93 */     localContainer.setLayout(new BorderLayout());
/* 141: 94 */     localContainer.add("Center", this.renderer.getCanvas());
/* 142: 95 */     this.window.setContentPane(localContainer);
/* 143: 96 */     GLCanvas localGLCanvas = this.renderer.getCanvas();
/* 144: 97 */     localGLCanvas.addMouseMotionListener(this);
/* 145: 98 */     localGLCanvas.addKeyListener(this);
/* 146: 99 */     MouseAdapter local3 = new MouseAdapter()
/* 147:    */     {
/* 148:    */       public void mousePressed(MouseEvent paramAnonymousMouseEvent)
/* 149:    */       {
/* 150:103 */         MapExplorer.this.throwGrenade();
/* 151:    */       }
/* 152:105 */     };
/* 153:106 */     localGLCanvas.addMouseListener(local3);
/* 154:107 */     this.window.setCursor(INVISIBLE_CURSOR);
/* 155:    */     try
/* 156:    */     {
/* 157:110 */       this.robot = new Robot();
/* 158:    */     }
/* 159:    */     catch (AWTException localAWTException)
/* 160:    */     {
/* 161:114 */       localAWTException.printStackTrace();
/* 162:    */     }
/* 163:116 */     this.centerPoint = new Point(this.window.getWidth() / 2, this.window.getHeight() / 2);
/* 164:117 */     recenterMouse();
/* 165:118 */     this.tmp1 = new Transform3D();
/* 166:119 */     this.tmp2 = new Transform3D();
/* 167:120 */     this.tmp3 = new Transform3D();
/* 168:121 */     new Thread(this).start();
/* 169:122 */     this.renderer.waitForInit();
/* 170:123 */     localGLCanvas.requestFocus();
/* 171:124 */     this.window.setVisible(true);
/* 172:125 */     this.camera.position.y = 1.5F;
/* 173:126 */     if (this.soundOn) {
/* 174:126 */       new PlayBackgroundThread(getClass().getClassLoader().getResource("music.ogg")).start();
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void throwGrenade()
/* 179:    */   {
/* 180:130 */     this.throwSound.play();
/* 181:131 */     if (this.grenades == null) {
/* 182:131 */       this.grenades = new ArrayList();
/* 183:    */     }
/* 184:132 */     Vector3D localVector3D1 = new Vector3D(this.camera.position);
/* 185:133 */     float f1 = -(float)Math.sin(Math.toRadians(this.camera.rotY));
/* 186:134 */     float f2 = -(float)Math.cos(Math.toRadians(this.camera.rotY));
/* 187:135 */     float f3 = (float)Math.cos(Math.toRadians(this.camera.rotX));
/* 188:136 */     float f4 = (float)Math.sin(Math.toRadians(this.camera.rotX));
/* 189:137 */     Vector3D localVector3D2 = new Vector3D(f3 * f1 * 3.0F, f4, f3 * f2 * 3.0F);
/* 190:138 */     localVector3D2.divide(50.0F);
/* 191:139 */     Grenade localGrenade = new Grenade(localVector3D1, localVector3D2, this.renderer);
/* 192:140 */     this.grenades.add(localGrenade);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public synchronized void recenterMouse()
/* 196:    */   {
/* 197:144 */     this.isRecentering = true;
/* 198:145 */     this.robot.mouseMove(this.centerPoint.x, this.centerPoint.y);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void mouseDragged(MouseEvent paramMouseEvent)
/* 202:    */   {
/* 203:149 */     mouseMoved(paramMouseEvent);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void mouseMoved(MouseEvent paramMouseEvent)
/* 207:    */   {
/* 208:153 */     if (this.centerPoint == null) {
/* 209:153 */       return;
/* 210:    */     }
/* 211:154 */     if (this.isRecentering)
/* 212:    */     {
/* 213:156 */       this.isRecentering = false;
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:160 */       Point localPoint = paramMouseEvent.getPoint();
/* 218:161 */       SwingUtilities.convertPointToScreen(localPoint, this.renderer.getCanvas());
/* 219:162 */       int i = localPoint.x - this.centerPoint.x;
/* 220:163 */       int j = localPoint.y - this.centerPoint.y;
/* 221:164 */       this.camera.rotY -= i * 0.04F;
/* 222:165 */       this.camera.rotX -= j * 0.04F;
/* 223:166 */       recalcCameraRotBounds();
/* 224:167 */       recenterMouse();
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void keyPressed(KeyEvent paramKeyEvent)
/* 229:    */   {
/* 230:172 */     switch (paramKeyEvent.getKeyCode())
/* 231:    */     {
/* 232:    */     case 87: 
/* 233:174 */       this.isForwardPressed = true; break;
/* 234:    */     case 83: 
/* 235:175 */       this.isBackwardPressed = true; break;
/* 236:    */     case 82: 
/* 237:176 */       this.isRunPressed = true; break;
/* 238:    */     case 67: 
/* 239:177 */       new CaptureScreenThread(this.robot).start(); break;
/* 240:    */     case 32: 
/* 241:178 */       this.isJumpPressed = true; break;
/* 242:    */     case 27: 
/* 243:179 */       exit();
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   private void exit()
/* 248:    */   {
/* 249:184 */     this.window.dispose();
/* 250:185 */     this.window = null;
/* 251:186 */     System.exit(0);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void keyReleased(KeyEvent paramKeyEvent)
/* 255:    */   {
/* 256:190 */     switch (paramKeyEvent.getKeyCode())
/* 257:    */     {
/* 258:    */     case 87: 
/* 259:192 */       this.isForwardPressed = false; break;
/* 260:    */     case 83: 
/* 261:193 */       this.isBackwardPressed = false; break;
/* 262:    */     case 82: 
/* 263:194 */       this.isRunPressed = false; break;
/* 264:    */     case 32: 
/* 265:195 */       this.isJumpPressed = false;
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void keyTyped(KeyEvent paramKeyEvent) {}
/* 270:    */   
/* 271:    */   public void moveForward(float paramFloat)
/* 272:    */   {
/* 273:201 */     this.temp2.setTo(this.camera.position);
/* 274:202 */     this.temp2.x -= (float)Math.sin(Math.toRadians(this.camera.rotY)) * paramFloat / 75.0F;
/* 275:203 */     this.temp2.z -= (float)Math.cos(Math.toRadians(this.camera.rotY)) * paramFloat / 75.0F;
/* 276:204 */     Vector3D localVector3D = performCollisionDetection(this.camera.position, this.temp2);
/* 277:205 */     this.camera.position.setTo(localVector3D);
/* 278:    */   }
/* 279:    */   
/* 280:    */   private void recalcCameraRotBounds()
/* 281:    */   {
/* 282:209 */     Transform3D localTransform3D = this.renderer.getCamera();
/* 283:210 */     if (localTransform3D.rotY < 0.0F) {
/* 284:210 */       localTransform3D.rotY += 360.0F;
/* 285:    */     }
/* 286:211 */     if (localTransform3D.rotY > 360.0F) {
/* 287:211 */       localTransform3D.rotY -= 360.0F;
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void moveBackward(float paramFloat)
/* 292:    */   {
/* 293:215 */     this.temp2.setTo(this.camera.position);
/* 294:216 */     this.temp2.x += (float)Math.sin(Math.toRadians(this.camera.rotY)) * paramFloat / 113.0F;
/* 295:217 */     this.temp2.z += (float)Math.cos(Math.toRadians(this.camera.rotY)) * paramFloat / 113.0F;
/* 296:218 */     Vector3D localVector3D = performCollisionDetection(this.camera.position, this.temp2);
/* 297:219 */     this.camera.position.setTo(localVector3D);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void run()
/* 301:    */   {
/* 302:223 */     long l1 = System.currentTimeMillis();
/* 303:224 */     long l2 = l1 - System.currentTimeMillis();
/* 304:    */     for (;;)
/* 305:    */     {
/* 306:    */       Object localObject;
/* 307:227 */       if (this.grenades != null) {
/* 308:229 */         for (i = 0; i < this.grenades.size(); i++)
/* 309:    */         {
/* 310:231 */           localObject = (Grenade)this.grenades.get(i);
/* 311:232 */           ((Grenade)localObject).update(l2);
/* 312:233 */           ParticleSystem localParticleSystem1 = this.map.performExplosions(this.renderer, ((Grenade)localObject).getBounds());
/* 313:234 */           if (localParticleSystem1 != null)
/* 314:    */           {
/* 315:236 */             this.explodeSound.play();
/* 316:237 */             this.grenades.remove(localObject);
/* 317:238 */             ((Grenade)localObject).destroy();
/* 318:239 */             this.systems.add(localParticleSystem1);
/* 319:    */           }
/* 320:241 */           else if (((Grenade)localObject).shouldDestroy())
/* 321:    */           {
/* 322:243 */             this.explodeSound.play();
/* 323:244 */             ((Grenade)localObject).destroy();
/* 324:245 */             this.grenades.remove(localObject);
/* 325:246 */             Vector3D localVector3D = ((Grenade)localObject).getPosition();
/* 326:247 */             localVector3D.y = 0.0F;
/* 327:248 */             this.renderer.addPolygon(new Decal(localVector3D.x, localVector3D.z, this.scorch));
/* 328:249 */             ParticleSystem localParticleSystem2 = new ParticleSystem(localVector3D, new Color[] { Color.red, Color.orange, Color.yellow });
/* 329:250 */             this.renderer.addPolygon(localParticleSystem2);
/* 330:251 */             this.systems.add(localParticleSystem2);
/* 331:    */           }
/* 332:    */         }
/* 333:    */       }
/* 334:255 */       for (int i = 0; i < this.systems.size(); i++)
/* 335:    */       {
/* 336:257 */         localObject = (ParticleSystem)this.systems.get(i);
/* 337:258 */         ((ParticleSystem)localObject).update(l2);
/* 338:259 */         if (((ParticleSystem)localObject).ended())
/* 339:    */         {
/* 340:261 */           this.systems.remove(i);
/* 341:262 */           this.renderer.removePolygon((Shape3D)localObject);
/* 342:263 */           break;
/* 343:    */         }
/* 344:    */       }
/* 345:266 */       if (this.isRunPressed) {
/* 346:266 */         moveForward((float)l2 * 1.5F);
/* 347:267 */       } else if (this.isForwardPressed) {
/* 348:267 */         moveForward((float)l2);
/* 349:    */       }
/* 350:268 */       if (this.isBackwardPressed) {
/* 351:268 */         moveBackward((float)l2);
/* 352:    */       }
/* 353:269 */       if (this.isJumpPressed) {
/* 354:269 */         jump();
/* 355:    */       }
/* 356:270 */       if (this.isJumping)
/* 357:    */       {
/* 358:272 */         this.velocityY -= 4.0E-005F * (float)l2;
/* 359:273 */         this.renderer.getCamera().position.y += this.velocityY * (float)l2;
/* 360:274 */         if (this.renderer.getCamera().position.y < 1.5F)
/* 361:    */         {
/* 362:276 */           this.renderer.getCamera().position.y = 1.5F;
/* 363:277 */           this.isJumping = false;
/* 364:    */         }
/* 365:    */       }
/* 366:282 */       else if (this.renderer.getCamera().position.y < 1.5F)
/* 367:    */       {
/* 368:282 */         this.renderer.getCamera().position.y = 1.5F;
/* 369:    */       }
/* 370:    */       try
/* 371:    */       {
/* 372:286 */         Thread.sleep(20L);
/* 373:    */       }
/* 374:    */       catch (InterruptedException localInterruptedException) {}
/* 375:289 */       l2 = System.currentTimeMillis() - l1;
/* 376:290 */       l1 += l2;
/* 377:    */     }
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void jump()
/* 381:    */   {
/* 382:295 */     if (this.isJumping) {
/* 383:295 */       return;
/* 384:    */     }
/* 385:296 */     this.isJumping = true;
/* 386:297 */     this.velocityY = 0.015F;
/* 387:    */   }
/* 388:    */   
/* 389:    */   private Vector3D performCollisionDetection(Vector3D paramVector3D1, Vector3D paramVector3D2)
/* 390:    */   {
/* 391:302 */     Object[] arrayOfObject = this.collision.intersects(paramVector3D1, paramVector3D2, this.walls);
/* 392:303 */     if (arrayOfObject != null) {
/* 393:303 */       return paramVector3D1;
/* 394:    */     }
/* 395:305 */     Sphere localSphere1 = new Sphere(1.5F, (Vector3D)paramVector3D1.clone());
/* 396:306 */     Sphere localSphere2 = new Sphere(1.5F, (Vector3D)paramVector3D2.clone());
/* 397:307 */     localSphere1.location.y = 1.5F;
/* 398:308 */     localSphere2.location.y = 1.5F;
/* 399:    */     
/* 400:    */ 
/* 401:    */ 
/* 402:    */ 
/* 403:    */ 
/* 404:314 */     boolean bool1 = this.map.collidesWith(localSphere1);
/* 405:315 */     boolean bool2 = this.map.collidesWith(localSphere2);
/* 406:316 */     if (bool2) {
/* 407:318 */       if (!bool1) {
/* 408:318 */         return paramVector3D1;
/* 409:    */       }
/* 410:    */     }
/* 411:320 */     return paramVector3D2;
/* 412:    */   }
/* 413:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\code.jar
 * Qualified Name:     MapExplorer
 * JD-Core Version:    0.7.0.1
 */