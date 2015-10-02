/*  1:   */ import com.teamdead.leafpine.ObjectParsingException;
/*  2:   */ import com.teamdead.leafpine.PolygonMesh;
/*  3:   */ import com.teamdead.leafpine.PolygonMeshLoader;
/*  4:   */ import com.teamdead.leafpine.Renderer3D;
/*  5:   */ import com.teamdead.leafpine.Sphere;
/*  6:   */ import com.teamdead.leafpine.Vector3D;
/*  7:   */ import java.io.IOException;
/*  8:   */ 
/*  9:   */ public class Grenade
/* 10:   */ {
/* 11: 6 */   private static Vector3D temp1 = new Vector3D();
/* 12:   */   private static PolygonMesh mesh;
/* 13:   */   private PolygonMesh grenade;
/* 14: 9 */   private long timeSinceThrown = 0L;
/* 15:   */   private Renderer3D renderer;
/* 16:   */   private static final int EXPLOSION_TIME = 5000;
/* 17:   */   private Vector3D velocity;
/* 18:   */   private Vector3D position;
/* 19:   */   private static final float GRAVITY = 2.0E-005F;
/* 20:   */   
/* 21:   */   public static void init()
/* 22:   */   {
/* 23:   */     try
/* 24:   */     {
/* 25:18 */       mesh = new PolygonMeshLoader().loadObject(new Grenade().getClass().getClassLoader().getResource("grenade.obj"));
/* 26:   */     }
/* 27:   */     catch (IOException localIOException)
/* 28:   */     {
/* 29:22 */       localIOException.printStackTrace();
/* 30:   */     }
/* 31:   */     catch (ObjectParsingException localObjectParsingException)
/* 32:   */     {
/* 33:26 */       localObjectParsingException.printStackTrace();
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   private Grenade() {}
/* 38:   */   
/* 39:   */   public Grenade(Vector3D paramVector3D1, Vector3D paramVector3D2, Renderer3D paramRenderer3D)
/* 40:   */   {
/* 41:32 */     this.position = paramVector3D1;
/* 42:33 */     this.velocity = paramVector3D2;
/* 43:34 */     this.grenade = ((PolygonMesh)mesh.clone());
/* 44:35 */     this.grenade.getTransform3D().position = this.position;
/* 45:36 */     this.renderer = paramRenderer3D;
/* 46:37 */     paramRenderer3D.addPolygon(this.grenade);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Sphere getBounds()
/* 50:   */   {
/* 51:41 */     return this.grenade.getBounds();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void update(long paramLong)
/* 55:   */   {
/* 56:45 */     this.timeSinceThrown += paramLong;
/* 57:46 */     if (this.position.y > 0.0F)
/* 58:   */     {
/* 59:48 */       temp1.setTo(this.velocity);
/* 60:49 */       temp1.multiply((float)paramLong);
/* 61:50 */       this.position.add(temp1);
/* 62:51 */       this.velocity.y -= 2.0E-005F * (float)paramLong;
/* 63:   */     }
/* 64:   */     else
/* 65:   */     {
/* 66:53 */       this.position.y = 0.0F;
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean shouldDestroy()
/* 71:   */   {
/* 72:57 */     return this.timeSinceThrown > 5000L;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void destroy()
/* 76:   */   {
/* 77:61 */     this.renderer.removePolygon(this.grenade);
/* 78:   */   }
/* 79:   */   
/* 80:   */   public Vector3D getPosition()
/* 81:   */   {
/* 82:65 */     return this.position;
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\code.jar
 * Qualified Name:     Grenade
 * JD-Core Version:    0.7.0.1
 */