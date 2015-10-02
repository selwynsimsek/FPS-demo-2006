/*  1:   */ package com.teamdead.leafpine.path;
/*  2:   */ 
/*  3:   */ import com.teamdead.leafpine.Vector3D;
/*  4:   */ import com.teamdead.leafpine.search.AStarNode;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class Portal
/*  9:   */   extends AStarNode
/* 10:   */ {
/* 11:   */   private List<AStarNode> neighbours;
/* 12:   */   private List<PortalGroup> rooms;
/* 13:   */   private Vector3D point;
/* 14:   */   
/* 15:   */   public Portal(Vector3D paramVector3D)
/* 16:   */   {
/* 17:14 */     this.neighbours = new ArrayList();
/* 18:15 */     this.point = paramVector3D;
/* 19:16 */     this.rooms = new ArrayList();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Vector3D getPoint()
/* 23:   */   {
/* 24:20 */     return this.point;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void addNeighbour(Portal paramPortal)
/* 28:   */   {
/* 29:24 */     this.neighbours.add(paramPortal);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void registerGroup(PortalGroup paramPortalGroup)
/* 33:   */   {
/* 34:28 */     this.rooms.add(paramPortalGroup);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void destroy()
/* 38:   */   {
/* 39:32 */     for (int i = 0; i < this.rooms.size(); i++)
/* 40:   */     {
/* 41:34 */       PortalGroup localPortalGroup = (PortalGroup)this.rooms.get(i);
/* 42:35 */       localPortalGroup.portals.remove(this);
/* 43:36 */       for (int j = 0; j < localPortalGroup.portals.size(); j++)
/* 44:   */       {
/* 45:38 */         Portal localPortal = (Portal)localPortalGroup.portals.get(j);
/* 46:39 */         localPortal.removeNeighbour(this);
/* 47:   */       }
/* 48:   */     }
/* 49:42 */     this.neighbours.clear();
/* 50:43 */     this.rooms.clear();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void removeNeighbour(Portal paramPortal)
/* 54:   */   {
/* 55:47 */     this.neighbours.remove(paramPortal);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public float getCost(AStarNode paramAStarNode)
/* 59:   */   {
/* 60:51 */     return getEstimatedCost(paramAStarNode);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public List<AStarNode> getNeighbours()
/* 64:   */   {
/* 65:55 */     return this.neighbours;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public float getEstimatedCost(AStarNode paramAStarNode)
/* 69:   */   {
/* 70:59 */     if ((paramAStarNode instanceof Portal))
/* 71:   */     {
/* 72:61 */       Portal localPortal = (Portal)paramAStarNode;
/* 73:62 */       float f1 = this.point.x - localPortal.point.x;
/* 74:63 */       float f2 = this.point.z - localPortal.point.z;
/* 75:64 */       return (float)Math.sqrt(f1 * f1 + f2 * f2);
/* 76:   */     }
/* 77:66 */     return paramAStarNode.getEstimatedCost(this);
/* 78:   */   }
/* 79:   */   
/* 80:   */   public String toString()
/* 81:   */   {
/* 82:70 */     return "portal: " + this.point.toString();
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.path.Portal
 * JD-Core Version:    0.7.0.1
 */