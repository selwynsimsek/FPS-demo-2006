/*  1:   */ package com.teamdead.leafpine.search;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class AStarSearch
/*  8:   */ {
/*  9:   */   public static class PriorityList<E>
/* 10:   */     extends LinkedList<E>
/* 11:   */   {
/* 12:   */     public void add(Comparable<E> paramComparable)
/* 13:   */     {
/* 14:11 */       for (int i = 0; i < size(); i++) {
/* 15:12 */         if (paramComparable.compareTo(get(i)) <= 0)
/* 16:   */         {
/* 17:13 */           add(i, paramComparable);
/* 18:14 */           return;
/* 19:   */         }
/* 20:   */       }
/* 21:17 */       addLast(paramComparable);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected static List<AStarNode> constructPath(AStarNode paramAStarNode)
/* 26:   */   {
/* 27:22 */     LinkedList localLinkedList = new LinkedList();
/* 28:23 */     while (paramAStarNode.pathParent != null)
/* 29:   */     {
/* 30:24 */       localLinkedList.addFirst(paramAStarNode);
/* 31:25 */       paramAStarNode = paramAStarNode.pathParent;
/* 32:   */     }
/* 33:27 */     return localLinkedList;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static Iterator<AStarNode> findPath(AStarNode paramAStarNode1, AStarNode paramAStarNode2)
/* 37:   */   {
/* 38:33 */     PriorityList localPriorityList = new PriorityList();
/* 39:34 */     LinkedList localLinkedList = new LinkedList();
/* 40:   */     
/* 41:36 */     paramAStarNode1.costFromStart = 0.0F;
/* 42:37 */     paramAStarNode1.estimatedCostToGoal = paramAStarNode1.getEstimatedCost(paramAStarNode2);
/* 43:   */     
/* 44:39 */     paramAStarNode1.pathParent = null;
/* 45:40 */     localPriorityList.add(paramAStarNode1);
/* 46:42 */     while (!localPriorityList.isEmpty())
/* 47:   */     {
/* 48:43 */       AStarNode localAStarNode1 = (AStarNode)localPriorityList.removeFirst();
/* 49:44 */       if (localAStarNode1 == paramAStarNode2) {
/* 50:45 */         return constructPath(paramAStarNode2).iterator();
/* 51:   */       }
/* 52:48 */       List localList = localAStarNode1.getNeighbours();
/* 53:49 */       for (int i = 0; i < localList.size(); i++)
/* 54:   */       {
/* 55:50 */         AStarNode localAStarNode2 = (AStarNode)localList.get(i);
/* 56:   */         
/* 57:52 */         boolean bool1 = localPriorityList.contains(localAStarNode2);
/* 58:53 */         boolean bool2 = localLinkedList.contains(localAStarNode2);
/* 59:   */         
/* 60:55 */         float f = localAStarNode1.costFromStart + localAStarNode1.getCost(localAStarNode2);
/* 61:58 */         if (((!bool1) && (!bool2)) || (f < localAStarNode2.costFromStart))
/* 62:   */         {
/* 63:61 */           localAStarNode2.pathParent = localAStarNode1;
/* 64:62 */           localAStarNode2.costFromStart = f;
/* 65:63 */           localAStarNode2.estimatedCostToGoal = localAStarNode2.getEstimatedCost(paramAStarNode2);
/* 66:65 */           if (bool2) {
/* 67:66 */             localLinkedList.remove(localAStarNode2);
/* 68:   */           }
/* 69:68 */           if (!bool1) {
/* 70:69 */             localPriorityList.add(localAStarNode2);
/* 71:   */           }
/* 72:   */         }
/* 73:   */       }
/* 74:73 */       localLinkedList.add(localAStarNode1);
/* 75:   */     }
/* 76:76 */     return null;
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.search.AStarSearch
 * JD-Core Version:    0.7.0.1
 */