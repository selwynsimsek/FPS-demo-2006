/*   1:    */ package com.teamdead.leafpine;
/*   2:    */ 
/*   3:    */ import com.sun.opengl.util.Animator;
/*   4:    */ import java.nio.FloatBuffer;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import javax.media.opengl.GL;
/*   8:    */ import javax.media.opengl.GLAutoDrawable;
/*   9:    */ import javax.media.opengl.GLCanvas;
/*  10:    */ import javax.media.opengl.GLCapabilities;
/*  11:    */ import javax.media.opengl.GLEventListener;
/*  12:    */ import javax.media.opengl.glu.GLU;
/*  13:    */ 
/*  14:    */ public final class Renderer3D
/*  15:    */   implements GLEventListener
/*  16:    */ {
/*  17:    */   private ArrayList<Shape3D> polygons;
/*  18:    */   private static ArrayList<Runnable> startTasks;
/*  19:    */   private GLCanvas canvas;
/*  20: 18 */   private Light3D[] lights = new Light3D[8];
/*  21:    */   private long numberOfFrames;
/*  22:    */   private long totalTime;
/*  23:    */   private long timeTaken;
/*  24: 20 */   private static boolean hasInited = false;
/*  25: 21 */   private float ambientLightIntensity = 0.2F;
/*  26:    */   private Transform3D camera;
/*  27:    */   private float aspectRatio;
/*  28: 24 */   final FloatBuffer diffuseM = FloatBuffer.wrap(new float[] { 0.8F, 0.8F, 0.8F }, 0, 3);
/*  29: 25 */   final FloatBuffer specularM = FloatBuffer.wrap(new float[] { 0.2F, 0.2F, 0.2F }, 0, 3);
/*  30: 26 */   final int shininess = 17;
/*  31:    */   private Plane[] frustumPlanes;
/*  32:    */   private static final float FOCAL_LENGTH = 45.0F;
/*  33:    */   private static final float NEAR_PLANE = 1.0F;
/*  34:    */   private static final float FAR_PLANE = 3.4028235E+38F;
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 33 */       Class.forName("com.teamdead.leafpine.particle.Particle");
/*  41:    */     }
/*  42:    */     catch (ClassNotFoundException localClassNotFoundException)
/*  43:    */     {
/*  44: 37 */       localClassNotFoundException.printStackTrace();
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Renderer3D()
/*  49:    */   {
/*  50: 43 */     GLCapabilities localGLCapabilities = new GLCapabilities();
/*  51: 44 */     localGLCapabilities.setDoubleBuffered(true);
/*  52: 45 */     localGLCapabilities.setHardwareAccelerated(true);
/*  53: 46 */     this.canvas = new GLCanvas(localGLCapabilities);
/*  54: 47 */     this.canvas.addGLEventListener(this);
/*  55: 48 */     this.canvas.setFocusable(true);
/*  56: 49 */     this.canvas.requestFocus();
/*  57: 50 */     this.camera = new Transform3D();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setAmbientLightIntensity(float paramFloat)
/*  61:    */   {
/*  62: 54 */     this.ambientLightIntensity = paramFloat;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean addLight(Light3D paramLight3D)
/*  66:    */   {
/*  67: 58 */     for (int i = 0; i < this.lights.length; i++) {
/*  68: 60 */       if (this.lights[i] == null)
/*  69:    */       {
/*  70: 62 */         this.lights[i] = paramLight3D;
/*  71: 63 */         return true;
/*  72:    */       }
/*  73:    */     }
/*  74: 66 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean removeLight(Light3D paramLight3D)
/*  78:    */   {
/*  79: 70 */     for (int i = 0; i < this.lights.length; i++) {
/*  80: 72 */       if (this.lights[i] == paramLight3D)
/*  81:    */       {
/*  82: 74 */         this.lights[i] = null;
/*  83: 75 */         return true;
/*  84:    */       }
/*  85:    */     }
/*  86: 78 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Renderer3D(Shape3D... paramVarArgs)
/*  90:    */   {
/*  91: 83 */     this();
/*  92: 84 */     addPolygons(paramVarArgs);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void addPolygons(Shape3D... paramVarArgs)
/*  96:    */   {
/*  97: 89 */     if (this.polygons == null) {
/*  98: 89 */       this.polygons = new ArrayList();
/*  99:    */     }
/* 100: 90 */     synchronized (this.polygons)
/* 101:    */     {
/* 102: 92 */       for (int i = 0; i < paramVarArgs.length; i++) {
/* 103: 94 */         this.polygons.add(paramVarArgs[i]);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void addPolygon(Shape3D paramShape3D)
/* 109:    */   {
/* 110:101 */     if (this.polygons == null) {
/* 111:101 */       this.polygons = new ArrayList();
/* 112:    */     }
/* 113:102 */     synchronized (this.polygons)
/* 114:    */     {
/* 115:104 */       this.polygons.add(paramShape3D);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void removePolygons(Shape3D... paramVarArgs)
/* 120:    */   {
/* 121:109 */     synchronized (this.polygons)
/* 122:    */     {
/* 123:111 */       for (int i = 0; i < paramVarArgs.length; i++) {
/* 124:113 */         this.polygons.remove(paramVarArgs[i]);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void removePolygon(Shape3D paramShape3D)
/* 130:    */   {
/* 131:120 */     synchronized (this.polygons)
/* 132:    */     {
/* 133:122 */       this.polygons.remove(paramShape3D);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Transform3D getCamera()
/* 138:    */   {
/* 139:128 */     return this.camera;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public GLCanvas getCanvas()
/* 143:    */   {
/* 144:133 */     return this.canvas;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void init(GLAutoDrawable paramGLAutoDrawable)
/* 148:    */   {
/* 149:138 */     GL localGL = paramGLAutoDrawable.getGL();
/* 150:139 */     localGL.glShadeModel(7425);
/* 151:140 */     localGL.glClearColor(0.0F, 0.0F, 0.0F, 0.5F);
/* 152:141 */     localGL.glClearDepth(1.0D);
/* 153:142 */     localGL.glEnable(2929);
/* 154:143 */     localGL.glEnable(2884);
/* 155:144 */     localGL.glCullFace(1029);
/* 156:145 */     localGL.glDepthFunc(515);
/* 157:146 */     localGL.glHint(3152, 4354);
/* 158:147 */     localGL.glEnable(3553);
/* 159:148 */     localGL.glTexEnvi(8960, 8704, 8448);
/* 160:149 */     if (startTasks != null)
/* 161:    */     {
/* 162:151 */       Iterator localIterator = startTasks.iterator();
/* 163:152 */       while (localIterator.hasNext()) {
/* 164:154 */         ((Runnable)localIterator.next()).run();
/* 165:    */       }
/* 166:    */     }
/* 167:160 */     for (int i = 0; i < this.lights.length; i++) {
/* 168:162 */       if (this.lights[i] != null) {
/* 169:163 */         this.lights[i].setup(localGL, 16384 + i);
/* 170:    */       }
/* 171:    */     }
/* 172:165 */     hasInited = true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void reshape(GLAutoDrawable paramGLAutoDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/* 176:    */   {
/* 177:172 */     GL localGL = paramGLAutoDrawable.getGL();
/* 178:173 */     GLU localGLU = new GLU();
/* 179:174 */     if (paramInt4 <= 0) {
/* 180:174 */       paramInt4 = 1;
/* 181:    */     }
/* 182:175 */     this.aspectRatio = (paramInt3 / paramInt4);
/* 183:176 */     localGL.glMatrixMode(5889);
/* 184:177 */     localGL.glLoadIdentity();
/* 185:178 */     localGLU.gluPerspective(45.0D, this.aspectRatio, 1.0D, 3.402823466385289E+038D);
/* 186:    */     
/* 187:180 */     float[] arrayOfFloat = new float[16];
/* 188:181 */     localGL.glGetFloatv(2983, arrayOfFloat, 0);
/* 189:182 */     this.frustumPlanes = Plane.createViewFrustum(arrayOfFloat);
/* 190:183 */     localGL.glMatrixMode(5888);
/* 191:184 */     localGL.glLoadIdentity();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static void executeOnInit(Runnable paramRunnable)
/* 195:    */   {
/* 196:189 */     if (startTasks == null) {
/* 197:189 */       startTasks = new ArrayList();
/* 198:    */     }
/* 199:190 */     startTasks.add(paramRunnable);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void waitForInit()
/* 203:    */   {
/* 204:194 */     this.canvas.addNotify();
/* 205:195 */     new Animator(this.canvas).start();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public final void display(GLAutoDrawable paramGLAutoDrawable)
/* 209:    */   {
/* 210:200 */     if ((this.polygons == null) || (this.polygons.isEmpty())) {
/* 211:200 */       return;
/* 212:    */     }
/* 213:201 */     this.timeTaken = System.currentTimeMillis();
/* 214:202 */     GL localGL = paramGLAutoDrawable.getGL();
/* 215:203 */     localGL.glLoadIdentity();
/* 216:204 */     localGL.glClear(16640);
/* 217:205 */     localGL.glEnable(3553);
/* 218:206 */     this.camera.applySubtract(localGL);
/* 219:    */     
/* 220:    */ 
/* 221:    */ 
/* 222:210 */     Object localObject1 = null;
/* 223:211 */     int i = 0;
/* 224:212 */     float[] arrayOfFloat = new float[16];
/* 225:213 */     localGL.glGetFloatv(2982, arrayOfFloat, 0);
/* 226:214 */     Object localObject2 = null;
/* 227:215 */     synchronized (this.polygons)
/* 228:    */     {
/* 229:217 */       for (int j = 0; j < this.polygons.size(); j++)
/* 230:    */       {
/* 231:219 */         Shape3D localShape3D = (Shape3D)this.polygons.get(j);
/* 232:220 */         localShape3D.render(localGL, this.camera, this.frustumPlanes, arrayOfFloat);
/* 233:    */       }
/* 234:    */     }
/* 235:223 */     this.timeTaken = (System.currentTimeMillis() - this.timeTaken);
/* 236:224 */     this.numberOfFrames += 1L;
/* 237:225 */     this.totalTime += this.timeTaken;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void displayChanged(GLAutoDrawable paramGLAutoDrawable, boolean paramBoolean1, boolean paramBoolean2) {}
/* 241:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.Renderer3D
 * JD-Core Version:    0.7.0.1
 */