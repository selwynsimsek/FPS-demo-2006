/*  1:   */ package com.teamdead.leafpine;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.net.URL;
/*  6:   */ import javax.sound.sampled.AudioFormat;
/*  7:   */ import javax.sound.sampled.AudioFormat.Encoding;
/*  8:   */ import javax.sound.sampled.AudioInputStream;
/*  9:   */ import javax.sound.sampled.AudioSystem;
/* 10:   */ import javax.sound.sampled.DataLine.Info;
/* 11:   */ import javax.sound.sampled.FloatControl;
/* 12:   */ import javax.sound.sampled.LineUnavailableException;
/* 13:   */ import javax.sound.sampled.SourceDataLine;
/* 14:   */ 
/* 15:   */ public class PlayBackgroundThread
/* 16:   */   extends Thread
/* 17:   */ {
/* 18:   */   private URL sound;
/* 19:10 */   public static boolean isPlaying = false;
/* 20:   */   private static final int LOOP_PAUSE_IN_MILLISECONDS = 5000;
/* 21:   */   private FloatControl volume;
/* 22:   */   
/* 23:   */   public PlayBackgroundThread(URL paramURL)
/* 24:   */   {
/* 25:15 */     this.sound = paramURL;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void run()
/* 29:   */   {
/* 30:19 */     isPlaying = true;
/* 31:20 */     while (isPlaying) {
/* 32:22 */       play();
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   private void play()
/* 37:   */   {
/* 38:   */     try
/* 39:   */     {
/* 40:29 */       AudioInputStream localAudioInputStream1 = AudioSystem.getAudioInputStream(this.sound);
/* 41:30 */       AudioInputStream localAudioInputStream2 = null;
/* 42:31 */       if (localAudioInputStream1 != null)
/* 43:   */       {
/* 44:33 */         AudioFormat localAudioFormat1 = localAudioInputStream1.getFormat();
/* 45:34 */         AudioFormat localAudioFormat2 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, localAudioFormat1.getSampleRate(), 16, localAudioFormat1.getChannels(), localAudioFormat1.getChannels() * 2, localAudioFormat1.getSampleRate(), false);
/* 46:35 */         localAudioInputStream2 = AudioSystem.getAudioInputStream(localAudioFormat2, localAudioInputStream1);
/* 47:36 */         rawplay(localAudioFormat2, localAudioInputStream2);
/* 48:37 */         localAudioInputStream1.close();
/* 49:   */       }
/* 50:   */     }
/* 51:   */     catch (Exception localException)
/* 52:   */     {
/* 53:42 */       localException.printStackTrace();
/* 54:43 */       isPlaying = false;
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   private void rawplay(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream)
/* 59:   */     throws IOException, LineUnavailableException
/* 60:   */   {
/* 61:48 */     byte[] arrayOfByte = new byte[4096];
/* 62:49 */     SourceDataLine localSourceDataLine = getLine(paramAudioFormat);
/* 63:50 */     if (localSourceDataLine != null)
/* 64:   */     {
/* 65:52 */       localSourceDataLine.start();
/* 66:53 */       int i = 0;int j = 0;
/* 67:54 */       while (i != -1)
/* 68:   */       {
/* 69:56 */         i = paramAudioInputStream.read(arrayOfByte, 0, arrayOfByte.length);
/* 70:57 */         if (i != -1) {
/* 71:57 */           j = localSourceDataLine.write(arrayOfByte, 0, i);
/* 72:   */         }
/* 73:   */       }
/* 74:59 */       localSourceDataLine.drain();
/* 75:60 */       localSourceDataLine.stop();
/* 76:61 */       localSourceDataLine.close();
/* 77:62 */       paramAudioInputStream.close();
/* 78:   */     }
/* 79:   */   }
/* 80:   */   
/* 81:   */   private void printVolume()
/* 82:   */   {
/* 83:67 */     System.out.println(this.volume);
/* 84:   */   }
/* 85:   */   
/* 86:   */   private SourceDataLine getLine(AudioFormat paramAudioFormat)
/* 87:   */     throws LineUnavailableException
/* 88:   */   {
/* 89:71 */     SourceDataLine localSourceDataLine = null;
/* 90:72 */     DataLine.Info localInfo = new DataLine.Info(SourceDataLine.class, paramAudioFormat);
/* 91:73 */     localSourceDataLine = (SourceDataLine)AudioSystem.getLine(localInfo);
/* 92:74 */     localSourceDataLine.open(paramAudioFormat);
/* 93:75 */     return localSourceDataLine;
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\Users\Selwyn\Documents\programming\Old programming (up to 2012)\FPS-demo-from-2006-2007\leafpine.jar
 * Qualified Name:     com.teamdead.leafpine.PlayBackgroundThread
 * JD-Core Version:    0.7.0.1
 */