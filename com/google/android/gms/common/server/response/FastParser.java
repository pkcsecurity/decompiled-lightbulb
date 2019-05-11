package com.google.android.gms.common.server.response;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zaa;
import com.google.android.gms.common.server.response.zab;
import com.google.android.gms.common.server.response.zac;
import com.google.android.gms.common.server.response.zad;
import com.google.android.gms.common.server.response.zae;
import com.google.android.gms.common.server.response.zaf;
import com.google.android.gms.common.server.response.zag;
import com.google.android.gms.common.server.response.zah;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@KeepForSdk
@ShowFirstParty
public class FastParser<T extends FastJsonResponse> {

   private static final char[] zaqf = new char[]{'u', 'l', 'l'};
   private static final char[] zaqg = new char[]{'r', 'u', 'e'};
   private static final char[] zaqh = new char[]{'r', 'u', 'e', '\"'};
   private static final char[] zaqi = new char[]{'a', 'l', 's', 'e'};
   private static final char[] zaqj = new char[]{'a', 'l', 's', 'e', '\"'};
   private static final char[] zaqk = new char[]{'\n'};
   private static final FastParser.zaa<Integer> zaqm = new zaa();
   private static final FastParser.zaa<Long> zaqn = new zab();
   private static final FastParser.zaa<Float> zaqo = new zac();
   private static final FastParser.zaa<Double> zaqp = new zad();
   private static final FastParser.zaa<Boolean> zaqq = new zae();
   private static final FastParser.zaa<String> zaqr = new zaf();
   private static final FastParser.zaa<BigInteger> zaqs = new zag();
   private static final FastParser.zaa<BigDecimal> zaqt = new zah();
   private final char[] zaqa = new char[1];
   private final char[] zaqb = new char[32];
   private final char[] zaqc = new char[1024];
   private final StringBuilder zaqd = new StringBuilder(32);
   private final StringBuilder zaqe = new StringBuilder(1024);
   private final Stack<Integer> zaql = new Stack();


   // $FF: synthetic method
   static int zaa(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zad(var1);
   }

   private final int zaa(BufferedReader var1, char[] var2) throws FastParser.ParseException, IOException {
      char var3 = this.zaj(var1);
      if(var3 == 0) {
         throw new FastParser.ParseException("Unexpected EOF");
      } else if(var3 == 44) {
         throw new FastParser.ParseException("Missing value");
      } else if(var3 == 110) {
         this.zab(var1, zaqf);
         return 0;
      } else {
         var1.mark(1024);
         int var4;
         int var6;
         if(var3 == 34) {
            var4 = 0;
            boolean var5 = false;

            while(true) {
               var6 = var4;
               if(var4 >= var2.length) {
                  break;
               }

               var6 = var4;
               if(var1.read(var2, var4, 1) == -1) {
                  break;
               }

               var3 = var2[var4];
               if(Character.isISOControl(var3)) {
                  throw new FastParser.ParseException("Unexpected control character while reading string");
               }

               if(var3 == 34 && !var5) {
                  var1.reset();
                  var1.skip((long)(var4 + 1));
                  return var4;
               }

               if(var3 == 92) {
                  var5 ^= true;
               } else {
                  var5 = false;
               }

               ++var4;
            }
         } else {
            var2[0] = var3;
            var4 = 1;

            while(true) {
               var6 = var4;
               if(var4 >= var2.length) {
                  break;
               }

               var6 = var4;
               if(var1.read(var2, var4, 1) == -1) {
                  break;
               }

               if(var2[var4] == 125 || var2[var4] == 44 || Character.isWhitespace(var2[var4]) || var2[var4] == 93) {
                  var1.reset();
                  var1.skip((long)(var4 - 1));
                  var2[var4] = 0;
                  return var4;
               }

               ++var4;
            }
         }

         if(var6 == var2.length) {
            throw new FastParser.ParseException("Absurdly long value");
         } else {
            throw new FastParser.ParseException("Unexpected EOF");
         }
      }
   }

