/*  1:   */ import java.awt.DisplayMode;
/*  2:   */ import java.awt.GraphicsDevice;
/*  3:   */ import java.awt.GraphicsEnvironment;
/*  4:   */ import java.awt.Window;
/*  5:   */ import javax.swing.JFrame;
/*  6:   */ 
/*  7:   */ public class ScreenManager
/*  8:   */ {
/*  9: 6 */   private static final DisplayMode[] ALL_MODES = { new DisplayMode(1024, 768, 32, 0), new DisplayMode(1024, 768, 24, 0), new DisplayMode(1024, 768, 16, 0), new DisplayMode(800, 600, 32, 0), new DisplayMode(800, 600, 24, 0), new DisplayMode(800, 600, 16, 0), new DisplayMode(640, 480, 32, 0), new DisplayMode(640, 480, 24, 0), new DisplayMode(640, 480, 16, 0) };
/* 10: 7 */   private static GraphicsDevice device = null;
/* 11:   */   
/* 12:   */   public static JFrame createFullScreenWindow(boolean paramBoolean)
/* 13:   */   {
/* 14:10 */     if (!paramBoolean)
/* 15:   */     {
/* 16:12 */       localJFrame = new JFrame("team dead");
/* 17:13 */       localJFrame.setSize(640, 480);
/* 18:14 */       localJFrame.setVisible(true);
/* 19:15 */       return localJFrame;
/* 20:   */     }
/* 21:17 */     if (device == null) {
/* 22:17 */       device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
/* 23:   */     }
/* 24:18 */     JFrame localJFrame = new JFrame("team dead");
/* 25:19 */     localJFrame.setUndecorated(true);
/* 26:20 */     localJFrame.setResizable(false);
/* 27:21 */     localJFrame.setIgnoreRepaint(true);
/* 28:22 */     localJFrame.setFocusTraversalKeysEnabled(false);
/* 29:23 */     device.setFullScreenWindow(localJFrame);
/* 30:   */     
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:33 */     return localJFrame;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static void restoreScreen()
/* 43:   */   {
/* 44:37 */     if (device == null) {
/* 45:37 */       return;
/* 46:   */     }
/* 47:38 */     Window localWindow = device.getFullScreenWindow();
/* 48:39 */     if (localWindow != null) {
/* 49:39 */       localWindow.dispose();
/* 50:   */     }
/* 51:40 */     device.setFullScreenWindow(null);
/* 52:   */   }
/* 53:   */   
/* 54:   */   private static DisplayMode findBestMode()
/* 55:   */   {
/* 56:44 */     DisplayMode[] arrayOfDisplayMode = device.getDisplayModes();
/* 57:45 */     for (int i = 0; i < ALL_MODES.length; i++) {
/* 58:47 */       for (int j = 0; j < arrayOfDisplayMode.length; j++) {
/* 59:49 */         if (modesMatch(ALL_MODES[i], arrayOfDisplayMode[j])) {
/* 60:49 */           return ALL_MODES[i];
/* 61:   */         }
/* 62:   */       }
/* 63:   */     }
/* 64:52 */     return null;
/* 65:   */   }
/* 66:   */   
/* 67:   */   private static boolean modesMatch(DisplayMode paramDisplayMode1, DisplayMode paramDisplayMode2)
/* 68:   */   {
/* 69:56 */     if ((paramDisplayMode1.getWidth() != paramDisplayMode2.getWidth()) || (paramDisplayMode1.getHeight() != paramDisplayMode2.getHeight())) {
/* 70:56 */       return false;
/* 71:   */     }
/* 72:57 */     if ((paramDisplayMode1.getBitDepth() != -1) && (paramDisplayMode2.getBitDepth() != -1) && (paramDisplayMode1.getBitDepth() != paramDisplayMode2.getBitDepth())) {
/* 73:57 */       return false;
/* 74:   */     }
/* 75:58 */     if ((paramDisplayMode1.getRefreshRate() != 0) && (paramDisplayMode2.getRefreshRate() != 0) && (paramDisplayMode1.getRefreshRate() != paramDisplayMode2.getRefreshRate())) {
/* 76:58 */       return false;
/* 77:   */     }
/* 78:59 */     return true;
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\code.jar
 * Qualified Name:     ScreenManager
 * JD-Core Version:    0.7.0.1
 */