/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.awt.Dimension;
/*  4:   */ import java.awt.Font;
/*  5:   */ import java.awt.FontMetrics;
/*  6:   */ import java.awt.Graphics2D;
/*  7:   */ import java.awt.Rectangle;
/*  8:   */ import java.awt.RenderingHints;
/*  9:   */ import java.awt.Robot;
/* 10:   */ import java.awt.Toolkit;
/* 11:   */ import java.awt.image.BufferedImage;
/* 12:   */ import java.io.File;
/* 13:   */ import java.io.PrintStream;
/* 14:   */ import javax.imageio.ImageIO;
/* 15:   */ 
/* 16:   */ public class CaptureScreenThread
/* 17:   */   extends Thread
/* 18:   */ {
/* 19:   */   private Robot robot;
/* 20:   */   private static final String WATERMARK = "MapExplorer - Selwyn Simsek :)";
/* 21:13 */   private static final Font FONT = new Font("Monospaced", 0, 12);
/* 22:   */   
/* 23:   */   public CaptureScreenThread(Robot paramRobot)
/* 24:   */   {
/* 25:16 */     this.robot = paramRobot;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void run()
/* 29:   */   {
/* 30:20 */     Dimension localDimension = Toolkit.getDefaultToolkit().getScreenSize();
/* 31:21 */     BufferedImage localBufferedImage = this.robot.createScreenCapture(new Rectangle(0, 0, localDimension.width, localDimension.height));
/* 32:22 */     drawWatermark(localBufferedImage);
/* 33:23 */     String str = System.getProperty("user.home");
/* 34:24 */     File localFile1 = new File(new File(str), "MapExplorer snapshots");
/* 35:25 */     if (!localFile1.exists()) {
/* 36:25 */       localFile1.mkdir();
/* 37:   */     }
/* 38:26 */     File localFile2 = new File(localFile1, "snapshot1.jpg");
/* 39:27 */     int i = 1;
/* 40:28 */     while (localFile2.exists())
/* 41:   */     {
/* 42:30 */       i++;
/* 43:31 */       localFile2 = new File(localFile1, "snapshot" + i + ".jpg");
/* 44:   */     }
/* 45:   */     try
/* 46:   */     {
/* 47:35 */       ImageIO.write(localBufferedImage, "jpeg", localFile2);
/* 48:   */     }
/* 49:   */     catch (Exception localException)
/* 50:   */     {
/* 51:39 */       System.out.println("Unfortunately for some reason the screenshot could not be taken properly: " + localException);
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   private void drawWatermark(BufferedImage paramBufferedImage)
/* 56:   */   {
/* 57:44 */     Graphics2D localGraphics2D = paramBufferedImage.createGraphics();
/* 58:45 */     localGraphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 59:46 */     localGraphics2D.setFont(FONT);
/* 60:47 */     FontMetrics localFontMetrics = localGraphics2D.getFontMetrics();
/* 61:48 */     localGraphics2D.drawString("MapExplorer - Selwyn Simsek :)", paramBufferedImage.getWidth(null) - (localFontMetrics.stringWidth("MapExplorer - Selwyn Simsek :)") + 5), paramBufferedImage.getHeight(null) - (localFontMetrics.getHeight() + 5));
/* 62:49 */     localGraphics2D.dispose();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.CaptureScreenThread
 * JD-Core Version:    0.7.0.1
 */