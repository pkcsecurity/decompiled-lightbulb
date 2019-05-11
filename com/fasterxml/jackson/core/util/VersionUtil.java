package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {

   public static final String PACKAGE_VERSION_CLASS_NAME = "PackageVersion";
   @Deprecated
   public static final String VERSION_FILE = "VERSION.txt";
   private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
   private final Version _version;


   protected VersionUtil() {
      Version var4;
      try {
         var4 = versionFor(this.getClass());
      } catch (Exception var3) {
         PrintStream var1 = System.err;
         StringBuilder var2 = new StringBuilder();
         var2.append("ERROR: Failed to load Version information for bundle (via ");
         var2.append(this.getClass().getName());
         var2.append(").");
         var1.println(var2.toString());
         var4 = null;
      }

      Version var5 = var4;
      if(var4 == null) {
         var5 = Version.unknownVersion();
      }

      this._version = var5;
   }

   private static Version doReadVersion(Reader param0) {
      // $FF: Couldn't be decompiled
   }

   public static Version mavenVersionFor(ClassLoader var0, String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("META-INF/maven/");
      var3.append(var1.replaceAll("\\.", "/"));
      var3.append("/");
      var3.append(var2);
      var3.append("/pom.properties");
      InputStream var12 = var0.getResourceAsStream(var3.toString());
      if(var12 != null) {
         Version var14;
         try {
            Properties var13 = new Properties();
            var13.load(var12);
            var2 = var13.getProperty("version");
            String var15 = var13.getProperty("artifactId");
            var14 = parseVersion(var2, var13.getProperty("groupId"), var15);
         } catch (IOException var10) {
            return Version.unknownVersion();
         } finally {
            try {
               var12.close();
            } catch (IOException var9) {
               ;
            }

         }

         return var14;
      } else {
         return Version.unknownVersion();
      }
   }

   public static Version packageVersionFor(Class<?> var0) {
      StringBuilder var1;
      try {
         var1 = new StringBuilder(var0.getPackage().getName());
         var1.append(".");
         var1.append("PackageVersion");
         var0 = Class.forName(var1.toString(), true, var0.getClassLoader());
      } catch (Exception var5) {
         return null;
      }

      if(var0 == null) {
         return null;
      } else {
         Object var6;
         try {
            var6 = var0.newInstance();
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Failed to instantiate ");
            var2.append(var0.getName());
            var2.append(" to find version information, problem: ");
            var2.append(var4.getMessage());
            throw new IllegalArgumentException(var2.toString(), var4);
         }

         if(!(var6 instanceof Versioned)) {
            var1 = new StringBuilder();
            var1.append("Bad version class ");
            var1.append(var0.getName());
            var1.append(": does not implement ");
            var1.append(Versioned.class.getName());
            throw new IllegalArgumentException(var1.toString());
         } else {
            return ((Versioned)var6).version();
         }
      }
   }

   @Deprecated
   public static Version parseVersion(String var0) {
      return parseVersion(var0, (String)null, (String)null);
   }

   public static Version parseVersion(String var0, String var1, String var2) {
      Object var6 = null;
      if(var0 == null) {
         return null;
      } else {
         var0 = var0.trim();
         if(var0.length() == 0) {
            return null;
         } else {
            String[] var7 = VERSION_SEPARATOR.split(var0);
            int var5 = parseVersionPart(var7[0]);
            int var3;
            if(var7.length > 1) {
               var3 = parseVersionPart(var7[1]);
            } else {
               var3 = 0;
            }

            int var4;
            if(var7.length > 2) {
               var4 = parseVersionPart(var7[2]);
            } else {
               var4 = 0;
            }

            var0 = (String)var6;
            if(var7.length > 3) {
               var0 = var7[3];
            }

            return new Version(var5, var3, var4, var0, var1, var2);
         }
      }
   }

   protected static int parseVersionPart(String var0) {
      var0 = var0.toString();
      int var3 = var0.length();
      int var1 = 0;

      int var2;
      for(var2 = 0; var1 < var3; ++var1) {
         char var4 = var0.charAt(var1);
         if(var4 > 57) {
            break;
         }

         if(var4 < 48) {
            return var2;
         }

         var2 = var2 * 10 + (var4 - 48);
      }

      return var2;
   }

   public static final void throwInternal() {
      throw new RuntimeException("Internal error: this code path should never get executed");
   }

   public static Version versionFor(Class<?> param0) {
      // $FF: Couldn't be decompiled
   }

   public Version version() {
      return this._version;
   }
}