   private final String zaa(BufferedReader var1) throws FastParser.ParseException, IOException {
      this.zaql.push(Integer.valueOf(2));
      char var2 = this.zaj(var1);
      if(var2 != 34) {
         if(var2 != 93) {
            if(var2 != 125) {
               StringBuilder var4 = new StringBuilder(19);
               var4.append("Unexpected token: ");
               var4.append(var2);
               throw new FastParser.ParseException(var4.toString());
            } else {
               this.zak(2);
               return null;
            }
         } else {
            this.zak(2);
            this.zak(1);
            this.zak(5);
            return null;
         }
      } else {
         this.zaql.push(Integer.valueOf(3));
         String var3 = zab(var1, this.zaqb, this.zaqd, (char[])null);
         this.zak(3);
         if(this.zaj(var1) != 58) {
            throw new FastParser.ParseException("Expected key/value separator");
         } else {
            return var3;
         }
      }
   }

   private final String zaa(BufferedReader var1, char[] var2, StringBuilder var3, char[] var4) throws FastParser.ParseException, IOException {
      char var5 = this.zaj(var1);
      if(var5 != 34) {
         if(var5 != 110) {
            throw new FastParser.ParseException("Expected string");
         } else {
            this.zab(var1, zaqf);
            return null;
         }
      } else {
         return zab(var1, var2, var3, var4);
      }
   }

   private final <T extends FastJsonResponse> ArrayList<T> zaa(BufferedReader var1, FastJsonResponse.Field<?, ?> var2) throws FastParser.ParseException, IOException {
      ArrayList var4 = new ArrayList();
      char var3 = this.zaj(var1);
      if(var3 == 93) {
         this.zak(5);
         return var4;
      } else if(var3 == 110) {
         this.zab(var1, zaqf);
         this.zak(5);
         return null;
      } else {
         StringBuilder var8;
         if(var3 != 123) {
            var8 = new StringBuilder(19);
            var8.append("Unexpected token: ");
            var8.append(var3);
            throw new FastParser.ParseException(var8.toString());
         } else {
            this.zaql.push(Integer.valueOf(1));

            while(true) {
               try {
                  FastJsonResponse var5 = var2.zacp();
                  if(!this.zaa(var1, var5)) {
                     return var4;
                  }

                  var4.add(var5);
               } catch (InstantiationException var6) {
                  throw new FastParser.ParseException("Error instantiating inner object", var6);
               } catch (IllegalAccessException var7) {
                  throw new FastParser.ParseException("Error instantiating inner object", var7);
               }

               var3 = this.zaj(var1);
               if(var3 != 44) {
                  if(var3 != 93) {
                     var8 = new StringBuilder(19);
                     var8.append("Unexpected token: ");
                     var8.append(var3);
                     throw new FastParser.ParseException(var8.toString());
                  }

                  this.zak(5);
                  return var4;
               }

               if(this.zaj(var1) != 123) {
                  throw new FastParser.ParseException("Expected start of next object in array");
               }

               this.zaql.push(Integer.valueOf(1));
            }
         }
      }
   }

   private final <O extends Object> ArrayList<O> zaa(BufferedReader var1, FastParser.zaa<O> var2) throws FastParser.ParseException, IOException {
      char var3 = this.zaj(var1);
      if(var3 == 110) {
         this.zab(var1, zaqf);
         return null;
      } else if(var3 != 91) {
         throw new FastParser.ParseException("Expected start of array");
      } else {
         this.zaql.push(Integer.valueOf(5));
         ArrayList var4 = new ArrayList();

         while(true) {
            var1.mark(1024);
            var3 = this.zaj(var1);
            if(var3 == 0) {
               throw new FastParser.ParseException("Unexpected EOF");
            }

            if(var3 != 44) {
               if(var3 == 93) {
                  this.zak(5);
                  return var4;
               }

               var1.reset();
               var4.add(var2.zah(this, var1));
            }
         }
      }
   }

