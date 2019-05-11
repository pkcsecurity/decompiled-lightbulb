package com.facebook.share;


public interface Sharer {

   boolean getShouldFailOnDataError();

   void setShouldFailOnDataError(boolean var1);

   public static class Result {

      final String postId;


      public Result(String var1) {
         this.postId = var1;
      }

      public String getPostId() {
         return this.postId;
      }
   }
}
