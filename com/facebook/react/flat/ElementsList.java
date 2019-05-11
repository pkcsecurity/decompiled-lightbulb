package com.facebook.react.flat;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;

final class ElementsList<E extends Object> {

   private ElementsList.Scope mCurrentScope = null;
   private final ArrayDeque<E> mElements = new ArrayDeque();
   private final E[] mEmptyArray;
   private int mScopeIndex = 0;
   private final ArrayList<ElementsList.Scope> mScopesStack = new ArrayList();


   public ElementsList(E[] var1) {
      this.mEmptyArray = var1;
      this.mScopesStack.add(this.mCurrentScope);
   }

   private E[] extractElements(int var1) {
      if(var1 == 0) {
         return this.mEmptyArray;
      } else {
         Object[] var2 = (Object[])Array.newInstance(this.mEmptyArray.getClass().getComponentType(), var1);
         --var1;

         while(var1 >= 0) {
            var2[var1] = this.mElements.pollLast();
            --var1;
         }

         return var2;
      }
   }

   private ElementsList.Scope getCurrentScope() {
      return this.mCurrentScope;
   }

   private void popScope() {
      --this.mScopeIndex;
      this.mCurrentScope = (ElementsList.Scope)this.mScopesStack.get(this.mScopeIndex);
   }

   private void pushScope() {
      ++this.mScopeIndex;
      if(this.mScopeIndex == this.mScopesStack.size()) {
         this.mCurrentScope = new ElementsList.Scope(null);
         this.mScopesStack.add(this.mCurrentScope);
      } else {
         this.mCurrentScope = (ElementsList.Scope)this.mScopesStack.get(this.mScopeIndex);
      }
   }

   public void add(E var1) {
      ElementsList.Scope var2 = this.getCurrentScope();
      if(var2.index < var2.elements.length && var2.elements[var2.index] == var1) {
         ++var2.index;
      } else {
         var2.index = Integer.MAX_VALUE;
      }

      this.mElements.add(var1);
   }

   public void clear() {
      if(this.getCurrentScope() != null) {
         throw new RuntimeException("Must call finish() for every start() call being made.");
      } else {
         this.mElements.clear();
      }
   }

   public E[] finish() {
      ElementsList.Scope var4 = this.getCurrentScope();
      this.popScope();
      int var2 = this.mElements.size() - var4.size;
      Object[] var3;
      if(var4.index != var4.elements.length) {
         var3 = this.extractElements(var2);
      } else {
         for(int var1 = 0; var1 < var2; ++var1) {
            this.mElements.pollLast();
         }

         var3 = null;
      }

      var4.elements = null;
      return var3;
   }

   public void start(Object[] var1) {
      this.pushScope();
      ElementsList.Scope var2 = this.getCurrentScope();
      var2.elements = var1;
      var2.index = 0;
      var2.size = this.mElements.size();
   }

   static final class Scope {

      Object[] elements;
      int index;
      int size;


      private Scope() {}

      // $FF: synthetic method
      Scope(Object var1) {
         this();
      }
   }
}