   // $FF: synthetic method
   static boolean zaa(FastParser var0, BufferedReader var1, boolean var2) throws FastParser.ParseException, IOException {
      return var0.zaa(var1, false);
   }

   private final boolean zaa(BufferedReader var1, FastJsonResponse var2) throws FastParser.ParseException, IOException {
      Map var6 = var2.getFieldMappings();
      String var5 = this.zaa(var1);
      if(var5 == null) {
         this.zak(1);
         return false;
      } else {
         while(true) {
            while(var5 != null) {
               FastJsonResponse.Field var7 = (FastJsonResponse.Field)var6.get(var5);
               if(var7 != null) {
                  this.zaql.push(Integer.valueOf(4));
                  char var3;
                  char var4;
                  StringBuilder var11;
                  switch(var7.zapq) {
                  case 0:
                     if(var7.zapr) {
                        var2.zaa(var7, this.zaa(var1, zaqm));
                     } else {
                        var2.zaa(var7, this.zad(var1));
                     }
                     break;
                  case 1:
                     if(var7.zapr) {
                        var2.zab(var7, this.zaa(var1, zaqs));
                     } else {
                        var2.zaa(var7, this.zaf(var1));
                     }
                     break;
                  case 2:
                     if(var7.zapr) {
                        var2.zac(var7, this.zaa(var1, zaqn));
                     } else {
                        var2.zaa(var7, this.zae(var1));
                     }
                     break;
                  case 3:
                     if(var7.zapr) {
                        var2.zad(var7, this.zaa(var1, zaqo));
                     } else {
                        var2.zaa(var7, this.zag(var1));
                     }
                     break;
                  case 4:
                     if(var7.zapr) {
                        var2.zae(var7, this.zaa(var1, zaqp));
                     } else {
                        var2.zaa(var7, this.zah(var1));
                     }
                     break;
                  case 5:
                     if(var7.zapr) {
                        var2.zaf(var7, this.zaa(var1, zaqt));
                     } else {
                        var2.zaa(var7, this.zai(var1));
                     }
                     break;
                  case 6:
                     if(var7.zapr) {
                        var2.zag(var7, this.zaa(var1, zaqq));
                     } else {
                        var2.zaa(var7, this.zaa(var1, false));
                     }
                     break;
                  case 7:
                     if(var7.zapr) {
                        var2.zah(var7, this.zaa(var1, zaqr));
                     } else {
                        var2.zaa(var7, this.zac(var1));
                     }
                     break;
                  case 8:
                     var2.zaa(var7, Base64Utils.decode(this.zaa(var1, this.zaqc, this.zaqe, zaqk)));
                     break;
                  case 9:
                     var2.zaa(var7, Base64Utils.decodeUrlSafe(this.zaa(var1, this.zaqc, this.zaqe, zaqk)));
                     break;
                  case 10:
                     var4 = this.zaj(var1);
                     HashMap var15;
                     if(var4 == 110) {
                        this.zab(var1, zaqf);
                        var15 = null;
                     } else {
                        if(var4 != 123) {
                           throw new FastParser.ParseException("Expected start of a map object");
                        }

                        this.zaql.push(Integer.valueOf(1));
                        var15 = new HashMap();

                        while(true) {
                           var4 = this.zaj(var1);
                           if(var4 == 0) {
                              throw new FastParser.ParseException("Unexpected EOF");
                           }

                           if(var4 != 34) {
                              if(var4 == 125) {
                                 this.zak(1);
                                 break;
                              }
                           } else {
                              String var8 = zab(var1, this.zaqb, this.zaqd, (char[])null);
                              String var12;
                              if(this.zaj(var1) != 58) {
                                 var12 = String.valueOf(var8);
                                 if(var12.length() != 0) {
                                    var12 = "No map value found for key ".concat(var12);
                                 } else {
                                    var12 = new String("No map value found for key ");
                                 }

                                 throw new FastParser.ParseException(var12);
                              }

                              if(this.zaj(var1) != 34) {
                                 var12 = String.valueOf(var8);
                                 if(var12.length() != 0) {
                                    var12 = "Expected String value for key ".concat(var12);
                                 } else {
                                    var12 = new String("Expected String value for key ");
                                 }

                                 throw new FastParser.ParseException(var12);
                              }

                              var15.put(var8, zab(var1, this.zaqb, this.zaqd, (char[])null));
                              var3 = this.zaj(var1);
                              if(var3 != 44) {
                                 if(var3 != 125) {
                                    var11 = new StringBuilder(48);
                                    var11.append("Unexpected character while parsing string map: ");
                                    var11.append(var3);
                                    throw new FastParser.ParseException(var11.toString());
                                 }

                                 this.zak(1);
                                 break;
                              }
                           }
                        }
                     }

                     var2.zaa(var7, (Map)var15);
                     break;
                  case 11:
                     if(var7.zapr) {
                        var4 = this.zaj(var1);
                        if(var4 == 110) {
                           this.zab(var1, zaqf);
                           var2.addConcreteTypeArrayInternal(var7, var7.zapu, (ArrayList)null);
                        } else {
                           this.zaql.push(Integer.valueOf(5));
                           if(var4 != 91) {
                              throw new FastParser.ParseException("Expected array start");
                           }

                           var2.addConcreteTypeArrayInternal(var7, var7.zapu, this.zaa(var1, var7));
                        }
                     } else {
                        var4 = this.zaj(var1);
                        if(var4 == 110) {
                           this.zab(var1, zaqf);
                           var2.addConcreteTypeInternal(var7, var7.zapu, (FastJsonResponse)null);
                        } else {
                           this.zaql.push(Integer.valueOf(1));
                           if(var4 != 123) {
                              throw new FastParser.ParseException("Expected start of object");
                           }

                           try {
                              FastJsonResponse var14 = var7.zacp();
                              this.zaa(var1, var14);
                              var2.addConcreteTypeInternal(var7, var7.zapu, var14);
                           } catch (InstantiationException var9) {
                              throw new FastParser.ParseException("Error instantiating inner object", var9);
                           } catch (IllegalAccessException var10) {
                              throw new FastParser.ParseException("Error instantiating inner object", var10);
                           }
                        }
                     }
                     break;
                  default:
                     int var13 = var7.zapq;
                     var11 = new StringBuilder(30);
                     var11.append("Invalid field type ");
                     var11.append(var13);
                     throw new FastParser.ParseException(var11.toString());
                  }

                  this.zak(4);
                  this.zak(2);
                  var3 = this.zaj(var1);
                  if(var3 != 44) {
                     if(var3 != 125) {
                        var11 = new StringBuilder(55);
                        var11.append("Expected end of object or field separator, but found: ");
                        var11.append(var3);
                        throw new FastParser.ParseException(var11.toString());
                     }

                     var5 = null;
                  } else {
                     var5 = this.zaa(var1);
                  }
               } else {
                  var5 = this.zab(var1);
               }
            }

            this.zak(1);
            return true;
         }
      }
   }

