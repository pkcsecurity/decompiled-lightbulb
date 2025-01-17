package com.fasterxml.jackson.core;

import java.io.Serializable;

public class Version implements Serializable, Comparable<Version> {

   private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, (String)null, (String)null, (String)null);
   private static final long serialVersionUID = 1L;
   protected final String _artifactId;
   protected final String _groupId;
   protected final int _majorVersion;
   protected final int _minorVersion;
   protected final int _patchLevel;
   protected final String _snapshotInfo;


   @Deprecated
   public Version(int var1, int var2, int var3, String var4) {
      this(var1, var2, var3, var4, (String)null, (String)null);
   }

   public Version(int var1, int var2, int var3, String var4, String var5, String var6) {
      this._majorVersion = var1;
      this._minorVersion = var2;
      this._patchLevel = var3;
      this._snapshotInfo = var4;
      var4 = var5;
      if(var5 == null) {
         var4 = "";
      }

      this._groupId = var4;
      var4 = var6;
      if(var6 == null) {
         var4 = "";
      }

      this._artifactId = var4;
   }

   public static Version unknownVersion() {
      return UNKNOWN_VERSION;
   }

   public int compareTo(Version var1) {
      if(var1 == this) {
         return 0;
      } else {
         int var3 = this._groupId.compareTo(var1._groupId);
         int var2 = var3;
         if(var3 == 0) {
            var3 = this._artifactId.compareTo(var1._artifactId);
            var2 = var3;
            if(var3 == 0) {
               var3 = this._majorVersion - var1._majorVersion;
               var2 = var3;
               if(var3 == 0) {
                  var3 = this._minorVersion - var1._minorVersion;
                  var2 = var3;
                  if(var3 == 0) {
                     var2 = this._patchLevel - var1._patchLevel;
                  }
               }
            }
         }

         return var2;
      }
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(var1.getClass() != this.getClass()) {
         return false;
      } else {
         Version var2 = (Version)var1;
         return var2._majorVersion == this._majorVersion && var2._minorVersion == this._minorVersion && var2._patchLevel == this._patchLevel && var2._artifactId.equals(this._artifactId) && var2._groupId.equals(this._groupId);
      }
   }

   public String getArtifactId() {
      return this._artifactId;
   }

   public String getGroupId() {
      return this._groupId;
   }

   public int getMajorVersion() {
      return this._majorVersion;
   }

   public int getMinorVersion() {
      return this._minorVersion;
   }

   public int getPatchLevel() {
      return this._patchLevel;
   }

   public int hashCode() {
      return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
   }

   public boolean isSnapshot() {
      return this._snapshotInfo != null && this._snapshotInfo.length() > 0;
   }

   public boolean isUknownVersion() {
      return this == UNKNOWN_VERSION;
   }

   public String toFullString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._groupId);
      var1.append('/');
      var1.append(this._artifactId);
      var1.append('/');
      var1.append(this.toString());
      return var1.toString();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this._majorVersion);
      var1.append('.');
      var1.append(this._minorVersion);
      var1.append('.');
      var1.append(this._patchLevel);
      if(this.isSnapshot()) {
         var1.append('-');
         var1.append(this._snapshotInfo);
      }

      return var1.toString();
   }
}
