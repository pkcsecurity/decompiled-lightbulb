package com.google.common.cache;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.cache.LocalCache;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public final class CacheBuilderSpec {

   private static final Splitter n = Splitter.a(',').a();
   private static final Splitter o = Splitter.a('=').a();
   private static final ra<String, CacheBuilderSpec.ValueParser> p = ra.f().b("initialCapacity", new CacheBuilderSpec.d()).b("maximumSize", new CacheBuilderSpec.h()).b("maximumWeight", new CacheBuilderSpec.i()).b("concurrencyLevel", new CacheBuilderSpec.b()).b("weakKeys", new CacheBuilderSpec.f(LocalCache.m.WEAK)).b("softValues", new CacheBuilderSpec.l(LocalCache.m.SOFT)).b("weakValues", new CacheBuilderSpec.l(LocalCache.m.WEAK)).b("recordStats", new CacheBuilderSpec.j()).b("expireAfterAccess", new CacheBuilderSpec.a()).b("expireAfterWrite", new CacheBuilderSpec.m()).b("refreshAfterWrite", new CacheBuilderSpec.k()).b("refreshInterval", new CacheBuilderSpec.k()).b();
   @VisibleForTesting
   Integer a;
   @VisibleForTesting
   Long b;
   @VisibleForTesting
   Long c;
   @VisibleForTesting
   Integer d;
   @VisibleForTesting
   LocalCache.m e;
   @VisibleForTesting
   LocalCache.m f;
   @VisibleForTesting
   Boolean g;
   @VisibleForTesting
   long h;
   @VisibleForTesting
   TimeUnit i;
   @VisibleForTesting
   long j;
   @VisibleForTesting
   TimeUnit k;
   @VisibleForTesting
   long l;
   @VisibleForTesting
   TimeUnit m;
   private final String q;


   @Nullable
   private static Long a(long var0, @Nullable TimeUnit var2) {
      return var2 == null?null:Long.valueOf(var2.toNanos(var0));
   }

   public String a() {
      return this.q;
   }

   public boolean equals(@Nullable Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof CacheBuilderSpec)) {
         return false;
      } else {
         CacheBuilderSpec var2 = (CacheBuilderSpec)var1;
         return pt.a(this.a, var2.a) && pt.a(this.b, var2.b) && pt.a(this.c, var2.c) && pt.a(this.d, var2.d) && pt.a(this.e, var2.e) && pt.a(this.f, var2.f) && pt.a(this.g, var2.g) && pt.a(a(this.h, this.i), a(var2.h, var2.i)) && pt.a(a(this.j, this.k), a(var2.j, var2.k)) && pt.a(a(this.l, this.m), a(var2.l, var2.m));
      }
   }

   public int hashCode() {
      return pt.a(new Object[]{this.a, this.b, this.c, this.d, this.e, this.f, this.g, a(this.h, this.i), a(this.j, this.k), a(this.l, this.m)});
   }

   public String toString() {
      return ps.a(this).a(this.a()).toString();
   }

   abstract static class g implements CacheBuilderSpec.ValueParser {

   }

   static class h extends CacheBuilderSpec.g {

   }

   static class i extends CacheBuilderSpec.g {

   }

   static class j implements CacheBuilderSpec.ValueParser {

   }

   static class k extends CacheBuilderSpec.c {

   }

   static class l implements CacheBuilderSpec.ValueParser {

      private final LocalCache.m a;


      public l(LocalCache.m var1) {
         this.a = var1;
      }
   }

   static class m extends CacheBuilderSpec.c {

   }

   interface ValueParser {
   }

   static class a extends CacheBuilderSpec.c {

   }

   static class b extends CacheBuilderSpec.e {

   }

   abstract static class c implements CacheBuilderSpec.ValueParser {

   }

   static class d extends CacheBuilderSpec.e {

   }

   abstract static class e implements CacheBuilderSpec.ValueParser {

   }

   static class f implements CacheBuilderSpec.ValueParser {

      private final LocalCache.m a;


      public f(LocalCache.m var1) {
         this.a = var1;
      }
   }
}