   private final boolean zaa(BufferedReader var1, boolean var2) throws FastParser.ParseException, IOException {
      while(true) {
         char var3 = this.zaj(var1);
         if(var3 != 34) {
            char[] var4;
            if(var3 != 102) {
               if(var3 != 110) {
                  if(var3 != 116) {
                     StringBuilder var5 = new StringBuilder(19);
                     var5.append("Unexpected token: ");
                     var5.append(var3);
                     throw new FastParser.ParseException(var5.toString());
                  }

                  if(var2) {
                     var4 = zaqh;
                  } else {
                     var4 = zaqg;
                  }

                  this.zab(var1, var4);
                  return true;
               }

               this.zab(var1, zaqf);
               return false;
            }

            if(var2) {
               var4 = zaqj;
            } else {
               var4 = zaqi;
            }

            this.zab(var1, var4);
            return false;
         }

         if(var2) {
            throw new FastParser.ParseException("No boolean value found in string");
         }

         var2 = true;
      }
   }

   // $FF: synthetic method
   static long zab(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zae(var1);
   }

   private final String zab(BufferedReader var1) throws FastParser.ParseException, IOException {
      var1.mark(1024);
      char var3 = this.zaj(var1);
      char var2;
      StringBuilder var7;
      boolean var8;
      if(var3 != 34) {
         if(var3 == 44) {
            throw new FastParser.ParseException("Missing value");
         }

         int var4 = 1;
         if(var3 != 91) {
            if(var3 != 123) {
               var1.reset();
               this.zaa(var1, this.zaqc);
            } else {
               this.zaql.push(Integer.valueOf(1));
               var1.mark(32);
               var2 = this.zaj(var1);
               if(var2 == 125) {
                  this.zak(1);
               } else {
                  if(var2 != 34) {
                     var7 = new StringBuilder(18);
                     var7.append("Unexpected token ");
                     var7.append(var2);
                     throw new FastParser.ParseException(var7.toString());
                  }

                  var1.reset();
                  this.zaa(var1);

                  while(this.zab(var1) != null) {
                     ;
                  }

                  this.zak(1);
               }
            }
         } else {
            this.zaql.push(Integer.valueOf(5));
            var1.mark(32);
            if(this.zaj(var1) == 93) {
               this.zak(5);
            } else {
               var1.reset();
               var8 = false;
               boolean var5 = false;

               while(var4 > 0) {
                  var2 = this.zaj(var1);
                  if(var2 == 0) {
                     throw new FastParser.ParseException("Unexpected EOF while parsing array");
                  }

                  if(Character.isISOControl(var2)) {
                     throw new FastParser.ParseException("Unexpected control character while reading array");
                  }

                  boolean var6 = var5;
                  if(var2 == 34) {
                     var6 = var5;
                     if(!var8) {
                        var6 = var5 ^ true;
                     }
                  }

                  int var10 = var4;
                  if(var2 == 91) {
                     var10 = var4;
                     if(!var6) {
                        var10 = var4 + 1;
                     }
                  }

                  var4 = var10;
                  if(var2 == 93) {
                     var4 = var10;
                     if(!var6) {
                        var4 = var10 - 1;
                     }
                  }

                  if(var2 == 92 && var6) {
                     var8 ^= true;
                     var5 = var6;
                  } else {
                     var8 = false;
                     var5 = var6;
                  }
               }

               this.zak(5);
            }
         }
      } else {
         if(var1.read(this.zaqa) == -1) {
            throw new FastParser.ParseException("Unexpected EOF while parsing string");
         }

         char var9 = this.zaqa[0];
         var8 = false;

         while(var9 != 34 || var8) {
            if(var9 == 92) {
               var8 ^= true;
            } else {
               var8 = false;
            }

            if(var1.read(this.zaqa) == -1) {
               throw new FastParser.ParseException("Unexpected EOF while parsing string");
            }

            var2 = this.zaqa[0];
            var9 = var2;
            if(Character.isISOControl(var2)) {
               throw new FastParser.ParseException("Unexpected control character while reading string");
            }
         }
      }

      var2 = this.zaj(var1);
      if(var2 != 44) {
         if(var2 != 125) {
            var7 = new StringBuilder(18);
            var7.append("Unexpected token ");
            var7.append(var2);
            throw new FastParser.ParseException(var7.toString());
         } else {
            this.zak(2);
            return null;
         }
      } else {
         this.zak(2);
         return this.zaa(var1);
      }
   }

