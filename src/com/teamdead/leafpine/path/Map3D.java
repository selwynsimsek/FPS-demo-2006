/*   1:    */ package com.teamdead.leafpine.path;
/*   2:    */ 
/*   3:    */ import com.teamdead.leafpine.ExplodableObject;
/*   4:    */ import com.teamdead.leafpine.Polygon3D;
/*   5:    */ import com.teamdead.leafpine.PolygonMesh;
/*   6:    */ import com.teamdead.leafpine.Renderer3D;
/*   7:    */ import com.teamdead.leafpine.Sphere;
/*   8:    */ import com.teamdead.leafpine.Vector3D;
/*   9:    */ import com.teamdead.leafpine.particle.ParticleSystem;
/*  10:    */ import com.teamdead.leafpine.search.AStarSearch;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Collections;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Set;
/*  16:    */ 
/*  17:    */ public class Map3D
/*  18:    */ {
/*  19:    */   private Polygon3D[] polys;
/*  20:    */   private PortalGroup[] rooms;
/*  21:    */   private PolygonMesh[] staticObjects;
/*  22:    */   private ExplodableObject[] explodableObjects;
/*  23:    */   
/*  24:    */   public Map3D(Polygon3D[] paramArrayOfPolygon3D, PortalGroup[] paramArrayOfPortalGroup, Renderer3D paramRenderer3D, PolygonMesh[] paramArrayOfPolygonMesh, ExplodableObject[] paramArrayOfExplodableObject)
/*  25:    */   {
/*  26: 16 */     this.polys = paramArrayOfPolygon3D;
/*  27: 17 */     this.rooms = paramArrayOfPortalGroup;
/*  28: 18 */     this.staticObjects = paramArrayOfPolygonMesh;
/*  29: 19 */     this.explodableObjects = paramArrayOfExplodableObject;
/*  30: 20 */     paramRenderer3D.addPolygons(paramArrayOfPolygon3D);
/*  31: 21 */     paramRenderer3D.addPolygons(paramArrayOfPolygonMesh);
/*  32: 22 */     for (int i = 0; i < paramArrayOfExplodableObject.length; i++) {
/*  33: 24 */       paramRenderer3D.addPolygon(paramArrayOfExplodableObject[i].getMesh());
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Polygon3D[] getWalls()
/*  38:    */   {
/*  39: 29 */     return this.polys;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean collidesWith(Sphere paramSphere)
/*  43:    */   {
/*  44: 33 */     for (int i = 0; i < this.staticObjects.length; i++) {
/*  45: 35 */       if (this.staticObjects[i].getBounds().intersects(paramSphere)) {
/*  46: 35 */         return true;
/*  47:    */       }
/*  48:    */     }
/*  49: 37 */     for (i = 0; i < this.explodableObjects.length; i++) {
/*  50: 39 */       if ((this.explodableObjects[i] != null) && 
/*  51: 40 */         (this.explodableObjects[i].getMesh().getBounds().intersects(paramSphere))) {
/*  52: 40 */         return true;
/*  53:    */       }
/*  54:    */     }
/*  55: 42 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ParticleSystem performExplosions(Renderer3D paramRenderer3D, Sphere paramSphere)
/*  59:    */   {
/*  60: 46 */     for (int i = 0; i < this.explodableObjects.length; i++) {
/*  61: 48 */       if ((this.explodableObjects[i] != null) && 
/*  62: 49 */         (paramSphere.intersects(this.explodableObjects[i].getMesh().getBounds())))
/*  63:    */       {
/*  64: 51 */         ParticleSystem localParticleSystem = this.explodableObjects[i].explode(paramRenderer3D);
/*  65: 52 */         paramRenderer3D.removePolygon(this.explodableObjects[i].getMesh());
/*  66: 53 */         this.explodableObjects[i] = null;
/*  67: 54 */         return localParticleSystem;
/*  68:    */       }
/*  69:    */     }
/*  70: 57 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Iterator<Vector3D> findPath(Vector3D paramVector3D1, Vector3D paramVector3D2)
/*  74:    */   {
/*  75: 61 */     if (paramVector3D1.equals(paramVector3D2)) {
/*  76: 61 */       return Collections.singleton(paramVector3D2).iterator();
/*  77:    */     }
/*  78: 62 */     Portal localPortal1 = new Portal(paramVector3D1);
/*  79: 63 */     Portal localPortal2 = new Portal(paramVector3D2);
/*  80: 64 */     for (int i = 0; i < this.rooms.length; i++)
/*  81:    */     {
/*  82: 66 */       localObject = this.rooms[i];
/*  83: 67 */       boolean bool1 = ((PortalGroup)localObject).withinBounds(localPortal1);
/*  84: 68 */       boolean bool2 = ((PortalGroup)localObject).withinBounds(localPortal2);
/*  85: 69 */       if ((bool1) && (bool2)) {
/*  86: 69 */         return Collections.singleton(paramVector3D2).iterator();
/*  87:    */       }
/*  88: 70 */       if (bool1) {
/*  89: 72 */         ((PortalGroup)localObject).addNewPortal(localPortal1);
/*  90: 74 */       } else if (bool2) {
/*  91: 76 */         ((PortalGroup)localObject).addNewPortal(localPortal2);
/*  92:    */       }
/*  93:    */     }
/*  94: 79 */     Iterator localIterator = AStarSearch.findPath(localPortal1, localPortal2);
/*  95: 80 */     if (localIterator == null)
/*  96:    */     {
/*  97: 82 */       localPortal1.destroy();
/*  98: 83 */       localPortal2.destroy();
/*  99: 84 */       return null;
/* 100:    */     }
/* 101: 86 */     Object localObject = new ArrayList();
/* 102: 87 */     while (localIterator.hasNext())
/* 103:    */     {
/* 104: 89 */       Portal localPortal3 = (Portal)localIterator.next();
/* 105: 90 */       ((ArrayList)localObject).add(localPortal3.getPoint());
/* 106:    */     }
/* 107: 92 */     localPortal1.destroy();
/* 108: 93 */     localPortal2.destroy();
/* 109: 94 */     return Collections.unmodifiableList((List)localObject).iterator();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String toString()
/* 113:    */   {
/* 114: 98 */     StringBuffer localStringBuffer = new StringBuffer();
/* 115: 99 */     for (int i = 0; i < this.rooms.length; i++) {
/* 116:101 */       localStringBuffer.append(this.rooms[i].toString());
/* 117:    */     }
/* 118:103 */     return localStringBuffer.toString();
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.path.Map3D
 * JD-Core Version:    0.7.0.1
 */