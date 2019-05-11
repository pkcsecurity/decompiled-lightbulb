package com.fasterxml.jackson.core.sym;


public abstract class Name {

   protected final int _hashCode;
   protected final String _name;


   protected Name(String var1, int var2) {
      this._name = var1;
      this._hashCode = var2;
   }

   public abstract boolean equals(int var1);

   public abstract boolean equals(int var1, int var2);

   public boolean equals(Object var1) {
      return var1 == this;
   }

   public abstract boolean equals(int[] var1, int var2);

   public String getName() {
      return this._name;
   }

   public final int hashCode() {
      return this._hashCode;
   }

   public String toString() {
      return this._name;
   }
}