   private static String zab(BufferedReader var0, char[] var1, StringBuilder var2, char[] var3) throws FastParser.ParseException, IOException {
      var2.setLength(0);
      var0.mark(var1.length);
      boolean var5 = false;
      boolean var6 = false;

      while(true) {
         int var9 = var0.read(var1);
         if(var9 == -1) {
            throw new FastParser.ParseException("Unexpected EOF while parsing string");
         }

         boolean var7 = var5;
         byte var8 = 0;
         var5 = var6;
         var6 = var7;

         for(int var10 = var8; var10 < var9; ++var10) {
            char var4 = var1[var10];
            if(Character.isISOControl(var4)) {
               boolean var12;
               label48: {
                  if(var3 != null) {
                     for(int var11 = 0; var11 < var3.length; ++var11) {
                        if(var3[var11] == var4) {
                           var12 = true;
                           break label48;
                        }
                     }
                  }

                  var12 = false;
               }

               if(!var12) {
                  throw new FastParser.ParseException("Unexpected control character while reading string");
               }
            }

            if(var4 == 34 && !var6) {
               var2.append(var1, 0, var10);
               var0.reset();
               var0.skip((long)(var10 + 1));
               if(var5) {
                  return JsonUtils.unescapeString(var2.toString());
               }

               return var2.toString();
            }

            if(var4 == 92) {
               var6 ^= true;
               var5 = true;
            } else {
               var6 = false;
            }
         }

         var2.append(var1, 0, var9);
         var0.mark(var1.length);
         var7 = var5;
         var5 = var6;
         var6 = var7;
      }
   }

