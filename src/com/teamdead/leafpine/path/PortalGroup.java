/*  1:   */ package com.teamdead.leafpine.path;
/*  2:   */ 
/*  3:   */ import com.teamdead.leafpine.Vector3D;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ 
/*  6:   */ public class PortalGroup
/*  7:   */ {
/*  8:   */   private float x;
/*  9:   */   private float z;
/* 10:   */   private float width;
/* 11:   */   private float length;
/* 12:   */   ArrayList<Portal> portals;
/* 13:   */   
/* 14:   */   public PortalGroup(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/* 15:   */   {
/* 16:13 */     this.portals = new ArrayList();
/* 17:14 */     this.x = paramFloat1;
/* 18:15 */     this.z = paramFloat2;
/* 19:16 */     this.width = paramFloat3;
/* 20:17 */     this.length = paramFloat4;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean withinBounds(Portal paramPortal)
/* 24:   */   {
/* 25:21 */     return withinBounds(paramPortal.getPoint());
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean withinBounds(Vector3D paramVector3D)
/* 29:   */   {
/* 30:25 */     return true;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void addNewPortal(Portal paramPortal)
/* 34:   */   {
/* 35:32 */     for (int i = 0; i < this.portals.size(); i++)
/* 36:   */     {
/* 37:34 */       Portal localPortal = (Portal)this.portals.get(i);
/* 38:35 */       localPortal.addNeighbour(paramPortal);
/* 39:36 */       paramPortal.addNeighbour(localPortal);
/* 40:   */     }
/* 41:38 */     this.portals.add(paramPortal);
/* 42:39 */     paramPortal.registerGroup(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String toString()
/* 46:   */   {
/* 47:43 */     StringBuffer localStringBuffer = new StringBuffer();
/* 48:44 */     for (int i = 0; i < this.portals.size(); i++)
/* 49:   */     {
/* 50:46 */       localStringBuffer.append(((Portal)this.portals.get(i)).toString());
/* 51:47 */       localStringBuffer.append('\n');
/* 52:   */     }
/* 53:49 */     return localStringBuffer.toString();
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.path.PortalGroup
 * JD-Core Version:    0.7.0.1
 */