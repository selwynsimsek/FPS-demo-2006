/*  1:   */ package com.teamdead.leafpine.search;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ public abstract class AStarNode
/*  6:   */   implements Comparable<AStarNode>
/*  7:   */ {
/*  8:   */   AStarNode pathParent;
/*  9:   */   float costFromStart;
/* 10:   */   float estimatedCostToGoal;
/* 11:   */   
/* 12:   */   public float getCost()
/* 13:   */   {
/* 14:10 */     return this.costFromStart + this.estimatedCostToGoal;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int compareTo(AStarNode paramAStarNode)
/* 18:   */   {
/* 19:14 */     float f1 = getCost();
/* 20:15 */     float f2 = paramAStarNode.getCost();
/* 21:16 */     float f3 = f1 - f2;
/* 22:17 */     if (f3 < 0.0F) {
/* 23:17 */       return -1;
/* 24:   */     }
/* 25:18 */     if (f3 > 0.0F) {
/* 26:18 */       return 1;
/* 27:   */     }
/* 28:19 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public abstract float getCost(AStarNode paramAStarNode);
/* 32:   */   
/* 33:   */   public abstract float getEstimatedCost(AStarNode paramAStarNode);
/* 34:   */   
/* 35:   */   public abstract List<AStarNode> getNeighbours();
/* 36:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.search.AStarNode
 * JD-Core Version:    0.7.0.1
 */