   private final void zab(BufferedReader var1, char[] var2) throws FastParser.ParseException, IOException {
      int var5;
      for(int var3 = 0; var3 < var2.length; var3 += var5) {
         var5 = var1.read(this.zaqb, 0, var2.length - var3);
         if(var5 == -1) {
            throw new FastParser.ParseException("Unexpected EOF");
         }

         for(int var4 = 0; var4 < var5; ++var4) {
            if(var2[var4 + var3] != this.zaqb[var4]) {
               throw new FastParser.ParseException("Unexpected character");
            }
         }
      }

   }

   // $FF: synthetic method
   static float zac(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zag(var1);
   }

   private final String zac(BufferedReader var1) throws FastParser.ParseException, IOException {
      return this.zaa(var1, this.zaqb, this.zaqd, (char[])null);
   }

   // $FF: synthetic method
   static double zad(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zah(var1);
   }

   private final int zad(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var7 = this.zaa(var1, this.zaqc);
      if(var7 == 0) {
         return 0;
      } else {
         char[] var8 = this.zaqc;
         if(var7 > 0) {
            int var2;
            boolean var4;
            int var5;
            if(var8[0] == 45) {
               var2 = 1;
               var4 = true;
               var5 = Integer.MIN_VALUE;
            } else {
               var2 = 0;
               var4 = false;
               var5 = -2147483647;
            }

            int var3;
            int var6;
            if(var2 < var7) {
               var3 = var2 + 1;
               var2 = Character.digit(var8[var2], 10);
               if(var2 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               var6 = -var2;
               var2 = var3;
               var3 = var6;
            } else {
               var3 = 0;
            }

            while(var2 < var7) {
               var6 = Character.digit(var8[var2], 10);
               if(var6 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               if(var3 < -214748364) {
                  throw new FastParser.ParseException("Number too large");
               }

               var3 *= 10;
               if(var3 < var5 + var6) {
                  throw new FastParser.ParseException("Number too large");
               }

               var3 -= var6;
               ++var2;
            }

            if(var4) {
               if(var2 > 1) {
                  return var3;
               } else {
                  throw new FastParser.ParseException("No digits to parse");
               }
            } else {
               return -var3;
            }
         } else {
            throw new FastParser.ParseException("No number to parse");
         }
      }
   }

   private final long zae(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var5 = this.zaa(var1, this.zaqc);
      if(var5 == 0) {
         return 0L;
      } else {
         char[] var12 = this.zaqc;
         if(var5 > 0) {
            int var2 = 0;
            boolean var3;
            long var8;
            if(var12[0] == 45) {
               var8 = Long.MIN_VALUE;
               var2 = 1;
               var3 = true;
            } else {
               var8 = -9223372036854775807L;
               var3 = false;
            }

            int var4;
            long var6;
            if(var2 < var5) {
               var4 = var2 + 1;
               var2 = Character.digit(var12[var2], 10);
               if(var2 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               var6 = (long)(-var2);
               var2 = var4;
            } else {
               var6 = 0L;
            }

            while(var2 < var5) {
               var4 = Character.digit(var12[var2], 10);
               if(var4 < 0) {
                  throw new FastParser.ParseException("Unexpected non-digit character");
               }

               if(var6 < -922337203685477580L) {
                  throw new FastParser.ParseException("Number too large");
               }

               var6 *= 10L;
               long var10 = (long)var4;
               if(var6 < var8 + var10) {
                  throw new FastParser.ParseException("Number too large");
               }

               ++var2;
               var6 -= var10;
            }

            if(var3) {
               if(var2 > 1) {
                  return var6;
               } else {
                  throw new FastParser.ParseException("No digits to parse");
               }
            } else {
               return -var6;
            }
         } else {
            throw new FastParser.ParseException("No number to parse");
         }
      }
   }

   // $FF: synthetic method
   static String zae(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zac(var1);
   }

   // $FF: synthetic method
   static BigInteger zaf(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zaf(var1);
   }

   private final BigInteger zaf(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zaqc);
      return var2 == 0?null:new BigInteger(new String(this.zaqc, 0, var2));
   }

   private final float zag(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zaqc);
      return var2 == 0?0.0F:Float.parseFloat(new String(this.zaqc, 0, var2));
   }

   // $FF: synthetic method
   static BigDecimal zag(FastParser var0, BufferedReader var1) throws FastParser.ParseException, IOException {
      return var0.zai(var1);
   }

   private final double zah(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zaqc);
      return var2 == 0?0.0D:Double.parseDouble(new String(this.zaqc, 0, var2));
   }

