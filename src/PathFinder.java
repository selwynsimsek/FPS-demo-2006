/*  1:   */ import com.teamdead.leafpine.PolygonMesh;
/*  2:   */ import com.teamdead.leafpine.Transform3D;
/*  3:   */ import com.teamdead.leafpine.Vector3D;
/*  4:   */ import com.teamdead.leafpine.path.Map3D;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.util.Iterator;
/*  7:   */ 
/*  8:   */ public class PathFinder
/*  9:   */ {
/* 10:   */   private Map3D map;
/* 11:   */   private PolygonMesh mesh;
/* 12:   */   private Iterator<Vector3D> path;
/* 13:   */   private Vector3D currentDest;
/* 14:   */   public static final float MOVE_DIST = 0.001F;
/* 15:   */   private Transform3D camera;
/* 16:   */   
/* 17:   */   public PathFinder(PolygonMesh paramPolygonMesh, Map3D paramMap3D, Transform3D paramTransform3D)
/* 18:   */   {
/* 19:15 */     this.mesh = paramPolygonMesh;
/* 20:16 */     this.map = paramMap3D;
/* 21:17 */     this.camera = paramTransform3D;
/* 22:18 */     recalcPath();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void recalcPath()
/* 26:   */   {
/* 27:22 */     System.out.println("recalcPath()");
/* 28:23 */     this.path = this.map.findPath(this.mesh.getTransform3D().position, this.camera.position);
/* 29:24 */     if (this.path != null) {
/* 30:24 */       this.currentDest = ((Vector3D)this.path.next());
/* 31:   */     } else {
/* 32:25 */       System.out.println("path==null!");
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void update(long paramLong)
/* 37:   */   {
/* 38:29 */     Vector3D localVector3D1 = this.mesh.getTransform3D().position;
/* 39:30 */     if (this.currentDest == null) {
/* 40:30 */       return;
/* 41:   */     }
/* 42:31 */     Vector3D localVector3D2 = new Vector3D();
/* 43:32 */     Vector3D localVector3D3 = new Vector3D();
/* 44:33 */     localVector3D3.setTo(this.currentDest);
/* 45:34 */     localVector3D3.subtract(localVector3D1);
/* 46:35 */     localVector3D2.setTo(localVector3D3);
/* 47:36 */     localVector3D2.normalize();
/* 48:37 */     localVector3D2.multiply((float)paramLong);
/* 49:38 */     localVector3D2.multiply(0.001F);
/* 50:39 */     if (localVector3D2.lengthSquared() > localVector3D3.lengthSquared())
/* 51:   */     {
/* 52:41 */       localVector3D1.setTo(this.currentDest);
/* 53:42 */       this.currentDest = ((Vector3D)this.path.next());
/* 54:   */     }
/* 55:   */     else
/* 56:   */     {
/* 57:46 */       localVector3D1.add(localVector3D2);
/* 58:   */     }
/* 59:48 */     if (Float.isNaN(localVector3D1.x)) {
/* 60:48 */       localVector3D1 = new Vector3D(90.0F, 0.0F, 120.0F);
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\code.jar
 * Qualified Name:     PathFinder
 * JD-Core Version:    0.7.0.1
 */