   private final BigDecimal zai(BufferedReader var1) throws FastParser.ParseException, IOException {
      int var2 = this.zaa(var1, this.zaqc);
      return var2 == 0?null:new BigDecimal(new String(this.zaqc, 0, var2));
   }

   private final char zaj(BufferedReader var1) throws FastParser.ParseException, IOException {
      if(var1.read(this.zaqa) == -1) {
         return '\u0000';
      } else {
         do {
            if(!Character.isWhitespace(this.zaqa[0])) {
               return this.zaqa[0];
            }
         } while(var1.read(this.zaqa) != -1);

         return '\u0000';
      }
   }

   private final void zak(int var1) throws FastParser.ParseException {
      StringBuilder var3;
      if(this.zaql.isEmpty()) {
         var3 = new StringBuilder(46);
         var3.append("Expected state ");
         var3.append(var1);
         var3.append(" but had empty stack");
         throw new FastParser.ParseException(var3.toString());
      } else {
         int var2 = ((Integer)this.zaql.pop()).intValue();
         if(var2 != var1) {
            var3 = new StringBuilder(46);
            var3.append("Expected state ");
            var3.append(var1);
            var3.append(" but had ");
            var3.append(var2);
            throw new FastParser.ParseException(var3.toString());
         }
      }
   }

   @KeepForSdk
   public void parse(InputStream param1, T param2) throws FastParser.ParseException {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   @ShowFirstParty
   public static class ParseException extends Exception {

      public ParseException(String var1) {
         super(var1);
      }

      public ParseException(String var1, Throwable var2) {
         super(var1, var2);
      }

      public ParseException(Throwable var1) {
         super(var1);
      }
   }

   interface zaa<O extends Object> {

      O zah(FastParser var1, BufferedReader var2) throws FastParser.ParseException, IOException;
   }
}
